<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/sidebar_panel/init.jsp" %>

		</div>
	</div>
</div>

<c:if test="<%= (resourceURL != null) && Validator.isNotNull(searchContainerId) %>">
	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"resourceURL", resourceURL
			).put(
				"searchContainerId", namespace + searchContainerId
			).put(
				"targetNodeId", namespace + "sidebarPanel"
			).build()
				%>'
		module="{SidebarPanel} from frontend-taglib"
	/>
</c:if>