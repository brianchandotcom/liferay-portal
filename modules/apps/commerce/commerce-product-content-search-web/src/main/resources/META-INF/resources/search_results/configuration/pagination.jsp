<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPSearchResultsDisplayContext cpSearchResultsDisplayContext = (CPSearchResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:fieldset markupView="lexicon">
	<aui:input checked="<%= cpSearchResultsDisplayContext.isPaginate() %>" inlineLabel="right" label="paginate" labelCssClass="simple-toggle-switch" name="preferences--paginate--" type="toggle-switch" />

	<aui:input helpMessage="maximum-number-of-products-to-display-if-pagination-is-disabled-otherwise-number-of-items-to-display-per-page" label="number-of-items-to-display" name="preferences--paginationDelta--" type="number" value="<%= cpSearchResultsDisplayContext.getPaginationDelta() %>" />
</aui:fieldset>