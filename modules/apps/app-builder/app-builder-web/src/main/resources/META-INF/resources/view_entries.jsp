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

<div id="<portlet:namespace />-app-builder-root">

	<%
	AppBuilderApp appBuilderApp = (AppBuilderApp)request.getAttribute(AppBuilderWebKeys.APP);
	%>

	<react:component
		module="js/pages/entry/ViewEntriesApp.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"appDeploymentType", request.getAttribute(AppBuilderWebKeys.APP_DEPLOYMENT_TYPE)
			).put(
				"appId", appBuilderApp.getAppBuilderAppId()
			).put(
				"appTab", request.getAttribute(AppBuilderWebKeys.APP_TAB)
			).put(
				"basePortletURL", String.valueOf(renderResponse.createRenderURL())
			).put(
				"dataDefinitionId", appBuilderApp.getDdmStructureId()
			).put(
				"dataLayoutId", appBuilderApp.getDdmStructureLayoutId()
			).put(
				"dataListViewId", appBuilderApp.getDeDataListViewId()
			).put(
				"defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
			).put(
				"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
			).put(
				"portraitURL", request.getAttribute(AppBuilderWebKeys.APP_PORTRAIT_URL)
			).put(
				"showFormView", request.getAttribute(AppBuilderWebKeys.SHOW_FORM_VIEW)
			).put(
				"showTableView", request.getAttribute(AppBuilderWebKeys.SHOW_TABLE_VIEW)
			).put(
				"workflowClassName", request.getAttribute(AppBuilderWebKeys.WORKFLOW_CLASS_NAME)
			).build()
		%>'
	/>
</div>