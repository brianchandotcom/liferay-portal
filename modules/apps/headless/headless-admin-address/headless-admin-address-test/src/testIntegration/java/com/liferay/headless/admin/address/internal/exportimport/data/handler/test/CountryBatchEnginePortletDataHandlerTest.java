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
import com.liferay.headless.admin.address.resource.v1_0.CountryResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class CountryBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_regionIndexReadOnly = _indexStatusManager.isIndexReadOnly(
			Region.class.getName());

		_indexStatusManager.setIndexReadOnly(Region.class.getName(), true);
	}

	@After
	public void tearDown() {
		_indexStatusManager.setIndexReadOnly(
			Region.class.getName(), _regionIndexReadOnly);
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		long companyId = _getCompanyId(groupId);

		Country country = _countryLocalService.addCountry(
			RandomTestUtil.randomString(), _randomA2(companyId),
			_randomA3(companyId), true, true,
			String.valueOf(RandomTestUtil.randomInt(100, 999)),
			RandomTestUtil.randomString(),
			String.valueOf(RandomTestUtil.nextInt()),
			RandomTestUtil.randomDouble(), true, true, true,
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, userId));

		country.setModifiedDate(dateModified);

		country = _countryLocalService.updateCountry(country);

		return country.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_countryLocalService.deleteCountry(
			_getCountry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		Country country = _getCountry(groupId, externalReferenceCode);

		return country.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		Country country = _getCountry(groupId, externalReferenceCode);

		return country.getName();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			CountryResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_countryLocalService.getCompanyCountries(_getCompanyId(groupId)),
			Country::getExternalReferenceCode);
	}

	@Override
	protected String getPermissionsActionKey() {
		return ActionKeys.UPDATE;
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		Country country = _getCountry(groupId, externalReferenceCode);

		return country.getCountryId();
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
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		Country country = _getCountry(groupId, externalReferenceCode);

		_countryLocalService.updateCountry(
			country.getExternalReferenceCode(), country.getCountryId(),
			country.getA2(), country.getA3(), country.isActive(),
			country.isBillingAllowed(), country.getIdd(),
			RandomTestUtil.randomString(), country.getNumber(),
			country.getPosition(), country.isShippingAllowed(),
			country.isSubjectToVAT());
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private Country _getCountry(long groupId, String externalReferenceCode)
		throws Exception {

		return _countryLocalService.fetchCountryByExternalReferenceCode(
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

	@Inject
	private CountryLocalService _countryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private IndexStatusManager _indexStatusManager;

	private boolean _regionIndexReadOnly;

}