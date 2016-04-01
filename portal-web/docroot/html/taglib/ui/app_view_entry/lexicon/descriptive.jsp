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

<h5>
	<c:choose>
		<c:when test="<%= Validator.isNull(url) %>">
			<%= HtmlUtil.escape(title) %>
		</c:when>
		<c:otherwise>
			<a href="<%= HtmlUtil.escapeAttribute(url) %>" title="<%= linkTitle %>">
				<%= HtmlUtil.escape(title) %>
			</a>
		</c:otherwise>
	</c:choose>
</h5>

<c:if test="<%= Validator.isNotNull(description) %>">
	<h6 class="text-default"><%= HtmlUtil.escape(description) %></h6>
</c:if>

<c:if test="<%= Validator.isNotNull(classTypeName) %>">
	<h6 class="text-default">
		<strong><liferay-ui:message key="type" />:</strong> <%= classTypeName %>
	</h6>
</c:if>

<c:if test="<%= (groupId > 0) && (groupId != scopeGroupId) %>">

	<%
	Group group = GroupLocalServiceUtil.getGroup(groupId);
	%>

	<c:if test="<%= !group.isLayout() || (group.getParentGroupId() != scopeGroupId) %>">
		<h6 class="text-default">

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

			<strong><liferay-ui:message key="site" />:</strong> <%= HtmlUtil.escape(groupDescriptiveName) %>
		</h6>
	</c:if>

	<c:if test="<%= group.isLayout() %>">
		<h6 class="text-default">
			<strong><liferay-ui:message key="scope" />:</strong> <%= group.getDescriptiveName(locale) %>
		</h6>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(version) %>">
	<h6 class="text-default">
		<strong><liferay-ui:message key="version" />:</strong> <%= HtmlUtil.escape(version) %>
	</h6>
</c:if>

<c:if test="<%= (createDate != null) && (modifiedDate != null) && Validator.isNotNull(author) %>">
	<h6 class="text-default">
		<strong>
			<c:choose>
				<c:when test="<%= modifiedDate.equals(createDate) %>">
					<liferay-ui:message key="created" />:
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="last-updated" />:
				</c:otherwise>
			</c:choose>
		</strong>

		<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - modifiedDate.getTime(), true), HtmlUtil.escape(author)} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
	</h6>
</c:if>

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test="<%= displayDate != null %>">
	<h6 class="text-default">
		<strong><liferay-ui:message key="display-date" />:</strong> <%= HtmlUtil.escape(dateFormatDateTime.format(displayDate)) %>
	</h6>
</c:if>

<c:if test="<%= expirationDate != null %>">
	<h6 class="text-default">
		<strong><liferay-ui:message key="expiration-date" />:</strong> <%= HtmlUtil.escape(dateFormatDateTime.format(expirationDate)) %>
	</h6>
</c:if>

<c:if test="<%= reviewDate != null %>">
	<h6 class="text-default">
		<strong><liferay-ui:message key="review-date" />:</strong> <%= HtmlUtil.escape(dateFormatDateTime.format(reviewDate)) %>
	</h6>
</c:if>

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
	<h6 class="text-default">
		<strong><liferay-ui:message key="latest-aproved-version" />:</strong> <%= HtmlUtil.escape(latestApprovedVersion) %>
		<strong><liferay-ui:message key="latest-aproved-version-author" />:</strong> <%= HtmlUtil.escape(latestApprovedVersionAuthor) %>
	</h6>
</c:if>

<c:if test="<%= !folder && (status != WorkflowConstants.STATUS_ANY) && (status != WorkflowConstants.STATUS_APPROVED) %>">
	<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= status %>" />

	<c:if test="<%= locked %>">
		<small class="text-default"><liferay-ui:message key="locked" /></small>
	</c:if>
</c:if>