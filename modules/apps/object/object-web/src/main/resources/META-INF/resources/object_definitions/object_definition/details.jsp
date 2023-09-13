<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				"isRootDescendantNode", objectDefinition.isRootDescendantNode()
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
				"storageTypes", objectDefinitionsDetailsDisplayContext.getStorageTypesJSONArray()
			).build()
		%>'
	/>
</div>