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

package com.liferay.asset.taglib.servlet.taglib.soy;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolverUtil;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.AUIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Chema Balsas
 */
public class AssetTagsSelectorTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			putValue("eventName", _getEventName());
			putValue("groupIds", _getGroupIds());
			putValue("id", _getNamespace() + _getId() + "assetTagsSelector");
			putValue("inputName", _getInputName());
			putValue("portletURL", _getPortletURL());

			List<Map<String, String>> selectedItems = new ArrayList<>();

			List<String> tagNames = StringUtil.split(_getTagNames());

			for (String tagName : tagNames) {
				Map<String, String> item = new HashMap<>();

				item.put("label", tagName);
				item.put("value", tagName);

				selectedItems.add(item);
			}

			putValue("selectedItems", selectedItems);

			putValue("showSelectButton", _showSelectButton);

			String pathThemeImages = themeDisplay.getPathThemeImages();

			putValue("spritemap", pathThemeImages.concat("/clay/icons.svg"));

			putValue("tagNames", _getTagNames());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		setTemplateNamespace(
			"com.liferay.asset.taglib.AssetTagsSelector.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return NPMResolverHolder._npmResolver.resolveModuleName(
			"asset-taglib/asset_tags_selector/AssetTagsSelector.es");
	}

	public void setAddCallback(String addCallback) {
		putValue("addCallback", _getNamespace() + addCallback);
	}

	public void setAllowAddEntry(boolean allowAddEntry) {
		putValue("allowAddEntry", allowAddEntry);
	}

	public void setAutoFocus(boolean autoFocus) {
		putValue("autoFocus", autoFocus);
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setGroupIds(long[] groupIds) {
		putValue("groupIds", groupIds);
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

	public void setRemoveCallback(String removeCallback) {
		putValue("removeCallback", _getNamespace() + removeCallback);
	}

	public void setShowSelectButton(boolean showSelectButton) {
		_showSelectButton = showSelectButton;
	}

	public void setTagNames(String tagNames) {
		_tagNames = tagNames;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_groupIds = null;
		_hiddenInput = "assetTagNames";
		_id = null;
		_ignoreRequestValue = false;
		_namespace = null;
		_showSelectButton = true;
		_tagNames = null;
	}

	private String _getEventName() {
		String portletId = PortletProviderUtil.getPortletId(
			AssetTag.class.getName(), PortletProvider.Action.BROWSE);

		return PortalUtil.getPortletNamespace(portletId) + "selectTag";
	}

	private long[] _getGroupIds() {
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

	private String _getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		String randomKey = PortalUtil.generateRandomKey(
			request, "taglib_ui_asset_tags_selector_page");

		return randomKey + StringPool.UNDERLINE;
	}

	private String _getInputName() {
		return _getNamespace() + _hiddenInput;
	}

	private String _getNamespace() {
		if (_namespace != null) {
			return _namespace;
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			_namespace = AUIUtil.getNamespace(request);

			return _namespace;
		}

		_namespace = AUIUtil.getNamespace(portletRequest, portletResponse);

		return _namespace;
	}

	private String _getPortletURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				request, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("eventName", _getEventName());
			portletURL.setParameter("selectedTagNames", "{selectedTagNames}");

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return null;
	}

	private String _getTagNames() {
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

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagsSelectorTag.class);

	private String _className;
	private long _classPK;
	private long[] _groupIds;
	private String _hiddenInput = "assetTagNames";
	private String _id;
	private boolean _ignoreRequestValue;
	private String _namespace;
	private Boolean _showSelectButton = true;
	private String _tagNames;

	private static class NPMResolverHolder {

		private static final NPMResolver _npmResolver =
			NPMResolverUtil.getNPMResolver(AssetCategoriesSelectorTag.class);

	}

}