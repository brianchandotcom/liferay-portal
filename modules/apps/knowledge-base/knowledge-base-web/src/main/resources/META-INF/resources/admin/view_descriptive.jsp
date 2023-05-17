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

<%@ include file="/admin/init.jsp" %>

<%
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long parentResourceClassNameId = ParamUtil.getLong(request, "parentResourceClassNameId", kbFolderClassNameId);

KBAdminManagementToolbarDisplayContext kbAdminManagementToolbarDisplayContext = new KBAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderRequest, renderResponse, portletConfig);
KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse);
KBArticleViewDisplayContext kbArticleViewDisplayContext = new KBArticleViewDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderResponse);
%>

<liferay-ui:search-container
	id="kbObjects"
	searchContainer="<%= kbAdminManagementToolbarDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="Object"
		modelVar="kbObject"
	>
		<c:choose>
			<c:when test="<%= kbObject instanceof KBFolder %>">

				<%
				KBFolder kbFolder = (KBFolder)kbObject;

				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(kbAdminManagementToolbarDisplayContext.getAvailableActions(kbFolder))
					).build());

				row.setPrimaryKey(String.valueOf(kbFolder.getKbFolderId()));
				%>

				<liferay-ui:search-container-column-icon
					icon="folder"
					toggleRowChecker="<%= true %>"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<liferay-portlet:renderURL varImpl="rowURL">
						<portlet:param name="mvcPath" value="/admin/view_kb_folders.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
						<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
						<portlet:param name="selectedItemId" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
					</liferay-portlet:renderURL>

					<h2 class="h5">
						<aui:a href="<%= rowURL.toString() %>">
							<%= HtmlUtil.escape(kbFolder.getName()) %>
						</aui:a>
					</h2>

					<span class="text-default">

						<%
						Date modifiedDate = kbFolder.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbFolder.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
					</span>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text>

					<%
					KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
					%>

					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
						dropdownItems="<%= kbDropdownItemsProvider.getKBFolderDropdownItems(kbFolder) %>"
						propsTransformer="admin/js/KBDropdownPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>

				<%
				KBArticle kbArticle = (KBArticle)kbObject;

				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(kbAdminManagementToolbarDisplayContext.getAvailableActions(kbArticle))
					).build());

				row.setPrimaryKey(String.valueOf(kbArticle.getResourcePrimKey()));
				%>

				<liferay-ui:search-container-column-user
					showDetails="<%= false %>"
					userId="<%= kbArticle.getUserId() %>"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<h2 class="h5">

						<%
						PortletURL viewURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
						%>

						<aui:a href="<%= viewURL.toString() %>">
							<%= HtmlUtil.escape(kbArticle.getTitle()) %>
						</aui:a>
					</h2>

					<span class="text-default">
						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbArticle.getUserName()), kbArticleViewDisplayContext.getModifiedDateDescription(kbArticle)} %>" key="x-modified-x-ago" />
					</span>

					<c:if test="<%= kbArticleViewDisplayContext.getChildKBArticlesCount(scopeGroupId, kbArticle) > 0 %>">
						<span class="text-default">
							<aui:a href="<%= kbArticleViewDisplayContext.getChildKBArticlesURLString(currentURL, kbArticle) %>">
								<liferay-ui:message arguments="<%= kbArticleViewDisplayContext.getChildKBArticlesCount(scopeGroupId, kbArticle) %>" key="x-child-articles" />
							</aui:a>
						</span>
					</c:if>

					<span class="text-default">

						<%
						String expirationDate = StringPool.BLANK;

						if (kbArticle.getExpirationDate() != null) {
							expirationDate = dateFormatDateTime.format(kbArticle.getExpirationDate());
						}
						%>

						<aui:workflow-status helpMessage="<%= kbArticle.isExpired() ? expirationDate : StringPool.BLANK %>" markupView="lexicon" showHelpMessage="<%= kbArticle.isExpired() %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" />

						<c:if test="<%= kbArticleViewDisplayContext.isExpiringSoon(kbArticle) %>">
							<span class="label label-warning">
								<span class="label-item label-item-expand"><liferay-ui:message key="expiring-soon" /></span>
							</span>

							<clay:icon
								aria-label="<%= expirationDate %>"
								cssClass="lfr-portal-tooltip"
								symbol="question-circle-full"
								title="<%= expirationDate %>"
							/>
						</c:if>
					</span>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
						dropdownItems="<%= kbArticleViewDisplayContext.getKBArticleDropdownItems(kbArticle) %>"
						propsTransformer="admin/js/KBDropdownPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= kbAdminManagementToolbarDisplayContext.getDisplayStyle() %>"
		markupView="lexicon"
		resultRowSplitter="<%= (parentResourceClassNameId == kbFolderClassNameId) ? new KBResultRowSplitter() : null %>"
	/>
</liferay-ui:search-container>