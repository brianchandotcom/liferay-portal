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

package com.liferay.fragment.entry.processor.styles;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=7",
	service = FragmentEntryProcessor.class
)
public class StylesFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getDataAttributesJSONArray() {
		return JSONUtil.put("lfr-styles");
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-styles]");

		if (elements.isEmpty()) {
			return null;
		}

		return JSONUtil.put("hasCommonStyles", true);
	}

	@Override
	public String processFragmentEntryLinkHTML(
		FragmentEntryLink fragmentEntryLink, String html,
		FragmentEntryProcessorContext fragmentEntryProcessorContext) {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-styles]");

		if (elements.isEmpty()) {
			return html;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			_getLayoutStructureItem(
				fragmentEntryLink,
				fragmentEntryProcessorContext.getHttpServletRequest());

		if (fragmentStyledLayoutStructureItem == null) {
			return html;
		}

		String fragmentEntryLinkCssClass =
			fragmentStyledLayoutStructureItem.getFragmentEntryLinkCssClass(
				fragmentEntryLink);
		String layoutStructureItemUniqueCssClass =
			fragmentStyledLayoutStructureItem.getUniqueCssClass();
		String styledLayoutStructureItemCssClasses =
			fragmentStyledLayoutStructureItem.getStyledCssClasses();

		for (Element element : elements) {
			element.addClass(fragmentEntryLinkCssClass);
			element.addClass(layoutStructureItemUniqueCssClass);
			element.addClass(styledLayoutStructureItemCssClasses);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-styles]");

		if (!elements.isEmpty() && (elements.size() > 1)) {
			throw new FragmentEntryContentException(
				_language.get(
					_portal.getResourceBundle(LocaleUtil.getDefault()),
					"the-data-lfr-styles-attribute-can-be-used-only-once-on-" +
						"the-same-fragment"));
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private FragmentStyledLayoutStructureItem _getLayoutStructureItem(
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest) {

		LayoutStructure layoutStructure = null;

		if (httpServletRequest != null) {
			layoutStructure = (LayoutStructure)httpServletRequest.getAttribute(
				LayoutWebKeys.LAYOUT_STRUCTURE);
		}

		if (layoutStructure == null) {
			try {
				LayoutPageTemplateStructure layoutPageTemplateStructure =
					_layoutPageTemplateStructureLocalService.
						fetchLayoutPageTemplateStructure(
							fragmentEntryLink.getGroupId(),
							fragmentEntryLink.getPlid(), true);

				layoutStructure = LayoutStructure.of(
					layoutPageTemplateStructure.getData(
						fragmentEntryLink.getSegmentsExperienceId()));
			}
			catch (Exception exception) {
				return null;
			}
		}

		return (FragmentStyledLayoutStructureItem)
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}