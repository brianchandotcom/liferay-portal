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

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SocialActivity socialActivity = null;

if (row == null) {
	socialActivity = (SocialActivity)request.getAttribute("page_info_panel.jsp-socialActivity");
}
else {
	socialActivity = (SocialActivity)row.getObject();
}

JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(HtmlUtil.unescape(socialActivity.getExtraData()));

double version = extraDataJSONObject.getDouble("version", 0);

WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

WikiPage socialActivityWikiPage = null;

if (version == 0) {
	socialActivityWikiPage = WikiPageLocalServiceUtil.fetchPage(wikiPage.getNodeId(), wikiPage.getTitle());
}
else {
	socialActivityWikiPage = WikiPageLocalServiceUtil.fetchPage(wikiPage.getNodeId(), wikiPage.getTitle(), version);
}
%>

<c:if test="<%= socialActivityWikiPage != null %>">
	<liferay-ui:icon-menu
		direction="left-side"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message="actions"
		showWhenSingleIcon="<%= true %>"
	>
		<c:if test="<%= (version != wikiPage.getVersion()) && socialActivityWikiPage.isApproved() && WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
			<portlet:actionURL name="/wiki/edit_page" var="revertURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
				<portlet:param name="title" value="<%= HtmlUtil.unescape(wikiPage.getTitle()) %>" />
				<portlet:param name="version" value="<%= String.valueOf(version) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message='<%= LanguageUtil.get(request, "restore-version") + " " + String.valueOf(version) %>'
				url="<%= revertURL %>"
			/>
		</c:if>

		<portlet:renderURL var="compareVersionsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/wiki/select_version" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
			<portlet:param name="title" value="<%= HtmlUtil.unescape(wikiPage.getTitle()) %>" />
			<portlet:param name="sourceVersion" value="<%= String.valueOf(version) %>" />
		</portlet:renderURL>

		<%
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("uri", compareVersionsURL);
		%>

		<liferay-ui:icon
			cssClass="compare-to-link"
			data="<%= data %>"
			label="<%= true %>"
			message="compare-to"
			url="javascript:;"
		/>
	</liferay-ui:icon-menu>
</c:if>

<%@ include file="/wiki/compare_versions_pop_up.jspf" %>