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
InfoCollectionProviderItemSelectorDisplayContext infoCollectionProviderItemSelectorDisplayContext = (InfoCollectionProviderItemSelectorDisplayContext)request.getAttribute(InfoCollectionProviderItemSelectorWebKeys.INFO_COLLECTION_PROVIDER_ITEM_SELECTOR_DISPLAY_CONTEXT);

SearchContainer<InfoCollectionProvider<?>> searchContainer = infoCollectionProviderItemSelectorDisplayContext.getSearchContainer();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new InfoCollectionProviderItemSelectorManagementToolbarDisplayContext(request, infoCollectionProviderItemSelectorDisplayContext, liferayPortletRequest, liferayPortletResponse, searchContainer) %>"
/>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "entriesContainer" %>'
>
	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= searchContainer %>"
		var="entriesSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.info.collection.provider.InfoCollectionProvider"
			modelVar="infoCollectionProvider"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"value", infoCollectionProviderItemSelectorDisplayContext.getPayload(infoCollectionProvider)
				).build());
			%>

			<c:choose>
				<c:when test="<%= infoCollectionProviderItemSelectorDisplayContext.isIconDisplayStyle() %>">

					<%
					row.setCssClass("card-page-item card-page-item-asset entry " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new InfoCollectionProviderVerticalCard(infoCollectionProvider, renderRequest, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= infoCollectionProviderItemSelectorDisplayContext.isDescriptiveDisplayStyle() %>">

					<%
					row.setCssClass("item-selector-list-row " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
						cssClass="entry"
					>
						<p class="font-weight-bold h5">
							<%= infoCollectionProvider.getLabel(locale) %>
						</p>

						<p class="h6 text-default">
							<%= infoCollectionProviderItemSelectorDisplayContext.getTitle(infoCollectionProvider, locale) %>

							<%
							String subtitle = infoCollectionProviderItemSelectorDisplayContext.getSubtitle(infoCollectionProvider, locale);
							%>

							<c:if test="<%= Validator.isNotNull(subtitle) %>">
								- <%= subtitle %>
							</c:if>
						</p>

						<p class="h6 text-default">
							<c:choose>
								<c:when test="<%= infoCollectionProviderItemSelectorDisplayContext.supportsFilters(infoCollectionProvider) %>">
									<liferay-ui:message key="supports-filters" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="does-not-support-filters" />
								</c:otherwise>
							</c:choose>
						</p>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="title"
					>
						<a class="entry" title="<%= infoCollectionProvider.getLabel(locale) %>">
							<%= infoCollectionProvider.getLabel(locale) %>
						</a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-150"
						name="type"
						value="<%= infoCollectionProviderItemSelectorDisplayContext.getTitle(infoCollectionProvider, locale) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-150"
						name="subtype"
						value="<%= infoCollectionProviderItemSelectorDisplayContext.getSubtitle(infoCollectionProvider, locale) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-smallest table-column-text-center"
						name="supports-filters"
					>
						<c:choose>
							<c:when test="<%= infoCollectionProviderItemSelectorDisplayContext.supportsFilters(infoCollectionProvider) %>">
								<liferay-ui:message key="yes" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="no" />
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= infoCollectionProviderItemSelectorDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var delegate = delegateModule.default;

	var selectItemHandler = delegate(
		document.querySelector('#<portlet:namespace />entriesContainer'),
		'click',
		'.entry',
		(event) => {
			var activeCards = document.querySelectorAll('.form-check-card.active');

			if (activeCards.length) {
				activeCards.forEach((card) => {
					card.classList.remove('active');
				});
			}

			var target = event.delegateTarget;

			var newSelectedCard = target.closest('.form-check-card');

			if (newSelectedCard) {
				newSelectedCard.classList.add('active');
			}

			var domElement = target.closest('li');

			if (domElement == null) {
				domElement = target.closest('dd');
			}

			if (domElement == null) {
				domElement = target.closest('tr');
			}

			var itemValue = '';

			if (domElement != null) {
				itemValue = domElement.dataset.value;
			}

			Liferay.Util.getOpener().Liferay.fire(
				'<%= infoCollectionProviderItemSelectorDisplayContext.getItemSelectedEventName() %>',
				{
					data: {
						returnType:
							'<%= infoCollectionProviderItemSelectorDisplayContext.getReturnType() %>',
						value: itemValue,
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