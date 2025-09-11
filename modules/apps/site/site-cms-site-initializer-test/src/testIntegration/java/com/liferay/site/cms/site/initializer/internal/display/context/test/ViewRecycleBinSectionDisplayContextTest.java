/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.dto.v1_0.Settings;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Leite
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
@Sync
public class ViewRecycleBinSectionDisplayContextTest
	extends BaseSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		AssetLibraryResource.Builder builder =
			_assetLibraryResourceFactory.create();

		_assetLibraryResource = builder.user(
			UserTestUtil.getAdminUser(group.getCompanyId())
		).build();
	}

	@Test
	public void testGetCMSSectionFilterString() throws Exception {
		AssetLibrary assetLibrary = _addAssetLibrary();

		Settings settings = assetLibrary.getSettings();

		settings.setTrashEnabled(true);

		assetLibrary.setSettings(settings);

		Object displayContext = getSectionDisplayContext(
			getMockHttpServletRequest());

		String filter = ReflectionTestUtil.invoke(
			displayContext, "getCMSSectionFilterString", new Class<?>[0],
			new Object[0]);

		Group defaultGroup = _groupLocalService.loadGetGroup(
			group.getCompanyId(), "Default");

		Assert.assertTrue(
			filter.contains(
				StringBundler.concat(
					"groupIds/any(g:g in (", defaultGroup.getGroupId(), ",",
					assetLibrary.getSiteId())));

		assetLibrary.getSettings(
		).setTrashEnabled(
			false
		);

		assetLibrary.setCreatorUserId(defaultGroup.getCreatorUserId());

		Group assetLibraryGroup = _groupLocalService.getGroup(
			assetLibrary.getSiteId());

		UnicodeProperties unicodeProperties =
			assetLibraryGroup.getTypeSettingsProperties();

		unicodeProperties.setProperty("trashEnabled", "false");

		assetLibraryGroup.setTypeSettingsProperties(unicodeProperties);

		_groupLocalService.updateGroup(assetLibraryGroup);

		filter = ReflectionTestUtil.invoke(
			displayContext, "getCMSSectionFilterString", new Class<?>[0],
			new Object[0]);

		Assert.assertTrue(
			filter.contains(
				"groupIds/any(g:g in (" + defaultGroup.getGroupId() + "))"));
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			getFDSActionDropdownItems();

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 3,
			fdsActionDropdownItems.size());

		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(0), "view", "actionLinkFolder",
			"view-folder", "get", "item");
		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(1), "trash", "delete", "delete",
			"delete", "item");
		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(2), "restore", "restore", "restore",
			"restore", "item");
	}

	@Override
	protected Map<String, String> getExpectedCreationMenuItems()
		throws PortalException {

		return Collections.emptyMap();
	}

	protected MockHttpServletRequest getMockHttpServletRequest()
		throws Exception {

		return getMockHttpServletRequest(null);
	}

	@Override
	protected String getObjectFolderExternalReferenceCode() {
		if (RandomTestUtil.randomBoolean()) {
			return ObjectFolderConstants.
				EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES;
		}

		return ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES;
	}

	@Override
	protected String[] getObjectFolderExternalReferenceCodes() {
		return new String[] {
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		};
	}

	@Override
	protected Object getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object viewRecycleBinSectionDisplayContext =
			httpServletRequest.getAttribute(
				"com.liferay.site.cms.site.initializer.internal.display." +
					"context.ViewRecycleBinSectionDisplayContext");

		Assert.assertNotNull(viewRecycleBinSectionDisplayContext);

		return viewRecycleBinSectionDisplayContext;
	}

	private AssetLibrary _addAssetLibrary() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_SPACE,
			new ServiceContext() {
				{
					setCompanyId(group.getCompanyId());
					setUserId(
						UserTestUtil.getAdminUser(
							group.getCompanyId()
						).getUserId());
				}
			});

		return _assetLibraryResource.getAssetLibrary(depotEntry.getGroupId());
	}

	private AssetLibraryResource _assetLibraryResource;

	@Inject
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewRecycleBinJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private GroupLocalService _groupLocalService;

}