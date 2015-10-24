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

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/wiki/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<liferay-util:include page="/wiki/page_tabs_history.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs3" value="activities" />
</liferay-util:include>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());

portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view_page_history");
portletURL.setParameter("redirect", currentURL);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "history"), portletURL.toString());

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("mvcRenderCommandName", "/wiki/view_page_activities");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
iteratorURL.setParameter("title", wikiPage.getTitle());
%>

<div class="page-activities">
	<liferay-ui:search-container
		iteratorURL="<%= iteratorURL %>"
		total="<%= SocialActivityLocalServiceUtil.getActivitiesCount(0, WikiPage.class.getName(), wikiPage.getResourcePrimKey()) %>"
	>
		<liferay-ui:search-container-results
			results="<%= SocialActivityLocalServiceUtil.getActivities(0, WikiPage.class.getName(), wikiPage.getResourcePrimKey(), searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.social.model.SocialActivity"
			escapedModel="<%= true %>"
			keyProperty="activityId"
			modelVar="socialActivity"
		>

			<%
			User socialActivityUser = UserLocalServiceUtil.fetchUserById(socialActivity.getUserId());

			if (socialActivityUser == null) {
				socialActivityUser = UserLocalServiceUtil.getDefaultUser(socialActivity.getCompanyId());
			}

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

						String title = extraDataJSONObject.getString("fileEntryTitle");

						if (fileEntry != null) {
							fileVersion = fileEntry.getFileVersion();
						}
						%>

						<liferay-util:buffer var="attachmentTitle">
							<c:choose>
								<c:when test="<%= fileVersion != null %>">
									<aui:a href="<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"><%= title %></aui:a>
								</c:when>
								<c:otherwise>
									<%= title %>
								</c:otherwise>
							</c:choose>
						</liferay-util:buffer>

						<c:choose>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT %>">
								<liferay-ui:icon
									iconCssClass="icon-paperclip"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "x-added-the-attachment-x", new Object[] {HtmlUtil.escape(socialActivityUser.getFullName()), attachmentTitle}, false) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH %>">
								<liferay-ui:icon
									iconCssClass="icon-remove"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "x-removed-the-attachment-x", new Object[] {HtmlUtil.escape(socialActivityUser.getFullName()), attachmentTitle}, false) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH %>">
								<liferay-ui:icon
									iconCssClass="icon-undo"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "x-restored-the-attachment-x", new Object[] {HtmlUtil.escape(socialActivityUser.getFullName()), attachmentTitle}, false) %>'
								/>
							</c:when>
						</c:choose>
					</c:when>

					<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_ADD_COMMENT %>">

						<%
						WikiPage socialActivityWikiPage = WikiPageLocalServiceUtil.getPage(node.getNodeId(), wikiPage.getTitle());
						%>

						<portlet:renderURL var="viewPageURL">
							<portlet:param name="mvcRenderCommandName" value="/wiki/view" />
							<portlet:param name="nodeName" value="<%= node.getName() %>" />
							<portlet:param name="title" value="<%= socialActivityWikiPage.getTitle() %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							label="<%= true %>"
							message='<%= LanguageUtil.format(request, "x-added-a-comment", new Object[] {HtmlUtil.escape(socialActivityUser.getFullName()), viewPageURL + "#wikiCommentsPanel"}, false) %>'
						/>
					</c:when>

					<c:when test="<%= (socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_TO_TRASH) || (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) || (socialActivity.getType() == WikiActivityKeys.ADD_PAGE) || (socialActivity.getType() == WikiActivityKeys.UPDATE_PAGE) %>">

						<%
						double version = extraDataJSONObject.getDouble("version");

						WikiPage socialActivityWikiPage = WikiPageLocalServiceUtil.fetchPage(node.getNodeId(), wikiPage.getTitle(), version);
						%>

						<portlet:renderURL var="viewPageURL">
							<portlet:param name="mvcRenderCommandName" value="/wiki/view" />
							<portlet:param name="nodeName" value="<%= node.getName() %>" />
							<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
							<portlet:param name="version" value="<%= String.valueOf(version) %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_TO_TRASH %>">
								<liferay-ui:icon
									iconCssClass="icon-trash"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "activity-wiki-page-move-to-trash", new Object[] {StringPool.BLANK, HtmlUtil.escape(socialActivityUser.getFullName()), wikiPage.getTitle()}, false) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH %>">
								<liferay-util:buffer var="pageTitleLink">
									<c:choose>
										<c:when test="<%= socialActivityWikiPage != null %>">
											<aui:a href="<%= viewPageURL.toString() %>"><%= wikiPage.getTitle() %></aui:a>
										</c:when>
										<c:otherwise>
											<%= wikiPage.getTitle() %>
										</c:otherwise>
									</c:choose>
								</liferay-util:buffer>

								<liferay-ui:icon
									iconCssClass="icon-undo"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "activity-wiki-page-restore-from-trash", new Object[] {StringPool.BLANK, HtmlUtil.escape(socialActivityUser.getFullName()), pageTitleLink}, false) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == WikiActivityKeys.ADD_PAGE %>">
								<liferay-util:buffer var="pageTitleLink">
									<c:choose>
										<c:when test="<%= socialActivityWikiPage != null %>">
											<aui:a href="<%= viewPageURL.toString() %>"><%= wikiPage.getTitle() %></aui:a>
										</c:when>
										<c:otherwise>
											<%= wikiPage.getTitle() %>
										</c:otherwise>
									</c:choose>
								</liferay-util:buffer>

								<liferay-ui:icon
									iconCssClass="icon-plus"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "x-added-the-page-x", new Object[] {HtmlUtil.escape(socialActivityUser.getFullName()), pageTitleLink}, false) %>'
								/>
							</c:when>
							<c:when test="<%= socialActivity.getType() == WikiActivityKeys.UPDATE_PAGE %>">
								<liferay-util:buffer var="pageTitleLink">
									<c:choose>
										<c:when test="<%= socialActivityWikiPage != null %>">
											<aui:a href="<%= viewPageURL.toString() %>">
												<%= version %>

												<c:if test="<%= socialActivityWikiPage.isMinorEdit() %>">
													(<liferay-ui:message key="minor-edit" />)
												</c:if>
											</aui:a>
										</c:when>
										<c:otherwise>
											<%= version %>
										</c:otherwise>
									</c:choose>
								</liferay-util:buffer>

								<liferay-ui:icon
									iconCssClass="icon-edit"
									label="<%= true %>"
									message='<%= LanguageUtil.format(request, "x-updated-the-page-to-version-x", new Object[] {HtmlUtil.escape(socialActivityUser.getFullName()), pageTitleLink}, false) %>'
								/>

								<c:if test="<%= (socialActivityWikiPage != null) && (socialActivityWikiPage.getStatus() != WorkflowConstants.STATUS_APPROVED) %>">
									<span class="activity-status"><liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(socialActivityWikiPage.getStatus()) %>" /></span>
								</c:if>

								<c:if test="<%= (socialActivityWikiPage != null) && Validator.isNotNull(socialActivityWikiPage.getSummary()) %>">
									<em class="activity-summary"><%= StringPool.QUOTE + HtmlUtil.escape(socialActivityWikiPage.getSummary()) + StringPool.QUOTE %></em>
								</c:if>
							</c:when>
						</c:choose>
					</c:when>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="date"
				value="<%= new Date(socialActivity.getCreateDate()) %>"
			/>

			<c:choose>
				<c:when test="<%= ((socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT) || (socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) || (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) && (fileEntry != null) %>">
					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/wiki/page_activity_attachment_action.jsp"
					/>
				</c:when>
				<c:when test="<%= (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) || (socialActivity.getType() == WikiActivityKeys.ADD_PAGE) || (socialActivity.getType() == WikiActivityKeys.UPDATE_PAGE) %>">
					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/wiki/page_activity_page_action.jsp"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text name="" value="" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>

