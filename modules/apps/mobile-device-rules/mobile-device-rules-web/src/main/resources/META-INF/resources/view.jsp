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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("groupId", String.valueOf(groupId));

RuleGroupSearch ruleGroupSearch = new RuleGroupSearch(liferayPortletRequest, PortletURLUtil.clone(portletURL, renderResponse));

RuleGroupSearchTerms searchTerms = (RuleGroupSearchTerms)ruleGroupSearch.getSearchTerms();

LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

params.put("includeGlobalScope", Boolean.TRUE);

int mdrRuleGroupsCount = MDRRuleGroupLocalServiceUtil.searchByKeywordsCount(groupId, searchTerms.getKeywords(), params, searchTerms.isAndOperator());

ruleGroupSearch.setTotal(mdrRuleGroupsCount);

List<MDRRuleGroup> mdrRuleGroups = MDRRuleGroupLocalServiceUtil.searchByKeywords(groupId, searchTerms.getKeywords(), params, searchTerms.isAndOperator(), ruleGroupSearch.getStart(), ruleGroupSearch.getEnd(), ruleGroupSearch.getOrderByComparator());

ruleGroupSearch.setResults(mdrRuleGroups);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel(LanguageUtil.get(request, "device-families"));
					});
			}
		}
	%>'
/>

<liferay-frontend:management-bar
	disabled="<%= mdrRuleGroupsCount <= 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="deviceFamilies"
>

	<%
	PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, renderResponse);
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<c:if test="<%= MDRPermission.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
			<portlet:renderURL var="viewRulesURL">
				<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/view" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="className" value="<%= className %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			</portlet:renderURL>

			<liferay-portlet:renderURL var="addRuleGroupURL">
				<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule_group" />
				<portlet:param name="redirect" value="<%= viewRulesURL %>" />
				<portlet:param name="backURL" value="<%= viewRulesURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(resourceBundle, "add-device-family") %>'
					url="<%= addRuleGroupURL %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<%
	PortletURL iteratorURL = PortletURLUtil.clone(portletURL, renderResponse);

	iteratorURL.setParameter("displayStyle", displayStyle);
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= iteratorURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= ruleGroupSearch.getOrderByCol() %>"
			orderByType="<%= ruleGroupSearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= iteratorURL %>"
		/>

		<c:if test="<%= (mdrRuleGroupsCount > 0) || searchTerms.isSearch() %>">
			<li>
				<aui:form action="<%= portletURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedDeviceFamilies"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/mobile_device_rules/edit_rule_group" var="editRuleGroupURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule_group" />
</portlet:actionURL>

<aui:form action="<%= editRuleGroupURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="ruleGroupIds" type="hidden" />

	<liferay-ui:search-container
		id="deviceFamilies"
		rowChecker="<%= new RuleGroupChecker(renderResponse) %>"
		searchContainer="<%= ruleGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRRuleGroup"
			escapedModel="<%= true %>"
			keyProperty="ruleGroupId"
			modelVar="ruleGroup"
		>

			<%
			Group group = GroupLocalServiceUtil.getGroup(ruleGroup.getGroupId());

			String rowHREF = null;

			if (MDRRuleGroupPermission.contains(permissionChecker, ruleGroup.getRuleGroupId(), ActionKeys.VIEW) && MDRPermission.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP)) {
			%>

				<portlet:renderURL var="editRulesURL">
					<portlet:param name="mvcPath" value="/view_rules.jsp" />
					<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroup.getRuleGroupId()) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				</portlet:renderURL>

			<%
				rowHREF = editRulesURL;
			}
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="mobile-portrait"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<span class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - ruleGroup.getModifiedDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</span>

						<h2 class="h5">
							<aui:a href="<%= rowHREF %>"><%= ruleGroup.getName(locale) %></aui:a>
						</h2>

						<span class="text-default">
							<%= HtmlUtil.escape(ruleGroup.getDescription(locale)) %>
						</span>
						<span class="text-default">
							<strong><liferay-ui:message key="scope" /></strong>: <%= LanguageUtil.get(resourceBundle, group.getScopeLabel(themeDisplay)) %>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/rule_group_actions.jsp"
					/>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/rule_group_actions.jsp"
							actionJspServletContext="<%= application %>"
							icon="mobile-portrait"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= HtmlUtil.escape(ruleGroup.getDescription(locale)) %>"
							title="<%= ruleGroup.getName(locale) %>"
							url="<%= rowHREF %>"
						>
							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - ruleGroup.getModifiedDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<%@ include file="/rule_group_columns.jspf" %>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
			type="more"
		/>
	</liferay-ui:search-container>
</aui:form>

<script>
	(function() {
		var deleteSelectedDeviceFamiliesButton = document.getElementById(
			'<portlet:namespace />deleteSelectedDeviceFamilies'
		);

		if (deleteSelectedDeviceFamiliesButton) {
			deleteSelectedDeviceFamiliesButton.addEventListener(
				'click',
				function() {
					if (
						confirm(
							'<%= UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-delete-this") %>'
						)
					) {
						submitForm(document.<portlet:namespace />fm);
					}
				}
			);
		}
	})();
</script>