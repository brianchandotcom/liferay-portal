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

package com.liferay.layout.reports.web.internal.struts;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.taglib.servlet.taglib.renderer.LayoutStructureRenderer;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "path=/layout_reports/get_render_times_data",
	service = StrutsAction.class
)
public class GetLayoutReportsRenderTimesDataStrutsAction
	implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			ParamUtil.getLong(httpServletRequest, "p_l_id"));

		if ((layout == null) || !layout.isTypeContent()) {
			return null;
		}

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				layout.getPlid(),
				_getSegmentsExperienceId(httpServletRequest, layout));

		if (layoutStructure == null) {
			return null;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		LayoutStructureRenderer layoutStructureRenderer =
			new LayoutStructureRenderer(
				httpServletRequest, layoutStructure,
				layoutStructure.getMainItemId(),
				FragmentEntryLinkConstants.VIEW,
				PageContextFactoryUtil.create(
					httpServletRequest,
					new PipingServletResponse(
						httpServletResponse, new DummyWriter())),
				false, false);

		List<LayoutStructureRenderer.LayoutStructureItemRenderTime>
			layoutStructureItemRenderTimes =
				layoutStructureRenderer.getLayoutStructureItemRenderTimes();

		ListUtil.sort(
			layoutStructureItemRenderTimes,
			Comparator.comparingLong(
				LayoutStructureRenderer.LayoutStructureItemRenderTime::
					getRenderTime
			).reversed());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		for (LayoutStructureRenderer.LayoutStructureItemRenderTime
				layoutStructureItemRenderTime :
					layoutStructureItemRenderTimes) {

			LayoutStructureItem layoutStructureItem =
				layoutStructureItemRenderTime.getLayoutStructureItem();

			String itemType = layoutStructureItem.getItemType();

			if (!StringUtil.equals(
					itemType, LayoutDataItemTypeConstants.TYPE_FRAGMENT) &&
				!StringUtil.equals(
					itemType, LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

				continue;
			}

			FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
				layoutStructureItem);

			jsonArray.put(
				JSONUtil.put(
					"fragment",
					() -> {
						if (fragmentEntryLink == null) {
							return false;
						}

						return !fragmentEntryLink.isTypePortlet();
					}
				).put(
					"fromMaster",
					() -> {
						if (layout.getMasterLayoutPlid() == 0) {
							return false;
						}

						if (fragmentEntryLink != null) {
							if (fragmentEntryLink.getPlid() ==
									layout.getMasterLayoutPlid()) {

								return true;
							}

							return false;
						}

						LayoutStructure masterLayoutStructure =
							_layoutStructureProvider.getLayoutStructure(
								layout.getMasterLayoutPlid(),
								_segmentsExperienceLocalService.
									fetchDefaultSegmentsExperienceId(
										layout.getMasterLayoutPlid()));

						if (masterLayoutStructure == null) {
							return false;
						}

						LayoutStructureItem masterLayoutStructureItem =
							masterLayoutStructure.getLayoutStructureItem(
								layoutStructureItem.getItemId());

						if (masterLayoutStructureItem != null) {
							return true;
						}

						return false;
					}
				).put(
					"itemId", layoutStructureItem.getItemId()
				).put(
					"itemType", layoutStructureItem.getItemType()
				).put(
					"name",
					_getLayoutStructureItemName(
						fragmentEntryLink, layoutStructureItem,
						themeDisplay.getLocale())
				).put(
					"renderTime", layoutStructureItemRenderTime.getRenderTime()
				));
		}

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());

		return null;
	}

	private FragmentEntryLink _getFragmentEntryLink(
		LayoutStructureItem layoutStructureItem) {

		if (!(layoutStructureItem instanceof
				FragmentStyledLayoutStructureItem)) {

			return null;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)layoutStructureItem;

		long fragmentEntryLinkId =
			fragmentStyledLayoutStructureItem.getFragmentEntryLinkId();

		if (fragmentEntryLinkId == 0) {
			return null;
		}

		return _fragmentEntryLinkLocalService.fetchFragmentEntryLink(
			fragmentEntryLinkId);
	}

	private String _getLayoutStructureItemName(
			FragmentEntryLink fragmentEntryLink,
			LayoutStructureItem layoutStructureItem, Locale locale)
		throws Exception {

		if (fragmentEntryLink != null) {
			if (Validator.isNotNull(fragmentEntryLink.getRendererKey()) ||
				(fragmentEntryLink.getFragmentEntryId() > 0)) {

				return _fragmentEntryLinkHelper.getFragmentEntryName(
					fragmentEntryLink, locale);
			}

			String portletId = _getPortletId(fragmentEntryLink);

			if (Validator.isNotNull(portletId)) {
				return _portal.getPortletTitle(portletId, locale);
			}

			return StringPool.BLANK;
		}

		String itemType = layoutStructureItem.getItemType();

		if (StringUtil.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

			return _language.get(locale, "collection-display");
		}
		else if (StringUtil.equals(
					itemType, LayoutDataItemTypeConstants.TYPE_CONTAINER)) {

			return _language.get(locale, "container");
		}
		else if (StringUtil.equals(
					itemType, LayoutDataItemTypeConstants.TYPE_FORM)) {

			return _language.get(locale, "form-container");
		}
		else if (StringUtil.equals(
					itemType, LayoutDataItemTypeConstants.TYPE_ROW)) {

			return _language.get(locale, "grid");
		}

		return StringPool.BLANK;
	}

	private String _getPortletId(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		if ((fragmentEntryLink == null) || !fragmentEntryLink.isTypePortlet()) {
			return StringPool.BLANK;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		return jsonObject.getString("portletId");
	}

	private long _getSegmentsExperienceId(
		HttpServletRequest httpServletRequest, Layout layout) {

		long segmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "segmentsExperienceId");

		if (segmentsExperienceId != 0) {
			return segmentsExperienceId;
		}

		return _segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
			layout.getPlid());
	}

	@Reference
	private FragmentEntryLinkHelper _fragmentEntryLinkHelper;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutStructureProvider _layoutStructureProvider;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}