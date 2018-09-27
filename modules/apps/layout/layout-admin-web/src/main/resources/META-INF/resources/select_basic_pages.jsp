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
%>

<div class="lfr-search-container-wrapper" id="<portlet:namespace/>layoutTypes">
	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getPrimaryTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="main-types" />
		</h6>

		<div class="row">

			<%
			for (String primaryType : selectLayoutPageTemplateEntryDisplayContext.getPrimaryTypes()) {
				LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(primaryType);

				ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
			%>

				<div class="col-md-4">
					<div class="card card-type-asset">
						<div class="aspect-ratio">
							<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid layout-type-img">
								<img src="<%= PortalUtil.getPathContext(request) %>/images/<%= primaryType %>.svg" />
							</div>
						</div>

						<div class="card-body">
							<div class="card-row">
								<div class="autofit-col autofit-col-expand">
									<section class="autofit-section">
										<h3 class="card-title">
											<span class="text-truncate-inline">
												<portlet:renderURL var="addLayoutURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
													<portlet:param name="mvcRenderCommandName" value="/layout/add_layout" />
													<portlet:param name="type" value="<%= primaryType %>" />
												</portlet:renderURL>

												<a class="add-layout-action-option" data-add-layout-url="<%= addLayoutURL %>" href="javascript:;"><%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + primaryType) %></a>
											</span>
										</h3>

										<p class="card-subtitle">
											<span class="text-truncate-inline">
												<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + primaryType + ".description") %>
											</span>
										</p>
									</section>
								</div>
							</div>
						</div>
					</div>
				</div>

			<%
			}
			%>

		</div>
	</c:if>

	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="other" />
		</h6>

		<div class="row">

			<%
			for (String type : selectLayoutPageTemplateEntryDisplayContext.getTypes()) {
				LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

				ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
			%>

				<div class="col-md-4">
					<div class="card card-horizontal card-type-directory">
						<div class="card-body">
							<div class="card-row">
								<div class="autofit-col">
									<span class="sticker">
										<svg aria-hidden="true" class="lexicon-icon lexicon-icon-page">
											<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#page" />
										</svg>
									</span>
								</div>

								<div class="autofit-col autofit-col-expand autofit-col-gutters">
									<section class="autofit-section">
										<h3 class="card-title">
											<span class="text-truncate-inline">
												<portlet:renderURL var="addLayoutURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
													<portlet:param name="mvcRenderCommandName" value="/layout/add_layout" />
													<portlet:param name="type" value="<%= type %>" />
												</portlet:renderURL>

												<a class="add-layout-action-option text-truncate" data-add-layout-url="<%= addLayoutURL %>" href="javascript:;" title="<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type) %>"><%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type) %></a>
											</span>
										</h3>
									</section>
								</div>
							</div>
						</div>
					</div>
				</div>

			<%
			}
			%>

		</div>
	</c:if>
</div>

<aui:script use="aui-base">
	var addLayoutActionOptionQueryClickHandler = A.one('#<portlet:namespace/>layoutTypes').delegate(
		'click',
		function(event) {
			var actionElement = event.target;

			Liferay.Util.openWindow(
				{
					dialog: {
						destroyOnHide: true,
						resizable: false
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: '<portlet:namespace />addLayoutDialog',
					title: '<liferay-ui:message key="add-page" />',
					uri: actionElement.getData('add-layout-url')
				}
			);
		},
		'.add-layout-action-option'
	);

	function handleDestroyPortlet () {
		addLayoutActionOptionQueryClickHandler.detach();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>