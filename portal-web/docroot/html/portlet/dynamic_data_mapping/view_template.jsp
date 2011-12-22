<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

String backURL = ParamUtil.getString(request, "backURL");

long structureId = ParamUtil.getLong(request, "structureId");

DDMStructure structure = null;

if (structureId > 0) {
	structure = DDMStructureLocalServiceUtil.getStructure(structureId);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/view_template");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("structureId", String.valueOf(structureId));
%>

<c:if test="<%= (structure != null) %>">
	<liferay-ui:header
		title='<%= LanguageUtil.format(pageContext, (Validator.isNull(templateHeaderTitle) ? "templates-for-structure-x" : templateHeaderTitle), structure.getName(locale), false) %>'
		backURL="<%= backURL %>"
	/>
</c:if>

<liferay-util:include page="/html/portlet/dynamic_data_mapping/template_toolbar.jsp">
	<liferay-util:param name="structureId" value="<%= String.valueOf(structureId) %>" />
	<liferay-util:param name="backURL" value="<%= backURL %>" />
</liferay-util:include>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/template_search.jsp"
	/>
</aui:form>

<div class="separator"></div>

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

		<%
		String rowHREF = null;

		if (Validator.isNotNull(chooseCallback)) {
			StringBundler sb = new StringBundler(7);

			sb.append("javascript:Liferay.Util.getOpener().");
			sb.append(chooseCallback);
			sb.append("('");
			sb.append(template.getTemplateId());
			sb.append("', '");
			sb.append(HtmlUtil.escapeJS(template.getName(locale)));
			sb.append("', Liferay.Util.getWindow());");

			rowHREF = sb.toString();
		}
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="id"
			property="templateId"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="name"
			value="<%= LanguageUtil.get(pageContext, template.getName(locale)) %>"
		/>

		<c:if test="<%= Validator.isNull(templateTypeValue) %>">
			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="type"
				value="<%= LanguageUtil.get(pageContext, template.getType()) %>"
			/>
		</c:if>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="mode"
			value="<%= LanguageUtil.get(pageContext, template.getMode()) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="language"
			value="<%= LanguageUtil.get(pageContext, template.getLanguage()) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			buffer="buffer"
			name="modified-date"
		>

			<%
			buffer.append(dateFormatDateTime.format(template.getModifiedDate()));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/dynamic_data_mapping/template_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>