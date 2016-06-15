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

<%@ include file="/admin/init.jsp" %>

<%
String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/view_templates.jsp");
portletURL.setParameter("orderBycol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "templates"));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewTemplatesURL">
			<portlet:param name="mvcPath" value="/admin/view_templates.jsp" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewTemplatesURL %>"
			label="templates"
			selected="<%= true %>"
		/>
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcPath" value="/admin/view_templates.jsp" />
		</liferay-portlet:renderURL>

		<aui:form action="<%= searchURL %>" method="get" name="fm2">
			<liferay-portlet:renderURLParams varImpl="searchURL" />

			<aui:nav-bar-search>
				<liferay-ui:input-search markupView="lexicon" />
			</aui:nav-bar-search>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<%
String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="kbTemplates"
>
	<c:if test="<%= Validator.isNull(keywords) %>">

		<%
		PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);
		%>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"descriptive"} %>'
				portletURL="<%= displayStyleURL %>"
				selectedDisplayStyle="descriptive"
			/>
		</liferay-frontend:management-bar-buttons>

		<%
		PortletURL navigationPortletURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);
		%>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= navigationPortletURL %>"
			/>

			<%
			PortletURL sortURL = renderResponse.createRenderURL();

			sortURL.setParameter("mvcPath", "/admin/view_templates.jsp");
			%>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"title", "user-name", "create-date", "modified-date"} %>'
				portletURL="<%= sortURL %>"
			/>
		</liferay-frontend:management-bar-filters>
	</c:if>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteKBTemplates();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcPath" value="/admin/view_templates.jsp" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="kbTemplateIds" type="hidden" />

		<aui:fieldset>
			<liferay-portlet:renderURL varImpl="iteratorURL">
				<portlet:param name="mvcPath" value="/admin/view_templates.jsp" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container
				id="kbTemplates"
				rowChecker="<%= AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.DELETE_KB_TEMPLATES) ? new RowChecker(renderResponse) : null %>"
				searchContainer="<%= new KBTemplateSearch(renderRequest, iteratorURL) %>"
			>
				<%@ include file="/admin/template_search_results.jspf" %>

				<liferay-ui:search-container-row
					className="com.liferay.knowledge.base.model.KBTemplate"
					escapedModel="<%= true %>"
					keyProperty="kbTemplateId"
					modelVar="kbTemplate"
				>
					<liferay-ui:search-container-column-user
						cssClass="user-icon-lg"
						showDetails="<%= false %>"
						userId="<%= kbTemplate.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text colspan="<%= 2 %>">

						<%
						Date modifiedDate = kbTemplate.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<h5 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {kbTemplate.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</h5>

						<liferay-portlet:renderURL var="editURL">
							<portlet:param name="mvcPath" value='<%= templatePath + "edit_template.jsp" %>' />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="kbTemplateId" value="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>" />
						</liferay-portlet:renderURL>

						<h4>
							<aui:a href="<%= editURL.toString() %>">
								<%= kbTemplate.getTitle() %>
							</aui:a>
						</h4>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/admin/template_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
			</liferay-ui:search-container>
		</aui:fieldset>
	</aui:form>
</div>

<c:if test="<%= AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_TEMPLATE) %>">
	<liferay-portlet:renderURL var="addKBTemplateURL">
		<portlet:param name="mvcPath" value='<%= templatePath + "edit_template.jsp" %>' />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-template") %>' url="<%= addKBTemplateURL %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteKBTemplates() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-templates") %>')) {
			document.<portlet:namespace />fm.method = 'post';
			document.<portlet:namespace />fm.<portlet:namespace />kbTemplateIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, '<liferay-portlet:actionURL name="deleteKBTemplates"><portlet:param name="mvcPath" value="/admin/view_templates.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:actionURL>');
		}
	}
</aui:script>