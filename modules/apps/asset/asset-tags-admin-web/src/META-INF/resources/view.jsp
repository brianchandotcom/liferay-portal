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

<aui:form name="fm">
	<aui:input name="deleteTagIds" type="hidden" />

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-tags"
		rowChecker="<%= new RowChecker(renderResponse) %>"
	>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_TAG) %>">
					<portlet:renderURL var="editTagURL">
						<portlet:param name="mvcPath" value="/edit_tag.jsp" />
					</portlet:renderURL>

					<aui:nav-item href="<%= editTagURL %>" iconCssClass="icon-plus" label="add-tag" />
				</c:if>

				<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="tagsActionsButton" label="actions">
					<aui:nav-item iconCssClass="icon-random" id="mergeSelectedTags" label="merge" />

					<aui:nav-item cssClass="item-remove" iconCssClass="icon-remove" id="deleteSelectedTags" label="delete" />
				</aui:nav-item>
			</aui:nav>

			<aui:nav-bar-search>
				<div class="form-search">
					<liferay-ui:input-search />
				</div>
			</aui:nav-bar-search>
		</aui:nav-bar>

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
				cssClass="entry-action"
				path="/tag_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer').on(
		'click',
		'input[type=checkbox]',
		function() {
			var hide = (Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			$('#<portlet:namespace />tagsActionsButton').toggleClass('hide', hide);
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