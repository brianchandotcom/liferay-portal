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
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
long classPK = BeanParamUtil.getLong(structure, request, "structureId");

boolean copyFormTemplates = ParamUtil.getBoolean(request, "copyFormTemplates");
boolean copyDisplayTemplates = ParamUtil.getBoolean(request, "copyDisplayTemplates");
%>

<portlet:actionURL var="copyStructureURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/copy_structure" />
</portlet:actionURL>

<aui:form action="<%= copyStructureURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.COPY %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="classNameId" type="hidden" value="<%= String.valueOf(classNameId) %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= true %>" />

	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<c:if test="<%= Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) %>">
			<aui:input checked="<%= copyFormTemplates %>" label='<%= Validator.isNull(templateTypeValue) ? "copy-form-templates" : "copy-templates" %>' name="copyFormTemplates" type="checkbox" />
		</c:if>

		<c:if test="<%= Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY) %>">
			<aui:input checked="<%= copyDisplayTemplates %>" label='<%= Validator.isNull(templateTypeValue) ? "copy-display-templates" : "copy-templates" %>' name="copyDisplayTemplates" type="checkbox" />
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="copy" />

		<aui:button onClick="Liferay.Util.getWindow().hide();" value="close" />
	</aui:button-row>
</aui:form>