<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
long[] selectedGroupIds = StringUtil.split(ParamUtil.getString(request, "selectedGroupIds"), 0L);
long refererAssetEntryId = ParamUtil.getLong(request, "refererAssetEntryId");
String typeSelection = ParamUtil.getString(request, "typeSelection");
String callback = ParamUtil.getString(request, "callback");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/asset_browser/view");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("selectedGroupIds", StringUtil.merge(selectedGroupIds));
portletURL.setParameter("refererAssetEntryId", String.valueOf(refererAssetEntryId));
portletURL.setParameter("typeSelection", typeSelection);
portletURL.setParameter("callback", callback);

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-util:include page="/html/portlet/asset_browser/toolbar.jsp">
	<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-util:param name="typeSelection" value="<%= typeSelection %>" />
</liferay-util:include>

<div class="asset-search">
	<aui:form action="<%= portletURL %>" method="post" name="searchFm">
		<aui:input name="callback" type="hidden" value="<%= callback %>" />
		<aui:input name="typeSelection" type="hidden" value="<%= typeSelection %>" />

		<liferay-ui:search-container
			searchContainer="<%= new AssetSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/asset_publisher/asset_search.jsp"
			/>

			<%
			AssetSearchTerms searchTerms = (AssetSearchTerms)searchContainer.getSearchTerms();

			long[] groupIds = selectedGroupIds;

			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);
			%>

			<liferay-ui:search-container-results>
				<%@ include file="/html/portlet/asset_publisher/asset_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<div class="separator"><!-- --></div>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.search.Document"
				escapedModel="<%= true %>"
				modelVar="doc"
			>

				<%
				long assetEntryId = 0;

				if (typeSelection.equals(JournalArticle.class.getName())) {
					assetEntryId = GetterUtil.getLong(doc.get(Field.ROOT_ENTRY_CLASS_PK));
				}
				else {
					assetEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));
				}

				AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(typeSelection, assetEntryId);

				if ((assetEntry == null) || !assetEntry.isVisible()) {
					continue;
				}

				String rowHREF = null;

				Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());

				if (assetEntry.getEntryId() != refererAssetEntryId) {
					StringBundler sb = new StringBundler(13);

					sb.append("javascript:Liferay.Util.getOpener().");
					sb.append(callback);
					sb.append("('");
					sb.append(assetEntry.getEntryId());
					sb.append("', '");
					sb.append(assetEntry.getClassName());
					sb.append("', '");
					sb.append(assetRendererFactory.getTypeName(locale, true));
					sb.append("', '");
					sb.append(HtmlUtil.escapeJS(assetEntry.getTitle(locale)));
					sb.append("', '");
					sb.append(HtmlUtil.escapeJS(group.getDescriptiveName(locale)));
					sb.append("');Liferay.Util.getWindow().hide();");

					rowHREF = sb.toString();
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="title"
					value="<%= HtmlUtil.escape(assetEntry.getTitle(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="description"
					value="<%= HtmlUtil.stripHtml(assetEntry.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="userName"
					value="<%= PortalUtil.getUserName(assetEntry) %>"
				/>

				<liferay-ui:search-container-column-date
					href="<%= rowHREF %>"
					name="modifiedDate"
					value="<%= assetEntry.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="descriptiveName"
					value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
				/>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</aui:form>
</div>