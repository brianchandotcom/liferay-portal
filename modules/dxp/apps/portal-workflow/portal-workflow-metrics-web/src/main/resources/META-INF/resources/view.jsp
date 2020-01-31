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

<%
Map<String, Object> data = new HashMap<>();

data.put("defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
data.put("deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES);
data.put("isAmPm", DateUtil.isFormatAmPm(locale));
data.put("maxPages", PropsValues.SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES);
%>

<div>
	<react:component
		data="<%= data %>"
		module="js/index.es"
	/>
</div>