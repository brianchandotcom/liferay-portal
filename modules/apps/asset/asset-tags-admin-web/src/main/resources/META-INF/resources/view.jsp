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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String keywords = ParamUtil.getString(request, "keywords", null);

if (Validator.isNotNull(keywords)) {
	keywords = StringUtil.quote(keywords, StringPool.PERCENT);
}

SearchContainer tagsSearchContainer = new SearchContainer(renderRequest, renderResponse.createRenderURL(), null, "there-are-no-tags.-you-can-add-a-tag-by-clicking-the-plus-button-on-the-bottom-right-corner");

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");

tagsSearchContainer.setOrderByCol(orderByCol);

OrderByComparator<AssetTag> orderByComparator = null;

boolean orderByAsc = false;

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

if (orderByCol.equals("name")) {
	orderByComparator = new AssetTagNameComparator(orderByAsc);
}
else if (orderByCol.equals("usages")) {
	orderByComparator = new AssetTagAssetCountComparator(orderByAsc);
}

tagsSearchContainer.setOrderByComparator(orderByComparator);

tagsSearchContainer.setOrderByType(orderByType);

tagsSearchContainer.setRowChecker(new EmptyOnClickRowChecker(renderResponse));

int tagsCount = AssetTagServiceUtil.getTagsCount(scopeGroupId, keywords);

tagsSearchContainer.setTotal(tagsCount);

List<AssetTag> tags = AssetTagServiceUtil.getTags(scopeGroupId, keywords, tagsSearchContainer.getStart(), tagsSearchContainer.getEnd(), tagsSearchContainer.getOrderByComparator());

tagsSearchContainer.setResults(tags);
%>

<liferay-portlet:renderURL varImpl="portletURL" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="tags" />
	</aui:nav>

	<c:if test="<%= Validator.isNotNull(keywords) || (tagsCount > 0) %>">
		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<c:if test="<%= Validator.isNotNull(keywords) || (tagsCount > 0) %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="assetTags"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= renderResponse.createRenderURL() %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"name", "usages"} %>'
				portletURL="<%= renderResponse.createRenderURL() %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-random" id="mergeSelectedTags" />

			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedTags" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="deleteTagIds" type="hidden" />

	<liferay-ui:search-container
		id="assetTags"
		searchContainer="<%= tagsSearchContainer %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetTag"
			keyProperty="tagId"
			modelVar="tag"
		>
			<liferay-ui:search-container-column-text
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				name="usages"
				value="<%= String.valueOf(tag.getAssetCount()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/tag_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_TAG) %>">
	<portlet:renderURL var="editTagURL">
		<portlet:param name="mvcPath" value="/edit_tag.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-tag") %>' url="<%= editTagURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedTags').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL name="deleteTag" var="deleteURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				form.fm('deleteTagIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form, '<%= deleteURL %>');
			}
		}
	);

	$('#<portlet:namespace />mergeSelectedTags').on(
		'click',
		function() {
			<portlet:renderURL var="mergeURL">
				<portlet:param name="mvcPath" value="/merge_tag.jsp" />
				<portlet:param name="mergeTagIds" value="[$MERGE_TAGS_IDS$]" />
			</portlet:renderURL>

			var mergeURL = '<%= mergeURL %>';

			location.href = mergeURL.replace(escape('[$MERGE_TAGS_IDS$]'), Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		}
	);
</aui:script>