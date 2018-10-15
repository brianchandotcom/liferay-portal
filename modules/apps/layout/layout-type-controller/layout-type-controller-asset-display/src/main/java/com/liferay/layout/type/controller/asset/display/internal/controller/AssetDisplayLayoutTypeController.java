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

package com.liferay.layout.type.controller.asset.display.internal.controller;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayLayoutTypeControllerConstants;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayLayoutTypeControllerWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.impl.BaseLayoutTypeControllerImpl;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juergen Kappler
 */
@Component(
	immediate = true,
	property = "layout.type=" + AssetDisplayLayoutTypeControllerConstants.LAYOUT_TYPE_ASSET_DISPLAY,
	service = LayoutTypeController.class
)
public class AssetDisplayLayoutTypeController
	extends BaseLayoutTypeControllerImpl {

	@Override
	public String getType() {
		return LayoutConstants.TYPE_PORTLET;
	}

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		AssetEntry assetEntry = (AssetEntry)request.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (assetEntry != null) {
			long layoutPageTemplateEntryId = _getLayoutPageTemplateEntryId(
				layout.getGroupId(), assetEntry);

			List<FragmentEntryLink> fragmentEntryLinks = _getFragmentEntryLinks(
				layout, layoutPageTemplateEntryId);

			request.setAttribute(
				AssetDisplayLayoutTypeControllerWebKeys.LAYOUT_FRAGMENTS,
				fragmentEntryLinks);
		}

		return super.includeLayoutContent(request, response, layout);
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return true;
	}

	@Override
	public boolean isInstanceable() {
		return false;
	}

	@Override
	public boolean isParentable() {
		return false;
	}

	@Override
	public boolean isSitemapable() {
		return false;
	}

	@Override
	public boolean isURLFriendliable() {
		return true;
	}

	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse response, UnsyncStringWriter unsyncStringWriter) {

		return new PipingServletResponse(response, unsyncStringWriter);
	}

	@Override
	protected String getEditPage() {
		return null;
	}

	@Override
	protected String getViewPage() {
		return _VIEW_PAGE;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.asset.display)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private List<FragmentEntryLink> _getFragmentEntryLinks(
			Layout layout, long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					_portal.getClassNameId(
						LayoutPageTemplateEntry.class.getName()),
					layoutPageTemplateEntryId, true);

		String data = layoutPageTemplateStructure.getData();

		if (Validator.isNull(data)) {
			return Collections.emptyList();
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return Collections.emptyList();
		}

		List<FragmentEntryLink> fragmentEntryLinksList = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				layout.getGroupId(),
				_portal.getClassNameId(LayoutPageTemplateEntry.class.getName()),
				layoutPageTemplateEntryId);

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinksMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		for (int i = 0; i < structureJSONArray.length(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinksMap.get(
				structureJSONArray.getLong(i));

			if (fragmentEntryLink != null) {
				fragmentEntryLinksList.add(fragmentEntryLink);
			}
		}

		return fragmentEntryLinksList;
	}

	private long _getLayoutPageTemplateEntryId(
		long groupId, AssetEntry assetEntry) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK());

		if ((assetDisplayPageEntry == null) ||
			(assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_NONE)) {

			return 0;
		}

		if (assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return assetDisplayPageEntry.getLayoutPageTemplateEntryId();
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			_layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				groupId, assetEntry.getClassNameId(),
				assetEntry.getClassTypeId());

		if (defaultLayoutPageTemplateEntry != null) {
			return defaultLayoutPageTemplateEntry.
				getLayoutPageTemplateEntryId();
		}

		return 0;
	}

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}" +
			"&p_v_l_s_g_id=${liferay:pvlsgid}";

	private static final String _VIEW_PAGE = "/layout/view/asset_display.jsp";

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}