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
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWishList commerceWishList = commerceWishListDisplayContext.getCommerceWishList();
%>

<portlet:actionURL name="/commerce_wish_list_content/edit_commerce_wish_list" var="editCommerceWishListActionURL" />

<aui:form action="<%= editCommerceWishListActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceWishList == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceWishListId" type="hidden" value="<%= commerceWishListDisplayContext.getCommerceWishListId() %>" />

	<liferay-ui:error exception="<%= CommerceWishListNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= commerceWishList %>" model="<%= CommerceWishList.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="name" />

			<aui:input checked='<%= BeanParamUtil.getBoolean(commerceWishList, request, "defaultWishList") %>' inlineLabel="right" label="default" labelCssClass="simple-toggle-switch" name="defaultWishList" type="toggle-switch" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
		/>
	</aui:button-row>
</aui:form>