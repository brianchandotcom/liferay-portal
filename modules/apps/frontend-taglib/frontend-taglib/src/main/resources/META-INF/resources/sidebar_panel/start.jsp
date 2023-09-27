<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/sidebar_panel/init.jsp" %>

<div aria-label="<%= Validator.isNotNull(title) ? title : StringPool.BLANK %>" class="info-panel sidenav-menu-slider" role="tabpanel" tabindex="-1">
	<div class="sidebar sidebar-light sidenav-menu">
		<c:if test="<%= closeButton %>">
			<clay:button
				aria-label='<%= Validator.isNotNull(title) ? LanguageUtil.format(request, "close-x", title, false) : StringPool.BLANK %>'
				borderless="<%= true %>"
				cssClass="d-flex sidenav-close"
				displayType="secondary"
				monospaced="<%= true %>"
				outline="<%= true %>"
				small="<%= true %>"
				title='<%= LanguageUtil.get(request, "close") %>'
			>
				<span class="c-inner" tabindex="-1">
					<span class="inline-item">
						<clay:icon
							symbol="times"
						/>
					</span>
				</span>
			</clay:button>
		</c:if>

		<div class="info-panel-content" id="<%= namespace %>sidebarPanel">