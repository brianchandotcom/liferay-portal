/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.odata.retriever.test;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.time.Instant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class OrganizationODataRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetOrganizationsFilterByAssetTagIds() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);
		_organizations.add(organization2);

		Company company = _companyLocalService.getCompany(
			organization1.getCompanyId());

		AssetTag tag = AssetTestUtil.addTag(company.getGroupId(), "tag1");

		_assetTags.add(tag);

		_organizationLocalService.updateAsset(
			TestPropsValues.getUserId(), organization1, new long[0],
			new String[] {tag.getName()});

		String filterString = String.format(
			"(name eq '%s') and (assetTagIds eq '%s')", organization1.getName(),
			tag.getTagId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCompanyId() throws Exception {
		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString = String.format(
			"(companyId eq '%s') and (parentOrganizationId eq '%s')",
			organization.getCompanyId(),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCountryAndRegion()
		throws Exception {

		Country country = _countryService.getCountryByName(
			TestPropsValues.getCompanyId(), "spain");

		List<Region> regions = _regionService.getRegions(
			country.getCountryId());

		Region region = regions.get(0);

		Organization organization1 = _organizationLocalService.addOrganization(
			null, TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			OrganizationConstants.TYPE_ORGANIZATION, region.getRegionId(),
			country.getCountryId(),
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			true, new ServiceContext());

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);
		_organizations.add(organization2);

		String filterString = String.format(
			"(country eq '%s') and (region eq '%s')",
			StringUtil.toLowerCase(country.getName()),
			StringUtil.toLowerCase(region.getName()));

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCountryForOrganizationUser()
		throws Exception {

		Country country = _countryService.getCountryByName(
			TestPropsValues.getCompanyId(), "brazil");

		List<Region> regions = _regionService.getRegions(
			country.getCountryId());

		Region region = regions.get(0);

		Organization organization = _organizationLocalService.addOrganization(
			null, TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			OrganizationConstants.TYPE_ORGANIZATION, region.getRegionId(),
			country.getCountryId(),
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			true, new ServiceContext());

		String filterString = String.format(
			"(country eq '%s')", StringUtil.toLowerCase(country.getName()));

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = UserTestUtil.addOrganizationUser(
				organization, RoleConstants.ORGANIZATION_USER);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			int count = _oDataRetriever.getResultsCount(
				TestPropsValues.getCompanyId(), filterString,
				LocaleUtil.getDefault());

			Assert.assertEquals(1, count);

			List<Organization> organizations = _oDataRetriever.getResults(
				TestPropsValues.getCompanyId(), filterString,
				LocaleUtil.getDefault(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.assertEquals(organization, organizations.get(0));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedEquals()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified eq %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization1.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedGreater()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		organization2 = _organizationLocalService.updateOrganization(
			organization2);

		String filterString = String.format(
			"(dateModified gt %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization1.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization2, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedGreaterOrEquals()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		organization2 = _organizationLocalService.updateOrganization(
			organization2);

		String filterString = String.format(
			"(dateModified ge %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization2.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization2, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedLower()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		organization2 = _organizationLocalService.updateOrganization(
			organization2);

		String filterString = String.format(
			"(dateModified lt %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization2.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedLowerOrEquals()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified le %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(modifiedDate),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByName() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);

		_organizations.add(organization2);

		String filterString = "(name eq '" + organization1.getName() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByNameTreePath() throws Exception {
		Organization parentOrganization = OrganizationTestUtil.addOrganization(
			0, "A", false);

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(), "B", false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString = "(nameTreePath eq 'A > B')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByNameTreePathContains()
		throws Exception {

		Organization parentOrganization = OrganizationTestUtil.addOrganization(
			0, RandomTestUtil.randomString(), false);

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString =
			"contains(nameTreePath, '" + organization.getName() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByOrganizationId() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);

		_organizations.add(organization2);

		String filterString =
			"(organizationId eq '" + organization1.getOrganizationId() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByParentOrganizationId()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString =
			"(parentOrganizationId eq '" +
				parentOrganization.getOrganizationId() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByParentOrganizationIdAndNotOrganizationId()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		String filterString = String.format(
			"(parentOrganizationId eq '%s') and (not (organizationId eq '%s'))",
			parentOrganization.getOrganizationId(),
			organization2.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByType() throws Exception {
		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString = String.format(
			" (parentOrganizationId eq '%s') and (type eq '%s')",
			parentOrganization.getOrganizationId(), organization.getType());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@DeleteAfterTestRun
	private final List<AssetTag> _assetTags = new ArrayList<>();

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CountryService _countryService;

	@Inject(
		filter = "model.class.name=com.liferay.portal.kernel.model.Organization"
	)
	private ODataRetriever<Organization> _oDataRetriever;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

	@Inject
	private RegionService _regionService;

}