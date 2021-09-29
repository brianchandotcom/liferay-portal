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
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String eventName = "_" + HtmlUtil.escapeJS(assetPublisherDisplayContext.getPortletResource()) + "_selectSite";

Set<Group> availableGroups = new HashSet<Group>();

availableGroups.add(company.getGroup());
availableGroups.add(themeDisplay.getScopeGroup());

if (layout.hasScopeGroup()) {
	availableGroups.add(layout.getScopeGroup());
}

List<Group> selectedGroups = GroupLocalServiceUtil.getGroups(assetPublisherDisplayContext.getGroupIds());
%>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	iteratorURL="<%= configurationRenderURL %>"
	total="<%= selectedGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= selectedGroups %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		keyProperty="groupId"
		modelVar="group"
	>
		<liferay-ui:search-container-column-text
			name="name"
			truncate="<%= true %>"
			value="<%= group.getScopeDescriptiveName(themeDisplay) %>"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="deleteURL">
				<portlet:param name="<%= Constants.CMD %>" value="remove-scope" />
				<portlet:param name="redirect" value="<%= configurationRenderURL.toString() %>" />
				<portlet:param name="scopeId" value="<%= assetPublisherHelper.getScopeId(group, scopeGroupId) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				icon="times-circle"
				markupView="lexicon"
				url="<%= deleteURL %>"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>

<liferay-ui:icon-menu
	cssClass="select-existing-selector"
	direction="right"
	message="select"
	showArrow="<%= false %>"
	showWhenSingleIcon="<%= true %>"
>

	<%
	for (Group group : availableGroups) {
		if (ArrayUtil.contains(assetPublisherDisplayContext.getGroupIds(), group.getGroupId())) {
			continue;
		}
	%>

		<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="addScopeURL">
			<portlet:param name="<%= Constants.CMD %>" value="add-scope" />
			<portlet:param name="redirect" value="<%= configurationRenderURL.toString() %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			id='<%= "scope" + group.getGroupId() %>'
			message="<%= group.getScopeDescriptiveName(themeDisplay) %>"
			method="post"
			url="<%= addScopeURL %>"
		/>

	<%
	}
	%>

	<liferay-ui:icon
		cssClass="highlited scope-selector"
		id="selectManageableGroup"
		message='<%= LanguageUtil.get(request, "other-site-or-asset-library") + StringPool.TRIPLE_PERIOD %>'
		method="get"
		url="javascript:;"
	/>
</liferay-ui:icon-menu>

<%
ItemSelector itemSelector = (ItemSelector)request.getAttribute(AssetPublisherWebKeys.ITEM_SELECTOR);

GroupItemSelectorCriterion groupItemSelectorCriterion = new GroupItemSelectorCriterion(layout.isPrivateLayout());

groupItemSelectorCriterion.setDesiredItemSelectorReturnTypes(new GroupItemSelectorReturnType());
groupItemSelectorCriterion.setIncludeChildSites(true);
groupItemSelectorCriterion.setIncludeLayoutScopes(true);
groupItemSelectorCriterion.setIncludeMySites(false);
groupItemSelectorCriterion.setIncludeParentSites(true);
groupItemSelectorCriterion.setIncludeRecentSites(false);
groupItemSelectorCriterion.setIncludeSitesThatIAdminister(true);

PortletURL itemSelectorURL = PortletURLBuilder.create(
	itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, groupItemSelectorCriterion)
).setPortletResource(
	assetPublisherDisplayContext.getPortletResource()
).setParameter(
	"groupId", layout.getGroupId()
).setParameter(
	"plid", layout.getPlid()
).buildPortletURL();
%>

<aui:script sandbox="<%= true %>">
	const form = document.<portlet:namespace />fm;

	const scopeSelect = document.getElementById(
		'<portlet:namespace />selectManageableGroup'
	);

	if (scopeSelect) {
		scopeSelect.addEventListener('click', (event) => {
			event.preventDefault();

			const opener = Liferay.Util.getOpener();

			opener.Liferay.Util.openSelectionModal({
				id: '<%= eventName %>' + event.currentTarget.id,
				onSelect: function (event) {
					Liferay.Util.postForm(form, {
						data: {
							cmd: 'add-scope',
							groupId: event.groupid,
						},
					});
				},
				selectEventName: '<%= eventName %>',
				title: '<liferay-ui:message key="scopes" />',
				url: '<%= itemSelectorURL.toString() %>',
			});
		});
	}
</aui:script>