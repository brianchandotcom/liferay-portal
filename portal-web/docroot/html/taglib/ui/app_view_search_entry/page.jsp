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

<%@ include file="/html/taglib/ui/app_view_search_entry/init.jsp" %>

<%
String actionJsp = (String)request.getAttribute("liferay-ui:app-view-search-entry:actionJsp");
String description = (String)request.getAttribute("liferay-ui:app-view-search-entry:description");
String cssClass = (String)request.getAttribute("liferay-ui:app-view-search-entry:cssClass");
String folderName = (String)request.getAttribute("liferay-ui:app-view-search-entry:folderName");
boolean locked = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-search-entry:locked"));
List<MBMessage> messages = (List<MBMessage>)request.getAttribute("liferay-ui:app-view-search-entry:messages");
String[] queryTerms = (String[])request.getAttribute("liferay-ui:app-view-search-entry:queryTerms");
String rowCheckerId = (String)request.getAttribute("liferay-ui:app-view-search-entry:rowCheckerId");
String rowCheckerName = (String)request.getAttribute("liferay-ui:app-view-search-entry:rowCheckerName");
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-search-entry:showCheckbox"));
int status = GetterUtil.getInteger(request.getAttribute("liferay-ui:app-view-search-entry:status"));
String thumbnailSrc = (String)request.getAttribute("liferay-ui:app-view-search-entry:thumbnailSrc");
String title = (String)request.getAttribute("liferay-ui:app-view-search-entry:title");
String url = (String)request.getAttribute("liferay-ui:app-view-search-entry:url");

String shortTitle = StringUtil.shorten(title, 60);
%>

<div class="app-view-entry app-view-search-entry-taglib entry-display-style <%= showCheckbox ? "selectable" : StringPool.BLANK %> <%= cssClass %>" data-title="<%= HtmlUtil.escapeAttribute(shortTitle) %>">
	<a class="entry-link" href="<%= url %>" title="<%= HtmlUtil.escapeAttribute(title + " - " + description) %>">
		<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
			<div class="entry-thumbnail">
				<img alt="" class="entry-thumbnail-image" src="<%= thumbnailSrc %>" />

				<c:if test="<%= locked %>">
					<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
				</c:if>
			</div>
		</c:if>

		<span class="entry-title">
			<%= StringUtil.highlight(HtmlUtil.escape(title), queryTerms) %>

			<c:if test="<%= ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

				<%
				String statusLabel = WorkflowConstants.toLabel(status);
				%>

				<span class="workflow-status-<%= statusLabel %>">
					(<liferay-ui:message key="<%= statusLabel %>" />)
				</span>
			</c:if>
		</span>

		<c:if test="<%= Validator.isNotNull(folderName) %>">
			<span class="entry-folder">
				<liferay-ui:icon
					image="folder"
					label="<%= true %>"
					message='<%= LanguageUtil.format(locale, "found-in-folder-x", folderName) %>'
				/>
			</span>
		</c:if>

		<span class="entry-description">
			<%= StringUtil.highlight(HtmlUtil.escape(description), queryTerms) %>
		</span>
	</a>

	<c:if test="<%= messages != null %>">

		<%
		for (MBMessage message : messages) {
			User userDisplay = UserLocalServiceUtil.getUser(message.getUserId());
		%>

			<div class="entry-discussion">
				<aui:a class="lfr-discussion-details" href="<%= url %>">
					<div class="entry-discussion-image">
						<img alt="<%= HtmlUtil.escapeAttribute(userDisplay.getFullName()) %>" class="avatar" src="<%= HtmlUtil.escape(userDisplay.getPortraitURL(themeDisplay)) %>" />
					</div>

					<span class="entry-discussion-title">
						<liferay-ui:icon
							image="message"
							label="<%= true %>"
							message='<%= LanguageUtil.format(locale, "comment-by-x", HtmlUtil.escape(userDisplay.getFullName())) %>'
						/>
					</span>

					<span cssClass="entry-discussion-body">
						<%= StringUtil.highlight(message.getSubject(), queryTerms) %>
					</span>
				</aui:a>
			</div>

		<%
		}
		%>

	</c:if>

	<c:if test="<%= showCheckbox %>">
		<aui:input cssClass="overlay entry-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
	</c:if>

	<c:if test="<%= Validator.isNotNull(actionJsp) %>">
		<liferay-util:include page="<%= actionJsp %>" />
	</c:if>
</div>