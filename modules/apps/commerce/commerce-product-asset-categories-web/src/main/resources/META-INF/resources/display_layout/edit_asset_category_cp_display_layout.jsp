<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CategoryCPDisplayLayoutDisplayContext categoryCPDisplayLayoutDisplayContext = (CategoryCPDisplayLayoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDisplayLayout cpDisplayLayout = categoryCPDisplayLayoutDisplayContext.getCPDisplayLayout();

AssetCategory assetCategory = null;

if (cpDisplayLayout != null) {
	assetCategory = categoryCPDisplayLayoutDisplayContext.getAssetCategory(cpDisplayLayout.getClassPK());
}

String layoutBreadcrumb = categoryCPDisplayLayoutDisplayContext.getLayoutBreadcrumb(cpDisplayLayout);
%>

<liferay-frontend:side-panel-content
	title='<%= (cpDisplayLayout == null) ? LanguageUtil.get(request, "add-display-layout") : LanguageUtil.get(request, "edit-display-layout") %>'
>
	<portlet:actionURL name="/commerce_channels/edit_asset_category_cp_display_layout" var="editAssetCategoryCPDisplayLayoutActionURL" />

	<aui:form action="<%= editAssetCategoryCPDisplayLayoutActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDisplayLayout == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDisplayLayoutId" type="hidden" value="<%= (cpDisplayLayout == null) ? 0 : cpDisplayLayout.getCPDisplayLayoutId() %>" />
		<aui:input name="classPK" type="hidden" value="<%= (cpDisplayLayout == null) ? 0 : cpDisplayLayout.getClassPK() %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= categoryCPDisplayLayoutDisplayContext.getCommerceChannelId() %>" />

		<liferay-ui:error exception="<%= CPDisplayLayoutEntryException.class %>" message="please-select-a-valid-category" />
		<liferay-ui:error exception="<%= CPDisplayLayoutEntryUuidException.class %>" message="please-select-a-valid-layout" />

		<aui:model-context bean="<%= cpDisplayLayout %>" model="<%= CPDisplayLayout.class %>" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
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
			</div>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"assetCategory", assetCategory
		).put(
			"categorySelectorUrl", categoryCPDisplayLayoutDisplayContext.getCategorySelectorURL(renderResponse)
		).put(
			"itemSelectorUrl", categoryCPDisplayLayoutDisplayContext.getItemSelectorUrl(renderRequest)
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).put(
			"title", (assetCategory == null) ? null : assetCategory.getTitle(locale)
		).build()
	%>'
	module="js/EditAssetCategoryCPDisplayLayout"
/>