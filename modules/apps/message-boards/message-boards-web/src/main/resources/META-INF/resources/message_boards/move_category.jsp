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

<%@ include file="/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

String headerTitle = LanguageUtil.format(request, "move-x", category.getName(), false);

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<c:if test="<%= !portletTitleBasedNavigation %>">
		<h3><%= LanguageUtil.format(request, "move-x", category.getName(), false) %></h3>
	</c:if>

	<portlet:actionURL name="/message_boards/move_category" var="moveCategoryURL" />

	<aui:form action="<%= moveCategoryURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
		<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

		<aui:model-context bean="<%= category %>" model="<%= MBCategory.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>

				<%
				String parentCategoryName = StringPool.BLANK;

				try {
					MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

					parentCategoryName = parentCategory.getName();
				}
				catch (NoSuchCategoryException nsce) {
				}
				%>

				<div class="form-group">
					<aui:input label="parent-category[message-board]" name="parentCategoryName" type="resource" value="<%= parentCategoryName %>" />

					<aui:button name="selectCategoryButton" value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('parentCategoryId', 'parentCategoryName', this, '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= parentCategoryId <= 0 %>" name="removeCategoryButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</div>

				<aui:input label="merge-with-parent-category" name="mergeWithParentCategory" type="checkbox" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<%
MBBreadcrumbUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "move"), currentURL);
%>

<script>
	var selectCategoryButton = document.getElementById(
		'<portlet:namespace />selectCategoryButton'
	);

	if (selectCategoryButton) {
		selectCategoryButton.addEventListener('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
					},
					id: '<portlet:namespace />selectCategory',
					title:
						'<liferay-ui:message arguments="category" key="select-x" />',

					<portlet:renderURL var="selectCategoryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/select_category" />
						<portlet:param name="mbCategoryId" value="<%= String.valueOf((category == null) ? MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID : category.getParentCategoryId()) %>" />
						<portlet:param name="excludedMBCategoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					uri: '<%= selectCategoryURL %>'
				},
				function(event) {
					var form = document.<portlet:namespace />fm;

					Liferay.Util.setFormValues(form, {
						parentCategoryId: event.categoryid,
						parentCategoryName: Liferay.Util.unescape(event.name)
					});

					var removeCategoryButton = document.getElementById(
						'<portlet:namespace />removeCategoryButton'
					);

					if (removeCategoryButton) {
						Liferay.Util.toggleDisabled(removeCategoryButton, false);
					}
				}
			);
		});
	}
</script>