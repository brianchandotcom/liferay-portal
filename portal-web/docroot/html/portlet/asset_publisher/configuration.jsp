<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

String typeSelection = ParamUtil.getString(request, "typeSelection", StringPool.BLANK);

AssetRendererFactory rendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);

List<AssetRendererFactory> classTypesAssetRendererFactories = new ArrayList<AssetRendererFactory>();
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
	<aui:input name="groupId" type="hidden" />
	<aui:input name="assetEntryType" type="hidden" value="<%= typeSelection %>" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="assetEntryId" type="hidden" />
	<aui:input name="assetParentId" type="hidden" />
	<aui:input name="preferences--assetTitle--" type="hidden" />
	<aui:input name="assetEntryOrder" type="hidden" value="-1" />

	<c:if test="<%= typeSelection.equals(StringPool.BLANK) %>">

		<%
		String rootPortletId = PortletConstants.getRootPortletId(portletResource);
		%>

		<c:choose>
			<c:when test="<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) %>">
				<aui:input name="preferences--selectionStyle--" type="hidden" value="dynamic" />
			</c:when>
			<c:otherwise>
				<aui:select label="asset-selection" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>'>
					<aui:option label="dynamic" selected='<%= selectionStyle.equals("dynamic") %>'/>
					<aui:option label="manual" selected='<%= selectionStyle.equals("manual") %>'/>
				</aui:select>
			</c:otherwise>
		</c:choose>

		<liferay-util:buffer var="selectScope">
			<aui:select label="" name="preferences--defaultScope--" onChange='<%= renderResponse.getNamespace() + "selectScope();" %>'>

				<%
				long layoutScopeGroupId = 0;
				%>

				<aui:option label="<%= _getName(themeDisplay, themeDisplay.getScopeGroup(), locale) %>" selected="<%= (groupIds.length == 1) && (themeDisplay.getScopeGroupId() == groupIds[0]) %>" value="<%= themeDisplay.getScopeGroupId() %>" />

				<c:if test="<%= layout.hasScopeGroup() %>">

					<%
					Group layoutScopeGroup = layout.getScopeGroup();

					layoutScopeGroupId = layoutScopeGroup.getGroupId();
					%>

					<aui:option label="<%= _getName(themeDisplay, layoutScopeGroup, locale) %>" selected="<%= (groupIds.length == 1) && (layoutScopeGroupId == groupIds[0]) %>" value="<%= _getScopeId(layoutScopeGroup, themeDisplay.getScopeGroupId()) %>" />
				</c:if>

				<aui:option label="<%= _getName(themeDisplay, company.getGroup(), locale) %>" selected="<%= (groupIds.length == 1) && (themeDisplay.getCompanyGroupId() == groupIds[0]) %>" value="<%= _getScopeId(company.getGroup(), themeDisplay.getScopeGroupId()) %>" />

				<optgroup label="-----------"></optgroup>

				<aui:option cssClass="advanced-options" label='<%= LanguageUtil.get(pageContext,"advanced-options") + "..." %>' selected="<%= (groupIds.length > 1) || ((groupIds.length == 1) && (groupIds[0] != themeDisplay.getScopeGroupId()) && (groupIds[0] != layoutScopeGroupId) && (groupIds[0] != themeDisplay.getCompanyGroupId())) %>" value="<%= false %>" />
			</aui:select>

			<%
			Set<Group> groups = new HashSet<Group>();

			groups.add(company.getGroup());
			groups.add(themeDisplay.getScopeGroup());

			for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
				if (curLayout.hasScopeGroup()) {
					groups.add(curLayout.getScopeGroup());
				}
			}

			// Left list

			List<KeyValuePair> scopesLeftList = new ArrayList<KeyValuePair>();

			for (long groupId : groupIds) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				scopesLeftList.add(new KeyValuePair(_getScopeId(group, scopeGroupId), _getName(themeDisplay, group, locale)));
			}

			// Right list

			List<KeyValuePair> scopesRightList = new ArrayList<KeyValuePair>();

			Arrays.sort(groupIds);

			for (Group group : groups) {
				if (Arrays.binarySearch(groupIds, group.getGroupId()) < 0) {
					scopesRightList.add(new KeyValuePair(_getScopeId(group, scopeGroupId), _getName(themeDisplay, group, locale)));
				}
			}

			scopesRightList = ListUtil.sort(scopesRightList, new KeyValuePairComparator(false, true));
			%>

			<aui:input name="preferences--scopeIds--" type="hidden" />

			<div class="<%= defaultScope ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />scopesBoxes">
				<liferay-ui:input-move-boxes
					leftBoxName="currentScopeIds"
					leftList="<%= scopesLeftList %>"
					leftReorder="true"
					leftTitle="selected"
					rightBoxName="availableScopeIds"
					rightList="<%= scopesRightList %>"
					rightTitle="available"
				/>
			</div>
		</liferay-util:buffer>

		<%
		request.setAttribute("configuration.jsp-classTypesAssetRendererFactories", classTypesAssetRendererFactories);
		request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
		request.setAttribute("configuration.jsp-redirect", redirect);
		request.setAttribute("configuration.jsp-rootPortletId", rootPortletId);
		request.setAttribute("configuration.jsp-selectScope", selectScope);
		%>

		<c:choose>
			<c:when test='<%= selectionStyle.equals("manual") %>'>
				<liferay-util:include page="/html/portlet/asset_publisher/configuration_manual.jsp" />
			</c:when>
			<c:when test='<%= selectionStyle.equals("dynamic") %>'>
				<liferay-util:include page="/html/portlet/asset_publisher/configuration_dynamic.jsp" />
			</c:when>
		</c:choose>
	</c:if>
