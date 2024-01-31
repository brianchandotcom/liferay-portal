<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/data_layout_renderer/init.jsp" %>

<c:choose>
	<c:when test='<%= Objects.equals(displayType, "borderless") %>'>
		<div class="borderless ddm-form-builder-app">
			<%= content %>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sheet">
			<clay:container-fluid
				cssClass="ddm-form-builder-app"
			>
				<%= content %>
			</clay:container-fluid>
		</div>
	</c:otherwise>
</c:choose>