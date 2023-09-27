<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCompareContentMiniDisplayContext cpCompareContentMiniDisplayContext = (CPCompareContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPContentListRenderer> cpContentListRenderers = cpCompareContentMiniDisplayContext.getCPContentListRenderers();
%>

<aui:fieldset markupView="lexicon">
	<aui:select label="product-list-renderer" name="preferences--cpContentListRendererKey--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseCPContentListRendererKey();" %>'>

		<%
		for (CPContentListRenderer cpContentListRenderer : cpContentListRenderers) {
			String key = cpContentListRenderer.getKey();
		%>

			<aui:option label="<%= HtmlUtil.escape(cpContentListRenderer.getLabel(locale)) %>" selected="<%= key.equals(cpCompareContentMiniDisplayContext.getCPContentListRendererKey()) %>" value="<%= key %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />chooseCPContentListRendererKey() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>