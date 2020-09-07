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
CategoryCPDisplayLayoutDisplayContext categoryCPDisplayLayoutDisplayContext = (CategoryCPDisplayLayoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = categoryCPDisplayLayoutDisplayContext.getCommerceChannel();
CPDisplayLayout cpDisplayLayout = categoryCPDisplayLayoutDisplayContext.getCPDisplayLayout();

long[] assetCategoryIds = new long[0];

if (cpDisplayLayout != null) {
	assetCategoryIds = ArrayUtil.append(assetCategoryIds, cpDisplayLayout.getClassPK());
}

String layoutBreadcrumb = StringPool.BLANK;

if (cpDisplayLayout != null) {
	Layout selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(cpDisplayLayout.getLayoutUuid(), commerceChannel.getSiteGroupId(), false);

	if (selLayout == null) {
		selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(cpDisplayLayout.getLayoutUuid(), commerceChannel.getSiteGroupId(), true);
	}

	if (selLayout != null) {
		layoutBreadcrumb = categoryCPDisplayLayoutDisplayContext.getLayoutBreadcrumb(selLayout);
	}
}
%>

<commerce-ui:side-panel-content
	title='<%= (cpDisplayLayout == null) ? LanguageUtil.get(request, "add-display-layout") : LanguageUtil.get(request, "edit-display-layout") %>'
>
	<portlet:actionURL name="editCategoryDisplayLayout" var="editCategoryDisplayPageActionURL" />

	<aui:form action="<%= editCategoryDisplayPageActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDisplayLayout == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= categoryCPDisplayLayoutDisplayContext.getCommerceChannelId() %>" />

		<liferay-ui:error exception="<%= CPDisplayLayoutEntryException.class %>" message="please-select-a-valid-category" />
		<liferay-ui:error exception="<%= CPDisplayLayoutLayoutUuidException.class %>" message="please-select-a-valid-layout" />

		<aui:model-context bean="<%= cpDisplayLayout %>" model="<%= CPDisplayLayout.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<liferay-asset:asset-categories-error />

				<h4><liferay-ui:message key="select-categories" /></h4>

				<div id="<portlet:namespace />categoriesContainer"></div>

				<aui:button name="selectCategories" value="select" />

				<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= (cpDisplayLayout == null) ? StringPool.BLANK : cpDisplayLayout.getLayoutUuid() %>" />

				<aui:field-wrapper helpMessage="category-display-page-help" label="category-display-page">
					<p class="text-default">
						<span class="<%= Validator.isNull(layoutBreadcrumb) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
							<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
						</span>
						<span id="<portlet:namespace />displayPageNameInput">
							<c:choose>
								<c:when test="<%= Validator.isNull(layoutBreadcrumb) %>">
									<span class="text-muted"><liferay-ui:message key="none" /></span>
								</c:when>
								<c:otherwise>
									<%= layoutBreadcrumb %>
								</c:otherwise>
							</c:choose>
						</span>
					</p>
				</aui:field-wrapper>

				<aui:button name="chooseDisplayPage" value="choose" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</commerce-ui:side-panel-content>

<aui:script use="liferay-item-selector-dialog">
	var displayPageItemContainer = window.document.querySelector(
		'#<portlet:namespace />displayPageItemContainer'
	);
	var displayPageItemRemove = window.document.querySelector(
		'#<portlet:namespace />displayPageItemRemove'
	);
	var displayPageNameInput = window.document.querySelector(
		'#<portlet:namespace />displayPageNameInput'
	);
	var pagesContainerInput = window.document.querySelector(
		'#<portlet:namespace />pagesContainerInput'
	);

	window.document
		.querySelector('#<portlet:namespace />chooseDisplayPage')
		.addEventListener('click', function (event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'selectDisplayPage',
				on: {
					selectedItemChange: function (event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							pagesContainerInput.value = selectedItem.id;

							displayPageNameInput.innerHTML = selectedItem.name;

							displayPageItemRemove.classList.remove('hide');
						}
					},
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="select-category-display-page" />',
				url:
					'<%= categoryCPDisplayLayoutDisplayContext.getItemSelectorUrl(renderRequest) %>',
			});

			itemSelectorDialog.open();
		});

	displayPageItemRemove.addEventListener('click', function () {
		displayPageNameInput.innerHTML = '<liferay-ui:message key="none" />';

		pagesContainerInput.value = '';

		displayPageItemRemove.classList.add('hide');
	});
</aui:script>

<%
String selectedCategories = StringUtil.merge(assetCategoryIds, StringPool.COMMA);
%>

<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var categoriesContainer = document.querySelector(
		'#<portlet:namespace />categoriesContainer'
	);

	function createLabel(id, title) {
		var labelContainer = document.createElement('span');
		labelContainer.classList.add('label');
		labelContainer.classList.add('label-dismissible');
		labelContainer.classList.add('label-secondary');
		labelContainer.setAttribute('id', id);

		var linkContainer = document.createElement('span');
		linkContainer.classList.add('label-item');
		linkContainer.classList.add('label-item-expand');

		var link = document.createElement('a');
		link.setAttribute('href', '#' + id);
		link.innerText = title;
		linkContainer.appendChild(link);

		var buttonContainer = document.createElement('span');
		buttonContainer.classList.add('label-item');
		buttonContainer.classList.add('label-item-after');

		var button = document.createElement('button');
		button.classList.add('close');
		button.setAttribute('aria-label', 'Close');
		button.setAttribute('type', 'button');
		button.addEventListener('click', handleCloseButtonClick);

		var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
		svg.classList.add('lexicon-icon');
		svg.classList.add('lexicon-icon-times');
		svg.setAttribute('focusable', false);
		svg.setAttribute('role', 'presentation');

		var use = document.createElementNS('http://www.w3.org/2000/svg', 'use');
		use.setAttribute(
			'href',
			Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg#times'
		);

		svg.appendChild(use);
		button.appendChild(svg);
		buttonContainer.appendChild(button);

		labelContainer.appendChild(linkContainer);
		labelContainer.appendChild(buttonContainer);

		return labelContainer;
	}

	function handleCloseButtonClick(event) {
		var button = event.currentTarget;
		categoriesContainer.removeChild(button.parentElement.parentElement);
	}

	window.document
		.querySelector('#<portlet:namespace />selectCategories')
		.addEventListener('click', function (event) {
			var itemSelectorDialog = new ItemSelectorDialog.default({
				eventName: '<portlet:namespace />selectCategory',
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="select-category-display-page" />',
				url:
					'<%= categoryCPDisplayLayoutDisplayContext.getCategorySelectorURL(renderResponse, selectedCategories) %>',
			});

			itemSelectorDialog.open();

			itemSelectorDialog.on('selectedItemChange', function (event) {
				if (event.selectedItem) {
					var selectedItem = event.selectedItem;

					Object.keys(selectedItem).forEach(function (key) {
						var item = selectedItem[key];

						var existingNode = categoriesContainer.querySelector(
							'#category-' + item.categoryId
						);

						if (item.unchecked) {
							if (categoriesContainer.contains(existingNode)) {
								categoriesContainer.removeChild(existingNode);
							}
							delete selectedItem.key;
							return;
						}

						if (!categoriesContainer.contains(existingNode)) {
							var categoryNode = createLabel(
								'category-' + item.categoryId,
								item.value
							);
							categoriesContainer.appendChild(categoryNode);
						}
					});
				}
			});
		});
</aui:script>