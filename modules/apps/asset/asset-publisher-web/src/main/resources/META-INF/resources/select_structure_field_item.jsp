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
String className = ParamUtil.getString(request, "className");
long classTypeId = ParamUtil.getLong(request, "classTypeId");
String ddmStructureFieldName = ParamUtil.getString(request, "ddmStructureFieldName");
Serializable ddmStructureFieldValue = ParamUtil.getString(request, "ddmStructureFieldValue");
String name = ParamUtil.getString(request, "name");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

ClassType classType = classTypeReader.getClassType(classTypeId, locale);

ClassTypeField classTypeField = classType.getClassTypeField(name);

com.liferay.dynamic.data.mapping.storage.Field ddmField = new com.liferay.dynamic.data.mapping.storage.Field();

ddmField.setDefaultLocale(themeDisplay.getLocale());
ddmField.setDDMStructureId(classTypeField.getClassTypeId());
ddmField.setName(name);

if (name.equals(ddmStructureFieldName)) {
	ddmField.setValue(themeDisplay.getLocale(), ddmStructureFieldValue);
}
%>

<liferay-ddm:html-field
	classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
	classPK="<%= classTypeField.getClassTypeId() %>"
	field="<%= ddmField %>"
/>

<aui:script>
	Liferay.componentReady('<portlet:namespace />ddmForm').then(() => {
		const initialDDMForm = Liferay.component('<portlet:namespace />ddmForm');

		initialDDMForm.get('fields').forEach((field) => {
			if (field.get('name') === '<%=ddmStructureFieldName %>') {
				field.setValue('<%=ddmStructureFieldValue %>');
			}
		});
	});
</aui:script>