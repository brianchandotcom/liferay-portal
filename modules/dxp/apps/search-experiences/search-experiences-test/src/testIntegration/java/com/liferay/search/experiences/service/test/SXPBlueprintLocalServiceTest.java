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
import com.liferay.search.experiences.exception.DuplicateSXPBlueprintExternalReferenceCodeException;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

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
public class SXPBlueprintLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddSXPBlueprint() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		SXPBlueprint sxpBlueprint = _addSXPBlueprint(externalReferenceCode);

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpBlueprint.getVersion());

		// Duplicate external reference code in different company

		User user = UserTestUtil.addCompanyAdminUser(
			CompanyTestUtil.addCompany());

		SXPBlueprint differentCompanySXPBlueprint = _addSXPBlueprint(
			sxpBlueprint.getExternalReferenceCode(), user.getUserId());

		Assert.assertEquals(
			sxpBlueprint.getExternalReferenceCode(),
			differentCompanySXPBlueprint.getExternalReferenceCode());

		// Duplicate external reference code in same company

		try {
			_addSXPBlueprint(sxpBlueprint.getExternalReferenceCode());

			Assert.fail();
		}
		catch (DuplicateSXPBlueprintExternalReferenceCodeException
					duplicateSXPBlueprintExternalReferenceCodeException) {

			Assert.assertNotNull(
				duplicateSXPBlueprintExternalReferenceCodeException);
		}

		// Null external reference code

		sxpBlueprint = _addSXPBlueprint(null);

		Assert.assertNotNull(sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpBlueprint.getVersion());
	}

	@Test
	public void testGetSXPBlueprintByExternalReferenceCode() throws Exception {
		SXPBlueprint sxpBlueprint = _addSXPBlueprint(
			RandomTestUtil.randomString());

		Assert.assertEquals(
			sxpBlueprint,
			_sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				TestPropsValues.getCompanyId(),
				sxpBlueprint.getExternalReferenceCode()));

		try {
			_sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				TestPropsValues.getCompanyId(), RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (NoSuchSXPBlueprintException noSuchSXPBlueprintException) {
			Assert.assertNotNull(noSuchSXPBlueprintException);
		}
	}

	@Test
	public void testUpdateSXPBlueprint() throws Exception {
		SXPBlueprint sxpBlueprint1 = _addSXPBlueprint(
			RandomTestUtil.randomString());
		SXPBlueprint sxpBlueprint2 = _addSXPBlueprint(
			RandomTestUtil.randomString());

		sxpBlueprint2.setExternalReferenceCode(
			sxpBlueprint1.getExternalReferenceCode());

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.batch.internal.BatchingBatch",
				LoggerTestUtil.ERROR);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.spi.SqlExceptionHelper",
				LoggerTestUtil.ERROR)) {

			try {
				_sxpBlueprintLocalService.updateSXPBlueprint(sxpBlueprint2);

				Assert.fail();
			}
			catch (PersistenceException persistenceException) {
				Assert.assertNotNull(persistenceException);
			}
		}

		String externalReferenceCode = RandomTestUtil.randomString();

		sxpBlueprint1.setExternalReferenceCode(externalReferenceCode);

		sxpBlueprint1 = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint1);

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint1.getExternalReferenceCode());

		sxpBlueprint1 = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint1.getUserId(), sxpBlueprint1.getSXPBlueprintId(),
			sxpBlueprint1.getConfigurationJSON(),
			sxpBlueprint1.getDescriptionMap(),
			sxpBlueprint1.getElementInstancesJSON(),
			sxpBlueprint1.getSchemaVersion(), sxpBlueprint1.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint1.getExternalReferenceCode());
		Assert.assertEquals("1.1", sxpBlueprint1.getVersion());

		sxpBlueprint1 = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint1.getUserId(), sxpBlueprint1.getSXPBlueprintId(),
			sxpBlueprint1.getConfigurationJSON(),
			sxpBlueprint1.getDescriptionMap(),
			sxpBlueprint1.getElementInstancesJSON(),
			sxpBlueprint1.getSchemaVersion(), sxpBlueprint1.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint1.getExternalReferenceCode());
		Assert.assertEquals("1.2", sxpBlueprint1.getVersion());
	}

	private SXPBlueprint _addSXPBlueprint(String externalReferenceCode)
		throws Exception {

		return _addSXPBlueprint(
			externalReferenceCode, TestPropsValues.getUserId());
	}

	private SXPBlueprint _addSXPBlueprint(
			String externalReferenceCode, long userId)
		throws Exception {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			externalReferenceCode, userId, "{}",
			Collections.singletonMap(LocaleUtil.US, ""), null, "",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		_sxpBlueprints.add(sxpBlueprint);

		return sxpBlueprint;
	}

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@DeleteAfterTestRun
	private List<SXPBlueprint> _sxpBlueprints = new ArrayList<>();

}