/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.configuration.FragmentPortletConfiguration;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProvider;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProviderUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class BasicFragmentEntryActionDropdownItemsProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpFragmentPortletConfiguration();
		_setUpHttpServletRequest();
		_setUpItemSelector();
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpPortletURLBuilder();
		_setUpRenderResponse();
		_setUpUploadServletRequestConfigurationProviderUtil();
	}

	@After
	public void tearDown() {
		_fragmentPermissionMockedStatic.close();
		_portletURLBuilderMockedStatic.close();
		_uploadServletRequestConfigurationProviderUtilMockedStatic.close();
	}

	@Test
	public void testGetActionDropdownsWithManageFragmentEntries()
		throws Exception {

		_setUpFragmentPermission(true);
		_setUpFragmentEntry(false, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, _renderRequest, _renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertEquals(dropdownItems.toString(), 9, dropdownItems.size());
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "change-thumbnail"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "delete"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "edit"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "export"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "make-a-copy"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "mark-as-cacheable"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "move"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "rename"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "view-site-usages"));
	}

	@Test
	public void testGetActionDropdownsWithoutManageFragmentEntries()
		throws Exception {

		_setUpFragmentPermission(false);
		_setUpFragmentEntry(false, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, _renderRequest, _renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertTrue(dropdownItems.isEmpty());
	}

	@Test
	public void testGetReactFragmentEntryActionDropdowns() throws Exception {
		_setUpFragmentPermission(true);
		_setUpFragmentEntry(false, true);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, _renderRequest, _renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertEquals(dropdownItems.toString(), 6, dropdownItems.size());
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "change-thumbnail"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "delete"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "make-a-copy"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "move"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "rename"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "view-site-usages"));
	}

	@Test
	public void testGetReadonlyFragmentEntryActionDropdowns() throws Exception {
		_setUpFragmentPermission(true);
		_setUpFragmentEntry(true, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, _renderRequest, _renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertEquals(dropdownItems.toString(), 2, dropdownItems.size());
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "edit"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "make-a-copy"));
	}

	private DropdownItem _findFirstDropdownItem(
		List<DropdownItem> dropdownItems, String label) {

		for (DropdownItem dropdownItem : dropdownItems) {
			if (StringUtil.equals((String)dropdownItem.get("label"), label)) {
				return dropdownItem;
			}
		}

		return null;
	}

	private List<DropdownItem> _getActionDropdownItems(
		List<DropdownItem> dropdownItems) {

		List<DropdownItem> allDropdownItems = new ArrayList<>();

		for (DropdownItem dropdownItem : dropdownItems) {
			if (!StringUtil.equals((String)dropdownItem.get("type"), "group")) {
				allDropdownItems.add(dropdownItem);

				continue;
			}

			allDropdownItems.addAll(
				(List<DropdownItem>)dropdownItem.get("items"));
		}

		return allDropdownItems;
	}

	private void _setUpFragmentEntry(boolean readOnly, boolean typeReact) {
		Mockito.when(
			_fragmentEntry.getGlobalUsageCount()
		).thenReturn(
			0
		);

		Mockito.when(
			_fragmentEntry.isReadOnly()
		).thenReturn(
			readOnly
		);

		Mockito.when(
			_fragmentEntry.isTypeReact()
		).thenReturn(
			typeReact
		);
	}

	private void _setUpFragmentPermission(boolean contains) {
		Mockito.when(
			FragmentPermission.contains(
				Mockito.any(), Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			contains
		);
	}

	private void _setUpFragmentPortletConfiguration() {
		Mockito.when(
			_fragmentPortletConfiguration.thumbnailExtensions()
		).thenReturn(
			new String[0]
		);
	}

	private void _setUpHttpServletRequest() {
		Mockito.when(
			_httpServletRequest.getAttribute(
				FragmentPortletConfiguration.class.getName())
		).thenReturn(
			_fragmentPortletConfiguration
		);

		Mockito.when(
			_httpServletRequest.getAttribute(ItemSelector.class.getName())
		).thenReturn(
			_itemSelector
		);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);
	}

	private void _setUpItemSelector() {
		Mockito.when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(), Mockito.anyString(), Mockito.any())
		).thenReturn(
			Mockito.mock(PortletURL.class)
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));

		Mockito.when(
			languageUtil.get(_httpServletRequest, "change-thumbnail")
		).thenReturn(
			"change-thumbnail"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "copy-to")
		).thenReturn(
			"copy-to"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "edit")
		).thenReturn(
			"edit"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "export")
		).thenReturn(
			"export"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "delete")
		).thenReturn(
			"delete"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "make-a-copy")
		).thenReturn(
			"make-a-copy"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "mark-as-cacheable")
		).thenReturn(
			"mark-as-cacheable"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "move")
		).thenReturn(
			"move"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "rename")
		).thenReturn(
			"rename"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "view-site-usages")
		).thenReturn(
			"view-site-usages"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "view-usages")
		).thenReturn(
			"view-usages"
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		Mockito.when(
			portalUtil.getHttpServletRequest(_renderRequest)
		).thenReturn(
			_httpServletRequest
		);
	}

	private void _setUpPortletURLBuilder() {
		Mockito.when(
			PortletURLBuilder.create(Mockito.any())
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);

		Mockito.when(
			PortletURLBuilder.createActionURL(_renderResponse)
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);

		Mockito.when(
			PortletURLBuilder.createRenderURL(_renderResponse)
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);
	}

	private void _setUpRenderResponse() {
		Mockito.when(
			_renderResponse.createRenderURL()
		).thenReturn(
			Mockito.mock(LiferayPortletURL.class)
		);

		Mockito.when(
			_renderResponse.createResourceURL()
		).thenReturn(
			Mockito.mock(LiferayPortletURL.class)
		);
	}

	private void _setUpUploadServletRequestConfigurationProviderUtil() {
		UploadServletRequestConfigurationProvider
			uploadServletRequestConfigurationProvider = Mockito.mock(
				UploadServletRequestConfigurationProvider.class);

		Mockito.when(
			uploadServletRequestConfigurationProvider.getMaxSize()
		).thenReturn(
			0L
		);
	}

	private final FragmentEntry _fragmentEntry = Mockito.mock(
		FragmentEntry.class);
	private final MockedStatic<FragmentPermission>
		_fragmentPermissionMockedStatic = Mockito.mockStatic(
			FragmentPermission.class);
	private final FragmentPortletConfiguration _fragmentPortletConfiguration =
		Mockito.mock(FragmentPortletConfiguration.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final ItemSelector _itemSelector = Mockito.mock(ItemSelector.class);
	private final MockedStatic<PortletURLBuilder>
		_portletURLBuilderMockedStatic = Mockito.mockStatic(
			PortletURLBuilder.class);
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);
	private final MockedStatic<UploadServletRequestConfigurationProviderUtil>
		_uploadServletRequestConfigurationProviderUtilMockedStatic =
			Mockito.mockStatic(
				UploadServletRequestConfigurationProviderUtil.class);

}