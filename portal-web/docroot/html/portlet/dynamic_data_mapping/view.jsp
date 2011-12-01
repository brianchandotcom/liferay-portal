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
String tabs1 = ParamUtil.getString(request, "tabs1", "structures");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/view");
portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:error exception="<%= RequiredStructureException.class %>" message="required-structures-could-not-be-deleted" />

<c:if test="<%= showToolbar %>">
	<liferay-util:include page="/html/portlet/dynamic_data_mapping/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value="view-all" />
	</liferay-util:include>
</c:if>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/structure_search.jsp"
	/>
</aui:form>

<div class="separator"></div>

<liferay-ui:search-container
	searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-container-results>
		<%@ include file="/html/portlet/dynamic_data_mapping/structure_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
		keyProperty="structureId"
		modelVar="structure"
	>

		<%
		String rowHREF = null;

		if (Validator.isNotNull(chooseCallback)) {
			StringBundler sb = new StringBundler(7);

			sb.append("javascript:Liferay.Util.getOpener().");
			sb.append(chooseCallback);
			sb.append("('");
			sb.append(structure.getStructureId());
			sb.append("', '");
			sb.append(HtmlUtil.escape(structure.getName(locale)));
			sb.append("', Liferay.Util.getWindow());");

			rowHREF = sb.toString();
		}
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="id"
			property="structureId"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="name"
			value="<%= structure.getName(locale) %>"
		/>

		<c:if test="<%= Validator.isNull(storageTypeValue) %>">
			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="storage-type"
				value="<%= LanguageUtil.get(pageContext, structure.getStorageType()) %>"
			/>
		</c:if>

		<c:if test="<%= classNameId == 0 %>">
			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				buffer="buffer"
				name="type"
			>

				<%
				buffer.append(ResourceActionsUtil.getModelResource(locale, structure.getClassName()));
				%>

			</liferay-ui:search-container-column-text>
		</c:if>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			buffer="buffer"
			name="modified-date"
		>

			<%
			buffer.append(dateFormatDateTime.format(structure.getModifiedDate()));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/dynamic_data_mapping/structure_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script>
	function <portlet:namespace />copyStructure(uri) {
		Liferay.Util.openWindow(
			{
				dialog: {
					centered: true,
					constrain: true,
					width: 600
				},
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.get(pageContext, "copy-structure") %>',
				uri: uri
			}
		);
	}
</aui:script>