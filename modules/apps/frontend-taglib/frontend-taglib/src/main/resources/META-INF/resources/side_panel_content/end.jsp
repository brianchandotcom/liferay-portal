<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/side_panel_content/init.jsp" %>

	<c:if test="<%= Validator.isNull(screenNavigatorKey) %>">
		</div>
	</c:if>

	<c:if test="<%= Validator.isNotNull(screenNavigatorKey) %>">
		<liferay-frontend:screen-navigation
			containerWrapperCssClass="side-panel-iframe-wrapper"
			headerContainerCssClass="side-panel-iframe-menu-wrapper"
			key="<%= screenNavigatorKey %>"
			modelBean="<%= screenNavigatorModelBean %>"
			portletURL="<%= screenNavigatorPortletURL %>"
		/>
	</c:if>
</div>

<liferay-frontend:component
	module="side_panel_content/SidePanelListenersInitializer"
/>