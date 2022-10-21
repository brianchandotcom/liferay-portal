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
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "type=link",
	service = EditableElementMapper.class
)
public class LinkEditableElementMapper implements EditableElementMapper {

	@Override
	public void map(
			Element element, JSONObject configJSONObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		String href = configJSONObject.getString("href");

		JSONObject hrefJSONObject = configJSONObject.getJSONObject("href");

		boolean assetDisplayPage =
			_fragmentEntryProcessorHelper.isAssetDisplayPage(
				fragmentEntryProcessorContext.getMode());
		boolean collectionMapped =
			_fragmentEntryProcessorHelper.isMappedCollection(configJSONObject);
		boolean layoutMapped = _fragmentEntryProcessorHelper.isMappedLayout(
			configJSONObject);
		boolean mapped = _fragmentEntryProcessorHelper.isMapped(
			configJSONObject);

		if (Validator.isNull(href) && (hrefJSONObject == null) &&
			!assetDisplayPage && !collectionMapped && !layoutMapped &&
			!mapped) {

			return;
		}

		if (collectionMapped) {
			href = GetterUtil.getString(
				_fragmentEntryProcessorHelper.getMappedCollectionValue(
					fragmentEntryProcessorContext.
						getContextInfoItemReferenceOptional(),
					configJSONObject,
					fragmentEntryProcessorContext.getLocale()));
		}
		else if (layoutMapped) {
			href = GetterUtil.getString(
				_getMappedLayoutValue(
					configJSONObject, fragmentEntryProcessorContext));
		}
		else if (mapped) {
			href = GetterUtil.getString(
				_fragmentEntryProcessorHelper.getMappedInfoItemFieldValue(
					configJSONObject, new HashMap<>(),
					fragmentEntryProcessorContext.getLocale(),
					fragmentEntryProcessorContext.getMode(),
					fragmentEntryProcessorContext.getPreviewClassPK(),
					fragmentEntryProcessorContext.getPreviewVersion()));
		}
		else if (assetDisplayPage && configJSONObject.has("mappedField")) {
			HttpServletRequest httpServletRequest =
				fragmentEntryProcessorContext.getHttpServletRequest();

			if (httpServletRequest != null) {
				String mappedField = configJSONObject.getString("mappedField");

				Object infoItem = httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM);

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						(InfoItemFieldValuesProvider)
							httpServletRequest.getAttribute(
								InfoDisplayWebKeys.
									INFO_ITEM_FIELD_VALUES_PROVIDER);

				href = GetterUtil.getString(
					_fragmentEntryProcessorHelper.getMappedInfoItemFieldValue(
						mappedField, infoItemFieldValuesProvider,
						fragmentEntryProcessorContext.getLocale(), infoItem));
			}
		}
		else if (hrefJSONObject != null) {
			String languageId = LocaleUtil.toLanguageId(
				fragmentEntryProcessorContext.getLocale());

			if (!hrefJSONObject.has(languageId)) {
				languageId = LocaleUtil.toLanguageId(
					LocaleUtil.getSiteDefault());
			}

			href = hrefJSONObject.getString(languageId);
		}

		Element linkElement = new Element("a");

		Elements elements = element.children();

		Element firstChildElement = elements.first();

		boolean processEditableTag = false;

		if (StringUtil.equalsIgnoreCase(element.tagName(), "a")) {
			linkElement = element;
		}
		else if (StringUtil.equalsIgnoreCase(
					element.tagName(), "lfr-editable")) {

			processEditableTag = true;
		}

		boolean replaceLink = false;

		if ((firstChildElement != null) && processEditableTag &&
			StringUtil.equalsIgnoreCase(firstChildElement.tagName(), "a")) {

			linkElement = firstChildElement;
			replaceLink = true;
		}

		String target = configJSONObject.getString("target");

		if (Validator.isNotNull(target)) {
			if (StringUtil.equalsIgnoreCase(target, "_parent") ||
				StringUtil.equalsIgnoreCase(target, "_top")) {

				target = "_self";
			}

			linkElement.attr("target", target);
		}

		if (Validator.isNull(href)) {
			return;
		}

		linkElement.attr("href", href);

		_replaceLinkContent(
			element, firstChildElement, linkElement, replaceLink);

		if (((linkElement != element) || processEditableTag) &&
			Validator.isNotNull(element.html())) {

			element.html(linkElement.outerHtml());
		}
		else if ((linkElement != element) && Validator.isNull(element.html())) {
			element.replaceWith(linkElement);
		}
	}

	private Object _getMappedLayoutValue(
			JSONObject jsonObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (!_fragmentEntryProcessorHelper.isMappedLayout(jsonObject)) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest =
			fragmentEntryProcessorContext.getHttpServletRequest();

		if (httpServletRequest == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		JSONObject layoutJSONObject = jsonObject.getJSONObject("layout");

		long groupId = layoutJSONObject.getLong("groupId");

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return StringPool.POUND;
		}

		Layout layout = _layoutLocalService.fetchLayout(
			groupId, layoutJSONObject.getBoolean("privateLayout"),
			layoutJSONObject.getLong("layoutId"));

		if (layout == null) {
			return StringPool.POUND;
		}

		return _portal.getLayoutRelativeURL(layout, themeDisplay);
	}

	private void _replaceLinkContent(
		Element element, Element firstChildElement, Element linkElement,
		boolean replaceLink) {

		if (replaceLink && Validator.isNull(firstChildElement.html())) {
			linkElement.html(firstChildElement.outerHtml());
		}
		else if (replaceLink && Validator.isNotNull(firstChildElement.html())) {
			linkElement.html(firstChildElement.html());
		}
		else if (Validator.isNull(element.html())) {
			linkElement.html(element.outerHtml());
		}
		else {
			linkElement.html(element.html());
		}
	}

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}