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
String pathThemeImages = (String)request.getAttribute("liferay-commerce:order-transitions:pathThemeImages");
%>

<c:if test="<%= !commerceOrderTransitionOVPs.isEmpty() %>">
	<div id="<portlet:namespace />orderTransition">
		<c:if test="<%= commerceOrderTransitionOVPs.size() > 1 %>">
			<div class="btn-group btn-group-sm dropdown" role="group">
		</c:if>

		<%
		ObjectValuePair<Long, String> firstTransitionOVP = commerceOrderTransitionOVPs.get(0);
		%>

		<button class="<%= cssClass + " transition-link" %>" data-commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>" data-transitionName="<%= firstTransitionOVP.getValue() %>" data-workflowTaskId="<%= firstTransitionOVP.getKey() %>" type="button">
			<%= LanguageUtil.get(request, firstTransitionOVP.getValue()) %>
		</button>

		<c:if test="<%= commerceOrderTransitionOVPs.size() > 1 %>">
				<button aria-expanded="false" aria-haspopup="true" class="<%= cssClass + " dropdown-toggle" %>" data-toggle="dropdown" type="button">
					<svg aria-hidden="true" class="lexicon-icon lexicon-icon-caret-bottom">
						<use xlink:href="<%= pathThemeImages %>/lexicon/icons.svg#caret-bottom">
					</svg>
				</button>

				<div class="dropdown-menu dropdown-menu-right">

					<%
					for (int i = 1; i < commerceOrderTransitionOVPs.size(); i++) {
						ObjectValuePair<Long, String> transitionOVP = commerceOrderTransitionOVPs.get(i);

						String transitionName = transitionOVP.getValue();
					%>

						<a class="dropdown-item transition-link" data-commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>" data-transitionName="<%= transitionName %>" data-workflowTaskId="<%= transitionOVP.getKey() %>" href="javascript:;"><%= LanguageUtil.get(request, transitionName) %></a>

					<%
					}
					%>

				</div>
			</div>
		</c:if>
	</div>
</c:if>