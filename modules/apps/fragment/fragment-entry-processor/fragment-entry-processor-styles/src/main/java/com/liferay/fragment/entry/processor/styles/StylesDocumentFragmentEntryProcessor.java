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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DocumentFragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=7",
	service = DocumentFragmentEntryProcessor.class
)
public class StylesDocumentFragmentEntryProcessor
	implements DocumentFragmentEntryProcessor {

	@Override
	public void processFragmentEntryLinkHTML(
		FragmentEntryLink fragmentEntryLink, Document document,
		FragmentEntryProcessorContext fragmentEntryProcessorContext) {

		Elements elements = document.select("[data-lfr-styles]");

		if (elements.isEmpty()) {
			return;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			_getLayoutStructureItem(
				fragmentEntryLink,
				fragmentEntryProcessorContext.getHttpServletRequest());

		if (fragmentStyledLayoutStructureItem == null) {
			return;
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
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return null;
			}
		}

		return (FragmentStyledLayoutStructureItem)
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StylesDocumentFragmentEntryProcessor.class);

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

}