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
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWishList commerceWishList = commerceWishListDisplayContext.getCommerceWishList();

boolean defaultWishList = BeanParamUtil.getBoolean(commerceWishList, request, "defaultWishList");
%>

<portlet:actionURL name="editCommerceWishList" var="editCommerceWishListActionURL" />

<aui:form action="<%= editCommerceWishListActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceWishList == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceWishListId" type="hidden" value="<%= commerceWishListDisplayContext.getCommerceWishListId() %>" />

	<liferay-ui:error exception="<%= CommerceWishListNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= commerceWishList %>" model="<%= CommerceWishList.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="name" />

			<aui:input checked="<%= defaultWishList %>" label="default" name="defaultWishList" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>