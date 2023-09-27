<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/order_transitions/init.jsp" %>

<%
CommerceOrder commerceOrder = (CommerceOrder)request.getAttribute("liferay-commerce:order-transitions:commerceOrder");
List<ObjectValuePair<Long, String>> commerceOrderTransitionOVPs = (List<ObjectValuePair<Long, String>>)request.getAttribute("liferay-commerce:order-transitions:commerceOrderTransitionOVPs");
String cssClass = (String)request.getAttribute("liferay-commerce:order-transitions:cssClass");
boolean disabled = (boolean)request.getAttribute("liferay-commerce:order-transitions:disabled");
%>

<c:if test="<%= !commerceOrderTransitionOVPs.isEmpty() %>">
	<div id="<portlet:namespace />orderTransition">

		<%
		for (int i = 0; i < commerceOrderTransitionOVPs.size(); i++) {
			ObjectValuePair<Long, String> transitionOVP = commerceOrderTransitionOVPs.get(i);

			String transitionName = transitionOVP.getValue();
		%>

			<button class="<%= HtmlUtil.escapeAttribute(cssClass) %> transition-link" data-commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>" data-transitionName="<%= HtmlUtil.escapeAttribute(transitionName) %>" data-workflowTaskId="<%= transitionOVP.getKey() %>" <%= disabled ? "disabled" : StringPool.BLANK %> type="button">
				<liferay-ui:message key="<%= HtmlUtil.escape(transitionName) %>" />
			</button>

		<%
		}
		%>

	</div>
</c:if>