</aui:form>

<c:if test="<%= Validator.isNotNull(typeSelection) %>">
	<%@ include file="/html/portlet/asset_publisher/select_asset.jspf" %>
</c:if>

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

	function <portlet:namespace />selectAsset(assetEntryId, assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'add-selection';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectScope() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'select-scope';

		if (document.<portlet:namespace />fm.<portlet:namespace />defaultScope.value != 'false') {
			submitForm(document.<portlet:namespace />fm);
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveSelectBoxes',
		function() {
			if (document.<portlet:namespace />fm.<portlet:namespace />scopeIds) {
				document.<portlet:namespace />fm.<portlet:namespace />scopeIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeIds);
			}

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

	Liferay.provide(
		window,
		'<portlet:namespace />selectScopes',
		function() {
			if (document.<portlet:namespace />fm.<portlet:namespace />scopeIds) {
				document.<portlet:namespace />fm.<portlet:namespace />scopeIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeIds);
			}

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'select-scope';

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType','false','<portlet:namespace />classNamesBoxes');
	Liferay.Util.toggleSelectBox('<portlet:namespace />defaultScope','false','<portlet:namespace />scopesBoxes');

	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />selectionStyle);

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			if ((event.fromBox.get('id') == '<portlet:namespace />currentScopeIds') || ( event.toBox.get('id') == '<portlet:namespace />currentScopeIds')) {
				<portlet:namespace />selectScopes();
			}
		}
	);
</aui:script>

<%!
private String _getName(ThemeDisplay themeDisplay, Group group, Locale locale) throws Exception {
	String name = null;

	if (group.isLayoutPrototype()) {
		name = LanguageUtil.get(locale, "default");
	}
	else if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
		StringBundler sb = new StringBundler(5);

		sb.append(LanguageUtil.get(locale, "current-site"));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(HtmlUtil.escape(group.getDescriptiveName(locale)));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();
	}
	else if (group.isLayout() && (group.getClassPK() == themeDisplay.getPlid())) {
		StringBundler sb = new StringBundler(5);

		sb.append(LanguageUtil.get(locale, "current-page"));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(HtmlUtil.escape(group.getDescriptiveName(locale)));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();
	}
	else {
		name = HtmlUtil.escape(group.getDescriptiveName(locale));
	}

	return name;
}

private String _getScopeId(Group group, long scopeGroupId) throws Exception {
	String key = null;

	if (group.isLayout()) {
		Layout layout = LayoutLocalServiceUtil.getLayout(group.getClassPK());

		key = AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX + layout.getLayoutId();
	}
	else if (group.isLayoutPrototype() || (group.getGroupId() == scopeGroupId)) {
		key = AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX + GroupConstants.DEFAULT;
	}
	else {
		key = AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX + group.getGroupId();
	}

	return key;
}
%>