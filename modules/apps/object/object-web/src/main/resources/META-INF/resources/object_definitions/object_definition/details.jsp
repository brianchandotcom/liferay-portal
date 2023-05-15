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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectDefinitionsDetailsDisplayContext objectDefinitionsDetailsDisplayContext = (ObjectDefinitionsDetailsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "edit-x", objectDefinition.getLabel(locale, true), false));
%>

<div id="<portlet:namespace />EditObjectDefinition">
	<react:component
		module="js/components/ObjectDetails/EditObjectDetails"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"backURL", ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()))
			).put(
				"companyKeyValuePair", objectDefinitionsDetailsDisplayContext.getScopeKeyValuePairs("company")
			).put(
				"dbTableName", objectDefinition.getDBTableName()
			).put(
				"externalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"hasPublishObjectPermission", objectDefinitionsDetailsDisplayContext.hasPublishObjectPermission()
			).put(
				"hasUpdateObjectDefinitionPermission", objectDefinitionsDetailsDisplayContext.hasUpdateObjectDefinitionPermission()
			).put(
				"isApproved", objectDefinition.isApproved()
			).put(
				"label", LocalizationUtil.getLocalizationMap(objectDefinition.getLabel())
			).put(
				"nonRelationshipObjectFieldsInfo", objectDefinitionsDetailsDisplayContext.getNonrelationshipObjectFieldsInfo()
			).put(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).put(
				"pluralLabel", LocalizationUtil.getLocalizationMap(objectDefinition.getPluralLabel())
			).put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).put(
				"shortName", objectDefinition.getShortName()
			).put(
				"siteKeyValuePair", objectDefinitionsDetailsDisplayContext.getScopeKeyValuePairs("site")
			).put(
				"storageTypes", objectDefinitionsDetailsDisplayContext.getStoragesJSONArray()
			).build()
		%>'
	/>
</div>