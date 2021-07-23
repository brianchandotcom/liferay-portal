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

AssetCategory assetCategory = null;

if (cpDisplayLayout != null) {
	assetCategory = categoryCPDisplayLayoutDisplayContext.getAssetCategory(cpDisplayLayout.getClassPK());
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
			"locale", locale
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
	module="js/EditAssetCategoryCPDisplayLayout"
/>