<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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