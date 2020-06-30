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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();
long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();
List<CommerceCatalog> commerceCatalogs = cpDefinitionsDisplayContext.getCommerceCatalogs();

String productTypeName = BeanParamUtil.getString(cpDefinition, request, "productTypeName");

String friendlyURLBase = themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL;

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((cpDefinition != null) && (cpDefinition.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<c:if test="<%= (cpDefinition != null) && cpDefinition.isPending() %>">
	<div class="alert alert-info">
		<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
	</div>
</c:if>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionActionURL" />

<aui:form action="<%= editProductDefinitionActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinition == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionId) %>" />
	<aui:input name="productTypeName" type="hidden" value="<%= productTypeName %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<liferay-ui:error exception="<%= CPDefinitionMetaDescriptionException.class %>" message="the-meta-description-is-too-long" />
	<liferay-ui:error exception="<%= CPDefinitionMetaKeywordsException.class %>" message="the-meta-keywords-are-too-long" />
	<liferay-ui:error exception="<%= CPDefinitionMetaTitleException.class %>" message="the-meta-title-is-too-long" />
	<liferay-ui:error exception="<%= CPDefinitionNameDefaultLanguageException.class %>" message="please-enter-the-product-name-for-the-default-language" />
	<liferay-ui:error exception="<%= NoSuchCatalogException.class %>" message="please-select-a-valid-catalog" />

	<liferay-ui:error exception="<%= CPFriendlyURLEntryException.class %>">

		<%
		CPFriendlyURLEntryException cpfuee = (CPFriendlyURLEntryException)errorException;
		%>

		<%@ include file="/error_friendly_url_exception.jspf" %>
	</liferay-ui:error>

	<div class="row">
		<div class="col-8">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:select disabled="<%= cpDefinition != null %>" label="catalog" name="commerceCatalogGroupId" required="<%= true %>" showEmptyOption="<%= true %>">

					<%
					for (CommerceCatalog commerceCatalog : commerceCatalogs) {
					%>

						<aui:option label="<%= commerceCatalog.getName() %>" selected="<%= (cpDefinition == null) ? (commerceCatalogs.size() == 1) : cpDefinitionsDisplayContext.isSelectedCatalog(commerceCatalog) %>" value="<%= commerceCatalog.getGroupId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input autoFocus="<%= true %>" label="name" localized="<%= true %>" name="nameMapAsXML" type="text">
					<aui:validator name="required" />
				</aui:input>

				<aui:input label="short-description" localized="<%= true %>" name="shortDescriptionMapAsXML" resizable="<%= true %>" type="textarea" />

				<%
				String descriptionMapAsXML = StringPool.BLANK;

				if (cpDefinition != null) {
					descriptionMapAsXML = cpDefinition.getDescriptionMapAsXML();
				}
				%>

				<aui:field-wrapper>
					<label class="control-label" for="<portlet:namespace />descriptionMapAsXML"><liferay-ui:message key="full-description" /></label>

					<div class="entry-content form-group">
						<liferay-ui:input-localized
							editorName="alloyeditor"
							name="descriptionMapAsXML"
							type="editor"
							xml="<%= descriptionMapAsXML %>"
						/>
					</div>
				</aui:field-wrapper>
			</commerce-ui:panel>

			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "seo") %>'
			>
				<div class="form-group">
					<label for="<portlet:namespace />urlTitleMapAsXML"><liferay-ui:message key="friendly-url" /><liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>news</em>", false) %>' /></label>

					<liferay-ui:input-localized
						defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
						inputAddon="<%= StringUtil.shorten(friendlyURLBase, 40) %>"
						name="urlTitleMapAsXML"
						xml="<%= HttpUtil.decodeURL(cpDefinitionsDisplayContext.getUrlTitleMapAsXML()) %>"
					/>
				</div>

				<aui:input label="meta-title" localized="<%= true %>" name="metaTitleMapAsXML" type="text" />

				<aui:input label="meta-description" localized="<%= true %>" name="metaDescriptionMapAsXML" type="textarea" />

				<aui:input label="meta-keywords" localized="<%= true %>" name="metaKeywordsMapAsXML" type="textarea" />
			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "categorization") %>'
			>
				<liferay-asset:asset-categories-error />

				<liferay-asset:asset-tags-error />

				<aui:field-wrapper>
					<liferay-asset:asset-categories-selector
						className="<%= CPDefinition.class.getName() %>"
						classPK="<%= cpDefinitionId %>"
						groupIds="<%= new long[] {company.getGroupId()} %>"
					/>
				</aui:field-wrapper>

				<aui:field-wrapper>
					<liferay-asset:asset-tags-selector
						className="<%= CPDefinition.class.getName() %>"
						classPK="<%= cpDefinitionId %>"
						groupIds="<%= new long[] {company.getGroupId()} %>"
					/>
				</aui:field-wrapper>
			</commerce-ui:panel>

			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "schedule") %>'
			>
				<liferay-ui:error exception="<%= CPDefinitionExpirationDateException.class %>" message="please-select-a-valid-expiration-date" />

				<aui:input name="published" />

				<aui:input formName="fm" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
			</commerce-ui:panel>

			<c:if test="<%= cpDefinitionsDisplayContext.hasCustomAttributesAvailable() %>">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "custom-attribute") %>'
				>
					<liferay-expando:custom-attribute-list
						className="<%= CPDefinition.class.getName() %>"
						classPK="<%= (cpDefinition != null) ? cpDefinition.getCPDefinitionId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</commerce-ui:panel>
			</c:if>
		</div>

		<c:if test="<%= cpDefinition != null %>">
			<div class="col-12">
				<div id="item-finder-root"></div>

				<aui:script require="commerce-frontend-js/components/item_finder/entry.es as itemFinder, commerce-frontend-js/utilities/index.es as utilities, commerce-frontend-js/utilities/eventsDefinitions.es as events">
					var headers = new Headers({
						Accept: 'application/json',
						'Content-Type': 'application/json',
						'x-csrf-token': Liferay.authToken
					});

					var id = <%= cpDefinitionsDisplayContext.getCPDefinitionId() %>;
					var productId = <%= cpDefinition.getCProductId() %>;

					function selectItem(specification) {
						return fetch(
							'/o/headless-commerce-admin-catalog/v1.0/products/' +
								id +
								'/productSpecifications/',
							{
								body: JSON.stringify({
									productId: productId,
									specificationId: specification.id,
									specificationKey: specification.key,
									value: {
										[themeDisplay.getLanguageId()]: name
									}
								}),
								credentials: 'include',
								headers: headers,
								method: 'POST'
							}
						).then(function() {
							Liferay.fire(events.UPDATE_DATASET_DISPLAY, {
								id:
									'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>'
							});
							return specification.id;
						});
					}

					function addNewItem(name) {
						return fetch('/o/headless-commerce-admin-catalog/v1.0/specifications', {
							body: JSON.stringify({
								key: utilities.slugify(encodeURIComponent(name)),
								title: {
									[themeDisplay.getLanguageId()]: name
								}
							}),
							credentials: 'include',
							headers: headers,
							method: 'POST'
						})
							.then(function(response) {
								if (response.ok) {
									return response.json();
								}

								return response.json().then(function(data) {
									return Promise.reject(data.message);
								});
							})
							.then(selectItem);
					}

					function getSelectedItems() {
						return fetch(
							'/o/headless-commerce-admin-catalog/v1.0/products/' +
								productId +
								'/productSpecifications/',
							{
								credentials: 'include',
								headers: headers
							}
						)
							.then(function(response) {
								return response.json();
							})
							.then(function(jsonResponse) {
								return jsonResponse.items.map(function(specification) {
									return specification.specificationId;
								});
							});
					}

					itemFinder.default('itemFinder', 'item-finder-root', {
						apiUrl: '/o/headless-commerce-admin-catalog/v1.0/specifications',
						createNewItemLabel:
							'<%= LanguageUtil.get(request, "create-new-specification") %>',
						getSelectedItems: getSelectedItems,
						inputPlaceholder:
							'<%= LanguageUtil.get(request, "find-or-create-a-specification") %>',
						itemsKey: 'id',
						linkedDatasetsId: [
							'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>'
						],
						onItemCreated: addNewItem,
						onItemSelected: selectItem,
						pageSize: 10,
						panelHeaderLabel: '<%= LanguageUtil.get(request, "add-specifications") %>',
						portletId: '<%= portletDisplay.getRootPortletId() %>',
						schema: {
							itemTitle: ['title', themeDisplay.getLanguageId()]
						},
						spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
						titleLabel: '<%= LanguageUtil.get(request, "add-existing-specification") %>'
					});
				</aui:script>
			</div>

			<div class="col-12">
				<commerce-ui:panel
					bodyClasses="p-0"
					title='<%= LanguageUtil.get(request, "specifications") %>'
				>

					<%
					Map<String, String> contextParams = new HashMap<>();

					contextParams.put("cpDefinitionId", String.valueOf(cpDefinitionId));
					%>

					<commerce-ui:dataset-display
						contextParams="<%= contextParams %>"
						dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>"
						formId="fm"
						id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>"
						itemsPerPage="<%= 10 %>"
						namespace="<%= renderResponse.getNamespace() %>"
						pageNumber="<%= 1 %>"
						portletURL="<%= currentURLObj %>"
						showManagementBar="<%= false %>"
					/>
				</commerce-ui:panel>
			</div>
		</c:if>
	</div>
</aui:form>

<c:if test="<%= cpDefinition == null %>">
	<aui:script require="commerce-frontend-js/utilities/index.es as utilities">
		function slugify(string) {
			return string
				.toLowerCase()
				.replace(/[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/|\s|\t]+/g, '-');
		}

		const form = document.getElementById('<portlet:namespace />fm');

		const nameInput = form.querySelector('#<portlet:namespace />nameMapAsXML');
		const urlInput = form.querySelector('#<portlet:namespace />urlTitleMapAsXML');
		const urlTitleInputLocalized = Liferay.component(
			'<portlet:namespace />urlTitleMapAsXML'
		);

		const debounce = utilities.debounce;

		var handleOnNameInput = function() {
			var slug = slugify(nameInput.value);
			urlInput.value = slug;

			urlTitleInputLocalized.updateInputLanguage(slug);
		};

		nameInput.addEventListener('input', debounce(handleOnNameInput, 200));
	</aui:script>
</c:if>