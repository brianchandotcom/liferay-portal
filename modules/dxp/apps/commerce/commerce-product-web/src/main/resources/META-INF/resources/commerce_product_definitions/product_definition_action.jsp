<%@ include file="/commerce_product_definitions/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="commerceProductDefinitionId"
			value="<%= String.valueOf(commerceProductDefinition.getCommerceProductDefinitionId()) %>"
		/>

		<portlet:param name="mvcRenderCommandName"
			value="/commerce_product_definitions/edit_product_definition"
		/>

		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/editProductDefinition" var="deleteURL">
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(commerceProductDefinition.getGroupId()) %>" />
		<portlet:param name="commerceProductDefinitionId" value="<%= String.valueOf(commerceProductDefinition.getCommerceProductDefinitionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteURL %>" />

	<liferay-ui:icon
		message="delete"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>