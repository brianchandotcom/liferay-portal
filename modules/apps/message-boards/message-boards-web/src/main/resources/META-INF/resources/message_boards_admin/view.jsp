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

<%@ include file="/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());

if ((category != null) && layout.isTypeControlPanel()) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
}
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<%
PortletURL navigationURL = renderResponse.createRenderURL();

navigationURL.setParameter("mvcRenderCommandName", "/message_boards/view");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		navigationURL.setParameter("top-link", "message-boards-home");
		navigationURL.setParameter("tag", StringPool.BLANK);
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="message-boards-home"
			selected='<%= topLink.equals("message-boards-home") %>'
		/>

		<%
		navigationURL.setParameter("topLink", "statistics");
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="statistics"
			selected='<%= topLink.equals("statistics") %>'
		/>

		<%
		navigationURL.setParameter("topLink", "banned-users");
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="banned-users"
			selected='<%= topLink.equals("banned-users") %>'
		/>
	</aui:nav>

	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
	</liferay-portlet:renderURL>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test='<%= topLink.equals("message-boards-home") %>'>
			<c:if test="<%= category != null %>">

				<%
				long parentCategoryId = category.getParentCategoryId();
				String parentCategoryName = LanguageUtil.get(request, "message-boards-home");

				if (!category.isRoot()) {
					MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

					parentCategoryId = parentCategory.getCategoryId();
					parentCategoryName = parentCategory.getName();
				}
				%>

				<portlet:renderURL var="backURL">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
				</portlet:renderURL>

				<liferay-ui:header
					backLabel="<%= parentCategoryName %>"
					backURL="<%= backURL.toString() %>"
					localizeTitle="<%= false %>"
					title="<%= category.getName() %>"
				/>
			</c:if>

			<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="messageBoardsPanelContainer" persistState="<%= true %>">

				<%
				int categoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED);
				%>

				<c:if test="<%= categoriesCount > 0 %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="messageBoardsCategoriesPanel" persistState="<%= true %>" title='<%= (category != null) ? "subcategories" : "categories" %>'>
						<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
							<aui:input name="<%= Constants.CMD %>" type="hidden" />
							<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
							<aui:input name="deleteCategoryIds" type="hidden" />

							<liferay-ui:search-container
								curParam="cur1"
								deltaConfigurable="<%= false %>"
								headerNames="category,categories,threads,posts"
								iteratorURL="<%= portletURL %>"
								rowChecker="<%= new RowChecker(renderResponse) %>"
								total="<%= categoriesCount %>"
								var="categorySearchContainer"
							>
								<liferay-ui:search-container-results
									results="<%= MBCategoryServiceUtil.getCategories(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED, categorySearchContainer.getStart(), categorySearchContainer.getEnd()) %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portlet.messageboards.model.MBCategory"
									escapedModel="<%= true %>"
									keyProperty="categoryId"
									modelVar="curCategory"
								>
									<liferay-portlet:renderURL varImpl="rowURL">
										<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
										<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
									</liferay-portlet:renderURL>

									<%@ include file="/message_boards/category_columns.jspf" %>
								</liferay-ui:search-container-row>

								<br>

								<aui:button disabled="<%= true %>" name="deleteCategory" onClick='<%= renderResponse.getNamespace() + "deleteCategories();" %>' value='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "move-to-the-recycle-bin" : "delete" %>' />

								<div class="separator"><!-- --></div>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</aui:form>
					</liferay-ui:panel>
				</c:if>

				<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" id="messageBoardsThreadsPanel" persistState="<%= true %>" title="threads">
					<aui:form action="<%= portletURL.toString() %>" method="get" name="fm1">

						<%
						int status = WorkflowConstants.STATUS_APPROVED;

						if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
							status = WorkflowConstants.STATUS_ANY;
						}
						%>

						<aui:input name="<%= Constants.CMD %>" type="hidden" />
						<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
						<aui:input name="threadIds" type="hidden" />

						<liferay-ui:search-container
							curParam="cur2"
							emptyResultsMessage="there-are-no-threads-in-this-category"
							headerNames="thread,flag,started-by,posts,views,last-post"
							iteratorURL="<%= portletURL %>"
							rowChecker="<%= new RowChecker(renderResponse) %>"
							total="<%= MBThreadServiceUtil.getThreadsCount(scopeGroupId, categoryId, status) %>"
							var="threadSearchContainer"
						>
							<liferay-ui:search-container-results
								results="<%= MBThreadServiceUtil.getThreads(scopeGroupId, categoryId, status, threadSearchContainer.getStart(), threadSearchContainer.getEnd()) %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portlet.messageboards.model.MBThread"
								keyProperty="threadId"
								modelVar="thread"
							>

								<%
								MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(thread.getRootMessageId());

								if (message == null) {
									_log.error("Thread requires missing root message id " + thread.getRootMessageId());

									message = new MBMessageImpl();

									row.setSkip(true);
								}

								message = message.toEscapedModel();

								row.setBold(!MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread));
								row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
								%>

								<liferay-portlet:renderURL varImpl="rowURL">
									<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
									<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
								</liferay-portlet:renderURL>

								<%@ include file="/message_boards/thread_priority.jspf" %>

								<liferay-ui:search-container-column-text
									href="<%= rowURL %>"
									name="flag"
								>
									<c:choose>
										<c:when test="<%= MBThreadLocalServiceUtil.hasAnswerMessage(thread.getThreadId()) %>">
											<liferay-ui:message key="resolved" />
										</c:when>
										<c:when test="<%= thread.isQuestion() %>">
											<liferay-ui:message key="waiting-for-an-answer" />
										</c:when>
									</c:choose>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									href="<%= rowURL %>"
									name="started-by"
									value='<%= message.isAnonymous() ? LanguageUtil.get(request, "anonymous") : PortalUtil.getUserName(message) %>'
								/>

								<liferay-ui:search-container-column-text
									href="<%= rowURL %>"
									name="posts"
									value="<%= String.valueOf(thread.getMessageCount()) %>"
								/>

								<liferay-ui:search-container-column-text
									href="<%= rowURL %>"
									name="views"
									value="<%= String.valueOf(thread.getViewCount()) %>"
								/>

								<liferay-ui:search-container-column-user
									date="<%= thread.getLastPostDate() %>"
									name="last-post"
									property="lastPostByUserId"
								/>

								<liferay-ui:search-container-column-status
									href="<%= rowURL %>"
									name="status"
									status="<%= thread.getStatus() %>"
								/>

								<%
								row.setObject(new Object[] {message});
								%>

								<liferay-ui:search-container-column-jsp
									align="right"
									cssClass="entry-action"
									path="/message_boards/message_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<br>

							<c:if test="<%= !results.isEmpty() %>">
								<aui:button disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteThreads();" %>' value='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "move-to-the-recycle-bin" : "delete" %>' />

								<aui:button disabled="<%= true %>" name="lockThread" onClick='<%= renderResponse.getNamespace() + "lockThreads();" %>' value="lock" />

								<aui:button disabled="<%= true %>" name="unlockThread" onClick='<%= renderResponse.getNamespace() + "unlockThreads();" %>' value="unlock" />

								<div class="separator"><!-- --></div>
							</c:if>

							<liferay-ui:search-iterator />
						</liferay-ui:search-container>
					</aui:form>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<%
			if (category != null) {
				PortalUtil.setPageSubtitle(category.getName(), request);
				PortalUtil.setPageDescription(category.getDescription(), request);

				MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
			}
			%>

		</c:when>
		<c:when test='<%= topLink.equals("recent-posts") %>'>

			<%
			long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

			if (groupThreadsUserId > 0) {
				portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
			}
			%>

			<c:if test="<%= groupThreadsUserId > 0 %>">
				<div class="alert alert-info">
					<liferay-ui:message key="filter-by-user" />: <%= HtmlUtil.escape(PortalUtil.getUserName(groupThreadsUserId, StringPool.BLANK)) %>
				</div>
			</c:if>

			<aui:form action="<%= portletURL.toString() %>" method="get" name="fm1">

				<%
				portletURL.setParameter("topLink", ParamUtil.getString(request, "topLink"));
				%>

				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="threadIds" type="hidden" />

				<liferay-ui:search-container
					emptyResultsMessage="there-are-no-recent-posts"
					headerNames="thread,started-by,posts,views,last-post"
					iteratorURL="<%= portletURL %>"
					rowChecker="<%= new RowChecker(renderResponse) %>"
					var="threadSearchContainer"
				>
					<liferay-ui:search-container-results>

						<%
						Calendar calendar = Calendar.getInstance();

						int offset = GetterUtil.getInteger(recentPostsDateOffset);

						calendar.add(Calendar.DATE, -offset);

						total = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED);

						threadSearchContainer.setTotal(total);

						results = MBThreadServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED, threadSearchContainer.getStart(), threadSearchContainer.getEnd());

						threadSearchContainer.setResults(results);
						%>

					</liferay-ui:search-container-results>

					<liferay-ui:search-container-row
						className="com.liferay.portlet.messageboards.model.MBThread"
						keyProperty="threadId"
						modelVar="thread"
					>

						<%
						MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(thread.getRootMessageId());

						if (message == null) {
							_log.error("Thread requires missing root message id " + thread.getRootMessageId());

							continue;
						}

						message = message.toEscapedModel();

						row.setBold(!MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread));
						row.setObject(new Object[] {message});
						row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
						%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
							<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
						</liferay-portlet:renderURL>

						<%@ include file="/message_boards/thread_priority.jspf" %>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="started-by"
							value='<%= message.isAnonymous() ? LanguageUtil.get(request, "anonymous") : PortalUtil.getUserName(message) %>'
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="posts"
							value="<%= String.valueOf(thread.getMessageCount()) %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="views"
							value="<%= String.valueOf(thread.getViewCount()) %>"
						/>

						<liferay-ui:search-container-column-user
							date="<%= thread.getLastPostDate() %>"
							name="last-post"
							userId="<%= thread.getLastPostByUserId() %>"
						/>

						<liferay-ui:search-container-column-jsp
							align="right"
							cssClass="entry-action"
							path="/message_boards/message_action.jsp"
						/>
					</liferay-ui:search-container-row>

					<br>

					<c:if test="<%= !results.isEmpty() %>">
						<aui:button disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteThreads();" %>' value='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "move-to-the-recycle-bin" : "delete" %>' />

						<aui:button disabled="<%= true %>" name="lockThread" onClick='<%= renderResponse.getNamespace() + "lockThreads();" %>' value="lock" />

						<aui:button disabled="<%= true %>" name="unlockThread" onClick='<%= renderResponse.getNamespace() + "unlockThreads();" %>' value="unlock" />

						<div class="separator"><!-- --></div>
					</c:if>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			</aui:form>

			<%
			PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
			%>

		</c:when>
		<c:when test='<%= topLink.equals("statistics") %>'>
			<liferay-ui:panel-container cssClass="statistics-panel" extended="<%= false %>" id="messageBoardsStatisticsPanelContainer" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsGeneralStatisticsPanel" persistState="<%= true %>" title="general">
					<dl>
						<dt>
							<liferay-ui:message key="num-of-categories" />:
						</dt>
						<dd>
							<%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %>
						</dd>
						<dt>
							<liferay-ui:message key="num-of-posts" />:
						</dt>
						<dd>
							<%= numberFormat.format(MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId)) %>
						</dd>
						<dt>
							<liferay-ui:message key="num-of-participants" />:
						</dt>
						<dd>
							<%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %>
						</dd>
					</dl>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsTopPostersPanel" persistState="<%= true %>" title="top-posters">
					<liferay-ui:search-container
						emptyResultsMessage="there-are-no-top-posters"
						iteratorURL="<%= portletURL %>"
						total="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId) %>"
					>
						<liferay-ui:search-container-results
							results="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portlet.messageboards.model.MBStatsUser"
							keyProperty="statsUserId"
							modelVar="statsUser"
						>
							<liferay-ui:search-container-column-jsp
								path="/message_boards/top_posters_user_display.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<%
			PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
			%>

		</c:when>
		<c:when test='<%= topLink.equals("banned-users") %>'>
			<liferay-ui:search-container
				emptyResultsMessage="there-are-no-banned-users"
				headerNames="banned-user,banned-by,ban-date"
				iteratorURL="<%= portletURL %>"
				total="<%= MBBanLocalServiceUtil.getBansCount(scopeGroupId) %>"
			>
				<liferay-ui:search-container-results
					results="<%= MBBanLocalServiceUtil.getBans(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.messageboards.model.MBBan"
					keyProperty="banId"
					modelVar="ban"
				>
					<liferay-ui:search-container-column-text
						name="banned-user"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="banned-by"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK)) %>"
					/>

					<liferay-ui:search-container-column-date
						name="ban-date"
						value="<%= ban.getCreateDate() %>"
					/>

					<c:if test="<%= PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0 %>">
						<liferay-ui:search-container-column-text
							name="unban-date"
							value="<%= dateFormatDateTime.format(MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL)) %>"
						/>
					</c:if>

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/message_boards/ban_user_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<%
			PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
			%>

		</c:when>
	</c:choose>
