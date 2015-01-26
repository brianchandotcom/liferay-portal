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

<%@ include file="/html/portlet/asset_browser/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
long[] selectedGroupIds = StringUtil.split(ParamUtil.getString(request, "selectedGroupIds"), 0L);
long refererAssetEntryId = ParamUtil.getLong(request, "refererAssetEntryId");
String typeSelection = ParamUtil.getString(request, "typeSelection");
long subtypeSelectionId = ParamUtil.getLong(request, "subtypeSelectionId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectAsset");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("selectedGroupIds", StringUtil.merge(selectedGroupIds));
portletURL.setParameter("refererAssetEntryId", String.valueOf(refererAssetEntryId));
portletURL.setParameter("typeSelection", typeSelection);
portletURL.setParameter("subtypeSelectionId", String.valueOf(subtypeSelectionId));
portletURL.setParameter("eventName", eventName);

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="asset-search">
	<aui:form action="<%= portletURL %>" method="post" name="selectAssetFm">
		<aui:input name="typeSelection" type="hidden" value="<%= typeSelection %>" />

		<liferay-ui:search-container
			searchContainer="<%= new AssetBrowserSearch(renderRequest, portletURL) %>"
		>
			<aui:nav-bar>
				<aui:nav cssClass="navbar-nav" searchContainer="<%= searchContainer %>">
					<liferay-util:include page="/html/portlet/asset_browser/toolbar.jsp">
						<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<liferay-util:param name="typeSelection" value="<%= typeSelection %>" />
						<liferay-util:param name="subtypeSelectionId" value="<%= String.valueOf(subtypeSelectionId) %>" />
					</liferay-util:include>
				</aui:nav>

				<aui:nav-bar-search file="/html/portlet/asset_browser/search.jsp" searchContainer="<%= searchContainer %>" />
			</aui:nav-bar>

			<%
			AssetBrowserSearchTerms searchTerms = (AssetBrowserSearchTerms)searchContainer.getSearchTerms();

			long[] groupIds = selectedGroupIds;

			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);
			%>

			<liferay-ui:search-container-results>
				<c:choose>
					<c:when test="<%= PropsValues.ASSET_BROWSER_SEARCH_WITH_DATABASE %>">

						<%
						int assetEntriesTotal = AssetEntryLocalServiceUtil.getEntriesCount(groupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getUserName(), searchTerms.getTitle(), searchTerms.getDescription(), searchTerms.isAdvancedSearch(), searchTerms.isAndOperator());

						searchContainer.setTotal(assetEntriesTotal);

						List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(groupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getUserName(), searchTerms.getTitle(), searchTerms.getDescription(), searchTerms.isAdvancedSearch(), searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), "modifiedDate", "title", "DESC", "ASC");

						searchContainer.setResults(assetEntries);
						%>

					</c:when>
					<c:otherwise>

						<%
						Hits hits = null;

						if (searchTerms.isAdvancedSearch()) {
							hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), new long[] {searchTerms.getGroupId()}, themeDisplay.getUserId(), assetRendererFactory.getClassName(), subtypeSelectionId, searchTerms.getUserName(), searchTerms.getTitle(), searchTerms.getDescription(), null, null, WorkflowConstants.STATUS_APPROVED, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd());
						}
						else {
							hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), groupIds, themeDisplay.getUserId(), assetRendererFactory.getClassName(), subtypeSelectionId, searchTerms.getKeywords(), WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd());
						}

						List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(hits);

						searchContainer.setResults(assetEntries);
						searchContainer.setTotal(hits.getLength());
						%>

					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.asset.model.AssetEntry"
				escapedModel="<%= true %>"
				modelVar="assetEntry"
			>

				<%
				Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());
				%>

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= assetEntry.getTitle(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					name="description"
					value="<%= HtmlUtil.stripHtml(assetEntry.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="user-name"
					value="<%= HtmlUtil.escape(PortalUtil.getUserName(assetEntry)) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= assetEntry.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text
					name="site"
					value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text>
					<c:if test="<%= assetEntry.getEntryId() != refererAssetEntryId %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("assetentryid", assetEntry.getEntryId());
						data.put("assetclassname", assetEntry.getClassName());
						data.put("assettype", assetRendererFactory.getTypeName(locale, subtypeSelectionId));
						data.put("assettitle", assetEntry.getTitle(locale));
						data.put("groupdescriptivename", group.getDescriptiveName(locale));
						%>

						<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
					</c:if>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectAssetFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>