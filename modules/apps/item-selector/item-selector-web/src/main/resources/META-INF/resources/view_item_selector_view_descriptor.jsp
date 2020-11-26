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

ItemSelectorViewDescriptor<Object> itemSelectorViewDescriptor = itemSelectorViewDescriptorRendererDisplayContext.getItemSelectorViewDescriptor();

SearchContainer<Object> searchContainer = itemSelectorViewDescriptorRendererDisplayContext.getSearchContainer();
%>

<c:if test="<%= itemSelectorViewDescriptor.isShowManagementToolbar() %>">
	<clay:management-toolbar-v2
		displayContext="<%= new ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext(itemSelectorViewDescriptor, request, liferayPortletRequest, liferayPortletResponse, searchContainer) %>"
	/>
</c:if>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "entriesContainer" %>'
>
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
			cssClass="entry"
			modelVar="entry"
		>

			<%
			ItemSelectorViewDescriptor.ItemDescriptor itemDescriptor = itemSelectorViewDescriptor.getItemDescriptor(row.getObject());

			row.setData(
				HashMapBuilder.<String, Object>put(
					"value", itemDescriptor.getPayload()
				).build());
			%>

			<c:choose>
				<c:when test="<%= itemSelectorViewDescriptorRendererDisplayContext.isIconDisplayStyle() %>">

					<%
					row.setCssClass("entry-card entry-display-style lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<c:choose>
							<c:when test="<%= itemDescriptor.isCompact() %>">
								<clay:horizontal-card-v2
									horizontalCard="<%= new ItemDescriptorHorizontalCard(itemDescriptor, renderRequest, searchContainer.getRowChecker()) %>"
								/>
							</c:when>
							<c:otherwise>
								<clay:vertical-card
									verticalCard="<%= new ItemDescriptorVerticalCard(itemDescriptor, renderRequest, searchContainer.getRowChecker()) %>"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= itemSelectorViewDescriptorRendererDisplayContext.isDescriptiveDisplayStyle() %>">

					<%
					row.setCssClass("item-selector-list-row " + row.getCssClass());
					%>

					<c:if test="<%= itemDescriptor.getUserId() != UserConstants.USER_ID_DEFAULT %>">
						<liferay-ui:search-container-column-user
							showDetails="<%= false %>"
							userId="<%= itemDescriptor.getUserId() %>"
						/>
					</c:if>

					<c:if test="<%= Validator.isNotNull(itemDescriptor.getImageURL()) %>">
						<liferay-ui:search-container-column-image
							src="<%= itemDescriptor.getImageURL() %>"
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
								<c:when test="<%= Validator.isNotNull(itemDescriptor.getUserName()) %>">
									<span class="text-default">
										<liferay-ui:message arguments="<%= new String[] {itemDescriptor.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
									</span>
								</c:when>
								<c:otherwise>
									<span class="text-default">
										<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" />
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
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						name="title"
						value="<%= itemDescriptor.getTitle(locale) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						name="user"
						value="<%= itemDescriptor.getUserName() %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						name="modified-date"
					>
						<c:if test="<%= Objects.nonNull(itemDescriptor.getModifiedDate()) %>">

							<%
							Date modifiedDate = itemDescriptor.getModifiedDate();
							%>

							<span class="text-default">
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="modified-x-ago" />
							</span>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= itemSelectorViewDescriptorRendererDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<c:choose>
	<c:when test="<%= itemSelectorViewDescriptorRendererDisplayContext.isMultipleSelection() %>">
		<aui:script use="liferay-search-container">
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />entries'
			);

			searchContainer.on('rowToggled', function (event) {
				var searchContainerItems = event.elements.allSelectedElements;

				var arr = [];

				searchContainerItems.each(function () {
					var domElement = this.ancestor('li');

					if (domElement == null) {
						domElement = this.ancestor('tr');
					}

					if (domElement != null) {
						var itemValue = domElement.getDOM().dataset.value;

						arr.push(itemValue);
					}
				});

				Liferay.Util.getOpener().Liferay.fire(
					'<%= itemSelectorViewDescriptorRendererDisplayContext.getItemSelectedEventName() %>',
					{
						data: {
							returnType:
								'<%= itemSelectorViewDescriptorRendererDisplayContext.getReturnType() %>',
							value: arr,
						},
					}
				);
			});
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
			var delegate = delegateModule.default;

			var selectItemHandler = delegate(
				document.querySelector('#<portlet:namespace />entriesContainer'),
				'click',
				'.entry',
				function (event) {
					var activeCards = document.querySelectorAll('.form-check-card.active');

					if (activeCards.length) {
						activeCards.forEach(function (card) {
							card.classList.remove('active');
						});
					}

					var newSelectedCard = event.delegateTarget.closest('.form-check-card');

					if (newSelectedCard) {
						newSelectedCard.classList.add('active');
					}

					Liferay.Util.getOpener().Liferay.fire(
						'<%= itemSelectorViewDescriptorRendererDisplayContext.getItemSelectedEventName() %>',
						{
							data: {
								returnType:
									'<%= itemSelectorViewDescriptorRendererDisplayContext.getReturnType() %>',
								value: event.delegateTarget.dataset.value,
							},
						}
					);
				}
			);

			Liferay.on('destroyPortlet', function removeListener() {
				selectItemHandler.dispose();

				Liferay.detach('destroyPortlet', removeListener);
			});
		</aui:script>
	</c:otherwise>
</c:choose>