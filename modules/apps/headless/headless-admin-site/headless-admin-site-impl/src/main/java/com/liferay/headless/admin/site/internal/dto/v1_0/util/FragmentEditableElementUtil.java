/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.admin.site.dto.v1_0.BackgroundImageFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.DirectFragmentImageValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentEditableElement;
import com.liferay.headless.admin.site.dto.v1_0.FragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentEditableElementValueFragmentLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentImage;
import com.liferay.headless.admin.site.dto.v1_0.FragmentImageValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentImageViewport;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkTextValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLFragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLFragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.ImageFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.ImageValue;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.ItemImageValue;
import com.liferay.headless.admin.site.dto.v1_0.LinkFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.MappedFragmentImageValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.URLImageValue;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * @author Rubén Pulido
 */
public class FragmentEditableElementUtil {

	public static JSONObject getBackgroundImageFragmentEntryProcessorJSONObject(
		FragmentEditableElement[] fragmentEditableElements,
		LayoutStructureItemImporterContext layoutStructureItemImporterContext) {

		JSONObject backgroundImageFragmentEntryProcessorJSONObject =
			_getBackgroundImageFragmentEntryProcessorJSONObject(
				fragmentEditableElements, layoutStructureItemImporterContext);

		if (backgroundImageFragmentEntryProcessorJSONObject.length() > 0) {
			return backgroundImageFragmentEntryProcessorJSONObject;
		}

		return null;
	}

	public static JSONObject getEditableFragmentEntryProcessorJSONObject(
		FragmentEditableElement[] fragmentEditableElements,
		LayoutStructureItemImporterContext layoutStructureItemImporterContext) {

		JSONObject editableFragmentEntryProcessorJSONObject =
			_getEditableFragmentEntryProcessorJSONObject(
				fragmentEditableElements, layoutStructureItemImporterContext);

		if (editableFragmentEntryProcessorJSONObject.length() > 0) {
			return editableFragmentEntryProcessorJSONObject;
		}

		return null;
	}

	public static FragmentEditableElement[] getFragmentEditableElements(
			long companyId, FragmentEntryLink fragmentEntryLink,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws JSONException {

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		if (editableValuesJSONObject == null) {
			return null;
		}

		JSONObject backgroundImageFragmentEntryProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR);
		JSONObject editableFragmentEntryProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if ((backgroundImageFragmentEntryProcessorJSONObject == null) &&
			(editableFragmentEntryProcessorJSONObject == null)) {

			return new FragmentEditableElement[0];
		}

		return _getFragmentEditableElements(
			companyId,
			EditableFragmentEntryProcessorUtil.getEditableTypes(
				fragmentEntryLink.getHtml()),
			infoItemServiceRegistry,
			JSONUtil.merge(
				backgroundImageFragmentEntryProcessorJSONObject,
				editableFragmentEntryProcessorJSONObject),
			scopeGroupId);
	}

	private static JSONObject
		_getBackgroundImageFragmentEntryProcessorJSONObject(
			FragmentEditableElement[] fragmentEditableElements,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentEditableElements == null) {
			return jsonObject;
		}

		for (FragmentEditableElement fragmentEditableElement :
				fragmentEditableElements) {

			if ((fragmentEditableElement == null) ||
				(fragmentEditableElement.getId() == null)) {

				continue;
			}

			FragmentEditableElementValue fragmentEditableElementValue =
				fragmentEditableElement.getFragmentEditableElementValue();

			if ((fragmentEditableElementValue == null) ||
				!Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.BACKGROUND_IMAGE)) {

				continue;
			}

