<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/portlet/wiki/init.jsp" %>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="activities" />
</liferay-util:include>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());

portletURL.setParameter("struts_action", "/wiki/view_page_activities");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "activities"), portletURL.toString());

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("struts_action", "/wiki/view_page_activities");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
%>

<div class="page-activities">
	<liferay-ui:search-container
		emptyResultsMessage="this-page-does-not-have-any-activity"
		iteratorURL="<%= iteratorURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= SocialActivityLocalServiceUtil.getActivities(0, WikiPage.class.getName(), wikiPage.getResourcePrimKey(), searchContainer.getStart(), searchContainer.getEnd()) %>"
			total="<%= SocialActivityLocalServiceUtil.getActivitiesCount(0, WikiPage.class.getName(), wikiPage.getResourcePrimKey()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.social.model.SocialActivity"
			escapedModel="<%= true %>"
			keyProperty="activityId"
			modelVar="activity"
			rowVar="row"
		>

			<%
			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(activity.getExtraData());

			User activityUser = UserLocalServiceUtil.getUserById(activity.getUserId());
			%>

			<liferay-ui:search-container-column-text
				name="activity"
			>
				<c:choose>
					<c:when test="<%= activity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT || activity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH || activity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH %>">

						<%
						FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(extraDataJSONObject.getLong("fileEntryId"));
						String title = extraDataJSONObject.getString("title");

						int status = WorkflowConstants.STATUS_APPROVED;

						if (TrashUtil.isInTrash(DLFileEntry.class.getName(), fileEntry.getFileEntryId())) {
							status = WorkflowConstants.STATUS_IN_TRASH;
						}
						%>

						<portlet:actionURL var="getPateAttachmentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
							<portlet:param name="struts_action" value="/wiki/get_page_attachment" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
							<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
							<portlet:param name="fileName" value="<%= fileEntry.getTitle() %>" />
							<portlet:param name="status" value="<%= String.valueOf(status) %>" />
						</portlet:actionURL>

						<liferay-util:buffer var="attachmentTitleLink">
							<aui:a href="<%= getPateAttachmentURL %>"><%= title %></aui:a>
						</liferay-util:buffer>

						<c:choose>
							<c:when test="<%= activity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT %>">
								<liferay-ui:icon
									image="clip"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "activity-wiki-add-the-attachment", new Object[] {activityUser.getFullName(), attachmentTitleLink}) %>'
								/>
							</c:when>
							<c:when test="<%= activity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH %>">
								<liferay-ui:icon
									image="delete_attachment"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "activity-wiki-remove-the-attachment", new Object[] {activityUser.getFullName(), attachmentTitleLink}) %>'
								/>
							</c:when>
							<c:when test="<%= activity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH %>">
								<liferay-ui:icon
									image="undo"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "activity-wiki-restore-the-attachment", new Object[] {activityUser.getFullName(), attachmentTitleLink}) %>'
								/>
							</c:when>
						</c:choose>
					</c:when>

					<c:when test="<%= activity.getType() == WikiActivityKeys.UPDATE_PAGE || activity.getType() == WikiActivityKeys.ADD_PAGE %>">

						<%
						double version = extraDataJSONObject.getDouble("version");

						WikiPage activityWikiPage = WikiPageLocalServiceUtil.getPage(node.getNodeId(), wikiPage.getTitle(), version);
						%>

						<portlet:renderURL var="viewPageURL">
							<portlet:param name="struts_action" value="/wiki/view" />
							<portlet:param name="nodeName" value="<%= node.getName() %>" />
							<portlet:param name="title" value="<%= activityWikiPage.getTitle() %>" />
							<portlet:param name="version" value="<%= String.valueOf(version) %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= activity.getType() == WikiActivityKeys.ADD_PAGE %>">
								<liferay-util:buffer var="pageTitleLink">
									<aui:a href="<%= viewPageURL.toString() %>"><%= activityWikiPage.getTitle() %></aui:a>
								</liferay-util:buffer>

								<liferay-ui:icon
									image="add_article"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "activity-wiki-add-the-page", new Object[] {activityUser.getFullName(), pageTitleLink}) %>'
								/>
							</c:when >
							<c:when test="<%= activity.getType() == WikiActivityKeys.UPDATE_PAGE %>">
								<liferay-util:buffer var="pageTitleLink">
									<aui:a href="<%= viewPageURL.toString() %>"><%= version %></aui:a>
								</liferay-util:buffer>

								<liferay-ui:icon
									image="edit"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "activity-wiki-update-the-page-to-version", new Object[] {activityUser.getFullName(), pageTitleLink}) %>'
								/>

								<c:if test="<%= Validator.isNotNull(activityWikiPage.getSummary()) %>">
									<em class="activity-summary"><%= StringPool.QUOTE + activityWikiPage.getSummary() + StringPool.QUOTE %></em>
								</c:if>
							</c:when>
						</c:choose>
					</c:when>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="date"
			>

				<%
				Date createDate = new Date(activity.getCreateDate());
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - createDate.getTime(), true) %>" key="x-ago" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>