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
		modelVar="object"
	>
		<c:choose>
			<c:when test="<%= object instanceof KBFolder %>">

				<%
				KBFolder kbFolder = (KBFolder)object;

				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(kbAdminManagementToolbarDisplayContext.getAvailableActions(kbFolder))
					).build());

				row.setPrimaryKey(String.valueOf(kbFolder.getKbFolderId()));
				%>

				<liferay-ui:search-container-column-text
					name="title"
				>
					<clay:content-row>
						<clay:content-col>
							<clay:sticker
								cssClass="sticker-secondary"
								icon="folder"
							/>
						</clay:content-col>

						<clay:content-col
							expand="<%= true %>"
						>
							<liferay-portlet:renderURL varImpl="rowURL">
								<portlet:param name="mvcPath" value="/admin/view_kb_folders.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
								<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
								<portlet:param name="selectedItemId" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
							</liferay-portlet:renderURL>

							<aui:a href="<%= rowURL.toString() %>">
								<%= HtmlUtil.escape(kbFolder.getName()) %>
							</aui:a>
						</clay:content-col>
					</clay:content-row>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
					name="num-of-kb-folders"
				>
					<%= kbArticleViewDisplayContext.getKBFoldersCount(scopeGroupId, kbFolder.getKbFolderId()) %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
					name="num-of-kb-articles"
				>
					<%= kbArticleViewDisplayContext.getKBFolderKBArticlesCount(scopeGroupId, kbFolder.getKbFolderId()) %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
					name="views"
					value="--"
				/>

				<liferay-ui:search-container-column-text
					align="right"
					name="modified-date"
				>
					<span class="text-default">

						<%
						Date modifiedDate = kbFolder.getModifiedDate();
						%>

						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="x-ago" />
					</span>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="status"
					value="--"
				/>

				<liferay-ui:search-container-column-text
					align="right"
				>

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
				KBArticle kbArticle = (KBArticle)object;

				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(kbAdminManagementToolbarDisplayContext.getAvailableActions(kbArticle))
					).build());

				row.setPrimaryKey(String.valueOf(kbArticle.getResourcePrimKey()));
				%>

				<liferay-ui:search-container-column-text
					name="title"
				>
					<clay:content-row>
						<clay:content-col>
							<clay:sticker
								cssClass="sticker-secondary"
								icon="document-text"
							/>
						</clay:content-col>

						<clay:content-col
							expand="<%= true %>"
						>

							<%
							PortletURL viewURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
							%>

							<aui:a href="<%= viewURL.toString() %>">
								<%= HtmlUtil.escape(kbArticle.getTitle()) %>
							</aui:a>
						</clay:content-col>
					</clay:content-row>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
					name="num-of-kb-folders"
					value="--"
				/>

				<liferay-ui:search-container-column-text
					align="right"
					name="num-of-kb-articles"
				>
					<%= kbArticleViewDisplayContext.getChildKBArticlesCount(scopeGroupId, kbArticle) %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
					name="views"
				>
					<%= kbArticle.getViewCount() %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
					name="modified-date"
				>
					<span class="text-default">
						<liferay-ui:message arguments="<%= kbArticleViewDisplayContext.getModifiedDateDescription(kbArticle) %>" key="x-ago" />
					</span>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="status"
				>

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
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					align="right"
				>
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