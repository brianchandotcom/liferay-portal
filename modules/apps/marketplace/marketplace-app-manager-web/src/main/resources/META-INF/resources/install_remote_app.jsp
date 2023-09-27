<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="installRemoteApp" var="installRemoteAppURL" />

<aui:form action="<%= installRemoteAppURL %>" cssClass="container-fluid container-fluid-max-xl container-view" method="post" name="fm">
	<aui:input name="mvcPath" type="hidden" value="/install_remote_app.jsp" />

	<clay:sheet>
		<c:if test="<%= CompanyLocalServiceUtil.getCompaniesCount() > 1 %>">
			<div class="alert alert-info">
				<liferay-ui:message key="installed-apps-are-available-to-all-portal-instances.-go-to-plugins-configuration-within-each-portal-instance-to-enable-disable-each-app" />
			</div>
		</c:if>

		<liferay-ui:error key="invalidURL" message="please-enter-a-valid-url" />

		<liferay-ui:success key="pluginDownloaded" message="the-plugin-was-downloaded-successfully-and-is-now-being-installed" />

		<aui:input cssClass="file-input" name="url" type="text" />

		<clay:sheet-footer>
			<aui:button type="submit" value="install" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>