<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewHomeQuickActionsDisplayContext viewSpaceMembersSummarySectionDisplayContext = (ViewHomeQuickActionsDisplayContext)request.getAttribute(ViewHomeQuickActionsDisplayContext.class.getName());
%>

<div class="cms-section">
	<div class="pb-2 pt-2 row">
		<div class="col">
			<span class="font-weight-semi-bold text-4">Quick Actions</span>
		</div>
	</div>

	<div class="row">

		<%
		for (Map<String, String> quickAction : viewSpaceMembersSummarySectionDisplayContext.getQuickActions()) {
		%>

			<div class="col">
				<a class="btn btn-secondary text-left w-100" href="<%= quickAction.get("href") %>">
					<clay:icon
						cssClass="mr-2"
						symbol='<%= quickAction.get("icon") %>'
					/>

					<span><%= quickAction.get("label") %></span>
				</a>
			</div>

		<%
		}
		%>

	</div>
</div>