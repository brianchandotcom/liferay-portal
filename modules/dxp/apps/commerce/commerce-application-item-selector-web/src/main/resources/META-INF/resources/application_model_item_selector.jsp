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
CommerceApplicationModelItemSelectorViewDisplayContext commerceApplicationModelItemSelectorViewDisplayContext = (CommerceApplicationModelItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commerceApplicationModelItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = commerceApplicationModelItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceApplicationModels"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceApplicationModelItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceApplicationModelItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceApplicationModelSelectorWrapper">
	<liferay-ui:search-container
		id="commerceApplicationModels"
		searchContainer="<%= commerceApplicationModelItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.application.model.CommerceApplicationModel"
			cssClass="commerce-application-model-row"
			keyProperty="commerceApplicationModelId"
			modelVar="commerceApplicationModel"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"commerce-application-model-id", commerceApplicationModel.getCommerceApplicationModelId()
				).put(
					"name", commerceApplicationModel.getName()
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="id"
				property="commerceApplicationModelId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceApplicationModelSelectorWrapper = A.one(
		'#<portlet:namespace />commerceApplicationModelSelectorWrapper'
	);

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />commerceApplicationModels'
	);

	searchContainer.on('rowToggled', function (event) {
		var allSelectedElements = event.elements.allSelectedElements;
		var arr = [];

		allSelectedElements.each(function () {
			var row = this.ancestor('tr');

			var data = row.getDOM().dataset;

			arr.push({
				commerceApplicationModelId: data.commerceApplicationModelId,
				name: data.name,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: arr,
			}
		);
	});
</aui:script>