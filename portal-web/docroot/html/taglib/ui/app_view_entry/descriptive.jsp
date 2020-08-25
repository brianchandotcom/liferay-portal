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

<%@ include file="/html/taglib/ui/app_view_entry/init.jsp" %>

<div class="app-view-entry app-view-entry-taglib display-<%= displayStyle %> entry-display-style <%= showCheckbox ? "selectable" : StringPool.BLANK %> <%= cssClass %>" <%= AUIUtil.buildData(data) %> data-draggable="<%= showCheckbox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= HtmlUtil.escapeAttribute(shortTitle) %>">
	<c:choose>
		<c:when test="<%= Validator.isNull(url) %>">
			<span class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-folder-id="<%= rowCheckerId %>" title="<%= linkTitle %>">
		</c:when>
		<c:otherwise>
			<a class="entry-link" data-folder="<%= folder ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-folder-id="<%= rowCheckerId %>" href="<%= HtmlUtil.escapeAttribute(url) %>" title="<%= linkTitle %>">
		</c:otherwise>
	</c:choose>

	<c:if test="<%= Validator.isNotNull(iconCssClass) || Validator.isNotNull(thumbnailSrc) %>">
		<div class="entry-thumbnail" style="<%= thumbnailDivStyle %>">
			<c:choose>
				<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
					<img alt="" class="img-thumbnail" src="<%= HtmlUtil.escapeAttribute(thumbnailSrc) %>" style="<%= thumbnailStyle %>" />
				</c:when>
				<c:when test="<%= Validator.isNotNull(iconCssClass) %>">
					<i class="<%= iconCssClass %>" style="<%= thumbnailStyle %>"></i>
				</c:when>
			</c:choose>

			<c:if test="<%= shortcut %>">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
			</c:if>

			<c:if test="<%= locked %>">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
			</c:if>

			<c:if test="<%= !folder && (status != WorkflowConstants.STATUS_ANY) && (status != WorkflowConstants.STATUS_APPROVED) %>">
				<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= status %>" />
			</c:if>
		</div>
	</c:if>

	<div class="entry-metadata">
		<span class="entry-title">
			<span class="entry-title-text">
				<%= HtmlUtil.escape(title) %>
			</span>
			<span class="entry-result-icon"></span>
		</span>

		<small>
			<c:if test="<%= Validator.isNotNull(description) %>">
				<span class="entry-description">
					<%= HtmlUtil.escape(description) %>
				</span>
			</c:if>

			<dl>
				<c:if test="<%= Validator.isNotNull(classTypeName) %>">
					<dt>
						<liferay-ui:message key="type" />:
					</dt>
					<dd>
						<%= classTypeName %>
					</dd>
				</c:if>

				<c:if test="<%= (groupId > 0) && (groupId != scopeGroupId) %>">

					<%
					Group group = GroupLocalServiceUtil.getGroup(groupId);
					%>

					<c:if test="<%= !group.isLayout() || (group.getParentGroupId() != scopeGroupId) %>">
						<dt>
							<liferay-ui:message key="site" />:
						</dt>
						<dd>

							<%
							String groupDescriptiveName = null;

							if (group.isLayout()) {
								Group parentGroup = group.getParentGroup();

								groupDescriptiveName = parentGroup.getDescriptiveName(locale);
							}
							else {
								groupDescriptiveName = group.getDescriptiveName(locale);
							}
							%>

							<%= HtmlUtil.escape(groupDescriptiveName) %>
						</dd>
					</c:if>

					<c:if test="<%= group.isLayout() %>">
						<dt>
							<liferay-ui:message key="scope" />:
						</dt>
						<dd>
							<%= group.getDescriptiveName(locale) %>
						</dd>
					</c:if>
				</c:if>

				<c:if test="<%= Validator.isNotNull(version) %>">
					<dt>
						<liferay-ui:message key="version" />:
					</dt>
					<dd>
						<%= HtmlUtil.escape(version) %>
					</dd>
				</c:if>

				<c:if test="<%= (createDate != null) && (modifiedDate != null) && Validator.isNotNull(author) %>">
					<c:choose>
						<c:when test="<%= modifiedDate.equals(createDate) %>">
							<dt>
								<liferay-ui:message key="created" />:
							</dt>
						</c:when>
						<c:otherwise>
							<dt>
								<liferay-ui:message key="last-updated" />:
							</dt>
						</c:otherwise>
					</c:choose>

					<dd class="entry-author">
						<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - modifiedDate.getTime(), true), HtmlUtil.escape(author)} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
					</dd>
				</c:if>

				<%
				Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
				%>

				<c:if test="<%= displayDate != null %>">
					<dt>
						<liferay-ui:message key="display-date" />:
					</dt>
					<dd>
						<%= HtmlUtil.escape(dateFormatDateTime.format(displayDate)) %>
					</dd>
				</c:if>

				<c:if test="<%= expirationDate != null %>">
					<dt>
						<liferay-ui:message key="expiration-date" />:
					</dt>
					<dd>
						<%= HtmlUtil.escape(dateFormatDateTime.format(expirationDate)) %>
					</dd>
				</c:if>

				<c:if test="<%= reviewDate != null %>">
					<dt>
						<liferay-ui:message key="review-date" />:
					</dt>
					<dd>
						<%= HtmlUtil.escape(dateFormatDateTime.format(reviewDate)) %>
					</dd>
				</c:if>
			</dl>

			<c:if test="<%= Validator.isNotNull(assetCategoryClassName) && (assetCategoryClassPK > 0) %>">
				<span class="entry-categories">
					<liferay-ui:asset-categories-summary
						className="<%= assetCategoryClassName %>"
						classPK="<%= assetCategoryClassPK %>"
					/>
				</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(assetTagClassName) && (assetTagClassPK > 0) %>">
				<span class="entry-tags">
					<liferay-ui:asset-tags-summary
						className="<%= assetTagClassName %>"
						classPK="<%= assetTagClassPK %>"
					/>
				</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(latestApprovedVersion) && (status > WorkflowConstants.STATUS_APPROVED) %>">
				<dl class="entry-latest-approved-container">
					<dt>
						<liferay-ui:message key="latest-aproved-version" />
					</dt>
					<dd>
						<%= HtmlUtil.escape(latestApprovedVersion) %>
					</dd>
					<dt>
						<liferay-ui:message key="latest-aproved-version-author" />:
					</dt>
					<dd>
						<%= HtmlUtil.escape(latestApprovedVersionAuthor) %>
					</dd>
				</dl>
			</c:if>
		</small>
	</div>

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

	<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />

	<c:if test="<%= showCheckbox %>">
		<aui:input cssClass="entry-selector overlay" id="<%= rowCheckerId %>" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" title='<%= LanguageUtil.format(request, "select-x", HtmlUtil.escapeAttribute(shortTitle)) %>' type="checkbox" value="<%= rowCheckerId %>" />
	</c:if>
</div>