<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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