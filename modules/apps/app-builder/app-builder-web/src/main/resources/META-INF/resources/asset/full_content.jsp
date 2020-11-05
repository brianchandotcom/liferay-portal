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

<%@ include file="/asset/init.jsp" %>

<%
AppBuilderAppPortletTabContext appBuilderAppPortletTabContext = (AppBuilderAppPortletTabContext)request.getAttribute(AppBuilderWebKeys.APP_TAB_CONTEXT);

List<Long> dataLayoutIds = appBuilderAppPortletTabContext.getDataLayoutIds();
%>

<clay:container-fluid>
	<div class="card card-root mb-0 mt-4 shadowless-card" id="edit-app-content">
		<div class="card-body px-0">

			<%
			for (Long dataLayoutId : dataLayoutIds) {
				if (dataLayoutIds.size() > 1) {
			%>

					<h3 class="px-4"><%= appBuilderAppPortletTabContext.getName(dataLayoutId, locale) %></h3>

				<%
				}
				%>

				<liferay-data-engine:data-layout-renderer
					containerId='<%= liferayPortletResponse.getNamespace() + "container" + dataLayoutId %>'
					dataLayoutId="<%= dataLayoutId %>"
					dataRecordId="<%= GetterUtil.getLong(request.getAttribute(AppBuilderWebKeys.DATA_RECORD_ID)) %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					readOnly="<%= true %>"
				/>

			<%
			}
			%>

		</div>
	</div>
</clay:container-fluid>