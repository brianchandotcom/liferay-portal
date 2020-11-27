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
CPDefinitionDisplayLayoutDisplayContext cpDefinitionDisplayLayoutDisplayContext = (CPDefinitionDisplayLayoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = cpDefinitionDisplayLayoutDisplayContext.getCommerceChannel();
CPDisplayLayout cpDisplayLayout = cpDefinitionDisplayLayoutDisplayContext.getCPDisplayLayout();

List<CPDefinition> cpDefinitionAsList = new ArrayList<>();

if (cpDisplayLayout != null) {
	cpDefinitionAsList = Arrays.asList(cpDisplayLayout.fetchCPDefinition());
}

String layoutBreadcrumb = StringPool.BLANK;

if (cpDisplayLayout != null) {
	Layout selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(cpDisplayLayout.getLayoutUuid(), commerceChannel.getSiteGroupId(), false);

	if (selLayout == null) {
		selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(cpDisplayLayout.getLayoutUuid(), commerceChannel.getSiteGroupId(), true);
	}

	if (selLayout != null) {
		layoutBreadcrumb = cpDefinitionDisplayLayoutDisplayContext.getLayoutBreadcrumb(selLayout);
	}
}
%>

<liferay-util:buffer
	var="removeCPDefinitionIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<commerce-ui:side-panel-content
	title='<%= (cpDisplayLayout == null) ? LanguageUtil.get(request, "add-display-layout") : LanguageUtil.get(request, "edit-display-layout") %>'
>
	<portlet:actionURL name="editProductDisplayLayout" var="editProductDisplayPageActionURL" />

	<aui:form action="<%= editProductDisplayPageActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDisplayLayout == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="classPK" type="hidden" value="<%= (cpDisplayLayout == null) ? 0 : cpDisplayLayout.getClassPK() %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= cpDefinitionDisplayLayoutDisplayContext.getCommerceChannelId() %>" />

		<liferay-ui:error exception="<%= CPDisplayLayoutEntryException.class %>" message="please-select-a-valid-product" />
		<liferay-ui:error exception="<%= CPDisplayLayoutLayoutUuidException.class %>" message="please-select-a-valid-layout" />
		<liferay-ui:error exception="<%= NoSuchCPDefinitionException.class %>" message="please-select-a-valid-product" />

		<aui:model-context bean="<%= cpDisplayLayout %>" model="<%= CPDisplayLayout.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<liferay-ui:search-container
					curParam="cpDefinitionCur"
					headerNames="null,null"
					id="CPDefinitionsSearchContainer"
					iteratorURL="<%= currentURLObj %>"
					total="<%= cpDefinitionAsList.size() %>"
				>
					<liferay-ui:search-container-results
						results="<%= cpDefinitionAsList %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.commerce.product.model.CPDefinition"
						keyProperty="CPDefinitionId"
						modelVar="cpDefinition"
					>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							value="<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>"
						/>

						<liferay-ui:search-container-column-text>
							<a class="float-right modify-link" data-rowId="<%= cpDefinition.getCPDefinitionId() %>" href="javascript:;"><%= removeCPDefinitionIcon %></a>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>

				<aui:button cssClass="mb-4" name="selectProduct" value='<%= LanguageUtil.format(locale, "select-x", "product") %>' />

				<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= (cpDisplayLayout == null) ? StringPool.BLANK : cpDisplayLayout.getLayoutUuid() %>" />

				<aui:field-wrapper helpMessage="product-display-page-help" label="product-display-page">
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

<aui:script use="aui-base,liferay-item-selector-dialog">
	window.document
		.querySelector('#<portlet:namespace />selectProduct')
		.addEventListener('click', function (event) {
			event.preventDefault();

			Liferay.Util.selectEntity({
				dialog: {
					constrain: true,
					modal: true,
				},
				eventName: 'productDefinitionsSelectItem',
				title: '<liferay-ui:message arguments="product" key="select-x" />',
				uri:
					'<%= cpDefinitionDisplayLayoutDisplayContext.getProductItemSelectorUrl() %>',
			});
		});
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />CPDefinitionsSearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			A.one('#<portlet:namespace />classPK').val(0);
		},
		'.modify-link'
	);

	Liferay.on('productDefinitionsSelectItem', function (event) {
		var item = event.data;

		if (item) {
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />CPDefinitionsSearchContainer'
			);

			var link = A.one('[data-rowid=' + searchContainer.getData() + ']');

			if (link !== null) {
				var tr = link.ancestor('tr');

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
			}

			if (!searchContainer.getData().includes(item.id)) {
				var rowColumns = [];

				rowColumns.push(item.name);
				rowColumns.push(
					'<a class="float-right modify-link" data-rowId="' +
						item.id +
						'" href="javascript:;"><%= UnicodeFormatter.toString(removeCPDefinitionIcon) %></a>'
				);

				A.one('#<portlet:namespace />classPK').val(item.id);

				searchContainer.addRow(rowColumns, item.id);

				searchContainer.updateDataStore();
			}
		}
	});
</aui:script>

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
				title: '<liferay-ui:message key="select-product-display-page" />',
				url:
					'<%= cpDefinitionDisplayLayoutDisplayContext.getDisplayPageItemSelectorUrl() %>',
			});

			itemSelectorDialog.open();
		});

	displayPageItemRemove.addEventListener('click', function (event) {
		displayPageNameInput.innerHTML = '<liferay-ui:message key="none" />';

		pagesContainerInput.value = '';

		displayPageItemRemove.classList.add('hide');
	});
</aui:script>