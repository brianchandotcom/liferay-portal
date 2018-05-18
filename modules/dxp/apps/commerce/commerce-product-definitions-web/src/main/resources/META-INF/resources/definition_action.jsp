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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPDefinition cpDefinition = null;

if (row != null) {
	cpDefinition = (CPDefinition)row.getObject();
}
else {
	cpDefinition = (CPDefinition)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editProductDefinition" />
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>" />
		<portlet:param name="screenNavigationCategoryKey" value="<%= CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<c:if test="<%= cpDefinition.isApproved() %>">

		<%
		String productURL = cpDefinitionsDisplayContext.getProductURL(cpDefinition);
		%>

		<liferay-ui:icon
			iconCssClass="icon-new-window"
			message="view-in-public-store"
			target="_blank"
			url="<%= productURL %>"
		/>
	</c:if>

	<portlet:actionURL name="editProductDefinition" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		label="<%= true %>"
		trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>