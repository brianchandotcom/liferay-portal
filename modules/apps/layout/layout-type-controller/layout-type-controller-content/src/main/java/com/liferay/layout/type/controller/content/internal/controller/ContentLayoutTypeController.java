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

package com.liferay.layout.type.controller.content.internal.controller;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.type.controller.content.internal.constants.ContentLayoutTypeControllerConstants;
import com.liferay.layout.type.controller.content.internal.constants.ContentLayoutTypeControllerWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.impl.BaseLayoutTypeControllerImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.TransferHeadersHelperUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
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

import javax.servlet.RequestDispatcher;
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
	property = "layout.type=" + ContentLayoutTypeControllerConstants.LAYOUT_TYPE_CONTENT,
	service = LayoutTypeController.class
)
public class ContentLayoutTypeController extends BaseLayoutTypeControllerImpl {

	@Override
	public String getType() {
		return LayoutConstants.TYPE_PORTLET;
	}

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public String includeEditContent(
		HttpServletRequest request, HttpServletResponse response,
		Layout layout) {

		return StringPool.BLANK;
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutMode = ParamUtil.getString(
			request, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT) &&
			!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				ActionKeys.UPDATE)) {

			layoutMode = Constants.VIEW;
		}

		if (layoutMode.equals(Constants.VIEW)) {
			List<FragmentEntryLink> fragmentEntryLinks = _getFragmentEntryLinks(
				layout);

			request.setAttribute(
				ContentLayoutTypeControllerWebKeys.LAYOUT_FRAGMENTS,
				fragmentEntryLinks);
		}
		else {
			request.setAttribute(
				ContentLayoutTypeControllerWebKeys.ITEM_SELECTOR,
				_itemSelector);
		}

		String page = getViewPage();

		if (layoutMode.equals(Constants.EDIT)) {
			page = _EDIT_LAYOUT_PAGE;
		}

		RequestDispatcher requestDispatcher =
			TransferHeadersHelperUtil.getTransferHeadersRequestDispatcher(
				servletContext.getRequestDispatcher(page));

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			response, unsyncStringWriter);

		String contentType = servletResponse.getContentType();

		String includeServletPath = (String)request.getAttribute(
			RequestDispatcher.INCLUDE_SERVLET_PATH);

		try {
			addAttributes(request);

			requestDispatcher.include(request, servletResponse);
		}
		finally {
			removeAttributes(request);

			request.setAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH, includeServletPath);
		}

		if (contentType != null) {
			response.setContentType(contentType);
		}

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());

		return false;
	}

	@Override
	public boolean isBrowsable() {
		return true;
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return false;
	}

	@Override
	public boolean isParentable() {
		return true;
	}

	@Override
	public boolean isPrimaryType() {
		return true;
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
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.content)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private List<FragmentEntryLink> _getFragmentEntryLinks(Layout layout)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layout.getPlid(), true);

		String data = layoutPageTemplateStructure.getData();

		if (Validator.isNull(data)) {
			return Collections.emptyList();
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutPageTemplateStructure.getData());

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return Collections.emptyList();
		}

		List<FragmentEntryLink> filteredFragmentEntryLinks = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				layout.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid());

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinksMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		for (int i = 0; i < structureJSONArray.length(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinksMap.get(
				structureJSONArray.getLong(i));

			if (fragmentEntryLink != null) {
				filteredFragmentEntryLinks.add(fragmentEntryLink);
			}
		}

		return filteredFragmentEntryLinks;
	}

	private static final String _EDIT_LAYOUT_PAGE =
		"/layout/edit_layout/content.jsp";

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}" +
			"&p_v_l_s_g_id=${liferay:pvlsgid}";

	private static final String _VIEW_PAGE = "/layout/view/content.jsp";

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}