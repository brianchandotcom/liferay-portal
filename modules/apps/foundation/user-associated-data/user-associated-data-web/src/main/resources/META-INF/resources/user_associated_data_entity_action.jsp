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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UADEntity uadEntity = (UADEntity)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:actionURL name="/user_associated_data/auto_anonymize_user_associated_data_entity" var="autoAnonymizeURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="uadEntityId" value="<%= uadEntity.getUADEntityId() %>" />
		<portlet:param name="uadRegistryKey" value="<%= uadEntity.getUADRegistryKey() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="auto-anonymize"
		onClick='<%= renderResponse.getNamespace() + "confirmAction('" + autoAnonymizeURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-anonymize-this-entity") + "')" %>'
		url="javascript:;"
	/>

	<portlet:actionURL name="/user_associated_data/delete_user_associated_data_entity" var="deleteURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="uadEntityId" value="<%= uadEntity.getUADEntityId() %>" />
		<portlet:param name="uadRegistryKey" value="<%= uadEntity.getUADRegistryKey() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="delete"
		onClick='<%= renderResponse.getNamespace() + "confirmAction('" + deleteURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this-entity") + "')" %>'
		url="javascript:;"
	/>

	<portlet:actionURL name="/user_associated_data/export_user_associated_data_entity" var="exportURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="uadEntityId" value="<%= uadEntity.getUADEntityId() %>" />
		<portlet:param name="uadRegistryKey" value="<%= uadEntity.getUADRegistryKey() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="export"
		url="<%= exportURL %>"
	/>
</liferay-ui:icon-menu>