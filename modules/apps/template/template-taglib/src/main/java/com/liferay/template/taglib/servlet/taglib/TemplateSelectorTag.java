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

package com.liferay.template.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.taglib.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.template.taglib.internal.servlet.ServletContextUtil;
import com.liferay.template.taglib.internal.util.PortletDisplayTemplateUtil;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class TemplateSelectorTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public String getDefaultDisplayStyle() {
		return _defaultDisplayStyle;
	}

	public String getDisplayStyle() {
		DDMTemplate portletDisplayDDMTemplate = getPortletDisplayDDMTemplate();

		if (portletDisplayDDMTemplate != null) {
			return PortletDisplayTemplateUtil.getDisplayStyle(
				portletDisplayDDMTemplate.getTemplateKey());
		}

		if (Validator.isNull(_displayStyle)) {
			return getDefaultDisplayStyle();
		}

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	public List<String> getDisplayStyles() {
		return _displayStyles;
	}

	public String getRefreshURL() {
		return _refreshURL;
	}

	public boolean isShowEmptyOption() {
		return _showEmptyOption;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDefaultDisplayStyle(String defaultDisplayStyle) {
		_defaultDisplayStyle = defaultDisplayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setDisplayStyles(List<String> displayStyles) {
		_displayStyles = displayStyles;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setRefreshURL(String refreshURL) {
		_refreshURL = refreshURL;
	}

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_defaultDisplayStyle = StringPool.BLANK;
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_displayStyles = null;
		_refreshURL = null;
		_showEmptyOption = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected DDMTemplate getPortletDisplayDDMTemplate() {
		String displayStyle = _displayStyle;

		if (Validator.isNull(displayStyle)) {
			displayStyle = _defaultDisplayStyle;
		}

		return PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplate(
			getDisplayStyleGroupId(), PortalUtil.getClassNameId(getClassName()),
			displayStyle, true);
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(
			httpServletRequest, "classNameId",
			String.valueOf(PortalUtil.getClassNameId(getClassName())));
		setNamespacedAttribute(
			httpServletRequest, "defaultDisplayStyle",
			getDefaultDisplayStyle());
		setNamespacedAttribute(
			httpServletRequest, "ddmTemplates",
			_getDDMTemplates(httpServletRequest));
		setNamespacedAttribute(
			httpServletRequest, "displayStyle", getDisplayStyle());
		setNamespacedAttribute(
			httpServletRequest, "displayStyleGroupId",
			getDisplayStyleGroupId());
		setNamespacedAttribute(
			httpServletRequest, "displayStyles", getDisplayStyles());
		setNamespacedAttribute(
			httpServletRequest, "refreshURL", getRefreshURL());
		setNamespacedAttribute(
			httpServletRequest, "portletDisplayDDMTemplate",
			getPortletDisplayDDMTemplate());
		setNamespacedAttribute(
			httpServletRequest, "showEmptyOption", isShowEmptyOption());
	}

	private List<DDMTemplate> _getDDMTemplates(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			List<DDMTemplate> ddmTemplates =
				DDMTemplateLocalServiceUtil.getTemplates(
					_getGroupIds(themeDisplay.getScopeGroup()),
					PortalUtil.getClassNameId(getClassName()), 0L);

			return ListUtil.filter(
				ddmTemplates,
				ddmTemplate -> {
					try {
						if (!DDMTemplatePermission.contains(
								themeDisplay.getPermissionChecker(),
								ddmTemplate.getTemplateId(), ActionKeys.VIEW) ||
							!DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY.equals(
								ddmTemplate.getType())) {

							return false;
						}
					}
					catch (Exception exception) {
						return false;
					}

					return true;
				});
		}
		catch (Exception exception) {
			return Collections.emptyList();
		}
	}

	private long[] _getGroupIds(Group group) {
		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		long groupId = group.getGroupId();

		if (group.isStagingGroup()) {
			Group liveGroup = group.getLiveGroup();

			if (!liveGroup.isStagedPortlet(TemplatePortletKeys.TEMPLATE)) {
				groupId = liveGroup.getGroupId();
			}
		}

		try {
			return PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return new long[] {groupId};
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-template:template-selector:";

	private static final String _PAGE = "/template_selector/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateSelectorTag.class);

	private String _className;
	private String _defaultDisplayStyle = StringPool.BLANK;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<String> _displayStyles;
	private String _refreshURL;
	private boolean _showEmptyOption;

}