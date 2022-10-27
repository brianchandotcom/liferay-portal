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

<clay:management-toolbar
	managementToolbarDisplayContext="<%= viewKBTemplatesDisplayContext.getManagementToolbarDisplayContext() %>"
	propsTransformer="admin/js/TemplatesManagementToolbarPropsTransformer"
	searchContainerId="kbTemplates"
/>

<clay:container-fluid>
	<aui:form action="<%= viewKBTemplatesDisplayContext.getSearchURL() %>" method="get" name="fm">
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
								"actions", StringUtil.merge(viewKBTemplatesDisplayContext.getAvailableActions(kbTemplate))
							).build());
						%>

						<liferay-ui:search-container-column-user
							showDetails="<%= false %>"
							userId="<%= kbTemplate.getUserId() %>"
						/>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>
							<h2 class="h5">
								<aui:a href="<%= viewKBTemplatesDisplayContext.getEditKBTemplateURL(kbTemplate) %>">
									<%= HtmlUtil.escape(kbTemplate.getTitle()) %>
								</aui:a>
							</h2>

							<span class="text-default">
								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbTemplate.getUserName()), viewKBTemplatesDisplayContext.getKBTemplateModifiedDateDescription(kbTemplate)} %>" key="x-modified-x-ago" />
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= viewKBTemplatesDisplayContext.getKBTemplateDropdownItems(kbTemplate) %>"
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
					actionDropdownItems="<%= viewKBTemplatesDisplayContext.getEmptyStateActionDropdownItems() %>"
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