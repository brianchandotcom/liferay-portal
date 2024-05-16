<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries = (List<CPDefinitionGroupedEntry>)request.getAttribute(GroupedCPTypeWebKeys.CP_DEFINITION_GROUPED_ENTRIES);

if (cpDefinitionGroupedEntries == null) {
	cpDefinitionGroupedEntries = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpDefinitionGroupedEntries.size() == 1 %>">

		<%
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = cpDefinitionGroupedEntries.get(0);

		CProduct cProduct = cpDefinitionGroupedEntry.getEntryCProduct();

		CPDefinition cProductCPDefinition = CPDefinitionLocalServiceUtil.getCPDefinition(cProduct.getPublishedCPDefinitionId());

		request.setAttribute("info_panel.jsp-entry", cpDefinitionGroupedEntry);
		%>

		<div class="sidebar-header">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><%= HtmlUtil.escape(cProductCPDefinition.getName(themeDisplay.getLanguageId())) %></h4>
				</div>

				<div class="autofit-col">
					<ul class="autofit-padded-no-gutters autofit-row">
						<li class="autofit-col">
							<liferay-util:include page="/cp_definition_grouped_entry_action.jsp" servletContext="<%= application %>" />
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="sidebar-body">
			<dl class="sidebar-dl sidebar-section">
				<dt class="sidebar-dt"><liferay-ui:message key="id" /></dt>

				<dd class="sidebar-dd">
					<%= HtmlUtil.escape(String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId())) %>
				</dd>
				<dt class="sidebar-dt"><liferay-ui:message key="status" /></dt>
			</dl>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><liferay-ui:message arguments="<%= cpDefinitionGroupedEntries.size() %>" key="x-items-are-selected" /></h4>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>