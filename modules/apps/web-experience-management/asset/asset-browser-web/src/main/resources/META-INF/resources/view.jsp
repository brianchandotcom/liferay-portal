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

long[] filterGroupIds = selectedGroupIds;

if (groupId > 0) {
	filterGroupIds = new long[] {groupId};
}

AssetBrowserSearch assetBrowserSearch = new AssetBrowserSearch(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse));

AssetBrowserSearchTerms searchTerms = (AssetBrowserSearchTerms)assetBrowserSearch.getSearchTerms();

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);

int assetEntriesTotal = 0;

if (AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE) {
	assetEntriesTotal = AssetEntryLocalServiceUtil.getEntriesCount(filterGroupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), listable, false, false);

	assetBrowserSearch.setTotal(assetEntriesTotal);

	List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(filterGroupIds, new long[] {assetRendererFactory.getClassNameId()}, searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.getKeywords(), listable, false, false, assetBrowserSearch.getStart(), assetBrowserSearch.getEnd(), "modifiedDate", "title", "DESC", "ASC");

	assetBrowserSearch.setResults(assetEntries);
}
else {
	int[] statuses = {WorkflowConstants.STATUS_APPROVED};

	if (showScheduled) {
		statuses = new int[] {WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_SCHEDULED};
	}

	Hits hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), filterGroupIds, themeDisplay.getUserId(), assetRendererFactory.getClassName(), subtypeSelectionId, searchTerms.getKeywords(), showNonindexable, statuses, assetBrowserSearch.getStart(), assetBrowserSearch.getEnd());

	assetEntriesTotal = hits.getLength();

	List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(hits);

	assetBrowserSearch.setResults(assetEntries);
	assetBrowserSearch.setTotal(assetEntriesTotal);
}

List<ManagementBarFilterItem> managementBarFilterItems = new ArrayList<>();

selectedGroupIds = ArrayUtil.append(new long[] {0}, selectedGroupIds);

for (long curGroupId : selectedGroupIds) {
	Group curGroup = GroupLocalServiceUtil.fetchGroup(curGroupId);

	if ((curGroup == null) && (curGroupId > 0)) {
		continue;
	}

	boolean active = false;

	if (groupId == curGroupId) {
		active = true;
	}

	PortletURL groupURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	groupURL.setParameter("groupId", String.valueOf(curGroupId));

	String label = StringPool.BLANK;

	if (curGroup != null) {
		label = HtmlUtil.escape(curGroup.getDescriptiveName(locale));
	}
	else {
		label = LanguageUtil.get(request, "all");
	}

	ManagementBarFilterItem managementBarFilterItem = new ManagementBarFilterItem(active, label, groupURL.toString());

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

			<%
			String label = StringPool.BLANK;

			if (groupId > 0) {
				Group group = GroupLocalServiceUtil.fetchGroup(groupId);

				label = HtmlUtil.escape(group.getDescriptiveName(locale));
			}
			else {
				label = LanguageUtil.get(request, "all");
			}
			%>

			<liferay-frontend:management-bar-filter
				managementBarFilterItems="<%= managementBarFilterItems %>"
				value="<%= label %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
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

			String cssClass = StringPool.BLANK;

			Map<String, Object> data = new HashMap<String, Object>();

			if (assetEntry.getEntryId() != refererAssetEntryId) {
				data.put("assetentryid", assetEntry.getEntryId());
				data.put("assetclassname", assetEntry.getClassName());
				data.put("assetclasspk", assetEntry.getClassPK());
				data.put("assettype", assetRendererFactory.getTypeName(locale, subtypeSelectionId));
				data.put("assettitle", assetEntry.getTitle(locale));
				data.put("groupdescriptivename", group.getDescriptiveName(locale));

				cssClass = "selector-button";
			}
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-ui:user-portrait
							cssClass="user-icon-lg"
							userId="<%= assetEntry.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date modifiedDate = assetEntry.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" /></span>
						</h6>

						<h5>
							<c:choose>
								<c:when test="<%= assetEntry.getEntryId() != refererAssetEntryId %>">
									<aui:a cssClass="<%= cssClass %>" data="<%= data %>" href="javascript:;">
										<%= assetEntry.getTitle(locale) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= assetEntry.getTitle(locale) %>
								</c:otherwise>
							</c:choose>
						</h5>

						<h6 class="text-default">
							<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("col-md-2 col-sm-4 col-xs-6");

					AssetRenderer assetRenderer = assetEntry.getAssetRenderer();
					%>

					<liferay-ui:search-container-column-text>
						<c:choose>
							<c:when test="<%= Validator.isNotNull(assetRenderer.getThumbnailPath(renderRequest)) %>">
								<liferay-frontend:vertical-card
									cssClass="<%= cssClass %>"
									data="<%= data %>"
									imageUrl="<%= assetRenderer.getThumbnailPath(renderRequest) %>"
									subtitle="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
									title="<%= assetEntry.getTitle(locale) %>"
								/>
							</c:when>
							<c:otherwise>
								<liferay-frontend:icon-vertical-card
									cssClass="<%= cssClass %>"
									data="<%= data %>"
									icon="<%= assetRendererFactory.getIconCssClass() %>"
									subtitle="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
									title="<%= assetEntry.getTitle(locale) %>"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="text-strong"
						name="title"
					>
						<c:choose>
							<c:when test="<%= assetEntry.getEntryId() != refererAssetEntryId %>">
								<aui:a cssClass="<%= cssClass %>" data="<%= data %>" href="javascript:;">
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
				</c:when>
			</c:choose>
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