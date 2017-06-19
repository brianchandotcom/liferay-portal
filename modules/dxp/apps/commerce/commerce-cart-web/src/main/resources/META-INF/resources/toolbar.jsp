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
CCartDisplayContext cCartDisplayContext = (CCartDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String searchContainerId = ParamUtil.getString(request, "searchContainerId", "cCarts");

int type = ParamUtil.getInteger(request, "type", CCartConstants.C_CART_TYPE_CART);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cCartDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cCartDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= cCartDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cCartDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cCartDisplayContext.getOrderByCol() %>"
			orderByType="<%= cCartDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= cCartDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cCartDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCCarts();" %>' icon="trash" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteCCarts() {

		<%
		String deleteMessage = StringPool.BLANK;

		if (type == CCartConstants.C_CART_TYPE_CART) {
		    deleteMessage = "are-you-sure-you-want-to-delete-the-selected-carts";
		}
		else if (type == CCartConstants.C_CART_TYPE_WISH_LIST) {
		    deleteMessage = "are-you-sure-you-want-to-delete-the-selected-wish-lists";
		}
		%>

		if (confirm('<%= UnicodeLanguageUtil.get(request, deleteMessage) %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCCartIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCart" />');
		}
	}
</aui:script>