<portlet:actionURL name="/wiki/edit_page_attachment" var="checkEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" />
</portlet:actionURL>

<portlet:renderURL var="duplicateEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/wiki/restore_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<aui:script use="liferay-restore-entry">
	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<%= checkEntryURL.toString() %>',
			duplicateEntryURL: '<%= duplicateEntryURL.toString() %>',
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>

<aui:script sandbox="<%= true %>">
	$('body').on(
		'click',
		'.compare-to-link a',
		function(event) {
			var currentTarget = $(event.currentTarget);

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<portlet:namespace />selectVersionFm',
					id: '<portlet:namespace />compareVersions' + currentTarget.attr('id'),
					title: '<liferay-ui:message key="compare-versions" />',
					uri: currentTarget.data('uri')
				},
				function(event) {
					<portlet:renderURL var="compareVersionURL">
						<portlet:param name="mvcRenderCommandName" value="/wiki/compare_versions" />
						<portlet:param name="backURL" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
						<portlet:param name="type" value="html" />
					</portlet:renderURL>

					var uri = '<%= compareVersionURL %>';

					uri = Liferay.Util.addParams('<portlet:namespace />sourceVersion=' + event.sourceversion, uri);
					uri = Liferay.Util.addParams('<portlet:namespace />targetVersion=' + event.targetversion, uri);

					location.href = uri;
				}
			);
		}
	);
</aui:script>