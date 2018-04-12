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
AssetCategory category = (AssetCategory)request.getAttribute("assetCategory");
String itemSelectorURL = (String)request.getAttribute("itemSelectorURL");
String layoutBreadcrumb = (String)request.getAttribute("layoutBreadcrumb");
String layoutUuid = (String)request.getAttribute("layoutUuid");
String titleMapAsXML = (String)request.getAttribute("titleMapAsXML");
long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

String friendlyURLBase = themeDisplay.getPortalURL() + CPConstants.SEPARATOR_ASSET_CATEGORY_URL;

PortletURL categoryRedirectURL = renderResponse.createRenderURL();

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId");

categoryRedirectURL.setParameter("mvcPath", "/view_categories.jsp");

if (parentCategoryId > 0) {
	categoryRedirectURL.setParameter("categoryId", String.valueOf(parentCategoryId));
}

if (vocabularyId > 0) {
	categoryRedirectURL.setParameter("vocabularyId", String.valueOf(vocabularyId));
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(categoryRedirectURL.toString());

renderResponse.setTitle(category.getTitle(locale));
%>

<portlet:actionURL name="editAssetCategoryFriendlyURL" var="editCategoryURL">
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="categoryId" type="hidden" value="<%= category.getCategoryId() %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<div class="commerce-product-definition-url-title form-group">
				<label for="<portlet:namespace />friendlyURL"><liferay-ui:message key="friendly-url" /> <liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>/news</em>", false) %>' /></label>

				<div class="input-group lfr-friendly-url-input-group">
					<span class="input-group-addon" id="<portlet:namespace />urlBase">
						<span class="input-group-constrain"><liferay-ui:message key="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>" /></span>
					</span>

					<liferay-ui:input-localized
						cssClass="form-control"
						defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
						name="urlTitleMapAsXML"
						xml="<%= HttpUtil.decodeURL(titleMapAsXML) %>"
					/>
				</div>
			</div>

			<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= layoutUuid %>" />

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

				<aui:button name="chooseDisplayPage" value="choose" />
			</aui:field-wrapper>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var displayPageItemContainer = $('#<portlet:namespace />displayPageItemContainer');
	var displayPageItemRemove = $('#<portlet:namespace />displayPageItemRemove');
	var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');
	var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');

	$('#<portlet:namespace />chooseDisplayPage').on(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'selectDisplayPage',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								pagesContainerInput.val(selectedItem.id);

								displayPageNameInput.html(selectedItem.name);

								displayPageItemRemove.removeClass('hide');
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-product-display-page" />',
					url: '<%= itemSelectorURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	displayPageItemRemove.on(
		'click',
		function(event) {
			displayPageNameInput.html('<liferay-ui:message key="none" />');

			pagesContainerInput.val('');

			displayPageItemRemove.addClass('hide');
		}
	);
</aui:script>