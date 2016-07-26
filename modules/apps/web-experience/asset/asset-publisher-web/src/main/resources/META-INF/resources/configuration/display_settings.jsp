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
%>

<c:if test="<%= assetPublisherDisplayContext.isShowEnableAddContentButton() %>">
	<aui:input helpMessage="show-add-content-button-help" name="preferences--showAddContentButton--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowAddContentButton() %>" />
</c:if>

<div class="display-template">
	<liferay-ddm:template-selector
		className="<%= AssetEntry.class.getName() %>"
		defaultDisplayStyle="<%= AssetPublisherWebConfigurationValues.DISPLAY_STYLE_DEFAULT %>"
		displayStyle="<%= assetPublisherDisplayContext.getDisplayStyle() %>"
		displayStyleGroupId="<%= assetPublisherDisplayContext.getDisplayStyleGroupId() %>"
		displayStyles="<%= Arrays.asList(AssetPublisherWebConfigurationValues.DISPLAY_STYLES) %>"
		label="display-template"
		refreshURL="<%= configurationRenderURL.toString() %>"
	/>
</div>

<aui:select cssClass="abstract-length hidden-field" helpMessage="abstract-length-key-help" name="preferences--abstractLength--" value="<%= assetPublisherDisplayContext.getAbstractLength() %>">
	<aui:option label="100" />
	<aui:option label="200" />
	<aui:option label="300" />
	<aui:option label="400" />
	<aui:option label="500" />
</aui:select>

<aui:input cssClass="hidden-field show-asset-title" name="preferences--showAssetTitle--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowAssetTitle() %>" />

<aui:input cssClass="hidden-field show-context-link" name="preferences--showContextLink--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowContextLink() %>" />

<aui:input cssClass="hidden-field show-extra-info" name="preferences--showExtraInfo--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowExtraInfo() %>" />

<aui:select cssClass="asset-link-behavior hidden-field" name="preferences--assetLinkBehavior--">
	<aui:option label="show-full-content" selected="<%= assetPublisherDisplayContext.isAssetLinkBehaviorShowFullContent() %>" value="showFullContent" />
	<aui:option label="view-in-context" selected="<%= assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet() %>" value="viewInPortlet" />
</aui:select>

<aui:input helpMessage="number-of-items-to-display-help" label="number-of-items-to-display" name="preferences--delta--" type="text" value="<%= assetPublisherDisplayContext.getDelta() %>">
	<aui:validator name="digits" />
</aui:input>

<aui:select name="preferences--paginationType--">

	<%
	for (String paginationType : assetPublisherDisplayContext.PAGINATION_TYPES) {
	%>

		<aui:option label="<%= paginationType %>" selected="<%= assetPublisherDisplayContext.isPaginationTypeSelected(paginationType) %>" />

	<%
	}
	%>

</aui:select>

<c:if test="<%= !AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX %>">
	<c:if test="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>">
		<aui:input label="exclude-assets-with-0-views" name="preferences--excludeZeroViewCount--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isExcludeZeroViewCount() %>" />
	</c:if>
</c:if>

<%
String helpMessage1 = "<em>" + LanguageUtil.format(request, "content-related-to-x", StringPool.DOUBLE_PERIOD, false) + "</em>";
String helpMessage2 = "<em>" + LanguageUtil.format(request, "content-with-tag-x", StringPool.DOUBLE_PERIOD, false) + "</em>";
%>

<aui:input helpMessage='<%= LanguageUtil.format(request, "such-as-x-or-x", new Object[] {helpMessage1, helpMessage2}, false) %>' name="preferences--showMetadataDescriptions--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowMetadataDescriptions() %>" />

<aui:input name="preferences--showAvailableLocales--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowAvailableLocales() %>" />

<c:if test="<%= assetPublisherDisplayContext.isEnableSetAsDefaultAssetPublisher() %>">
	<aui:input helpMessage="set-as-the-default-asset-publisher-for-this-page-help" label="set-as-the-default-asset-publisher-for-this-page" name="defaultAssetPublisher" type="toggle-switch" value="<%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) %>" />
</c:if>

<aui:field-wrapper helpMessage='<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() ? "enabling-openoffice-integration-provides-document-conversion-functionality" : StringPool.BLANK %>' label="enable-conversion-to">
	<div class="field-row">

		<%
		String[] conversions = DocumentConversionUtil.getConversions("html");

		for (String conversion : conversions) {
		%>

			<aui:input checked="<%= ArrayUtil.contains(assetPublisherDisplayContext.getExtensions(), conversion) %>" disabled="<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() %>" id='<%= "extensions" + conversion %>' inlineField="<%= true %>" label="<%= StringUtil.toUpperCase(conversion) %>" name="extensions" type="toggle-switch" value="<%= conversion %>" />

		<%
		}
		%>

	</div>
</aui:field-wrapper>

<aui:input name="preferences--enablePrint--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnablePrint() %>" />

<aui:input name="preferences--enableFlags--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableFlags() %>" />

<c:choose>
	<c:when test="<%= !assetPublisherDisplayContext.isShowEnablePermissions() %>">
		<aui:input name="preferences--enablePermissions--" type="hidden" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
	</c:when>
	<c:otherwise>
		<aui:input name="preferences--enablePermissions--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="<%= !assetPublisherDisplayContext.isShowEnableRelatedAssets() %>">
		<aui:input name="preferences--enableRelatedAssets--" type="hidden" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
	</c:when>
	<c:otherwise>
		<aui:input name="preferences--enableRelatedAssets--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
	</c:otherwise>
</c:choose>

<aui:input name="preferences--enableRatings--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableRatings() %>" />

<aui:input name="preferences--enableComments--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableComments() %>" />

<aui:input name="preferences--enableCommentRatings--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>" />

<liferay-ui:social-bookmarks-settings
	displayPosition="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayPosition() %>"
	displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
	enabled="<%= assetPublisherDisplayContext.isEnableSocialBookmarks() %>"
/>

<c:if test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
	<aui:input helpMessage="enable-tag-based-navigation-help" label="enable-tag-based-navigation" name="preferences--enableTagBasedNavigation--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableTagBasedNavigation() %>" />
</c:if>

<aui:input name="preferences--enableViewCountIncrement--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableViewCountIncrement() %>" />

<aui:script sandbox="<%= true %>">
	var selectDisplayStyle = $('#<portlet:namespace />displayStyle');

	function showHiddenFields() {
		var hiddenFields = $('.hidden-field');

		hiddenFields.parentsUntil('.general-display-settings', '.checkbox, .form-group').addClass('hide');

		var displayStyle = selectDisplayStyle.val();

		if (displayStyle == 'full-content') {
			showParent('.show-asset-title', '.checkbox');
			showParent('.show-context-link', '.checkbox');
			showParent('.show-extra-info', '.checkbox');
		}
		else {
			if (displayStyle == 'abstracts') {
				showParent('.abstract-length', '.form-group');
			}

			showParent('.asset-link-behavior', '.form-group');
		}
	}

	function showParent(child, parent) {
		$(child).parentsUntil('.general-display-settings', parent).removeClass('hide');
	}

	showHiddenFields();

	selectDisplayStyle.on('change', showHiddenFields);
</aui:script>