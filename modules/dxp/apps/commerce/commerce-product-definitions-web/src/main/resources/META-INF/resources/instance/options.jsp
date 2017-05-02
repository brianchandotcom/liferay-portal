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
CPInstance cpInstance = (CPInstance)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_INSTANCE);

CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

<%
if (cpInstance == null) {
	List<CPDefinitionOptionRel> cpDefinitionOptionRels = cpInstanceDisplayContext.getSkuContributorCPDefinitionOptionRels();

	for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
		long cpDefinitionOptionRelId = cpDefinitionOptionRel.getCPDefinitionOptionRelId();
%>

		<aui:select label="<%= cpDefinitionOptionRel.getName(languageId) %>" name="<%= String.valueOf(cpDefinitionOptionRelId) %>">

			<%
			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = cpInstanceDisplayContext.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId);

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
				long cpDefinitionOptionValueRelId = cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId();
			%>

				<aui:option label="<%= cpDefinitionOptionValueRel.getTitle(languageId) %>" value="<%= String.valueOf(cpDefinitionOptionValueRelId) %>" />

			<%
			}
			%>

		</aui:select>

<%
	}
}
else {
	JSONArray ddmContents = JSONFactoryUtil.createJSONArray(cpInstance.getDDMContent());

	for (int i = 0; i < ddmContents.length(); i++) {
		JSONObject ddmContent = ddmContents.getJSONObject(i);

		long cpDefinitionOptionRelId = ddmContent.getLong("cpDefinitionOptionRelId");
		long cpDefinitionOptionValueRelId = ddmContent.getLong("cpDefinitionOptionValueRelId");

		CPDefinitionOptionRel cpDefinitionOptionRel = CPDefinitionOptionRelServiceUtil.fetchCPDefinitionOptionRel(cpDefinitionOptionRelId);
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = CPDefinitionOptionValueRelServiceUtil.fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
%>

		<%= cpDefinitionOptionRel.getName(languageId) + " - " + cpDefinitionOptionValueRel.getTitle(languageId) %>

<%
	}
}
%>