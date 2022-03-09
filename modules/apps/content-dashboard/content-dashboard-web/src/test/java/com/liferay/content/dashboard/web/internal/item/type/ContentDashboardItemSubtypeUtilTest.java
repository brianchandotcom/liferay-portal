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

import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.Optional;

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
	public void testToContentDashboardItemSubtypeOptionalByClassNameAndClassPK()
		throws PortalException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			_getContentDashboardItemSubtypeFactory(contentDashboardItemSubtype);

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker =
				_getContentDashboardItemSubtypeFactoryTracker(
					contentDashboardItemSubtype,
					contentDashboardItemSubtypeFactory);

		Optional<ContentDashboardItemSubtype>
			contentDashboardItemSubtypeOptional =
				ContentDashboardItemSubtypeUtil.
					toContentDashboardItemSubtypeOptional(
						contentDashboardItemSubtypeFactoryTracker,
						contentDashboardItemSubtype.getInfoItemReference());

		Assert.assertEquals(
			contentDashboardItemSubtype,
			contentDashboardItemSubtypeOptional.get());
	}

	@Test
	public void testToContentDashboardItemSubtypeOptionalByClassNameAndClassPKWithoutContentDashboardItemSubtypeFactory() {
		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker =
				_getContentDashboardItemSubtypeFactoryTracker(
					contentDashboardItemSubtype, null);

		Optional<ContentDashboardItemSubtype>
			contentDashboardItemSubtypeOptional =
				ContentDashboardItemSubtypeUtil.
					toContentDashboardItemSubtypeOptional(
						contentDashboardItemSubtypeFactoryTracker,
						contentDashboardItemSubtype.getInfoItemReference());

		Assert.assertFalse(contentDashboardItemSubtypeOptional.isPresent());
	}

	@Test
	public void testToContentDashboardItemSubtypeOptionalByDocument()
		throws PortalException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		InfoItemReference infoItemReference =
			contentDashboardItemSubtype.getInfoItemReference();

		Document document = Mockito.mock(Document.class);

		Mockito.when(
			document.get(Field.ENTRY_CLASS_NAME)
		).thenReturn(
			infoItemReference.getClassName()
		);

		Mockito.when(
			document.get(Field.ENTRY_CLASS_PK)
		).thenReturn(
			String.valueOf(infoItemReference.getClassPK())
		);

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			_getContentDashboardItemSubtypeFactory(contentDashboardItemSubtype);

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker =
				_getContentDashboardItemSubtypeFactoryTracker(
					contentDashboardItemSubtype,
					contentDashboardItemSubtypeFactory);

		Optional<ContentDashboardItemSubtype>
			contentDashboardItemSubtypeOptional =
				ContentDashboardItemSubtypeUtil.
					toContentDashboardItemSubtypeOptional(
						contentDashboardItemSubtypeFactoryTracker, document);

		Assert.assertEquals(
			contentDashboardItemSubtype,
			contentDashboardItemSubtypeOptional.get());
	}

	@Test
	public void testToContentDashboardItemSubtypeOptionalByJSONObject()
		throws PortalException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			_getContentDashboardItemSubtypeFactory(contentDashboardItemSubtype);

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker =
				_getContentDashboardItemSubtypeFactoryTracker(
					contentDashboardItemSubtype,
					contentDashboardItemSubtypeFactory);

		Optional<? extends ContentDashboardItemSubtype>
			contentDashboardItemSubtypeOptional =
				ContentDashboardItemSubtypeUtil.
					toContentDashboardItemSubtypeOptional(
						contentDashboardItemSubtypeFactoryTracker,
						JSONFactoryUtil.createJSONObject(
							contentDashboardItemSubtype.toJSONString(
								LocaleUtil.US)));

		Assert.assertEquals(
			contentDashboardItemSubtype,
			contentDashboardItemSubtypeOptional.get());
	}

	@Test
	public void testToContentDashboardItemSubtypeOptionalByJSONObjectWithoutContentDashboardItemSubtypeFactory()
		throws JSONException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker =
				_getContentDashboardItemSubtypeFactoryTracker(
					contentDashboardItemSubtype, null);

		Optional<ContentDashboardItemSubtype>
			contentDashboardItemSubtypeOptional =
				ContentDashboardItemSubtypeUtil.
					toContentDashboardItemSubtypeOptional(
						contentDashboardItemSubtypeFactoryTracker,
						JSONFactoryUtil.createJSONObject(
							contentDashboardItemSubtype.toJSONString(
								LocaleUtil.US)));

		Assert.assertFalse(contentDashboardItemSubtypeOptional.isPresent());
	}

	@Test
	public void testToContentDashboardItemSubtypeOptionalByStringWithoutContentDashboardItemSubtypeFactory()
		throws JSONException {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_getContentDashboardItemSubtype();

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker =
				_getContentDashboardItemSubtypeFactoryTracker(
					contentDashboardItemSubtype, null);

		Optional<ContentDashboardItemSubtype>
			contentDashboardItemSubtypeOptional =
				ContentDashboardItemSubtypeUtil.
					toContentDashboardItemSubtypeOptional(
						contentDashboardItemSubtypeFactoryTracker,
						contentDashboardItemSubtype.toJSONString(
							LocaleUtil.US));

		Assert.assertFalse(contentDashboardItemSubtypeOptional.isPresent());
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
				).toJSONString();
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

		Mockito.when(
			contentDashboardItemSubtypeFactory.create(
				infoItemReference.getClassPK())
		).thenReturn(
			contentDashboardItemSubtype
		);

		return contentDashboardItemSubtypeFactory;
	}

	private ContentDashboardItemSubtypeFactoryTracker
		_getContentDashboardItemSubtypeFactoryTracker(
			ContentDashboardItemSubtype contentDashboardItemSubtype,
			ContentDashboardItemSubtypeFactory
				contentDashboardItemSubtypeFactory) {

		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker = Mockito.mock(
				ContentDashboardItemSubtypeFactoryTracker.class);

		InfoItemReference infoItemReference =
			contentDashboardItemSubtype.getInfoItemReference();

		Mockito.when(
			contentDashboardItemSubtypeFactoryTracker.
				getContentDashboardItemSubtypeFactoryOptional(
					infoItemReference.getClassName())
		).thenReturn(
			Optional.ofNullable(contentDashboardItemSubtypeFactory)
		);

		return contentDashboardItemSubtypeFactoryTracker;
	}

}