<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);
%>

<div id="<portlet:namespace />configuration-tabs">
	<ul class="nav nav-tabs">

		<%
		for (CPType cpType : cpContentHelper.getCPTypes()) {
		%>

			<li class="nav-item">
				<a class="nav-link" href="#<%= HtmlUtil.escape(cpType.getName()) %>"><%= HtmlUtil.escape(cpType.getLabel(locale)) %></a>
			</li>

		<%
		}
		%>

	</ul>

	<div class="tab-content">

		<%
		for (CPType cpType : cpContentHelper.getCPTypes()) {
		%>

			<div id="<%= HtmlUtil.escape(cpType.getName()) %>">
				<aui:fieldset markupView="lexicon">
					<aui:select label='<%= HtmlUtil.escape(cpType.getLabel(locale) + StringPool.SPACE + LanguageUtil.get(request, "cp-type-renderer-key")) %>' name='<%= "preferences--" + cpType.getName() + "--cpTypeRendererKey--" %>'>

						<%
						for (CPContentRenderer cpContentRenderer : cpContentHelper.getCPContentRenderers(cpType.getName())) {
							String key = cpContentRenderer.getKey();
						%>

							<aui:option label="<%= HtmlUtil.escape(cpContentRenderer.getLabel(locale)) %>" selected="<%= key.equals(cpContentHelper.getCPContentRendererKey(cpType.getName(), renderRequest)) %>" value="<%= key %>" />

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>
			</div>

		<%
		}
		%>

	</div>
</div>

<aui:script use="aui-tabview">
	new A.TabView({
		srcNode: '#<portlet:namespace />configuration-tabs',
	}).render();
</aui:script>