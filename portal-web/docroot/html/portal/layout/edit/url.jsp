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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<%
String url = StringPool.BLANK;

if (selLayout != null) {
	UnicodeProperties typeSettingsProperties = selLayout.getTypeSettingsProperties();

	url = typeSettingsProperties.getProperty("url", StringPool.BLANK);
}
%>

<aui:input cssClass="lfr-input-text-container" id="url" label="url" name="TypeSettingsProperties--url--" type="text" value="<%= url %>">
	<aui:validator errorMessage="please-enter-a-valid-url" name="required" />
</aui:input>

<aui:script use="liferay-form">
	var form = Liferay.Form.get('<portlet:namespace />addPageFm');

	if (!form) {
		form = Liferay.Form.get('<portlet:namespace />editLayoutFm');
	}

	if (form) {
		var rules = form.formValidator.get('rules');

		var fieldName = '<portlet:namespace />TypeSettingsProperties--url--';

		if (!(fieldName in rules)) {
			rules[fieldName] = {
				custom: false,
				required: true
			};
		}
	}
</aui:script>