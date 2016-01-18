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
String entriesNavigation = ParamUtil.getString(request, "entriesNavigation", "all");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(MBPortletKeys.MESSAGE_BOARDS, "entries-display-style", "descriptive");
}
else {
	portalPreferences.setValue(MBPortletKeys.MESSAGE_BOARDS, "entries-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

int entriesTotal = 0;

long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

Calendar calendar = Calendar.getInstance();

int offset = GetterUtil.getInteger(recentPostsDateOffset);

calendar.add(Calendar.DATE, -offset);

if (entriesNavigation.equals("all")) {
	entriesTotal = MBCategoryLocalServiceUtil.getCategoriesAndThreadsCount(scopeGroupId, categoryId);
}
else if (entriesNavigation.equals("recent")) {
	entriesTotal = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED);
}

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

if (groupThreadsUserId > 0) {
	portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
}
%>

<liferay-frontend:management-bar
	disabled="<%= entriesTotal == 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="mbEntries"
>

	<%
	PortletURL displayStyleURL = renderResponse.createRenderURL();

	if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
		displayStyleURL.setParameter("mvcRenderCommandName", "/message_boards/view");
	}
	else {
		displayStyleURL.setParameter("mvcRenderCommandName", "/message_boards/view_category");
		displayStyleURL.setParameter("categoryId", String.valueOf(categoryId));
	}
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<portlet:renderURL var="viewEntriesHomeURL">
		<portlet:param name="categoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
	</portlet:renderURL>

	<liferay-frontend:management-bar-filters>

		<%
		PortletURL navigationPortletURL = renderResponse.createRenderURL();

		navigationPortletURL.setParameter("categoryId", String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID));
		%>

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "recent"} %>'
			navigationParam="entriesNavigation"
			portletURL="<%= navigationPortletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "lockEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="lock" label="lock" />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "unlockEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="unlock" label="unlock" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
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
			<c:choose>
				<c:when test="<%= parentCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
				</c:when>
				<c:otherwise>
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
				</c:otherwise>
			</c:choose>
		</portlet:renderURL>

		<%
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(backURL.toString());

		renderResponse.setTitle(category.getName());
		%>

	</c:if>

	<%
	MBBreadcrumbUtil.addPortletBreadcrumbEntries(categoryId, request, renderResponse);
	%>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />

		<liferay-ui:search-container
			curParam="cur1"
			emptyResultsMessage="there-are-no-threads-nor-categories"
			id="mbEntries"
			iteratorURL="<%= portletURL %>"
			rowChecker="<%= new EntriesChecker(liferayPortletRequest, liferayPortletResponse) %>"
			total="<%= entriesTotal %>"
			var="categorySearchContainer"
		>

			<%
			int status = WorkflowConstants.STATUS_APPROVED;

			if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
				status = WorkflowConstants.STATUS_ANY;
			}

			List entriesResults = null;

			if (entriesNavigation.equals("all")) {
				entriesResults = MBCategoryServiceUtil.getCategoriesAndThreads(scopeGroupId, categoryId, status, categorySearchContainer.getStart(), categorySearchContainer.getEnd());
			}
			else if (entriesNavigation.equals("recent")) {
				entriesResults = MBThreadServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED, categorySearchContainer.getStart(), categorySearchContainer.getEnd());
			}
			%>

			<liferay-ui:search-container-results
				results="<%= entriesResults %>"
			/>

			<liferay-ui:search-container-row
				className="Object"
				escapedModel="<%= true %>"
				keyProperty="categoryId"
				modelVar="result"
			>

				<%@ include file="/message_boards/cast_result.jspf" %>

				<c:choose>
					<c:when test="<%= curCategory != null %>">

						<%
						row.setPrimaryKey(String.valueOf(curCategory.getCategoryId()));
						%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
							<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<h4>
								<aui:a href="<%= rowURL.toString() %>">
									<%= HtmlUtil.escape(curCategory.getName()) %>
								</aui:a>
							</h4>

							<h5 class="text-default">
								<%= HtmlUtil.escape(curCategory.getDescription()) %>
							</h5>

							<%
							int subcategoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, curCategory.getCategoryId());
							int threadsCount = MBThreadServiceUtil.getThreadsCount(scopeGroupId, curCategory.getCategoryId(), WorkflowConstants.STATUS_APPROVED);
							%>

							<span class="h6">
								<liferay-ui:message arguments="<%= subcategoriesCount %>" key='<%= subcategoriesCount == 1 ? "x-subcategory" : "x-subcategories" %>' />
							</span>

							<span class="h6">
								<liferay-ui:message arguments="<%= threadsCount %>" key='<%= threadsCount == 1 ? "x-thread" : "x-threads" %>' />
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							path="/message_boards/category_action.jsp"
						/>
					</c:when>
					<c:otherwise>

						<%
						MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(thread.getRootMessageId());

						if (message == null) {
							_log.error("Thread requires missing root message id " + thread.getRootMessageId());

							message = new MBMessageImpl();

							row.setSkip(true);
						}

						message = message.toEscapedModel();

						row.setBold(!MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread));
						row.setPrimaryKey(String.valueOf(thread.getThreadId()));
						row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
						%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
							<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-user
							cssClass="user-icon-lg"
							showDetails="<%= false %>"
							userId="<%= message.getUserId() %>"
						/>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">

							<%
							Date modifiedDate = message.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
							%>

							<h5 class="text-default">
								<liferay-ui:message arguments="<%= new String[] {message.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</h5>

							<h4>
								<aui:a href="<%= rowURL.toString() %>">
									<%= message.getSubject() %>
								</aui:a>
							</h4>

							<%
							int messageCount = thread.getMessageCount();
							int viewCount = thread.getViewCount();
							%>

							<span class="h6">
								<liferay-ui:message arguments="<%= messageCount %>" key='<%= messageCount == 1 ? "x-post" : "x-posts" %>' />
							</span>

							<span class="h6">
								<liferay-ui:message arguments="<%= viewCount %>" key='<%= viewCount == 1 ? "x-view" : "x-views" %>' />
							</span>
						</liferay-ui:search-container-column-text>

						<%
						row.setObject(new Object[] {message});
						%>

						<liferay-ui:search-container-column-jsp
							path="/message_boards/message_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new MBResultRowSplitter() %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<liferay-util:include page="/message_boards_admin/add_button.jsp" servletContext="<%= application %>" />

<%
if (category != null) {
	PortalUtil.setPageSubtitle(category.getName(), request);
	PortalUtil.setPageDescription(category.getDescription(), request);
}
%>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, TrashUtil.isTrashEnabled(scopeGroupId) ? "are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin" : "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
		}
	}

	function <portlet:namespace />lockEntries() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.LOCK %>');

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
	}

	function <portlet:namespace />unlockEntries() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.UNLOCK %>');

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
	}
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards_admin.view_entries_jsp");
%>