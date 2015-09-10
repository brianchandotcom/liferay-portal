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
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="tags" />
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-ui:input-search markupView="lexicon" />
	</aui:nav-bar-search>
</aui:nav-bar>

<div class="management-bar-container">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-portlet:renderURL varImpl="portletURL" />

			<liferay-frontend:management-bar-display-buttons
				displayStyleURL="<%= portletURL %>"
				displayViews='<%= new String[]{"list"} %>'
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>

	<liferay-frontend:management-bar
		cssClass="management-bar-no-collapse"
		id="tagsActionsButton"
	>

		<liferay-frontend:management-bar-buttons>
			<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-random" id="mergeSelectedTags" />

			<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedTags" />
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>
</div>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="deleteTagIds" type="hidden" />

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-tags"
		id="assetTags"
		rowChecker="<%= new RowChecker(renderResponse) %>"
	>

		<liferay-ui:search-container-results>

			<%
			String keywords = ParamUtil.getString(request, "keywords", null);

			total = AssetTagServiceUtil.getTagsCount(scopeGroupId, keywords);

			searchContainer.setTotal(total);

			results = AssetTagServiceUtil.getTags(scopeGroupId, keywords, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

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
			>
				<c:choose>
					<c:when test="<%= tag.getAssetCount() > 0 %>">
						<liferay-ui:message arguments="<%= tag.getAssetCount() %>" key="used-in-x-assets" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="this-tag-is-not-used" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="checkbox-cell entry-action"
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

	$('#<portlet:namespace />assetTagsSearchContainer').on(
		'click',
		'input[type=checkbox]',
		function() {
			var hide = (Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			$('#<portlet:namespace />tagsActionsButton').toggleClass('on', !hide);
		}
	);

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