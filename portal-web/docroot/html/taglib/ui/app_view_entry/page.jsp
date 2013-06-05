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

<%@ include file="/html/taglib/init.jsp" %>

<%
String actionJsp = (String)request.getAttribute("liferay-ui:app-view-entry:actionJsp");
String assetCategoryClassName = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:assetCategoryClassName"));
long assetCategoryClassPK = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:assetCategoryClassPK"));
String assetTagClassName = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:assetTagClassName"));
long assetTagClassPK = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:assetTagClassPK"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:app-view-entry:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-entry:data");
String description = (String)request.getAttribute("liferay-ui:app-view-entry:description");
String displayStyle = (String)request.getAttribute("liferay-ui:app-view-entry:displayStyle");
boolean folder = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:folder"));
boolean locked = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:locked"));
String rowCheckerId = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerId");
String rowCheckerName = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerName");
boolean shortcut = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:shortcut"));
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:showCheckbox"));
boolean showLinkTitle = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:showLinkTitle"));
int status = GetterUtil.getInteger(request.getAttribute("liferay-ui:app-view-entry:status"));
String thumbnailDivStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailDivStyle");
String thumbnailSrc = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailSrc");
String thumbnailStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailStyle");
String title = (String)request.getAttribute("liferay-ui:app-view-entry:title");
String url = (String)request.getAttribute("liferay-ui:app-view-entry:url");

String shortTitle = StringUtil.shorten(title, 60);

String linkTitle = StringPool.BLANK;

if (showLinkTitle) {
	linkTitle = HtmlUtil.escapeAttribute(HtmlUtil.unescape(title) + " - " + HtmlUtil.unescape(description));
}
%>

<c:choose>
	<c:when test='<%= displayStyle.equals("icon") %>'>
		<div class="app-view-entry app-view-entry-taglib entry-display-style display-<%= displayStyle %><aui:spacer /><%= showCheckbox ? "selectable" : StringPool.BLANK %><aui:spacer /><%= cssClass %>" <%= AUIUtil.buildData(data) %> data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= HtmlUtil.escapeAttribute(shortTitle) %>">
			<c:if test="<%= showCheckbox %>">
				<aui:input cssClass="overlay entry-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
			</c:if>

			<%
			if (!folder) {
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			}
			%>

			<liferay-util:include page="<%= actionJsp %>" />

			<c:choose>
				<c:when test="<%= Validator.isNull(url) %>">
					<span class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" <%= folder ? "data-folder-id=\"" + rowCheckerId + "\"" : StringPool.BLANK %> title="<%= linkTitle %>">
				</c:when>
				<c:otherwise>
					<a class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" <%= folder ? "data-folder-id=\"" + rowCheckerId + "\"" : StringPool.BLANK %> href="<%= url %>" title="<%= linkTitle %>">
				</c:otherwise>
			</c:choose>
				<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
					<img alt="" border="no" class="img-polaroid" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

					<c:if test="<%= shortcut %>">
						<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon img-polaroid" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
					</c:if>

					<c:if test="<%= locked %>">
						<img alt="<liferay-ui:message key="locked" />" class="locked-icon img-polaroid" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
					</c:if>

				</div>

				<span class="entry-title">
					<span class="entry-title-text">
						<%= HtmlUtil.escape(shortTitle) %>
					</span>

					<c:if test="<%= !folder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

						<%
						String statusLabel = WorkflowConstants.toLabel(status);
						%>

						<span class="workflow-status-<%= statusLabel %>">
							(<liferay-ui:message key="<%= statusLabel %>" />)
						</span>
					</c:if>

					<span class="entry-result-icon"></span>
				</span>
			<c:choose>
				<c:when test="<%= Validator.isNull(url) %>">
					</span>
				</c:when>
				<c:otherwise>
					</a>
				</c:otherwise>
			</c:choose>
		</div>
	</c:when>
	<c:when test='<%= displayStyle.equals("descriptive") %>'>
		<div class="app-view-entry app-view-entry-taglib entry-display-style display-<%= displayStyle %><aui:spacer /><%= showCheckbox ? "selectable" : StringPool.BLANK %><aui:spacer /><%= cssClass %>" <%= AUIUtil.buildData(data) %> data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= HtmlUtil.escapeAttribute(shortTitle) %>">
			<c:choose>
				<c:when test="<%= Validator.isNull(url) %>">
					<span class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-folder-id="<%= rowCheckerId %>" title="<%= linkTitle %>">
				</c:when>
				<c:otherwise>
					<a class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-folder-id="<%= rowCheckerId %>" href="<%= url %>" title="<%= linkTitle %>">
				</c:otherwise>
			</c:choose>
				<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
					<img alt="" border="no" class="img-polaroid" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

					<c:if test="<%= shortcut %>">
						<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon img-polaroid" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
					</c:if>

					<c:if test="<%= locked %>">
						<img alt="<liferay-ui:message key="locked" />" class="locked-icon img-polaroid" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
					</c:if>
				</div>

				<span class="entry-title">
					<span class="entry-title-text">
						<%= HtmlUtil.escape(title) %>
					</span>

					<c:if test="<%= !folder && ((status == WorkflowConstants.STATUS_DRAFT) || (status == WorkflowConstants.STATUS_PENDING)) %>">

						<%
						String statusLabel = WorkflowConstants.toLabel(status);
						%>

						<span class="workflow-status-<%= statusLabel %>">
							(<liferay-ui:message key="<%= statusLabel %>" />)
						</span>
					</c:if>

					<span class="entry-result-icon"></span>
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
			<c:choose>
				<c:when test="<%= Validator.isNull(url) %>">
					</span>
				</c:when>
				<c:otherwise>
					</a>
				</c:otherwise>
			</c:choose>

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
		<div class="app-view-entry app-view-entry-taglib entry-display-style display-<%= displayStyle %><aui:spacer /><%= locked ? "locked" : StringPool.BLANK %><aui:spacer /><%= cssClass %>" <%= AUIUtil.buildData(data) %>>
			<liferay-ui:icon
				cssClass='<%= showCheckbox ? "app-view-entry app-view-entry-taglib entry-display-style selectable" : "app-view-entry app-view-entry-taglib entry-display-style" %>'
				data="<%= data %>"
				label="<%= true %>"
				linkCssClass="entry-link"
				localizeMessage="<%= false %>"
				message="<%= title %>"
				method="get"
				src="<%= thumbnailSrc %>"
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
		</div>
	</c:when>
</c:choose>