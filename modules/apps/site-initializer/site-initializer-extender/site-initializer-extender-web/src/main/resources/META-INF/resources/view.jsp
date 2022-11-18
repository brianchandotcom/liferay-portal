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

<clay:container-fluid>
	<clay:sheet
		cssClass="custom-sheet"
	>
		<portlet:actionURL name="/site_initializer/synchronize_site_initializer" var="synchronizeSiteInitializerActionURL" />

		<aui:form action="<%= synchronizeSiteInitializerActionURL %>" method="post" name="fm">
			<clay:sheet-header>
				<div class="sheet-title">
					<%= portletDisplay.getTitle() %>
				</div>
			</clay:sheet-header>

			<div class="sheet-section">
				<div class="alert alert-info">

					<%
					Group group = themeDisplay.getScopeGroup();

					UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();
					%>

					<liferay-ui:message arguments='<%= unicodeProperties.get("siteInitializerKey") %>' key="site-initializer-extender-synchronize-help-x" />
				</div>

				<div>
					<aui:input label="jar-file" name="siteInitializerFile" type="file" />
				</div>
			</div>

			<clay:sheet-footer>
				<button class="btn btn-primary" type="submit">
					<span class="lfr-btn-label">
						<liferay-ui:message key="synchronize" />
					</span>
				</button>
			</clay:sheet-footer>
		</aui:form>
	</clay:sheet>
</clay:container-fluid>