<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/plugin_installer/init.jsp" %>

<c:choose>
	<c:when test="<%= permissionChecker.isOmniadmin() %>">

		<%
		String uploadProgressId = PortalUtil.generateRandomKey(request, "portlet_plugin_installer_view");

		String tabs1Names = "browse-repository,upload-file,download-file,configuration";
		String tabs1 = ParamUtil.getString(request, "tabs1");

		if (!PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED)) {
			tabs1Names = "configuration";
			tabs1 = "configuration";
		}

		String tabs2 = ParamUtil.getString(request, "tabs2");

		if (Validator.isNull(tabs2)) {
			tabs2 = "portlet-plugins";
		}

		String redirect = ParamUtil.getString(request, "redirect");
		String backURL = ParamUtil.getString(request, "backURL");

		String pluginType = null;

		if (tabs2.equals("portlet-plugins")) {
			pluginType = Plugin.TYPE_PORTLET;
		}
		else if (tabs2.equals("theme-plugins")) {
			pluginType = Plugin.TYPE_THEME;
		}
		else if (tabs2.equals("layout-template-plugins")) {
			pluginType = Plugin.TYPE_LAYOUT_TEMPLATE;
		}
		else if (tabs2.equals("hook-plugins")) {
			pluginType = Plugin.TYPE_HOOK;
		}
		else if (tabs2.equals("web-plugins")) {
			pluginType = Plugin.TYPE_WEB;
		}

		String moduleId = ParamUtil.getString(request, "moduleId");
		String repositoryURL = ParamUtil.getString(request, "repositoryURL");

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/plugin_installer/view");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("tabs2", tabs2);
		portletURL.setParameter("backURL", backURL);
		portletURL.setParameter("moduleId", moduleId);
		portletURL.setParameter("repositoryURL", repositoryURL);

		pageContext.setAttribute("portletURL", portletURL);

		String portletURLString = portletURL.toString();
		%>

		<aui:form action="<%= portletURL %>" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="<%= Constants.PROGRESS_ID %>" type="hidden" value="<%= uploadProgressId %>" />
			<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="backURL" type="hidden" value="<%= backURL %>" />

			<c:if test="<%= Validator.isNull(moduleId) || Validator.isNull(repositoryURL) %>">
				<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />
			</c:if>

			<aui:input name="pluginType" type="hidden" value="<%= pluginType %>" />
			<aui:input name="moduleId" type="hidden" value="<%= moduleId %>" />
			<aui:input name="repositoryURL" type="hidden" value="<%= repositoryURL %>" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(moduleId) && Validator.isNotNull(repositoryURL) %>">
					<%@ include file="/html/portlet/plugin_installer/view_plugin_package.jspf" %>
				</c:when>
				<c:otherwise>
					<liferay-ui:tabs
						backURL="<%= backURL %>"
						names="<%= tabs1Names %>"
						param="tabs1"
						url="<%= portletURLString %>"
					/>

					<c:choose>
						<c:when test='<%= tabs1.equals("upload-file") %>'>
							<%@ include file="/html/portlet/plugin_installer/upload_file.jspf" %>
						</c:when>
						<c:when test='<%= tabs1.equals("download-file") %>'>
							<%@ include file="/html/portlet/plugin_installer/download_file.jspf" %>
						</c:when>
						<c:when test='<%= tabs1.equals("configuration") %>'>
							<%@ include file="/html/portlet/plugin_installer/configuration.jspf" %>
						</c:when>
						<c:otherwise>
							<%@ include file="/html/portlet/plugin_installer/browse_repository.jspf" %>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>

		</aui:form>

		<aui:script>
			function <portlet:namespace />installPluginPackage(cmd) {
				if (cmd == "localDeploy") {
					document.<portlet:namespace />fm.encoding = "multipart/form-data";
				}

				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/plugin_installer/install_plugin" /></portlet:actionURL>");
			}

			function <portlet:namespace />reloadRepositories() {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reloadRepositories";

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/plugin_installer/install_plugin" /></portlet:actionURL>");
			}

			function <portlet:namespace />saveDeployConfiguration() {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'deployConfiguration';

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/plugin_installer/install_plugin" /></portlet:actionURL>");
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>