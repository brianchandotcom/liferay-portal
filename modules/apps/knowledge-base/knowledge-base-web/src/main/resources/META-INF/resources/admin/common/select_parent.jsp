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
long originalParentResourcePrimKey = ParamUtil.getLong(request, "originalParentResourcePrimKey");
double priority = ParamUtil.getDouble(request, "priority", KBArticleConstants.DEFAULT_PRIORITY);

long kbArticleClassNameId = PortalUtil.getClassNameId(KBArticleConstants.getClassName());

long[] selectableClassNameIds = ParamUtil.getLongValues(request, "selectableClassNameIds", new long[] {kbFolderClassNameId, kbArticleClassNameId});

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectKBObject");

String parentTitle = LanguageUtil.get(request, "home");

KBSelectParentDisplayContext kbSelectParentDisplayContext = new KBSelectParentDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderRequest);

kbSelectParentDisplayContext.populatePortletBreadcrumbEntries(currentURLObj);
%>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<c:if test="<%= ArrayUtil.contains(selectableClassNameIds, kbSelectParentDisplayContext.getParentResourceClassNameId()) && ((kbSelectParentDisplayContext.getParentResourceClassNameId() != kbArticleClassNameId) || (kbSelectParentDisplayContext.getParentResourcePrimKey() != 0)) %>">
			<aui:button-row cssClass="input-append">
				<aui:button
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"priority", priority
						).put(
							"resourceClassNameId", kbSelectParentDisplayContext.getParentResourceClassNameId()
						).put(
							"resourcePrimKey", kbSelectParentDisplayContext.getParentResourcePrimKey()
						).put(
							"title", parentTitle
						).build()
					%>'
					value='<%= (kbSelectParentDisplayContext.getParentResourceClassNameId() == kbFolderClassNameId) ? "choose-this-folder" : "choose-this-article" %>'
				/>
			</aui:button-row>
		</c:if>

		<liferay-ui:search-container
			searchContainer="<%= kbSelectParentDisplayContext.getSearchContainer() %>"
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

						<liferay-portlet:renderURL varImpl="rowURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value='<%= templatePath + "select_parent.jsp" %>' />
							<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
							<portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(originalParentResourcePrimKey) %>" />
							<portlet:param name="eventName" value="<%= eventName %>" />
						</liferay-portlet:renderURL>

						<%
						rowURL.setParameter("selectableClassNameIds", ArrayUtil.toStringArray(selectableClassNameIds));

						int kbArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, kbFolder.getKbFolderId(), status);
						int kbFoldersCount = KBFolderServiceUtil.getKBFoldersCount(scopeGroupId, kbFolder.getKbFolderId());

						if ((kbFolder.getKbFolderId() == resourcePrimKey) || ((kbArticlesCount == 0) && (kbFoldersCount == 0))) {
							rowURL = null;
						}
						%>

						<liferay-ui:search-container-column-text>
							<c:choose>
								<c:when test="<%= rowURL != null %>">
									<aui:a href="<%= rowURL.toString() %>">
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
							href="<%= (rowURL == null) ? StringPool.BLANK : rowURL.toString() %>"
							name="num-of-kb-folders"
							value="<%= String.valueOf(kbFoldersCount) %>"
						/>

						<liferay-ui:search-container-column-text
							align="right"
							href="<%= (rowURL == null) ? StringPool.BLANK : rowURL.toString() %>"
							name="num-of-kb-articles"
							value="<%= String.valueOf(kbArticlesCount) %>"
						/>

						<liferay-ui:search-container-column-text
							align="right"
						>
							<aui:button
								cssClass="selector-button"
								data='<%=
									HashMapBuilder.<String, Object>put(
										"priority", KBArticleConstants.DEFAULT_PRIORITY
									).put(
										"resourceClassNameId", kbFolder.getClassNameId()
									).put(
										"resourcePrimKey", kbFolder.getKbFolderId()
									).put(
										"title", kbFolder.getName()
									).build()
								%>'
								disabled="<%= (kbFolder.getKbFolderId() == resourcePrimKey) || (kbFolder.getKbFolderId() == originalParentResourcePrimKey) || !ArrayUtil.contains(selectableClassNameIds, kbFolderClassNameId) %>"
								value="select"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>

						<%
						KBArticle kbArticle = (KBArticle)kbObject;
						%>

						<liferay-portlet:renderURL varImpl="rowURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value='<%= templatePath + "select_parent.jsp" %>' />
							<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
							<portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(originalParentResourcePrimKey) %>" />
							<portlet:param name="status" value="<%= String.valueOf(status) %>" />
							<portlet:param name="targetStatus" value="<%= String.valueOf(kbSelectParentDisplayContext.getTargetStatus()) %>" />
							<portlet:param name="eventName" value="<%= eventName %>" />
						</liferay-portlet:renderURL>

						<%
						rowURL.setParameter("selectableClassNameIds", ArrayUtil.toStringArray(selectableClassNameIds));

						int kbArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, kbArticle.getResourcePrimKey(), kbSelectParentDisplayContext.getTargetStatus());

						if ((kbArticle.getResourcePrimKey() == resourcePrimKey) || (kbArticlesCount == 0)) {
							rowURL = null;
						}
						%>

						<liferay-ui:search-container-column-text>
							<c:choose>
								<c:when test="<%= rowURL != null %>">
									<aui:a href="<%= rowURL.toString() %>">
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
							href="<%= (rowURL == null) ? StringPool.BLANK : rowURL.toString() %>"
							name="folders"
							value="-"
						/>

						<liferay-ui:search-container-column-text
							align="right"
							href="<%= (rowURL == null) ? StringPool.BLANK : rowURL.toString() %>"
							name="articles"
							value="<%= String.valueOf(kbArticlesCount) %>"
						/>

						<liferay-ui:search-container-column-text
							align="right"
						>
							<aui:button
								cssClass="selector-button"
								data='<%=
									HashMapBuilder.<String, Object>put(
										"priority", kbArticle.getPriority()
									).put(
										"resourceClassNameId", kbArticle.getClassNameId()
									).put(
										"resourcePrimKey", kbArticle.getResourcePrimKey()
									).put(
										"title", kbArticle.getTitle()
									).build()
								%>'
								disabled="<%= (kbArticle.getResourcePrimKey() == resourcePrimKey) || (kbArticle.getResourcePrimKey() == originalParentResourcePrimKey) || !ArrayUtil.contains(selectableClassNameIds, kbArticleClassNameId) %>"
								value="select"
							/>
						</liferay-ui:search-container-column-text>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				resultRowSplitter="<%= kbSelectParentDisplayContext.isKBFolderView() ? null : new KBResultRowSplitter() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>