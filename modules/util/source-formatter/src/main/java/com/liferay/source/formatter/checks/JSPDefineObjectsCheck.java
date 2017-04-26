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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPDefineObjectsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatDefineObjects(content);

		_checkDefineObjectsVariables(fileName, absolutePath, content);

		return content;
	}

	private void _checkDefineObjectsVariables(
		String fileName, String absolutePath, String content) {

		for (String[] defineObject : _LIFERAY_THEME_DEFINE_OBJECTS) {
			_checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "liferay-theme");
		}

		for (String[] defineObject : _PORTLET_DEFINE_OBJECTS) {
			_checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "portlet");
		}

		if (!isPortalSource() && !isSubrepository()) {
			return;
		}

		for (String directoryName : getPluginsInsideModulesDirectoryNames()) {
			if (absolutePath.contains(directoryName)) {
				return;
			}
		}

		for (String[] defineObject : _LIFERAY_FRONTEND_DEFINE_OBJECTS) {
			_checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "liferay-frontend");
		}
	}

	private void _checkDefineObjectsVariables(
		String fileName, String content, String objectType, String variableName,
		String value, String tag) {

		int x = -1;

		while (true) {
			x = content.indexOf(
				objectType + " " + variableName + " = " + value + ";", x + 1);

			if (x == -1) {
				return;
			}

			int y = content.lastIndexOf("<%", x);

			if ((y == -1) ||
				(getLevel(content.substring(y, x), "{", "}") > 0)) {

				continue;
			}

			addMessage(
				fileName,
				"Use '" + tag + ":defineObjects' or rename var, see LPS-62493",
				getLineCount(content, x));
		}
	}

	private String _formatDefineObjects(String content) {
		Matcher matcher = _missingEmptyLineBetweenDefineOjbectsPattern.matcher(
			content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		String previousDefineObjectsTag = null;

		matcher = _defineObjectsPattern.matcher(content);

		while (matcher.find()) {
			String defineObjectsTag = matcher.group(1);

			if (Validator.isNotNull(previousDefineObjectsTag) &&
				(previousDefineObjectsTag.compareTo(defineObjectsTag) > 0)) {

				content = StringUtil.replaceFirst(
					content, previousDefineObjectsTag, defineObjectsTag);
				content = StringUtil.replaceLast(
					content, defineObjectsTag, previousDefineObjectsTag);

				return content;
			}

			previousDefineObjectsTag = defineObjectsTag;
		}

		return content;
	}

	private static final String[][] _LIFERAY_FRONTEND_DEFINE_OBJECTS =
		new String[][] {
			new String[] {"String", "currentURL", "currentURLObj.toString()"},
			new String[] {
				"PortletURL", "currentURLObj",
				"PortletURLUtil.getCurrent(liferayPortletRequest, " +
					"liferayPortletResponse)"
			},
			new String[] {
				"ResourceBundle", "resourceBundle",
				"ResourceBundleUtil.getBundle(\"content.Language\", locale, " +
					"getClass()"
			},
			new String[] {
				"WindowState", "windowState",
				"liferayPortletRequest.getWindowState()"
			}
		};

	private static final String[][] _LIFERAY_THEME_DEFINE_OBJECTS =
		new String[][] {
			new String[] {"Account", "account", "themeDisplay.getAccount()"},
			new String[] {
				"ColorScheme", "colorScheme", "themeDisplay.getColorScheme()"
			},
			new String[] {"Company", "company", "themeDisplay.getCompany()"},
			new String[] {"Contact", "contact", "themeDisplay.getContact()"},
			new String[] {"Layout", "layout", "themeDisplay.getLayout()"},
			new String[] {
				"List<Layout>", "layouts", "themeDisplay.getLayouts()"
			},
			new String[] {
				"LayoutTypePortlet", "layoutTypePortlet",
				"themeDisplay.getLayoutTypePortlet()"
			},
			new String[] {"Locale", "locale", "themeDisplay.getLocale()"},
			new String[] {
				"PermissionChecker", "permissionChecker",
				"themeDisplay.getPermissionChecker()"
			},
			new String[] {"long", "plid", "themeDisplay.getPlid()"},
			new String[] {
				"PortletDisplay", "portletDisplay",
				"themeDisplay.getPortletDisplay()"
			},
			new String[] {"User", "realUser", "themeDisplay.getRealUser()"},
			new String[] {
				"long", "scopeGroupId", "themeDisplay.getScopeGroupId()"
			},
			new String[] {"Theme", "theme", "themeDisplay.getTheme()"},
			new String[] {
				"ThemeDisplay", "themeDisplay",
				"(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY)"
			},
			new String[] {"TimeZone", "timeZone", "themeDisplay.getTimeZone()"},
			new String[] {"User", "user", "themeDisplay.getUser()"},
			new String[] {
				"long", "portletGroupId", "themeDisplay.getScopeGroupId()"
			}
		};

	private static final String[][] _PORTLET_DEFINE_OBJECTS = new String[][] {
		new String[] {
			"PortletConfig", "portletConfig",
			"(PortletConfig)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_CONFIG)"
		},
		new String[] {
			"String", "portletName", "portletConfig.getPortletName()"
		},
		new String[] {
			"LiferayPortletRequest", "liferayPortletRequest",
			"PortalUtil.getLiferayPortletRequest(portletRequest)"
		},
		new String[] {
			"PortletRequest", "actionRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "eventRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "renderRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "resourceRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletPreferences", "portletPreferences",
			"portletRequest.getPreferences()"
		},
		new String[] {
			"Map<String, String[]>", "portletPreferencesValues",
			"portletPreferences.getMap()"
		},
		new String[] {
			"PortletSession", "portletSession",
			"portletRequest.getPortletSession()"
		},
		new String[] {
			"Map<String, Object>", "portletSessionScope",
			"portletSession.getAttributeMap()"
		},
		new String[] {
			"LiferayPortletResponse", "liferayPortletResponse",
			"PortalUtil.getLiferayPortletResponse(portletResponse)"
		},
		new String[] {
			"PortletResponse", "actionResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "eventResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "renderResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "resourceResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		}
	};

	private final Pattern _defineObjectsPattern = Pattern.compile(
		"\n\t*(<.*:defineObjects />)(\n|$)");
	private final Pattern _missingEmptyLineBetweenDefineOjbectsPattern =
		Pattern.compile("<.*:defineObjects />\n<.*:defineObjects />\n");

}