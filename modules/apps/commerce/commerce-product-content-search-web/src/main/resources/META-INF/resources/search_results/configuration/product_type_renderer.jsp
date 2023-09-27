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

<liferay-ui:tabs
	names="<%= cpSearchResultsDisplayContext.getNames() %>"
	refresh="<%= false %>"
>

	<%
	for (CPType cpType : cpSearchResultsDisplayContext.getCPTypes()) {
	%>

		<liferay-ui:section>
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
		</liferay-ui:section>

	<%
	}
	%>

</liferay-ui:tabs>