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
for (AssetEntryResult assetEntryResult : assetPublisherDisplayContext.getAssetEntryResults()) {
	List<AssetEntry> assetEntries = assetEntryResult.getAssetEntries();
%>

	<c:choose>
		<c:when test='<%= Objects.equals(assetPublisherDisplayContext.getDisplayStyle(), "abstracts") %>'>

			<%
			request.setAttribute("view.jsp-assetEntryResult", assetEntryResult);
			%>

			<liferay-util:include page="/view_asset_entry_abstract.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= Objects.equals(assetPublisherDisplayContext.getDisplayStyle(), "table") %>'>

			<%
			request.setAttribute("view.jsp-assetEntryResult", assetEntryResult);
			%>

			<liferay-util:include page="/view_asset_entry_table.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= Objects.equals(assetPublisherDisplayContext.getDisplayStyle(), "title-list") %>'>

			<%
			request.setAttribute("view.jsp-assetEntryResult", assetEntryResult);
			%>

			<liferay-util:include page="/view_asset_entry_title_list.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<c:if test="<%= Validator.isNotNull(assetEntryResult.getTitle()) %>">
				<h3 class="asset-entries-group-label"><%= HtmlUtil.escape(assetEntryResult.getTitle()) %></h3>
			</c:if>

			<liferay-ddm:template-renderer
				className="<%= AssetEntry.class.getName() %>"
				displayStyle="<%= assetPublisherDisplayContext.getDisplayStyle() %>"
				displayStyleGroupId="<%= assetPublisherDisplayContext.getDisplayStyleGroupId() %>"
				entries="<%= assetEntries %>"
			>

				<%
				request.setAttribute("view.jsp-results", assetEntries);

				for (int assetEntryIndex = 0; assetEntryIndex < assetEntries.size(); assetEntryIndex++) {
					AssetEntry assetEntry = assetEntries.get(assetEntryIndex);

					long classPK = assetEntry.getClassPK();

					AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(assetEntry.getClassNameId());

					if (assetRendererFactory == null) {
						continue;
					}

					AssetRenderer<?> assetRenderer = null;

					try {
						assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn(e, e);
						}
					}

					if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
						continue;
					}

					request.setAttribute("view.jsp-assetEntry", assetEntry);
					request.setAttribute("view.jsp-assetEntryIndex", assetEntryIndex);
					request.setAttribute("view.jsp-assetRenderer", assetRenderer);
					request.setAttribute("view.jsp-assetRendererFactory", assetRendererFactory);
					request.setAttribute("view.jsp-print", Boolean.FALSE);
					request.setAttribute("view.jsp-title", assetRenderer.getTitle(locale));

					try {
				%>

						<liferay-util:include page='<%= "/display/" + TextFormatter.format(assetPublisherDisplayContext.getDisplayStyle(), TextFormatter.N) + ".jsp" %>' servletContext="<%= application %>" />

				<%
					}
					catch (Exception e) {
						_log.error(e.getMessage());
					}
				}
				%>

			</liferay-ddm:template-renderer>
		</c:otherwise>
	</c:choose>

<%
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_asset_entry_list_jsp");
%>