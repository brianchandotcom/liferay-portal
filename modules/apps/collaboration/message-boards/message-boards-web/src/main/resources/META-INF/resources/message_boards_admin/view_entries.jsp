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
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

SearchContainer entriesSearchContainer = (SearchContainer)request.getAttribute("view.jsp-entriesSearchContainer");

long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

boolean showBreadcrumb = ParamUtil.getBoolean(request, "showBreadcrumb", true);

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

if (groupThreadsUserId > 0) {
	portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
%>

<div class="container-fluid-1280 view-entries-container">
	<c:if test="<%= category != null %>">

		<%
		long parentCategoryId = category.getParentCategoryId();

		if (!category.isRoot()) {
			MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

			parentCategoryId = parentCategory.getCategoryId();
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
		if (portletTitleBasedNavigation) {
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(backURL.toString());

			renderResponse.setTitle(category.getName());
		}
		%>

	</c:if>

	<c:if test="<%= showBreadcrumb %>">

		<%
		MBBreadcrumbUtil.addPortletBreadcrumbEntries(categoryId, request, renderResponse);
		%>

		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</c:if>

	<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= entriesSearchContainer %>"
		>
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
									<%= curCategory.getName() %>
								</aui:a>
							</h4>

							<h5 class="text-default">
								<%= curCategory.getDescription() %>
							</h5>

							<%
							int subcategoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, curCategory.getCategoryId(), WorkflowConstants.STATUS_APPROVED);
							int threadsCount = MBThreadServiceUtil.getThreadsCount(scopeGroupId, curCategory.getCategoryId(), WorkflowConstants.STATUS_APPROVED);
							%>

							<span class="h6 text-default">
								<liferay-ui:message arguments="<%= subcategoriesCount %>" key='<%= subcategoriesCount == 1 ? "x-subcategory" : "x-subcategories" %>' />
							</span>
							<span class="h6 text-default">
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

							row.setSkip(true);
						}

						if (message != null) {
							message = message.toEscapedModel();

							row.setPrimaryKey(String.valueOf(thread.getThreadId()));
							row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
						}
						%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
							<portlet:param name="messageId" value="<%= (message != null) ? String.valueOf(message.getMessageId()) : String.valueOf(MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-text>
							<liferay-ui:user-portrait
								userId="<%= thread.getLastPostByUserId() %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<c:choose>
								<c:when test="<%= ((message != null) && (thread.getMessageCount() == 1)) %>">

									<%
									String messageUserName = "anonymous";

									if (!message.isAnonymous()) {
										messageUserName = message.getUserName();
									}

									Date modifiedDate = message.getModifiedDate();

									String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
									%>

									<h5 class="text-default">
										<liferay-ui:message arguments="<%= new String[] {messageUserName, modifiedDateDescription} %>" key="x-modified-x-ago" />
									</h5>
								</c:when>
								<c:otherwise>

									<%
									String messageUserName = "anonymous";

									if (thread.getLastPostByUserId() != 0) {
										messageUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));
									}

									Date lastPostDate = thread.getLastPostDate();

									String lastPostDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - lastPostDate.getTime(), true);
									%>

									<h5 class="text-default">
										<liferay-ui:message arguments="<%= new String[] {messageUserName, lastPostDateDescription} %>" key="x-replied-x-ago" />
									</h5>
								</c:otherwise>
							</c:choose>

							<h4>
								<aui:a href="<%= rowURL.toString() %>">
									<c:if test="<%= (message != null) %>">
										<c:choose>
											<c:when test="<%= !MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread) %>">
												<strong><%= message.getSubject() %></strong>
											</c:when>
											<c:otherwise>
												<%= message.getSubject() %>
											</c:otherwise>
										</c:choose>
									</c:if>
								</aui:a>

								<%
								String[] threadPriority = MBUtil.getThreadPriority(mbGroupServiceSettings, themeDisplay.getLanguageId(), thread.getPriority());
								%>

								<c:if test="<%= (threadPriority != null) && (thread.getPriority() > 0) %>">
									<span class="text-default <%= threadPriority[1] %>" title="<%= HtmlUtil.escapeAttribute(threadPriority[0]) %>"></span>
								</c:if>

								<c:if test="<%= thread.isQuestion() %>">
									<aui:icon cssClass="icon-monospaced" image="question-circle" markupView="lexicon" message="question" />
								</c:if>
							</h4>

							<c:if test="<%= portletTitleBasedNavigation || ((message != null) && !message.isApproved()) %>">
								<span class="h6">
									<aui:workflow-status bean="<%= message %>" markupView="lexicon" model="<%= MBMessage.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= message.getStatus() %>" />
								</span>
							</c:if>

							<%
							int repliesCount = Math.max(thread.getMessageCount() - 1, 0);
							int viewCount = thread.getViewCount();
							%>

							<span class="h6 text-default">
								<liferay-ui:message arguments="<%= repliesCount %>" key='<%= repliesCount == 1 ? "x-reply" : "x-replies" %>' />
							</span>
							<span class="h6 text-default">
								<liferay-ui:message arguments="<%= viewCount %>" key='<%= viewCount == 1 ? "x-view" : "x-views" %>' />
							</span>

							<c:if test="<%= thread.isQuestion() %>">

								<%
								int threadAnswersCount = MBMessageServiceUtil.getThreadAnswersCount(thread.getGroupId(), thread.getCategoryId(), thread.getThreadId());
								%>

								<span class="h6">
									<%= threadAnswersCount %>

									<liferay-ui:message key='<%= threadAnswersCount == 1 ? "answer" : "answers" %>' />
								</span>
							</c:if>

							<c:if test="<%= thread.isLocked() %>">
								<span class="h6">
									<liferay-ui:message key="locked" />
								</span>
							</c:if>
						</liferay-ui:search-container-column-text>

						<%
						row.setObject(new Object[] {message});
						%>

						<c:if test="<%= (message != null) %>">
							<liferay-ui:search-container-column-jsp
								path="/message_boards/message_action.jsp"
							/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle='<%= GetterUtil.getString(request.getAttribute("view.jsp-displayStyle")) %>' markupView="lexicon" resultRowSplitter="<%= new MBResultRowSplitter() %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards_admin.view_entries_jsp");
%>