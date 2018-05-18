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
List<CommercePriceList> commercePriceLists = (List<CommercePriceList>)request.getAttribute(CommercePriceListWebKeys.COMMERCE_PRICE_LISTS);

if (commercePriceLists == null) {
	commercePriceLists = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= commercePriceLists.size() == 1 %>">

		<%
		CommercePriceList commercePriceList = commercePriceLists.get(0);

		request.setAttribute("info_panel.jsp-entry", commercePriceList);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/price_list_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(commercePriceList.getName()) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(commercePriceList.getCommercePriceListId())) %>
			</p>

			<h5><liferay-ui:message key="status" /></h5>

			<p>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= commercePriceList.getStatus() %>" />
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= commercePriceLists.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>