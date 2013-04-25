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

<%@ include file="/html/taglib/ddm/html_field/init.jsp" %>

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= Validator.isNotNull(xsd) %>">
		<%= DDMXSDUtil.getSimpleFieldHTMLByName(pageContext, classNameId, classPK, field, portletResponse.getNamespace(), fieldsNamespace, null, readOnly, requestedLocale) %>

		<aui:input name="<%= fieldsDisplayInputName %>" type="hidden" />

		<aui:script use="liferay-ddm-repeatable-fields">
			new Liferay.DDM.RepeatableFields(
				{
					classNameId: <%= classNameId %>,
					classPK: <%= classPK %>,
					container: '#<%= randomNamespace %>',
					fieldsDisplayInput: '#<portlet:namespace /><%= fieldsDisplayInputName %>',
					namespace: '<%= fieldsNamespace %>',
					portletNamespace: '<portlet:namespace />',
					repeatable: <%= repeatable %>
				}
			);
		</aui:script>
	</c:if>