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

<liferay-portlet:renderURL varImpl="portletURL">
	<liferay-portlet:param name="keywords" value="<%= assetDisplayTemplateDisplayContext.getKeywords() %>" />
</liferay-portlet:renderURL>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= assetDisplayTemplateDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= assetDisplayTemplateDisplayContext.isDisabledManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="assetDisplayTemplates"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= assetDisplayTemplateDisplayContext.getOrderByCol() %>"
			orderByType="<%= assetDisplayTemplateDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "asset-type"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= assetDisplayTemplateDisplayContext.getDisplayStyle() %>"
		/>

		<c:if test="<%= assetDisplayTemplateDisplayContext.isShowAddButton() %>">
			<liferay-portlet:renderURL varImpl="editAssetDisplayTemplateURL">
				<portlet:param name="mvcPath" value="/edit_asset_display_template.jsp" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>

				<%
				for (long curClassNameId : assetDisplayTemplateDisplayContext.getAvailableClassNameIds()) {
					ClassName className = ClassNameLocalServiceUtil.getClassName(curClassNameId);

					editAssetDisplayTemplateURL.setParameter("classNameId", String.valueOf(curClassNameId));
				%>

					<liferay-frontend:add-menu-item
						title="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>"
						url="<%= editAssetDisplayTemplateURL.toString() %>"
					/>

				<%
				}
				%>

			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedAssetDisplayTemplates"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteAssetDisplayTemplate" var="deleteAssetDisplayTemplateURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteAssetDisplayTemplateURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="assetDisplayTemplates"
		searchContainer="<%= assetDisplayTemplateDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.display.template.model.AssetDisplayTemplate"
			keyProperty="assetDisplayTemplateId"
			modelVar="assetDisplayTemplate"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(assetDisplayTemplateDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="page-template"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = assetDisplayTemplate.getCreateDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(assetDisplayTemplate.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</h6>

						<h5>
							<%= assetDisplayTemplate.getName() %>
						</h5>

						<h6 class="text-default">
							<%= assetDisplayTemplate.getAssetTypeName(locale) %>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/asset_display_template_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetDisplayTemplateDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/asset_display_template_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="page-template"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= assetDisplayTemplate.getName() %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date createDate = assetDisplayTemplate.getCreateDate();

								String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(assetDisplayTemplate.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<%= assetDisplayTemplate.getAssetTypeName(locale) %>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetDisplayTemplateDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= assetDisplayTemplate.getName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="asset-type"
						value="<%= assetDisplayTemplate.getAssetTypeName(locale) %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= assetDisplayTemplate.getCreateDate() %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/asset_display_template_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetDisplayTemplateDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedAssetDisplayTemplates').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>