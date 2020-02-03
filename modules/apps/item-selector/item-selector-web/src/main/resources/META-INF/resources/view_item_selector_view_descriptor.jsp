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
ItemSelectorViewDescriptorRendererDisplayContext itemSelectorViewDescriptorRendererDisplayContext = (ItemSelectorViewDescriptorRendererDisplayContext)request.getAttribute(ItemSelectorViewDescriptorRendererDisplayContext.class.getName());

ItemSelectorViewDescriptor itemSelectorViewDescriptor = itemSelectorViewDescriptorRendererDisplayContext.getItemSelectorViewDescriptor();

SearchContainer searchContainer = itemSelectorViewDescriptor.getSearchContainer();
%>

<c:if test="<%= itemSelectorViewDescriptor.isShowManagementToolbar() %>">
	<clay:management-toolbar
		displayContext="<%= new ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext(itemSelectorViewDescriptor, request, liferayPortletRequest, liferayPortletResponse, searchContainer) %>"
	/>
</c:if>

<div class="container-fluid container-fluid-max-xl item-selector lfr-item-viewer" id="<portlet:namespace />entriesContainer">
	<c:if test="<%= itemSelectorViewDescriptor.isShowBreadcrumb() %>">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= itemSelectorViewDescriptorRendererDisplayContext.getBreadcrumbEntries(currentURLObj) %>"
		/>
	</c:if>

	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= searchContainer %>"
		var="entriesSearch"
	>
		<liferay-ui:search-container-row
			className="Object"
			cssClass="entry-display-style"
			modelVar="entry"
		>

			<%
			ItemSelectorViewDescriptor.ItemDescriptor itemDescriptor = itemSelectorViewDescriptor.getItemDescriptor(row.getObject());

			row.setData(
				HashMapBuilder.<String, Object>put(
					"value",
					itemDescriptor.getPayload()
				).build());
			%>

			<c:choose>
				<c:when test="<%= itemSelectorViewDescriptorRendererDisplayContext.isIconDisplayStyle() %>">

					<%
					row.setCssClass("entry entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<c:choose>
							<c:when test="<%= itemDescriptor.isCompact() %>">
								<clay:horizontal-card
									horizontalCard="<%= new ItemDescriptorHorizontalCard(itemDescriptor, renderRequest) %>"
								/>
							</c:when>
							<c:otherwise>
								<clay:vertical-card
									verticalCard="<%= new ItemDescriptorVerticalCard(itemDescriptor, renderRequest) %>"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<c:if test="<%= itemDescriptor.getUserId() != UserConstants.USER_ID_DEFAULT %>">
						<liferay-ui:search-container-column-user
							showDetails="<%= false %>"
							userId="<%= itemDescriptor.getUserId() %>"
						/>
					</c:if>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<c:if test="<%= Objects.nonNull(itemDescriptor.getModifiedDate()) %>">

							<%
							Date modifiedDate = itemDescriptor.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
							%>

							<c:choose>
								<c:when test="<%= Validator.isNull(itemDescriptor.getUserName()) %>">
									<span class="text-default">
										<liferay-ui:message arguments="<%= new String[] {itemDescriptor.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
									</span>
								</c:when>
								<c:otherwise>
									<span class="text-default">
										<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription} %>" key="modified-x-ago" />
									</span>
								</c:otherwise>
							</c:choose>
						</c:if>

						<p class="font-weight-bold h5">
							<%= itemDescriptor.getTitle(locale) %>
						</p>

						<p class="h6 text-default">
							<%= itemDescriptor.getSubtitle(locale) %>
						</p>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectItemHandler = dom.delegate(
		document.querySelector('#<portlet:namespace/>entriesContainer'),
		'click',
		'.entry',
		function(event) {
			dom.removeClasses(
				document.querySelectorAll('.form-check-card.active'),
				'active'
			);
			dom.addClasses(
				dom.closest(event.delegateTarget, '.form-check-card'),
				'active'
			);

			Liferay.Util.getOpener().Liferay.fire(
				'<%= itemSelectorViewDescriptorRendererDisplayContext.getItemSelectedEventName() %>',
				{
					data: {
						returnType:
							'<%= itemSelectorViewDescriptorRendererDisplayContext.getReturnType() %>',
						value: event.delegateTarget.dataset.value
					}
				}
			);
		}
	);

	Liferay.on('destroyPortlet', function removeListener() {
		selectItemHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	});
</aui:script>