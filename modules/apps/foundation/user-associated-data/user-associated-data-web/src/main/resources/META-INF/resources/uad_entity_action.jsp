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

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
	triggerCssClass="component-action"
>
	<portlet:actionURL name="/auto_anonymize_uad_entity" var="autoAnonymizeURL">
		<portlet:param name="uadEntityId" value="<%= String.valueOf(uadEntity.getEntityId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="anonymize"
		onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADEntitiesFm', '" + autoAnonymizeURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-anonymize-this-entity") + "')" %>'
		url="javascript:;"
	/>

	<portlet:actionURL name="/delete_uad_entity" var="deleteURL">
		<portlet:param name="uadEntityId" value="<%= String.valueOf(uadEntity.getEntityId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="delete"
		onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADEntitiesFm', '" + deleteURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this-entity") + "')" %>'
		url="javascript:;"
	/>
</liferay-ui:icon-menu>