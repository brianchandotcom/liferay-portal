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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String eventName = "_" + HtmlUtil.escapeJS(assetPublisherDisplayContext.getPortletResource()) + "_selectSite";

List<AssetRendererFactory> classTypesAssetRendererFactories = new ArrayList<AssetRendererFactory>();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
	<aui:input name="groupId" type="hidden" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="assetEntryId" type="hidden" />
	<aui:input name="assetEntryOrder" type="hidden" value="-1" />
	<aui:input name="assetEntryType" type="hidden" />
	<aui:input name="scopeId" type="hidden" />

	<liferay-util:buffer var="selectStyle">
		<c:choose>
			<c:when test="<%= !assetPublisherDisplayContext.isSelectionStyleEnabled() %>">
				<aui:input name="preferences--selectionStyle--" type="hidden" value="dynamic" />
			</c:when>
			<c:otherwise>
				<aui:fieldset label="asset-selection">
					<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>" id="selectionStyleDynamic" label="dynamic" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="dynamic" />

					<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>" id="selectionStyleManual" label="manual" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="manual" />
				</aui:fieldset>
			</c:otherwise>
		</c:choose>
	</liferay-util:buffer>

	<liferay-util:buffer var="selectScope">

		<%
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
				emptyResultsMessage="no-groups-were-found"
				iteratorURL="<%= configurationRenderURL %>"
			>
				<liferay-ui:search-container-results
					results="<%= selectedGroups %>"
					total="<%= selectedGroups.size() %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Group"
					modelVar="group"
				>
					<liferay-ui:search-container-column-text
						name="name"
					>
						<liferay-ui:icon
							iconCssClass="<%= group.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= group.getScopeDescriptiveName(themeDisplay) %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
					/>

					<liferay-ui:search-container-column-text
						align="right"
					>
						<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="deleteURL">
							<portlet:param name="<%= Constants.CMD %>" value="remove-scope" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="scopeId" value="<%= AssetPublisherUtil.getScopeId(group, scopeGroupId) %>" />
						</liferay-portlet:actionURL>

						<liferay-ui:icon
							iconCssClass="icon-remove"
							url="<%= deleteURL %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= false %>" />
			</liferay-ui:search-container>

			<div class="select-asset-selector">
				<liferay-ui:icon-menu cssClass="select-existing-selector" direction="right" icon="../aui/plus" message="select" showWhenSingleIcon="<%= true %>">

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
							<portlet:param name="scopeId" value="<%= AssetPublisherUtil.getScopeId(group, scopeGroupId) %>" />
						</liferay-portlet:actionURL>

						<liferay-ui:icon
							iconCssClass="<%= group.getIconCssClass() %>"
							id='<%= "scope" + group.getGroupId() %>'
							message="<%= group.getScopeDescriptiveName(themeDisplay) %>"
							method="post"
							url="<%= addScopeURL %>"
						/>

					<%
					}
					%>

					<c:if test="<%= GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), Layout.class.getName(), layout.getGroupId()) > 0 %>">

						<%
						PortletURL layoutSiteBrowserURL = PortletURLFactoryUtil.create(request, PortletKeys.SITE_BROWSER, PortalUtil.getControlPanelPlid(company.getCompanyId()), PortletRequest.RENDER_PHASE);

						layoutSiteBrowserURL.setParameter("struts_action", "/site_browser/view");
						layoutSiteBrowserURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
						layoutSiteBrowserURL.setParameter("selectedGroupIds", StringUtil.merge(assetPublisherDisplayContext.getGroupIds()));
						layoutSiteBrowserURL.setParameter("type", "layoutScopes");
						layoutSiteBrowserURL.setParameter("eventName", eventName);
						layoutSiteBrowserURL.setPortletMode(PortletMode.VIEW);
						layoutSiteBrowserURL.setWindowState(LiferayWindowState.POP_UP);

						String layoutSiteBrowserURLString = HttpUtil.addParameter(layoutSiteBrowserURL.toString(), "doAsGroupId", scopeGroupId);

						data = new HashMap<String, Object>();

						data.put("href", layoutSiteBrowserURLString);
						data.put("title", LanguageUtil.get(request, "pages"));
						%>

						<liferay-ui:icon
							cssClass="highlited scope-selector"
							data="<%= data %>"
							iconCssClass="icon-plus"
							id="selectGroup"
							message='<%= LanguageUtil.get(request, "pages") + StringPool.TRIPLE_PERIOD %>'
							method="get"
							url="javascript:;"
						/>
					</c:if>

					<%
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

					<c:if test="<%= !types.isEmpty() %>">

						<%
						PortletURL siteBrowserURL = PortletURLFactoryUtil.create(request, PortletKeys.SITE_BROWSER, PortalUtil.getControlPanelPlid(company.getCompanyId()), PortletRequest.RENDER_PHASE);

						siteBrowserURL.setParameter("struts_action", "/site_browser/view");
						siteBrowserURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
						siteBrowserURL.setParameter("selectedGroupIds", StringUtil.merge(assetPublisherDisplayContext.getGroupIds()));
						siteBrowserURL.setParameter("types", StringUtil.merge(types));
						siteBrowserURL.setParameter("filter", "contentSharingWithChildrenEnabled");
						siteBrowserURL.setParameter("eventName", eventName);
						siteBrowserURL.setPortletMode(PortletMode.VIEW);
						siteBrowserURL.setWindowState(LiferayWindowState.POP_UP);

						String siteBrowserURLString = HttpUtil.addParameter(siteBrowserURL.toString(), "doAsGroupId", scopeGroupId);

						data = new HashMap<String, Object>();

						data.put("href", siteBrowserURLString);
						data.put("title", LanguageUtil.get(request, "sites"));
						%>

						<liferay-ui:icon
							cssClass="highlited scope-selector"
							data="<%= data %>"
							iconCssClass="icon-plus"
							id="selectManageableGroup"
							message='<%= LanguageUtil.get(request, "other-site") + StringPool.TRIPLE_PERIOD %>'
							method="get"
							url="javascript:;"
						/>
					</c:if>
				</liferay-ui:icon-menu>
			</div>
		</div>
	</liferay-util:buffer>

	<%
	request.setAttribute("configuration.jsp-classTypesAssetRendererFactories", classTypesAssetRendererFactories);
	request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
	request.setAttribute("configuration.jsp-redirect", redirect);
	request.setAttribute("configuration.jsp-selectScope", selectScope);
	request.setAttribute("configuration.jsp-selectStyle", selectStyle);
	%>

	<c:choose>
		<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
			<liferay-util:include page="/html/portlet/asset_publisher/configuration_manual.jsp" />
		</c:when>
		<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>">
			<liferay-util:include page="/html/portlet/asset_publisher/configuration_dynamic.jsp" />
		</c:when>
	</c:choose>
</aui:form>

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
					form.<portlet:namespace />scopeId.value = event.scopeid;

					submitForm(form);
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />chooseSelectionStyle() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'selection-style';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />moveSelectionDown(assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'move-selection-down';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />moveSelectionUp(assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'move-selection-up';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveSelectBoxes',
		function() {
			if (document.<portlet:namespace />fm.<portlet:namespace />classNameIds) {
				document.<portlet:namespace />fm.<portlet:namespace />classNameIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentClassNameIds);
			}

			<%
			for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
				String className = AssetPublisherUtil.getClassName(curRendererFactory);
			%>

				if (document.<portlet:namespace />fm.<portlet:namespace />classTypeIds<%= className %>) {
					document.<portlet:namespace />fm.<portlet:namespace />classTypeIds<%= className %>.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace /><%= className %>currentClassTypeIds);
				}

			<%
			}
			%>

			document.<portlet:namespace />fm.<portlet:namespace />metadataFields.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentMetadataFields);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType', 'false', '<portlet:namespace />classNamesBoxes');
</aui:script>