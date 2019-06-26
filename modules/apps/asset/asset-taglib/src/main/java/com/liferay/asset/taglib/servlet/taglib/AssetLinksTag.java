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

package com.liferay.asset.taglib.servlet.taglib;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Juan Fernández
 * @author Shuyang Zhou
 */
public class AssetLinksTag extends IncludeTag {

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public boolean getViewInContext() {
		return _viewInContext;
	}

	public boolean isViewInContext() {
		return _viewInContext;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setViewInContext(boolean viewInContext) {
		_viewInContext = viewInContext;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_assetEntryId = 0;
		_className = StringPool.BLANK;
		_classPK = 0;
		_page = _PAGE;
		_portletURL = null;
		_viewInContext = true;
	}

	@Override
	protected String getPage() {
		return _page;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		if (_page == null) {
			return;
		}

		if ((_assetEntryId <= 0) && (_classPK > 0)) {
			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
					_className, _classPK);

				if (assetEntry != null) {
					_assetEntryId = assetEntry.getEntryId();
				}
			}
			catch (SystemException se) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
		}

		if (_assetEntryId <= 0) {
			_page = null;

			return;
		}

		List<Tuple> assetLinkEntries = null;

		try {
			assetLinkEntries = _getAssetLinkEntries();
		}
		catch (Exception e) {
		}

		if (ListUtil.isEmpty(assetLinkEntries)) {
			_page = null;

			return;
		}

		httpServletRequest.setAttribute(
			"liferay-asset:asset-links:assetLinkEntries", assetLinkEntries);
	}

	private List<Tuple> _getAssetLinkEntries() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(portletRequest);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		List<Tuple> assetLinkEntries = new ArrayList<>();

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getDirectLinks(
			_assetEntryId);

		for (AssetLink assetLink : assetLinks) {
			AssetEntry assetLinkEntry = null;

			if (assetLink.getEntryId1() == _assetEntryId) {
				assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(
					assetLink.getEntryId2());
			}
			else {
				assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(
					assetLink.getEntryId1());
			}

			if (!assetLinkEntry.isVisible()) {
				continue;
			}

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(
						assetLinkEntry.getClassNameId());

			if (assetRendererFactory == null) {
				if (_log.isWarnEnabled()) {
					String className = PortalUtil.getClassName(
						assetLinkEntry.getClassNameId());

					_log.warn(
						"No asset renderer factory found for class " +
							className);
				}

				continue;
			}

			if (!assetRendererFactory.isActive(themeDisplay.getCompanyId())) {
				continue;
			}

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					assetLinkEntry.getClassPK());

			if (!assetRenderer.hasViewPermission(
					themeDisplay.getPermissionChecker())) {

				continue;
			}

			Group group = GroupLocalServiceUtil.getGroup(
				assetLinkEntry.getGroupId());

			Group scopeGroup = themeDisplay.getScopeGroup();

			if (group.isStaged() &&
				(group.isStagingGroup() ^ scopeGroup.isStagingGroup())) {

				continue;
			}

			String viewURL = _getViewURL(
				assetLinkEntry, assetRenderer, assetRendererFactory.getType(),
				liferayPortletRequest, liferayPortletResponse, themeDisplay);

			assetLinkEntries.add(new Tuple(assetLinkEntry, viewURL));
		}

		return assetLinkEntries;
	}

	private String _getViewURL(
			AssetEntry assetLinkEntry, AssetRenderer assetRenderer, String type,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			ThemeDisplay themeDisplay)
		throws Exception {

		PortletURL viewAssetURL = null;

		if (_portletURL != null) {
			viewAssetURL = PortletURLUtil.clone(
				_portletURL, liferayPortletResponse);
		}
		else {
			viewAssetURL = PortletProviderUtil.getPortletURL(
				request, assetRenderer.getClassName(),
				PortletProvider.Action.VIEW);

			viewAssetURL.setParameter("redirect", themeDisplay.getURLCurrent());
			viewAssetURL.setWindowState(WindowState.MAXIMIZED);
		}

		viewAssetURL.setParameter(
			"assetEntryId", String.valueOf(assetLinkEntry.getEntryId()));
		viewAssetURL.setParameter("type", type);

		String urlTitle = assetRenderer.getUrlTitle(themeDisplay.getLocale());

		if (Validator.isNotNull(urlTitle)) {
			if (assetRenderer.getGroupId() != themeDisplay.getSiteGroupId()) {
				viewAssetURL.setParameter(
					"groupId", String.valueOf(assetRenderer.getGroupId()));
			}

			viewAssetURL.setParameter("urlTitle", urlTitle);
		}

		String viewURL = null;

		if (_viewInContext) {
			String noSuchEntryRedirect = viewAssetURL.toString();

			String urlViewInContext = assetRenderer.getURLViewInContext(
				liferayPortletRequest, liferayPortletResponse,
				noSuchEntryRedirect);

			if (Validator.isNotNull(urlViewInContext) &&
				!Objects.equals(urlViewInContext, noSuchEntryRedirect)) {

				urlViewInContext = HttpUtil.setParameter(
					urlViewInContext, "inheritRedirect", Boolean.TRUE);
				urlViewInContext = HttpUtil.setParameter(
					urlViewInContext, "redirect", themeDisplay.getURLCurrent());
			}

			viewURL = urlViewInContext;
		}

		if (Validator.isNull(viewURL)) {
			viewURL = viewAssetURL.toString();
		}

		return viewURL;
	}

	private static final String _PAGE = "/asset_links/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(AssetLinksTag.class);

	private long _assetEntryId;
	private String _className = StringPool.BLANK;
	private long _classPK;
	private String _page = _PAGE;
	private PortletURL _portletURL;
	private boolean _viewInContext = true;

}