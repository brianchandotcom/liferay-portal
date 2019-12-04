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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsAdminDisplayContext.getSelLayout(), themeDisplay);
}

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<portlet:actionURL name="/layout/edit_open_graph" var="editOpenGraphURL" />

<aui:form action="<%= editOpenGraphURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="groupId" type="hidden" value="<%= layoutsAdminDisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />

	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2 class="sheet-title"><liferay-ui:message key="open-graph" /></h2>
		</div>

		<div class="sheet-section">
			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="open-graph"
			/>

			<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

			<div class="form-group">
				<label class="control-label"><liferay-ui:message key="image" /></label>

				<aui:input label="<%= StringPool.BLANK %>" name="openGraphImageURL" placeholder="image" readonly="<%= true %>" type="text" value="<%= layoutsAdminDisplayContext.getOpenGraphImageURL() %>" wrapperCssClass="mb-3" />

				<aui:button-row cssClass="mt-0">
					<aui:button name="openGraphImageButton" value="select" />
					<aui:button name="openGraphClearImageButton" value="clear" />
				</aui:button-row>
			</div>

			<%
			LayoutSEOEntry selLayoutSEOEntry = layoutsAdminDisplayContext.getSelLayoutSEOEntry();
			%>

			<c:choose>
				<c:when test="<%= selLayoutSEOEntry != null %>">
					<aui:model-context bean="<%= selLayoutSEOEntry %>" model="<%= LayoutSEOEntry.class %>" />

					<div id="<portlet:namespace />openGraphSettings">
						<aui:input checked="<%= selLayoutSEOEntry.isOpenGraphTitleEnabled() %>" helpMessage="use-custom-open-graph-title-help" label="use-custom-open-graph-title" name="openGraphTitleEnabled" type="checkbox" wrapperCssClass="mb-1" />

						<aui:input disabled="<%= !selLayoutSEOEntry.isOpenGraphTitleEnabled() %>" label="<%= StringPool.BLANK %>" name="openGraphTitle" placeholder="title" />

						<aui:input checked="<%= selLayoutSEOEntry.isOpenGraphDescriptionEnabled() %>" helpMessage="use-custom-open-graph-description-help" label="use-custom-open-graph-description" name="openGraphDescriptionEnabled" type="checkbox" wrapperCssClass="mb-1" />

						<aui:input disabled="<%= !selLayoutSEOEntry.isOpenGraphDescriptionEnabled() %>" label="<%= StringPool.BLANK %>" name="openGraphDescription" placeholder="descripton" />

						<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" />
					</div>
				</c:when>
				<c:otherwise>
					<div id="<portlet:namespace />openGraphSettings">
						<aui:input checked="<%= false %>" helpMessage="use-custom-open-graph-title-help" label="use-custom-open-graph-title" name="openGraphTitleEnabled" type="checkbox" wrapperCssClass="mb-1" />

						<aui:input disabled="<%= true %>" label="<%= StringPool.BLANK %>" localized="<%= true %>" name="openGraphTitle" placeholder="title" type="text" />

						<aui:input checked="<%= false %>" helpMessage="use-custom-open-graph-description-help" label="use-custom-open-graph-description" name="openGraphDescriptionEnabled" type="checkbox" wrapperCssClass="mb-1" />

						<aui:input disabled="<%= true %>" id="openGraphDescription" label="<%= StringPool.BLANK %>" localized="<%= true %>" name="openGraphDescription" placeholder="description" type="textarea" />

						<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" />
					</div>
				</c:otherwise>
			</c:choose>

			<div class="form-group">
				<label><liferay-ui:message key="preview" /></label>

				<div>

					<%
					Map<String, Object> data = HashMapBuilder.<String, Object>put(
						"displayType", "og"
					).put(
						"targets",
						HashMapBuilder.<String, Object>put(
							"description",
							HashMapBuilder.<String, Object>put(
								"customizable", Boolean.TRUE
							).put(
								"defaultValue", selLayout.getDescription(locale)
							).put(
								"id", "openGraphDescription"
							).build()
						).put(
							"imgUrl", Collections.singletonMap("id", "openGraphImageURL")
						).put(
							"title",
							HashMapBuilder.<String, Object>put(
								"customizable", Boolean.TRUE
							).put(
								"defaultValue", layoutsAdminDisplayContext.getPageTitle()
							).put(
								"id", "openGraphTitle"
							).build()
						).put(
							"url",
							Collections.singletonMap("defaultValue", layoutsAdminDisplayContext.getCanonicalLayoutURL())
						).build()
					).put(
						"titleSuffix", layoutsAdminDisplayContext.getPageTitleSuffix()
					).build();
					%>

					<react:component
						data="<%= data %>"
						module="js/seo/PreviewSeo.es"
					/>
				</div>
			</div>
		</div>

		<div class="sheet-footer">
			<aui:button primary="<%= true %>" type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</div>
	</div>
</aui:form>

<portlet:actionURL name="/layout/upload_open_graph_image" var="uploadOpenGraphImageURL" />

<liferay-frontend:component module="js/seo/openGraph.es" context="<%=
	HashMapBuilder.<String, Object>put(
		"uploadOpenGraphImageURL", layoutsAdminDisplayContext.getItemSelectorURL()
	).build()
 %>" />