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
String companyName = (String)request.getAttribute("liferay-flags:flags:companyName");
String contentTitle = (String)request.getAttribute("liferay-flags:flags:contentTitle");
String contentURL = (String)request.getAttribute("liferay-flags:flags:contentURL");
boolean enabled = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:enabled"), true);
String message = (String)request.getAttribute("liferay-flags:flags:message");
String id = StringUtil.randomId() + StringPool.UNDERLINE + "id";
String reportedUserId = (String)request.getAttribute("liferay-flags:flags:reportedUserId");
Map<String, String> reasons = (Map<String, String>)request.getAttribute("liferay-flags:flags:reasons");
boolean signedIn = (boolean)request.getAttribute("liferay-flags:flags:signedIn");
String uri = (String)request.getAttribute("liferay-flags:flags:uri");

String namespace = PortalUtil.getPortletNamespace(PortletKeys.FLAGS);

JSONObject dataJSONObject = JSONUtil.put(
		namespace + "className", className
	).put(
		namespace + "classPK", classPK
	).put(
		namespace + "contentTitle", contentTitle
	).put(
		namespace + "contentURL", contentURL
	).put(
		namespace + "reportedUserId", reportedUserId
	);

if (signedIn) {
	String reporterEmailAddress = (String)request.getAttribute("liferay-flags:flags:reporterEmailAddress");

	dataJSONObject.put(
		namespace + "reporterEmailAddress", reporterEmailAddress
	);
}

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<div id="<%= id %>"></div>

<aui:script require='<%= npmResolvedPackageName + "/flags/react/js/index.es as FlagsComponent" %>'>

	new FlagsComponent.default(
		'<%= id %>',
		{
			companyName: '<%= companyName %>',
			formData: <%= dataJSONObject %>,
			enabled: <%= enabled %>,
			<c:if test="<%= Validator.isNotNull(message) %>">
				message: '<%= message %>',
			</c:if>
			pathTermsOfUse: Liferay.ThemeDisplay.getPathMain() + '/portal/terms_of_use',
			namespace: '<%= namespace %>',
			spritemap: Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg',
			reasons: <%= jsonSerializer.serializeDeep(reasons) %>,
			signedIn: <%= signedIn %>,
			uri: '<%= uri %>'
		}
	);
</aui:script>