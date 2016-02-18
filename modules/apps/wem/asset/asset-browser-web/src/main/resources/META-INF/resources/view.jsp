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

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectAsset");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("groupId", String.valueOf(groupId));
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

AssetBrowserSearchTerms searchTerms = (AssetBrowserSearchTerms)assetBrowserSearch.getSearchTerms();

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);

int assetEntriesTotal = 0;

if (AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE) {
	assetEntriesTotal = AssetEntryLocalServiceUtil.getEntriesCount(selectedGroupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), listable, false, false);

	assetBrowserSearch.setTotal(assetEntriesTotal);

	List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(selectedGroupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), listable, false, false, assetBrowserSearch.getStart(), assetBrowserSearch.getEnd(), "modifiedDate", "title", "DESC", "ASC");

	assetBrowserSearch.setResults(assetEntries);
}
else {
	int[] statuses = {WorkflowConstants.STATUS_APPROVED};

	if (showScheduled) {
		statuses = new int[] {WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_SCHEDULED};
	}

	Hits hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), ArrayUtil.clone(selectedGroupIds), themeDisplay.getUserId(), assetRendererFactory.getClassName(), subtypeSelectionId, searchTerms.getKeywords(), showNonindexable, statuses, assetBrowserSearch.getStart(), assetBrowserSearch.getEnd());

	assetEntriesTotal = hits.getLength();

	List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(hits);

	assetBrowserSearch.setResults(assetEntries);
	assetBrowserSearch.setTotal(assetEntriesTotal);
}

List<ManagementBarFilterItem> managementBarFilterItems = new ArrayList<>();

for (long curGroupId : selectedGroupIds) {
	Group curGroup = GroupLocalServiceUtil.fetchGroup(curGroupId);

	if (curGroup == null) {
		continue;
	}

	boolean active = false;

	if (groupId == curGroupId) {
		active = true;
	}

	PortletURL groupURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	groupURL.setParameter("groupId", String.valueOf(curGroupId));

	ManagementBarFilterItem managementBarFilterItem = new ManagementBarFilterItem(active, HtmlUtil.escape(curGroup.getDescriptiveName(locale)), groupURL.toString());

	managementBarFilterItems.add(managementBarFilterItem);
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav" searchContainer="<%= assetBrowserSearch %>">
		<aui:nav-item href="<%= portletURL %>" label="entries" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL %>" cssClass="container-fluid-1280" method="post" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= (assetEntriesTotal <= 0) && Validator.isNull(searchTerms.getKeywords()) %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>

			<%
			Group group = GroupLocalServiceUtil.fetchGroup(groupId);
			%>

			<liferay-frontend:management-bar-filter
				label="my-sites"
				managementBarFilterItems="<%= managementBarFilterItems %>"
				value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
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
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetEntry"
			escapedModel="<%= true %>"
			modelVar="assetEntry"
		>

			<%
			Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());
			%>

			<liferay-ui:search-container-column-text
				cssClass="text-strong"
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
				value="<%= PortalUtil.getUserName(assetEntry) %>"
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

<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>">
	<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-util:param name="typeSelection" value="<%= typeSelection %>" />
	<liferay-util:param name="subtypeSelectionId" value="<%= String.valueOf(subtypeSelectionId) %>" />
</liferay-util:include>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectAssetFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>