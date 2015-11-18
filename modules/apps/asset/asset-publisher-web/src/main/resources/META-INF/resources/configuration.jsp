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
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String eventName = "_" + HtmlUtil.escapeJS(assetPublisherDisplayContext.getPortletResource()) + "_selectSite";

List<AssetRendererFactory<?>> classTypesAssetRendererFactories = new ArrayList<>();
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

					<c:if test="<%= GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), Layout.class.getName(), layout.getGroupId()) > 0 %>">

						<%
						data = new HashMap<String, Object>();

						PortletURL layoutSiteBrowserURL = PortletProviderUtil.getPortletURL(request, Group.class.getName(), PortletProvider.Action.BROWSE);

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

					<c:if test="<%= !types.isEmpty() && !siteGroup.isLayoutPrototype() && !siteGroup.isLayoutSetPrototype() %>">

						<%
						data = new HashMap<String, Object>();

						PortletURL siteBrowserURL = PortletProviderUtil.getPortletURL(renderRequest, Group.class.getName(), PortletProvider.Action.BROWSE);

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
			<liferay-util:include page="/configuration_manual.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>">
			<liferay-util:include page="/configuration_dynamic.jsp" servletContext="<%= application %>" />
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
					form.<portlet:namespace />groupId.value = event.groupid;

					submitForm(form);
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />chooseSelectionStyle() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('selection-style');

		submitForm(form);
	}

	function <portlet:namespace />moveSelectionDown(assetEntryOrder) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('move-selection-down');
		form.fm('redirect').val('<%= HtmlUtil.escapeJS(currentURL) %>');
		form.fm('assetEntryOrder').val(assetEntryOrder);

		submitForm(form);
	}

	function <portlet:namespace />moveSelectionUp(assetEntryOrder) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('move-selection-up');
		form.fm('redirect').val('<%= HtmlUtil.escapeJS(currentURL) %>');
		form.fm('assetEntryOrder').val(assetEntryOrder);

		submitForm(form);
	}

	function <portlet:namespace />saveSelectBoxes() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('classNameIds').val(Util.listSelect(form.fm('currentClassNameIds')));

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = AssetPublisherUtil.getClassName(curRendererFactory);
		%>

			form.fm('classTypeIds<%= className %>').val(Util.listSelect(form.fm('<%= className %>currentClassTypeIds')));

		<%
		}
		%>

		form.fm('metadataFields').val(Util.listSelect(form.fm('currentMetadataFields')));

		submitForm(form);
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType', 'false', '<portlet:namespace />classNamesBoxes');
</aui:script>