</div>

<liferay-util:include page="/message_boards_admin/add_button.jsp" servletContext="<%= application %>" />

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />deleteCategory', '#<portlet:namespace /><%= searchContainerReference.getId("categorySearchContainer") %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId("threadSearchContainer") %>SearchContainer', document.<portlet:namespace />fm1, '<portlet:namespace />allRowIds');
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />lockThread', '#<portlet:namespace /><%= searchContainerReference.getId("threadSearchContainer") %>SearchContainer', document.<portlet:namespace />fm1, '<portlet:namespace />allRowIds');
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />unlockThread', '#<portlet:namespace /><%= searchContainerReference.getId("threadSearchContainer") %>SearchContainer', document.<portlet:namespace />fm1, '<portlet:namespace />allRowIds');

	function <portlet:namespace />deleteCategories() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, TrashUtil.isTrashEnabled(scopeGroupId) ? "are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin" : "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
			form.fm('deleteCategoryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="/message_boards/edit_category" />');
		}
	}

	function <portlet:namespace />deleteThreads() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, TrashUtil.isTrashEnabled(scopeGroupId) ? "are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin" : "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm1);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
			form.fm('threadIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="/message_boards/delete_thread" />');
		}
	}

	function <portlet:namespace />lockThreads() {
		var form = AUI.$(document.<portlet:namespace />fm1);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.LOCK %>');
		form.fm('threadIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_message" />');
	}

	function <portlet:namespace />unlockThreads() {
		var form = AUI.$(document.<portlet:namespace />fm1);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.UNLOCK %>');
		form.fm('threadIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_message" />');
	}
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards_admin.view_jsp");
%>