<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<CommerceTierPriceEntry> commerceTierPriceEntries = (List<CommerceTierPriceEntry>)request.getAttribute(CommercePriceListWebKeys.COMMERCE_TIER_PRICE_ENTRIES);

if (commerceTierPriceEntries == null) {
	commerceTierPriceEntries = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= commerceTierPriceEntries.size() == 1 %>">

		<%
		CommerceTierPriceEntry commerceTierPriceEntry = commerceTierPriceEntries.get(0);

		request.setAttribute("info_panel.jsp-entry", commerceTierPriceEntries);

		CommercePriceEntry commercePriceEntry = commerceTierPriceEntry.getCommercePriceEntry();

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/tier_price_entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpDefinition.getName(languageId)) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(commerceTierPriceEntry.getCommerceTierPriceEntryId())) %>
			</p>

			<h5><liferay-ui:message key="name" /></h5>

			<p>
				<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>
			</p>

			<h5><liferay-ui:message key="sku" /></h5>

			<p>
				<%= HtmlUtil.escape(cpInstance.getSku()) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= commerceTierPriceEntries.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>