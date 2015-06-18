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
LocalizedItemSelectorRendering localizedItemSelectorRendering = LocalizedItemSelectorRendering.get(liferayPortletRequest);

ResourceBundle resourceBundle = ResourceBundle.getBundle("content/Language", locale);

List<String> titles = localizedItemSelectorRendering.getTitles();
%>

<c:choose>
	<c:when test="<%= titles.isEmpty() %>">

		<%
		if (_log.isWarnEnabled()) {
			String[] criteria = ParamUtil.getParameterValues(renderRequest, "criteria");

			_log.warn("No item selector views found for " + StringUtil.merge(criteria, StringPool.COMMA_AND_SPACE));
		}
		%>

		<div class="portlet-msg-alert">
			<%= LanguageUtil.get(resourceBundle, "selection-is-not-available") %>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs names="<%= StringUtil.merge(titles) %>" refresh="<%= false %>" type="pills" value="<%= localizedItemSelectorRendering.getSelectedTab() %>">

			<%
			for (String title : titles) {
				ItemSelectorViewRenderer itemSelectorViewRenderer = localizedItemSelectorRendering.getItemSelectorViewRenderer(title);
			%>

				<liferay-ui:section>
					<div>

						<%
						itemSelectorViewRenderer.renderHTML(pageContext);
						%>

					</div>
				</liferay-ui:section>

			<%
			}
			%>

		</liferay-ui:tabs>
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_item_selector_web.view.jsp");
%>