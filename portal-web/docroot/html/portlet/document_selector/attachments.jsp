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

<%@ include file="/html/portlet/document_selector/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

String eventName = ParamUtil.getString(request, "eventName");

String attachmentURLPrefix = ParamUtil.getString(request, "attachmentURLPrefix");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_selector/view");
portletURL.setParameter("eventName", eventName);
portletURL.setParameter("groupId", String.valueOf(groupId));
%>

<aui:form method="post" name="selectDocumentFm">

	<%
	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("struts_action", "/document_selector/view");
	iteratorURL.setParameter("eventName", eventName);
	iteratorURL.setParameter("groupId", String.valueOf(groupId));
	%>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-documents-in-this-folder"
		iteratorURL="<%= iteratorURL %>"
	>

		<%
		long wikiPageResourcePrimKey = ParamUtil.getLong(request, "wikiPageResourcePrimKey");

		WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.fetchWikiPageResource(wikiPageResourcePrimKey);

		WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

		Map<String, String> params = new HashMap<String, String>();

		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(request);

		for (String param : httpServletRequest.getParameterMap().keySet()) {
			String[] values = httpServletRequest.getParameterValues(param);

			params.put(param, StringUtil.merge(values));
		}
		%>

		<liferay-ui:search-container-results
			results="<%= wikiPage.getAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			keyProperty="fileEntryId"
			modelVar="fileEntry"
		>

			<%
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());
			%>

			<liferay-ui:search-container-column-text
				name="file-name"
			>
				<liferay-ui:icon
					iconCssClass="<%= assetRenderer.getIconCssClass() %>"
					label="<%= true %>"
					message="<%= fileEntry.getTitle() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="size"
				value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>(params);

				data.put("groupid", fileEntry.getGroupId());
				data.put("title", fileEntry.getTitle());
				data.put("url", attachmentURLPrefix + fileEntry.getTitle());
				data.put("uuid", fileEntry.getUuid());
				data.put("version", fileEntry.getVersion());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectDocumentFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>