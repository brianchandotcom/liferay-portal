<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid container-fluid-max-xl container-form-lg\"" : StringPool.BLANK %>>
	<c:if test="<%= !portletTitleBasedNavigation %>">
		<h3><%= LanguageUtil.format(request, "move-x", category.getName(), false) %></h3>
	</c:if>

	<portlet:actionURL name="/message_boards/move_category" var="moveCategoryURL" />

	<aui:form action="<%= moveCategoryURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
		<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

		<aui:model-context bean="<%= category %>" model="<%= MBCategory.class %>" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
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
						String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('parentCategoryId', 'parentCategoryName', this, '" + liferayPortletResponse.getNamespace() + "');";
						%>

						<aui:button disabled="<%= parentCategoryId <= 0 %>" name="removeCategoryButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
					</div>

					<aui:input label="merge-with-parent-category" name="mergeWithParentCategory" type="checkbox" />
				</aui:fieldset>

				<div class="sheet-footer">
					<aui:button type="submit" value="move" />

					<aui:button href="<%= redirect %>" type="cancel" />
				</div>
			</div>
		</div>
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
		selectCategoryButton.addEventListener('click', (event) => {
			Liferay.Util.openSelectionModal({
				onSelect: function (event) {
					var form = document.<portlet:namespace />fm;

					Liferay.Util.setFormValues(form, {
						parentCategoryId: event.categoryid,
						parentCategoryName: Liferay.Util.unescape(event.name),
					});

					var removeCategoryButton = document.getElementById(
						'<portlet:namespace />removeCategoryButton'
					);

					if (removeCategoryButton) {
						Liferay.Util.toggleDisabled(removeCategoryButton, false);
					}
				},
				selectEventName: '<portlet:namespace />selectCategory',
				title: '<liferay-ui:message arguments="category" key="select-x" />',

				<portlet:renderURL var="selectCategoryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/select_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf((category == null) ? MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID : category.getParentCategoryId()) %>" />
					<portlet:param name="excludedMBCategoryId" value="<%= String.valueOf(categoryId) %>" />
				</portlet:renderURL>

				url: '<%= selectCategoryURL %>',
			});
		});
	}
</script>