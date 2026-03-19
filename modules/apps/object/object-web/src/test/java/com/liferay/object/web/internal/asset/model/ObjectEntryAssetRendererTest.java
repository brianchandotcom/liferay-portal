/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContextFactoryImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 */
public class ObjectEntryAssetRendererTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_objectFieldUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_setUpDLAppLocalService();
		_setUpObjectDefinition();
		_setUpObjectEntry();
		_setUpObjectField();
		_setUpObjectFieldLocalService();
		_setUpObjectFieldUtilMockedStatic();
	}

	@Test
	public void testGetSharingEntryRowPortletURL() throws Exception {
		Mockito.when(
			_objectDefinition.isCMS()
		).thenReturn(
			false
		);

		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Assert.assertNull(
			assetRenderer.getSharingEntryRowPortletURL(false, themeDisplay));

		Mockito.when(
			_objectDefinition.isCMS()
		).thenReturn(
			true
		);

		Assert.assertEquals(
			_getCMSFriendlyURL(themeDisplay),
			assetRenderer.getSharingEntryRowPortletURL(false, themeDisplay));
	}

	@Test
	public void testGetTitle() throws Exception {
		String title = RandomTestUtil.randomString();

		Mockito.when(
			_objectEntry.getTitleValue("en_US", true)
		).thenReturn(
			title
		);

		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		Assert.assertEquals(title, assetRenderer.getTitle(LocaleUtil.US));

		Mockito.verify(
			_objectEntry, Mockito.times(1)
		).getTitleValue(
			"en_US", true
		);
	}

	@Test
	public void testGetURLDownload() throws Exception {
		Mockito.when(
			_objectDefinition.isCMS()
		).thenReturn(
			true
		);

		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		Assert.assertEquals(
			_ATTACHMENT_DOWNLOAD_URL,
			assetRenderer.getURLDownload(
				new ThemeDisplay() {
					{
						setPermissionChecker(_permissionChecker);
					}
				}));
	}

	@Test
	public void testGetURLSharingNotification() throws Exception {
		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Assert.assertEquals(
			_getCMSFriendlyURL(themeDisplay),
			assetRenderer.getURLSharingNotification(themeDisplay));
	}

	@Test
	public void testGetURLViewInContext() throws Exception {
		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);

		Mockito.when(
			liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			null
		);

		LiferayPortletResponse liferayPortletResponse = Mockito.mock(
			LiferayPortletResponse.class);

		Assert.assertNull(
			assetRenderer.getURLViewInContext(
				liferayPortletRequest, liferayPortletResponse, null));
		Assert.assertEquals(
			_getFriendlyURL(liferayPortletRequest),
			assetRenderer.getURLViewInContext(
				liferayPortletRequest, liferayPortletResponse, null));
	}

	@Test
	public void testHasViewPermissionReturnsFalseOnFailure() throws Exception {
		Mockito.when(
			_objectEntryService.hasModelResourcePermission(
				_objectEntry, ActionKeys.VIEW)
		).thenThrow(
			new PortalException()
		);

		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		Assert.assertFalse(assetRenderer.hasViewPermission(_permissionChecker));
	}

	@Test
	public void testHasViewPermissionReturnsFalseWhenUserDoesNotHavePermission()
		throws Exception {

		Mockito.when(
			_objectEntryService.hasModelResourcePermission(
				_objectEntry, ActionKeys.VIEW)
		).thenReturn(
			false
		);

		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		Assert.assertFalse(assetRenderer.hasViewPermission(_permissionChecker));
	}

	@Test
	public void testHasViewPermissionReturnsTrueWhenUserHasPermission()
		throws Exception {

		Mockito.when(
			_objectEntryService.hasModelResourcePermission(
				_objectEntry, ActionKeys.VIEW)
		).thenReturn(
			true
		);

		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		Assert.assertTrue(assetRenderer.hasViewPermission(_permissionChecker));
	}

	private String _getCMSFriendlyURL(ThemeDisplay themeDisplay) {
		String pathMain = StringPool.SLASH + RandomTestUtil.randomString();

		Mockito.when(
			themeDisplay.getPathMain()
		).thenReturn(
			pathMain
		);

		String portalURL = "http://" + RandomTestUtil.randomString();

		Mockito.when(
			themeDisplay.getPortalURL()
		).thenReturn(
			portalURL
		);

		String urlCurrent = RandomTestUtil.randomString();

		Mockito.when(
			themeDisplay.getURLCurrent()
		).thenReturn(
			urlCurrent
		);

		long objectEntryId = RandomTestUtil.randomLong();

		Mockito.doReturn(
			objectEntryId
		).when(
			_objectEntry
		).getObjectEntryId();

		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.doReturn(
			DepotConstants.TYPE_SPACE
		).when(
			depotEntry
		).getType();

		Mockito.when(
			_depotEntryLocalService.fetchGroupDepotEntry(Mockito.anyLong())
		).thenReturn(
			depotEntry
		);

		return StringBundler.concat(
			portalURL, pathMain, GroupConstants.CMS_FRIENDLY_URL,
			"/edit_content_item?objectEntryId=", objectEntryId,
			"&p_l_mode=read&redirect=", HtmlUtil.escapeURL(urlCurrent));
	}

	private String _getFriendlyURL(LiferayPortletRequest liferayPortletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			themeDisplay
		).when(
			liferayPortletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		String modelClassName = RandomTestUtil.randomString();

		Mockito.doReturn(
			modelClassName
		).when(
			_objectEntry
		).getModelClassName();

		String friendlyURL = RandomTestUtil.randomString();

		Mockito.when(
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				new InfoItemReference(
					modelClassName, new ClassPKInfoItemIdentifier(0)),
				themeDisplay)
		).thenReturn(
			friendlyURL
		);

		return friendlyURL;
	}

	private AssetRenderer<ObjectEntry> _getObjectEntryAssetRenderer()
		throws Exception {

		return new ObjectEntryAssetRenderer(
			_assetDisplayPageFriendlyURLProvider, _depotEntryLocalService,
			_dlAppLocalService, _dlURLHelper, _objectDefinition, _objectEntry,
			_objectEntryDisplayContextFactoryImpl, _objectEntryService,
			_objectFieldLocalService);
	}

	private void _setUpDLAppLocalService() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileEntry(_FILE_ENTRY_ID)
		).thenReturn(
			_fileEntry
		);
	}

	private void _setUpObjectDefinition() {
		Mockito.when(
			_objectDefinition.getExternalReferenceCode()
		).thenReturn(
			_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODE
		);

		Mockito.when(
			_objectDefinition.getObjectDefinitionId()
		).thenReturn(
			_OBJECT_DEFINITION_ID
		);
	}

	private void _setUpObjectEntry() {
		Mockito.when(
			_objectEntry.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		Mockito.when(
			_objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME, _FILE_ENTRY_ID
			).build()
		);
	}

	private void _setUpObjectField() {
		Mockito.when(
			_objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)
		).thenReturn(
			true
		);

		Mockito.when(
			_objectField.getName()
		).thenReturn(
			_OBJECT_FIELD_NAME
		);
	}

	private void _setUpObjectFieldLocalService() {
		Mockito.when(
			_objectFieldLocalService.fetchObjectField(
				_OBJECT_DEFINITION_ID, "file")
		).thenReturn(
			_objectField
		);
	}

	private void _setUpObjectFieldUtilMockedStatic() throws Exception {
		_objectFieldUtilMockedStatic.when(
			() -> ObjectFieldUtil.getAttachmentDownloadURL(
				Mockito.eq(_dlURLHelper), Mockito.eq(_fileEntry),
				Mockito.eq(_GROUP_ID),
				Mockito.eq(_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODE),
				Mockito.eq(_objectEntry), Mockito.eq(_objectEntryService),
				Mockito.eq(_objectField), Mockito.eq(_permissionChecker),
				Mockito.any(ThemeDisplay.class))
		).thenReturn(
			_ATTACHMENT_DOWNLOAD_URL
		);
	}

	private static final String _ATTACHMENT_DOWNLOAD_URL =
		RandomTestUtil.randomString();

	private static final long _FILE_ENTRY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODE =
		RandomTestUtil.randomString();

	private static final long _OBJECT_DEFINITION_ID =
		RandomTestUtil.randomLong();

	private static final String _OBJECT_FIELD_NAME =
		RandomTestUtil.randomString();

	private static final MockedStatic<ObjectFieldUtil>
		_objectFieldUtilMockedStatic = Mockito.mockStatic(
			ObjectFieldUtil.class);

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider = Mockito.mock(
			AssetDisplayPageFriendlyURLProvider.class);
	private final DepotEntryLocalService _depotEntryLocalService = Mockito.mock(
		DepotEntryLocalService.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final DLURLHelper _dlURLHelper = Mockito.mock(DLURLHelper.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectEntry _objectEntry = Mockito.mock(ObjectEntry.class);
	private final ObjectEntryDisplayContextFactoryImpl
		_objectEntryDisplayContextFactoryImpl = Mockito.mock(
			ObjectEntryDisplayContextFactoryImpl.class);
	private final ObjectEntryService _objectEntryService = Mockito.mock(
		ObjectEntryService.class);
	private final ObjectField _objectField = Mockito.mock(ObjectField.class);
	private final ObjectFieldLocalService _objectFieldLocalService =
		Mockito.mock(ObjectFieldLocalService.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);

}