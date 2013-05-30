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

<%@ page import="com.liferay.portal.plugin.PluginUtil" %>
<%@ page import="com.liferay.portal.util.LayoutLister" %>
<%@ page import="com.liferay.portal.util.LayoutView" %>

<%
Group group = null;

if (layout != null) {
	group = layout.getGroup();
}

LayoutLister layoutLister = new LayoutLister();

String pagesName = null;

long groupId = group.getGroupId();

boolean privateLayout = layout.isPrivateLayout();

String rootNodeName = LanguageUtil.get(pageContext, pagesName);

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

long layoutId = layout.getLayoutId();

String layoutTemplateId = StringPool.BLANK;

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

Theme selTheme = layout.getTheme();

List<LayoutTemplate> layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId());
%>

<aui:model-context model="<%= Layout.class %>" />

<portlet:actionURL var="editPageURL">
	<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
</portlet:actionURL>

<aui:form action="<%= editPageURL %>" enctype="multipart/form-data" method="post">
	<aui:input id="addLayoutCmd" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input id="addLayoutExplicitCreation" name="explicitCreation" type="hidden" value="<%= true %>" />
	<aui:input id="addLayoutGroupId" name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input id="addLayoutPrivateLayoutId" name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input id="addLayoutParentPlid" name="parentPlid" type="hidden" value="<%= plid %>" />
	<aui:input id="addLayoutParentLayoutId" name="parentLayoutId" type="hidden" value="<%= layoutId %>" />

	<fieldset>
		<div class="row-fluid">

			<span class="span9">
				<aui:input id="addLayoutName" name="name" type="text" />
			</span>

			<span class="span3">
				<aui:input id="addLayoutHidden" label="hidden" name="hidden" type="checkbox" />
			</span>

			<span class="span12">
				<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="addPagePanelContainer">
					<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" title="templates">
						<liferay-util:include page="/html/portlet/dockbar/search_templates.jsp" />

						<aui:nav id="templateList" cssClass="nav-list no-margin-nav-list">
							<aui:nav-item cssClass="lfr-content-item lfr-page-template"
								data-type="blank" data-search="blank"
							>
								<div class="toggler-header">
									<h5>Blank (default)</h5>
									Donec sit amet enim mi, sit amet blandit est. Sed id sapien auctor.
								</div>

								<div class="layout-type-form layout-type-form-blank toggler-content">
									<%@ include file="/html/portlet/layouts_admin/layout/layout_templates.jspf" %>
								</div>

								<a href="#">
									<liferay-ui:message key="choose-page-layout" />
								</a>
							</aui:nav-item>

							<%
							List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
							for (LayoutPrototype layoutPrototype : layoutPrototypes) {

								String name = HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId()));
							%>
								<aui:nav-item cssClass="lfr-content-item lfr-page-template"
									href="" data-type="" data-search="<%= name %>"
								>
									<div class="toggler-header">
										<h5><%= name %></h5>
										<%= HtmlUtil.escape(layoutPrototype.getDescription()) %>

									</div>

									<div class="toggler-content">
										<p>
											Lorem ipsum Ut commodo dolore dolor ex irure ex cupidatat amet enim officia pariatur in dolore sunt.
										</p>
										<p>
											Lorem ipsum Veniam id minim ut id adipisicing dolore deserunt ex pariatur consectetur ad in reprehenderit aute qui culpa Excepteur eiusmod nostrud.
										</p>
									</div>
								</aui:nav-item>
							<%
							}
							%>

							<%
							for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
								Map<String, Object> data = new HashMap<String, Object>();
								data.put("type", LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]));

								String layoutType = LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]);
							%>
								<aui:nav-item cssClass='lfr-content-item lfr-page-template'
									data="<%= data %>" data-search="<%= layoutType %>"
									href=""
								>
									<div class="toggler-header">
										<h5><%= layoutType %></h5>
										Vivamus nec pulvinar lectus. Donec condimentum, augue id congue porttitor, libero enim semper.
									</div>

									<div class="layout-type-form layout-type-form-<%= layoutType %> toggler-content">
										<div id="<portlet:namespace />layoutTypeForm">
											<div class="layout-type-form layout-type-form-template hide">
												<aui:input label='<%= LanguageUtil.get(pageContext, "automatically-apply-changes-done-to-the-page-template") %>' name="layoutPrototypeLinkEnabled" type="checkbox" />
											</div>
										</div>

										<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(PropsValues.LAYOUT_TYPES[i]) %>" />
									</div>
								</aui:nav-item>
							<%
							}
							%>
						</aui:nav>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</span>
		</div>
	</fieldset>

	<div class="pull-right">
		<button class="btn hide">OK</button>
		<button class="btn btn-primary btn-submit" type="submit">Add Page</button>
		<button class="btn" id="<portlet:namespace />cancelAction">Cancel</button>
	</div>
</aui:form>

<aui:script use="aui-toggler-delegate">
	new A.TogglerDelegate(
		{
			animated: true,
			closeAllOnExpand: true,
			container: '#addPagePanelContainer',
			content: '.toggler-content-test',
			expanded: false,
			header: '.toggler-header-test',
			transition: {
				duration: 0.3
			}
		}
	);
</aui:script>

<aui:script use="node-base">
	A.one('#<portlet:namespace />cancelAction').on(
		'click',
		function(event) {
			var Dockbar = Liferay.Dockbar;

			Dockbar.loadPanel();

			event.preventDefault();
		}
	);

	function toggleLayoutTypeFields(type) {
		var currentType = 'layout-type-form-' + type;

		var typeFormContainer = A.one('#<portlet:namespace />layoutTypeForm');

		typeFormContainer.all('.layout-type-form').each(
			function(item, index, collection) {
				var active = item.hasClass(currentType);

				var disabled = !active;

				item.toggle(active);

				item.all('input, select, textarea').set('disabled', disabled);
			}
		);
	}

	toggleLayoutTypeFields();
</aui:script>