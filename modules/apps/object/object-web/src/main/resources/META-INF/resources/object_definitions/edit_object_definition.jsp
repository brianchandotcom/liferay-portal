<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectDefinitionsDetailsDisplayContext objectDefinitionsDetailsDisplayContext = (ObjectDefinitionsDetailsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey");
%>

<c:choose>
	<c:when test="<%= objectDefinitionsDetailsDisplayContext.isChangeTrackingEnabled() %>">
		<div class="lfr-object__edit-object-definition publication">
	</c:when>
	<c:otherwise>
		<div class="lfr-object__edit-object-definition">
		</c:otherwise>
</c:choose>
	<c:choose>
		<c:when test='<%= Objects.equals(screenNavigationCategoryKey, "details") || Validator.isNull(screenNavigationCategoryKey) %>'>
			<div class="details-margin" style="margin-bottom: 4rem;"></div>
		</c:when>
		<c:otherwise>
			<div>
				<react:component
					module="js/components/ObjectManagementToolbar"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"backURL", ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()))
						).put(
							"externalReferenceCode", objectDefinition.getExternalReferenceCode()
						).put(
							"hasPublishObjectPermission", objectDefinitionsDetailsDisplayContext.hasPublishObjectPermission()
						).put(
							"hasUpdateObjectDefinitionPermission", objectDefinitionsDetailsDisplayContext.hasUpdateObjectDefinitionPermission()
						).put(
							"isApproved", objectDefinition.isApproved()
						).put(
							"label", objectDefinition.getLabel(locale, true)
						).put(
							"objectDefinitionId", objectDefinition.getObjectDefinitionId()
						).put(
							"portletNamespace", liferayPortletResponse.getNamespace()
						).put(
							"screenNavigationCategoryKey", ParamUtil.getString(request, "screenNavigationCategoryKey")
						).put(
							"system", objectDefinition.isSystem()
						).build()
					%>'
				/>
			</div>
		</c:otherwise>
	</c:choose>

	<liferay-frontend:screen-navigation
		context="<%= objectDefinition %>"
		key="<%= ObjectDefinitionsScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_OBJECT_DEFINITION %>"
		portletURL='<%=
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/object_definitions/edit_object_definition"
			).setParameter(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).build()
		%>'
	/>
</div>