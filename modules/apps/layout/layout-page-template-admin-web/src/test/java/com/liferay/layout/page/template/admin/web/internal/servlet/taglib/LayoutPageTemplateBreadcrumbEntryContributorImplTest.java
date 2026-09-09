/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib;

import com.liferay.design.library.util.DesignLibraryUtil;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletURL;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Georgel Pop
 */
public class LayoutPageTemplateBreadcrumbEntryContributorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpLayoutPageTemplateBreadcrumbEntryContributorImpl();
		_setUpMockHttpServletRequest();
		_setUpPortal();
	}

	@After
	public void tearDown() {
		_designLibraryUtilMockedStatic.close();
		_layoutPageTemplatePortletUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-104843")
	public void testGetBreadcrumbEntries() {
		_testGetBreadcrumbEntriesWithLayoutPageTemplateCollection();
		_testGetBreadcrumbEntriesWithoutDesignLibraryScope();
		_testGetBreadcrumbEntriesWithoutLayoutPageTemplatesPortlet();
	}

	private LayoutPageTemplateCollection _mockLayoutPageTemplateCollection() {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			Mockito.mock(LayoutPageTemplateCollection.class);

		Mockito.when(
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			layoutPageTemplateCollection.getName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_layoutPageTemplatePortletUtilMockedStatic.when(
			() ->
				LayoutPageTemplatePortletUtil.fetchLayoutPageTemplateCollection(
					Mockito.any(), Mockito.anyLong())
		).thenReturn(
			layoutPageTemplateCollection
		);

		return layoutPageTemplateCollection;
	}

	private void _setUpLayoutPageTemplateBreadcrumbEntryContributorImpl() {
		ReflectionTestUtil.setFieldValue(
			_layoutPageTemplateBreadcrumbEntryContributorImpl, "_portal",
			_portal);
	}

	private void _setUpLayoutPageTemplatesPortletInDesignLibraryScope() {
		Mockito.when(
			_portletDisplay.getPortletName()
		).thenReturn(
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES
		);

		_designLibraryUtilMockedStatic.when(
			() -> DesignLibraryUtil.isDesignLibraryScope(_group)
		).thenReturn(
			true
		);
	}

	private void _setUpMockHttpServletRequest() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			_portletDisplay
		);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
	}

	private void _setUpPortal() {
		Mockito.when(
			_portal.getControlPanelPortletURL(
				_mockHttpServletRequest, _group,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES, 0L,
				0L, PortletRequest.RENDER_PHASE)
		).thenReturn(
			Mockito.mock(PortletURL.class)
		);
	}

	private void _testGetBreadcrumbEntriesWithLayoutPageTemplateCollection() {
		_setUpLayoutPageTemplatesPortletInDesignLibraryScope();

		BreadcrumbEntry originalBreadcrumbEntry = new BreadcrumbEntry();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_mockLayoutPageTemplateCollection();

		List<BreadcrumbEntry> breadcrumbEntries =
			_layoutPageTemplateBreadcrumbEntryContributorImpl.
				getBreadcrumbEntries(
					Collections.singletonList(originalBreadcrumbEntry),
					_mockHttpServletRequest);

		BreadcrumbEntry layoutPageTemplateCollectionBreadcrumbEntry =
			breadcrumbEntries.get(0);

		Assert.assertEquals(
			layoutPageTemplateCollection.getName(),
			layoutPageTemplateCollectionBreadcrumbEntry.getTitle());

		Assert.assertSame(originalBreadcrumbEntry, breadcrumbEntries.get(1));

		Assert.assertEquals(
			breadcrumbEntries.toString(), 2, breadcrumbEntries.size());
	}

	private void _testGetBreadcrumbEntriesWithoutDesignLibraryScope() {
		Mockito.when(
			_portletDisplay.getPortletName()
		).thenReturn(
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES
		);

		_designLibraryUtilMockedStatic.when(
			() -> DesignLibraryUtil.isDesignLibraryScope(_group)
		).thenReturn(
			false
		);

		List<BreadcrumbEntry> originalBreadcrumbEntries =
			Collections.singletonList(new BreadcrumbEntry());

		Assert.assertSame(
			originalBreadcrumbEntries,
			_layoutPageTemplateBreadcrumbEntryContributorImpl.
				getBreadcrumbEntries(
					originalBreadcrumbEntries, _mockHttpServletRequest));
	}

	private void _testGetBreadcrumbEntriesWithoutLayoutPageTemplatesPortlet() {
		Mockito.when(
			_portletDisplay.getPortletName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_designLibraryUtilMockedStatic.when(
			() -> DesignLibraryUtil.isDesignLibraryScope(_group)
		).thenReturn(
			true
		);

		List<BreadcrumbEntry> originalBreadcrumbEntries =
			Collections.singletonList(new BreadcrumbEntry());

		Assert.assertSame(
			originalBreadcrumbEntries,
			_layoutPageTemplateBreadcrumbEntryContributorImpl.
				getBreadcrumbEntries(
					originalBreadcrumbEntries, _mockHttpServletRequest));
	}

	private final MockedStatic<DesignLibraryUtil>
		_designLibraryUtilMockedStatic = Mockito.mockStatic(
			DesignLibraryUtil.class);
	private final Group _group = Mockito.mock(Group.class);
	private final LayoutPageTemplateBreadcrumbEntryContributorImpl
		_layoutPageTemplateBreadcrumbEntryContributorImpl =
			new LayoutPageTemplateBreadcrumbEntryContributorImpl();
	private final MockedStatic<LayoutPageTemplatePortletUtil>
		_layoutPageTemplatePortletUtilMockedStatic = Mockito.mockStatic(
			LayoutPageTemplatePortletUtil.class);
	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private final Portal _portal = Mockito.mock(Portal.class);
	private final PortletDisplay _portletDisplay = Mockito.mock(
		PortletDisplay.class);

}