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

<%@ include file="/document_library/init.jsp" %>

<%
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

String refererWebDAVToken = WebDAVUtil.getStorageToken(portlet);

DLEditDDMStructureDisplayContext dlEditDDMStructureDisplayContext = new DLEditDDMStructureDisplayContext(request, liferayPortletResponse);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = dlEditDDMStructureDisplayContext.getDDMStructure();
%>

<aui:model-context bean="<%= ddmStructure %>" model="<%= com.liferay.dynamic.data.mapping.model.DDMStructure.class %>" />

<clay:row
	cssClass="lfr-ddm-types-form-column"
>
	<aui:input name="storageType" type="hidden" value="<%= StorageType.DEFAULT.getValue() %>" />
</clay:row>

<aui:input name="description" />

<c:if test="<%= ddmStructure != null %>">
	<portlet:resourceURL id="/dynamic_data_mapping/get_structure" var="getStructureURL">
		<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
	</portlet:resourceURL>

	<aui:input name="url" type="resource" value="<%= getStructureURL.toString() %>" />

	<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
		<aui:input name="webDavURL" type="resource" value="<%= ddmStructure.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
	</c:if>
</c:if>