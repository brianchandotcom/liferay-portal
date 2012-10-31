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
String actionJsp = (String)request.getAttribute("liferay-ui:app-view-entry:actionJsp");
String assetCategoryClassName = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:assetCategoryClassName"));
long assetCategoryClassPK = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:assetCategoryClassPK"));
String assetTagClassName = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:assetTagClassName"));
long assetTagClassPK = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:assetTagClassPK"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-entry:data");
String description = (String)request.getAttribute("liferay-ui:app-view-entry:description");
String displayStyle = (String)request.getAttribute("liferay-ui:app-view-entry:displayStyle");
boolean folder = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:folder"));
boolean locked = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:locked"));
String rowCheckerId = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerId");
String rowCheckerName = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerName");
boolean shortcut = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:shortcut"));
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:showCheckbox"));
int status = GetterUtil.getInteger(request.getAttribute("liferay-ui:app-view-entry:status"));
String thumbnailDivStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailDivStyle");
String thumbnailSrc = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailSrc");
String thumbnailStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailStyle");
String title = (String)request.getAttribute("liferay-ui:app-view-entry:title");
String url = (String)request.getAttribute("liferay-ui:app-view-entry:url");

String shortTitle = StringUtil.shorten(title, 60);
%>

<c:choose>
	<c:when test='<%= displayStyle.equals("icon") %>'>
		<div class="app-view-entry-taglib entry-display-style display-<%= displayStyle %> <%= showCheckbox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= HtmlUtil.escapeAttribute(shortTitle) %>">
			<c:if test="<%= showCheckbox %>">
					<aui:input cssClass="overlay entry-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
			</c:if>

			<%
			if (!folder) {
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			}
			%>

			<liferay-util:include page="<%= actionJsp %>" />

			<a class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" <%= folder ? "data-folder-id=\"" + rowCheckerId + "\"" : StringPool.BLANK %> href="<%= url %>" title="<%= HtmlUtil.escapeAttribute(HtmlUtil.unescape(title) + " - " + HtmlUtil.unescape(description)) %>">
				<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
					<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

					<c:if test="<%= shortcut %>">
						<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
					</c:if>

					<c:if test="<%= locked %>">
						<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
					</c:if>

				</div>

				<span class="entry-title">
					<%= HtmlUtil.escape(shortTitle) %>

					<c:if test="<%= !folder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

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
		<div class="app-view-entry-taglib entry-display-style display-<%= displayStyle %> <%= showCheckbox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= HtmlUtil.escapeAttribute(shortTitle) %>">
			<a class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-folder-id="<%= rowCheckerId %>" href="<%= url %>" title="<%= HtmlUtil.escapeAttribute(title + " - " + description) %>">
				<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
					<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

					<c:if test="<%= shortcut %>">
						<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
					</c:if>

					<c:if test="<%= locked %>">
						<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
					</c:if>
				</div>

				<span class="entry-title">
					<%= HtmlUtil.escape(title) %>

					<c:if test="<%= !folder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

						<%
						String statusLabel = WorkflowConstants.toLabel(status);
						%>

						<span class="workflow-status-<%= statusLabel %>">
							(<liferay-ui:message key="<%= statusLabel %>" />)
						</span>
					</c:if>
				</span>

				<span class="entry-description">
					<%= HtmlUtil.escape(description) %>

					<c:if test="<%= Validator.isNotNull(assetCategoryClassName) && (assetCategoryClassPK > 0) %>">
						<div class="categories">
							<liferay-ui:asset-categories-summary
								className="<%= assetCategoryClassName %>"
								classPK="<%= assetCategoryClassPK %>"
							/>
						</div>
					</c:if>

					<c:if test="<%= Validator.isNotNull(assetTagClassName) && (assetTagClassPK > 0) %>">
						<div class="tags">
							<liferay-ui:asset-tags-summary
								className="<%= assetTagClassName %>"
								classPK="<%= assetTagClassPK %>"
							/>
						</div>
					</c:if>
				</span>
			</a>

			<%
			if (!folder) {
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
			linkCssClass="entry-link"
			localizeMessage="<%= false %>"
			message="<%= title %>"
			method="get"
			url="<%= url %>"
		/>

		<c:if test="<%= !folder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

			<%
			String statusLabel = WorkflowConstants.toLabel(status);
			%>

			<span class="workflow-status-<%= statusLabel %>">
				(<liferay-ui:message key="<%= statusLabel %>" />)
			</span>
		</c:if>
	</c:when>
</c:choose>