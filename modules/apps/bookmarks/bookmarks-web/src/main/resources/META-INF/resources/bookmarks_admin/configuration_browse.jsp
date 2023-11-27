<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
BookmarksConfigurationDisplayContext bookmarksConfigurationDisplayContext = new BookmarksConfigurationDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(bookmarksConfigurationDisplayContext.getBackURL());
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="settings" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= bookmarksConfigurationDisplayContext.getSettingsVerticalNavItemList() %>"
			/>

			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= bookmarksConfigurationDisplayContext.getNotificationsVerticalNavItemList() %>"
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>