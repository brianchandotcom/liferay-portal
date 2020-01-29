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

<%@ include file="/init.jsp" %>

<%
UnicodeProperties typeSettingsProperties = (UnicodeProperties)request.getAttribute("view.jsp-typeSettingsProperties");

long lastImportDate = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-date"));

String lastImportLayoutSetBranchName = null;

long lastImportLayoutSetBranchId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-set-branch-id"));

if (lastImportLayoutSetBranchId > 0) {
	try {
		LayoutSetBranch lastImportLayoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(lastImportLayoutSetBranchId);

		lastImportLayoutSetBranchName = lastImportLayoutSetBranch.getName();
	}
	catch (Exception e) {
	}
}

if (Validator.isNull(lastImportLayoutSetBranchName)) {
	lastImportLayoutSetBranchName = typeSettingsProperties.getProperty("last-import-layout-set-branch-name");
}

if (Validator.isNull(lastImportLayoutSetBranchName)) {
	lastImportLayoutSetBranchName = LanguageUtil.get(request, "staging");
}

String lastImportLayoutBranchName = null;

List<LayoutRevision> layoutRevisions = new ArrayList<LayoutRevision>();

long lastImportLayoutRevisionId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-revision-id"));

if (lastImportLayoutRevisionId > 0) {
	try {
		LayoutRevision lastImportLayoutRevision = LayoutRevisionLocalServiceUtil.getLayoutRevision(lastImportLayoutRevisionId);

		lastImportLayoutBranchName = lastImportLayoutRevision.getLayoutBranch().getName();

		layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(lastImportLayoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, lastImportLayoutRevision.getPlid());
	}
	catch (Exception e) {
	}
}

if (Validator.isNull(lastImportLayoutBranchName)) {
	lastImportLayoutBranchName = typeSettingsProperties.getProperty("last-import-layout-branch-name");
}

String publisherName = null;

String lastImportUserUuid = GetterUtil.getString(typeSettingsProperties.getProperty("last-import-user-uuid"));

if (Validator.isNotNull(lastImportUserUuid)) {
	try {
		User publisher = UserLocalServiceUtil.getUserByUuidAndCompanyId(lastImportUserUuid, company.getCompanyId());

		publisherName = publisher.getFullName();
	}
	catch (Exception e) {
	}
}

if (Validator.isNull(publisherName)) {
	publisherName = typeSettingsProperties.getProperty("last-import-user-name");
}
%>

<c:choose>
	<c:when test="<%= lastImportDate > 0 %>">
		<c:if test="<%= Validator.isNotNull(lastImportLayoutSetBranchName) && Validator.isNotNull(publisherName) %>">
			<div class="alert alert-info" role="alert">
				<span class="alert-indicator">
					<svg aria-hidden="true" class="lexicon-icon lexicon-icon-info-circle">
						<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#info-circle" />
					</svg>
				</span>
				<span class="last-publication-branch">
					<liferay-ui:message arguments='<%= new String[] {"<strong>" + HtmlUtil.escape(layout.getName(locale)) + "</strong>", "<em>" + HtmlUtil.escape(layoutSetBranchDisplayContext.getLayoutSetBranchDisplayName(lastImportLayoutSetBranchName)) + "</em>"} %>' key='<%= (group.isStagingGroup() || group.isStagedRemotely()) ? "page-x-was-last-published-to-live" : "page-x-was-last-published-from-x" %>' translateArguments="<%= false %>" />

					<c:if test="<%= (Validator.isNotNull(lastImportLayoutBranchName) && (layoutRevisions.size() > 1)) || Validator.isNotNull(lastImportLayoutRevisionId) %>">
						<span class="last-publication-variation-details">(
							<c:if test="<%= Validator.isNotNull(lastImportLayoutBranchName) && (layoutRevisions.size() > 1) %>">
								<span class="variation-name">
									<liferay-ui:message key="variation" />: <strong><liferay-ui:message key="<%= HtmlUtil.escape(layoutBranchDisplayContext.getLayoutBranchDisplayName(lastImportLayoutBranchName)) %>" localizeKey="<%= false %>" /></strong>
								</span>
							</c:if>

							<c:if test="<%= Validator.isNotNull(lastImportLayoutRevisionId) %>">
								<span class="layout-version">
									<liferay-ui:message key="version" />: <strong><%= lastImportLayoutRevisionId %></strong>
								</span>
							</c:if>
						)</span>
					</c:if>
				</span>
				<span class="last-publication-user">
					<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(StringUtil.toLowerCase(LanguageUtil.getTimeDescription(request, (System.currentTimeMillis() - lastImportDate), true))), HtmlUtil.escape(publisherName)} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
				</span>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info" role="alert">
			<span class="alert-indicator">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-info-circle">
					<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#info-circle" />
				</svg>
			</span>

			<liferay-ui:message arguments="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>" key="x-is-staged" translateArguments="<%= false %>" />

			<liferay-ui:message arguments="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>" key='<%= (group.isStagingGroup() || group.isStagedRemotely()) ? "staging-staging-help-x" : !PropsValues.STAGING_LIVE_GROUP_LOCKING_ENABLED ? "staging-live-help2-x" : "staging-live-help-x" %>' translateArguments="<%= false %>" />
		</div>
	</c:otherwise>
</c:choose>