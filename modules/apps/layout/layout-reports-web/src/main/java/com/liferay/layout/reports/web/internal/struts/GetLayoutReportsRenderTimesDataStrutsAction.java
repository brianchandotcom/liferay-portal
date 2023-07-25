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
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.taglib.servlet.taglib.renderer.LayoutStructureRenderer;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletRequest;

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

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			GuestOrUserUtil.getPermissionChecker(), layout);

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

		try {
			layoutStructureRenderer.render();
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get layout structure item render times", exception);

			return null;
		}

		List<LayoutStructureRenderer.LayoutStructureItemRenderTime>
			layoutStructureItemRenderTimes = ListUtil.sort(
				layoutStructureRenderer.getLayoutStructureItemRenderTimes(),
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

			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem) &&
				!(layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem)) {

				continue;
			}

			if (layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem) {

				jsonArray.put(
					JSONUtil.put(
						"cached", false
					).put(
						"fragment", false
					).put(
						"fragmentCollectionURL", StringPool.BLANK
					).put(
						"fromMaster",
						_isFromMaster(null, layout, layoutStructureItem)
					).put(
						"hierarchy",
						_getLayoutStructureHierarchy(
							layoutStructure, layoutStructureItem,
							themeDisplay.getLocale())
					).put(
						"itemId", layoutStructureItem.getItemId()
					).put(
						"itemType", layoutStructureItem.getItemType()
					).put(
						"name",
						_language.get(
							themeDisplay.getLocale(), "collection-display")
					).put(
						"renderTime",
						layoutStructureItemRenderTime.getRenderTime()
					));

				continue;
			}

			FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
				layoutStructureItem);

			FragmentEntry fragmentEntry = _getFragmentEntry(fragmentEntryLink);

			jsonArray.put(
				JSONUtil.put(
					"cached",
					() -> {
						if (fragmentEntryLink == null) {
							return false;
						}

						return fragmentEntryLink.isCacheable();
					}
				).put(
					"fragment",
					() -> {
						if (fragmentEntryLink == null) {
							return false;
						}

						return !fragmentEntryLink.isTypePortlet();
					}
				).put(
					"fragmentCollectionURL",
					_getFragmentCollectionURL(
						fragmentEntry, fragmentEntryLink, httpServletRequest,
						themeDisplay)
				).put(
					"fromMaster",
					_isFromMaster(
						fragmentEntryLink, layout, layoutStructureItem)
				).put(
					"hierarchy",
					_getLayoutStructureHierarchy(
						layoutStructure, layoutStructureItem,
						themeDisplay.getLocale())
				).put(
					"itemId", layoutStructureItem.getItemId()
				).put(
					"itemType", layoutStructureItem.getItemType()
				).put(
					"name",
					_getFragmentEntryName(
						fragmentEntryLink, themeDisplay.getLocale())
				).put(
					"renderTime", layoutStructureItemRenderTime.getRenderTime()
				));
		}

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());

		return null;
	}

	private String _getFragmentCollectionURL(
		FragmentEntry fragmentEntry, FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		if ((fragmentEntryLink == null) || (fragmentEntry == null)) {
			return StringPool.BLANK;
		}

		long fragmentCollectionId = fragmentEntry.getFragmentCollectionId();

		if (fragmentCollectionId > 0) {
			return PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest, themeDisplay.getScopeGroup(),
					FragmentPortletKeys.FRAGMENT, 0, 0,
					PortletRequest.RENDER_PHASE)
			).setParameter(
				"fragmentCollectionId", fragmentCollectionId
			).buildString();
		}

		String fragmentEntryKey = fragmentEntry.getFragmentEntryKey();

		int index = fragmentEntryKey.indexOf(CharPool.DASH);

		if (index == -1) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				FragmentPortletKeys.FRAGMENT, 0, 0, PortletRequest.RENDER_PHASE)
		).setParameter(
			"fragmentCollectionKey", fragmentEntryKey.substring(0, index)
		).buildString();
	}

	private FragmentEntry _getFragmentEntry(
		FragmentEntryLink fragmentEntryLink) {

		if (fragmentEntryLink == null) {
			return null;
		}

		long fragmentEntryId = fragmentEntryLink.getFragmentEntryId();

		if (fragmentEntryId > 0) {
			return _fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryId);
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNull(rendererKey)) {
			return null;
		}

		return _fragmentCollectionContributorRegistry.getFragmentEntry(
			rendererKey);
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

	private String _getFragmentEntryName(
			FragmentEntryLink fragmentEntryLink, Locale locale)
		throws Exception {

		if (fragmentEntryLink == null) {
			return StringPool.BLANK;
		}

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

	private String _getLayoutStructureHierarchy(
			LayoutStructure layoutStructure,
			LayoutStructureItem layoutStructureItem, Locale locale)
		throws Exception {

		List<String> layoutStructureHierarchy = _getLayoutStructureHierarchy(
			new ArrayList<>(), layoutStructure, layoutStructureItem, locale);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < layoutStructureHierarchy.size(); i++) {
			sb.append(layoutStructureHierarchy.get(i));

			if (i < (layoutStructureHierarchy.size() - 1)) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.GREATER_THAN);
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private List<String> _getLayoutStructureHierarchy(
			List<String> fragmentHierarchy, LayoutStructure layoutStructure,
			LayoutStructureItem layoutStructureItem, Locale locale)
		throws Exception {

		if (!Objects.equals(
				layoutStructureItem.getItemId(),
				layoutStructure.getMainItemId()) &&
			Validator.isNotNull(layoutStructureItem.getParentItemId())) {

			_getLayoutStructureHierarchy(
				fragmentHierarchy, layoutStructure,
				layoutStructure.getLayoutStructureItem(
					layoutStructureItem.getParentItemId()),
				locale);
		}

		String name = _getLayoutStructureItemName(layoutStructureItem, locale);

		if (Validator.isNotNull(name)) {
			fragmentHierarchy.add(name);
		}

		return fragmentHierarchy;
	}

	private String _getLayoutStructureItemName(
			LayoutStructureItem layoutStructureItem, Locale locale)
		throws Exception {

		if (layoutStructureItem instanceof
				CollectionStyledLayoutStructureItem) {

			return _language.get(locale, "collection-display");
		}
		else if (layoutStructureItem instanceof
					ContainerStyledLayoutStructureItem) {

			return _language.get(locale, "container");
		}
		else if (layoutStructureItem instanceof FormStyledLayoutStructureItem) {
			return _language.get(locale, "form-container");
		}
		else if (layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem) {

			return _getFragmentEntryName(
				_getFragmentEntryLink(layoutStructureItem), locale);
		}
		else if (layoutStructureItem instanceof RowStyledLayoutStructureItem) {
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

	private boolean _isFromMaster(
		FragmentEntryLink fragmentEntryLink, Layout layout,
		LayoutStructureItem layoutStructureItem) {

		if (layout.getMasterLayoutPlid() == 0) {
			return false;
		}

		if (fragmentEntryLink != null) {
			if (fragmentEntryLink.getPlid() == layout.getMasterLayoutPlid()) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		GetLayoutReportsRenderTimesDataStrutsAction.class);

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentEntryLinkHelper _fragmentEntryLinkHelper;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

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