<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<c:choose>
	<c:when test="<%= Validator.isNotNull(request.getAttribute(NoSuchLayoutException.class.getName())) %>">

		<%
		LayoutUtilityPageEntryViewRenderer layoutUtilityPageEntryViewRenderer = LayoutUtilityPageEntryViewRendererRegistryUtil.getLayoutUtilityPageEntryViewRenderer(LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND);
		%>

		<c:choose>
			<c:when test="<%= layoutUtilityPageEntryViewRenderer != null %>">

				<%
				layoutUtilityPageEntryViewRenderer.renderHTML(request, response);
				%>

			</c:when>
			<c:otherwise>
				<div class="container pb-3 pt-3">
					<h3 class="alert alert-danger">
						<liferay-ui:message key="not-found" />
					</h3>

					<liferay-ui:message key="the-requested-resource-could-not-be-found" />

					<br /><br />

					<%
					String url = ParamUtil.getString(request, "previousURL");

					if (Validator.isNull(url)) {
						url = PortalUtil.getCurrentURL(request);
					}

					url = HttpComponentsUtil.decodeURL(themeDisplay.getPortalURL() + url);
					%>

					<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>

		<%
		StringBundler sb = (StringBundler)request.getAttribute(WebKeys.LAYOUT_CONTENT);

		sb.writeTo(out);

		request.removeAttribute(WebKeys.LAYOUT_CONTENT);
		%>

	</c:otherwise>
</c:choose>