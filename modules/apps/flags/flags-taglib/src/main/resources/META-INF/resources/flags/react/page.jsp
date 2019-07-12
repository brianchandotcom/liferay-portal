<%--
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
--%>

<%@ include file="/flags/react/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-flags:flags:className");
long classPK = GetterUtil.getLong(request.getAttribute("liferay-flags:flags:classPK"));
String contentTitle = (String)request.getAttribute("liferay-flags:flags:contentTitle");


String namespace = PortalUtil.getPortletNamespace(PortletKeys.FLAGS);

JSONObject dataJSONObject = JSONUtil.put(
		namespace + "className", className
	).put(
		namespace + "classPK", classPK
	).put(
		namespace + "contentTitle", contentTitle
	).put(
		namespace + "contentURL", "TODO contentURL"
	).put(
		namespace + "reportedUserId", "TODO reportedUserId"
	);

boolean enabled = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:enabled"), true);

//TODO Get all the data required

//ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
%>

<div id="testFlagsRect"></div>

<aui:script require='<%= npmResolvedPackageName + "/flags/react/js/index.es as FlagsComponent" %>'>
	new FlagsComponent.default(
		'testFlagsRect',
		{
			className: '<%= className %>',
			dataJSONObject: <%= dataJSONObject %>,
			enabled: <%= enabled %>,
			message: 'Report',
			reasons: {
				'harmful-dangerous-acts': 'Harmful Dangerous Acts',
				'infringes-my-rights': 'Infringes My Rights',
				'sexual-content': 'Sexual Content',
				'hateful-or-abusive-content': 'Hateful or Abusive Content',
				'spam': 'Spam',
				'violent-or-repulsive-content': 'Violent or Repulsive Content'
			},
			urlTermsOfUse: 'https://google.com',
			spritemap: Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg'
		}
	);
</aui:script>