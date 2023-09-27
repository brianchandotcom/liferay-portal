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
	<aui:input checked="<%= cpSearchResultsDisplayContext.isSelectionStyleADT() %>" id="selectionStyleADT" label="use-adt" name="preferences--selectionStyle--" type="radio" value="adt" />

	<aui:input checked="<%= cpSearchResultsDisplayContext.isSelectionStyleCustomRenderer() %>" label="use-custom-renderer" name="preferences--selectionStyle--" type="radio" value="custom" />
</aui:fieldset>

<liferay-frontend:component
	module="js/configuration/selection_style"
/>