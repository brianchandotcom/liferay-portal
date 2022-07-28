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

<%@ include file="/admin/common/init.jsp" %>

<%
KBAdminNavigationDisplayContext kbAdminNavigationDisplayContext = new KBAdminNavigationDisplayContext(request, renderRequest, renderResponse);
%>

<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-156421")) %>'>
	<div class="expanded knowledge-base-vertical-bar" id="<portlet:namespace />verticalBarId">
		<react:component
			componentId="verticalBarId"
			module="admin/js/components/VerticalBar"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"items", kbAdminNavigationDisplayContext.getVerticalNavigationJSONObjects()
				).put(
					"parentContainerId", liferayPortletResponse.getNamespace() + "verticalBarId"
				).build()
			%>'
		/>
	</div>
</c:if>