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

package com.liferay.fragment.entry.processor.background.image;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=5",
	service = FragmentEntryProcessor.class
)
public class BackgroundImageFragmentEntryProcessor
	implements FragmentEntryProcessor {

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		JSONObject defaultEditableValuesJSONObject =
			_jsonFactory.createJSONObject();

		Document document = _getDocument(html);

		for (Element element :
				document.select("[data-lfr-background-image-id]")) {

			String id = element.attr("data-lfr-background-image-id");

			defaultEditableValuesJSONObject.put(
				id, _jsonFactory.createJSONObject());
		}

		return defaultEditableValuesJSONObject;
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Document document = _getDocument(html);

		Map<Long, InfoItemFieldValues> infoDisplaysFieldValues =
			new HashMap<>();

		for (Element element :
				document.select("[data-lfr-background-image-id]")) {

			String id = element.attr("data-lfr-background-image-id");

			Class<?> clazz = getClass();

			JSONObject editableValuesJSONObject = jsonObject.getJSONObject(
				clazz.getName());

			if ((editableValuesJSONObject == null) ||
				!editableValuesJSONObject.has(id)) {

				continue;
			}

			JSONObject editableValueJSONObject =
				editableValuesJSONObject.getJSONObject(id);

			String value = StringPool.BLANK;

			Object fieldValue = _getFieldValue(
				editableValueJSONObject, infoDisplaysFieldValues,
				fragmentEntryProcessorContext);

			if (fieldValue != null) {
				value = _getImageURL(fieldValue);
			}

			if (Validator.isNull(value)) {
				value = _fragmentEntryProcessorHelper.getEditableValue(
					editableValueJSONObject,
					fragmentEntryProcessorContext.getLocale());
			}

			if (Validator.isNotNull(value)) {
				long fileEntryId = 0;

				if (JSONUtil.isValid(value)) {
					JSONObject valueJSONObject = _jsonFactory.createJSONObject(
						value);

					fileEntryId = valueJSONObject.getLong("fileEntryId");

					if (fileEntryId == 0) {
						fileEntryId =
							_fragmentEntryProcessorHelper.getFileEntryId(
								valueJSONObject.getString("className"),
								valueJSONObject.getLong("classPK"));
					}

					value = valueJSONObject.getString("url", value);
				}

				StringBundler sb = new StringBundler(6);

				sb.append("background-image: url(");
				sb.append(value);
				sb.append("); background-size: cover;");

				if (fileEntryId == 0) {
					fileEntryId = _fragmentEntryProcessorHelper.getFileEntryId(
						editableValueJSONObject.getLong("classNameId"),
						editableValueJSONObject.getLong("classPK"),
						editableValueJSONObject.getString("fieldId"),
						fragmentEntryProcessorContext.getLocale());
				}

				Optional<InfoItemReference> contextInfoItemReferenceOptional =
					fragmentEntryProcessorContext.
						getContextInfoItemReferenceOptional();

				if ((fileEntryId == 0) &&
					contextInfoItemReferenceOptional.isPresent()) {

					fileEntryId = _fragmentEntryProcessorHelper.getFileEntryId(
						contextInfoItemReferenceOptional.get(),
						editableValueJSONObject.getString("collectionFieldId"),
						fragmentEntryProcessorContext.getLocale());
				}

				if ((fileEntryId == 0) &&
					contextInfoItemReferenceOptional.isPresent()) {

					fileEntryId = _fragmentEntryProcessorHelper.getFileEntryId(
						contextInfoItemReferenceOptional.get(),
						editableValueJSONObject.getString("mappedField"),
						fragmentEntryProcessorContext.getLocale());
				}

				if (fileEntryId > 0) {
					sb.append(" --background-image-file-entry-id: ");
					sb.append(fileEntryId);
					sb.append(StringPool.SEMICOLON);
				}

				element.attr("style", sb.toString());
			}
		}

		if (Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.VIEW)) {

			for (Element element :
					document.select("[data-lfr-background-image-id]")) {

				element.removeAttr("data-lfr-background-image-id");
			}
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-background-image-id]");

		Set<String> ids = new HashSet<>();

		for (Element element : elements) {
			if (ids.add(element.attr("data-lfr-background-image-id"))) {
				continue;
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-background-image-" +
						"element"));
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private Object _getFieldValue(
			JSONObject editableValueJSONObject,
			Map<Long, InfoItemFieldValues> infoDisplaysFieldValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (_fragmentEntryProcessorHelper.isAssetDisplayPage(
				fragmentEntryProcessorContext.getMode())) {

			HttpServletRequest httpServletRequest =
				fragmentEntryProcessorContext.getHttpServletRequest();

			if (httpServletRequest == null) {
				return null;
			}

			String mappedField = editableValueJSONObject.getString(
				"mappedField");

			Object infoItem = httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM);

			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				(InfoItemFieldValuesProvider)httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER);

			return _fragmentEntryProcessorHelper.getMappedInfoItemFieldValue(
				mappedField, infoItemFieldValuesProvider,
				fragmentEntryProcessorContext.getLocale(), infoItem);
		}
		else if (_fragmentEntryProcessorHelper.isMapped(
					editableValueJSONObject)) {

			return _fragmentEntryProcessorHelper.getMappedInfoItemFieldValue(
				editableValueJSONObject, infoDisplaysFieldValues,
				fragmentEntryProcessorContext.getLocale(),
				fragmentEntryProcessorContext.getMode(),
				fragmentEntryProcessorContext.getPreviewClassPK(),
				fragmentEntryProcessorContext.getPreviewVersion());
		}
		else if (_fragmentEntryProcessorHelper.isMappedCollection(
					editableValueJSONObject)) {

			return _fragmentEntryProcessorHelper.getMappedCollectionValue(
				fragmentEntryProcessorContext.
					getContextInfoItemReferenceOptional(),
				editableValueJSONObject,
				fragmentEntryProcessorContext.getLocale());
		}

		return null;
	}

	private String _getImageURL(Object fieldValue) {
		if (fieldValue instanceof JSONObject) {
			JSONObject fieldValueJSONObject = (JSONObject)fieldValue;

			return fieldValueJSONObject.getString("url");
		}

		if (fieldValue instanceof WebImage) {
			WebImage webImage = (WebImage)fieldValue;

			return String.valueOf(webImage.toJSONObject());
		}

		return String.valueOf(fieldValue);
	}

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}