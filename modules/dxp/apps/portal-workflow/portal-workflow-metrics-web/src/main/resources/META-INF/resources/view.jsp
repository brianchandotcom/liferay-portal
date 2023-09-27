<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<%
	SimpleDateFormat simpleDateFormat = (SimpleDateFormat)DateFormat.getTimeInstance(DateFormat.SHORT, locale);
	%>

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
				"timeFormat", simpleDateFormat.toPattern()
			).put(
				"userId", themeDisplay.getUserId()
			).put(
				"userName", PortalUtil.getUserName(themeDisplay.getUserId(), String.valueOf(themeDisplay.getUserId()))
			).build()
		%>'
	/>
</div>