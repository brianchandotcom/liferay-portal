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

<%@ include file="/init.jsp" %>

<%
JournalSelectDDMTemplateDisplayContext journalSelectDDMTemplateDisplayContext = new JournalSelectDDMTemplateDisplayContext(renderRequest, renderResponse);
%>

<clay:management-toolbar
	displayContext="<%= new JournalSelectDDMTemplateManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalSelectDDMTemplateDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280" method="post" name="selectDDMTemplateFm">
	<liferay-ui:search-container
		searchContainer="<%= journalSelectDDMTemplateDisplayContext.getTemplateSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
			keyProperty="templateId"
			modelVar="ddmTemplate"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(journalSelectDDMTemplateDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new JournalSelectDDMTemplateVerticalCard(ddmTemplate, renderRequest, journalSelectDDMTemplateDisplayContext) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
					>
						<c:choose>
							<c:when test="<%= ddmTemplate.getTemplateId() != journalSelectDDMTemplateDisplayContext.getDDMTemplateId() %>">

								<%
								Map<String, Object> data = new HashMap<>();

								data.put("ddmtemplateid", ddmTemplate.getTemplateId());
								data.put("ddmtemplatekey", ddmTemplate.getTemplateKey());
								data.put("description", ddmTemplate.getDescription(locale));
								data.put("imageurl", ddmTemplate.getTemplateImageURL(themeDisplay));
								data.put("name", ddmTemplate.getName(locale));
								%>

								<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
									<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>
								</aui:a>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
						value="<%= HtmlUtil.escape(ddmTemplate.getDescription(locale)) %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= ddmTemplate.getModifiedDate() %>"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= journalSelectDDMTemplateDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectDDMTemplateFm',
		'<%= HtmlUtil.escapeJS(journalSelectDDMTemplateDisplayContext.getEventName()) %>'
	);
</aui:script>