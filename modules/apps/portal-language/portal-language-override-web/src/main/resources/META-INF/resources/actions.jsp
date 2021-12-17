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

LanguageItemDisplay rowObjectLanguageItemDisplay = (LanguageItemDisplay)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= viewDisplayContext.isHasManageLanguageOverridesPermission() %>">
		<portlet:renderURL var="editPLOEntryURL">
			<portlet:param name="mvcPath" value="/edit_plo_entry.jsp" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="key" value="<%= rowObjectLanguageItemDisplay.getKey() %>" />
			<portlet:param name="selectedLanguageId" value="<%= viewDisplayContext.getSelectedLanguageId() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			markupView="lexicon"
			message="edit"
			url="<%= editPLOEntryURL %>"
		/>

		<c:if test="<%= rowObjectLanguageItemDisplay.isOverride() %>">
			<c:if test="<%= rowObjectLanguageItemDisplay.isOverrideSelectedLanguageId() %>">
				<portlet:actionURL name="deletePLOEntry" var="deletePLOEntryURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="key" value="<%= rowObjectLanguageItemDisplay.getKey() %>" />
					<portlet:param name="selectedLanguageId" value="<%= viewDisplayContext.getSelectedLanguageId() %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					confirmation='<%= LanguageUtil.format(request, "do-you-want-to-reset-the-translation-override-for-x", viewDisplayContext.getSelectedLanguageId()) %>'
					message='<%= LanguageUtil.format(request, "remove-translation-override-for-x", viewDisplayContext.getSelectedLanguageId()) %>'
					url="<%= deletePLOEntryURL %>"
				/>
			</c:if>

			<portlet:actionURL name="deletePLOEntries" var="deletePLOEntriesURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="key" value="<%= rowObjectLanguageItemDisplay.getKey() %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				confirmation="do-you-want-to-reset-all-translation-overrides"
				message="remove-all-translation-overrides"
				url="<%= deletePLOEntriesURL %>"
			/>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>