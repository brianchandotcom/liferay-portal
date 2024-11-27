/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.util;

import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.entry.processor.helper.InfoItemFieldMapped;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.Map;

import org.jsoup.nodes.Element;

/**
 * @author Eudaldo Alonso
 */
public class AnalyticsAttributesUtil {

	public static final String ACTION_IMPRESSION = "impression";

	public static final String ACTION_VIEW = "view";

	public static void addAnalyticsAttributes(
		JSONObject editableValueJSONObject, Element element,
		FragmentEntryProcessorContext fragmentEntryProcessorContext,
		FragmentEntryProcessorHelper fragmentEntryProcessorHelper,
		Map<InfoItemReference, InfoItemFieldValues> infoDisplaysFieldValues,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		InfoItemFieldMapped infoItemFieldMapped =
			fragmentEntryProcessorHelper.getInfoItemFieldMapped(
				editableValueJSONObject, fragmentEntryProcessorContext);

		if (infoItemFieldMapped == null) {
			return;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemFieldMapped.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return;
		}

		element.attr(
			"data-analytics-asset-action",
			_getAnalyticsAction(infoDisplaysFieldValues, infoItemFieldMapped));
		element.attr(
			"data-analytics-asset-field", infoItemFieldMapped.getFieldName());

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		element.attr(
			"data-analytics-asset-id",
			String.valueOf(classPKInfoItemIdentifier.getClassPK()));

		element.attr(
			"data-analytics-asset-subtype",
			_getAnalyticsSubtype(infoItemFieldMapped, infoItemServiceRegistry));
		element.attr(
			"data-analytics-asset-title",
			_getAnalyticsTitle(
				infoDisplaysFieldValues, infoItemFieldMapped,
				fragmentEntryProcessorContext.getLocale()));
		element.attr(
			"data-analytics-asset-type", infoItemFieldMapped.getClassName());
	}

	private static String _getAnalyticsAction(
		Map<InfoItemReference, InfoItemFieldValues> infoDisplaysFieldValues,
		InfoItemFieldMapped infoItemFieldMapped) {

		InfoItemFieldValues infoItemFieldValues = infoDisplaysFieldValues.get(
			infoItemFieldMapped.getInfoItemReference());

		if (infoItemFieldValues == null) {
			return ACTION_IMPRESSION;
		}

		InfoFieldValue<?> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				infoItemFieldMapped.getFieldName());

		if (infoFieldValue == null) {
			return ACTION_IMPRESSION;
		}

		InfoField<?> infoField = infoFieldValue.getInfoField();

		if (infoField.getInfoFieldType() instanceof HTMLInfoFieldType ||
			infoField.getInfoFieldType() instanceof TextInfoFieldType) {

			return ACTION_VIEW;
		}

		return ACTION_IMPRESSION;
	}

	private static String _getAnalyticsSubtype(
		InfoItemFieldMapped infoItemFieldMapped,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		InfoItemObjectVariationProvider infoItemObjectVariationProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectVariationProvider.class,
				infoItemFieldMapped.getClassName());

		if (infoItemObjectVariationProvider == null) {
			return StringPool.BLANK;
		}

		return infoItemObjectVariationProvider.getInfoItemFormVariationKey(
			infoItemFieldMapped.getObject());
	}

	private static String _getAnalyticsTitle(
		Map<InfoItemReference, InfoItemFieldValues> infoDisplaysFieldValues,
		InfoItemFieldMapped infoItemFieldMapped, Locale locale) {

		InfoItemFieldValues infoItemFieldValues = infoDisplaysFieldValues.get(
			infoItemFieldMapped.getInfoItemReference());

		if (infoItemFieldValues == null) {
			return StringPool.BLANK;
		}

		InfoFieldValue<?> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("title");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		return String.valueOf(infoFieldValue.getValue(locale));
	}

}