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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPDefinitionOptionValueRel cpDefinitionOptionValueRel = null;

if (row != null) {
	cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)row.getObject();
}
else {
	cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editProductDefinitionOptionValueRelURL">
		<portlet:param name="mvcRenderCommandName" value="editProductDefinitionOptionValueRel" />
		<portlet:param name="cpDefinitionOptionValueRelId" value="<%= String.valueOf(cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editProductDefinitionOptionValueRelURL %>"
	/>

	<portlet:actionURL name="editProductDefinitionOptionValueRel" var="deleteProductDefinitionOptionValueRelURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="cpDefinitionOptionValueRelId" value="<%= String.valueOf(cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteProductDefinitionOptionValueRelURL %>"
	/>
</liferay-ui:icon-menu>