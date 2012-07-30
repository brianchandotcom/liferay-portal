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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:showCheckbox"));

String title = (String)request.getAttribute("liferay-ui:app-view-entry:title");
String description = (String)request.getAttribute("liferay-ui:app-view-entry:description");

String rowCheckerId = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerId");
String rowCheckerName = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerName");

String actionJsp = (String)request.getAttribute("liferay-ui:app-view-entry:actionJsp");

boolean isFolder = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:isFolder"));
boolean isShortcut = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:isShortcut"));
boolean isLocked = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:isLocked"));

String url = (String)request.getAttribute("liferay-ui:app-view-entry:url");

int status = GetterUtil.getInteger(request.getAttribute("liferay-ui:app-view-entry:status"));

String thumbnailSrc = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailSrc");
String thumbnailStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailStyle");
String thumbnailDivStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailDivStyle");

String displayStyle = (String)request.getAttribute("liferay-ui:app-view-entry:displayStyle");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-entry:data");
%>

<c:choose>
	<c:when test='<%= displayStyle.equals("icon") %>'>
		<div class="app-view-entry-taglib entry-display-style display-<%= displayStyle %> <%= showCheckbox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= StringUtil.shorten(title, 60) %>">
			<c:if test="<%= showCheckbox %>">
					<aui:input cssClass="overlay entry-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
			</c:if>

			<%
			if (!isFolder) {
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			}
			%>

			<liferay-util:include page="<%= actionJsp %>" />

			<a class="entry-link" data-folder="<%= isFolder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" <%= isFolder ? "data-folder-id=\"" + rowCheckerId + "\"" : StringPool.BLANK %> href="<%= url %>" title="<%= HtmlUtil.escapeAttribute(HtmlUtil.unescape(title) + " - " + HtmlUtil.unescape(description)) %>">
				<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
					<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

					<c:if test="<%= isShortcut %>">
						<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
					</c:if>

					<c:if test="<%= isLocked %>">
						<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
					</c:if>

				</div>

				<span class="entry-title">
					<%= StringUtil.shorten(title, 60) %>

					<c:if test="<%= !isFolder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

						<%
						String statusLabel = WorkflowConstants.toLabel(status);
						%>

						<span class="workflow-status-<%= statusLabel %>">
							(<liferay-ui:message key="<%= statusLabel %>" />)
						</span>
					</c:if>
				</span>
			</a>
		</div>
	</c:when>
	<c:when test='<%= displayStyle.equals("descriptive") %>'>
		<div class="app-view-entry-taglib entry-display-style display-<%= displayStyle %> <%= showCheckbox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= StringUtil.shorten(title, 60) %>">
			<a class="entry-link" data-folder="<%= isFolder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-folder-id="<%= rowCheckerId %>" href="<%= url %>" title="<%= HtmlUtil.escape(title) + " - " + HtmlUtil.escape(description) %>">
				<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
					<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

					<c:if test="<%= isShortcut %>">
						<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
					</c:if>

					<c:if test="<%= isLocked %>">
						<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
					</c:if>
				</div>

				<span class="entry-title">
					<%= HtmlUtil.escape(title) %>

					<c:if test="<%= !isFolder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

						<%
						String statusLabel = WorkflowConstants.toLabel(status);
						%>

						<span class="workflow-status-<%= statusLabel %>">
							(<liferay-ui:message key="<%= statusLabel %>" />)
						</span>
					</c:if>
				</span>

				<span class="entry-description"><%= HtmlUtil.escape(description) %></span>
			</a>

			<%
			if (!isFolder) {
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			}
			%>

			<liferay-util:include page="<%= actionJsp %>" />

			<c:if test="<%= showCheckbox %>">
				<aui:input cssClass="overlay entry-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
			</c:if>
		</div>
	</c:when>
	<c:when test='<%= displayStyle.equals("list") %>'>
		<liferay-ui:icon
			cssClass='<%= showCheckbox ? "app-view-entry-taglib entry-display-style selectable" : "app-view-entry-taglib entry-display-style" %>'
			data="<%= data %>"
			image="<%= thumbnailSrc %>"
			label="<%= true %>"
			message="<%= title %>"
			method="get"
			url="<%= url %>"
		/>

		<c:if test="<%= !isFolder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

			<%
			String statusLabel = WorkflowConstants.toLabel(status);
			%>

			<span class="workflow-status-<%= statusLabel %>">
				(<liferay-ui:message key="<%= statusLabel %>" />)
			</span>
		</c:if>
	</c:when>
</c:choose>