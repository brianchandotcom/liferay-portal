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

<%@ include file="/html/portlet/update_manager/init.jsp" %>

<%
List updatablePackageIds = new ArrayList();
%>

<liferay-ui:success key="triggeredPortletUndeploy" message="plugin-is-undeploying.-the-undeploy-process-will-complete-in-a-separate-process" />

<c:choose>
	<c:when test="<%= permissionChecker.isOmniadmin() %>">
		<c:choose>
			<c:when test="<%= !PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) %>">

				<%
				PortletURL configurationURL = ((RenderResponseImpl)renderResponse).createRenderURL(PortletKeys.PLUGIN_INSTALLER);

				configurationURL.setParameter("struts_action", "/plugin_installer/view");
				configurationURL.setParameter("backURL", currentURL);
				configurationURL.setParameter("tabs1", "configuration");
				%>

				<aui:a href="<%= configurationURL.toString() %>"><liferay-ui:message key="auto-deploy-is-not-enabled" /></aui:a>
			</c:when>
			<c:otherwise>

				<%
				String uploadProgressId = PortalUtil.generateRandomKey(request, "portlet_update_manager_view");
				%>

				<portlet:actionURL var="installPluginURL">
					<portlet:param name="struts_action" value="/update_manager/install_plugin" />
				</portlet:actionURL>

				<portlet:renderURL var="redirectURL">
					<portlet:param name="struts_action" value="/update_manager/view" />
				</portlet:renderURL>

				<aui:form action="<%= installPluginURL %>" method="post" name="fm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" />
					<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

					<%
					try {
						List<String> headerNames = new ArrayList<String>();

						headerNames.add("plugin");
						headerNames.add("trusted");
						headerNames.add("status");
						headerNames.add("installed-version");
						headerNames.add("available-version");
						headerNames.add(StringPool.BLANK);

						SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, renderResponse.createRenderURL(), headerNames, null);

						List pluginPackages = PluginPackageUtil.getInstalledPluginPackages();

						int total = pluginPackages.size();

						searchContainer.setTotal(total);

						pluginPackages = pluginPackages.subList(searchContainer.getStart(), Math.min(total, searchContainer.getEnd()));

						List resultRows = searchContainer.getResultRows();

						for (int i = 0; i < pluginPackages.size(); i++) {
							PluginPackage pluginPackage = (PluginPackage)pluginPackages.get(i);

							PluginPackage availablePluginPackage = null;

							try {
								availablePluginPackage = PluginPackageUtil.getLatestAvailablePluginPackage(pluginPackage.getGroupId(), pluginPackage.getArtifactId());
							}
							catch (Exception e) {
							}

							String pluginPackageModuleId = pluginPackage.getModuleId();
							String pluginPackageName = pluginPackage.getName();
							String pluginPackageVersion = pluginPackage.getVersion();
							String pluginPackageContext = pluginPackage.getContext();

							String pluginPackageStatus = "up-to-date";

							if (PluginPackageUtil.isInstallationInProcess(pluginPackage.getContext())) {
								pluginPackageStatus = "installation-in-process";
							}
							else
							if ((availablePluginPackage != null) && Version.getInstance(availablePluginPackage.getVersion()).isLaterVersionThan(pluginPackageVersion)) {
								if (PluginPackageUtil.isIgnored(pluginPackage)) {
									pluginPackageStatus = "update-ignored";
								}
								else {
									pluginPackageStatus = "update-available";
								}

								updatablePackageIds.add(pluginPackage.getPackageId());
							}
							else
							if (pluginPackage.getVersion().equals(Version.UNKNOWN)) {
								pluginPackageStatus = "unknown";
							}

							ResultRow row = new ResultRow(new Object[] {pluginPackage, availablePluginPackage, pluginPackageStatus, uploadProgressId, currentURL}, pluginPackageModuleId, i);

							row.setClassName("status-" + pluginPackageStatus);

							// Name

							StringBundler sb = new StringBundler(10);

							sb.append("<strong>");
							sb.append(pluginPackageName);
							sb.append("</strong>");
							sb.append("<br />/");
							sb.append(pluginPackageContext);

							row.addText(sb.toString());

							// Trusted

							if ((availablePluginPackage != null) && PluginPackageUtil.isTrusted(availablePluginPackage.getRepositoryURL())) {
								row.addText(LanguageUtil.get(pageContext, "yes"));
							}
							else {
								row.addText(LanguageUtil.get(pageContext, "no"));
							}

							// Status

							row.addText(LanguageUtil.get(pageContext, pluginPackageStatus));

							// Installed version

							row.addText(pluginPackageVersion);

							// Available version

							if (availablePluginPackage != null) {
								PortletURL rowURL = ((RenderResponseImpl)renderResponse).createRenderURL(PortletKeys.PLUGIN_INSTALLER);

								rowURL.setParameter("struts_action", "/plugin_installer/view");
								rowURL.setParameter("redirect", currentURL);
								rowURL.setParameter("tabs1", "browse-repository");
								rowURL.setParameter("moduleId", availablePluginPackage.getModuleId());
								rowURL.setParameter("repositoryURL", availablePluginPackage.getRepositoryURL());

								sb.setIndex(0);

								sb.append("<a href=\"");
								sb.append(rowURL.toString());
								sb.append("\">");
								sb.append(availablePluginPackage.getVersion());
								sb.append("</a>&nbsp;<img align=\"absmiddle\" border=\"0\" src='");
								sb.append(themeDisplay.getPathThemeImages());
								sb.append("/document_library/page.png");
								sb.append("' onmouseover=\"Liferay.Portal.ToolTip.show(this, '");
								sb.append(availablePluginPackage.getChangeLog());
								sb.append("')\" />");

								row.addText(sb.toString());
							}
							else {
								row.addText(StringPool.DASH);
							}

							// Actions

							row.addJSP("/html/portlet/update_manager/plugin_package_action.jsp");

							// Add result row

							resultRows.add(row);
						}
					%>

						<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

						<liferay-ui:upload-progress
							id="<%= uploadProgressId %>"
							message="downloading"
							redirect="<%= currentURL %>"
						/>

						<aui:button-row>
							<c:if test="<%= PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) %>">

								<%
								PortletURL marketplaceURL = ((RenderResponseImpl)renderResponse).createRenderURL(PortletKeys.MARKETPLACE_STORE);
								%>

								<aui:button onClick='<%= "submitForm(document.hrefFm," + StringPool.APOSTROPHE + marketplaceURL.toString() + StringPool.APOSTROPHE + ");" %>' value="install-more-plugins" />
							</c:if>

							<c:if test="<%= !updatablePackageIds.isEmpty() %>">
								<portlet:actionURL var="ignoreAllURL">
									<portlet:param name="struts_action" value="/update_manager/install_plugin" />
									<portlet:param name="<%= Constants.CMD %>" value="ignorePackages" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="pluginPackagesIgnored" value='<%= StringUtil.merge(updatablePackageIds, "\n") %>' />
								</portlet:actionURL>

								<aui:button onClick='<%= "submitForm(document.hrefFm," + StringPool.APOSTROPHE + ignoreAllURL.toString() + StringPool.APOSTROPHE + ");" %>' value="ignore-all-updates" />
							</c:if>
						</aui:button-row>

						<div class="separator"><!-- --></div>

						<div>
							<c:if test="<%= PluginPackageUtil.getLastUpdateDate() != null %>">
								<%= LanguageUtil.format(pageContext, "list-of-plugins-was-last-refreshed-on-x", dateFormatDateTime.format(PluginPackageUtil.getLastUpdateDate())) %>
							</c:if>

							<aui:button onClick='<%= renderResponse.getNamespace() + "reloadRepositories();" %>' value="refresh" />

							<liferay-util:include page="/html/portlet/plugin_installer/repository_report.jsp" />
						</div>

					<%
					}
					catch (PluginPackageException ppe) {
						if (_log.isWarnEnabled()) {
							_log.warn("Error browsing the repository", ppe);
						}
					%>

						<div class="alert alert-error">
							<liferay-ui:message key="an-error-occurred-while-retrieving-available-plugins" />
						</div>

					<%
					}
					%>

				</aui:form>

				<aui:script>
					function <portlet:namespace />reloadRepositories() {
						document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reloadRepositories";
						submitForm(document.<portlet:namespace />fm);
					}
				</aui:script>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.update_manager.view_jsp");
%>