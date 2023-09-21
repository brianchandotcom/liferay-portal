/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.portlet.display.template.util.PortletDisplayTemplateUtil;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.taglib.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.template.taglib.internal.servlet.ServletContextUtil;

import java.text.Collator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

	public List<Map<String, Object>> getDisplayStyles() {
		List<Map<String, Object>> displayStyles = new ArrayList<>();
		HttpServletRequest httpServletRequest = getRequest();

		if (_displayStyles != null) {
			for (String style : _displayStyles) {
				displayStyles.add(
					HashMapBuilder.<String, Object>put(
						"label", LanguageUtil.get(httpServletRequest, style)
					).put(
						"value", style
					).build());
			}
		}

		if (isShowEmptyOption()) {
			displayStyles.add(
				HashMapBuilder.<String, Object>put(
					"label", LanguageUtil.get(httpServletRequest, "default")
				).put(
					"value", "default"
				).build());
		}

		displayStyles.sort(
			Comparator.comparing(
				displayStyle -> (String)displayStyle.get("label")));

		return displayStyles;
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
			httpServletRequest, "templateSelectorProps",
			_getTemplateSelectorProps(httpServletRequest));
	}

	private Map<String, List<Map<String, Object>>> _getDDMTemplates(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			List<DDMTemplate> ddmTemplates =
				DDMTemplateLocalServiceUtil.getTemplates(
					_getGroupIds(themeDisplay.getScopeGroup()),
					PortalUtil.getClassNameId(getClassName()), 0L);

			List<DDMTemplate> ddmTemplatesFiltered = ListUtil.filter(
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
						if (_log.isDebugEnabled()) {
							_log.debug(exception);
						}

						return false;
					}

					return true;
				});

			List<Map<String, Object>> ddmTemplatesValues = new ArrayList<>();

			if (ddmTemplates != null) {
				for (DDMTemplate ddmTemplate : ddmTemplatesFiltered) {
					Group group = GroupLocalServiceUtil.fetchGroup(
						ddmTemplate.getGroupId());

					ddmTemplatesValues.add(
						HashMapBuilder.<String, Object>put(
							"groupId", ddmTemplate.getGroupId()
						).put(
							"label",
							ddmTemplate.getName(themeDisplay.getLocale())
						).put(
							"siteName",
							StringUtil.toLowerCase(group.getDescriptiveName())
						).put(
							"value",
							PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
								ddmTemplate.getTemplateKey()
						).build());
				}
			}

			Collator collator = CollatorUtil.getInstance(
				themeDisplay.getLocale());

			ddmTemplatesValues.sort(
				(map1, map2) -> {
					int siteNameComparison = collator.compare(
						MapUtil.getString(map1, "siteName"),
						MapUtil.getString(map2, "siteName"));

					if (siteNameComparison == 0) {
						return collator.compare(
							MapUtil.getString(map1, "label"),
							MapUtil.getString(map2, "label"));
					}

					return siteNameComparison;
				});

			Map<String, List<Map<String, Object>>> ddmTemplatesBySiteName =
				new HashMap<>();

			for (Map<String, Object> ddmTemplate : ddmTemplatesValues) {
				String siteName = (String)ddmTemplate.get("siteName");

				if (ddmTemplatesBySiteName.containsKey(siteName)) {
					List<Map<String, Object>> innerList =
						ddmTemplatesBySiteName.get(siteName);

					innerList.add(ddmTemplate);
				}
				else {
					ddmTemplatesBySiteName.put(
						siteName, ListUtil.fromArray(ddmTemplate));
				}
			}

			return TreeMapBuilder.<String, List<Map<String, Object>>>create(
				String.CASE_INSENSITIVE_ORDER
			).putAll(
				ddmTemplatesBySiteName
			).build();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return new TreeMap<>();
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

		return PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId);
	}

	private Map<String, Object> _getTemplateSelectorProps(
		HttpServletRequest httpServletRequest) {

		Map<String, List<Map<String, Object>>> ddmTemplates = _getDDMTemplates(
			httpServletRequest);

		List<Map<String, Object>> getDisplayStyles = getDisplayStyles();

		List<Map<String, Object>> templateSelectorItems = new ArrayList<>();

		if (!getDisplayStyles.isEmpty()) {
			templateSelectorItems.add(
				HashMapBuilder.<String, Object>put(
					"items", getDisplayStyles
				).put(
					"label", LanguageUtil.get(httpServletRequest, "default")
				).build());
		}

		for (Map.Entry<String, List<Map<String, Object>>> ddmTemplate :
				ddmTemplates.entrySet()) {

			templateSelectorItems.add(
				HashMapBuilder.<String, Object>put(
					"items", ddmTemplate.getValue()
				).put(
					"label", ddmTemplate.getKey()
				).build());
		}

		return HashMapBuilder.<String, Object>put(
			"displayStyle", getDisplayStyle()
		).put(
			"displayStyleGroupId",
			() -> {
				DDMTemplate portletDisplayDDMTemplate =
					getPortletDisplayDDMTemplate();

				if (portletDisplayDDMTemplate != null) {
					return portletDisplayDDMTemplate.getGroupId();
				}

				return getDisplayStyleGroupId();
			}
		).put(
			"items", templateSelectorItems
		).build();
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