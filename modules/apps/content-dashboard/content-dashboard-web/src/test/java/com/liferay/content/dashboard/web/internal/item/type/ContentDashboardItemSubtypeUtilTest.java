/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.content.dashboard.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactoryRegistry;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
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
 * @author Cristina González
 */
public class ContentDashboardItemSubtypeUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToContentDashboardItemSubtypeByClassNameAndClassPK()
		throws PortalException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			_getContentDashboardItemSubtypeFactory(contentDashboardItemSubtype);

		Assert.assertEquals(
			contentDashboardItemSubtype,
			ContentDashboardItemSubtypeUtil.toContentDashboardItemSubtype(
				_getContentDashboardItemSubtypeFactoryRegistry(
					contentDashboardItemSubtype,
					contentDashboardItemSubtypeFactory),
				contentDashboardItemSubtype.getInfoItemReference()));
	}

	@Test
	public void testToContentDashboardItemSubtypeByClassNameAndClassPKWithoutContentDashboardItemSubtypeFactory() {
		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		Assert.assertNull(
			ContentDashboardItemSubtypeUtil.toContentDashboardItemSubtype(
				_getContentDashboardItemSubtypeFactoryRegistry(
					contentDashboardItemSubtype, null),
				contentDashboardItemSubtype.getInfoItemReference()));
	}

	@Test
	public void testToContentDashboardItemSubtypeByJSONObject()
		throws PortalException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			_getContentDashboardItemSubtypeFactory(contentDashboardItemSubtype);

		Assert.assertEquals(
			contentDashboardItemSubtype,
			ContentDashboardItemSubtypeUtil.toContentDashboardItemSubtype(
				_getContentDashboardItemSubtypeFactoryRegistry(
					contentDashboardItemSubtype,
					contentDashboardItemSubtypeFactory),
				JSONFactoryUtil.createJSONObject(
					contentDashboardItemSubtype.toJSONString(LocaleUtil.US))));
	}

	@Test
	public void testToContentDashboardItemSubtypeByJSONObjectWithoutContentDashboardItemSubtypeFactory()
		throws JSONException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		Assert.assertNull(
			ContentDashboardItemSubtypeUtil.toContentDashboardItemSubtype(
				_getContentDashboardItemSubtypeFactoryRegistry(
					contentDashboardItemSubtype, null),
				JSONFactoryUtil.createJSONObject(
					contentDashboardItemSubtype.toJSONString(LocaleUtil.US))));
	}

	@Test
	public void testToContentDashboardItemSubtypeByStringWithoutContentDashboardItemSubtypeFactory() {
		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		Assert.assertNull(
			ContentDashboardItemSubtypeUtil.toContentDashboardItemSubtype(
				_getContentDashboardItemSubtypeFactoryRegistry(
					contentDashboardItemSubtype, null),
				contentDashboardItemSubtype.toJSONString(LocaleUtil.US)));
	}

	private ContentDashboardItemSubtype _getContentDashboardItemSubtype() {
		String className = RandomTestUtil.randomString();
		Long classPK = RandomTestUtil.randomLong();

		return new ContentDashboardItemSubtype() {

			@Override
			public String getFullLabel(Locale locale) {
				return null;
			}

			@Override
			public InfoItemReference getInfoItemReference() {
				return new InfoItemReference(className, classPK);
			}

			@Override
			public String getLabel(Locale locale) {
				return null;
			}

			@Override
			public String toJSONString(Locale locale) {
				return JSONUtil.put(
					"className", className
				).put(
					"classPK", classPK
				).toString();
			}

		};
	}

	private ContentDashboardItemSubtypeFactory
			_getContentDashboardItemSubtypeFactory(
				ContentDashboardItemSubtype contentDashboardItemSubtype)
		throws PortalException {

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			Mockito.mock(ContentDashboardItemSubtypeFactory.class);

		InfoItemReference infoItemReference =
			contentDashboardItemSubtype.getInfoItemReference();

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		Assert.assertTrue(
			infoItemIdentifier instanceof ClassNameClassPKInfoItemIdentifier);

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Mockito.when(
			contentDashboardItemSubtypeFactory.create(
				classNameClassPKInfoItemIdentifier.getClassPK())
		).thenReturn(
			contentDashboardItemSubtype
		);

		return contentDashboardItemSubtypeFactory;
	}

	private ContentDashboardItemSubtypeFactoryRegistry
		_getContentDashboardItemSubtypeFactoryRegistry(
			ContentDashboardItemSubtype contentDashboardItemSubtype,
			ContentDashboardItemSubtypeFactory
				contentDashboardItemSubtypeFactory) {

		ContentDashboardItemSubtypeFactoryRegistry
			contentDashboardItemSubtypeFactoryRegistry = Mockito.mock(
				ContentDashboardItemSubtypeFactoryRegistry.class);

		InfoItemReference infoItemReference =
			contentDashboardItemSubtype.getInfoItemReference();

		Mockito.when(
			contentDashboardItemSubtypeFactoryRegistry.
				getContentDashboardItemSubtypeFactory(
					infoItemReference.getClassName())
		).thenReturn(
			contentDashboardItemSubtypeFactory
		);

		return contentDashboardItemSubtypeFactoryRegistry;
	}

}