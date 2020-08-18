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
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class ContentDashboardItemTypeUtilTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testToContentDashboardItemTypeOptionalByClassNameAndClassPK()
		throws PortalException {

		ContentDashboardItemType contentDashboardItemType =
			_getContentDashboardItemType();

		ContentDashboardItemTypeFactory contentDashboardItemTypeFactory =
			_getContentDashboardItemTypeFactory(contentDashboardItemType);

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker =
				_getContentDashboardItemTypeFactoryTracker(
					contentDashboardItemType, contentDashboardItemTypeFactory);

		Optional<ContentDashboardItemType> contentDashboardItemTypeOptional =
			ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
				contentDashboardItemTypeFactoryTracker,
				contentDashboardItemType.getInfoItemReference());

		Assert.assertEquals(
			contentDashboardItemType, contentDashboardItemTypeOptional.get());
	}

	@Test
	public void testToContentDashboardItemTypeOptionalByClassNameAndClassPKWithoutContentDashboardItemTypeFactory() {
		ContentDashboardItemType contentDashboardItemType =
			_getContentDashboardItemType();

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker =
				_getContentDashboardItemTypeFactoryTracker(
					contentDashboardItemType, null);

		Optional<ContentDashboardItemType> contentDashboardItemTypeOptional =
			ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
				contentDashboardItemTypeFactoryTracker,
				contentDashboardItemType.getInfoItemReference());

		Assert.assertFalse(contentDashboardItemTypeOptional.isPresent());
	}

	@Test
	public void testToContentDashboardItemTypeOptionalByDocument()
		throws PortalException {

		ContentDashboardItemType contentDashboardItemType =
			_getContentDashboardItemType();

		InfoItemReference infoItemReference =
			contentDashboardItemType.getInfoItemReference();

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

		ContentDashboardItemTypeFactory contentDashboardItemTypeFactory =
			_getContentDashboardItemTypeFactory(contentDashboardItemType);

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker =
				_getContentDashboardItemTypeFactoryTracker(
					contentDashboardItemType, contentDashboardItemTypeFactory);

		Optional<ContentDashboardItemType> contentDashboardItemTypeOptional =
			ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
				contentDashboardItemTypeFactoryTracker, document);

		Assert.assertEquals(
			contentDashboardItemType, contentDashboardItemTypeOptional.get());
	}

	@Test
	public void testToContentDashboardItemTypeOptionalByJSONObject()
		throws PortalException {

		ContentDashboardItemType contentDashboardItemType =
			_getContentDashboardItemType();

		ContentDashboardItemTypeFactory contentDashboardItemTypeFactory =
			_getContentDashboardItemTypeFactory(contentDashboardItemType);

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker =
				_getContentDashboardItemTypeFactoryTracker(
					contentDashboardItemType, contentDashboardItemTypeFactory);

		Optional<? extends ContentDashboardItemType>
			contentDashboardItemTypeOptional =
				ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
					contentDashboardItemTypeFactoryTracker,
					JSONFactoryUtil.createJSONObject(
						contentDashboardItemType.toJSONString(LocaleUtil.US)));

		Assert.assertEquals(
			contentDashboardItemType, contentDashboardItemTypeOptional.get());
	}

	@Test
	public void testToContentDashboardItemTypeOptionalByJSONObjectWithoutContentDashboardItemTypeFactory()
		throws JSONException {

		ContentDashboardItemType contentDashboardItemType =
			_getContentDashboardItemType();

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker =
				_getContentDashboardItemTypeFactoryTracker(
					contentDashboardItemType, null);

		Optional<ContentDashboardItemType> contentDashboardItemTypeOptional =
			ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
				contentDashboardItemTypeFactoryTracker,
				JSONFactoryUtil.createJSONObject(
					contentDashboardItemType.toJSONString(LocaleUtil.US)));

		Assert.assertFalse(contentDashboardItemTypeOptional.isPresent());
	}

	@Test
	public void testToContentDashboardItemTypeOptionalByStringWithoutContentDashboardItemTypeFactory()
		throws JSONException {

		ContentDashboardItemType contentDashboardItemType =
			_getContentDashboardItemType();

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker =
				_getContentDashboardItemTypeFactoryTracker(
					contentDashboardItemType, null);

		Optional<ContentDashboardItemType> contentDashboardItemTypeOptional =
			ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
				contentDashboardItemTypeFactoryTracker,
				contentDashboardItemType.toJSONString(LocaleUtil.US));

		Assert.assertFalse(contentDashboardItemTypeOptional.isPresent());
	}

	private ContentDashboardItemType _getContentDashboardItemType() {
		String className = RandomTestUtil.randomString();
		Long classPK = RandomTestUtil.randomLong();

		return new ContentDashboardItemType() {

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
			public Date getModifiedDate() {
				return new Date();
			}

			@Override
			public long getUserId() {
				return 0;
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

	private ContentDashboardItemTypeFactory _getContentDashboardItemTypeFactory(
			ContentDashboardItemType contentDashboardItemType)
		throws PortalException {

		ContentDashboardItemTypeFactory contentDashboardItemTypeFactory =
			Mockito.mock(ContentDashboardItemTypeFactory.class);

		InfoItemReference infoItemReference =
			contentDashboardItemType.getInfoItemReference();

		Mockito.when(
			contentDashboardItemTypeFactory.create(
				infoItemReference.getClassPK())
		).thenReturn(
			contentDashboardItemType
		);

		return contentDashboardItemTypeFactory;
	}

	private ContentDashboardItemTypeFactoryTracker
		_getContentDashboardItemTypeFactoryTracker(
			ContentDashboardItemType contentDashboardItemType,
			ContentDashboardItemTypeFactory contentDashboardItemTypeFactory) {

		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker = Mockito.mock(
				ContentDashboardItemTypeFactoryTracker.class);

		InfoItemReference infoItemReference =
			contentDashboardItemType.getInfoItemReference();

		Mockito.when(
			contentDashboardItemTypeFactoryTracker.
				getContentDashboardItemTypeFactoryOptional(
					infoItemReference.getClassName())
		).thenReturn(
			Optional.ofNullable(contentDashboardItemTypeFactory)
		);

		return contentDashboardItemTypeFactoryTracker;
	}

}