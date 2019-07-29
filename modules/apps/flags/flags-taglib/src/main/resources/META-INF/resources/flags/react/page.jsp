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
String companyName = (String)request.getAttribute("liferay-flags:flags:companyName");
JSONObject dataJSONObject = (JSONObject)request.getAttribute("liferay-flags:flags:dataJSONObject");
String elementClasses = (String)request.getAttribute("liferay-flags:flags:elementClasses");
boolean enabled = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:enabled"), true);
boolean flagsEnabled = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:flagsEnabled"), true);
String id = StringUtil.randomId() + StringPool.UNDERLINE + "id";
boolean label = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:label"), true);
String message = (String)request.getAttribute("liferay-flags:flags:message");
Map<String, String> reasons = (Map<String, String>)request.getAttribute("liferay-flags:flags:reasons");
boolean signedIn = (boolean)request.getAttribute("liferay-flags:flags:signedIn");
String uri = (String)request.getAttribute("liferay-flags:flags:uri");
%>

<div class="taglib-flags <%= Validator.isNotNull(elementClasses) ? elementClasses : "" %>" id="<%= id %>">
</div>

<aui:script require='<%= npmResolvedPackageName + "/flags/react/js/index.es as FlagsComponent" %>'>

	new FlagsComponent.default(
		'<%= id %>',
		{
			baseData: <%= dataJSONObject %>,
			companyName: '<%= companyName %>',
			disabled: <%= !enabled %>,
			forceLogin: <%= !flagsEnabled %>,
			<c:if test="<%= Validator.isNotNull(message) %>">
				message: '<%= message %>',
			</c:if>
			onlyIcon: <%= !label %>,
			pathTermsOfUse: Liferay.ThemeDisplay.getPathMain() + '/portal/terms_of_use',
			reasons: <%= JSONFactoryUtil.looseSerializeDeep(reasons) %>,
			signedIn: <%= signedIn %>,
			uri: '<%= uri %>'
		},
		{
			namespace: '<%= PortalUtil.getPortletNamespace(PortletKeys.FLAGS) %>',
			spritemap: Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg'
		}
	);
</aui:script>