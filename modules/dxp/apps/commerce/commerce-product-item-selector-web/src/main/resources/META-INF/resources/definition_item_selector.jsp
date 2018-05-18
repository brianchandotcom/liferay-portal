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
CPDefinitionItemSelectorViewDisplayContext cpDefinitionItemSelectorViewDisplayContext = (CPDefinitionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpDefinitionSearchContainer = cpDefinitionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpDefinitionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpDefinitionItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpDefinitionItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpDefinitions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title", "modified-date", "display-date"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(portletURL) %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpDefinitionSelectorWrapper">
	<liferay-ui:search-container
		id="cpDefinitions"
		searchContainer="<%= cpDefinitionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPDefinition"
			cssClass="commerce-product-definition-row"
			keyProperty="CPDefinitionId"
			modelVar="cpDefinition"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("cp-definition-id", cpDefinition.getCPDefinitionId());
			data.put("name", cpDefinition.getName(themeDisplay.getLanguageId()));

			row.setData(data);

			CPType cpType = cpDefinitionItemSelectorViewDisplayContext.getCPType(cpDefinition.getProductTypeName());

			String thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
			%>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
					<liferay-ui:search-container-column-image
						name="image"
						src="<%= thumbnailSrc %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-icon
						icon="documents-and-media"
						name="image"
					/>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<div class="commerce-product-definition-title" data-id="<%= cpDefinition.getCPDefinitionId() %>">
					<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="type"
			>
				<%= cpType.getLabel(locale) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="sku"
				value="<%= cpDefinitionItemSelectorViewDisplayContext.getSku(cpDefinition, locale) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-content"
				name="status"
				status="<%= cpDefinition.getStatus() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
			searchContainer="<%= cpDefinitionSearchContainer %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= cpDefinitionSearchContainer %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpDefinitionSelectorWrapper = A.one("#<portlet:namespace />cpDefinitionSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpDefinitions');

	searchContainer.on(
		'rowToggled',
		function(event) {
			var allSelectedElements = event.elements.allSelectedElements
			var arr = [];

			allSelectedElements.each(
				function() {
					var row = this.ancestor('tr');

					var data = row.getDOM().dataset;

					arr.push({cpDefinitionId : data.cpDefinitionId, name : data.name});
				}
			);

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: arr
				}
			);
		}
	);
</aui:script>