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
JournalArticle article = (JournalArticle)request.getAttribute("info_panel.jsp-entry");
JournalFolder folder = (JournalFolder)request.getAttribute("info_panel.jsp-folder");

long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (folder != null) {
	folderId = folder.getFolderId();
}

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

long ddmStructureId = 0;

if (Validator.isNotNull(ddmStructureKey)) {
	DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey, true);

	if (ddmStructure != null) {
		ddmStructureId = ddmStructure.getStructureId();
	}
}

String subscribeActionName = StringPool.BLANK;
String unsubscribeActionName = StringPool.BLANK;
%>

<div class="subscribe-action">
	<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) && JournalUtil.getEmailArticleAnyEventEnabled(journalGroupServiceConfiguration) %>">

		<%
		boolean subscribed = false;
		boolean unsubscribable = true;

		if (Validator.isNotNull(ddmStructureKey)) {
			subscribed = JournalUtil.isSubscribedToStructure(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), ddmStructureId);

			subscribeActionName = "/journal/subscribe_ddm_structure";
			unsubscribeActionName = "/journal/unsubscribe_ddm_structure";
		}
		else if (Validator.isNull(ddmStructureKey) && (article != null)) {
			subscribed = JournalUtil.isSubscribedToArticle(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), article.getResourcePrimKey());

			subscribeActionName = "/journal/subscribe_article";
			unsubscribeActionName = "/journal/unsubscribe_article";
		}
		else {
			subscribed = JournalUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId);

			if (subscribed) {
				if (!JournalUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId, false)) {
					unsubscribable = false;
				}
			}

			subscribeActionName = "/journal/subscribe_folder";
			unsubscribeActionName = "/journal/unsubscribe_folder";
		}
		%>

		<c:choose>
			<c:when test="<%= subscribed %>">
				<c:choose>
					<c:when test="<%= unsubscribable %>">
						<portlet:actionURL name="<%= unsubscribeActionName %>" var="unsubscribeURL">
							<portlet:param name="redirect" value="<%= currentURL %>" />

							<c:choose>
								<c:when test="<%= Validator.isNotNull(ddmStructureKey) %>">
									<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" />
								</c:when>
								<c:when test="<%= Validator.isNull(ddmStructureKey) && (article != null) %>">
									<portlet:param name="articleId" value="<%= String.valueOf(article.getResourcePrimKey()) %>" />
								</c:when>
								<c:otherwise>
									<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
								</c:otherwise>
							</c:choose>
						</portlet:actionURL>

						<liferay-ui:icon
							icon="star"
							linkCssClass="icon-monospaced"
							markupView="lexicon"
							message="unsubscribe"
							url="<%= unsubscribeURL %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							icon="star"
							linkCssClass="icon-monospaced"
							markupView="lexicon"
							message="subscribed-to-a-parent-folder"
						/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="<%= subscribeActionName %>" var="subscribeURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />

					<c:choose>
						<c:when test="<%= Validator.isNotNull(ddmStructureKey) %>">
							<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" />
						</c:when>
						<c:when test="<%= Validator.isNull(ddmStructureKey) && (article != null) %>">
							<portlet:param name="articleId" value="<%= String.valueOf(article.getResourcePrimKey()) %>" />
						</c:when>
						<c:otherwise>
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</c:otherwise>
					</c:choose>
				</portlet:actionURL>

				<liferay-ui:icon
					icon="star-o"
					linkCssClass="icon-monospaced"
					markupView="lexicon"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>