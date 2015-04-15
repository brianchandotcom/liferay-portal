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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

Long groupId = layoutsAdminDisplayContext.getGroupId();
long parentPlid = LayoutConstants.DEFAULT_PLID;
long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

if (layout.isTypeControlPanel()) {
	if (layoutsAdminDisplayContext.getSelPlid() != 0) {
		selLayout = LayoutLocalServiceUtil.getLayout(layoutsAdminDisplayContext.getSelPlid());

		parentPlid = selLayout.getPlid();
		parentLayoutId = selLayout.getLayoutId();
	}
}
else {
	selLayout = layout;

	parentPlid = layout.getParentPlid();
	parentLayoutId = layout.getParentLayoutId();
}

String[] types = LayoutTypeControllerTracker.getTypes();
%>

<c:if test="<%= !portletName.equals(PortletKeys.DOCKBAR) %>">
	<portlet:renderURL var="backURL">
		<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set" />
		<portlet:param name="tabs1" value="<%= layoutsAdminDisplayContext.getTabs1() %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</portlet:renderURL>

	<liferay-ui:header
		backURL="<%= backURL %>"
		title="add-new-page"
	/>
</c:if>

<portlet:actionURL var="editLayoutActionURL" windowState="<%= themeDisplay.isStateExclusive() ? LiferayWindowState.EXCLUSIVE.toString() : WindowState.NORMAL.toString() %>">
	<portlet:param name="struts_action" value='<%= portletName.equals(PortletKeys.DOCKBAR) ? "/layouts_admin/add_layout" : "/layouts_admin/edit_layouts" %>' />
</portlet:actionURL>

<portlet:renderURL var="editLayoutRenderURL" windowState="<%= themeDisplay.isStateExclusive() ? LiferayWindowState.EXCLUSIVE.toString() : WindowState.NORMAL.toString() %>">
	<portlet:param name="struts_action" value='<%= portletName.equals(PortletKeys.DOCKBAR) ? "/layouts_admin/add_layout" : "/layouts_admin/edit_layouts" %>' />
	<portlet:param name="tabs1" value="<%= layoutsAdminDisplayContext.getTabs1() %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:renderURL>

