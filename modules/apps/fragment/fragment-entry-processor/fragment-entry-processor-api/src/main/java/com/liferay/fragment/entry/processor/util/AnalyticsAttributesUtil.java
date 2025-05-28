/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.util;

import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.entry.processor.helper.InfoItemFieldMapped;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * @author Eudaldo Alonso
 */
public class AnalyticsAttributesUtil {

	public static final String ACTION_DOWNLOAD = "download";

	public static final String ACTION_IMPRESSION = "impression";

	public static final String ACTION_VIEW = "view";

	public static void addAnalyticsAttributes(
		JSONObject editableValueJSONObject, Element element,
		FragmentEntryProcessorContext fragmentEntryProcessorContext,
		FragmentEntryProcessorHelper fragmentEntryProcessorHelper,
		Map<InfoItemReference, InfoItemFieldValues> infoDisplaysFieldValues,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		JSONObject configJSONObject = editableValueJSONObject.getJSONObject(
			"config");

		if ((configJSONObject != null) &&
			StringUtil.equals(
				configJSONObject.getString("fieldId"),
				"FileEntry_downloadURL")) {

			element.attr("data-analytics-asset-action", ACTION_DOWNLOAD);
			element.attr(
				"data-analytics-asset-field",
				configJSONObject.getString("fieldId"));
			element.attr(
				"data-analytics-asset-id",
				configJSONObject.getString("classPK"));

			element.attr(
				"data-analytics-asset-subtype",
				configJSONObject.getString("itemSubtype"));
			element.attr(
				"data-analytics-asset-title",
				configJSONObject.getString("title"));
			element.attr(
				"data-analytics-asset-type",
				configJSONObject.getString("type"));

			return;
		}

		InfoItemFieldMapped infoItemFieldMapped =
			fragmentEntryProcessorHelper.getInfoItemFieldMapped(
				editableValueJSONObject, fragmentEntryProcessorContext);

		if (infoItemFieldMapped == null) {
			if (_isImageTag(element)) {
				JSONObject jsonObject = editableValueJSONObject.getJSONObject(
					String.valueOf(fragmentEntryProcessorContext.getLocale()));

				if (jsonObject == null) {
					jsonObject = editableValueJSONObject;
				}

				element.attr("data-analytics-asset-action", ACTION_VIEW);
				element.attr(
					"data-analytics-asset-field",
					jsonObject.getString("fieldId"));
				element.attr(
					"data-analytics-asset-id", jsonObject.getString("classPK"));
				element.attr(
					"data-analytics-asset-title",
					jsonObject.getString("title"));
				element.attr(
					"data-analytics-asset-type", FileEntry.class.getName());
			}

			return;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemFieldMapped.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return;
		}

		if (_isImageTag(element)) {
			element.attr("data-analytics-asset-action", ACTION_VIEW);
		}
		else {
			element.attr("data-analytics-asset-action", ACTION_IMPRESSION);
		}

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

	private static boolean _isImageTag(Element element) {
		Tag tag = element.tag();

		if (StringUtil.equals(tag.getName(), "img")) {
			return true;
		}

		return false;
	}

}