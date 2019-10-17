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

<%@ include file="/admin/common/init.jsp" %>

<%
int status = (Integer)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS);

resourceClassNameId = ParamUtil.getLong(request, "resourceClassNameId");
resourcePrimKey = ParamUtil.getLong(request, "resourcePrimKey");
long parentResourceClassNameId = ParamUtil.getLong(request, "parentResourceClassNameId", kbFolderClassNameId);
long parentResourcePrimKey = ParamUtil.getLong(request, "parentResourcePrimKey", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
long originalParentResourcePrimKey = ParamUtil.getLong(request, "originalParentResourcePrimKey");
double priority = ParamUtil.getDouble(request, "priority", KBArticleConstants.DEFAULT_PRIORITY);
int targetStatus = ParamUtil.getInteger(request, "targetStatus", status);

long kbArticleClassNameId = PortalUtil.getClassNameId(KBArticleConstants.getClassName());

long[] selectableClassNameIds = ParamUtil.getLongValues(request, "selectableClassNameIds", new long[] {kbFolderClassNameId, kbArticleClassNameId});

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectKBObject");

String parentTitle = LanguageUtil.get(request, "home");

if (parentResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	if (parentResourceClassNameId == kbFolderClassNameId) {
		KBFolder parentKBFolder = KBFolderLocalServiceUtil.fetchKBFolder(parentResourcePrimKey);

		if ((parentKBFolder == null) || !KBFolderPermission.contains(permissionChecker, parentKBFolder, ActionKeys.VIEW)) {
			parentResourceClassNameId = kbFolderClassNameId;

			parentResourcePrimKey = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
		else {
			parentTitle = parentKBFolder.getName();
		}
	}
	else {
		KBArticle parentKBArticle = KBArticleLocalServiceUtil.fetchLatestKBArticle(parentResourcePrimKey, status);

		if ((parentKBArticle == null) || !KBArticlePermission.contains(permissionChecker, parentKBArticle, ActionKeys.VIEW)) {
			parentResourceClassNameId = kbFolderClassNameId;

			parentResourcePrimKey = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
		else {
			parentTitle = parentKBArticle.getTitle();
		}
	}
}

SearchContainer kbObjectSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, "there-are-no-entries");

boolean kbFolderView = (resourceClassNameId == kbFolderClassNameId);

if (kbFolderView) {
	kbObjectSearchContainer.setTotal(KBFolderServiceUtil.getKBFoldersCount(scopeGroupId, parentResourcePrimKey));
	kbObjectSearchContainer.setResults(KBFolderServiceUtil.getKBFolders(scopeGroupId, parentResourcePrimKey, kbObjectSearchContainer.getStart(), kbObjectSearchContainer.getEnd()));
}
else {
	kbObjectSearchContainer.setTotal(KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(scopeGroupId, parentResourcePrimKey, targetStatus));
	kbObjectSearchContainer.setResults(KBFolderServiceUtil.getKBFoldersAndKBArticles(scopeGroupId, parentResourcePrimKey, targetStatus, kbObjectSearchContainer.getStart(), kbObjectSearchContainer.getEnd(), new KBObjectsTitleComparator<Object>()));
}
%>

<div class="container-fluid-1280">
	<aui:form method="post" name="fm">

		<%
		KBSelectParentDisplayContext kbSelectParentDisplayContext = new KBSelectParentDisplayContext(parentResourceClassNameId, parentResourcePrimKey, request, liferayPortletResponse);

		kbSelectParentDisplayContext.populatePortletBreadcrumbEntries(currentURLObj);
		%>

		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<c:if test="<%= ArrayUtil.contains(selectableClassNameIds, parentResourceClassNameId) && ((parentResourceClassNameId != kbArticleClassNameId) || (parentResourcePrimKey != 0)) %>">
			<aui:button-row cssClass="input-append">

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("priority", priority);
				data.put("resourceClassNameId", parentResourceClassNameId);
				data.put("resourcePrimKey", parentResourcePrimKey);
				data.put("title", parentTitle);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value='<%= (parentResourceClassNameId == kbFolderClassNameId) ? "choose-this-folder" : "choose-this-article" %>' />
			</aui:button-row>
		</c:if>

		<liferay-ui:search-container
			searchContainer="<%= kbObjectSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="Object"
				modelVar="kbObject"
			>
				<c:choose>
					<c:when test="<%= kbObject instanceof KBFolder %>">

						<%
						KBFolder kbFolder = (KBFolder)kbObject;
						%>

						<liferay-portlet:renderURL var="rowURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value='<%= templatePath + "select_parent.jsp" %>' />
							<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
							<portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(originalParentResourcePrimKey) %>" />
							<portlet:param name="selectableClassNameIds" value="<%= StringUtil.merge(selectableClassNameIds) %>" />
							<portlet:param name="eventName" value="<%= eventName %>" />
						</liferay-portlet:renderURL>

						<%
						int kbArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, kbFolder.getKbFolderId(), status);
						int kbFoldersCount = KBFolderServiceUtil.getKBFoldersCount(scopeGroupId, kbFolder.getKbFolderId());

						if ((kbFolder.getKbFolderId() == resourcePrimKey) || ((kbArticlesCount == 0) && (kbFoldersCount == 0))) {
							rowURL = null;
						}
						%>

						<liferay-ui:search-container-column-text>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(rowURL) %>">
									<aui:a href="<%= rowURL %>">
										<%= HtmlUtil.escape(kbFolder.getName()) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(kbFolder.getName()) %>
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							align="right"
							href="<%= rowURL %>"
							name="num-of-kb-folders"
							value="<%= String.valueOf(kbFoldersCount) %>"
						/>

						<liferay-ui:search-container-column-text
							align="right"
							href="<%= rowURL %>"
							name="num-of-kb-articles"
							value="<%= String.valueOf(kbArticlesCount) %>"
						/>

						<liferay-ui:search-container-column-text
							align="right"
						>

							<%
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("priority", KBArticleConstants.DEFAULT_PRIORITY);
							data.put("resourceClassNameId", kbFolder.getClassNameId());
							data.put("resourcePrimKey", kbFolder.getKbFolderId());
							data.put("title", kbFolder.getName());
							%>

							<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= (kbFolder.getKbFolderId() == resourcePrimKey) || (kbFolder.getKbFolderId() == originalParentResourcePrimKey) || !ArrayUtil.contains(selectableClassNameIds, kbFolderClassNameId) %>" value="select" />
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>

						<%
						KBArticle kbArticle = (KBArticle)kbObject;
						%>

						<liferay-portlet:renderURL var="rowURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value='<%= templatePath + "select_parent.jsp" %>' />
							<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
							<portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(originalParentResourcePrimKey) %>" />
							<portlet:param name="selectableClassNameIds" value="<%= StringUtil.merge(selectableClassNameIds) %>" />
							<portlet:param name="status" value="<%= String.valueOf(status) %>" />
							<portlet:param name="targetStatus" value="<%= String.valueOf(targetStatus) %>" />
							<portlet:param name="eventName" value="<%= eventName %>" />
						</liferay-portlet:renderURL>

						<%
						int kbArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, kbArticle.getResourcePrimKey(), targetStatus);

						if ((kbArticle.getResourcePrimKey() == resourcePrimKey) || (kbArticlesCount == 0)) {
							rowURL = null;
						}
						%>

						<liferay-ui:search-container-column-text>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(rowURL) %>">
									<aui:a href="<%= rowURL %>">
										<%= HtmlUtil.escape(kbArticle.getTitle()) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(kbArticle.getTitle()) %>
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							align="right"
							href="<%= rowURL %>"
							name="folders"
							value="-"
						/>

						<liferay-ui:search-container-column-text
							align="right"
							href="<%= rowURL %>"
							name="articles"
							value="<%= String.valueOf(kbArticlesCount) %>"
						/>

						<liferay-ui:search-container-column-text
							align="right"
						>

							<%
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("priority", kbArticle.getPriority());
							data.put("resourceClassNameId", kbArticle.getClassNameId());
							data.put("resourcePrimKey", kbArticle.getResourcePrimKey());
							data.put("title", kbArticle.getTitle());
							%>

							<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= (kbArticle.getResourcePrimKey() == resourcePrimKey) || (kbArticle.getResourcePrimKey() == originalParentResourcePrimKey) || !ArrayUtil.contains(selectableClassNameIds, kbArticleClassNameId) %>" value="select" />
						</liferay-ui:search-container-column-text>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				resultRowSplitter="<%= kbFolderView ? null : new KBResultRowSplitter() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />fm',
		'<%= HtmlUtil.escape(eventName) %>'
	);
</aui:script>