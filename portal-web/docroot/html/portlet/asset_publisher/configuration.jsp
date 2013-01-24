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

String emailParam = "emailAssetEntryAdded";

String currentLanguageId = LanguageUtil.getLanguageId(request);

String editorParam = emailParam + "Body_" + currentLanguageId;
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
	<aui:input name="groupId" type="hidden" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="assetEntryId" type="hidden" />
	<aui:input name="assetEntryOrder" type="hidden" value="-1" />
	<aui:input name="assetEntryType" type="hidden" />

	<%
	String rootPortletId = PortletConstants.getRootPortletId(portletResource);
	%>

	<liferay-util:buffer var="selectStyle">
		<c:choose>
			<c:when test="<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) %>">
				<aui:input name="preferences--selectionStyle--" type="hidden" value="dynamic" />
			</c:when>
			<c:otherwise>
				<aui:fieldset label="asset-selection">
					<aui:input checked='<%= selectionStyle.equals("dynamic") %>' id="selectionStyleDynamic" label="dynamic" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="dynamic" />

					<aui:input checked='<%= selectionStyle.equals("manual") %>' id="selectionStyleManual" label="manual" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="manual" />
				</aui:fieldset>
			</c:otherwise>
		</c:choose>
	</liferay-util:buffer>

	<liferay-util:buffer var="selectScope">
		<aui:select label="" name="preferences--defaultScope--" onChange='<%= renderResponse.getNamespace() + "selectScope();" %>'>

			<%
			long layoutScopeGroupId = 0;
			%>

			<aui:option label="<%= _getName(themeDisplay, themeDisplay.getScopeGroup()) %>" selected="<%= (groupIds.length == 1) && (themeDisplay.getScopeGroupId() == groupIds[0]) %>" value="<%= true %>" />

			<c:if test="<%= layout.hasScopeGroup() %>">

				<%
				Group layoutScopeGroup = layout.getScopeGroup();

				layoutScopeGroupId = layoutScopeGroup.getGroupId();
				%>

				<aui:option label="<%= _getName(themeDisplay, layoutScopeGroup) %>" selected="<%= (groupIds.length == 1) && (layoutScopeGroupId == groupIds[0]) %>" value="<%= AssetPublisherUtil.getScopeId(layoutScopeGroup, themeDisplay.getScopeGroupId()) %>" />
			</c:if>

			<aui:option label="<%= _getName(themeDisplay, company.getGroup()) %>" selected="<%= (groupIds.length == 1) && (themeDisplay.getCompanyGroupId() == groupIds[0]) %>" value="<%= AssetPublisherUtil.getScopeId(company.getGroup(), themeDisplay.getScopeGroupId()) %>" />

			<optgroup label="----------"></optgroup>

			<aui:option cssClass="advanced-options" label='<%= LanguageUtil.get(pageContext,"advanced-options") + "..." %>' selected="<%= (groupIds.length > 1) || ((groupIds.length == 1) && (groupIds[0] != themeDisplay.getScopeGroupId()) && (groupIds[0] != layoutScopeGroupId) && (groupIds[0] != themeDisplay.getCompanyGroupId())) %>" value="<%= false %>" />
		</aui:select>

		<%
		Set<Group> availableGroups = new HashSet<Group>();

		availableGroups.add(company.getGroup());
		availableGroups.add(themeDisplay.getScopeGroup());

		for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
			if (curLayout.hasScopeGroup()) {
				availableGroups.add(curLayout.getScopeGroup());
			}
		}

		List<Group> selectedGroups = GroupLocalServiceUtil.getGroups(groupIds);
		%>

		<div class="<%= defaultScope ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />scopesBoxes">
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

					<%
					group = group.toEscapedModel();
					%>

					<liferay-ui:search-container-column-text
						name="name"
					>
						<liferay-ui:icon
							label="<%= true %>"
							message="<%= _getName(themeDisplay, group) %>"
							src="<%= group.getIconURL(themeDisplay) %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(pageContext, _getType(themeDisplay, group)) %>"
					/>

					<liferay-ui:search-container-column-text
						align="right"
					>
						<liferay-portlet:actionURL portletConfiguration="true" var="deleteURL">
							<portlet:param name="<%= Constants.CMD %>" value="remove-scope" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="scopeId" value="<%= AssetPublisherUtil.getScopeId(group, scopeGroupId) %>" />
						</liferay-portlet:actionURL>

						<liferay-ui:icon-delete
							url="<%= deleteURL %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= false %>" />
			</liferay-ui:search-container>

			<div class="select-asset-selector">
				<liferay-ui:icon-menu align="left" cssClass="select-existing-selector" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="select" showWhenSingleIcon="<%= true %>">

					<%
					for (Group group : availableGroups) {
						if (ArrayUtil.contains(groupIds, group.getGroupId())) {
							continue;
						}
					%>

						<liferay-portlet:actionURL portletConfiguration="true" var="addScopeURL">
							<portlet:param name="<%= Constants.CMD %>" value="add-scope" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="scopeId" value="<%= AssetPublisherUtil.getScopeId(group, scopeGroupId) %>" />
						</liferay-portlet:actionURL>

						<liferay-ui:icon id='<%= "scope" + group.getGroupId() %>' message="<%= _getName(themeDisplay, group) %>" method="post" src="<%= group.getIconURL(themeDisplay) %>" url="<%= addScopeURL %>" />

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</div>
		</div>
	</liferay-util:buffer>

	<%
	request.setAttribute("configuration.jsp-classTypesAssetRendererFactories", classTypesAssetRendererFactories);
	request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
	request.setAttribute("configuration.jsp-editorParam", editorParam);
	request.setAttribute("configuration.jsp-emailParam", emailParam);
	request.setAttribute("configuration.jsp-redirect", redirect);
	request.setAttribute("configuration.jsp-rootPortletId", rootPortletId);
	request.setAttribute("configuration.jsp-selectScope", selectScope);
	request.setAttribute("configuration.jsp-selectStyle", selectStyle);
	%>

	<c:choose>
		<c:when test='<%= selectionStyle.equals("manual") %>'>
			<liferay-util:include page="/html/portlet/asset_publisher/configuration_manual.jsp" />
		</c:when>
		<c:when test='<%= selectionStyle.equals("dynamic") %>'>
			<liferay-util:include page="/html/portlet/asset_publisher/configuration_dynamic.jsp" />
		</c:when>
	</c:choose>
</aui:form>

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

	function <portlet:namespace />selectAsset(assetEntryId, assetClassName, assetType, assetEntryTitle, groupName) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'add-selection';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryType.value = assetClassName;

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
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType','false','<portlet:namespace />classNamesBoxes');
	Liferay.Util.toggleSelectBox('<portlet:namespace />defaultScope','false','<portlet:namespace />scopesBoxes');

	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />selectionStyle);
</aui:script>

<%!
private String _getName(ThemeDisplay themeDisplay, Group group) throws Exception {
	String name = null;

	if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
		StringBundler sb = new StringBundler(5);

		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "current-site"));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale())));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();
	}
	else if (group.isLayout() && (group.getClassPK() == themeDisplay.getPlid())) {
		StringBundler sb = new StringBundler(5);

		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "current-page"));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale())));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();
	}
	else if (group.isLayoutPrototype()) {
		name = LanguageUtil.get(themeDisplay.getLocale(), "default");
	}
	else {
		name = HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale()));
	}

	return name;
}

private String _getType(ThemeDisplay themeDisplay, Group group) {
	String type = "site";

	if (group.getGroupId() == themeDisplay.getCompanyGroupId()) {
		type = "global";
	}
	else if (group.isLayout()) {
		type = "page";
	}

	return type;
}
%>