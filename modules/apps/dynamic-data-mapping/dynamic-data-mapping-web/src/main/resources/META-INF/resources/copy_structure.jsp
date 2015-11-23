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
DDMStructure structure = (DDMStructure)request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

DDMStructureVersion structureVersion = structure.getStructureVersion();

long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
long classPK = BeanParamUtil.getLong(structure, request, "structureId");

boolean copyFormTemplates = ParamUtil.getBoolean(request, "copyFormTemplates");
boolean copyDisplayTemplates = ParamUtil.getBoolean(request, "copyDisplayTemplates");
%>

<portlet:actionURL name="copyStructure" var="copyStructureURL">
	<portlet:param name="mvcPath" value="/copy_structure.jsp" />
</portlet:actionURL>

<portlet:renderURL var="closeRedirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<aui:form action="<%= copyStructureURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirectURL %>" />
	<aui:input name="classNameId" type="hidden" value="<%= String.valueOf(classNameId) %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="resourceClassNameId" type="hidden" value="<%= String.valueOf(scopeClassNameId) %>" />
	<aui:input name="status" type="hidden" value="<%= structureVersion.getStatus() %>" />
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