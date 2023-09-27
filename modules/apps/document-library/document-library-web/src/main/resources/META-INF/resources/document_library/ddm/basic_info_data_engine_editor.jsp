<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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