			jsonObject.put(
				fragmentEditableElement.getId(),
				() -> _getJSONObject(
					() -> {
						BackgroundImageFragmentEditableElementValue
							backgroundImageFragmentEditableElementValue =
								(BackgroundImageFragmentEditableElementValue)
									fragmentEditableElement.
										getFragmentEditableElementValue();

						return _toFragmentImageValueJSONObject(
							backgroundImageFragmentEditableElementValue.
								getBackgroundFragmentImageValue(),
							layoutStructureItemImporterContext);
					}));
		}

		return jsonObject;
	}

	private static JSONObject _getEditableFragmentEntryProcessorJSONObject(
		FragmentEditableElement[] fragmentEditableElements,
		LayoutStructureItemImporterContext layoutStructureItemImporterContext) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentEditableElements == null) {
			return jsonObject;
		}

		for (FragmentEditableElement fragmentEditableElement :
				fragmentEditableElements) {

			if ((fragmentEditableElement == null) ||
				(fragmentEditableElement.getId() == null)) {

				continue;
			}

			FragmentEditableElementValue fragmentEditableElementValue =
				fragmentEditableElement.getFragmentEditableElementValue();

			if (fragmentEditableElementValue == null) {
				continue;
			}

			if (Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.HTML) ||
				Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.RICH_TEXT)) {

				jsonObject.put(
					fragmentEditableElement.getId(),
					() -> _getJSONObject(
						() -> _getHTMLFragmentEditableElementJSONObject(
							layoutStructureItemImporterContext.getCompanyId(),
							(HTMLFragmentEditableElementValue)
								fragmentEditableElementValue,
							layoutStructureItemImporterContext.
								getInfoItemServiceRegistry(),
							layoutStructureItemImporterContext.getGroupId())));

				continue;
			}

			if (Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.IMAGE)) {

				jsonObject.put(
					fragmentEditableElement.getId(),
					() -> _getJSONObject(
						() -> _getImageFragmentEditableElementJSONObject(
							(ImageFragmentEditableElementValue)
								fragmentEditableElementValue,
							layoutStructureItemImporterContext)));

				continue;
			}

			if (Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.LINK)) {

				LinkFragmentEditableElementValue
					linkFragmentEditableElementValue =
						(LinkFragmentEditableElementValue)
							fragmentEditableElementValue;

				jsonObject.put(
					fragmentEditableElement.getId(),
					() -> _getJSONObject(
						() -> _getFragmentLinkTextJSONObject(
							layoutStructureItemImporterContext.getCompanyId(),
							linkFragmentEditableElementValue.
								getFragmentLinkTextValue(),
							layoutStructureItemImporterContext.
								getInfoItemServiceRegistry(),
							null,
							layoutStructureItemImporterContext.getGroupId())));
			}

			if (Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.TEXT)) {

				TextFragmentEditableElementValue
					textFragmentEditableElementValue =
						(TextFragmentEditableElementValue)
							fragmentEditableElementValue;

				jsonObject.put(
					fragmentEditableElement.getId(),
					() -> _getJSONObject(
						() -> _getFragmentLinkTextJSONObject(
							layoutStructureItemImporterContext.getCompanyId(),
							textFragmentEditableElementValue.
								getFragmentLinkTextValue(),
							layoutStructureItemImporterContext.
								getInfoItemServiceRegistry(),
							"link",
							layoutStructureItemImporterContext.getGroupId())));
			}
		}

		return jsonObject;
	}

	private static FragmentEditableElement[] _getFragmentEditableElements(
		long companyId, Map<String, String> editableTypes,
		InfoItemServiceRegistry infoItemServiceRegistry, JSONObject jsonObject,
		long scopeGroupId) {

		return TransformUtil.transformToArray(
			new TreeSet<>(jsonObject.keySet()),
			fieldId -> {
				FragmentEditableElementValue fragmentEditableElementValue =
					_getFragmentEditableElementValue(
						companyId, infoItemServiceRegistry,
						jsonObject.getJSONObject(fieldId), scopeGroupId,
						editableTypes.getOrDefault(fieldId, "text"));

				if (fragmentEditableElementValue == null) {
					return null;
				}

				FragmentEditableElement fragmentEditableElement =
					new FragmentEditableElement();

				fragmentEditableElement.setFragmentEditableElementValue(
					() -> fragmentEditableElementValue);
				fragmentEditableElement.setId(() -> fieldId);

				return fragmentEditableElement;
			},
			FragmentEditableElement.class);
	}

	private static FragmentEditableElementValue
			_getFragmentEditableElementValue(
				long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
				JSONObject jsonObject, long scopeGroupId, String type)
		throws Exception {

		if (Objects.equals(type, "background-image")) {
			return _toBackgroundImageFragmentEditableElementValue(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		if (Objects.equals(type, "html")) {
			return _toHTMLFragmentEditableElementValue(
				companyId, FragmentEditableElementValue.Type.HTML,
				infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		if (Objects.equals(type, "image")) {
			return _toImageFragmentEditableElementValue(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		if (Objects.equals(type, "link")) {
			return _toLinkFragmentEditableElementValue(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		if (Objects.equals(type, "rich-text")) {
			return _toHTMLFragmentEditableElementValue(
				companyId, FragmentEditableElementValue.Type.RICH_TEXT,
				infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		if (Objects.equals(type, "text")) {
			return _toTextFragmentEditableElementValue(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		return null;
	}

	private static FragmentInlineValue _getFragmentInlineValue(
		JSONObject jsonObject) {

		Map<String, String> i18nMap = LocalizedMapUtil.getI18nMap(
			true,
			LocalizedMapUtil.populateLocalizedMap(
				JSONUtil.toStringMap(jsonObject)));

		if (MapUtil.isEmpty(i18nMap)) {
			return null;
		}

		FragmentInlineValue fragmentInlineValue = new FragmentInlineValue();

		fragmentInlineValue.setValue_i18n(() -> i18nMap);

		return fragmentInlineValue;
	}

	private static JSONObject _getFragmentInlineValueJSONObject(
		FragmentInlineValue fragmentInlineValue) {

		if (fragmentInlineValue == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, String> languageIdMap = LocalizedMapUtil.getLanguageIdMap(
			LocalizedMapUtil.getLocalizedMap(
				fragmentInlineValue.getValue_i18n()));

		for (Map.Entry<String, String> entry : languageIdMap.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	private static JSONObject _getFragmentLinkTextJSONObject(
			long companyId, FragmentLinkTextValue fragmentLinkTextValue,
			InfoItemServiceRegistry infoItemServiceRegistry, String mapperType,
			long scopeGroupId)
		throws Exception {

		JSONObject jsonObject = _toConfigJSONObject(
			companyId,
			fragmentLinkTextValue.getFragmentEditableElementValueFragmentLink(),
			infoItemServiceRegistry, mapperType, scopeGroupId);

		TextFragmentValue textFragmentValue =
			fragmentLinkTextValue.getTextFragmentValue();

		if (textFragmentValue == null) {
			return jsonObject;
		}

		if (textFragmentValue instanceof TextFragmentInlineValue) {
			TextFragmentInlineValue textFragmentInlineValue =
				(TextFragmentInlineValue)textFragmentValue;

			return JSONUtil.merge(
				_getFragmentInlineValueJSONObject(
					textFragmentInlineValue.getFragmentInlineValue()),
				jsonObject);
		}

		if (!(textFragmentValue instanceof TextFragmentMappedValue)) {
			return jsonObject;
		}

		TextFragmentMappedValue textFragmentMappedValue =
			(TextFragmentMappedValue)textFragmentValue;

		return JSONUtil.merge(
			_getFragmentMappedValueJSONObject(
				companyId, textFragmentMappedValue.getFragmentMappedValue(),
				infoItemServiceRegistry, scopeGroupId),
			jsonObject);
	}

	private static JSONObject _getFragmentMappedValueJSONObject(
			long companyId, FragmentMappedValue fragmentMappedValue,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws Exception {

		if (fragmentMappedValue == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return FragmentMappingUtil.getFragmentMappedValueJSONObject(
			companyId, infoItemServiceRegistry,
			fragmentMappedValue.getMapping(), scopeGroupId);
	}

	private static JSONObject _getHTMLFragmentEditableElementJSONObject(
			long companyId,
			HTMLFragmentEditableElementValue htmlFragmentEditableElementValue,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws Exception {

		HTMLFragmentValue htmlFragmentValue =
			htmlFragmentEditableElementValue.getHtmlFragmentValue();

		if (htmlFragmentValue == null) {
			return null;
		}

		if (htmlFragmentValue instanceof HTMLFragmentInlineValue) {
			HTMLFragmentInlineValue htmlFragmentInlineValue =
				(HTMLFragmentInlineValue)htmlFragmentValue;

			return _getFragmentInlineValueJSONObject(
				htmlFragmentInlineValue.getFragmentInlineValue());
		}

		if (!(htmlFragmentValue instanceof HTMLFragmentMappedValue)) {
			return null;
		}

		HTMLFragmentMappedValue htmlFragmentMappedValue =
			(HTMLFragmentMappedValue)htmlFragmentValue;

		return _getFragmentMappedValueJSONObject(
			companyId, htmlFragmentMappedValue.getFragmentMappedValue(),
			infoItemServiceRegistry, scopeGroupId);
	}

	private static JSONObject _getImageConfigurationJSONObject(
		FragmentImage fragmentImage) {

		if (ArrayUtil.isEmpty(fragmentImage.getFragmentImageViewports())) {
			return null;
		}

		JSONObject imageConfigurationJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (FragmentImageViewport fragmentImageViewport :
				fragmentImage.getFragmentImageViewports()) {

			imageConfigurationJSONObject.put(
				ViewportIdUtil.toInternalValue(
					fragmentImageViewport.getIdAsString()),
				fragmentImageViewport.getResolution());
		}

		if (JSONUtil.isEmpty(imageConfigurationJSONObject)) {
			return null;
		}

		return imageConfigurationJSONObject;
	}

	private static JSONObject _getImageFragmentEditableElementJSONObject(
			ImageFragmentEditableElementValue imageFragmentEditableElementValue,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		JSONObject jsonObject = _toConfigJSONObject(
			layoutStructureItemImporterContext.getCompanyId(),
			imageFragmentEditableElementValue.
				getFragmentEditableElementValueFragmentLink(),
			layoutStructureItemImporterContext.getInfoItemServiceRegistry(),
			"link", layoutStructureItemImporterContext.getGroupId());

		FragmentImage fragmentImage =
			imageFragmentEditableElementValue.getFragmentImage();

		if (fragmentImage == null) {
			return jsonObject;
		}

		return JSONUtil.merge(
			JSONUtil.put(
				"config",
				() -> _getJSONObject(
					() -> JSONUtil.merge(
						jsonObject.getJSONObject("config"),
						JSONUtil.put(
							"alt",
							() -> LocalizedValueUtil.toJSONObject(
								fragmentImage.getDescription_i18n())
						).put(
							"imageConfiguration",
							() -> _getImageConfigurationJSONObject(
								fragmentImage)
						).put(
							"lazyLoading",
							() -> {
								if (fragmentImage.getLazyLoading() == null) {
									return null;
								}

								return GetterUtil.getBoolean(
									fragmentImage.getLazyLoading());
							}
						)))),
			_toFragmentImageValueJSONObject(
				fragmentImage.getFragmentImageValue(),
				layoutStructureItemImporterContext));
	}

	private static ImageValue _getImageValue(
			long companyId, Object value, long scopeGroupId)
		throws Exception {

		if (!(value instanceof JSONObject)) {
			return _getUrlImageValue(GetterUtil.getString(value));
		}

		JSONObject jsonObject = (JSONObject)value;

		if (JSONUtil.isEmpty(jsonObject)) {
			return null;
		}

		if (FileEntryUtil.isItemImageValue(jsonObject)) {
			ItemExternalReference itemExternalReference =
				FileEntryUtil.getFileEntryItemExternalReference(
					companyId, jsonObject, scopeGroupId);

			if (itemExternalReference == null) {
				return null;
			}

			ItemImageValue itemImageValue = new ItemImageValue();

			itemImageValue.setItemExternalReference(
				() -> itemExternalReference);
			itemImageValue.setType(ImageValue.Type.ITEM);

			return itemImageValue;
		}

		return _getUrlImageValue(jsonObject.getString("url"));
	}

	private static JSONObject _getImageValueJSONObject(
			ImageValue imageValue,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws PortalException {

		if (imageValue == null) {
			return null;
		}

		if (Objects.equals(imageValue.getType(), ImageValue.Type.ITEM)) {
			ItemImageValue itemImageValue = (ItemImageValue)imageValue;

			ItemExternalReference itemExternalReference =
				itemImageValue.getItemExternalReference();

			if ((itemExternalReference == null) ||
				Validator.isNull(
					itemExternalReference.getExternalReferenceCode())) {

				return null;
			}

			return FileEntryUtil.getFileEntryJSONObject(
				layoutStructureItemImporterContext.getCompanyId(),
				itemExternalReference.getExternalReferenceCode(),
				itemExternalReference.getScope(),
				layoutStructureItemImporterContext.getGroupId());
		}

		URLImageValue urlImageValue = (URLImageValue)imageValue;

		if (Validator.isNull(urlImageValue.getUrl())) {
			return null;
		}

		return JSONUtil.put("url", urlImageValue.getUrl());
	}

	private static JSONObject _getJSONObject(
			UnsafeSupplier<JSONObject, Exception> unsafeSupplier)
		throws Exception {

		JSONObject jsonObject = unsafeSupplier.get();

		if (JSONUtil.isEmpty(jsonObject)) {
			return null;
		}

		return jsonObject;
	}

	private static URLImageValue _getUrlImageValue(String url) {
		if (Validator.isNull(url)) {
			return null;
		}

		URLImageValue urlImageValue = new URLImageValue();

		urlImageValue.setType(URLImageValue.Type.URL);
		urlImageValue.setUrl(() -> url);

		return urlImageValue;
	}

	private static FragmentEditableElementValue
			_toBackgroundImageFragmentEditableElementValue(
				long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
				JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		if (jsonObject == null) {
			return null;
		}

		FragmentImageValue backgroundFragmentImageValue = _toFragmentImageValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (backgroundFragmentImageValue == null) {
			return null;
		}

		BackgroundImageFragmentEditableElementValue
			backgroundImageFragmentEditableElementValue =
				new BackgroundImageFragmentEditableElementValue();

		backgroundImageFragmentEditableElementValue.
			setBackgroundFragmentImageValue(() -> backgroundFragmentImageValue);
		backgroundImageFragmentEditableElementValue.setType(
			FragmentEditableElementValue.Type.BACKGROUND_IMAGE);

		return backgroundImageFragmentEditableElementValue;
	}

	private static JSONObject _toConfigJSONObject(
		long companyId,
		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink,
		InfoItemServiceRegistry infoItemServiceRegistry, String mapperType,
		long scopeGroupId) {

		return JSONUtil.put(
			"config",
			() -> {
				if (fragmentEditableElementValueFragmentLink == null) {
					return null;
				}

				JSONObject configJSONObject = FragmentLinkUtil.toJSONObject(
					companyId,
					fragmentEditableElementValueFragmentLink.getFragmentLink(),
					infoItemServiceRegistry, scopeGroupId);

				if (JSONUtil.isEmpty(configJSONObject)) {
					return null;
				}

				configJSONObject.put("mapperType", mapperType);

				FragmentEditableElementValueFragmentLink.Prefix prefix =
					fragmentEditableElementValueFragmentLink.getPrefix();

				if ((prefix == null) ||
					Objects.equals(
						prefix,
						FragmentEditableElementValueFragmentLink.Prefix.NONE)) {

					return configJSONObject;
				}

				if (Objects.equals(
						prefix,
						FragmentEditableElementValueFragmentLink.Prefix.
							EMAIL)) {

					return configJSONObject.put("prefix", "mailto:");
				}

				return configJSONObject.put("prefix", "tel:");
			});
	}

	private static FragmentEditableElementValueFragmentLink
		_toFragmentEditableElementValueFragmentLink(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		FragmentLink fragmentLink = FragmentLinkUtil.toFragmentLink(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (fragmentLink == null) {
			return null;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				new FragmentEditableElementValueFragmentLink();

		fragmentEditableElementValueFragmentLink.setFragmentLink(
			() -> fragmentLink);
		fragmentEditableElementValueFragmentLink.setPrefix(
			() -> {
				if (!jsonObject.has("prefix")) {
					return null;
				}

				String prefix = jsonObject.getString("prefix");

				if (Validator.isNull(prefix)) {
					return FragmentEditableElementValueFragmentLink.Prefix.NONE;
				}

				if (prefix.equals("mailto:")) {
					return FragmentEditableElementValueFragmentLink.Prefix.
						EMAIL;
				}

				if (prefix.equals("tel:")) {
					return FragmentEditableElementValueFragmentLink.Prefix.
						PHONE;
				}

				return null;
			});

		return fragmentEditableElementValueFragmentLink;
	}

	private static FragmentImage _toFragmentImage(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		if (jsonObject == null) {
			return null;
		}

		FragmentImageValue fragmentImageValue = _toFragmentImageValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if ((fragmentImageValue == null) &&
			((configJSONObject == null) ||
			 (!configJSONObject.has("alt") &&
			  !configJSONObject.has("imageConfiguration") &&
			  !configJSONObject.has("lazyLoading")))) {

			return null;
		}

		FragmentImage fragmentImage = new FragmentImage();

		fragmentImage.setFragmentImageValue(() -> fragmentImageValue);

		if (configJSONObject == null) {
			return fragmentImage;
		}

		fragmentImage.setDescription_i18n(
			() -> {
				JSONObject altJSONObject = configJSONObject.getJSONObject(
					"alt");

				return LocalizedValueUtil.toLocalizedValues(
					altJSONObject, key -> altJSONObject.getString(key));
			});

		JSONObject imageConfigurationJSONObject =
			configJSONObject.getJSONObject("imageConfiguration");

		if (!JSONUtil.isEmpty(imageConfigurationJSONObject)) {
			fragmentImage.setFragmentImageViewports(
				() -> TransformUtil.transformToArray(
					new TreeSet<>(imageConfigurationJSONObject.keySet()),
					key -> {
						FragmentImageViewport.Id id =
							FragmentImageViewport.Id.create(
								ViewportIdUtil.toExternalType(key));
						String resolution =
							imageConfigurationJSONObject.getString(key);

						if ((id == null) || Validator.isNull(resolution)) {
							return null;
						}

						FragmentImageViewport fragmentImageViewport =
							new FragmentImageViewport();

						fragmentImageViewport.setId(() -> id);
						fragmentImageViewport.setResolution(() -> resolution);

						return fragmentImageViewport;
					},
					FragmentImageViewport.class));
		}

		fragmentImage.setLazyLoading(
			() -> {
				if (!configJSONObject.has("lazyLoading")) {
					return null;
				}

				return configJSONObject.getBoolean("lazyLoading");
			});

		return fragmentImage;
	}

	private static FragmentImageValue _toFragmentImageValue(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		if (FragmentMappingUtil.isMappedValue(jsonObject)) {
			FragmentMappedValue fragmentMappedValue =
				FragmentMappingUtil.toFragmentMappedValue(
					companyId, infoItemServiceRegistry, jsonObject,
					scopeGroupId);

			if (fragmentMappedValue == null) {
				return null;
			}

			MappedFragmentImageValue mappedFragmentImageValue =
				new MappedFragmentImageValue();

			mappedFragmentImageValue.setFragmentMappedValue(
				() -> fragmentMappedValue);
			mappedFragmentImageValue.setType(FragmentImageValue.Type.MAPPED);

			return mappedFragmentImageValue;
		}

		Map<String, ImageValue> imageValueMap =
			LocalizedValueUtil.toLocalizedValues(
				jsonObject,
				key -> _getImageValue(
					companyId, jsonObject.get(key), scopeGroupId));

		if (MapUtil.isEmpty(imageValueMap)) {
			return null;
		}

		DirectFragmentImageValue directFragmentImageValue =
			new DirectFragmentImageValue();

		directFragmentImageValue.setValue_i18n(() -> imageValueMap);
		directFragmentImageValue.setType(FragmentImageValue.Type.DIRECT);

		return directFragmentImageValue;
	}

	private static JSONObject _toFragmentImageValueJSONObject(
			FragmentImageValue fragmentImageValue,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		if (fragmentImageValue == null) {
			return null;
		}

		if (fragmentImageValue instanceof DirectFragmentImageValue) {
			DirectFragmentImageValue directFragmentImageValue =
				(DirectFragmentImageValue)fragmentImageValue;

			return LocalizedValueUtil.toJSONObject(
				directFragmentImageValue.getValue_i18n(),
				imageValue -> _getImageValueJSONObject(
					imageValue, layoutStructureItemImporterContext));
		}

		if (!(fragmentImageValue instanceof MappedFragmentImageValue)) {
			return null;
		}

		MappedFragmentImageValue mappedFragmentImageValue =
			(MappedFragmentImageValue)fragmentImageValue;

		return _getFragmentMappedValueJSONObject(
			layoutStructureItemImporterContext.getCompanyId(),
			mappedFragmentImageValue.getFragmentMappedValue(),
			layoutStructureItemImporterContext.getInfoItemServiceRegistry(),
			layoutStructureItemImporterContext.getGroupId());
	}

	private static FragmentLinkTextValue _toFragmentLinkTextValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				_toFragmentEditableElementValueFragmentLink(
					companyId, infoItemServiceRegistry,
					jsonObject.getJSONObject("config"), scopeGroupId);

		TextFragmentValue textFragmentValue = _toTextFragmentValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if ((fragmentEditableElementValueFragmentLink == null) &&
			(textFragmentValue == null)) {

			return null;
		}

		FragmentLinkTextValue fragmentLinkTextValue =
			new FragmentLinkTextValue();

		fragmentLinkTextValue.setFragmentEditableElementValueFragmentLink(
			() -> fragmentEditableElementValueFragmentLink);
		fragmentLinkTextValue.setTextFragmentValue(() -> textFragmentValue);

		return fragmentLinkTextValue;
	}

	private static HTMLFragmentEditableElementValue
		_toHTMLFragmentEditableElementValue(
			long companyId,
			FragmentEditableElementValue.Type fragmentEditableElementValueType,
			InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		HTMLFragmentValue htmlFragmentValue = _toHTMLFragmentValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (htmlFragmentValue == null) {
			return null;
		}

		HTMLFragmentEditableElementValue htmlFragmentEditableElementValue =
			new HTMLFragmentEditableElementValue();

		htmlFragmentEditableElementValue.setHtmlFragmentValue(
			() -> htmlFragmentValue);
		htmlFragmentEditableElementValue.setType(
			() -> fragmentEditableElementValueType);

		return htmlFragmentEditableElementValue;
	}

	private static HTMLFragmentValue _toHTMLFragmentValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		if (FragmentMappingUtil.isMappedValue(jsonObject)) {
			HTMLFragmentMappedValue htmlFragmentMappedValue =
				new HTMLFragmentMappedValue();

			htmlFragmentMappedValue.setFragmentMappedValue(
				() -> FragmentMappingUtil.toFragmentMappedValue(
					companyId, infoItemServiceRegistry, jsonObject,
					scopeGroupId));
			htmlFragmentMappedValue.setType(HTMLFragmentValue.Type.MAPPED);

			return htmlFragmentMappedValue;
		}

		FragmentInlineValue fragmentInlineValue = _getFragmentInlineValue(
			jsonObject);

		if (fragmentInlineValue == null) {
			return null;
		}

		HTMLFragmentInlineValue htmlFragmentInlineValue =
			new HTMLFragmentInlineValue();

		htmlFragmentInlineValue.setFragmentInlineValue(
			() -> fragmentInlineValue);
		htmlFragmentInlineValue.setType(HTMLFragmentValue.Type.INLINE);

		return htmlFragmentInlineValue;
	}

	private static FragmentEditableElementValue
			_toImageFragmentEditableElementValue(
				long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
				JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				_toFragmentEditableElementValueFragmentLink(
					companyId, infoItemServiceRegistry,
					jsonObject.getJSONObject("config"), scopeGroupId);

		FragmentImage fragmentImage = _toFragmentImage(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if ((fragmentEditableElementValueFragmentLink == null) &&
			(fragmentImage == null)) {

			return null;
		}

		ImageFragmentEditableElementValue imageFragmentEditableElementValue =
			new ImageFragmentEditableElementValue();

		imageFragmentEditableElementValue.
			setFragmentEditableElementValueFragmentLink(
				() -> fragmentEditableElementValueFragmentLink);
		imageFragmentEditableElementValue.setFragmentImage(() -> fragmentImage);
		imageFragmentEditableElementValue.setType(
			FragmentEditableElementValue.Type.IMAGE);

		return imageFragmentEditableElementValue;
	}

	private static LinkFragmentEditableElementValue
		_toLinkFragmentEditableElementValue(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		FragmentLinkTextValue fragmentLinkTextValue = _toFragmentLinkTextValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (fragmentLinkTextValue == null) {
			return null;
		}

		LinkFragmentEditableElementValue linkFragmentEditableElementValue =
			new LinkFragmentEditableElementValue();

		linkFragmentEditableElementValue.setFragmentLinkTextValue(
			() -> fragmentLinkTextValue);
		linkFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.LINK);

		return linkFragmentEditableElementValue;
	}

	private static TextFragmentEditableElementValue
		_toTextFragmentEditableElementValue(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		FragmentLinkTextValue fragmentLinkTextValue = _toFragmentLinkTextValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (fragmentLinkTextValue == null) {
			return null;
		}

		TextFragmentEditableElementValue textFragmentEditableElementValue =
			new TextFragmentEditableElementValue();

		textFragmentEditableElementValue.setFragmentLinkTextValue(
			() -> fragmentLinkTextValue);
		textFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.TEXT);

		return textFragmentEditableElementValue;
	}

	private static TextFragmentValue _toTextFragmentValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		if (FragmentMappingUtil.isMappedValue(jsonObject)) {
			TextFragmentMappedValue textFragmentMappedValue =
				new TextFragmentMappedValue();

			textFragmentMappedValue.setFragmentMappedValue(
				() -> FragmentMappingUtil.toFragmentMappedValue(
					companyId, infoItemServiceRegistry, jsonObject,
					scopeGroupId));
			textFragmentMappedValue.setType(
				() -> TextFragmentValue.Type.MAPPED);

			return textFragmentMappedValue;
		}

		FragmentInlineValue fragmentInlineValue = _getFragmentInlineValue(
			jsonObject);

		if (fragmentInlineValue == null) {
			return null;
		}

		TextFragmentInlineValue textFragmentInlineValue =
			new TextFragmentInlineValue();

		textFragmentInlineValue.setFragmentInlineValue(
			() -> fragmentInlineValue);
		textFragmentInlineValue.setType(() -> TextFragmentValue.Type.INLINE);

		return textFragmentInlineValue;
	}

}