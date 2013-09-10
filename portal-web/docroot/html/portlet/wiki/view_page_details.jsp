<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

List<FileEntry> attachmentsFileEntries = null;

if (wikiPage != null) {
	attachmentsFileEntries = wikiPage.getAttachmentsFileEntries();
}

int numOfVersions = WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle());
WikiPage initialPage = (WikiPage)WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), numOfVersions - 1, numOfVersions).get(0);

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", wikiPage.getTitle());

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", wikiPage.getTitle());

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "details"), currentURL);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="details" />
</liferay-util:include>

<table class="table table-bordered table-hover table-striped page-info">
<tr>
	<th class="table-header">
		<liferay-ui:message key="title" />
	</th>
	<td class="table-cell">
		<%= wikiPage.getTitle() %>
	</td>
</tr>
<tr>
	<th class="table-header">
		<liferay-ui:message key="format" />
	</th>
	<td class="table-cell">
		<liferay-ui:message key='<%= "wiki.formats." + wikiPage.getFormat() %>'/>
	</td>
</tr>
<tr>
	<th class="table-header">
		<liferay-ui:message key="latest-version" />
	</th>
	<td class="table-cell">
		<%= wikiPage.getVersion() %>

		<c:if test="<%= wikiPage.isMinorEdit() %>">
			(<liferay-ui:message key="minor-edit" />)
		</c:if>
	</td>
</tr>
<tr>
	<th class="table-header">
		<liferay-ui:message key="created-by" />
	</th>
	<td class="table-cell">
		<%= HtmlUtil.escape(initialPage.getUserName()) %> (<%= dateFormatDateTime.format(initialPage.getCreateDate()) %>)
	</td>
</tr>
<tr>
	<th class="table-header">
		<liferay-ui:message key="last-changed-by" />
	</th>
	<td class="table-cell">
		<%= HtmlUtil.escape(wikiPage.getUserName()) %> (<%= dateFormatDateTime.format(wikiPage.getCreateDate()) %>)
	</td>
</tr>
<tr>
	<th class="table-header">
		<liferay-ui:message key="attachments" />
	</th>
	<td class="table-cell">
		<%= (attachmentsFileEntries != null) ? attachmentsFileEntries.size() : 0 %>
	</td>
</tr>

<c:if test="<%= PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED) && WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.VIEW) %>">

	<%
	String[] conversions = DocumentConversionUtil.getConversions("html");

	PortletURL exportPageURL = renderResponse.createActionURL();

	exportPageURL.setParameter("struts_action", "/wiki/export_page");
	exportPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
	exportPageURL.setParameter("nodeName", node.getName());
	exportPageURL.setParameter("title", wikiPage.getTitle());
	exportPageURL.setParameter("version", String.valueOf(wikiPage.getVersion()));
	exportPageURL.setWindowState(LiferayWindowState.EXCLUSIVE);
	%>

	<tr>
		<th class="table-header">
			<liferay-ui:message key="convert-to" />
		</th>
		<td class="table-cell">
			<liferay-ui:icon-list>

			<%
			for (String conversion : conversions) {
				exportPageURL.setParameter("targetExtension", conversion);
			%>

				<liferay-ui:icon
					image='<%= "../file_system/small/" + conversion %>'
					label="<%= true %>"
					message="<%= StringUtil.toUpperCase(conversion) %>"
					method="get"
					url="<%= exportPageURL.toString() %>"
				/>

			<%
			}
			%>

			</liferay-ui:icon-list>
		</td>
	</tr>
</c:if>

<c:if test="<%= enableRSS %>">
	<tr>
		<th class="table-header">
			<liferay-ui:message key="rss-subscription" />
		</th>
		<td class="table-cell">
			<liferay-ui:rss
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				feedType="<%= rssFeedType %>"
				url='<%= themeDisplay.getPathMain() + "/wiki/rss?p_l_id=" + plid + "&companyId=" + company.getCompanyId() + "&nodeId=" + wikiPage.getNodeId() + "&title=" + wikiPage.getTitle() %>'
			/>
		</td>
	</tr>
</c:if>

