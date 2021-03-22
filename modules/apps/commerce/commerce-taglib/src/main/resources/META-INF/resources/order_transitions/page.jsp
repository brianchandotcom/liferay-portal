<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/order_transitions/init.jsp" %>

<%
CommerceOrder commerceOrder = (CommerceOrder)request.getAttribute("liferay-commerce:order-transitions:commerceOrder");
List<ObjectValuePair<Long, String>> commerceOrderTransitionOVPs = (List<ObjectValuePair<Long, String>>)request.getAttribute("liferay-commerce:order-transitions:commerceOrderTransitionOVPs");
String cssClass = (String)request.getAttribute("liferay-commerce:order-transitions:cssClass");
%>

<c:if test="<%= !commerceOrderTransitionOVPs.isEmpty() %>">
	<div id="<portlet:namespace />orderTransition">

		<%
		for (int i = 0; i < commerceOrderTransitionOVPs.size(); i++) {
			ObjectValuePair<Long, String> transitionOVP = commerceOrderTransitionOVPs.get(i);

			String transitionName = transitionOVP.getValue();
		%>

			<button class="<%= HtmlUtil.escapeAttribute(cssClass) %> transition-link" data-commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>" data-transitionName="<%= HtmlUtil.escapeAttribute(transitionName) %>" data-workflowTaskId="<%= transitionOVP.getKey() %>" type="button">
				<%= LanguageUtil.get(request, HtmlUtil.escape(transitionName)) %>
			</button>

		<%
		}
		%>

	</div>
</c:if>