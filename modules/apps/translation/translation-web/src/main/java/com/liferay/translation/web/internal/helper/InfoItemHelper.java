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

package com.liferay.translation.web.internal.helper;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class InfoItemHelper {

	public InfoItemHelper(
		String className, InfoItemObjectProvider<Object> infoItemObjectProvider,
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider) {

		_className = className;
		_infoItemObjectProvider = infoItemObjectProvider;
		_infoItemFieldValuesProvider = infoItemFieldValuesProvider;
	}

	public Optional<String> getInfoItemTitleOptional(
		long classPK, Locale locale) {

		try {
			ObjectValuePair<InfoItemReference, String>
				infoItemReferenceSuffixObjectValuePair =
					_getInfoItemReferenceSuffixObjectValuePair(
						_className, classPK, locale);

			InfoItemReference infoItemReference =
				infoItemReferenceSuffixObjectValuePair.getKey();

			Object object = _infoItemObjectProvider.getInfoItem(
				infoItemReference.getClassPK());

			return _getTitleOptional(
				object, locale
			).map(
				title ->
					title + infoItemReferenceSuffixObjectValuePair.getValue()
			);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return Optional.empty();
		}
	}

	private ObjectValuePair<InfoItemReference, String>
		_getInfoItemReferenceSuffixObjectValuePair(
			String className, long classPK, Locale locale) {

		if (!Objects.equals(className, SegmentsExperience.class.getName())) {
			return new ObjectValuePair<>(
				new InfoItemReference(className, classPK), StringPool.BLANK);
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(classPK);

		if (segmentsExperience == null) {
			return new ObjectValuePair<>(
				new InfoItemReference(className, classPK), StringPool.BLANK);
		}

		return new ObjectValuePair<>(
			new InfoItemReference(
				segmentsExperience.getClassName(),
				segmentsExperience.getClassPK()),
			StringBundler.concat(
				StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
				segmentsExperience.getName(locale),
				StringPool.CLOSE_PARENTHESIS));
	}

	private Optional<String> _getTitleOptional(Object object, Locale locale) {
		InfoFieldValue<Object> titleInfoFieldValue =
			_infoItemFieldValuesProvider.getInfoFieldValue(object, "title");

		if (titleInfoFieldValue != null) {
			return Optional.ofNullable(
				(String)titleInfoFieldValue.getValue(locale));
		}

		InfoFieldValue<Object> nameInfoFieldValue =
			_infoItemFieldValuesProvider.getInfoFieldValue(object, "name");

		return Optional.ofNullable((String)nameInfoFieldValue.getValue(locale));
	}

	private static final Log _log = LogFactoryUtil.getLog(InfoItemHelper.class);

	private final String _className;
	private final InfoItemFieldValuesProvider<Object>
		_infoItemFieldValuesProvider;
	private final InfoItemObjectProvider<Object> _infoItemObjectProvider;

}