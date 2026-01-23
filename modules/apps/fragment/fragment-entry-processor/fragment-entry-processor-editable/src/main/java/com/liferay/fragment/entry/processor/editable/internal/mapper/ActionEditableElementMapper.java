/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.editable.internal.mapper;

import com.liferay.fragment.entry.processor.editable.element.constants.ActionEditableElementConstants;
import com.liferay.fragment.entry.processor.editable.mapper.EditableElementMapper;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.entry.processor.helper.InfoItemFieldMapped;
import com.liferay.fragment.entry.processor.helper.LayoutReferenceResolver;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.info.type.WebURL;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

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

		InfoItemFieldMapped infoItemFieldMapped =
			_fragmentEntryProcessorHelper.getInfoItemFieldMapped(
				mappedActionJSONObject, fragmentEntryProcessorContext);

		if ((infoItemFieldMapped == null) ||
			(infoItemFieldMapped.getObject() == null)) {

			return;
		}

		element.attr(
			"data-lfr-class-name-id",
			String.valueOf(
				_portal.getClassNameId(infoItemFieldMapped.getClassName())));
		element.attr(
			"data-lfr-class-pk",
			String.valueOf(
				_getClassPK(
					infoItemFieldMapped,
					fragmentEntryProcessorContext.getScopeGroupId())));
		element.attr("data-lfr-field-id", infoItemFieldMapped.getFieldName());

		_addDataAtributes(
			element, fragmentEntryProcessorContext, infoItemFieldMapped,
			configJSONObject.getJSONObject("onError"), "error");
		_addDataAtributes(
			element, fragmentEntryProcessorContext, infoItemFieldMapped,
			configJSONObject.getJSONObject("onSuccess"), "success");
	}

	private void _addDataAtributes(
			Element element,
			FragmentEntryProcessorContext fragmentEntryProcessorContext,
			InfoItemFieldMapped infoItemFieldMapped, JSONObject jsonObject,
			String resultType)
		throws PortalException {

		if (jsonObject == null) {
			return;
		}

		String interaction = jsonObject.getString("interaction");

		if (Validator.isNull(interaction)) {
			interaction = ActionEditableElementConstants.INTERACTION_NONE;
		}

		element.attr("data-lfr-on-" + resultType + "-interaction", interaction);

		if ((interaction.equals(
				ActionEditableElementConstants.INTERACTION_NONE) ||
			 interaction.equals(
				 ActionEditableElementConstants.INTERACTION_NOTIFICATION)) &&
			jsonObject.getBoolean("reload")) {

			element.attr(
				"data-lfr-on-" + resultType + "-reload", StringPool.TRUE);
		}

		if (interaction.equals(
				ActionEditableElementConstants.INTERACTION_DISPLAY_PAGE)) {

			if (!resultType.equals("success") ||
				(infoItemFieldMapped.getObject() == null)) {

				return;
			}

			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					infoItemFieldMapped.getClassName());

			if (infoItemFieldValuesProvider == null) {
				return;
			}

			InfoFieldValue<Object> infoFieldValue =
				infoItemFieldValuesProvider.getInfoFieldValue(
					infoItemFieldMapped.getObject(),
					jsonObject.getString("displayPageUniqueFieldId"));

			if (infoFieldValue == null) {
				return;
			}

			String url = null;

			Object infoFieldValueValue = infoFieldValue.getValue();

			if (infoFieldValueValue instanceof FunctionInfoLocalizedValue) {
				FunctionInfoLocalizedValue<?> functionInfoLocalizedValue =
					(FunctionInfoLocalizedValue<?>)infoFieldValueValue;

				Object value = functionInfoLocalizedValue.getValue();

				if (!(value instanceof WebURL)) {
					return;
				}

				WebURL webURL = (WebURL)value;

				url = webURL.getURL();
			}
			else if (infoFieldValueValue instanceof String) {
				url = (String)infoFieldValueValue;
			}

			if (Validator.isNull(url)) {
				return;
			}

			element.attr("data-lfr-on-" + resultType + "-page-url", url);
		}

		if (interaction.equals(
				ActionEditableElementConstants.INTERACTION_NOTIFICATION)) {

			JSONObject textJSONObject = jsonObject.getJSONObject("text");

			if (textJSONObject == null) {
				return;
			}

			String text = textJSONObject.getString(
				LocaleUtil.toLanguageId(
					fragmentEntryProcessorContext.getLocale()));

			if (Validator.isNull(text)) {
				return;
			}

			element.attr("data-lfr-on-" + resultType + "-text", text);
		}
		else if (interaction.equals(
					ActionEditableElementConstants.INTERACTION_PAGE)) {

			JSONObject pageJSONObject = jsonObject.getJSONObject("page");

			if (pageJSONObject == null) {
				return;
			}

			Layout layout = _layoutReferenceResolver.resolve(
				fragmentEntryProcessorContext.getCompanyId(), pageJSONObject,
				fragmentEntryProcessorContext.getScopeGroupId());

			if (layout == null) {
				return;
			}

			HttpServletRequest httpServletRequest =
				fragmentEntryProcessorContext.getHttpServletRequest();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (themeDisplay == null) {
				return;
			}

			element.attr(
				"data-lfr-on-" + resultType + "-page-url",
				_portal.getLayoutURL(layout, themeDisplay));
		}
		else if (interaction.equals(
					ActionEditableElementConstants.INTERACTION_URL)) {

			JSONObject urlJSONObject = jsonObject.getJSONObject("url");

			if (urlJSONObject == null) {
				return;
			}

			String url = urlJSONObject.getString(
				LocaleUtil.toLanguageId(
					fragmentEntryProcessorContext.getLocale()));

			if (Validator.isNull(url)) {
				Locale locale = LocaleUtil.getSiteDefault();

				url = urlJSONObject.getString(locale.getLanguage());
			}

			if (Validator.isNull(url)) {
				return;
			}

			element.attr("data-lfr-on-" + resultType + "-page-url", url);
		}
	}

	private long _getClassPK(
		InfoItemFieldMapped infoItemFieldMapped, long scopeGroupId) {

		if (infoItemFieldMapped.getInfoItemIdentifier() instanceof
				ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemFieldMapped.getInfoItemIdentifier();

			return classPKInfoItemIdentifier.getClassPK();
		}

		if (infoItemFieldMapped.getObject() == null) {
			return 0;
		}

		InfoItemDetailsProvider infoItemDetailsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				infoItemFieldMapped.getClassName());

		InfoItemDetails infoItemDetails =
			infoItemDetailsProvider.getInfoItemDetails(
				scopeGroupId, ClassPKInfoItemIdentifier.class,
				infoItemFieldMapped.getObject());

		InfoItemReference infoItemReference =
			infoItemDetails.getInfoItemReference();

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return classPKInfoItemIdentifier.getClassPK();
	}

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutReferenceResolver _layoutReferenceResolver;

	@Reference
	private Portal _portal;

}