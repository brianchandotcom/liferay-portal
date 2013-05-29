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

	request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);	
%>

<form>
	<fieldset>
		<div class="row-fluid">

			<!-- <span class="span9"> -->
			<span class="span8">
				<aui:input name="" placeholder="name" type="text" />
			</span>

			<span class="span3">
				<aui:input name="hidden" label="hidden" type="checkbox" />
			</span>

			<span class="span12">
				<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="messageBoardsPanelContainer" persistState="<%= true %>">

					<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" persistState="<%= true %>" title="templates">
						<liferay-util:include page="/html/portlet/dockbar/search_templates.jsp" />

						<aui:nav id="templateList" cssClass="nav-list no-margin-nav-list">

							<aui:nav-item cssClass='lfr-content-item'
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

								<div class="layout-type-form layout-type-form-template hide">
									<aui:input label='<%= LanguageUtil.get(pageContext, "automatically-apply-changes-done-to-the-page-template") %>' name="layoutPrototypeLinkEnabled" type="checkbox" />
								</div>

							<%
							for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
								String curLayoutType = PropsValues.LAYOUT_TYPES[i];
							%>

								<div class="layout-type-form layout-type-form-<%= curLayoutType %> hide">
									<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(curLayoutType) %>" />
								</div>

							<%
							}
							%>
						</div>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<div class="pull-right">
					<button class="btn hide">OK</button>
					<button type="submit" class="btn btn-primary btn-submit">Add Page</button>
					<button class="btn">Cancel</button>
				</div>
			</span>
		</div>
	</fieldset>
</form>

<aui:script use="node-base">
	A.one('#<portlet:namespace />templateList').delegate(
		'click',
		function(event) {
			var templateList = A.one('#<portlet:namespace />templateList');

			templateList.hide();

			var templateType = event.currentTarget.getData('type');

			var templateForm = A.one('.layout-type-form-template');

			if (templateType) {
				templateForm = A.one('.layout-type-form-' + templateType.toLowerCase());
			}

			templateForm.show();
		},
		'.lfr-content-item'
	);
</aui:script>