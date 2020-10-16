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
CPSearchResultsDisplayContext cpSearchResultsDisplayContext = (CPSearchResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div id="<portlet:namespace />configuration-tabs">
	<ul class="nav nav-tabs">

		<%
		for (CPType cpType : cpSearchResultsDisplayContext.getCPTypes()) {
		%>

			<li class="nav-item">
				<a class="nav-link" href="#<%= cpType.getName() %>"><%= HtmlUtil.escape(cpType.getLabel(locale)) %></a>
			</li>

		<%
		}
		%>

	</ul>

	<div class="tab-content">

		<%
		for (CPType cpType : cpSearchResultsDisplayContext.getCPTypes()) {
		%>

			<div id="<%= cpType.getName() %>">
				<aui:fieldset markupView="lexicon">
					<aui:select label='<%= HtmlUtil.escape(cpType.getLabel(locale)) + StringPool.SPACE + LanguageUtil.get(request, "cp-type-list-entry-renderer-key") %>' name='<%= "preferences--" + cpType.getName() + "--cpTypeListEntryRendererKey--" %>'>

						<%
						for (CPContentListEntryRenderer cpContentListEntryRenderer : cpSearchResultsDisplayContext.getCPContentListEntryRenderers(cpType.getName())) {
							String key = cpContentListEntryRenderer.getKey();
						%>

							<aui:option label="<%= HtmlUtil.escape(cpContentListEntryRenderer.getLabel(locale)) %>" selected="<%= key.equals(cpSearchResultsDisplayContext.getCPTypeListEntryRendererKey(cpType.getName())) %>" value="<%= key %>" />

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