<aui:form action="<%= editLayoutActionURL %>" enctype="multipart/form-data" method="post" name="addPageFm" onSubmit="event.preventDefault()">
	<aui:input id="addLayoutCMD" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input id="addLayoutRedirect" name="redirect" type="hidden" value="<%= editLayoutRenderURL %>" />
	<aui:input id="addLayoutGroupId" name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />
	<aui:input id="addLayoutParentPlid" name="parentPlid" type="hidden" value="<%= parentPlid %>" />
	<aui:input id="addLayoutParentLayoutId" name="parentLayoutId" type="hidden" value="<%= parentLayoutId %>" />
	<aui:input id="addLayoutType" name="type" type="hidden" value="portlet" />
	<aui:input id="addLayoutPrototypeId" name="layoutPrototypeId" type="hidden" value="" />
	<aui:input id="addLayoutExplicitCreation" name="explicitCreation" type="hidden" value="<%= true %>" />

	<liferay-ui:error exception="<%= LayoutTypeException.class %>">

		<%
		LayoutTypeException lte = (LayoutTypeException)errorException;

		String type = BeanParamUtil.getString(selLayout, request, "type");
		%>

		<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
			<liferay-ui:message arguments='<%= Validator.isNull(lte.getLayoutType()) ? type : "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
		</c:if>

		<c:if test="<%= lte.getType() == LayoutTypeException.NOT_PARENTABLE %>">
			<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-have-child-pages" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= LayoutNameException.class %>" message="please-enter-a-valid-name" />

	<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

		<%
		RequiredLayoutException rle = (RequiredLayoutException)errorException;
		%>

		<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
			<liferay-ui:message key="you-must-have-at-least-one-page" />
		</c:if>
	</liferay-ui:error>

	<aui:model-context model="<%= Layout.class %>" />

	<aui:fieldset>
		<div class="col-md-12">
			<aui:input id="addLayoutName" name="name" />

			<aui:input id="addLayoutHidden" label="hide-from-navigation-menu" name="hidden" />

			<aui:fieldset cssClass="template-selector" label="type">
				<aui:nav cssClass="list-group" id="templateList">
					<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>
						<aui:nav-item cssClass="lfr-page-template" data-search='<%= HtmlUtil.escape(LanguageUtil.get(request, "empty-page")) %>'>
							<div class="active lfr-page-template-title toggler-header toggler-header-expanded" data-type="portlet">
								<aui:input checked="<%= true %>" id="addLayoutSelectedPageTemplateBlank" label="empty-page" name="selectedPageTemplate" type="radio" />

								<div class="lfr-page-template-description">
									<small><%= LanguageUtil.get(request, "empty-page-description" ) %></small>
								</div>
							</div>

							<div class="lfr-page-template-options toggler-content toggler-content-expanded">

								<%
								String layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
								String layoutTemplateIdPrefix = "addLayout";

								Theme selTheme = layout.getTheme();

								List<LayoutTemplate> layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId());
								%>

								<%@ include file="/html/portlet/layouts_admin/layout/layout_templates_list.jspf" %>
							</div>
						</aui:nav-item>
					</c:if>

					<%
					List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

					for (LayoutPrototype layoutPrototype : layoutPrototypes) {
						String name = HtmlUtil.escape(layoutPrototype.getName(locale));
					%>

						<aui:nav-item cssClass="lfr-page-template" data-search="<%= name %>">
							<div class="lfr-page-template-title toggler-header toggler-header-collapsed" data-prototype-id="<%= layoutPrototype.getLayoutPrototypeId() %>">
								<aui:input id='<%= "addLayoutSelectedPageTemplate" + layoutPrototype.getUuid() %>' label="<%= name %>" name="selectedPageTemplate" type="radio" />

								<div class="lfr-page-template-description">
									<small><%= HtmlUtil.escape(layoutPrototype.getDescription(locale)) %></small>
								</div>
							</div>

							<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
								<aui:input id='<%= "addLayoutLayoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' label="automatically-apply-changes-done-to-the-page-template" name="layoutPrototypeLinkEnabled" type="checkbox" value="<%= PropsValues.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT %>" />
							</div>
						</aui:nav-item>

					<%
					}
					%>

					<%
					liferayPortletRequest.setAttribute(WebKeys.LAYOUT_DESCRIPTIONS, layoutsAdminDisplayContext.getLayoutDescriptions());

					int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(layoutsAdminDisplayContext.getGroup(), false);

					for (String type : types) {
						if (type.equals("portlet")) {
							continue;
						}

						LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);
					%>

						<aui:nav-item cssClass="lfr-page-template" data-search='<%= LanguageUtil.get(request, "layout.types." + type) %>'>
							<div class="lfr-page-template-title toggler-header toggler-header-collapsed" data-type="<%= type %>">
								<aui:input disabled="<%= (layoutsCount == 0) && !layoutTypeController.isFirstPageable() %>" id='<%= "addLayoutSelectedPageTemplate" + type %>' label='<%= "layout.types." + type %>' name="selectedPageTemplate" type="radio" />

								<div class="lfr-page-template-description">
									<small><%= LanguageUtil.get(request, "layout.types." + type + ".description" ) %></small>
								</div>
							</div>

							<div class="lfr-page-template-options toggler-content toggler-content-collapsed">

								<%= layoutTypeController.includeEditContent(request, response, selLayout) %>

							</div>
						</aui:nav-item>

					<%
					}
					%>

					<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>
						<aui:nav-item cssClass="lfr-page-template" data-search="portlet">
							<div class="lfr-page-template-title toggler-header toggler-header-collapsed" data-type="portlet">
								<aui:input id="addLayoutSelectedPageTemplateCopyOfPage" label="copy-of-a-page" name="selectedPageTemplate" type="radio" />

								<div class="lfr-page-template-description">
									<small><%= LanguageUtil.get(request, "copy-of-a-page-description" ) %></small>
								</div>
							</div>

							<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
								<liferay-util:include page="/html/portal/layout/edit/portlet_applications.jsp">
									<liferay-util:param name="copyLayoutIdPrefix" value="addLayout" />
								</liferay-util:include>
							</div>
						</aui:nav-item>
					</c:if>
				</aui:nav>
			</aui:fieldset>
		</div>
	</aui:fieldset>

	<aui:button-row cssClass="lfr-add-page-toolbar">
		<aui:button id="addLayoutSubmit" type="submit" value="add-page" />

		<aui:button id="cancelAddOperation" value="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= portletName.equals(PortletKeys.DOCKBAR) %>">

	<%
	Layout addedLayout = (Layout)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "pageAdded");
	%>

	<c:if test="<%= addedLayout != null && !addedLayout.isHidden() %>">

		<%
		NavItem navItem = new NavItem(request, addedLayout, null);
		%>

		<aui:script>
			Liferay.fire(
				'dockbaraddpage:addPage',
				{
					data: {
						layoutId: <%= addedLayout.getLayoutId() %>,
						parentLayoutId: <%= addedLayout.getParentLayoutId() %>,
						title: '<%= navItem.getName() %>',
						url: '<%= navItem.getURL() %>'
					}
				}
			);
		</aui:script>
	</c:if>
</c:if>

<aui:script use="liferay-dockbar-add-page">
	var addPage = new Liferay.Dockbar.AddPage(
		{
			createPageMessage: '<liferay-ui:message key="loading" />',
			focusItem: A.one('#<portlet:namespace />addLayoutName'),
			namespace: '<portlet:namespace />',
			nodeList: A.one('#<portlet:namespace />templateList'),
			nodeSelector: '.lfr-page-template',
			parentLayoutId: <%= parentLayoutId %>,
			refresh: <%= layout.isTypeControlPanel() %>,
			selected: !A.one('#<portlet:namespace />addPageFm').ancestor().hasClass('hide'),
			toggleOnCancel: <%= portletName.equals(PortletKeys.DOCKBAR) %>
		}
	);

	Liferay.component('<portlet:namespace />addPage', addPage);
</aui:script>