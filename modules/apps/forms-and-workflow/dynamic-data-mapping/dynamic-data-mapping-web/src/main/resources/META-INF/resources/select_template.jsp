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
String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

long templateId = ParamUtil.getLong(request, "templateId");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
long resourceClassNameId = ParamUtil.getLong(request, "resourceClassNameId");
String eventName = ParamUtil.getString(request, "eventName", "selectTemplate");

String mode = ParamUtil.getString(request, "mode", DDMTemplateConstants.TEMPLATE_MODE_CREATE);

DDMStructure structure = null;

long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);

if ((classPK > 0) && (structureClassNameId == classNameId)) {
	structure = DDMStructureLocalServiceUtil.getStructure(classPK);
}

String title = ddmDisplay.getViewTemplatesTitle(structure, locale);

PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer templateSearch = new TemplateSearch(renderRequest, portletURL, WorkflowConstants.STATUS_APPROVED);

OrderByComparator<DDMTemplate> orderByComparator = DDMUtil.getTemplateOrderByComparator(ddmDisplayContext.getOrderByCol(), ddmDisplayContext.getOrderByType());

templateSearch.setOrderByCol(ddmDisplayContext.getOrderByCol());
templateSearch.setOrderByComparator(orderByComparator);
templateSearch.setOrderByType(ddmDisplayContext.getOrderByType());
%>

<portlet:actionURL var="selectURL">
	<portlet:param name="mvcPath" value="/select_template.jsp" />
</portlet:actionURL>

<liferay-util:include page="/template_search_bar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="mvcPath" value="/select_template.jsp" />
	<liferay-util:param name="tabs1" value="<%= tabs1 %>" />
	<liferay-util:param name="templateId" value="<%= String.valueOf(templateId) %>" />
	<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<liferay-util:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
	<liferay-util:param name="eventName" value="<%= eventName %>" />
</liferay-util:include>

<aui:form action="<%= selectURL.toString() %>" method="post" name="selectTemplateFm">
	<aui:input name="templateId" type="hidden" value="<%= String.valueOf(templateId) %>" />
	<aui:input name="classNameId" type="hidden" value="<%= String.valueOf(classNameId) %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="resourceClassNameId" type="hidden" value="<%= String.valueOf(resourceClassNameId) %>" />
	<aui:input name="eventName" type="hidden" value="<%= eventName %>" />

	<%
	request.setAttribute(WebKeys.SEARCH_CONTAINER, templateSearch);
	%>

	<liferay-util:include page="/template_toolbar.jsp" servletContext="<%= application %>">
		<liferay-util:param name="mvcPath" value="/select_template.jsp" />
		<liferay-util:param name="redirect" value="<%= currentURL %>" />
		<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
		<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
		<liferay-util:param name="eventName" value="<%= eventName %>" />
		<liferay-util:param name="includeCheckBox" value="<%= Boolean.FALSE.toString() %>" />
		<liferay-util:param name="orderByCol" value="<%= ddmDisplayContext.getOrderByCol() %>" />
		<liferay-util:param name="orderByType" value="<%= ddmDisplayContext.getOrderByType() %>" />
	</liferay-util:include>

	<div class="container-fluid-1280">
		<liferay-ui:search-container
			searchContainer="<%= templateSearch %>"
		>
			<liferay-ui:search-container-results>
				<%@ include file="/template_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
				keyProperty="templateId"
				modelVar="template"
			>

				<liferay-ui:search-container-column-text
					name="id"
					value="<%= String.valueOf(template.getTemplateId()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="name"
					value="<%= HtmlUtil.escape(template.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-jsp
					name="description"
					path="/template_description.jsp"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= template.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text>
					<c:if test="<%= template.getTemplateId() != templateId %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						if (ddmDisplay.isShowConfirmSelectTemplate()) {
							data.put("confirm-selection", Boolean.TRUE.toString());
							data.put("confirm-selection-message", ddmDisplay.getConfirmSelectTemplateMessage(locale));
						}

						data.put("ddmtemplateid", template.getTemplateId());
						data.put("ddmtemplatekey", template.getTemplateKey());
						data.put("description", template.getDescription(locale));
						data.put("imageurl", template.getTemplateImageURL(themeDisplay));
						data.put("name", template.getName(locale));
						%>

						<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
					</c:if>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</div>
</aui:form>

<liferay-util:include page="/template_add_buttons.jsp" servletContext="<%= application %>">
	<liferay-util:param name="redirect" value="<%= currentURL %>" />
	<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<liferay-util:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
	<liferay-util:param name="mode" value="<%= mode %>" />
</liferay-util:include>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />searchForm.<portlet:namespace />keywords);
</aui:script>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectTemplateFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>