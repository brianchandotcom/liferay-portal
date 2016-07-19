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

<div id="<portlet:namespace />scopesBoxes">
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
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="scopeId" value="<%= AssetPublisherUtil.getScopeId(group, scopeGroupId) %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					icon="times"
					markupView="lexicon"
					url="<%= deleteURL %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
	</liferay-ui:search-container>

	<div class="select-asset-selector">
		<liferay-ui:icon-menu cssClass="select-existing-selector" direction="right" message="select" showArrow="<%= false %>" showWhenSingleIcon="<%= true %>">

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			for (Group group : availableGroups) {
				if (ArrayUtil.contains(assetPublisherDisplayContext.getGroupIds(), group.getGroupId())) {
					continue;
				}
			%>

				<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="addScopeURL">
					<portlet:param name="<%= Constants.CMD %>" value="add-scope" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
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

			PortletURL layoutSiteBrowserURL = PortletProviderUtil.getPortletURL(request, Group.class.getName(), PortletProvider.Action.BROWSE);
			%>

			<c:if test="<%= (GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), Layout.class.getName(), layout.getGroupId()) > 0) && (layoutSiteBrowserURL != null) %>">

				<%
				data = new HashMap<String, Object>();

				layoutSiteBrowserURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
				layoutSiteBrowserURL.setParameter("selectedGroupIds", StringUtil.merge(assetPublisherDisplayContext.getGroupIds()));
				layoutSiteBrowserURL.setParameter("privateLayout", String.valueOf(layout.isPrivateLayout()));
				layoutSiteBrowserURL.setParameter("type", "layoutScopes");
				layoutSiteBrowserURL.setParameter("eventName", eventName);
				layoutSiteBrowserURL.setPortletMode(PortletMode.VIEW);
				layoutSiteBrowserURL.setWindowState(LiferayWindowState.POP_UP);

				data.put("href", layoutSiteBrowserURL.toString());

				data.put("title", LanguageUtil.get(request, "pages"));
				%>

				<liferay-ui:icon
					cssClass="highlited scope-selector"
					data="<%= data %>"
					id="selectGroup"
					message='<%= LanguageUtil.get(request, "pages") + StringPool.TRIPLE_PERIOD %>'
					method="get"
					url="javascript:;"
				/>
			</c:if>

			<%
			PortletURL siteBrowserURL = PortletProviderUtil.getPortletURL(renderRequest, Group.class.getName(), PortletProvider.Action.BROWSE);

			List<String> types = new ArrayList<String>();

			if (PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED)) {
				types.add("sites-that-i-administer");
			}

			if (GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), layout.getGroupId(), Boolean.TRUE) > 0) {
				types.add("child-sites");
			}

			Group siteGroup = themeDisplay.getSiteGroup();

			if (!siteGroup.isRoot()) {
				types.add("parent-sites");
			}
			%>

			<c:if test="<%= (siteBrowserURL != null) && !types.isEmpty() && !siteGroup.isLayoutPrototype() && !siteGroup.isLayoutSetPrototype() %>">

				<%
				data = new HashMap<String, Object>();

				siteBrowserURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
				siteBrowserURL.setParameter("selectedGroupIds", StringUtil.merge(assetPublisherDisplayContext.getGroupIds()));
				siteBrowserURL.setParameter("types", StringUtil.merge(types));
				siteBrowserURL.setParameter("filter", "contentSharingWithChildrenEnabled");
				siteBrowserURL.setParameter("includeCurrentGroup", Boolean.FALSE.toString());
				siteBrowserURL.setParameter("eventName", eventName);
				siteBrowserURL.setPortletMode(PortletMode.VIEW);
				siteBrowserURL.setWindowState(LiferayWindowState.POP_UP);

				data.put("href", siteBrowserURL.toString());

				data.put("title", LanguageUtil.get(request, "sites"));
				%>

				<liferay-ui:icon
					cssClass="highlited scope-selector"
					data="<%= data %>"
					id="selectManageableGroup"
					message='<%= LanguageUtil.get(request, "other-site") + StringPool.TRIPLE_PERIOD %>'
					method="get"
					url="javascript:;"
				/>
			</c:if>
		</liferay-ui:icon-menu>
	</div>
</div>


<aui:script sandbox="<%= true %>">
	var form = document.<portlet:namespace />fm;

	$('body').on(
		'click',
		'.scope-selector a',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<%= eventName %>',
					id: '<%= eventName %>' + currentTarget.attr('id'),
					title: currentTarget.data('title'),
					uri: currentTarget.data('href')
				},
				function(event) {
					form.<portlet:namespace /><%= Constants.CMD %>.value = 'add-scope';
					form.<portlet:namespace />groupId.value = event.groupid;

					submitForm(form);
				}
			);
		}
	);
</aui:script>