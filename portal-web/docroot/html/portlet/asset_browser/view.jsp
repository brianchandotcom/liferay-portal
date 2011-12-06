<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_browser/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

long refererAssetEntryId = ParamUtil.getLong(request, "refererAssetEntryId");
String typeSelection = ParamUtil.getString(request, "typeSelection");
String callback = ParamUtil.getString(request, "callback");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/asset_browser/view");
portletURL.setParameter("refererAssetEntryId", String.valueOf(refererAssetEntryId));
portletURL.setParameter("typeSelection", typeSelection);
portletURL.setParameter("callback", callback);
%>

<liferay-ui:header
	title='<%= LanguageUtil.get(pageContext, "select") + StringPool.COLON + StringPool.SPACE + ResourceActionsUtil.getModelResource(locale, typeSelection) %>'
/>

<div class="asset-search">
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="struts_action" value="/asset_browser/view" />
		<portlet:param name="callback" value="<%= callback%>" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="post" name="searchFm">
		<aui:input name="typeSelection" type="hidden" value="<%= typeSelection %>" />

		<%
		AssetSearch searchContainer = new AssetSearch(renderRequest, portletURL);
		%>

		<liferay-ui:search-form
			page="/html/portlet/asset_browser/asset_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		AssetSearchTerms searchTerms = (AssetSearchTerms)searchContainer.getSearchTerms();

		long[] groupIds = new long[] {groupId};
		%>

		<%@ include file="/html/portlet/asset_publisher/asset_search_results.jspf" %>

		<div class="separator"><!-- --></div>

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.getDocs().length; i++) {
			Document doc = results.doc(i);

			ResultRow row = new ResultRow(doc, i, i);

			long assetEntryId = 0;

			if (typeSelection.equals(JournalArticle.class.getName())) {
				assetEntryId = GetterUtil.getLong(doc.get(Field.ROOT_ENTRY_CLASS_PK));
			}
			else {
				assetEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));
			}

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(typeSelection, assetEntryId);

			if (assetEntry == null) {
				continue;
			}

			assetEntry = assetEntry.toEscapedModel();

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

			String rowHREF = null;

			if ((assetEntry.getEntryId() != refererAssetEntryId) && assetEntry.isVisible()) {
				StringBundler sb = new StringBundler(9);

				sb.append("javascript:Liferay.Util.getOpener().");
				sb.append(callback);
				sb.append("('");
				sb.append(assetEntry.getEntryId());
				sb.append("', '");
				sb.append(ResourceActionsUtil.getModelResource(locale, assetEntry.getClassName()));
				sb.append("', '");
				sb.append(HtmlUtil.escapeJS(assetRenderer.getTitle(locale)));
				sb.append("');Liferay.Util.getWindow().close();");

				rowHREF = sb.toString();
			}

			// Title

			row.addText(assetRenderer.getTitle(locale), rowHREF);

			// Description

			row.addText(assetRenderer.getSummary(locale), rowHREF);

			// User name

			row.addText(HtmlUtil.escape(PortalUtil.getUserName(assetEntry.getUserId(), assetEntry.getUserName())), rowHREF);

			// Modified date

			row.addText(dateFormatDate.format(assetEntry.getModifiedDate()), rowHREF);

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</aui:form>
</div>