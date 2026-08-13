/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.search.similarity;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.bag.ObjectFieldBag;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HtmlParser;

import java.io.Serializable;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class CMSContentTextSimilarityTextExtractor {

	public CMSContentTextSimilarityTextExtractor(HtmlParser htmlParser) {
		_htmlParser = htmlParser;
	}

	public Set<String> getLanguageIds(ObjectEntry objectEntry)
		throws Exception {

		Set<String> languageIds = new LinkedHashSet<>();

		languageIds.add(objectEntry.getDefaultLanguageId());

		Map<String, Serializable> indexedValues =
			objectEntry.getIndexedValues();

		ObjectDefinition objectDefinition = objectEntry.getObjectDefinition();

		for (ObjectField objectField :
				_getSignatureObjectFields(objectDefinition)) {

			if (!objectField.isLocalized()) {
				continue;
			}

			Object localizedValues = indexedValues.get(
				objectField.getI18nObjectFieldName());

			if (!(localizedValues instanceof Map)) {
				continue;
			}

			Map<?, ?> localizedValuesMap = (Map<?, ?>)localizedValues;

			for (Object languageId : localizedValuesMap.keySet()) {
				languageIds.add(String.valueOf(languageId));
			}
		}

		return languageIds;
	}

	public String getText(String languageId, ObjectEntry objectEntry)
		throws Exception {

		Map<String, Serializable> indexedValues =
			objectEntry.getIndexedValues();

		ObjectDefinition objectDefinition = objectEntry.getObjectDefinition();

		StringBundler sb = new StringBundler();

		for (ObjectField objectField :
				_getSignatureObjectFields(objectDefinition)) {

			Object indexedValue = null;

			if (objectField.isLocalized()) {
				Object localizedValues = indexedValues.get(
					objectField.getI18nObjectFieldName());

				if (localizedValues instanceof Map) {
					Map<?, ?> localizedValuesMap = (Map<?, ?>)localizedValues;

					indexedValue = localizedValuesMap.get(languageId);
				}
			}
			else {
				indexedValue = indexedValues.get(objectField.getName());
			}

			if (indexedValue == null) {
				continue;
			}

			String indexedValueString = String.valueOf(indexedValue);

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

				indexedValueString = _htmlParser.extractText(
					indexedValueString);
			}

			sb.append(indexedValueString);
			sb.append(CharPool.SPACE);
		}

		return sb.toString();
	}

	private Iterable<ObjectField> _getSignatureObjectFields(
		ObjectDefinition objectDefinition) {

		ObjectFieldBag objectFieldBag = objectDefinition.getObjectFieldBag();

		Set<ObjectField> objectFields = new LinkedHashSet<>();

		for (ObjectField objectField :
				objectFieldBag.getIndexedObjectFields()) {

			if (_isTitleObjectField(objectDefinition, objectField)) {
				continue;
			}

			if (_isTextObjectField(objectField)) {
				objectFields.add(objectField);
			}
		}

		return objectFields;
	}

	private boolean _isTextObjectField(ObjectField objectField) {
		String businessType = objectField.getBusinessType();

		if (businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_TEXT) ||
			businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT) ||
			businessType.equals(ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

			return true;
		}

		return false;
	}

	private boolean _isTitleObjectField(
		ObjectDefinition objectDefinition, ObjectField objectField) {

		if (objectField.getObjectFieldId() ==
				objectDefinition.getTitleObjectFieldId()) {

			return true;
		}

		return false;
	}

	private final HtmlParser _htmlParser;

}