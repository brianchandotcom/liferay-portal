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

String defaultLanguageId = cpDefinitionsDisplayContext.getCatalogDefaultLanguageId();

String productTypeName = BeanParamUtil.getString(cpDefinition, request, "productTypeName");

String friendlyURLBase = themeDisplay.getPortalURL() + cpDefinitionsDisplayContext.getProductURLSeparator();

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

<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionActionURL" />

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
	<liferay-ui:error exception="<%= FriendlyURLLengthException.class %>" message="the-friendly-url-is-too-long" />
	<liferay-ui:error exception="<%= NoSuchCatalogException.class %>" message="please-select-a-valid-catalog" />

	<div class="row">
		<div class="col-8">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<c:choose>
					<c:when test="<%= (cpDefinition != null) || ((cpDefinition == null) && (commerceCatalogs.size() > 1)) %>">
						<aui:select disabled="<%= cpDefinition != null %>" label="catalog" name="commerceCatalogGroupId" required="<%= true %>">

							<%
							for (CommerceCatalog curCommerceCatalog : commerceCatalogs) {
							%>

								<aui:option data-languageId="<%= curCommerceCatalog.getCatalogDefaultLanguageId() %>" label="<%= HtmlUtil.escape(curCommerceCatalog.getName()) %>" selected="<%= (cpDefinition == null) ? (commerceCatalogs.size() == 1) : cpDefinitionsDisplayContext.isSelectedCatalog(curCommerceCatalog) %>" value="<%= curCommerceCatalog.getGroupId() %>" />

							<%
							}
							%>

						</aui:select>
					</c:when>
					<c:otherwise>

						<%
						CommerceCatalog commerceCatalog = commerceCatalogs.get(0);
						%>

						<aui:input name="commerceCatalogGroupId" type="hidden" value="<%= commerceCatalog.getGroupId() %>" />
					</c:otherwise>
				</c:choose>

				<aui:input autoFocus="<%= true %>" defaultLanguageId="<%= defaultLanguageId %>" label="name" localized="<%= true %>" name="nameMapAsXML" type="text">
					<aui:validator name="required" />
				</aui:input>

				<aui:input defaultLanguageId="<%= defaultLanguageId %>" label="short-description" localized="<%= true %>" name="shortDescriptionMapAsXML" resizable="<%= true %>" type="textarea" />

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
							defaultLanguageId="<%= defaultLanguageId %>"
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
						defaultLanguageId="<%= defaultLanguageId %>"
						inputAddon="<%= StringUtil.shorten(friendlyURLBase, 40) %>"
						name="urlTitleMapAsXML"
						xml="<%= HttpUtil.decodeURL(cpDefinitionsDisplayContext.getUrlTitleMapAsXML()) %>"
					/>
				</div>

				<aui:input defaultLanguageId="<%= defaultLanguageId %>" label="meta-title" localized="<%= true %>" name="metaTitleMapAsXML" type="text" />

				<aui:input defaultLanguageId="<%= defaultLanguageId %>" label="meta-description" localized="<%= true %>" name="metaDescriptionMapAsXML" type="textarea" />

				<aui:input defaultLanguageId="<%= defaultLanguageId %>" label="meta-keywords" localized="<%= true %>" name="metaKeywordsMapAsXML" type="textarea" />
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
						visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
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

				<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/index as utilities">
					var headers = utilities.fetchParams.headers;
					var id = <%= cpDefinitionsDisplayContext.getCPDefinitionId() %>;
					var productId = <%= cpDefinition.getCProductId() %>;

					function selectItem(specification) {
						return Liferay.Util.fetch(
							'/o/headless-commerce-admin-catalog/v1.0/products/' +
								id +
								'/productSpecifications/',
							{
								body: JSON.stringify(
									Object.assign(
										{
											productId: productId,
											specificationId: specification.id,
											specificationKey: specification.key,
											value: {},
										},
										specification.optionCategory
											? {
													optionCategoryId:
														specification.optionCategory.id,
											  }
											: {}
									)
								),
								headers: headers,
								method: 'POST',
							}
						).then(() => {
							Liferay.fire(events.UPDATE_DATASET_DISPLAY, {
								id:
									'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>',
							});
							return null;
						});
					}

					function addNewItem(name) {
						var nameDefinition = {};

						nameDefinition[themeDisplay.getLanguageId()] = name;

						if (themeDisplay.getLanguageId() !== themeDisplay.getDefaultLanguageId()) {
							nameDefinition[themeDisplay.getDefaultLanguageId()] = name;
						}

						return Liferay.Util.fetch(
							'/o/headless-commerce-admin-catalog/v1.0/specifications',
							{
								body: JSON.stringify({
									key: slugify.default(name),
									title: nameDefinition,
								}),
								headers: headers,
								method: 'POST',
							}
						)
							.then((response) => {
								if (response.ok) {
									return response.json();
								}

								return response.json().then((data) => {
									return Promise.reject(data.errorDescription);
								});
							})
							.then(selectItem);
					}

					function getSelectedItems() {
						return Promise.resolve([]);
					}

					itemFinder.default('itemFinder', 'item-finder-root', {
						apiUrl: '/o/headless-commerce-admin-catalog/v1.0/specifications',
						createNewItemLabel:
							'<%= LanguageUtil.get(request, "create-new-specification") %>',
						getSelectedItems: getSelectedItems,
						inputPlaceholder:
							'<%= LanguageUtil.get(request, "find-or-create-a-specification") %>',
						itemSelectedMessage:
							'<%= LanguageUtil.get(request, "specification-selected") %>',
						itemsKey: 'id',
						linkedDatasetsId: [
							'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>',
						],
						multiSelectableEntries: true,
						itemsKey: 'id',
						onItemCreated: addNewItem,
						onItemSelected: selectItem,
						pageSize: 10,
						panelHeaderLabel: '<%= LanguageUtil.get(request, "add-specifications") %>',
						portletId: '<%= portletDisplay.getRootPortletId() %>',
						schema: [
							{
								fieldName: ['title', 'LANG'],
							},
							{
								fieldName: 'key',
							},
						],
						spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
						titleLabel:
							'<%= LanguageUtil.get(request, "add-existing-specification") %>',
					});
				</aui:script>
			</div>

			<div class="col-12">
				<commerce-ui:panel
					bodyClasses="p-0"
					title='<%= LanguageUtil.get(request, "specifications") %>'
				>
					<clay:data-set-display
						contextParams='<%=
							HashMapBuilder.<String, String>put(
								"cpDefinitionId", String.valueOf(cpDefinitionId)
							).build()
						%>'
						dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>"
						formId="fm"
						id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS %>"
						itemsPerPage="<%= 10 %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
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
	<aui:script require="commerce-frontend-js/utilities/debounce as debounce, commerce-frontend-js/utilities/slugify as slugify">
		var form = document.getElementById('<portlet:namespace />fm');

		var nameInput = form.querySelector('#<portlet:namespace />nameMapAsXML');
		var urlInput = form.querySelector('#<portlet:namespace />urlTitleMapAsXML');
		var urlTitleInputLocalized = Liferay.component(
			'<portlet:namespace />urlTitleMapAsXML'
		);

		var handleOnNameInput = function () {
			var slug = slugify.default(nameInput.value);
			urlInput.value = slug;

			urlTitleInputLocalized.updateInputLanguage(slug);
		};

		nameInput.addEventListener('input', debounce.default(handleOnNameInput, 200));
	</aui:script>

	<aui:script>
		document
			.getElementById('<portlet:namespace />commerceCatalogGroupId')
			.addEventListener('change', (event) => {
				var languageId = event.target.querySelector(
					'[value="' + event.target.value + '"]'
				).dataset.languageid;

				var nameInput = document.getElementById(
					'<portlet:namespace />nameMapAsXML'
				);
				var shortDescriptionInput = document.getElementById(
					'<portlet:namespace />shortDescriptionMapAsXML'
				);
				var descriptionInput =
					window.<portlet:namespace />descriptionMapAsXMLEditor;
				var urlInput = document.getElementById(
					'<portlet:namespace />urlTitleMapAsXML'
				);
				var metaTitleInput = document.getElementById(
					'<portlet:namespace />metaTitleMapAsXML'
				);
				var metaDescriptionInput = document.getElementById(
					'<portlet:namespace />metaDescriptionMapAsXML'
				);
				var metaKeywordsInput = document.getElementById(
					'<portlet:namespace />metaKeywordsMapAsXML'
				);

				var nameInputLocalized = Liferay.component(
					'<portlet:namespace />nameMapAsXML'
				);
				var shortDescriptionInputLocalized = Liferay.component(
					'<portlet:namespace />shortDescriptionMapAsXML'
				);
				var descriptionInputLocalized = Liferay.component(
					'<portlet:namespace />descriptionMapAsXML'
				);
				var urlTitleInputLocalized = Liferay.component(
					'<portlet:namespace />urlTitleMapAsXML'
				);
				var metaTitleInputLocalized = Liferay.component(
					'<portlet:namespace />metaTitleMapAsXML'
				);
				var metaDescriptionInputLocalized = Liferay.component(
					'<portlet:namespace />metaDescriptionMapAsXML'
				);
				var metaKeywordsInputLocalized = Liferay.component(
					'<portlet:namespace />metaKeywordsMapAsXML'
				);

				nameInputLocalized.updateInputLanguage(nameInput.value, languageId);
				shortDescriptionInputLocalized.updateInputLanguage(
					shortDescriptionInput.value,
					languageId
				);
				descriptionInputLocalized.updateInputLanguage(
					descriptionInput.getHTML(),
					languageId
				);
				urlTitleInputLocalized.updateInputLanguage(urlInput.value, languageId);
				metaTitleInputLocalized.updateInputLanguage(
					metaTitleInput.value,
					languageId
				);
				metaDescriptionInputLocalized.updateInputLanguage(
					metaDescriptionInput.value,
					languageId
				);
				metaKeywordsInputLocalized.updateInputLanguage(
					metaKeywordsInput.value,
					languageId
				);

				nameInputLocalized.selectFlag(languageId, false);
				shortDescriptionInputLocalized.selectFlag(languageId, false);
				descriptionInputLocalized.selectFlag(languageId, false);
				urlTitleInputLocalized.selectFlag(languageId, false);
				metaTitleInputLocalized.selectFlag(languageId, false);
				metaDescriptionInputLocalized.selectFlag(languageId, false);
				metaKeywordsInputLocalized.selectFlag(languageId, false);
			});
	</aui:script>
</c:if>