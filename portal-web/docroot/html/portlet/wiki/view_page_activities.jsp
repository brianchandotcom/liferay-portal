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
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<liferay-util:include page="/html/portlet/wiki/page_tabs_history.jsp">
	<liferay-util:param name="tabs3" value="activities" />
</liferay-util:include>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());

portletURL.setParameter("struts_action", "/wiki/view_page_history");
portletURL.setParameter("redirect", currentURL);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "history"), portletURL.toString());

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("struts_action", "/wiki/view_page_activities");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
%>

<div class="page-activities">
	<liferay-ui:search-container
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
			modelVar="socialActivity"
		>

			<%
			User socialActivityUser = UserLocalServiceUtil.getUserById(socialActivity.getUserId());

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(socialActivity.getExtraData());

			FileEntry fileEntry = null;
			FileVersion fileVersion = null;
			%>

			<liferay-ui:search-container-column-text
				name="activity"
			>
				<c:choose>
					<c:when test="<%= (socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT) || (socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) || (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH) %>">

						<%
						try {
							fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(extraDataJSONObject.getLong("fileEntryId"));
						}
						catch (NoSuchModelException nsme) {
						}

						String title = extraDataJSONObject.getString("title");

						if (fileEntry != null) {
							fileVersion = fileEntry.getFileVersion();
						}
						%>

						<liferay-util:buffer var="attachmentTitle">
							<c:choose>
								<c:when test="<%= fileVersion != null %>">
									<portlet:actionURL var="getPateAttachmentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
										<portlet:param name="struts_action" value="/wiki/get_page_attachment" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
										<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
										<portlet:param name="fileName" value="<%= fileEntry.getTitle() %>" />
										<portlet:param name="status" value="<%= String.valueOf(fileVersion.getStatus()) %>" />
									</portlet:actionURL>

									<aui:a href="<%= getPateAttachmentURL %>"><%= title %></aui:a>
								</c:when>
								<c:otherwise>
									<%= title %>
								</c:otherwise>
							</c:choose>
						</liferay-util:buffer>

						<c:choose>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT %>">
								<liferay-ui:icon
									image="clip"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "x-added-the-attachment-x", new Object[] {socialActivityUser.getFullName(), attachmentTitle}) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH %>">
								<liferay-ui:icon
									image="delete_attachment"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "x-removed-the-attachment-x", new Object[] {socialActivityUser.getFullName(), attachmentTitle}) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH %>">
								<liferay-ui:icon
									image="undo"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "x-restored-the-attachment-x", new Object[] {socialActivityUser.getFullName(), attachmentTitle}) %>'
								/>
							</c:when>
						</c:choose>
					</c:when>

					<c:when test="<%= (socialActivity.getType() == WikiActivityKeys.ADD_PAGE) || (socialActivity.getType() == WikiActivityKeys.UPDATE_PAGE) %>">

						<%
						double version = extraDataJSONObject.getDouble("version");

						WikiPage socialActivityWikiPage = WikiPageLocalServiceUtil.getPage(node.getNodeId(), wikiPage.getTitle(), version);
						%>

						<portlet:renderURL var="viewPageURL">
							<portlet:param name="struts_action" value="/wiki/view" />
							<portlet:param name="nodeName" value="<%= node.getName() %>" />
							<portlet:param name="title" value="<%= socialActivityWikiPage.getTitle() %>" />
							<portlet:param name="version" value="<%= String.valueOf(version) %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= socialActivity.getType() == WikiActivityKeys.ADD_PAGE %>">
								<liferay-util:buffer var="pageTitleLink">
									<aui:a href="<%= viewPageURL.toString() %>"><%= socialActivityWikiPage.getTitle() %></aui:a>
								</liferay-util:buffer>

								<liferay-ui:icon
									image="add_article"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "x-added-the-page-x", new Object[] {socialActivityUser.getFullName(), pageTitleLink}) %>'
								/>
							</c:when >
							<c:when test="<%= socialActivity.getType() == WikiActivityKeys.UPDATE_PAGE %>">
								<liferay-util:buffer var="pageTitleLink">
									<aui:a href="<%= viewPageURL.toString() %>">
										<%= version %>

										<c:if test="<%= socialActivityWikiPage.isMinorEdit() %>">
											(<liferay-ui:message key="minor-edit" />)
										</c:if>
									</aui:a>
								</liferay-util:buffer>

								<liferay-ui:icon
									image="edit"
									label="<%= true %>"
									message='<%= LanguageUtil.format(pageContext, "x-updated-the-page-to-version-x", new Object[] {socialActivityUser.getFullName(), pageTitleLink}) %>'
								/>

								<c:if test="<%= socialActivityWikiPage.getStatus() != WorkflowConstants.STATUS_APPROVED %>">
									<span class="activity-status"><liferay-ui:message key="<%= WorkflowConstants.toLabel(socialActivityWikiPage.getStatus()) %>" /></span>
								</c:if>

								<c:if test="<%= Validator.isNotNull(socialActivityWikiPage.getSummary()) %>">
									<em class="activity-summary"><%= StringPool.QUOTE + socialActivityWikiPage.getSummary() + StringPool.QUOTE %></em>
								</c:if>
							</c:when>
						</c:choose>
					</c:when>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="date"
			>
				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - socialActivity.getCreateDate(), true) %>" key="x-ago" />
			</liferay-ui:search-container-column-text>

			<c:choose>
				<c:when test="<%= ((socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT) || (socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) || (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) && (fileEntry != null) %>">
					<liferay-ui:search-container-column-jsp
						align="right"
						path="/html/portlet/wiki/page_activity_attachment_action.jsp"
					/>
				</c:when>
				<c:when test="<%= (socialActivity.getType() == WikiActivityKeys.ADD_PAGE) || (socialActivity.getType() == WikiActivityKeys.UPDATE_PAGE) %>">
					<liferay-ui:search-container-column-jsp
						align="right"
						path="/html/portlet/wiki/page_activity_page_action.jsp"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text name="" value="" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<liferay-ui:restore-entry
		duplicateEntryAction="/wiki/restore_entry"
		overrideMessage="overwrite-the-existing-attachment-with-the-removed-one"
		renameMessage="keep-both-attachments-and-rename-the-removed-attachment-as"
		restoreEntryAction="/wiki/restore_page_attachment"
	/>
</div>