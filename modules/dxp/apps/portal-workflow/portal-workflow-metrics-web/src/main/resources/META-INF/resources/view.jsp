<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="js/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
			).put(
				"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
			).put(
				"isAmPm", DateUtil.isFormatAmPm(locale)
			).put(
				"maxPages", PropsValues.SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES
			).put(
				"userId", themeDisplay.getUserId()
			).put(
				"userName", PortalUtil.getUserName(themeDisplay.getUserId(), String.valueOf(themeDisplay.getUserId()))
			).put(
				"workflowInstanceTrackerEnabled", FFWorkflowMetricsConfigurationUtil.workflowInstanceTrackerEnabled()
			).build()
		%>'
	/>
</div>