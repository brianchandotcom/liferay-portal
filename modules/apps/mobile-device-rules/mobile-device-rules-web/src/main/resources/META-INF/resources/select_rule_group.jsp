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
String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectRuleGroup");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_rule_group.jsp");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("eventName", eventName);

RuleGroupSearch ruleGroupSearch = new RuleGroupSearch(liferayPortletRequest, portletURL);

RuleGroupDisplayTerms displayTerms = (RuleGroupDisplayTerms)ruleGroupSearch.getDisplayTerms();
RuleGroupSearchTerms searchTerms = (RuleGroupSearchTerms)ruleGroupSearch.getSearchTerms();

if (displayTerms.getGroupId() == 0) {
	displayTerms.setGroupId(groupId);
	searchTerms.setGroupId(groupId);
}

LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

params.put("includeGlobalScope", Boolean.TRUE);

int mdrRuleGroupsCount = MDRRuleGroupLocalServiceUtil.searchByKeywordsCount(searchTerms.getGroupId(), searchTerms.getKeywords(), params, searchTerms.isAndOperator());

ruleGroupSearch.setTotal(mdrRuleGroupsCount);

List<MDRRuleGroup> mdrRuleGroups = MDRRuleGroupLocalServiceUtil.searchByKeywords(searchTerms.getGroupId(), searchTerms.getKeywords(), params, searchTerms.isAndOperator(), ruleGroupSearch.getStart(), ruleGroupSearch.getEnd());

ruleGroupSearch.setResults(mdrRuleGroups);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav-item label="device-families" selected="<%= true %>" />

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<c:if test="<%= (mdrRuleGroupsCount > 0) || searchTerms.isSearch() %>">
	<liferay-frontend:management-bar>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>
</c:if>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectRuleGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= ruleGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRRuleGroup"
			escapedModel="<%= true %>"
			keyProperty="ruleGroupId"
			modelVar="ruleGroup"
		>

			<liferay-ui:search-container-column-text
				name="name"
			>

				<%
				MDRRuleGroupInstance ruleGroupInstance = MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(className, classPK, ruleGroup.getRuleGroupId());
				%>

				<c:choose>
					<c:when test="<%= ruleGroupInstance == null %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("rulegroupid", ruleGroup.getRuleGroupId());
						data.put("rulegroupname", ruleGroup.getName());
						%>

						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= ruleGroup.getName(locale) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= ruleGroup.getName(locale) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= ruleGroup.getDescription(locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" type="more" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectRuleGroupFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>