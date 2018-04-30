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
EditConfigurationDisplayContext editConfigurationDisplayContext = (EditConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String name = ParamUtil.getString(request, "name");
String[] values = ParamUtil.getStringValues(request, "values");

String label = ParamUtil.getString(request, "label", name);

String[] chosenValues = null;

JSONObject commerceCloudOrderForecastConfigurationJSONObject = editConfigurationDisplayContext.getCommerceCloudOrderForecastConfiguration();

if (commerceCloudOrderForecastConfigurationJSONObject != null) {
	chosenValues = ArrayUtil.toStringArray(commerceCloudOrderForecastConfigurationJSONObject.getJSONArray(name));
}
%>

<aui:field-wrapper helpMessage='<%= label + "-help" %>' label="<%= label %>">

	<%
	for (String value : values) {
	%>

		<div class="form-check">
			<aui:input checked="<%= ArrayUtil.contains(chosenValues, value) %>" label="<%= editConfigurationDisplayContext.getCommerceCloudOrderForecastConfigurationLabel(value) %>" name="<%= name %>" type="checkbox" value="<%= value %>" />
		</div>

	<%
	}
	%>

</aui:field-wrapper>