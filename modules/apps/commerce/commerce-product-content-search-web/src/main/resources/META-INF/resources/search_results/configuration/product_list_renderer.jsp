<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPSearchResultsDisplayContext cpSearchResultsDisplayContext = (CPSearchResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPContentListRenderer> cpContentListRenderers = cpSearchResultsDisplayContext.getCPContentListRenderers();
%>

<aui:fieldset markupView="lexicon">
	<aui:select id="cpContentListRendererKeySelect" label="product-list-renderer" name="preferences--cpContentListRendererKey--">

		<%
		for (CPContentListRenderer cpContentListRenderer : cpContentListRenderers) {
			String key = cpContentListRenderer.getKey();
		%>

			<aui:option label="<%= cpContentListRenderer.getLabel(locale) %>" selected="<%= key.equals(cpSearchResultsDisplayContext.getCPContentListRendererKey()) %>" value="<%= key %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>

<liferay-frontend:component
	module="js/configuration/product_list_renderer"
/>