<c:if test="<%= (WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.SUBSCRIBE) || WikiNodePermission.contains(permissionChecker, node, ActionKeys.SUBSCRIBE)) && (WikiUtil.getEmailPageAddedEnabled(portletPreferences) || WikiUtil.getEmailPageUpdatedEnabled(portletPreferences)) %>">
	<tr>
		<th class="table-header">
			<liferay-ui:message key="email-subscription" />
		</th>
		<td>
			<table class="lfr-table subscription-info">

			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.SUBSCRIBE) %>">
				<tr>
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiPage.class.getName(), wikiPage.getResourcePrimKey()) %>">
							<td>
								<liferay-ui:message key="you-are-subscribed-to-this-page" />
							</td>
							<td>
								<portlet:actionURL var="unsubscribeURL">
									<portlet:param name="struts_action" value="/wiki/edit_page" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
									<portlet:param name="title" value="<%= String.valueOf(wikiPage.getTitle()) %>" />
								</portlet:actionURL>

								<liferay-ui:icon
									image="unsubscribe"
									label="<%= true %>"
									url="<%= unsubscribeURL %>"
								/>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<liferay-ui:message key="you-are-not-subscribed-to-this-page" />
							</td>
							<td>
								<portlet:actionURL var="subscribeURL">
									<portlet:param name="struts_action" value="/wiki/edit_page" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
									<portlet:param name="title" value="<%= String.valueOf(wikiPage.getTitle()) %>" />
								</portlet:actionURL>

								<liferay-ui:icon
									image="subscribe"
									label="<%= true %>"
									url="<%= subscribeURL %>"
								/>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:if>

			<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.SUBSCRIBE) %>">
				<tr>
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiNode.class.getName(), node.getNodeId()) %>">
							<td>
								<liferay-ui:message key="you-are-subscribed-to-this-wiki" />
							</td>
							<td>
								<portlet:actionURL var="unsubscribeURL">
									<portlet:param name="struts_action" value="/wiki/edit_node" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
								</portlet:actionURL>

								<liferay-ui:icon
									image="unsubscribe"
									label="<%= true %>"
									url="<%= unsubscribeURL %>"
								/>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<liferay-ui:message key="you-are-not-subscribed-to-this-wiki" />
							</td>
							<td>
								<portlet:actionURL var="subscribeURL">
									<portlet:param name="struts_action" value="/wiki/edit_node" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
								</portlet:actionURL>

								<liferay-ui:icon
									image="subscribe"
									label="<%= true %>"
									url="<%= subscribeURL %>"
								/>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:if>

			</table>
		</td>
	</tr>
</c:if>

<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.PERMISSIONS) || (WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE)) || WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">
	<tr>
		<th class="table-header">
			<liferay-ui:message key="advanced-actions" />
		</th>
		<td class="table-cell">
			<liferay-ui:icon-list>
				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="<%= WikiPage.class.getName() %>"
						modelResourceDescription="<%= wikiPage.getTitle() %>"
						resourcePrimKey="<%= String.valueOf(wikiPage.getResourcePrimKey()) %>"
						var="permissionsURL"
						windowState="<%= LiferayWindowState.POP_UP.toString() %>"
					/>

					<liferay-ui:icon
						image="permissions"
						label="<%= true %>"
						method="get"
						url="<%= permissionsURL %>"
						useDialog="<%= true %>"
					/>
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE) %>">

					<%
					PortletURL copyPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

					copyPageURL.setParameter("struts_action", "/wiki/edit_page");
					copyPageURL.setParameter("redirect", viewPageURL.toString());
					copyPageURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
					copyPageURL.setParameter("title", StringPool.BLANK);
					copyPageURL.setParameter("editTitle", "1");
					copyPageURL.setParameter("templateNodeId", String.valueOf(wikiPage.getNodeId()));
					copyPageURL.setParameter("templateTitle", wikiPage.getTitle());
					%>

					<liferay-ui:icon
						image="copy"
						label="<%= true %>"
						url="<%= copyPageURL.toString() %>"
					/>
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE) %>">

					<%
					PortletURL movePageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

					movePageURL.setParameter("struts_action", "/wiki/move_page");
					movePageURL.setParameter("redirect", viewPageURL.toString());
					%>

					<liferay-ui:icon
						image="forward"
						label="<%= true %>"
						message="move"
						url="<%= movePageURL.toString() %>"
					/>
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) %>">

					<%
					PortletURL frontPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

					frontPageURL.setParameter("title", WikiPageConstants.FRONT_PAGE);

					PortletURL deletePageURL = PortletURLUtil.clone(editPageURL, PortletRequest.ACTION_PHASE, renderResponse);

					if (TrashUtil.isTrashEnabled(scopeGroupId)) {
						deletePageURL.setParameter(Constants.CMD, Constants.MOVE_TO_TRASH);
					}
					else {
						deletePageURL.setParameter(Constants.CMD, Constants.DELETE);
					}

					deletePageURL.setParameter("redirect", frontPageURL.toString());
					%>

					<liferay-ui:icon-delete label="<%= true %>" trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>" url="<%= deletePageURL.toString() %>" />
				</c:if>
			</liferay-ui:icon-list>
		</td>
	</tr>
</c:if>
</table>