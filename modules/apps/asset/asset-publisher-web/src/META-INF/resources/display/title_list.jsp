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
List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

request.setAttribute("view.jsp-showIconLabel", false);

boolean viewInContext = ((Boolean)request.getAttribute("view.jsp-viewInContext")).booleanValue();

String viewURL = AssetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetEntry, viewInContext);
%>

	<c:if test="<%= assetEntryIndex == 0 %>">
		<ul class="title-list">
	</c:if>

	<li class="title-list <%= assetRendererFactory.getType() %>">
		<liferay-ui:icon
			iconCssClass="<%= assetRenderer.getIconCssClass() %>"
			label="<%= true %>"
			localizeMessage="<%= false %>"
			message="<%= HtmlUtil.escape(title) %>"
			method="get"
			url="<%= viewURL %>"
		/>

		<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />

		<c:if test="<%= metadataFields.length > 0 %>">
			<div class="asset-metadata-toggler" id="<portlet:namespace /><%= assetEntry.getEntryId() %>_toggler">
				<span class="text-primary toggler-header toggler-header-collapsed">

				<%= LanguageUtil.get(request, "more-details") %>

				</span>

				<div class="toggler-content toggler-content-collapsed">
					<liferay-ui:asset-metadata
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
						filterByMetadata="<%= true %>"
						metadataFields="<%= metadataFields %>"
					/>
				</div>
			</div>

			<aui:script use="aui-toggler">
				new A.TogglerDelegate(
					{
						animated: true,
						container: '#<portlet:namespace /><%= assetEntry.getEntryId() %>_toggler',
						content: '.toggler-content',
						expanded: false,
						header: '.toggler-header',
						transition: {
						duration: 0.2,
						easing: 'cubic-bezier(0, 0.1, 0, 1)'
					}
				});
			</aui:script>
		</c:if>
	</li>

	<c:if test="<%= (assetEntryIndex + 1) == results.size() %>">
		</ul>
	</c:if>