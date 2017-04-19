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
String redirect = trashDisplayContext.getViewContentRedirectURL();

long classPK = trashDisplayContext.getClassPK();

PortletURL portletURL = renderResponse.createRenderURL();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "recycle-bin"), portletURL.toString());

PortletURL containerModelURL = renderResponse.createRenderURL();

TrashHandler trashHandler = trashDisplayContext.getTrashHandler();

String trashHandlerContainerModelClassName = trashHandler.getContainerModelClassName(classPK);

containerModelURL.setParameter("mvcPath", "/view_content.jsp");
containerModelURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(trashHandlerContainerModelClassName)));

TrashUtil.addBaseModelBreadcrumbEntries(request, liferayPortletResponse, trashDisplayContext.getClassName(), classPK, containerModelURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

TrashRenderer trashRenderer = trashDisplayContext.getTrashRenderer();

renderResponse.setTitle(trashRenderer.getTitle(locale));
%>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/view_content_toolbar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/restore_path.jsp" servletContext="<%= application %>" />

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/trash/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
	>
		<liferay-util:include page="/view_content_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<div id="<portlet:namespace />trashContainer">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showParentGroups="<%= false %>"
			/>

			<%
			PortletURL iteratorURL = renderResponse.createRenderURL();

			iteratorURL.setParameter("mvcPath", "/view_content.jsp");
			iteratorURL.setParameter("classNameId", String.valueOf(trashDisplayContext.getClassNameId()));
			iteratorURL.setParameter("classPK", String.valueOf(classPK));

			String emptyResultsMessage = LanguageUtil.format(request, "this-x-does-not-contain-an-entry", ResourceActionsUtil.getModelResource(locale, trashDisplayContext.getClassName()), false);
			%>

			<liferay-ui:search-container
				deltaConfigurable="<%= false %>"
				emptyResultsMessage="<%= emptyResultsMessage %>"
				id="trash"
				iteratorURL="<%= iteratorURL %>"
				total="<%= trashHandler.getTrashModelsCount(classPK) %>"
			>
				<liferay-ui:search-container-results
					results="<%= trashHandler.getTrashModelTrashedModels(classPK, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.TrashedModel"
					modelVar="curTrashedModel"
				>

					<%
					String modelClassName = ((ClassedModel)curTrashedModel).getModelClassName();

					TrashHandler curTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(modelClassName);

					TrashRenderer curTrashRenderer = curTrashHandler.getTrashRenderer(curTrashedModel.getTrashEntryClassPK());

					PortletURL rowURL = renderResponse.createRenderURL();

					if (curTrashHandler.isContainerModel()) {
						rowURL.setParameter("mvcPath", "/view_content.jsp");
					}
					else {
						rowURL.setParameter("mvcPath", "/preview.jsp");
						rowURL.setWindowState(LiferayWindowState.POP_UP);
					}

					rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(curTrashRenderer.getClassName())));
					rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
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
									<c:choose>
										<c:when test="<%= curTrashHandler.isContainerModel() %>">
											<aui:a href="<%= rowURL.toString() %>">
												<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
											</aui:a>
										</c:when>
										<c:otherwise>
											<span class="preview" data-title="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>" data-url="<%= rowURL.toString() %>">
												<aui:a href="javascript:;">
													<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
												</aui:a>
											</span>
										</c:otherwise>
									</c:choose>
								</h5>

								<h6 class="text-default">
									<liferay-ui:message key="type" /> <%= ResourceActionsUtil.getModelResource(locale, curTrashRenderer.getClassName()) %>
								</h6>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-jsp
								path="/view_content_action.jsp"
							/>
						</c:when>
						<c:when test="<%= trashDisplayContext.isIconView() %>">

							<%
							row.setCssClass("entry-card lfr-asset-item");
							%>

							<liferay-ui:search-container-column-text>
								<c:choose>
									<c:when test="<%= !curTrashHandler.isContainerModel() %>">

										<%
										Map<String, Object> data = new HashMap<String, Object>();

										data.put("title", HtmlUtil.escape(curTrashRenderer.getTitle(locale)));
										data.put("url", rowURL.toString());
										%>

										<liferay-frontend:icon-vertical-card
											actionJsp="/view_content_action.jsp"
											actionJspServletContext="<%= application %>"
											cssClass="preview"
											data="<%= data %>"
											icon="<%= curTrashRenderer.getIconCssClass() %>"
											resultRow="<%= row %>"
											title="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>"
											url="javascript:;"
										>
											<liferay-frontend:vertical-card-footer>
												<%= ResourceActionsUtil.getModelResource(locale, curTrashRenderer.getClassName()) %>
											</liferay-frontend:vertical-card-footer>
										</liferay-frontend:icon-vertical-card>
									</c:when>
									<c:otherwise>
										<liferay-frontend:horizontal-card
											actionJsp="/view_content_action.jsp"
											actionJspServletContext="<%= application %>"
											resultRow="<%= row %>"
											text="<%= curTrashRenderer.getTitle(locale) %>"
											url="<%= rowURL.toString() %>"
										>
										</liferay-frontend:horizontal-card>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test="<%= trashDisplayContext.isListView() %>">
							<liferay-ui:search-container-column-text
								name="name"
								truncate="<%= true %>"
							>
								<c:choose>
									<c:when test="<%= curTrashHandler.isContainerModel() %>">
										<aui:a href="<%= rowURL.toString() %>">
											<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
										</aui:a>
									</c:when>
									<c:otherwise>
										<span class="preview" data-title="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>" data-url="<%= rowURL.toString() %>">
											<aui:a href="javascript:;">
												<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
											</aui:a>
										</span>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-jsp
								path="/view_content_action.jsp"
							/>
						</c:when>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= trashDisplayContext.getDisplayStyle() %>" markupView="lexicon" resultRowSplitter="<%= new TrashResultRowSplitter() %>" />
			</liferay-ui:search-container>
		</div>
	</div>
</div>

<aui:script use="liferay-url-preview">
	A.one('#<portlet:namespace />trashContainer').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var parent = currentTarget.ancestor('.preview');

			var urlPreview = new Liferay.UrlPreview(
				{
					title: parent.attr('data-title'),
					url: parent.attr('data-url')
				}
			);

			urlPreview.open();
		},
		'.preview a'
	);
</aui:script>