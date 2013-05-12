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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");

DDMStructure structure = null;

long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);

if ((classPK > 0) && (structureClassNameId == classNameId)) {
	structure = DDMStructureServiceUtil.getStructure(classPK);
}

String title = ddmDisplay.getViewTemplatesTitle(structure, locale);
%>

<c:if test="<%= showToolbar %>">
	<liferay-util:include page="/html/portlet/dynamic_data_mapping/template_toolbar.jsp">
		<liferay-util:param name="redirect" value="<%= currentURL %>" />
		<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
		<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	</liferay-util:include>
</c:if>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/select_template" />
	<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectTemplateFm">
	<c:if test="<%= !showToolbar %>">
		<liferay-ui:header
			localizeTitle="<%= false %>"
			title="<%= title %>"
		/>
	</c:if>

	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/template_search.jsp"
	/>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-container
		searchContainer="<%= new TemplateSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_mapping/template_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate"
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

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(template.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="modified-date"
				value="<%= dateFormatDateTime.format(template.getModifiedDate()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= template.getTemplateId() != classPK %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("ddmtemplateid", template.getTemplateId());
					data.put("ddmtemplatekey", template.getTemplateKey());
					data.put("name", HtmlUtil.escapeAttribute(template.getName(locale)));
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />selectTemplateFm.<portlet:namespace />toggle_id_ddm_template_searchkeywords);
</aui:script>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />selectTemplateFm').delegate(
		'click',
		function(event) {
			var result = Util.getAttributes(event.currentTarget, 'data-');

			Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

			Util.getWindow().hide();
		},
		'.selector-button'
	);
</aui:script>