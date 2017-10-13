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

<%@ include file="/permissions/init.jsp" %>

<%
String inputTitle = StringPool.BLANK;
String inputDesc = StringPool.BLANK;

if (action.equals("publish")) {
	inputTitle = "publish-permissions";
}
else if (action.equals("export")) {
	inputTitle = "export-permissions";
}
else {
	inputTitle = "import-permissions";
}

if (global) {
	inputDesc = "publish-global-permissions-help";
}
else {
	inputDesc = "export-import-permissions-help";
}

String inputLabel = "<span style='font-weight: bold;'>" + LanguageUtil.get(request, inputTitle) + ":</span> " + LanguageUtil.get(request, inputDesc);
%>

<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="permissions" markupView="lexicon">
	<span class="<%= labelCSSClass %>">
		<aui:input disabled="<%= disableInputs %>" name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="checkbox" label="<%= inputLabel %>"
			value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PERMISSIONS, false) %>"
		/>
	</span>
</aui:fieldset>