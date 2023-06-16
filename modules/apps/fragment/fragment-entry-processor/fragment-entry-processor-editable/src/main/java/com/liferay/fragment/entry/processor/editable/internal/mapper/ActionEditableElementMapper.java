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

package com.liferay.fragment.entry.processor.editable.internal.mapper;

import com.liferay.fragment.entry.processor.editable.mapper.EditableElementMapper;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(property = "type=action", service = EditableElementMapper.class)
public class ActionEditableElementMapper implements EditableElementMapper {

	@Override
	public void map(
			Element element, JSONObject configJSONObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject mappedActionJSONObject = configJSONObject.getJSONObject(
			"mappedAction");

		if (mappedActionJSONObject == null) {
			return;
		}

		String fieldId = mappedActionJSONObject.getString("fieldId");

		if (Validator.isNull(fieldId)) {
			fieldId = mappedActionJSONObject.getString("collectionFieldId");
		}

		if (Validator.isNull(fieldId)) {
			fieldId = mappedActionJSONObject.getString("mappedField");
		}

		if (Validator.isNull(fieldId)) {
			return;
		}

		String classNameId = mappedActionJSONObject.getString("classNameId");
		String classPK = mappedActionJSONObject.getString("classPK");

		if (Validator.isNull(classNameId) || Validator.isNull(classPK)) {
			InfoItemReference infoItemReference =
				fragmentEntryProcessorContext.getContextInfoItemReference();

			if (infoItemReference == null) {
				return;
			}

			classNameId = String.valueOf(
				_portal.getClassNameId(infoItemReference.getClassName()));

			InfoItemIdentifier infoItemIdentifier =
				infoItemReference.getInfoItemIdentifier();

			if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)infoItemIdentifier;

				classPK = String.valueOf(
					classPKInfoItemIdentifier.getClassPK());
			}

			if (Validator.isNull(classNameId) || Validator.isNull(classPK)) {
				return;
			}
		}

		element.attr("data-lfr-class-name-id", classNameId);
		element.attr("data-lfr-class-pk", classPK);
		element.attr("data-lfr-field-id", fieldId);

		_addDataAtributes(
			element, configJSONObject.getJSONObject("onError"), "error");
		_addDataAtributes(
			element, configJSONObject.getJSONObject("onSuccess"), "success");
	}

	private void _addDataAtributes(
			Element element, JSONObject jsonObject, String resultType)
		throws PortalException {

		if (jsonObject == null) {
			return;
		}

		String interaction = jsonObject.getString("interaction");

		if (Validator.isNull(interaction)) {
			interaction = _INTERACTION_NONE;
		}

		element.attr("data-lfr-on-" + resultType + "-interaction", interaction);

		if ((interaction.equals(_INTERACTION_NONE) ||
			 interaction.equals(_INTERACTION_NOTIFICATION)) &&
			jsonObject.getBoolean("reload")) {

			element.attr(
				"data-lfr-on-" + resultType + "-reload", StringPool.TRUE);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (themeDisplay == null) {
			return;
		}

		if (interaction.equals(_INTERACTION_NOTIFICATION)) {
			JSONObject textJSONObject = jsonObject.getJSONObject("text");

			if (textJSONObject != null) {
				String text = textJSONObject.getString(
					themeDisplay.getLanguageId());

				if (Validator.isNotNull(text)) {
					element.attr("data-lfr-on-" + resultType + "-text", text);
				}
			}
		}
		else if (interaction.equals(_INTERACTION_PAGE)) {
			JSONObject pageJSONObject = jsonObject.getJSONObject("page");

			if (pageJSONObject != null) {
				Layout layout = _layoutLocalService.fetchLayout(
					pageJSONObject.getLong("groupId"),
					pageJSONObject.getBoolean("privateLayout"),
					pageJSONObject.getLong("layoutId"));

				if (layout != null) {
					element.attr(
						"data-lfr-on-" + resultType + "-page-url",
						_portal.getLayoutURL(layout, themeDisplay));
				}
			}
		}
		else if (interaction.equals(_INTERACTION_URL)) {
			JSONObject urlJSONObject = jsonObject.getJSONObject("url");

			if (urlJSONObject != null) {
				String url = urlJSONObject.getString(
					themeDisplay.getLanguageId());

				if (Validator.isNull(url)) {
					Locale locale = LocaleUtil.getSiteDefault();

					url = urlJSONObject.getString(locale.getLanguage());
				}

				if (Validator.isNotNull(url)) {
					element.attr(
						"data-lfr-on-" + resultType + "-page-url", url);
				}
			}
		}
	}

	private static final String _INTERACTION_NONE = "none";

	private static final String _INTERACTION_NOTIFICATION = "notification";

	private static final String _INTERACTION_PAGE = "page";

	private static final String _INTERACTION_URL = "url";

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}