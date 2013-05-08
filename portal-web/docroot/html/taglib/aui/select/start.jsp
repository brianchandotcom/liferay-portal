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

<%@ include file="/html/taglib/aui/select/init.jsp" %>

<div class="<%= controlGroupCss %>">
	<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") %>'>
		<label <%= AUIUtil.buildLabel("select", inlineField, true, namespace + id) %>>
			<liferay-ui:message key="<%= label %>" />

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>

			<c:if test="<%= changesContext %>">
				<span class="hide-accessible">(<liferay-ui:message key="changing-the-value-of-this-field-will-reload-the-page" />)</span>
			</c:if>
		</label>
	</c:if>

	<c:if test="<%= Validator.isNotNull(prefix) %>">
		<span class="prefix">
			<liferay-ui:message key="<%= prefix %>" />
		</span>
	</c:if>

	<select class="<%= fieldCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= namespace + id %>" <%= multiple ? "multiple" : StringPool.BLANK %> name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<c:if test="<%= showEmptyOption %>">
			<aui:option />
		</c:if>

		<c:if test="<%= Validator.isNotNull(listType) %>">

			<%
			int listTypeId = ParamUtil.getInteger(request, name, BeanParamUtil.getInteger(bean, request, listTypeFieldName));

			List<ListType> listTypeModels = ListTypeServiceUtil.getListTypes(listType);

			for (ListType listTypeModel : listTypeModels) {
			%>

				<aui:option selected="<%= listTypeId == listTypeModel.getListTypeId() %>" value="<%= listTypeModel.getListTypeId() %>"><liferay-ui:message key="<%= listTypeModel.getName() %>" /></aui:option>

			<%
			}
			%>

		</c:if>