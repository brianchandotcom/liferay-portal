<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="installLocalApp" var="installLocalAppURL" />

<aui:form action="<%= installLocalAppURL %>" cssClass="container-fluid container-fluid-max-xl container-view install-apps" enctype="multipart/form-data" method="post" name="fm1">
	<aui:input name="mvcPath" type="hidden" value="/install_local_app.jsp" />

	<clay:sheet>
		<c:if test="<%= CompanyLocalServiceUtil.getCompaniesCount() > 1 %>">
			<div class="alert alert-info">
				<liferay-ui:message key="installed-apps-are-available-to-all-portal-instances.-go-to-plugins-configuration-within-each-portal-instance-to-enable-disable-each-app" />
			</div>
		</c:if>

		<liferay-ui:error exception="<%= FileExtensionException.class %>" message="please-upload-a-file-with-a-valid-extension-jar-lpkg-or-war" />
		<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

		<liferay-ui:success key="pluginDownloaded" message="the-plugin-was-downloaded-successfully-and-is-now-being-installed" />
		<liferay-ui:success key="pluginUploaded" message="the-plugin-was-uploaded-successfully-and-is-now-being-installed" />

		<h2 class="sheet-title">
			<liferay-ui:message key="install" />
		</h2>

		<aui:input cssClass="file-input" label="" name="file" type="file" />

		<clay:sheet-footer>
			<aui:button type="submit" value="install" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>