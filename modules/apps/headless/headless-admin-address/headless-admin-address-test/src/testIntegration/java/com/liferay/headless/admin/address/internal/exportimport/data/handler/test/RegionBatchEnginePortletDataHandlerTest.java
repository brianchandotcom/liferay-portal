/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.address.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.address.resource.v1_0.RegionResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@ExportImportScopes(Scope.COMPANY)
@RunWith(Arquillian.class)
public class RegionBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_indexReadOnly = IndexStatusManagerThreadLocal.isIndexReadOnly();

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);
	}

	@After
	public void tearDown() {
		IndexStatusManagerThreadLocal.setIndexReadOnly(_indexReadOnly);
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		Country country = _getCountry(groupId, userId);

		Region region = _regionLocalService.addRegion(
			RandomTestUtil.randomString(), country.getCountryId(), true,
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			StringUtil.toUpperCase(RandomTestUtil.randomString(4)),
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId, userId));

		region.setModifiedDate(dateModified);

		region = _regionLocalService.updateRegion(region);

		return region.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_regionLocalService.deleteRegion(
			_getRegion(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		Region region = _getRegion(groupId, externalReferenceCode);

		return region.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		Region region = _getRegion(groupId, externalReferenceCode);

		return region.getName();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			RegionResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		List<String> externalReferenceCodes = new ArrayList<>();

		for (Country country :
				_countryLocalService.getCompanyCountries(
					_getCompanyId(groupId))) {

			externalReferenceCodes.addAll(
				TransformUtil.transform(
					_regionLocalService.getRegions(
						country.getCountryId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null),
					Region::getExternalReferenceCode));
		}

		return externalReferenceCodes;
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		Region region = _getRegion(groupId, externalReferenceCode);

		return region.getRegionId();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return false;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		Region region = _getRegion(groupId, externalReferenceCode);

		_regionLocalService.updateRegion(
			region.getExternalReferenceCode(), region.getRegionId(),
			region.isActive(), RandomTestUtil.randomString(),
			region.getPosition(), region.getRegionCode());
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private Country _getCountry(long groupId, long userId) throws Exception {
		long companyId = _getCompanyId(groupId);

		Country country =
			_countryLocalService.fetchCountryByExternalReferenceCode(
				_COUNTRY_EXTERNAL_REFERENCE_CODE, companyId);

		if (country != null) {
			return country;
		}

		return _countryLocalService.addCountry(
			_COUNTRY_EXTERNAL_REFERENCE_CODE, _randomA2(companyId),
			_randomA3(companyId), true, true,
			String.valueOf(RandomTestUtil.randomInt(100, 999)),
			RandomTestUtil.randomString(),
			String.valueOf(RandomTestUtil.nextInt()),
			RandomTestUtil.randomDouble(), true, true, true,
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, userId));
	}

	private Region _getRegion(long groupId, String externalReferenceCode)
		throws Exception {

		return _regionLocalService.fetchRegionByExternalReferenceCode(
			externalReferenceCode, _getCompanyId(groupId));
	}

	private String _randomA2(long companyId) {
		return StringUtil.toUpperCase(
			RandomTestUtil.randomString(
				2,
				randomString -> {
					Country country = _countryLocalService.fetchCountryByA2(
						companyId, StringUtil.toUpperCase(randomString));

					return country == null;
				}));
	}

	private String _randomA3(long companyId) {
		return StringUtil.toUpperCase(
			RandomTestUtil.randomString(
				3,
				randomString -> {
					Country country = _countryLocalService.fetchCountryByA3(
						companyId, StringUtil.toUpperCase(randomString));

					return country == null;
				}));
	}

	private static final String _COUNTRY_EXTERNAL_REFERENCE_CODE =
		"batch-engine-region-test-country";

	@Inject
	private CountryLocalService _countryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	private boolean _indexReadOnly;

	@Inject
	private RegionLocalService _regionLocalService;

}