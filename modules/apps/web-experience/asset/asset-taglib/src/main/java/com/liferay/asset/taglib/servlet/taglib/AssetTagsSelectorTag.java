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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Antonio Pol
 */
public class AssetTagsSelectorTag extends IncludeTag {

	public void setAddCallback(String addCallback) {
		_addCallback = addCallback;
	}

	public void setAllowAddEntry(boolean allowAddEntry) {
		_allowAddEntry = allowAddEntry;
	}

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setRemoveCallback(String removeCallback) {
		_removeCallback = removeCallback;
	}

	public void setTagNames(String tagNames) {
		_tagNames = tagNames;
	}

	@Override
	protected void cleanUp() {
		_addCallback = null;
		_allowAddEntry = true;
		_autoFocus = false;
		_className = null;
		_classPK = 0;
		_groupIds = null;
		_hiddenInput = "assetTagNames";
		_id = null;
		_ignoreRequestValue = false;
		_removeCallback = null;
		_tagNames = null;
	}

	protected String getEventName() {
		String portletId = PortletProviderUtil.getPortletId(
			AssetTag.class.getName(), PortletProvider.Action.BROWSE);

		return PortalUtil.getPortletNamespace(portletId) + "selectTag";
	}

	protected long[] getGroupIds() {
		if (_groupIds != null) {
			return _groupIds;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = null;

		Group group = themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			groupIds = new long[] {group.getParentGroupId()};
		}
		else {
			groupIds = new long[] {group.getGroupId()};
		}

		if (group.getParentGroupId() != themeDisplay.getCompanyGroupId()) {
			groupIds = ArrayUtil.append(
				groupIds, themeDisplay.getCompanyGroupId());
		}

		return groupIds;
	}

	protected String getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		String randomKey = PortalUtil.generateRandomKey(
			request, "taglib_ui_asset_tags_selector_page");

		return randomKey + StringPool.UNDERLINE;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected PortletURL getPortletURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				request, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("eventName", getEventName());
			portletURL.setParameter("selectedTags", "{selectedTags}");

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL;
		}
		catch (Exception e) {
		}

		return null;
	}

	protected String getTagNames() {
		String tagNames = _tagNames;

		if (Validator.isNotNull(_className) && (_classPK > 0)) {
			List<AssetTag> tags = AssetTagServiceUtil.getTags(
				_className, _classPK);

			tagNames = ListUtil.toString(tags, AssetTag.NAME_ACCESSOR);
		}

		if (!_ignoreRequestValue) {
			String curTagsParam = request.getParameter(_hiddenInput);

			if (Validator.isNotNull(curTagsParam)) {
				tagNames = curTagsParam;
			}
		}

		return tagNames;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-asset:asset-tags-selector:addCallback",
			String.valueOf(_addCallback));
		request.setAttribute(
			"liferay-asset:asset-tags-selector:allowAddEntry",
			String.valueOf(_allowAddEntry));
		request.setAttribute(
			"liferay-asset:asset-tags-selector:autoFocus",
			String.valueOf(_autoFocus));
		request.setAttribute(
			"liferay-asset:asset-tags-selector:eventName", getEventName());
		request.setAttribute(
			"liferay-asset:asset-tags-selector:groupIds", getGroupIds());
		request.setAttribute(
			"liferay-asset:asset-tags-selector:hiddenInput", _hiddenInput);
		request.setAttribute("liferay-asset:asset-tags-selector:id", getId());
		request.setAttribute(
			"liferay-asset:asset-tags-selector:portletURL", getPortletURL());
		request.setAttribute(
			"liferay-asset:asset-tags-selector:removeCallback",
			String.valueOf(_removeCallback));
		request.setAttribute(
			"liferay-asset:asset-tags-selector:tagNames", getTagNames());
	}

	private static final String _PAGE = "/asset_tags_selector/page.jsp";

	private String _addCallback;
	private boolean _allowAddEntry = true;
	private boolean _autoFocus;
	private String _className;
	private long _classPK;
	private long[] _groupIds;
	private String _hiddenInput = "assetTagNames";
	private String _id;
	private boolean _ignoreRequestValue;
	private String _removeCallback;
	private String _tagNames;

}