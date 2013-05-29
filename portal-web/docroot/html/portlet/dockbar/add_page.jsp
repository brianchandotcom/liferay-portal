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

			<!-- <span class="span9"> -->
			<span class="span8">
				<aui:input id="addLayoutName" name="name" placeholder="name" type="text" />
			</span>

			<span class="span3">
				<aui:input id="addLayoutHidden" label="hidden" name="hidden" type="checkbox" />
			</span>

			<span class="span12">
				<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="messageBoardsPanelContainer" persistState="<%= true %>">

					<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" persistState="<%= true %>" title="templates">
						<liferay-util:include page="/html/portlet/dockbar/search_templates.jsp" />

						<aui:nav id="templateList" cssClass="nav-list no-margin-nav-list">

							<aui:nav-item cssClass='lfr-content-item active'
								data-type="blank"
								href=""
							>
								<div>
									<h5>Blank (default)</h5>
									Donec sit amet enim mi, sit amet blandit est. Sed id sapien auctor.
									</br>
									<a href="#">
										<liferay-ui:message key="choose-page-layout" />
									</a>
								</div>
							</aui:nav-item>

							<%
							List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
							for (LayoutPrototype layoutPrototype : layoutPrototypes) {
							%>
								<aui:nav-item cssClass='lfr-content-item'
									href=""
								>
									<div>
										<h5><%= HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId())) %></h5>
										<%= HtmlUtil.escape(layoutPrototype.getDescription()) %>
										</br>
									</div>
								</aui:nav-item>
							<%
							}
							%>

							<%
							for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
								Map<String, Object> data = new HashMap<String, Object>();
								data.put("type", LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]));
							%>
								<aui:nav-item cssClass='lfr-content-item'
									data="<%= data %>"
									href=""
								>
									<div>
										<h5><%= LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]) %></h5>
										Vivamus nec pulvinar lectus. Donec condimentum, augue id congue porttitor, libero enim semper.
										</br>
									</div>
								</aui:nav-item>								
							<%
							}
							%>
						</aui:nav>

						<div id="<portlet:namespace />layoutTypeForm">

							<div class="layout-type-form layout-type-form-blank hide">
								<div>
									<button class="btn back-button" href=""><i class="icon-arrow-left"></i></button>
									<span>BLANK</span>
								</div>
								<%@ include file="/html/portlet/layouts_admin/layout/layout_templates.jspf" %>
							</div>

							<div class="layout-type-form layout-type-form-template hide">
								<div>
									<button class="btn back-button" href=""><i class="icon-arrow-left"></i></button>
									<span>Template</span>
								</div>
								<aui:input label='<%= LanguageUtil.get(pageContext, "automatically-apply-changes-done-to-the-page-template") %>' name="layoutPrototypeLinkEnabled" type="checkbox" />
							</div>

							<%
							for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
								String curLayoutType = PropsValues.LAYOUT_TYPES[i];
							%>

								<div class="layout-type-form layout-type-form-<%= curLayoutType %> hide">
									<div>
										<button class="btn back-button" href=""><i class="icon-arrow-left"></i></button>
										<span><%= curLayoutType %></span>
									</div>
									<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(curLayoutType) %>" />
								</div>

							<%
							}
							%>
						</div>
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

<aui:script use="node-base">
	A.one('#<portlet:namespace />templateList').delegate(
		'click',
		function(event) {
			var templateList = A.one('#<portlet:namespace />templateList');

			templateList.all('.active').removeClass('active');

			event.currentTarget.addClass('active');

			templateList.hide();

			var templateType = event.currentTarget.getData('type');

			if (!templateType) templateType = 'template';

			toggleLayoutTypeFields(templateType.toLowerCase());
		},
		'.lfr-content-item'
	);

	A.one('#<portlet:namespace />layoutTypeForm').delegate(
		'click',
		function(event) {
			event.preventDefault();

			var templateList = A.one('#<portlet:namespace />templateList');
			templateList.show();

			var typeFormContainer = A.one('#<portlet:namespace />layoutTypeForm');

			typeFormContainer.all('.layout-type-form').hide();
		},
		'.back-button'
	);

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