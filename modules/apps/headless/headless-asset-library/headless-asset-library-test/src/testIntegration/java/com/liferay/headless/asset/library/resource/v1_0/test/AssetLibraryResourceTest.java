/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.service.DepotEntryPinLocalService;
import com.liferay.headless.asset.library.client.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.client.dto.v1_0.MimeTypeLimit;
import com.liferay.headless.asset.library.client.dto.v1_0.Settings;
import com.liferay.headless.asset.library.client.problem.Problem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class AssetLibraryResourceTest extends BaseAssetLibraryResourceTestCase {

	@Override
	@Test
	public void testDeleteAssetLibrary() throws Exception {
		super.testDeleteAssetLibrary();

		// Nonexistent asset library ID

		long assetLibraryId = RandomTestUtil.randomLong();

		try {
			assetLibraryResource.deleteAssetLibrary(assetLibraryId);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testPatchAssetLibrary() throws Exception {
		super.testPatchAssetLibrary();

		String[] availableLanguageIds = _getAvailableLanguageIds(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY);
		String defaultLanguageId = _language.getLanguageId(LocaleUtil.US);
		String logoColor = RandomTestUtil.randomString();
		MimeTypeLimit[] mimeTypeLimits = {
			new MimeTypeLimit() {
				{
					maximumSize = 1234;
					mimeType = "application/pdf";
				}
			}
		};
		boolean sharingEnabled = true;
		boolean useCustomLanguages = true;

		AssetLibrary assetLibrary = _postAssetLibraryWithSettings(
			true, availableLanguageIds, defaultLanguageId, logoColor,
			mimeTypeLimits, sharingEnabled, useCustomLanguages);

		boolean autoTaggingEnabled = false;

		Settings settings = new Settings();

		settings.setAutoTaggingEnabled(autoTaggingEnabled);

		assetLibrary.setSettings(settings);

		assetLibrary = assetLibraryResource.patchAssetLibrary(
			assetLibrary.getId(), assetLibrary);

		_assertSettings(
			assetLibrary, autoTaggingEnabled, availableLanguageIds,
			defaultLanguageId, logoColor, mimeTypeLimits, sharingEnabled,
			useCustomLanguages);
	}

	@Override
	@Test
	public void testPostAssetLibrary() throws Exception {
		super.testPostAssetLibrary();

		_testPostAssetLibrary(
			new MimeTypeLimit[] {
				new MimeTypeLimit() {
					{
						maximumSize = 1234;
						mimeType = "application/pdf";
					}
				}
			});
		_testPostAssetLibrary(null);
	}

	@Override
	@Test
	public void testPutAssetLibraryByExternalReferenceCode() throws Exception {
		super.testPutAssetLibraryByExternalReferenceCode();

		_testPutAssetLibraryByExternalReferenceCode(
			new MimeTypeLimit[] {
				new MimeTypeLimit() {
					{
						maximumSize = 1234;
						mimeType = "application/pdf";
					}
				}
			});
		_testPutAssetLibraryByExternalReferenceCode(null);
	}

	@Override
	protected void assertValid(AssetLibrary assetLibrary) throws Exception {
		DepotEntry originalTestDepotEntry = testDepotEntry;
		Group originalTestGroup = testGroup;

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			assetLibrary.getId());

		testDepotEntry = depotEntry;
		testGroup = depotEntry.getGroup();

		super.assertValid(assetLibrary);

		testDepotEntry = originalTestDepotEntry;
		testGroup = originalTestGroup;
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	protected AssetLibrary randomAssetLibrary() throws Exception {
		AssetLibrary assetLibrary = super.randomAssetLibrary();

		assetLibrary.setSettings(
			new Settings() {
				{
					autoTaggingEnabled = false;
					logoColor = "color-1";
					sharingEnabled = false;
					useCustomLanguages = false;
				}
			});

		return assetLibrary;
	}

	@Override
	protected AssetLibrary randomPatchAssetLibrary() throws Exception {
		AssetLibrary assetLibrary = randomAssetLibrary();

		assetLibrary.setName(RandomTestUtil.randomString());

		return assetLibrary;
	}

	@Override
	protected AssetLibrary testDeleteAssetLibrary_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	protected AssetLibrary
			testDeleteAssetLibraryByExternalReferenceCode_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary
			testDeleteAssetLibraryByExternalReferenceCodePin_addAssetLibrary()
		throws Exception {

		return testDeleteAssetLibraryPin_addAssetLibrary();
	}

	@Override
	protected AssetLibrary testDeleteAssetLibraryPin_addAssetLibrary()
		throws Exception {

		AssetLibrary assetLibrary = _addAssetLibrary();

		return assetLibraryResource.putAssetLibraryPin(assetLibrary.getId());
	}

	@Override
	protected AssetLibrary testGetAssetLibrariesPage_addAssetLibrary(
			AssetLibrary assetLibrary)
		throws Exception {

		return assetLibraryResource.postAssetLibrary(assetLibrary);
	}

	@Override
	protected AssetLibrary testGetAssetLibrariesPinnedByMePage_addAssetLibrary(
			AssetLibrary assetLibrary)
		throws Exception {

		assetLibrary = assetLibraryResource.postAssetLibrary(assetLibrary);

		return assetLibraryResource.putAssetLibraryPin(assetLibrary.getId());
	}

	@Override
	protected AssetLibrary testGetAssetLibrary_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary
			testGetAssetLibraryByExternalReferenceCode_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testPatchAssetLibrary_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary
			testPatchAssetLibraryByExternalReferenceCode_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testPostAssetLibrary_addAssetLibrary(
			AssetLibrary assetLibrary)
		throws Exception {

		return assetLibraryResource.postAssetLibrary(assetLibrary);
	}

	@Override
	protected AssetLibrary
			testPutAssetLibraryByExternalReferenceCode_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary
			testPutAssetLibraryByExternalReferenceCodePin_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary
		testPutAssetLibraryByExternalReferenceCodePin_getAssetLibrary(
			String externalReferenceCode) {

		try {
			Group group = _groupLocalService.getGroupByExternalReferenceCode(
				externalReferenceCode, testCompany.getCompanyId());

			DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
				group.getGroupId());

			return testPutAssetLibraryPin_getAssetLibrary(
				depotEntry.getDepotEntryId());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected AssetLibrary testPutAssetLibraryPin_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testPutAssetLibraryPin_getAssetLibrary(
		Long assetLibraryId) {

		try {
			User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

			Assert.assertNotNull(
				_depotEntryPinLocalService.getDepotEntryPin(
					user.getUserId(), assetLibraryId));

			return assetLibraryResource.getAssetLibrary(assetLibraryId);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private AssetLibrary _addAssetLibrary() throws Exception {
		return assetLibraryResource.postAssetLibrary(randomAssetLibrary());
	}

	private void _assertSettings(
		AssetLibrary assetLibrary, boolean expectedAutoTaggingEnabled,
		String[] expectedAvailableLanguageIds, String expectedDefaultLanguageId,
		String expectedLogoColor, MimeTypeLimit[] expectedMimeTypeLimits,
		boolean expectedSharingEnabled, boolean expectedUseCustomLanguages) {

		Settings settings = assetLibrary.getSettings();

		Assert.assertEquals(
			expectedAutoTaggingEnabled, settings.getAutoTaggingEnabled());
		Assert.assertEquals(
			expectedDefaultLanguageId, settings.getDefaultLanguageId());
		Assert.assertEquals(
			expectedAvailableLanguageIds, settings.getAvailableLanguageIds());
		Assert.assertEquals(expectedLogoColor, settings.getLogoColor());

		MimeTypeLimit[] mimeTypeLimits = settings.getMimeTypeLimits();

		if (expectedMimeTypeLimits == null) {
			Assert.assertEquals(
				Arrays.toString(mimeTypeLimits), 0, mimeTypeLimits.length);
		}
		else {
			Assert.assertEquals(
				Arrays.toString(mimeTypeLimits), mimeTypeLimits.length,
				mimeTypeLimits.length);
			Assert.assertEquals(expectedMimeTypeLimits[0], mimeTypeLimits[0]);
		}

		Assert.assertEquals(
			expectedSharingEnabled, settings.getSharingEnabled());
		Assert.assertEquals(
			expectedUseCustomLanguages, settings.getUseCustomLanguages());
	}

	private String[] _getAvailableLanguageIds(Locale... locales) {
		return TransformUtil.transformToArray(
			ListUtil.fromArray(locales),
			(Locale locale) -> _language.getLanguageId(locale), String.class);
	}

	private AssetLibrary _postAssetLibraryWithSettings(
			boolean autoTaggingEnabled, String[] availableLanguageIds,
			String defaultLanguageId, String logoColor,
			MimeTypeLimit[] mimeTypeLimits, boolean sharingEnabled,
			boolean useCustomLanguages)
		throws Exception {

		AssetLibrary assetLibrary = randomAssetLibrary();

		Settings settings = new Settings();

		settings.setAutoTaggingEnabled(autoTaggingEnabled);
		settings.setAvailableLanguageIds(availableLanguageIds);
		settings.setDefaultLanguageId(defaultLanguageId);
		settings.setLogoColor(logoColor);
		settings.setMimeTypeLimits(mimeTypeLimits);
		settings.setSharingEnabled(sharingEnabled);
		settings.setUseCustomLanguages(useCustomLanguages);

		assetLibrary.setSettings(settings);

		return assetLibraryResource.postAssetLibrary(assetLibrary);
	}

	private void _testPostAssetLibrary(MimeTypeLimit[] mimeTypeLimits)
		throws Exception {

		boolean autoTaggingEnabled = true;
		String[] availableLanguageIds = _getAvailableLanguageIds(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY);
		String defaultLanguageId = _language.getLanguageId(LocaleUtil.US);
		String logoColor = RandomTestUtil.randomString();
		boolean sharingEnabled = true;
		boolean useCustomLanguages = true;

		AssetLibrary assetLibrary = _postAssetLibraryWithSettings(
			autoTaggingEnabled, availableLanguageIds, defaultLanguageId,
			logoColor, mimeTypeLimits, sharingEnabled, useCustomLanguages);

		_assertSettings(
			assetLibrary, autoTaggingEnabled, availableLanguageIds,
			defaultLanguageId, logoColor, mimeTypeLimits, sharingEnabled,
			useCustomLanguages);
	}

	private void _testPutAssetLibraryByExternalReferenceCode(
			MimeTypeLimit[] mimeTypeLimits)
		throws Exception {

		AssetLibrary assetLibrary = _postAssetLibraryWithSettings(
			true,
			_getAvailableLanguageIds(
				LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			_language.getLanguageId(LocaleUtil.US),
			RandomTestUtil.randomString(), mimeTypeLimits, true, true);

		String defaultLanguageId = _language.getLanguageId(LocaleUtil.SPAIN);

		assetLibrary.setName_i18n(
			Collections.singletonMap(
				defaultLanguageId, RandomTestUtil.randomString()));

		Settings settings = new Settings();

		boolean autoTaggingEnabled = true;

		settings.setAutoTaggingEnabled(autoTaggingEnabled);

		String[] availableLanguageIds = _getAvailableLanguageIds(
			LocaleUtil.SPAIN);

		settings.setAvailableLanguageIds(availableLanguageIds);

		settings.setDefaultLanguageId(defaultLanguageId);

		boolean useCustomLanguages = true;

		settings.setUseCustomLanguages(useCustomLanguages);

		assetLibrary.setSettings(settings);

		assetLibrary =
			assetLibraryResource.putAssetLibraryByExternalReferenceCode(
				assetLibrary.getExternalReferenceCode(), assetLibrary);

		_assertSettings(
			assetLibrary, autoTaggingEnabled, availableLanguageIds,
			defaultLanguageId, "outline-0", null, false, true);
	}

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DepotEntryPinLocalService _depotEntryPinLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _language;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

}