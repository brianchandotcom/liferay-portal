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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<aui:model-context model="<%= Layout.class %>" />

<portlet:actionURL var="editLayoutActionURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
</portlet:actionURL>

<portlet:renderURL var="editLayoutRenderURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
</portlet:renderURL>

<aui:form action="<%= editLayoutActionURL %>" enctype="multipart/form-data" method="post" name="addPageFm" onSubmit="event.preventDefault()">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= editLayoutRenderURL.toString() %>" />
	<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layout.isPrivateLayout() %>" />
	<aui:input name="parentPlid" type="hidden" value="<%= layout.getParentPlid() %>" />
	<aui:input name="parentLayoutId" type="hidden" value="<%= layout.getParentLayoutId() %>" />
	<aui:input name="type" type="hidden" value="portlet" />
	<aui:input name="layoutPrototypeId" type="hidden" value="" />
	<aui:input name="explicitCreation" type="hidden" value="<%= true %>" />

	<aui:fieldset>
		<div class="row-fluid">
			<div class="span12">
				<aui:input name="name" />

				<aui:input label="hide-from-navigation-menu" name="hidden" />

				<aui:fieldset cssClass="template-selector" label="templates">
					<div class="search-panel btn-toolbar">
						<aui:input cssClass="search-query span12" label="" name="searchTemplates" placeholder="search" type="text"  />
					</div>

					<aui:nav cssClass="nav-list no-margin-nav-list" id="templateList">
						<aui:nav-item cssClass="lfr-page-template" data-search="blank">
							<div class="active lfr-page-template-title toggler-header toggler-header-collapsed" data-type="portlet">
								<aui:input checked="<%= true %>" id="blank" label="blank" name="selectedPageTemplate" type="radio" />
							</div>

							<div class="lfr-page-template-options toggler-content toggler-content-collapsed">

								<%
								String layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;

								Theme selTheme = layout.getTheme();

								List<LayoutTemplate> layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId());

								int columnsCount = 2;
								%>

								<%@ include file="/html/portlet/layouts_admin/layout/layout_templates.jspf" %>
							</div>
						</aui:nav-item>

						<%
						List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

						for (LayoutPrototype layoutPrototype : layoutPrototypes) {
							String name = HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId()));
						%>

							<aui:nav-item cssClass="lfr-page-template" data-search="<%= name %>">
								<div class="lfr-page-template-title toggler-header toggler-header-collapsed" data-prototype-id="<%= layoutPrototype.getLayoutPrototypeId() %>">
									<aui:input label="<%= name %>" name="selectedPageTemplate" type="radio" />

									<%= HtmlUtil.escape(layoutPrototype.getDescription()) %>
								</div>

								<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
									<aui:input label="automatically-apply-changes-done-to-the-page-template" name="layoutPrototypeLinkEnabled" type="checkbox" />
								</div>
							</aui:nav-item>

						<%
						}
						%>

						<%
						LayoutLister layoutLister = new LayoutLister();

						LayoutView layoutView = layoutLister.getLayoutView(scopeGroupId, layout.isPrivateLayout(), StringPool.BLANK, locale);

						request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutView.getList());

						for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
							if (PropsValues.LAYOUT_TYPES[i].equals("portlet")) {
								continue;
							}
						%>

							<aui:nav-item cssClass="lfr-page-template" data-search='<%= LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]) %>'>
								<div class="lfr-page-template-title toggler-header toggler-header-collapsed" data-type="<%= PropsValues.LAYOUT_TYPES[i] %>">
									<aui:input label='<%= "layout.types." + PropsValues.LAYOUT_TYPES[i] %>' name="selectedPageTemplate" type="radio" />
								</div>

								<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
									<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(PropsValues.LAYOUT_TYPES[i]) %>" />
								</div>
							</aui:nav-item>

						<%
						}
						%>

						<c:if test='<%= ArrayUtil.contains(PropsValues.LAYOUT_TYPES, "portlet") %>'>
							<aui:nav-item cssClass="lfr-page-template" data-search="portlet">
								<div class="lfr-page-template-title toggler-header toggler-header-collapsed" data-type="portlet">
									<aui:input label="copy-of-a-page" name="selectedPageTemplate" type="radio" />
								</div>

								<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
									<liferay-util:include page="/html/portal/layout/edit/portlet.jsp" />
								</div>
							</aui:nav-item>
						</c:if>
					</aui:nav>
				</aui:fieldset>
			</div>
		<div>
	</aui:fieldset>

	<aui:button-row cssClass="lfr-add-page-toolbar">
		<div class="pull-right">
			<aui:button type="submit" value="add-page" />

			<aui:button name="cancelAddOperation" value="cancel" />
		</div>
	</aui:button-row>
</aui:form>

<%
Layout addedLayout = (Layout)SessionMessages.get(renderRequest, portletDisplay.getId() + "pageAdded");
%>

<c:if test="<%= addedLayout != null && !addedLayout.isHidden() %>">

	<%
	NavItem navItem = new NavItem(request, addedLayout, null);
	%>

	<aui:script use="aui-base">
		Liferay.fire('dockbaraddpage:addPage',
			{
				data: {
					layoutId: <%= addedLayout.getLayoutId() %>,
					parentLayoutId: <%= addedLayout.getParentLayoutId() %>,
					title: A.Lang.String.escapeHTML('<%= navItem.getName() %>'),
					url: '<%= navItem.getURL() %>'
				}
			}
		);
	</aui:script>
</c:if>

<aui:script use="liferay-dockbar-add-page">
	new Liferay.Dockbar.AddPage(
		{
			inputNode: A.one('#<portlet:namespace />searchTemplates'),
			namespace: '<portlet:namespace />',
			nodeList: A.one('#<portlet:namespace />templateList'),
			nodeSelector: '.lfr-page-template',
			parentLayoutId: <%= layout.getParentLayoutId() %>
		}
	);

	A.one('#<portlet:namespace />name').focus();
</aui:script>