/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContextFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 */
public class ObjectEntryAssetRendererTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetURLViewInContext() throws Exception {
		AssetRenderer<ObjectEntry> assetRenderer =
			_getObjectEntryAssetRenderer();

		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);
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

	private String _getFriendlyURL(LiferayPortletRequest liferayPortletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
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
			_assetDisplayPageFriendlyURLProvider, _objectDefinition,
			_objectEntry, _objectEntryDisplayContextFactory,
			_objectEntryService);
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider = Mockito.mock(
			AssetDisplayPageFriendlyURLProvider.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectEntry _objectEntry = Mockito.mock(ObjectEntry.class);
	private final ObjectEntryDisplayContextFactory
		_objectEntryDisplayContextFactory = Mockito.mock(
			ObjectEntryDisplayContextFactory.class);
	private final ObjectEntryService _objectEntryService = Mockito.mock(
		ObjectEntryService.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);

}