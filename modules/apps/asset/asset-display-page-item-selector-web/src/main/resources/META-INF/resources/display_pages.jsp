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
AssetDisplayPagesItemSelectorViewDisplayContext assetDisplayPagesItemSelectorViewDisplayContext = (AssetDisplayPagesItemSelectorViewDisplayContext)request.getAttribute(AssetDisplayPageItemSelectorWebKeys.ASSET_DISPLAY_PAGES_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	displayContext="<%= new AssetDisplayPagesItemSelectorViewManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, assetDisplayPagesItemSelectorViewDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="displayPages"
		searchContainer="<%= assetDisplayPagesItemSelectorViewDisplayContext.getAssetDisplayPageSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card form-check-card lfr-asset-item " + row.getCssClass());

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("id", layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
			data.put("name", layoutPageTemplateEntry.getName());
			data.put("type", "asset-display-page");
			%>

			<liferay-ui:search-container-column-text>
				<liferay-frontend:icon-vertical-card
					cssClass="entry-display-style layout-page-template-entry"
					data="<%= data %>"
					icon="page"
					resultRow="<%= row %>"
					title="<%= layoutPageTemplateEntry.getName() %>"
					url="javascript:;"
				>
					<liferay-frontend:vertical-card-footer>
						<div class="row">
							<div class="col text-truncate">

								<%
								String typeLabel = assetDisplayPagesItemSelectorViewDisplayContext.getTypeLabel(layoutPageTemplateEntry);
								%>

								<c:choose>
									<c:when test="<%= Validator.isNotNull(typeLabel) %>">
										<%= typeLabel %>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="card-subtitle row">
							<div class="col text-truncate">

								<%
								String subtypeLabel = assetDisplayPagesItemSelectorViewDisplayContext.getSubtypeLabel(layoutPageTemplateEntry);
								%>

								<c:choose>
									<c:when test="<%= Validator.isNotNull(subtypeLabel) %>">
										<%= subtypeLabel %>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</liferay-frontend:vertical-card-footer>
				</liferay-frontend:icon-vertical-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectFragmentEntryHandler = dom.delegate(
		document.querySelector('#<portlet:namespace/>fm'),
		'click',
		'.layout-page-template-entry',
		function(event) {
			dom.removeClasses(document.querySelectorAll('.form-check-card.active'), 'active');
			dom.addClasses(dom.closest(event.delegateTarget, '.form-check-card'), 'active');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= assetDisplayPagesItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
				{
					data: event.delegateTarget.dataset
				}
			);
		}
	);

	function removeListener() {
		selectFragmentEntryHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	}

	Liferay.on('destroyPortlet', removeListener);
</aui:script>