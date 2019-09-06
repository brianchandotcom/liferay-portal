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
import com.liferay.fragment.entry.processor.util.FragmentEntryProcessorHelper;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;

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

		boolean assetDisplayPage =
			_fragmentEntryProcessorHelper.isAssetDisplayPage(
				fragmentEntryProcessorContext.getMode());

		boolean mapped = _fragmentEntryProcessorHelper.isMapped(
			configJSONObject);

		if (Validator.isNull(href) && !assetDisplayPage && !mapped) {
			return;
		}

		Element linkElement = new Element("a");

		Elements elements = element.children();

		Element firstChild = elements.first();

		boolean replaceLink = false;

		if ((firstChild != null) &&
			StringUtil.equalsIgnoreCase(firstChild.tagName(), "a")) {

			linkElement = firstChild;
			replaceLink = true;
		}

		if (configJSONObject.has("target")) {
			linkElement.attr("target", configJSONObject.getString("target"));
		}

		String mappedField = configJSONObject.getString("mappedField");

		if (mapped) {
			Object fieldValue = _fragmentEntryProcessorHelper.getMappedValue(
				configJSONObject, new HashMap<>(),
				fragmentEntryProcessorContext);

			if (fieldValue == null) {
				return;
			}

			linkElement.attr("href", fieldValue.toString());

			linkElement.html(replaceLink ? firstChild.html() : element.html());

			element.html(linkElement.outerHtml());
		}
		else if (Validator.isNotNull(href)) {
			linkElement.attr("href", href);

			linkElement.html(replaceLink ? firstChild.html() : element.html());

			element.html(linkElement.outerHtml());
		}
		else if (assetDisplayPage && Validator.isNotNull(mappedField)) {
			linkElement.attr("href", "${" + mappedField + "}");

			linkElement.html(replaceLink ? firstChild.html() : element.html());

			element.html(
				_fragmentEntryProcessorHelper.processTemplate(
					linkElement.outerHtml(), fragmentEntryProcessorContext));
		}
	}

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

}