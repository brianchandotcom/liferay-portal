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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.sort.display.context.SortDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.sort.display.context.SortTermDisplayContext" %>

<liferay-theme:defineObjects />

<%
SortDisplayContext sortDisplayContext = (SortDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<c:choose>
	<c:when test="<%= sortDisplayContext.isRenderNothing() %>">
		<div class="alert alert-info text-center">
			<liferay-ui:message key="this-widget-is-not-visible-to-users-yet" />

			<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="complete-its-configuration-to-make-it-visible" /></aui:a>
		</div>
	</c:when>
	<c:otherwise>
		<aui:form method="post" name="fm">
			<aui:fieldset>
				<aui:input cssClass="sort-parameter-name" name="sort-parameter-name" type="hidden" value="<%= sortDisplayContext.getParameterName() %>" />

				<aui:select class="sort-term" label="sort-by" name="sortSelection">

					<%
					for (SortTermDisplayContext sortTermDisplayContext : sortDisplayContext.getSortTermDisplayContexts()) {
					%>

						<aui:option label="<%= sortTermDisplayContext.getLabel() %>" selected="<%= sortTermDisplayContext.isSelected() %>" value="<%= sortTermDisplayContext.getField() %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>
		</aui:form>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-sort-util">
AUI().ready('aui-base', 'node', 'event', function(A) {
	A.one('#<portlet:namespace />sortSelection').on('change', function() {
		var selections = [];

		var sortSelect = A.one('#<portlet:namespace />sortSelection').get(
			'value'
		);

		selections.push(sortSelect);

		var key = A.one('#<portlet:namespace />sort-parameter-name').get(
			'value'
		);

		document.location.search = Liferay.Search.SortUtil.updateQueryString(
			key,
			selections,
			document.location.search
		);
	});
});
</aui:script>