/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.exception.DuplicateSXPBlueprintExternalReferenceCodeException;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddSXPBlueprint() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		SXPBlueprint sxpBlueprint = _addSXPBlueprint(externalReferenceCode);

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpBlueprint.getVersion());

		// Duplicate external reference code in a different company

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		SXPBlueprint differentCompanySXPBlueprint = _addSXPBlueprint(
			sxpBlueprint.getExternalReferenceCode(), user.getUserId(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		_companyLocalService.deleteCompany(company);

		Assert.assertEquals(
			sxpBlueprint.getExternalReferenceCode(),
			differentCompanySXPBlueprint.getExternalReferenceCode());

		// Duplicate external reference code in the same company

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

		// Title

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		try {
			_addSXPBlueprint(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
				Collections.emptyMap(), serviceContext);
		}
		catch (SXPBlueprintTitleException sxpBlueprintTitleException) {
			Assert.assertNotNull(sxpBlueprintTitleException);
		}

		// Validate

		String attributeName =
			"com.liferay.search.experiences.service.impl." +
				"SXPBlueprintLocalServiceImpl#_validate";

		serviceContext.setAttribute(attributeName, Boolean.TRUE);

		try {
			_addSXPBlueprint(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
				Collections.emptyMap(), serviceContext);
		}
		catch (SXPBlueprintTitleException sxpBlueprintTitleException) {
			Assert.assertNotNull(sxpBlueprintTitleException);
		}

		serviceContext.setAttribute(attributeName, Boolean.FALSE);

		sxpBlueprint = _addSXPBlueprint(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			Collections.emptyMap(), serviceContext);

		Assert.assertEquals(Collections.emptyMap(), sxpBlueprint.getTitleMap());
	}

	@Test
	public void testGetSXPBlueprintByExternalReferenceCode() throws Exception {
		SXPBlueprint sxpBlueprint = _addSXPBlueprint(
			RandomTestUtil.randomString());

		Assert.assertEquals(
			sxpBlueprint,
			_sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				sxpBlueprint.getExternalReferenceCode(),
				TestPropsValues.getCompanyId()));

		try {
			_sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				RandomTestUtil.randomString(), TestPropsValues.getCompanyId());

			Assert.fail();
		}
		catch (NoSuchSXPBlueprintException noSuchSXPBlueprintException) {
			Assert.assertNotNull(noSuchSXPBlueprintException);
		}
	}

	@Test
	public void testUpdateSXPBlueprint() throws Exception {
		SXPBlueprint sxpBlueprint = _addSXPBlueprint(
			RandomTestUtil.randomString());

		String externalReferenceCode = RandomTestUtil.randomString();

		sxpBlueprint.setExternalReferenceCode(externalReferenceCode);

		sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint);

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint.getExternalReferenceCode());

		sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint.getExternalReferenceCode(), sxpBlueprint.getUserId(),
			sxpBlueprint.getSXPBlueprintId(),
			sxpBlueprint.getConfigurationJSON(),
			sxpBlueprint.getDescriptionMap(),
			sxpBlueprint.getElementInstancesJSON(),
			sxpBlueprint.getSchemaVersion(), sxpBlueprint.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.1", sxpBlueprint.getVersion());
	}

	@Test(expected = DuplicateSXPBlueprintExternalReferenceCodeException.class)
	public void testUpdateSXPBlueprintWithSameExternalReferenceCode()
		throws Exception {

		SXPBlueprint sxpBlueprint1 = _addSXPBlueprint(
			RandomTestUtil.randomString());
		SXPBlueprint sxpBlueprint2 = _addSXPBlueprint(
			RandomTestUtil.randomString());

		sxpBlueprint2.setExternalReferenceCode(
			sxpBlueprint1.getExternalReferenceCode());

		_sxpBlueprintLocalService.updateSXPBlueprint(sxpBlueprint2);
	}

	private SXPBlueprint _addSXPBlueprint(String externalReferenceCode)
		throws Exception {

		return _addSXPBlueprint(
			externalReferenceCode, TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());
	}

	private SXPBlueprint _addSXPBlueprint(
			String externalReferenceCode, long userId,
			Map<Locale, String> descriptionMap, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws Exception {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			externalReferenceCode, userId, "{}", descriptionMap, null,
			StringPool.BLANK, titleMap, serviceContext);

		_sxpBlueprints.add(sxpBlueprint);

		return sxpBlueprint;
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static String _originalName;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@DeleteAfterTestRun
	private List<SXPBlueprint> _sxpBlueprints = new ArrayList<>();

}