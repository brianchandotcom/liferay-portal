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

<%@ include file="/init.jsp" %>

<%
Group group = themeDisplay.getScopeGroup();

UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();
%>

<clay:container-fluid
	cssClass="main-content-body mt-4"
>
	<clay:sheet
		cssClass="custom-sheet"
	>
		<portlet:actionURL name="/site_initializer_extender/synchronize_site_initializer_extender" var="synchronizeSiteInitializerExtenderActionURL" />

		<aui:form action="<%= synchronizeSiteInitializerExtenderActionURL %>" method="post" name="fm">
			<clay:sheet-header>
				<div class="sheet-title">
					<liferay-ui:message key="javax.portlet.title.com_liferay_site_initializer_extender_web_SiteInitializerExtenderPortlet" />
				</div>
			</clay:sheet-header>

			<div class="sheet-section">
				<div class="alert alert-info">
					<liferay-ui:message arguments='<%= unicodeProperties.get("siteInitializerKey") %>' key="site-initializer-extender-synchronize-help-x" />
				</div>
			</div>

			<clay:sheet-footer>
				<div class="btn-group-item">
					<div class="btn-group-item">
						<button class="btn btn-primary" type="submit">
							<span class="lfr-btn-label">
								<liferay-ui:message key="synchronize" />
							</span>
						</button>
					</div>
				</div>
			</clay:sheet-footer>
		</aui:form>
	</clay:sheet>
</clay:container-fluid>