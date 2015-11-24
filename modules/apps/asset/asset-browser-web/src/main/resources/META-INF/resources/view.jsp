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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

long groupId = ParamUtil.getLong(request, "groupId");
long[] selectedGroupIds = StringUtil.split(ParamUtil.getString(request, "selectedGroupIds"), 0L);
long refererAssetEntryId = ParamUtil.getLong(request, "refererAssetEntryId");
String typeSelection = ParamUtil.getString(request, "typeSelection");
long subtypeSelectionId = ParamUtil.getLong(request, "subtypeSelectionId");
boolean showNonindexable = ParamUtil.getBoolean(request, "showNonindexable");
boolean showScheduled = ParamUtil.getBoolean(request, "showScheduled");

Boolean listable = null;

String listableValue = ParamUtil.getString(request, "listable", null);

if (Validator.isNotNull(listableValue)) {
	listable = ParamUtil.getBoolean(request, "listable", true);
}

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectAsset");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("selectedGroupIds", StringUtil.merge(selectedGroupIds));
portletURL.setParameter("refererAssetEntryId", String.valueOf(refererAssetEntryId));
portletURL.setParameter("typeSelection", typeSelection);
portletURL.setParameter("subtypeSelectionId", String.valueOf(subtypeSelectionId));

if (listable != null) {
	portletURL.setParameter("listable", String.valueOf(listable));
}

portletURL.setParameter("eventName", eventName);

request.setAttribute("view.jsp-portletURL", portletURL);

AssetBrowserSearch assetBrowserSearch = new AssetBrowserSearch(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav" searchContainer="<%= assetBrowserSearch %>">
		<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<liferay-util:param name="typeSelection" value="<%= typeSelection %>" />
			<liferay-util:param name="subtypeSelectionId" value="<%= String.valueOf(subtypeSelectionId) %>" />
		</liferay-util:include>
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL %>" cssClass="container-fluid-1280" method="post" name="searchFm">
			<%@ include file="/search.jspf" %>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL %>" cssClass="container-fluid-1280" method="post" name="selectAssetFm">
	<aui:input name="typeSelection" type="hidden" value="<%= typeSelection %>" />

	<liferay-ui:search-container
		searchContainer="<%= assetBrowserSearch %>"
	>

		<%
		AssetBrowserSearchTerms searchTerms = (AssetBrowserSearchTerms)assetBrowserSearch.getSearchTerms();

		AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);
		%>

		<liferay-ui:search-container-results>
			<c:choose>
				<c:when test="<%= AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE %>">

					<%
					int assetEntriesTotal = AssetEntryLocalServiceUtil.getEntriesCount(selectedGroupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getUserName(), searchTerms.getTitle(), searchTerms.getDescription(), listable, searchTerms.isAdvancedSearch(), searchTerms.isAndOperator());

					assetBrowserSearch.setTotal(assetEntriesTotal);

					List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(selectedGroupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getUserName(), searchTerms.getTitle(), searchTerms.getDescription(), listable, searchTerms.isAdvancedSearch(), searchTerms.isAndOperator(), assetBrowserSearch.getStart(), assetBrowserSearch.getEnd(), "modifiedDate", "title", "DESC", "ASC");

					assetBrowserSearch.setResults(assetEntries);
					%>

				</c:when>
				<c:otherwise>

					<%
					Hits hits = null;

					int[] statuses = {WorkflowConstants.STATUS_APPROVED};

					if (showScheduled) {
						statuses = new int[] {WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_SCHEDULED};
					}

					if (searchTerms.isAdvancedSearch()) {
						hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), new long[] {searchTerms.getGroupId()}, themeDisplay.getUserId(), assetRendererFactory.getClassName(), subtypeSelectionId, searchTerms.getUserName(), searchTerms.getTitle(), searchTerms.getDescription(), null, null, showNonindexable, statuses, searchTerms.isAndOperator(), assetBrowserSearch.getStart(), assetBrowserSearch.getEnd());
					}
					else {
						hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), selectedGroupIds, themeDisplay.getUserId(), assetRendererFactory.getClassName(), subtypeSelectionId, searchTerms.getKeywords(), showNonindexable, statuses, assetBrowserSearch.getStart(), assetBrowserSearch.getEnd());
					}

					List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(hits);

					assetBrowserSearch.setResults(assetEntries);
					assetBrowserSearch.setTotal(hits.getLength());
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
			>
				<c:choose>
					<c:when test="<%= assetEntry.getEntryId() != refererAssetEntryId %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("assetentryid", assetEntry.getEntryId());
						data.put("assetclassname", assetEntry.getClassName());
						data.put("assetclasspk", assetEntry.getClassPK());
						data.put("assettype", assetRendererFactory.getTypeName(locale, subtypeSelectionId));
						data.put("assettitle", assetEntry.getTitle(locale));
						data.put("groupdescriptivename", group.getDescriptiveName(locale));
						%>

						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= assetEntry.getTitle(locale) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= assetEntry.getTitle(locale) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

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
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectAssetFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>