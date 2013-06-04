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

<%
int columnsCount = 2;

Group group = layout.getGroup();

long groupId = group.getGroupId();

long layoutId = layout.getLayoutId();

boolean privateLayout = layout.isPrivateLayout();

String layoutTemplateId = StringPool.BLANK;

String rootNodeName = LanguageUtil.get(pageContext, null);

LayoutLister layoutLister = new LayoutLister();

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

Theme selTheme = layout.getTheme();

List<LayoutTemplate> layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId());

request.setAttribute("add_page.jsp-embedded", true);
%>

<aui:model-context model="<%= Layout.class %>" />

<portlet:actionURL var="editPageURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
</portlet:actionURL>

<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
</portlet:renderURL>

<aui:form action="<%= editPageURL %>" enctype="multipart/form-data" method="post" name="addPageFm" onSubmit="event.preventDefault()">
	<aui:input id="cmd" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input id="explicitCreation" name="explicitCreation" type="hidden" value="<%= true %>" />
	<aui:input id="groupId" name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input id="layoutPrototypeId" name="layoutPrototypeId" type="hidden" value="" />
	<aui:input id="privateLayout" name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input id="parentPlid" name="parentPlid" type="hidden" value="<%= layout.getParentPlid() %>" />
	<aui:input id="parentLayoutId" name="parentLayoutId" type="hidden" value="<%= layout.getParentLayoutId() %>" />
	<aui:input id="redirect" name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input id="type" name="type" type="hidden" value="portlet" />

	<div class="row-fluid">

		<div class="span12">
			<div>
				<aui:input id="addLayoutName" name="name" />
				<aui:input id="addLayoutHidden" label="show-in-navigation" name="hidden" type="checkbox" />
			</div>

			<h5 class="category-header">Templates</h5>

			<liferay-util:include page="/html/portlet/dockbar/search_templates.jsp" />

			<aui:nav cssClass="nav-list no-margin-nav-list" id="templateList">
				<aui:nav-item cssClass="lfr-page-template" data-search="blank">
					<div class="active toggler-header toggler-header-collapsed" data-type="portlet">
						<label class="radio lfr-page-template-title">
							<input checked id="Blank" name="selectedPageTemplate" type="radio">
							<strong><%= LanguageUtil.get(pageContext, "blank-default") %></strong>
						</label>
					</div>

					<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
						<%@ include file="/html/portlet/layouts_admin/layout/layout_templates.jspf" %>
					</div>
				</aui:nav-item>

				<%
				List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
				for (LayoutPrototype layoutPrototype : layoutPrototypes) {

					String name = HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId()));
				%>

					<aui:nav-item cssClass="lfr-page-template" data-search="<%= name %>">
						<div class="toggler-header toggler-header-collapsed" data-prototype-id="<%= layoutPrototype.getLayoutPrototypeId() %>">
							<label class="radio lfr-page-template-title">
								<input id="<%= name %>" name="selectedPageTemplate" type="radio">
								<strong><%= name %></strong>
							</label>
							<%= HtmlUtil.escape(layoutPrototype.getDescription()) %>
						</div>

						<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
							<aui:input label='<%= LanguageUtil.get(pageContext, "automatically-apply-changes-done-to-the-page-template") %>' name="layoutPrototypeLinkEnabled" type="checkbox" />
						</div>
					</aui:nav-item>

				<%
				}
				%>

				<%
				for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
					String layoutType = LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]);

					if (layoutType.equals("Portlet")) {
						continue;
					}
				%>
					<aui:nav-item cssClass="lfr-page-template" data-search="<%= layoutType %>">
						<div class="toggler-header toggler-header-collapsed" data-type="<%= PropsValues.LAYOUT_TYPES[i] %>">
							<label class="radio lfr-page-template-title">
								<input id="<%= layoutType %>" name="selectedPageTemplate" type="radio">
								<strong><%= layoutType %></strong>
							</label>
						</div>

						<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
							<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(PropsValues.LAYOUT_TYPES[i]) %>" />
						</div>
					</aui:nav-item>

				<%
				}
				%>

				<aui:nav-item cssClass="lfr-page-template" data-search="portlet">
					<div class="toggler-header toggler-header-collapsed" data-type="portlet">
						<label class="radio lfr-page-template-title">
							<input id="pageTemplateCopy" name="selectedPageTemplate" type="radio">
							<strong><%= LanguageUtil.get(pageContext, "copy-of-a-page") %></strong>
						</label>
					</div>

					<div class="lfr-page-template-options toggler-content toggler-content-collapsed">
						<liferay-util:include page="/html/portal/layout/edit/portlet.jsp" />
					</div>
				</aui:nav-item>

			</aui:nav>
		</div>
	<div>

	<div class="lfr-add-page-toolbar">
		<div>
			<div class="pull-right">
				<aui:button type="submit" value="add-page" />
				<aui:button name="cancelAddOperation" value="cancel" />
			</div>
		</div>
	</div>
</aui:form>

<%
Layout addedLayout = (Layout)SessionMessages.get(renderRequest, portletDisplay.getId() + "PAGE_ADDED");
%>

<c:if test="<%= addedLayout != null && !addedLayout.isHidden() %>">
	<aui:script use="aui-base">
		var TPL_TAB_LINK = '<li class="lfr-nav-item lfr-nav-deletable lfr-nav-sortable lfr-nav-updateable yui3-dd-drop" aria-selected="true"> <a class="" href="{url}" tabindex="-1"><span> {pageTitle} </span> </a> </li>';

		var tabHtml = A.Lang.sub(
			TPL_TAB_LINK,
			{
				pageTitle: A.Lang.String.escapeHTML('<%= addedLayout.getName(locale) %>'),
				url: '<%= addedLayout.getFriendlyURL() %>'
			}
		);

		A.one('#banner .nav').append(tabHtml);
	</aui:script>
</c:if>

<aui:script use="aui-toggler-delegate,liferay-dockbar-add-page">
	new Liferay.Dockbar.AddPage(
		{
			inputNode: A.one('#<portlet:namespace />searchTemplates'),
			namespace: '<portlet:namespace />',
			nodeList: A.one('#<portlet:namespace />templateList'),
			nodeSelector: '.lfr-page-template'
		}
	);

	A.one('#<portlet:namespace />addLayoutName').focus();
</aui:script>