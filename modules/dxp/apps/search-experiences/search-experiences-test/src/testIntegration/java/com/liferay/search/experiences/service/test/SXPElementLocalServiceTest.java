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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
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
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_sxpElement = _sxpElementLocalService.addSXPElement(
			TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, ""), "{}", false,
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0,
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		_sxpElements.add(_sxpElement);
	}

	@Test
	public void testAddSXPElementWithExternalReferenceCodeVersion()
		throws Exception {

		Assert.assertEquals("1.0", _sxpElement.getVersion());

		Assert.assertNotNull(_sxpElement.getExternalReferenceCode());
	}

	@Test(expected = PersistenceException.class)
	public void testSXPElementExternalReferenceCodeUniqueness()
		throws Exception {

		Assert.assertEquals(
			_sxpElement,
			_sxpElementLocalService.getSXPElementByExternalReferenceCode(
				_group.getCompanyId(), _sxpElement.getExternalReferenceCode()));

		SXPElement sxpElement = _sxpElementLocalService.addSXPElement(
			TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, ""), "{}", false,
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0,
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		_sxpElements.add(sxpElement);

		sxpElement.setExternalReferenceCode(
			_sxpElement.getExternalReferenceCode());

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.batch.internal.BatchingBatch",
				LoggerTestUtil.ERROR);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.spi.SqlExceptionHelper",
				LoggerTestUtil.ERROR)) {

			_sxpElementLocalService.updateSXPElement(sxpElement);
		}
	}

	@Test
	public void testSXPElementIndexOnCompanyIdExternalReferenceCode()
		throws Exception {

		Assert.assertEquals(
			_sxpElement,
			_sxpElementLocalService.getSXPElementByExternalReferenceCode(
				_group.getCompanyId(), _sxpElement.getExternalReferenceCode()));

		_company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addCompanyAdminUser(_company);

		SXPElement sxpElement = _sxpElementLocalService.addSXPElement(
			user.getUserId(), Collections.singletonMap(LocaleUtil.US, ""), "{}",
			false, RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0,
			ServiceContextTestUtil.getServiceContext(_group, user.getUserId()));

		_sxpElements.add(sxpElement);

		sxpElement.setExternalReferenceCode(
			_sxpElement.getExternalReferenceCode());

		sxpElement = _sxpElementLocalService.updateSXPElement(sxpElement);

		Assert.assertEquals(
			_sxpElement.getExternalReferenceCode(),
			sxpElement.getExternalReferenceCode());
	}

	@Test
	public void testUpdateSXPElementWithCustomExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_sxpElement.setExternalReferenceCode(externalReferenceCode);

		_sxpElement = _sxpElementLocalService.updateSXPElement(_sxpElement);

		Assert.assertEquals(
			externalReferenceCode, _sxpElement.getExternalReferenceCode());
	}

	@Test
	public void testUpdateSXPElementWithExternalReferenceCodeVersion()
		throws Exception {

		String externalReferenceCode = _sxpElement.getExternalReferenceCode();

		_sxpElement = _sxpElementLocalService.updateSXPElement(
			_sxpElement.getUserId(), _sxpElement.getSXPElementId(),
			_sxpElement.getDescriptionMap(),
			_sxpElement.getElementDefinitionJSON(), _sxpElement.isHidden(),
			_sxpElement.getSchemaVersion(), _sxpElement.getTitleMap(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals("1.1", _sxpElement.getVersion());
		Assert.assertEquals(
			externalReferenceCode, _sxpElement.getExternalReferenceCode());

		_sxpElement = _sxpElementLocalService.updateSXPElement(
			_sxpElement.getUserId(), _sxpElement.getSXPElementId(),
			_sxpElement.getDescriptionMap(),
			_sxpElement.getElementDefinitionJSON(), _sxpElement.isHidden(),
			_sxpElement.getSchemaVersion(), _sxpElement.getTitleMap(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals("1.2", _sxpElement.getVersion());
		Assert.assertEquals(
			externalReferenceCode, _sxpElement.getExternalReferenceCode());
	}

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private Group _group;

	private SXPElement _sxpElement;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

	@DeleteAfterTestRun
	private List<SXPElement> _sxpElements = new ArrayList<>();

}