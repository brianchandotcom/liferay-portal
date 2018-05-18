<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			<div class="form-group">
				<label for="<portlet:namespace />urlTitleMapAsXML"><liferay-ui:message key="friendly-url" /><liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>news</em>", false) %>' /></label>

				<liferay-ui:input-localized
					defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
					inputAddon="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>"
					name="urlTitleMapAsXML"
					xml="<%= HttpUtil.decodeURL(cpDefinitionsDisplayContext.getUrlTitleMapAsXML()) %>"
				/>
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