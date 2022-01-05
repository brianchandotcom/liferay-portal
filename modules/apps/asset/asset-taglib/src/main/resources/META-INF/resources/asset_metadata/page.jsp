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

<%@ include file="/asset_metadata/init.jsp" %>

<%
AssetEntry assetEntry = (AssetEntry)request.getAttribute("liferay-asset:asset-metadata:assetEntry");
String[] metadataFields = (String[])request.getAttribute("liferay-asset:asset-metadata:metadataFields");
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<dl class="taglib-asset-metadata">
	<c:choose>
		<c:when test="<%= metadataFields.length == 1 %>">

			<%
			request.setAttribute("liferay-asset:asset-metadata:metadataField", metadataFields[0]);
			%>

			<liferay-util:include page="/asset_metadata/metadata_entry.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<c:if test='<%= ArrayUtil.contains(metadataFields, String.valueOf("author")) %>'>

				<%
				request.setAttribute("liferay-asset:asset-metadata:metadataField", "author");

				metadataFields = ArrayUtil.remove(metadataFields, String.valueOf("author"));
				%>

				<liferay-util:include page="/asset_metadata/metadata_entry.jsp" servletContext="<%= application %>" />
			</c:if>

			<liferay-util:buffer
				var="metadataPanelContent"
			>

				<%
				for (String metadataField : metadataFields) {
					request.setAttribute("liferay-asset:asset-metadata:metadataField", metadataField);
				%>

					<liferay-util:include page="/asset_metadata/metadata_entry.jsp" servletContext="<%= application %>" />

				<%
				}
				%>

			</liferay-util:buffer>

			<c:if test="<%= Validator.isNotNull(metadataPanelContent) %>">
				<liferay-ui:panel
					collapsible="<%= true %>"
					cssClass="asset-metadata-panel"
					defaultState="closed"
					extended="<%= false %>"
					id='<%= "metadataPanel" + assetEntry.getEntryId() %>'
					persistState="<%= false %>"
					title="more-details"
				>
					<%= metadataPanelContent %>
				</liferay-ui:panel>
			</c:if>
		</c:otherwise>
	</c:choose>
</dl>