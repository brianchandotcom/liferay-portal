/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.exportimport.content.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alicia García
 */
public class LayoutImportDDMFormFieldValueTransformerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONWhenNoSuchLayoutExceptionIsThrown()
		throws PortalException {

		Mockito.when(
			_layout.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_layout.getUuid()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_layout.getLayoutId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_layout.isPrivateLayout()
		).thenReturn(
			false
		);

		Locale locale = LocaleUtil.US;

		Mockito.when(
			_layout.getFriendlyURL(locale)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_layout.getBreadcrumb(locale)
		).thenThrow(
			new NoSuchLayoutException()
		);

		String name = RandomTestUtil.randomString();

		MockDDMFormValuesExportImportContentProcessor
			mockDDMFormValuesExportImportContentProcessor =
				new MockDDMFormValuesExportImportContentProcessor();

		DDMFormValuesExportImportContentProcessor.
			LayoutImportDDMFormFieldValueTransformer
				layoutImportDDMFormFieldValueTransformer =
					mockDDMFormValuesExportImportContentProcessor.
						getLayoutImportDDMFormFieldValueTransformer(
							_portletDataContext);

		Assert.assertEquals(
			JSONUtil.put(
				"groupId", _layout.getGroupId()
			).put(
				"id", _layout.getUuid()
			).put(
				"layoutId", _layout.getLayoutId()
			).put(
				"name", name
			).put(
				"privateLayout", _layout.isPrivateLayout()
			).put(
				"value", _layout.getFriendlyURL(locale)
			).toString(),
			layoutImportDDMFormFieldValueTransformer.toJSON(
				_layout, locale, name));
	}

	private final Layout _layout = Mockito.mock(Layout.class);
	private final PortletDataContext _portletDataContext = Mockito.mock(
		PortletDataContext.class);

	private static class MockDDMFormValuesExportImportContentProcessor
		extends DDMFormValuesExportImportContentProcessor {

		public LayoutImportDDMFormFieldValueTransformer
			getLayoutImportDDMFormFieldValueTransformer(
				PortletDataContext portletDataContext) {

			return new LayoutImportDDMFormFieldValueTransformer(
				portletDataContext);
		}

	}

}