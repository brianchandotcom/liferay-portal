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

<%@ include file="/shared_assets/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectAssetType");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/shared_assets/select_asset_type");
portletURL.setParameter("eventName", eventName);

SharedAssetsViewDisplayContext
	sharedAssetsViewDisplayContext = (SharedAssetsViewDisplayContext)renderRequest.getAttribute(SharedAssetsViewDisplayContext.class.getName());
%>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectAssetTypeFm">
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= sharedAssetsViewDisplayContext.getSharedAssetsFilterItems() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.sharing.filter.SharedAssetsFilterItem"
			cssClass="select-action"
			modelVar="sharedAssetsFilterItem"
		>

			<%
			String className = ParamUtil.getString(request, "className");

			if (className.equals(sharedAssetsFilterItem.getClassName())) {
				row.setCssClass("select-action active");
			}

			Map<String, Object> rowData = new HashMap<String, Object>();

			rowData.put("className", sharedAssetsFilterItem.getClassName());

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-icon
				icon="edit-layout"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5><%= HtmlUtil.escape(sharedAssetsFilterItem.getLabel(locale)) %></h5>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />selectAssetTypeFm');

	form.delegate(
		'click',
		function(event) {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			A.all('.select-action').removeClass('active');

			currentTarget.addClass('active');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(eventName) %>',
				{
					data: currentTarget.attr('data-className')
				}
			);
		},
		'.select-action'
	);
</aui:script>