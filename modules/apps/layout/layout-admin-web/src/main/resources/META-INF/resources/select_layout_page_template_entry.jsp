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
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(layoutsAdminDisplayContext.getPortletURL()));

renderResponse.setTitle(LanguageUtil.get(request, "select-template"));
%>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="row">
		<div class="col-lg-3">
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<p class="text-uppercase">
							<strong><liferay-ui:message key="collections" /></strong>
						</p>

						<ul class="nav nav-stacked">

							<%
							List<LayoutPageTemplateCollection> layoutPageTemplateCollections = LayoutPageTemplateCollectionServiceUtil.getLayoutPageTemplateCollections(scopeGroupId);

							for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
								int layoutPageTemplateEntriesCount = LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntriesCount(themeDisplay.getScopeGroupId(), layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(), WorkflowConstants.STATUS_APPROVED);
							%>

								<c:if test="<%= layoutPageTemplateEntriesCount > 0 %>">
									<li class="nav-item">
										<a class="nav-link truncate-text <%= (selectLayoutPageTemplateEntryDisplayContext.getLayoutPageTemplateCollectionId() == layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(), layoutsAdminDisplayContext.isPrivateLayout()) %>">
											<%= layoutPageTemplateCollection.getName() %>
										</a>
									</li>
								</c:if>

							<%
							}
							%>

							<li class="nav-item">

								<%
								String basicPagesURL = layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(0, layoutsAdminDisplayContext.getSelPlid(), "basic-pages", layoutsAdminDisplayContext.isPrivateLayout());
								%>

								<a class="nav-link truncate-text <%= selectLayoutPageTemplateEntryDisplayContext.isBasicPages() ? "active" : StringPool.BLANK %>" href="<%= basicPagesURL %>">
									<liferay-ui:message key="basic-pages" />
								</a>
							</li>
							<li class="nav-item">

								<%
								String globalTemplatesURL = layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(0, layoutsAdminDisplayContext.getSelPlid(), "global-templates", layoutsAdminDisplayContext.isPrivateLayout());
								%>

								<a class="nav-link truncate-text <%= selectLayoutPageTemplateEntryDisplayContext.isGlobalTemplates() ? "active" : StringPool.BLANK %>" href="<%= globalTemplatesURL %>">
									<liferay-ui:message key="global-templates" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>

		<div class="col-lg-9">
			<div class="sheet">
				<h3 class="sheet-title">
					<c:choose>
						<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isContentPages() %>">

							<%
							LayoutPageTemplateCollection layoutPageTemplateCollection = LayoutPageTemplateCollectionLocalServiceUtil.fetchLayoutPageTemplateCollection(selectLayoutPageTemplateEntryDisplayContext.getLayoutPageTemplateCollectionId());
							%>

							<c:if test="<%= layoutPageTemplateCollection != null %>">
								<%= layoutPageTemplateCollection.getName() %>
							</c:if>
						</c:when>
						<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isBasicPages() %>">
							<liferay-ui:message key="basic-pages" />
						</c:when>
						<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isGlobalTemplates() %>">
							<liferay-ui:message key="global-templates" />
						</c:when>
					</c:choose>
				</h3>

				<c:choose>
					<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isContentPages() %>">
						<liferay-ui:search-container
							total="<%= selectLayoutPageTemplateEntryDisplayContext.getLayoutPageTemplateEntriesCount() %>"
						>
							<liferay-ui:search-container-results
								results="<%= selectLayoutPageTemplateEntryDisplayContext.getLayoutPageTemplateEntries(searchContainer.getStart(), searchContainer.getEnd()) %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
								keyProperty="layoutPageTemplateEntryId"
								modelVar="layoutPageTemplateEntry"
							>

								<%
								row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
								%>

								<liferay-ui:search-container-column-text>

									<%
									Map<String, Object> addLayoutData = new HashMap<>();

									addLayoutData.put("layout-page-template-entry-id", layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

									String imagePreviewURL = layoutPageTemplateEntry.getImagePreviewURL(themeDisplay);
									%>

									<c:choose>
										<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
											<liferay-frontend:vertical-card
												cssClass='<%= renderResponse.getNamespace() + "add-layout-action-option" %>'
												data="<%= addLayoutData %>"
												imageCSSClass="aspect-ratio-bg-contain"
												imageUrl="<%= imagePreviewURL %>"
												title="<%= layoutPageTemplateEntry.getName() %>"
												url="javascript:;"
											>
												<liferay-frontend:vertical-card-header>
													<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - layoutPageTemplateEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
												</liferay-frontend:vertical-card-header>
											</liferay-frontend:vertical-card>
										</c:when>
										<c:otherwise>
											<liferay-frontend:icon-vertical-card
												cssClass='<%= renderResponse.getNamespace() + "add-layout-action-option" %>'
												data="<%= addLayoutData %>"
												icon='<%= Objects.equals(layoutPageTemplateEntry.getType(), LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) ? "page-template" : "page" %>'
												title="<%= layoutPageTemplateEntry.getName() %>"
												url="javascript:;"
											>
												<liferay-frontend:vertical-card-header>
													<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - layoutPageTemplateEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
												</liferay-frontend:vertical-card-header>
											</liferay-frontend:icon-vertical-card>
										</c:otherwise>
									</c:choose>
								</liferay-ui:search-container-column-text>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator
								displayStyle="icon"
								markupView="lexicon"
								searchResultCssClass="show-quick-actions-on-hover table table-autofit table-heading-nowrap";
							/>
						</liferay-ui:search-container>

						<portlet:actionURL name="/layout/add_content_layout" var="addLayoutURL">
							<portlet:param name="mvcPath" value="/select_layout_page_template_entry.jsp" />
							<portlet:param name="groupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getGroupId()) %>" />
							<portlet:param name="portletResource" value="<%= layoutsAdminDisplayContext.getPortletResource() %>" />
							<portlet:param name="parentLayoutId" value="<%= String.valueOf(layoutsAdminDisplayContext.getParentLayoutId()) %>" />
							<portlet:param name="privateLayout" value="<%= String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()) %>" />
							<portlet:param name="explicitCreation" value="<%= Boolean.TRUE.toString() %>" />
						</portlet:actionURL>

						<%
						String autoSiteNavigationMenuNames = layoutsAdminDisplayContext.getAutoSiteNavigationMenuNames();
						%>

						<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
							var addLayoutActionOptionQueryClickHandler = dom.delegate(
								document.body,
								'click',
								'.<portlet:namespace />add-layout-action-option',
								function(event) {
									var actionElement = event.delegateTarget;

									modalCommands.openSimpleInputModal(
										{
											<c:if test="<%= Validator.isNotNull(autoSiteNavigationMenuNames) %>">
												checkboxFieldLabel: '<liferay-ui:message arguments="<%= autoSiteNavigationMenuNames %>" key="add-this-page-to-the-following-menus-x" />',
												checkboxFieldName: 'TypeSettingsProperties--addToAutoMenus--',
												checkboxFieldValue: true,
											</c:if>

											dialogTitle: '<liferay-ui:message key="add-page" />',
											formSubmitURL: '<%= addLayoutURL %>',
											idFieldName: 'TypeSettingsProperties--layoutPageTemplateEntryId--',
											idFieldValue: actionElement.dataset.layoutPageTemplateEntryId,
											mainFieldName: 'name',
											mainFieldLabel: '<liferay-ui:message key="name" />',
											namespace: '<portlet:namespace />',
											spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
										}
									);
								}
							);

							function handleDestroyPortlet () {
								addLayoutActionOptionQueryClickHandler.removeListener();

								Liferay.detach('destroyPortlet', handleDestroyPortlet);
							}

							Liferay.on('destroyPortlet', handleDestroyPortlet);
						</aui:script>
					</c:when>
					<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isBasicPages() %>">
						<liferay-util:include page="/select_basic_pages.jsp" servletContext="<%= application %>" />
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/select_global_templates.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>