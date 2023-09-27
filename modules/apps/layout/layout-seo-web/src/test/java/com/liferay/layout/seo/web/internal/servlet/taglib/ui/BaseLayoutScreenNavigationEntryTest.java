/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.web.internal.servlet.taglib.ui;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.seo.web.internal.frontend.taglib.servlet.taglib.BaseLayoutScreenNavigationEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class BaseLayoutScreenNavigationEntryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsVisibleLayoutPageTemplate() {
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService = Mockito.mock(
				LayoutPageTemplateEntryLocalService.class);

		Mockito.when(
			layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(LayoutPageTemplateEntry.class)
		);

		TestLayoutScreenNavigationEntry testLayoutScreenNavigationEntry =
			new TestLayoutScreenNavigationEntry(
				layoutPageTemplateEntryLocalService);

		Assert.assertFalse(
			testLayoutScreenNavigationEntry.isVisible(
				Mockito.mock(User.class), _getLayout(null)));
	}

	@Test
	public void testIsVisibleLayoutPrototype() {
		TestLayoutScreenNavigationEntry testLayoutScreenNavigationEntry =
			new TestLayoutScreenNavigationEntry();

		Assert.assertFalse(
			testLayoutScreenNavigationEntry.isVisible(
				Mockito.mock(User.class), _getLayout(LayoutPrototype.class)));
	}

	@Test
	public void testIsVisibleLayoutSetPrototype() {
		TestLayoutScreenNavigationEntry testLayoutScreenNavigationEntry =
			new TestLayoutScreenNavigationEntry();

		Assert.assertFalse(
			testLayoutScreenNavigationEntry.isVisible(
				Mockito.mock(User.class),
				_getLayout(LayoutSetPrototype.class)));
	}

	private Layout _getLayout(Class<?> clazz) {
		Group group = Mockito.mock(Group.class);

		if (LayoutSetPrototype.class.equals(clazz)) {
			Mockito.when(
				group.isLayoutSetPrototype()
			).thenReturn(
				true
			);
		}
		else if (LayoutPrototype.class.equals(clazz)) {
			Mockito.when(
				group.isLayoutPrototype()
			).thenReturn(
				true
			);
		}

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getGroup()
		).thenReturn(
			group
		);

		return layout;
	}

	private static class TestLayoutScreenNavigationEntry
		extends BaseLayoutScreenNavigationEntry {

		public TestLayoutScreenNavigationEntry() {
			this(null);
		}

		public TestLayoutScreenNavigationEntry(
			LayoutPageTemplateEntryLocalService
				layoutPageTemplateEntryLocalService) {

			this.layoutPageTemplateEntryLocalService =
				layoutPageTemplateEntryLocalService;
		}

		@Override
		public String getEntryKey() {
			return StringUtil.randomString();
		}

		@Override
		protected String getJspPath() {
			return StringUtil.randomString();
		}

	}

}