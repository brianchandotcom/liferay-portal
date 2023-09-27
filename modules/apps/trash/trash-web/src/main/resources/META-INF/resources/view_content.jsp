<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
TrashHandler trashHandler = trashDisplayContext.getTrashHandler();
%>

<liferay-util:include page="/restore_path.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test="<%= trashHandler.isContainerModel() %>">
		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new TrashContainerManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, trashDisplayContext) %>"
		/>

		<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/trash/info_panel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
			>
				<liferay-util:include page="/view_content_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>

			<clay:container-fluid
				cssClass="container-view sidenav-content"
			>
				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= trashDisplayContext.getBaseModelBreadcrumbEntries() %>"
				/>

				<liferay-ui:search-container
					id="trash"
					searchContainer="<%= trashDisplayContext.getTrashContainerSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.TrashedModel"
						modelVar="curTrashedModel"
					>

						<%
						ClassedModel classedModel = (ClassedModel)curTrashedModel;

						String modelClassName = classedModel.getModelClassName();

						TrashHandler curTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(modelClassName);

						TrashRenderer curTrashRenderer = curTrashHandler.getTrashRenderer(curTrashedModel.getTrashEntryClassPK());

						String rowURL = PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCPath(
							"/view_content.jsp"
						).setRedirect(
							currentURL
						).setParameter(
							"classNameId", PortalUtil.getClassNameId(curTrashRenderer.getClassName())
						).setParameter(
							"classPK", curTrashRenderer.getClassPK()
						).buildString();
						%>

						<c:choose>
							<c:when test="<%= trashDisplayContext.isDescriptiveView() %>">
								<liferay-ui:search-container-column-icon
									icon="<%= curTrashRenderer.getIconCssClass() %>"
									toggleRowChecker="<%= false %>"
								/>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<h5>
										<aui:a href="<%= rowURL %>">
											<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
										</aui:a>
									</h5>

									<h6 class="text-default">
										<liferay-ui:message key="type" /> <%= ResourceActionsUtil.getModelResource(locale, curTrashRenderer.getClassName()) %>
									</h6>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
										dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(modelClassName, curTrashedModel.getTrashEntryClassPK()) %>"
										propsTransformer="js/EntriesPropsTransformer"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test="<%= trashDisplayContext.isIconView() %>">
								<c:choose>
									<c:when test="<%= !curTrashHandler.isContainerModel() %>">
										<liferay-ui:search-container-column-text>
											<clay:vertical-card
												propsTransformer="js/EntriesPropsTransformer"
												verticalCard="<%= new TrashContentVerticalCard(curTrashedModel, curTrashRenderer, liferayPortletResponse, renderRequest, rowURL) %>"
											/>
										</liferay-ui:search-container-column-text>
									</c:when>
									<c:otherwise>
										<liferay-ui:search-container-column-text>

											<%
											row.setCssClass("card-page-item card-page-item-directory");
											%>

											<clay:horizontal-card
												horizontalCard="<%= new TrashContentHorizontalCard(curTrashedModel, curTrashRenderer, liferayPortletResponse, renderRequest, rowURL) %>"
												propsTransformer="js/EntriesPropsTransformer"
											/>
										</liferay-ui:search-container-column-text>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="<%= trashDisplayContext.isListView() %>">
								<liferay-ui:search-container-column-text
									name="name"
									truncate="<%= true %>"
								>
									<aui:a href="<%= rowURL %>">
										<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
									</aui:a>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
										dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(modelClassName, curTrashedModel.getTrashEntryClassPK()) %>"
										propsTransformer="js/EntriesPropsTransformer"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= trashDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
						resultRowSplitter="<%= new TrashResultRowSplitter() %>"
					/>
				</liferay-ui:search-container>
			</clay:container-fluid>
		</div>
	</c:when>
	<c:otherwise>

		<%
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(trashDisplayContext.getViewContentRedirectURL());

		TrashRenderer trashRenderer = trashDisplayContext.getTrashRenderer();

		renderResponse.setTitle(trashRenderer.getTitle(locale));
		%>

		<clay:container-fluid
			cssClass="container-view"
		>
			<clay:sheet>
				<liferay-asset:asset-display
					renderer="<%= trashRenderer %>"
				/>
			</clay:sheet>
		</clay:container-fluid>
	</c:otherwise>
</c:choose>