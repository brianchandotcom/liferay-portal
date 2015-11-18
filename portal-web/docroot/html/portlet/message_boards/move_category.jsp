<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (category == null) %>"
	title='<%= LanguageUtil.format(pageContext, "move-x", category.getName()) %>'
/>

<portlet:actionURL var="moveCategoryURL">
	<portlet:param name="struts_action" value="/message_boards/move_category" />
</portlet:actionURL>

<aui:form action="<%= moveCategoryURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

	<aui:model-context bean="<%= category %>" model="<%= MBCategory.class %>" />

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

		<div class="control-group">
			<aui:input label="parent-category[message-board]" name="parentCategoryName" type="resource" value="<%= parentCategoryName %>" />

			<aui:button name="selectCategoryButton" value="select" />

			<aui:button disabled="<%= (parentCategoryId <= 0) %>" name="removeCategoryButton" onClick='<%= renderResponse.getNamespace() + "removeCategory();" %>' value="remove" />
		</div>

		<aui:input label="merge-with-parent-category" name="mergeWithParentCategory" type="checkbox" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="move" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />removeCategory() {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = "<%= MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>";

		document.getElementById('<portlet:namespace />parentCategoryName').value = "";

		Liferay.Util.toggleDisabled('#<portlet:namespace />removeCategoryButton', true);
	}
</aui:script>

<%
if (category != null) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move"), currentURL);
}
%>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />selectCategoryButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
					},
					id: '<portlet:namespace />selectCategory',
					title: '<liferay-ui:message arguments="category" key="select-x" />',
					uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/message_boards/select_category" /><portlet:param name="mbCategoryId" value="<%= String.valueOf((category == null) ? MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID : category.getParentCategoryId()) %>" /></portlet:renderURL>'
				},
				function(event) {
					document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = event.categoryid;

					document.getElementById('<portlet:namespace />parentCategoryName').value = A.Lang.String.unescapeEntities(event.name);

					Liferay.Util.toggleDisabled('#<portlet:namespace />removeCategoryButton', false);
				}
			);
		}
	);
</aui:script>