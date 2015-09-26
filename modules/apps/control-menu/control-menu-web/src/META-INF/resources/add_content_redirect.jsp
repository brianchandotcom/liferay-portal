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
String redirect = request.getParameter("redirect");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());

String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");

String portletId = PortletProviderUtil.getPortletId(className, PortletProvider.Action.ADD);

AssetRenderer<?> assetRenderer = null;

Map<String, Object> data = new HashMap<String, Object>();

if (Validator.isNotNull(className) && (classPK > 0)) {
	AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	assetRenderer = assetRendererFactory.getAssetRenderer(classPK);

	data.put("class-name", className);
	data.put("class-pk", classPK);
	data.put("instanceable", true);
	data.put("portlet-id", portletId);
}
%>

<span <%= AUIUtil.buildData(data) %> class="hide portlet-item"></span>

<aui:script use="aui-base">
	<c:if test="<%= assetRenderer != null %>">
		var Util = Liferay.Util;

		<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.ADD_TO_PAGE) %>">
			Util.getOpener().Liferay.fire(
				'AddContent:addPortlet',
				{
					node: A.one('.portlet-item')
				}
			);
		</c:if>

		Util.getOpener().Liferay.fire('AddContent:refreshContentList');
	</c:if>

	Liferay.fire(
		'closeWindow',
		{
			id: '<portlet:namespace />editAsset',
			portletAjaxable: <%= selPortlet.isAjaxable() %>,

			<c:choose>
				<c:when test="<%= Validator.isNotNull(redirect) %>">
					redirect: '<%= HtmlUtil.escapeJS(redirect) %>'
				</c:when>
				<c:otherwise>
					refresh: '<%= portletDisplay.getId() %>'
				</c:otherwise>
			</c:choose>
		}
	);
</aui:script>