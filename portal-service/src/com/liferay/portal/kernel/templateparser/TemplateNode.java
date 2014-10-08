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

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class TemplateNode extends LinkedHashMap<String, Object> {

	public TemplateNode(
		ThemeDisplay themeDisplay, String name, String data, String type,
		Map<String, String> attributes) {

		_themeDisplay = themeDisplay;

		put("attributes", attributes);
		put("name", name);
		put("data", data);
		put("type", type);
		put("options", new ArrayList<String>());
	}

	public void appendChild(TemplateNode templateNode) {
		_childTemplateNodes.put(templateNode.getName(), templateNode);

		put(templateNode.getName(), templateNode);
	}

	public void appendChildren(List<TemplateNode> templateNodes) {
		for (TemplateNode templateNode : templateNodes) {
			appendChild(templateNode);
		}
	}

	public void appendOption(String option) {
		List<String> options = getOptions();

		options.add(option);
	}

	public void appendOptions(List<String> options) {
		List<String> curOptions = getOptions();

		curOptions.addAll(options);
	}

	public void appendSibling(TemplateNode templateNode) {
		_siblingTemplateNodes.add(templateNode);
	}

	public String getAttribute(String name) {
		Map<String, String> attributes = getAttributes();

		if (attributes == null) {
			return StringPool.BLANK;
		}

		return attributes.get(name);
	}

	public Map<String, String> getAttributes() {
		return (Map<String, String>)get("attributes");
	}

	public TemplateNode getChild(String name) {
		return _childTemplateNodes.get(name);
	}

	public List<TemplateNode> getChildren() {
		return new ArrayList<TemplateNode>(_childTemplateNodes.values());
	}

	public String getData() {
		String type = getType();

		if (type.equals("link_to_layout")) {
			String data = (String)get("data");

			int pos = data.indexOf(CharPool.AT);

			if (pos != -1) {
				data = data.substring(0, pos);
			}

			return data;
		}
		else {
			return (String)get("data");
		}
	}

	public String getFriendlyUrl() {
		if (_themeDisplay == null) {
			return getUrl();
		}

		String type = getType();

		if (!type.equals("link_to_layout")) {
			return StringPool.BLANK;
		}

		String layoutType = getLayoutType();

		if (Validator.isNull(layoutType)) {
			return StringPool.BLANK;
		}

		long groupId = getLayoutGroupId();

		if (groupId == 0) {
			groupId = _themeDisplay.getScopeGroupId();
		}

		boolean privateLayout = layoutType.startsWith("private");

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, getLayoutId());

			return PortalUtil.getLayoutFriendlyURL(layout, _themeDisplay);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Error finding friendly URL on page " +
						_themeDisplay.getURLCurrent(),
					e);
			}

			return getUrl();
		}
	}

	public String getName() {
		return (String)get("name");
	}

	public List<String> getOptions() {
		return (List<String>)get("options");
	}

	public List<TemplateNode> getSiblings() {
		return _siblingTemplateNodes;
	}

	public String getType() {
		return (String)get("type");
	}

	public String getUrl() {
		String type = getType();

		if (!type.equals("link_to_layout")) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		String layoutType = getLayoutType();

		if (Validator.isNull(layoutType)) {
			return StringPool.BLANK;
		}

		if (layoutType.equals(_LAYOUT_TYPE_PRIVATE_GROUP)) {
			sb.append(PortalUtil.getPathFriendlyURLPrivateGroup());
		}
		else if (layoutType.equals(_LAYOUT_TYPE_PRIVATE_USER)) {
			sb.append(PortalUtil.getPathFriendlyURLPrivateUser());
		}
		else if (layoutType.equals(_LAYOUT_TYPE_PUBLIC)) {
			sb.append(PortalUtil.getPathFriendlyURLPublic());
		}
		else {
			sb.append("@friendly_url_current@");
		}

		sb.append(StringPool.SLASH);

		try {
			Group group = GroupLocalServiceUtil.getGroup(getLayoutGroupId());

			String name = group.getFriendlyURL();

			name = name.substring(1);

			sb.append(name);
		}
		catch (Exception e) {
			sb.append("@group_id@");
		}

		sb.append(StringPool.SLASH);
		sb.append(getLayoutId());

		return sb.toString();
	}

	protected long getLayoutGroupId() {
		String data = (String)get("data");

		int pos = data.lastIndexOf(CharPool.AT);

		if (pos != -1) {
			data = data.substring(pos + 1);
		}

		return GetterUtil.getLong(data);
	}

	protected long getLayoutId() {
		String data = (String)get("data");

		int pos = data.indexOf(CharPool.AT);

		if (pos != -1) {
			data = data.substring(0, pos);
		}

		return GetterUtil.getLong(data);
	}

	protected String getLayoutType() {
		String data = (String)get("data");

		int x = data.indexOf(CharPool.AT);
		int y = data.lastIndexOf(CharPool.AT);

		if ((x != -1) && (y != -1)) {
			if (x == y) {
				data = data.substring(x + 1);
			}
			else {
				data = data.substring(x + 1, y);
			}
		}

		return data;
	}

	private static final String _LAYOUT_TYPE_PRIVATE_GROUP = "private-group";

	private static final String _LAYOUT_TYPE_PRIVATE_USER = "private-user";

	private static final String _LAYOUT_TYPE_PUBLIC = "public";

	private static final Log _log = LogFactoryUtil.getLog(TemplateNode.class);

	private final Map<String, TemplateNode> _childTemplateNodes =
		new LinkedHashMap<String, TemplateNode>();
	private final List<TemplateNode> _siblingTemplateNodes =
		new ArrayList<TemplateNode>();
	private ThemeDisplay _themeDisplay;

}