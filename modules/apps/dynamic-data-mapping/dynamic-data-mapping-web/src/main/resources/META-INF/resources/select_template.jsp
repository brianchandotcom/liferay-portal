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
long templateId = ParamUtil.getLong(request, "templateId");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
long resourceClassNameId = ParamUtil.getLong(request, "resourceClassNameId");
String eventName = ParamUtil.getString(request, "eventName", "selectTemplate");

DDMStructure structure = null;

long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);

if ((classPK > 0) && (structureClassNameId == classNameId)) {
	structure = DDMStructureLocalServiceUtil.getStructure(classPK);
}

String title = ddmDisplay.getViewTemplatesTitle(structure, locale);

PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer templateSearch = new TemplateSearch(renderRequest, portletURL, WorkflowConstants.STATUS_APPROVED);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if ((orderByCol != null) && (orderByType != null)) {
	portalPreferences.setValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", orderByCol);
	portalPreferences.setValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", "id");
	orderByType = portalPreferences.getValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", "asc");
}

OrderByComparator<DDMTemplate> orderByComparator = DDMUtil.getTemplateOrderByComparator(orderByCol, orderByType);
%>

<portlet:actionURL var="selectURL">
	<portlet:param name="mvcPath" value="/select_template.jsp" />
</portlet:actionURL>

<aui:form action="<%= selectURL.toString() %>" method="post" name="selectTemplateFm">
	<aui:input name="templateId" type="hidden" value="<%= String.valueOf(templateId) %>" />
	<aui:input name="classNameId" type="hidden" value="<%= String.valueOf(classNameId) %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="resourceClassNameId" type="hidden" value="<%= String.valueOf(resourceClassNameId) %>" />
	<aui:input name="eventName" type="hidden" value="<%= eventName %>" />

	<c:choose>
		<c:when test="<%= showToolbar %>">

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
			</liferay-util:include>
		</c:when>
		<c:otherwise>
			<liferay-ui:header
				localizeTitle="<%= false %>"
				title="<%= title %>"
			/>
		</c:otherwise>
	</c:choose>

	<div class="container-fluid-1280">
		<liferay-ui:search-container
			orderByCol="<%= orderByCol %>"
			orderByComparator="<%= orderByComparator %>"
			orderByType="<%= orderByType %>"
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
</liferay-util:include>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />selectTemplateFm.<portlet:namespace />keywords);
</aui:script>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectTemplateFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>