/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.exception.DuplicateSXPElementExternalReferenceCodeException;
import com.liferay.search.experiences.exception.NoSuchSXPElementException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPElementLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddSXPElement() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		SXPElement sxpElement = _addSXPElement(externalReferenceCode);

		Assert.assertEquals(
			externalReferenceCode, sxpElement.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpElement.getVersion());
	}

	@Test
	public void testAddSXPElementWithDuplicateERCDifferentCompany()
		throws Exception {

		SXPElement sxpElement1 = _addSXPElement(RandomTestUtil.randomString());

		User user = UserTestUtil.addCompanyAdminUser(
			CompanyTestUtil.addCompany());

		SXPElement sxpElement2 = _addSXPElement(
			sxpElement1.getExternalReferenceCode(), user.getUserId());

		Assert.assertEquals(
			sxpElement1.getExternalReferenceCode(),
			sxpElement2.getExternalReferenceCode());
	}

	@Test
	public void testAddSXPElementWithDuplicateERCSameCompany()
		throws Exception {

		SXPElement sxpElement = _addSXPElement(RandomTestUtil.randomString());

		try {
			_addSXPElement(sxpElement.getExternalReferenceCode());

			Assert.fail();
		}
		catch (DuplicateSXPElementExternalReferenceCodeException
					duplicateSXPElementExternalReferenceCodeException) {

			Assert.assertNotNull(
				duplicateSXPElementExternalReferenceCodeException);
		}
	}

	@Test
	public void testAddSXPElementWithNullERC() throws Exception {
		SXPElement sxpElement = _addSXPElement(null);

		Assert.assertNotNull(sxpElement.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpElement.getVersion());
	}

	@Test
	public void testGetSXPElementByExternalReferenceCode() throws Exception {
		SXPElement sxpElement = _addSXPElement(RandomTestUtil.randomString());

		Assert.assertEquals(
			sxpElement,
			_sxpElementLocalService.getSXPElementByExternalReferenceCode(
				TestPropsValues.getCompanyId(),
				sxpElement.getExternalReferenceCode()));

		try {
			_sxpElementLocalService.getSXPElementByExternalReferenceCode(
				TestPropsValues.getCompanyId(), RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (NoSuchSXPElementException noSuchSXPElementException) {
			Assert.assertNotNull(noSuchSXPElementException);
		}
	}

	@Test
	public void testUpdateSXPElement() throws Exception {
		SXPElement sxpElement1 = _addSXPElement(RandomTestUtil.randomString());
		SXPElement sxpElement2 = _addSXPElement(RandomTestUtil.randomString());

		sxpElement2.setExternalReferenceCode(
			sxpElement1.getExternalReferenceCode());

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.batch.internal.BatchingBatch",
				LoggerTestUtil.ERROR);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.spi.SqlExceptionHelper",
				LoggerTestUtil.ERROR)) {

			try {
				_sxpElementLocalService.updateSXPElement(sxpElement2);

				Assert.fail();
			}
			catch (PersistenceException persistenceException) {
				Assert.assertNotNull(persistenceException);
			}
		}

		String externalReferenceCode = RandomTestUtil.randomString();

		sxpElement1.setExternalReferenceCode(externalReferenceCode);

		sxpElement1 = _sxpElementLocalService.updateSXPElement(sxpElement1);

		Assert.assertEquals(
			externalReferenceCode, sxpElement1.getExternalReferenceCode());

		sxpElement1 = _sxpElementLocalService.updateSXPElement(
			sxpElement1.getUserId(), sxpElement1.getSXPElementId(),
			sxpElement1.getDescriptionMap(),
			sxpElement1.getElementDefinitionJSON(), sxpElement1.isHidden(),
			sxpElement1.getSchemaVersion(), sxpElement1.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, sxpElement1.getExternalReferenceCode());
		Assert.assertEquals("1.1", sxpElement1.getVersion());

		sxpElement1 = _sxpElementLocalService.updateSXPElement(
			sxpElement1.getUserId(), sxpElement1.getSXPElementId(),
			sxpElement1.getDescriptionMap(),
			sxpElement1.getElementDefinitionJSON(), sxpElement1.isHidden(),
			sxpElement1.getSchemaVersion(), sxpElement1.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, sxpElement1.getExternalReferenceCode());
		Assert.assertEquals("1.2", sxpElement1.getVersion());
	}

	private SXPElement _addSXPElement(String externalReferenceCode)
		throws Exception {

		return _addSXPElement(
			externalReferenceCode, TestPropsValues.getUserId());
	}

	private SXPElement _addSXPElement(String externalReferenceCode, long userId)
		throws Exception {

		SXPElement sxpElement = _sxpElementLocalService.addSXPElement(
			externalReferenceCode, userId,
			Collections.singletonMap(LocaleUtil.US, ""), "{}", false,
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0, ServiceContextTestUtil.getServiceContext());

		_sxpElements.add(sxpElement);

		return sxpElement;
	}

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

	@DeleteAfterTestRun
	private List<SXPElement> _sxpElements = new ArrayList<>();

}