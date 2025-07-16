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

		boolean initialAutoTaggingEnabled = true;
		String[] initialAvailableLanguageIds = _getAvailableLanguageIds(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY);
		String initialDefaultLanguageId = _language.getLanguageId(
			LocaleUtil.US);
		String initialLogoColor = RandomTestUtil.randomString();
		MimeTypeLimit[] initialMimeTypeLimits = {
			new MimeTypeLimit() {
				{
					setMaximumSize(1234);
					setMimeType("application/pdf");
				}
			}
		};
		boolean initialSharingEnabled = true;
		boolean initialUseCustomLanguages = true;

		AssetLibrary assetLibrary = _postAssetLibraryWithSettings(
			initialAutoTaggingEnabled, initialAvailableLanguageIds,
			initialDefaultLanguageId, initialLogoColor, initialMimeTypeLimits,
			initialSharingEnabled, initialUseCustomLanguages);

		boolean patchAutoTaggingEnabled = false;

		assetLibrary.setSettings(
			new Settings() {
				{
					setAutoTaggingEnabled(() -> patchAutoTaggingEnabled);
				}
			});

		assetLibrary = assetLibraryResource.patchAssetLibrary(
			assetLibrary.getId(), assetLibrary);

		_assertSettings(
			assetLibrary, patchAutoTaggingEnabled, initialAvailableLanguageIds,
			initialDefaultLanguageId, initialLogoColor, initialMimeTypeLimits,
			initialSharingEnabled, initialUseCustomLanguages);
	}

	@Override
	@Test
	public void testPostAssetLibrary() throws Exception {
		super.testPostAssetLibrary();

		MimeTypeLimit[] mimeTypeLimits = {
			new MimeTypeLimit() {
				{
					setMaximumSize(1234);
					setMimeType("application/pdf");
				}
			}
		};

		_testPostAssetLibrary(mimeTypeLimits);

		_testPostAssetLibrary(null);
	}

	@Override
	@Test
	public void testPutAssetLibraryByExternalReferenceCode() throws Exception {
		super.testPutAssetLibraryByExternalReferenceCode();

		MimeTypeLimit[] mimeTypeLimits = {
			new MimeTypeLimit() {
				{
					setMaximumSize(1234);
					setMimeType("application/pdf");
				}
			}
		};

		_testPutAssetLibraryByExternalReferenceCode(mimeTypeLimits);

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
					setAutoTaggingEnabled(() -> false);
					setLogoColor(() -> "color-1");
					setSharingEnabled(() -> false);
					setUseCustomLanguages(() -> false);
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
			boolean initialAutoTaggingEnabled,
			String[] initialAvailableLanguageIds,
			String initialDefaultLanguageId, String initialLogoColor,
			MimeTypeLimit[] initialMimeTypeLimits,
			boolean initialSharingEnabled, boolean initialUseCustomLanguages)
		throws Exception {

		AssetLibrary assetLibrary = randomAssetLibrary();

		assetLibrary.setSettings(
			new Settings() {
				{
					setAutoTaggingEnabled(() -> initialAutoTaggingEnabled);
					setAvailableLanguageIds(() -> initialAvailableLanguageIds);
					setDefaultLanguageId(() -> initialDefaultLanguageId);
					setLogoColor(() -> initialLogoColor);
					setMimeTypeLimits(() -> initialMimeTypeLimits);
					setSharingEnabled(() -> initialSharingEnabled);
					setUseCustomLanguages(() -> initialUseCustomLanguages);
				}
			});

		return assetLibraryResource.postAssetLibrary(assetLibrary);
	}

	private void _testPostAssetLibrary(MimeTypeLimit[] initialMimeTypeLimits)
		throws Exception {

		boolean initialAutoTaggingEnabled = true;
		String[] initialAvailableLanguageIds = _getAvailableLanguageIds(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY);
		String initialDefaultLanguageId = _language.getLanguageId(
			LocaleUtil.US);
		String initialLogoColor = RandomTestUtil.randomString();
		boolean initialSharingEnabled = true;
		boolean initialUseCustomLanguages = true;

		AssetLibrary assetLibrary = _postAssetLibraryWithSettings(
			initialAutoTaggingEnabled, initialAvailableLanguageIds,
			initialDefaultLanguageId, initialLogoColor, initialMimeTypeLimits,
			initialSharingEnabled, initialUseCustomLanguages);

		_assertSettings(
			assetLibrary, initialAutoTaggingEnabled,
			initialAvailableLanguageIds, initialDefaultLanguageId,
			initialLogoColor, initialMimeTypeLimits, initialSharingEnabled,
			initialUseCustomLanguages);
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

		boolean putAutoTaggingEnabled = true;
		String[] putAvailableLanguageIds = _getAvailableLanguageIds(
			LocaleUtil.SPAIN);
		String putDefaultLanguageId = _language.getLanguageId(LocaleUtil.SPAIN);
		boolean putUseCustomLanguages = true;

		assetLibrary.setName_i18n(
			Collections.singletonMap(
				putDefaultLanguageId, RandomTestUtil.randomString()));
		assetLibrary.setSettings(
			new Settings() {
				{
					setAutoTaggingEnabled(() -> putAutoTaggingEnabled);
					setAvailableLanguageIds(() -> putAvailableLanguageIds);
					setDefaultLanguageId(() -> putDefaultLanguageId);
					setUseCustomLanguages(() -> putUseCustomLanguages);
				}
			});

		assetLibrary =
			assetLibraryResource.putAssetLibraryByExternalReferenceCode(
				assetLibrary.getExternalReferenceCode(), assetLibrary);

		_assertSettings(
			assetLibrary, putAutoTaggingEnabled, putAvailableLanguageIds,
			putDefaultLanguageId, "outline-0", null, false, true);
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