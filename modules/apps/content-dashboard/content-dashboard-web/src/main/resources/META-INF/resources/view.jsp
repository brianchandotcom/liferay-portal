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
ContentDashboardAdminDisplayContext contentDashboardAdminDisplayContext = (ContentDashboardAdminDisplayContext)request.getAttribute(ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_DISPLAY_CONTEXT);
%>

<div class="cadmin sidebar-wrapper">
	<clay:container-fluid
		cssClass="container-form-lg"
	>
		<clay:sheet
			size="<%= StringPool.BLANK %>"
		>
			<h2 class="sheet-title">
				<clay:content-row
					noGutters="true"
				>
					<clay:content-col
						expand="<%= true %>"
					>
						<span class="component-title">
							<%= contentDashboardAdminDisplayContext.getAuditGraphTitle() %>
						</span>
					</clay:content-col>

					<clay:content-col>
						<span class="lfr-portal-tooltip" title="<%= LanguageUtil.get(request, "flip-axes") %>">
							<form action="<%= contentDashboardAdminDisplayContext.getSwapConfigurationURL() %>" method="post">
								<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

								<clay:button
									borderless="<%= true %>"
									cssClass="component-action"
									disabled="<%= !contentDashboardAdminDisplayContext.isSwapConfigurationEnabled() %>"
									displayType="secondary"
									icon="change"
									small="<%= true %>"
									type="submit"
								/>
							</form>
						</span>
					</clay:content-col>

					<clay:content-col>
						<span class="lfr-portal-tooltip" title="<%= LanguageUtil.get(request, "configure-chart") %>">
							<clay:button
								borderless="<%= true %>"
								cssClass="component-action"
								displayType="secondary"
								icon="cog"
								onClick="<%= contentDashboardAdminDisplayContext.getOnClickConfiguration() %>"
								small="<%= true %>"
							/>
						</span>
					</clay:content-col>
				</clay:content-row>
			</h2>

			<div class="audit-graph">
				<div class="audit-graph-loading c-my-5 c-p-5 inline-item w-100">
					<span aria-hidden="true" class="loading-animation"></span>
				</div>

				<react:component
					module="js/AuditGraphApp"
					props="<%= contentDashboardAdminDisplayContext.getData() %>"
				/>
			</div>
		</clay:sheet>

		<clay:sheet
			cssClass="c-mt-5"
			size="<%= StringPool.BLANK %>"
		>
			<div class="align-items-center d-flex justify-content-between mb-4">
				<h2 class="mb-0 sheet-title">
					<span class="component-title">
						<%= LanguageUtil.format(request, "content-x", contentDashboardAdminDisplayContext.getSearchContainer().getTotal(), false) %>
					</span>
				</h2>

				<div>
					<react:component
						module="js/components/DownloadSpreadsheetButton/DownloadSpreadsheetButton"
						props="<%= contentDashboardAdminDisplayContext.getXlsProps() %>"
					/>
				</div>
			</div>

			<clay:management-toolbar
				cssClass="content-dashboard-management-toolbar"
				managementToolbarDisplayContext="<%= (ContentDashboardAdminManagementToolbarDisplayContext)request.getAttribute(ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT) %>"
				propsTransformer="js/ContentDashboardManagementToolbarPropsTransformer"
			/>

			<liferay-ui:search-container
				cssClass="table-hover"
				id="content"
				searchContainer="<%= contentDashboardAdminDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.content.dashboard.web.internal.item.ContentDashboardItem"
					keyProperty="id"
					modelVar="contentDashboardItem"
				>

					<%
					InfoItemReference infoItemReference = contentDashboardItem.getInfoItemReference();

					String rowId = String.valueOf(infoItemReference.getClassPK());

					row.setData(Collections.singletonMap("rowId", rowId));
					row.setRowId(rowId);

					ContentDashboardItemAction contentDashboardItemAction = contentDashboardItem.getDefaultContentDashboardItemAction(request);
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-title"
						name="title"
					>
						<c:choose>
							<c:when test="<%= contentDashboardItemAction != null %>">
								<a class="lfr-portal-tooltip" href="<%= contentDashboardItemAction.getURL() %>" title="<%= HtmlUtil.escape(contentDashboardItem.getTitle(locale)) %>">
									<span class="text-truncate-inline">
										<span class="text-truncate"><%= HtmlUtil.escape(contentDashboardItem.getTitle(locale)) %></span>
									</span>
								</a>
							</c:when>
							<c:otherwise>
								<span class="lfr-portal-tooltip text-truncate-inline" title="<%= HtmlUtil.escape(contentDashboardItem.getTitle(locale)) %>">
									<span class="text-truncate"><%= HtmlUtil.escape(contentDashboardItem.getTitle(locale)) %></span>
								</span>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="text-center"
						name=""
					>
						<c:if test="<%= contentDashboardItem.isViewable(request) %>">
							<span class="lfr-portal-tooltip" title="<%= LanguageUtil.get(request, "this-content-has-a-display-page") %>">
								<clay:icon
									cssClass="text-secondary"
									symbol="page"
								/>
							</span>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="text-center"
						name="author"
					>
						<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(contentDashboardItem.getUserName()) %>">
							<liferay-ui:user-portrait
								userId="<%= contentDashboardItem.getUserId() %>"
							/>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller text-truncate"
						name="type"
						value="<%= HtmlUtil.escape(contentDashboardItem.getTypeLabel(locale)) %>"
					/>

					<%
					ContentDashboardItemSubtype contentDashboardItemSubtype = contentDashboardItem.getContentDashboardItemSubtype();
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller text-truncate"
						name="subtype"
						value="<%= HtmlUtil.escape(contentDashboardItemSubtype.getLabel(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="site-or-asset-library"
						value="<%= HtmlUtil.escape(contentDashboardItem.getScopeName(locale)) %>"
					/>

					<%
					for (AssetVocabulary assetVocabulary : contentDashboardAdminDisplayContext.getAssetVocabularies()) {
					%>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smaller"
							name="<%= assetVocabulary.getTitle(locale) %>"
						>

							<%
							List<String> assetCategories = contentDashboardAdminDisplayContext.getAssetCategoryTitles(contentDashboardItem, assetVocabulary.getVocabularyId());
							%>

							<c:if test="<%= !assetCategories.isEmpty() %>">
								<clay:label
									displayType="secondary"
									large="<%= true %>"
								>
									<clay:label-item-expand><%= assetCategories.get(0) %></clay:label-item-expand>
								</clay:label>
							</c:if>

							<c:if test="<%= assetCategories.size() > 1 %>">

								<%
								List<String> restOfAssetCategories = assetCategories.subList(1, assetCategories.size());
								%>

								<div class="">
									<react:component
										module="js/components/CategoriesPopover"
										props='<%=
											HashMapBuilder.<String, Object>put(
												"categories", restOfAssetCategories
											).put(
												"vocabulary", assetVocabulary.getTitle(locale)
											).build()
										%>'
									/>
								</div>
							</c:if>
						</liferay-ui:search-container-column-text>

					<%
					}
					%>

					<liferay-ui:search-container-column-text
						cssClass="text-nowrap"
						name="status"
					>

						<%
						List<ContentDashboardItem.Version> versions = contentDashboardItem.getVersions(locale);

						for (ContentDashboardItem.Version version : versions) {
						%>

							<clay:label
								displayType="<%= version.getStyle() %>"
								label="<%= version.getLabel() %>"
							/>

						<%
						}
						%>

					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= contentDashboardItem.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							dropdownItems="<%= contentDashboardAdminDisplayContext.getDropdownItems(contentDashboardItem) %>"
							propsTransformer="js/transformers/ActionsComponentPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</clay:sheet>
	</clay:container-fluid>
</div>