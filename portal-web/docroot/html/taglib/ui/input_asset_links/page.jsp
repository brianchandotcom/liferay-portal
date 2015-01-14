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

<%@ include file="/html/taglib/ui/input_asset_links/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_asset_links_page") + StringPool.UNDERLINE;

String eventName = randomNamespace + "selectAsset";

long assetEntryId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:input-asset-links:assetEntryId"));
String className = (String)request.getAttribute("liferay-ui:input-asset-links:className");

List<AssetLink> assetLinks = new ArrayList<AssetLink>();

String assetLinksSearchContainerPrimaryKeys = ParamUtil.getString(request, "assetLinksSearchContainerPrimaryKeys");

if (Validator.isNull(assetLinksSearchContainerPrimaryKeys) && SessionErrors.isEmpty(portletRequest) && (assetEntryId > 0)) {
	List<AssetLink> directAssetLinks = AssetLinkLocalServiceUtil.getDirectLinks(assetEntryId);

	for (AssetLink assetLink : directAssetLinks) {
		AssetEntry assetLinkEntry = null;

		if ((assetEntryId > 0) || (assetLink.getEntryId1() == assetEntryId)) {
			assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId2());
		}
		else {
			assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId1());
		}

		AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetLinkEntry.getClassName());

		if (assetRendererFactory.isActive(company.getCompanyId())) {
			assetLinks.add(assetLink);
		}
	}
}
else {
	String[] assetEntriesPrimaryKeys = StringUtil.split(assetLinksSearchContainerPrimaryKeys);

	for (String assetEntryPrimaryKey : assetEntriesPrimaryKeys) {
		long assetEntryPrimaryKeyLong = GetterUtil.getLong(assetEntryPrimaryKey);

		AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(assetEntryPrimaryKeyLong);

		AssetLink assetLink = AssetLinkLocalServiceUtil.createAssetLink(0);

		if (assetEntryId > 0) {
			assetLink.setEntryId1(assetEntryId);
		}
		else {
			assetLink.setEntryId1(0);
		}

		assetLink.setEntryId2(assetEntry.getEntryId());

		assetLinks.add(assetLink);
	}
}

long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);

boolean stagedLocally = scopeGroup.isStaged() && !scopeGroup.isStagedRemotely();
boolean stagedReferrerPortlet = false;

if (stagedLocally) {
	AssetRendererFactory referrerAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	stagedReferrerPortlet = scopeGroup.isStagedPortlet(referrerAssetRendererFactory.getPortletId());
}

PortletURL assetBrowserURL = PortletURLFactoryUtil.create(request, PortletKeys.ASSET_BROWSER, controlPanelPlid, PortletRequest.RENDER_PHASE);

assetBrowserURL.setParameter("struts_action", "/asset_browser/view");
assetBrowserURL.setParameter("eventName", eventName);
assetBrowserURL.setPortletMode(PortletMode.VIEW);
assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);
%>

<liferay-ui:icon-menu cssClass="select-existing-selector" icon='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' id='<%= randomNamespace + "inputAssetLinks" %>' message="select" showWhenSingleIcon="<%= true %>">

	<%
	for (AssetRendererFactory assetRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId())) {
		if (assetRendererFactory.isLinkable() && assetRendererFactory.isSelectable()) {
			if (assetEntryId > 0) {
				assetBrowserURL.setParameter("refererAssetEntryId", String.valueOf(assetEntryId));
			}

			long groupId = scopeGroupId;

			if (stagedLocally) {
				boolean stagedReferencePortlet = scopeGroup.isStagedPortlet(assetRendererFactory.getPortletId());

				if (stagedReferrerPortlet && !stagedReferencePortlet) {
					groupId = scopeGroup.getLiveGroupId();
				}
			}

			assetBrowserURL.setParameter("groupId", String.valueOf(groupId));
			assetBrowserURL.setParameter("selectedGroupIds", themeDisplay.getCompanyGroupId() + "," + groupId);
			assetBrowserURL.setParameter("typeSelection", assetRendererFactory.getClassName());

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("href", assetBrowserURL.toString());
			data.put("title", LanguageUtil.format(pageContext, "select-x", assetRendererFactory.getTypeName(locale, false)));

			String type = assetRendererFactory.getTypeName(locale, false);

			data.put("type", assetRendererFactory.getClassName());
		%>

			<liferay-ui:icon
				cssClass="asset-selector"
				data="<%= data %>"
				id="<%= FriendlyURLNormalizerUtil.normalize(type) %>"
				message="<%= assetRendererFactory.getTypeName(locale, false) %>"
				src="<%= assetRendererFactory.getIconPath(portletRequest) %>"
				url="javascript:;"
			/>

		<%
		}
	}
	%>

</liferay-ui:icon-menu>

<br />

<div class="separator"><!-- --></div>

<liferay-util:buffer var="removeLinkIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	headerNames="type,title,scope,null"
>
	<liferay-ui:search-container-results
		results="<%= assetLinks %>"
		total="<%= assetLinks.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.asset.model.AssetLink"
		keyProperty="entryId2"
		modelVar="assetLink"
	>

		<%
		AssetEntry assetLinkEntry = null;

		if ((assetEntryId > 0) || (assetLink.getEntryId1() == assetEntryId)) {
			assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId2());
		}
		else {
			assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId1());
		}

		assetLinkEntry = assetLinkEntry.toEscapedModel();

		AssetRendererFactory assetRendererFactory = assetLinkEntry.getAssetRendererFactory();

		Group assetLinkEntryGroup = GroupLocalServiceUtil.getGroup(assetLinkEntry.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= assetRendererFactory.getTypeName(locale, false) %>"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= assetLinkEntry.getTitle(locale) %>"
		/>

		<liferay-ui:search-container-column-text
			name="scope"
			value="<%= HtmlUtil.escape(assetLinkEntryGroup.getDescriptiveName(locale)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= assetLinkEntry.getEntryId() %>" href="javascript:;"><%= removeLinkIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:input name="assetLinkEntryIds" type="hidden" />

<aui:script use="aui-base,escape,liferay-search-container">
	A.getBody().delegate(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					eventName: '<%= eventName %>',
					id: '<%= eventName %>' + event.currentTarget.attr('id'),
					title: event.currentTarget.attr('data-title'),
					uri: event.currentTarget.attr('data-href')
				},
				function(event) {
					var searchContainerName = '<%= portletResponse.getNamespace() %>assetLinksSearchContainer';

					searchContainer = Liferay.SearchContainer.get(searchContainerName);

					var entryLink = '<a class="modify-link" data-rowId="' + event.assetentryid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeLinkIcon) %></a>';

					searchContainer.addRow([event.assettype, A.Escape.html(event.assettitle), A.Escape.html(event.groupdescriptivename), entryLink], event.assetentryid);

					searchContainer.updateDataStore();
				}
			);
		},
		'.asset-selector a'
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<%= portletResponse.getNamespace() %>assetLinksSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>