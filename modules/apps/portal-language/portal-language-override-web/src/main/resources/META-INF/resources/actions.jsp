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
ViewDisplayContext viewDisplayContext = (ViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

PLOItemDTO rowObjectPLOItemDTO = (PLOItemDTO)row.getObject();
%>

<portlet:renderURL var="editURL">
	<portlet:param name="mvcPath" value="/edit.jsp" />
	<portlet:param name="backURL" value="<%= currentURL %>" />
	<portlet:param name="key" value="<%= rowObjectPLOItemDTO.getKey() %>" />
	<portlet:param name="selectedLanguage" value="<%= viewDisplayContext.getSelectedLanguage() %>" />
</portlet:renderURL>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-ui:icon
		markupView="lexicon"
		message="edit"
		url="<%= editURL %>"
	/>

	<c:if test="<%= rowObjectPLOItemDTO.isOverride() %>">
		<c:if test="<%= rowObjectPLOItemDTO.isOverrideSelectedLanguage() %>">
			<portlet:actionURL name="deletePortalLanguageOverride" var="deletePortalLanguageOverrideURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="key" value="<%= rowObjectPLOItemDTO.getKey() %>" />
				<portlet:param name="selectedLanguage" value="<%= viewDisplayContext.getSelectedLanguage() %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				confirmation='<%= LanguageUtil.format(request, "do-you-want-to-reset-the-translation-override-for-x", viewDisplayContext.getSelectedLanguage()) %>'
				message='<%= LanguageUtil.format(request, "remove-translation-override-for-x", viewDisplayContext.getSelectedLanguage()) %>'
				url="<%= deletePortalLanguageOverrideURL %>"
			/>
		</c:if>

		<portlet:actionURL name="deletePortalLanguageOverrides" var="deletePortalLanguageOverridesURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="key" value="<%= rowObjectPLOItemDTO.getKey() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="do-you-want-to-reset-all-translation-overrides"
			message="remove-all-translation-overrides"
			url="<%= deletePortalLanguageOverridesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>