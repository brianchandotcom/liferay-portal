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

KeywordEntry keywordEntry = (KeywordEntry)row.getObject();

String keywordEntryId = keywordEntry.getKeywordEntryId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="viewEntryURL">
		<portlet:param name="mvcRenderCommandName" value="<%= KeywordIndexMVCCommandNames.VIEW_KEYWORD_ENTRY %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="<%= KeywordIndexWebKeys.KEYWORD_ENTRY_ID %>" value="<%= keywordEntryId %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view-details"
		url="<%= viewEntryURL %>"
	/>

	<portlet:renderURL var="editEntryURL">
		<portlet:param name="mvcRenderCommandName" value="<%= KeywordIndexMVCCommandNames.EDIT_KEYWORD_ENTRY %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="<%= KeywordIndexWebKeys.KEYWORD_ENTRY_ID %>" value="<%= keywordEntryId %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editEntryURL %>"
	/>

	<portlet:actionURL name="<%= KeywordIndexMVCCommandNames.EDIT_KEYWORD_ENTRY %>" var="blacklistEntryURL">
		<portlet:param name="<%= KeywordIndexWebKeys.STATUS %>" value="<%= KeywordEntryStatus.BLACKLISTED.name() %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="<%= KeywordIndexWebKeys.KEYWORD_ENTRY_ID %>" value="<%= keywordEntryId %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="blacklist"
		url="<%= blacklistEntryURL %>"
	/>

	<portlet:actionURL name="<%= KeywordIndexMVCCommandNames.DELETE_KEYWORD_ENTRY %>" var="deleteEntryURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="<%= KeywordIndexWebKeys.KEYWORD_ENTRY_ID %>" value="<%= keywordEntryId %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteEntryURL %>"
	/>
</liferay-ui:icon-menu>