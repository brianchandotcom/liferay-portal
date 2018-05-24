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
CommerceDashboardDisplayContext commerceDashboardDisplayContext = (CommerceDashboardDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="editCommerceDashboardPeriod" var="editCommerceDashboardPeriodURL" />

<aui:form action="<%= editCommerceDashboardPeriodURL %>" cssClass="form-group-autofit" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="btn-group">

		<%
		for (int period : CommerceForecastEntryConstants.PERIODS) {
		%>

			<clay:button
				elementClasses='<%= period == commerceDashboardDisplayContext.getPeriod() ? "active" : StringPool.BLANK %>'
				label="<%= LanguageUtil.get(request, CommerceForecastEntryConstants.getPeriodLabel(period)) %>"
				name='<%= renderResponse.getNamespace() + "period" %>'
				style="secondary"
				type="submit"
				value="<%= String.valueOf(period) %>"
			/>

		<%
		}
		%>

	</div>
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom">
	var fm = document.querySelector('#<portlet:namespace/>fm');

	var editCommerceDashboardPeriodHandler = dom.delegate(
		fm,
		'click',
		'button',
		function(event) {
			event.preventDefault();

			submitForm(document.hrefFm, fm.action + '&<portlet:namespace />period=' + event.delegateTarget.value);
		}
	);

	function removeListener() {
		editCommerceDashboardPeriodHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	}

	Liferay.on('destroyPortlet', removeListener);
</aui:script>