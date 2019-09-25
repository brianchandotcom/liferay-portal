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

<div class="app-builder-root">
	<aui:form>
		<liferay-data-engine:data-layout-renderer
			containerId='<%= renderResponse.getNamespace() + "container" %>'
			dataLayoutId='<%= ParamUtil.getLong(request, "dataLayoutId") %>'
			dataRecordId='<%= ParamUtil.getLong(request, "dataRecordId") %>'
			namespace="<%= renderResponse.getNamespace() %>"
		/>

		<div id="<portlet:namespace />-app-builder-edit-entry">

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("basePortletURL", String.valueOf(renderResponse.createRenderURL()));
			data.put("dataDefinitionId", ParamUtil.getLong(request, "dataDefinitionId"));
			data.put("dataRecordId", ParamUtil.getLong(request, "dataRecordId"));
			data.put("editEntryContainerElementId", renderResponse.getNamespace() + "container");
			%>

			<react:component
				data="<%= data %>"
				module="js/pages/entry/EditEntryApp.es"
			/>
		</div>
	</aui:form>
</div>