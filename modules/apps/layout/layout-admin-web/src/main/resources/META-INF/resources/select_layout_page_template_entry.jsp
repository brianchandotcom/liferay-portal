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
String backURL = layoutsAdminDisplayContext.getRedirect();

if (Validator.isNull(backURL)) {
	PortletURL portletURL = layoutsAdminDisplayContext.getPortletURL();

	backURL = portletURL.toString();
}

SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "select-template"));
%>

<clay:container-fluid
	cssClass="container-view"
	id='<%= liferayPortletResponse.getNamespace() + "layoutPageTemplateEntries" %>'
>
	<clay:row>
		<clay:col
			lg="3"
		>
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
										<a class="nav-link text-truncate <%= (selectLayoutPageTemplateEntryDisplayContext.getLayoutPageTemplateCollectionId() == layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(), layoutsAdminDisplayContext.isPrivateLayout()) %>">
											<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>
										</a>
									</li>
								</c:if>

							<%
							}
							%>

							<li class="nav-item">
								<a class="nav-link text-truncate <%= selectLayoutPageTemplateEntryDisplayContext.isBasicTemplates() ? "active" : StringPool.BLANK %>" href="<%= layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(0, layoutsAdminDisplayContext.getSelPlid(), "basic-templates", layoutsAdminDisplayContext.isPrivateLayout()) %>">
									<liferay-ui:message key="basic-templates" />
								</a>
							</li>
							<li class="nav-item">
								<a class="nav-link text-truncate <%= selectLayoutPageTemplateEntryDisplayContext.isGlobalTemplates() ? "active" : StringPool.BLANK %>" href="<%= layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(0, layoutsAdminDisplayContext.getSelPlid(), "global-templates", layoutsAdminDisplayContext.isPrivateLayout()) %>">
									<liferay-ui:message key="global-templates" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>
			<clay:sheet
				size="full"
			>
				<h2 class="sheet-title">
					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<span class="text-uppercase">
								<c:choose>
									<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isContentPages() %>">

										<%
										LayoutPageTemplateCollection layoutPageTemplateCollection = LayoutPageTemplateCollectionLocalServiceUtil.fetchLayoutPageTemplateCollection(selectLayoutPageTemplateEntryDisplayContext.getLayoutPageTemplateCollectionId());
										%>

										<c:if test="<%= layoutPageTemplateCollection != null %>">
											<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>
										</c:if>
									</c:when>
									<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isBasicTemplates() %>">
										<liferay-ui:message key="basic-templates" />
									</c:when>
									<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isGlobalTemplates() %>">
										<liferay-ui:message key="global-templates" />
									</c:when>
								</c:choose>
							</span>
						</clay:content-col>
					</clay:content-row>
				</h2>

				<c:choose>
					<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isContentPages() %>">
						<liferay-ui:search-container
							iteratorURL="<%= currentURLObj %>"
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
								<liferay-ui:search-container-column-text>
									<clay:vertical-card
										verticalCard="<%= new SelectLayoutPageTemplateEntryVerticalCard(layoutPageTemplateEntry, renderRequest, renderResponse) %>"
									/>
								</liferay-ui:search-container-column-text>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator
								displayStyle="icon"
								markupView="lexicon"
							/>
						</liferay-ui:search-container>
					</c:when>
					<c:when test="<%= selectLayoutPageTemplateEntryDisplayContext.isBasicTemplates() %>">
						<liferay-util:include page="/select_basic_templates.jsp" servletContext="<%= application %>" />
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/select_global_templates.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</clay:sheet>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var delegate = delegateModule.default;

	var layoutPageTemplateEntries = document.getElementById(
		'<portlet:namespace />layoutPageTemplateEntries'
	);

	var addLayoutActionOptionQueryClickHandler = delegate(
		layoutPageTemplateEntries,
		'click',
		'.add-layout-action-option',
		function (event) {
			Liferay.Util.openModal({
				height: '60vh',
				id: '<portlet:namespace />addLayoutDialog',
				size: 'md',
				title: '<liferay-ui:message key="add-page" />',
				url: event.delegateTarget.dataset.addLayoutUrl,
			});
		}
	);

	var addLayoutActionOptionQueryKeyDownHandler = delegate(
		layoutPageTemplateEntries,
		'keydown',
		'.add-layout-action-option',
		function (event) {
			if (event.code === 'Space' || event.code === 'Enter') {
				event.preventDefault();
				event.delegateTarget.click();
			}
		}
	);

	function handleDestroyPortlet() {
		addLayoutActionOptionQueryClickHandler.dispose();
		addLayoutActionOptionQueryKeyDownHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>