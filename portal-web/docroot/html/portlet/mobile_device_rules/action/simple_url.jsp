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

<%@ include file="/html/portlet/mobile_device_rules/action/init.jsp" %>

<%
String url = GetterUtil.getString(typeSettingsProperties.get("url"));
boolean ajax = GetterUtil.getBoolean(request.getParameter("ajax"));
%>

<aui:input cssClass="lfr-input-text-container" name="url" type="text" value="<%= url %>">
	<aui:validator name="required" />
</aui:input>

<c:if test="<%= ajax %>">
	<aui:script use="liferay-form">
		var form = Liferay.Form.get('<portlet:namespace />fm');

		if (form) {
			var rules = form.formValidator.get('rules');

			var fieldName = '<portlet:namespace />url';

			if (!(fieldName in rules)) {
				rules[fieldName] = {
					custom: false,
					required: true
				};
			}
		}
	</aui:script>
</c:if>