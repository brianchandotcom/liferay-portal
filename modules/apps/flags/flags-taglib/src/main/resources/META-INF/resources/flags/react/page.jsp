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
String message = (String)request.getAttribute("liferay-flags:flags:message");
boolean onlyIcon = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:onlyIcon"));
Map<String, String> reasons = (Map<String, String>)request.getAttribute("liferay-flags:flags:reasons");
boolean signedIn = (boolean)request.getAttribute("liferay-flags:flags:signedIn");
String uri = (String)request.getAttribute("liferay-flags:flags:uri");

if (Validator.isNull(message)) {
	message = LanguageUtil.get(resourceBundle, "report");
}
%>

<div class="taglib-flags <%= Validator.isNotNull(elementClasses) ? elementClasses : "" %>" id="<%= id %>">
	<c:choose>
		<c:when test="<%= onlyIcon %>">
			<clay:button
				disabled="<%= true %>"
				elementClasses="btn-outline-borderless btn-outline-secondary lfr-portal-tooltip"
				icon="flag-empty"
				monospaced="<%= true %>"
				size="sm"
				style="secondary"
				title="<%= message %>"
			/>
		</c:when>
		<c:otherwise>
			<clay:button
				disabled="<%= true %>"
				elementClasses="btn-outline-borderless btn-outline-secondary"
				icon="flag-empty"
				label="<%= message %>"
				size="sm"
				style="secondary"
			/>
		</c:otherwise>
	</c:choose>

	<%
	Map<String, Object> context = new HashMap<>();
	context.put("namespace", PortalUtil.getPortletNamespace(PortletKeys.FLAGS));

	Map<String, Object> props = new HashMap<>();
	props.put("baseData", dataJSONObject);
	props.put("companyName", companyName);
	props.put("disabled", !enabled);
	props.put("forceLogin", !flagsEnabled);

	if (Validator.isNotNull(message)) {
		props.put("message", message);
	}

	props.put("onlyIcon", onlyIcon);
	props.put("pathTermsOfUse", PortalUtil.getPathMain() + "/portal/terms_of_use");
	props.put("reasons", reasons);
	props.put("signedIn", signedIn);
	props.put("uri", uri);

	Map<String, Object> data = new HashMap<>();
	data.put("props", props);
	data.put("context", context);
	%>

	<react:component
		data="<%= data %>"
		module="flags/react/js/index.es"
	/>
</div>