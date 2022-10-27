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
ViewKBTemplatesDisplayContext viewKBTemplatesDisplayContext = (ViewKBTemplatesDisplayContext)request.getAttribute(ViewKBTemplatesDisplayContext.class.getName());

KBTemplatesManagementToolbarDisplayContext kbTemplatesManagementToolbarDisplayContext = viewKBTemplatesDisplayContext.getManagementToolbarDisplayContext();
%>

<c:choose>
	<c:when test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-156421")) %>'>
		<liferay-util:include page="/admin/common/vertical_menu.jsp" servletContext="<%= application %>" />

		<div class="knowledge-base-admin-content">
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<liferay-portlet:actionURL name="/knowledge_base/delete_kb_templates" var="deleteKBTemplatesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:actionURL>

<clay:management-toolbar
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteKBTemplatesURL", deleteKBTemplatesURL.toString()
		).build()
	%>'
	managementToolbarDisplayContext="<%= kbTemplatesManagementToolbarDisplayContext %>"
	propsTransformer="admin/js/TemplatesManagementToolbarPropsTransformer"
	searchContainerId="kbTemplates"
/>

<clay:container-fluid>
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcRenderCommandName" value="/knowledge_base/view_kb_templates" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="kbTemplateIds" type="hidden" />

		<c:choose>
			<c:when test="<%= viewKBTemplatesDisplayContext.hasKBTemplates() %>">
				<liferay-ui:search-container
					id="kbTemplates"
					searchContainer="<%= viewKBTemplatesDisplayContext.getSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.knowledge.base.model.KBTemplate"
						keyProperty="kbTemplateId"
						modelVar="kbTemplate"
					>

						<%
						row.setData(
							HashMapBuilder.<String, Object>put(
								"actions", StringUtil.merge(kbTemplatesManagementToolbarDisplayContext.getAvailableActions(kbTemplate))
							).build());
						%>

						<liferay-ui:search-container-column-user
							showDetails="<%= false %>"
							userId="<%= kbTemplate.getUserId() %>"
						/>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>

							<%
							Date modifiedDate = kbTemplate.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
							%>

							<liferay-portlet:renderURL var="editURL">
								<portlet:param name="mvcPath" value="/admin/common/edit_kb_template.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="kbTemplateId" value="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>" />
							</liferay-portlet:renderURL>

							<h2 class="h5">
								<aui:a href="<%= editURL.toString() %>">
									<%= HtmlUtil.escape(kbTemplate.getTitle()) %>
								</aui:a>
							</h2>

							<span class="text-default">
								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbTemplate.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>

							<%
							KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
							%>

							<clay:dropdown-actions
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= kbDropdownItemsProvider.getKBTemplateDropdownItems(kbTemplate) %>"
								propsTransformer="admin/js/KBDropdownPropsTransformer"
							/>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="descriptive"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</c:when>
			<c:otherwise>
				<liferay-frontend:empty-result-message
					actionDropdownItems="<%= kbTemplatesManagementToolbarDisplayContext.getEmptyStateActionDropdownItems() %>"
					animationType="<%= EmptyResultMessageKeys.AnimationType.EMPTY %>"
					buttonCssClass="secondary"
					title='<%= LanguageUtil.get(request, "there-are-no-article-templates") %>'
				/>
			</c:otherwise>
		</c:choose>
	</aui:form>
</clay:container-fluid>

<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-156421")) %>'>
	</div>
</c:if>