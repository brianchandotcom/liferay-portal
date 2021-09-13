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

String layoutBreadcrumb = StringPool.BLANK;

Layout selLayout = cpDefinitionDisplayLayoutDisplayContext.getDefaultProductLayout();

if (selLayout != null) {
	layoutBreadcrumb = cpDefinitionDisplayLayoutDisplayContext.getLayoutBreadcrumb(selLayout);
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

<commerce-ui:panel
	elementClasses="flex-fill"
	title='<%= LanguageUtil.get(request, "default-product-display-page") %>'
>
	<portlet:actionURL name="/commerce_channels/edit_cp_definition_cp_display_layout" var="editCPDefinitionCPDisplayLayoutActionURL" />

	<aui:form action="<%= editCPDefinitionCPDisplayLayoutActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="setDefaultLayout" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= cpDefinitionDisplayLayoutDisplayContext.getCommerceChannelId() %>" />
		<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.getUuid() %>" />

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
	</aui:form>
</commerce-ui:panel>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"displayPageItemSelectorUrl", cpDefinitionDisplayLayoutDisplayContext.getDisplayPageItemSelectorUrl()
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).put(
			"removeIcon", removeCPDefinitionIcon
		).build()
	%>'
	module="js/EditDisplayLayout"
/>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "override-default-product-display-page") %>'
>
	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(cpDefinitionDisplayLayoutDisplayContext.getCommerceChannelId())
			).build()
		%>'
		creationMenu="<%= cpDefinitionDisplayLayoutDisplayContext.getCreationMenu() %>"
		dataProviderKey="<%= CommerceProductDisplayPageClayTable.NAME %>"
		id="<%= CommerceProductDisplayPageClayTable.NAME %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= currentURLObj %>"
	/>
</commerce-ui:panel>