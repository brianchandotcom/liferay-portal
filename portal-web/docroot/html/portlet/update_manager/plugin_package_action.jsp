<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/update_manager/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] rowObj = (Object[])row.getObject();

PluginPackage pluginPackage = (PluginPackage)rowObj[0];
PluginPackage availablePluginPackage = (PluginPackage)rowObj[1];
String pluginPackageStatus = (String)rowObj[2];
String uploadProgressId = (String)rowObj[3];
String redirect = (String)rowObj[4];

String downloadURL = StringPool.BLANK;

if (availablePluginPackage != null) {
	downloadURL = availablePluginPackage.getDownloadURL();
}
%>

<liferay-ui:icon-menu>
	<c:if test='<%= pluginPackageStatus.equals("update-available") || pluginPackageStatus.equals("update-ignored") %>'>
		<portlet:actionURL var="updateURL">
			<portlet:param name="struts_action" value="/update_manager/install_plugin" />
			<portlet:param name="<%= Constants.CMD %>" value="remoteDeploy" />
			<portlet:param name="<%= Constants.PROGRESS_ID %>" value="<%= uploadProgressId %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="url" value="<%= downloadURL %>" />
		</portlet:actionURL>

		<%
		String taglibUpdateURL = "javascript:" + uploadProgressId + ".startProgress(); submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(updateURL) + "');";
		%>

		<liferay-ui:icon
			image="download"
			message="update"
			url="<%= taglibUpdateURL %>"
		/>

		<c:choose>
			<c:when test="<%= !PluginPackageUtil.isIgnored(pluginPackage) %>">
				<portlet:actionURL var="ignoreURL">
					<portlet:param name="struts_action" value="/update_manager/install_plugin" />
					<portlet:param name="<%= Constants.CMD %>" value="ignorePackages" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="pluginPackagesIgnored" value="<%= pluginPackage.getPackageId() %>" />
				</portlet:actionURL>

				<%
				String taglibIgnoreURL = "javascript:submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(ignoreURL) + "');";
				%>

				<liferay-ui:icon
					image="unsubscribe"
					message="ignore"
					url="<%= taglibIgnoreURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="unignoreURL">
					<portlet:param name="struts_action" value="/update_manager/install_plugin" />
					<portlet:param name="<%= Constants.CMD %>" value="unignorePackages" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="pluginPackagesUnignored" value="<%= pluginPackage.getPackageId() %>" />
				</portlet:actionURL>

				<%
				String taglibUnignoreURL = "javascript:submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(unignoreURL) + "');";
				%>

				<liferay-ui:icon
					image="subscribe"
					message="unignore"
					url="<%= taglibUnignoreURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= PrefsPropsUtil.getBoolean(PropsKeys.HOT_UNDEPLOY_ENABLED, PropsValues.HOT_UNDEPLOY_ENABLED) %>">
		<portlet:actionURL var="uninstallURL">
			<portlet:param name="struts_action" value="/update_manager/install_plugin" />
			<portlet:param name="<%= Constants.CMD %>" value="uninstall" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="deploymentContext" value="<%= pluginPackage.getContext() %>" />
		</portlet:actionURL>

		<%
		String taglibUninstallURL = "javascript:submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(uninstallURL) + "');";
		%>

		<liferay-ui:icon
			image="delete"
			message="uninstall"
			url="<%= taglibUninstallURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>