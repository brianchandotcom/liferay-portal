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

<%@ include file="/init.jsp" %>

<%
CommerceCartDisplayContext commerceCartDisplayContext = (CommerceCartDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String searchContainerId = ParamUtil.getString(request, "searchContainerId", "commerceCarts");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= commerceCartDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceCartDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= commerceCartDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceCartDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceCartDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceCartDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= commerceCartDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(commerceCartDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= commerceCartDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceCarts();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteCommerceCarts() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-carts") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceCartIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceCart" />');
		}
	}
</aui:script>