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

<%@ include file="/html/taglib/init.jsp" %>

<portlet:defineObjects />

<%
boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:asset-tags-selector:autoFocus"));
String className = (String)request.getAttribute("liferay-ui:asset-tags-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-tags-selector:classPK"));
String contentCallback = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-tags-selector:contentCallback"));
String curTags = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-tags-selector:curTags"));
long[] groupIds = (long[])request.getAttribute("liferay-ui:asset-tags-selector:groupIds");
String hiddenInput = (String)request.getAttribute("liferay-ui:asset-tags-selector:hiddenInput");
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-tags-selector:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean(request.getAttribute("liferay-ui:asset-tags-selector:ignoreRequestValue"));

if (Validator.isNotNull(className) && (classPK > 0)) {
	List<AssetTag> tags = AssetTagServiceUtil.getTags(className, classPK);

	curTags = ListUtil.toString(tags, AssetTag.NAME_ACCESSOR);
}

if (!ignoreRequestValue) {
	String curTagsParam = request.getParameter(hiddenInput);

	if (Validator.isNotNull(curTagsParam)) {
		curTags = curTagsParam;
	}
}
%>

<div class="lfr-tags-selector-content" id="<%= namespace + id %>assetTagsSelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" />

	<input class="lfr-tag-selector-input" id="<%= id %>assetTagNames" maxlength="75" size="15" title="<liferay-ui:message key="add-tags" />" type="text" />
</div>

<aui:script use="liferay-asset-tags-selector">
	new Liferay.AssetTagsSelector(
		{
			allowSuggestions: <%= PropsValues.ASSET_TAG_SUGGESTIONS_ENABLED %>,
			contentBox: '#<%= namespace + id %>assetTagsSelector',

			<c:if test="<%= PropsValues.ASSET_TAG_SUGGESTIONS_ENABLED && Validator.isNotNull(contentCallback) %>">
				contentCallback: function() {
					if (window.<%= contentCallback %>) {
						return <%= contentCallback %>();
					}
				},
			</c:if>

			curEntries: '<%= HtmlUtil.escapeJS(curTags) %>',

			<c:if test="<%= groupIds != null %>">
				groupIds: '<%= StringUtil.merge(groupIds) %>',
			</c:if>

			hiddenInput: '#<%= namespace + hiddenInput %>',
			input: '#<%= id %>assetTagNames',
			instanceVar: '<%= namespace + id %>',
			portalModelResource: <%= Validator.isNotNull(className) && (ResourceActionsUtil.isPortalModelResource(className) || className.equals(Group.class.getName())) %>
		}
	).render();

	<c:if test="<%= autoFocus %>">
		Liferay.Util.focusFormField('#<%= id %>assetTagNames');
	</c:if>
</aui:script>