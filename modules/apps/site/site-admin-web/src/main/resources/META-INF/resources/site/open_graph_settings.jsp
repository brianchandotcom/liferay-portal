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
Group group = (Group)request.getAttribute("site.group");
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

long groupId = group.getGroupId();

if (liveGroup != null) {
	groupId = liveGroup.getGroupId();
}

SiteSEOEntry siteSEOEntry = siteAdminDisplayContext.getSelSiteSEOEntry(groupId);
%>

<div class="form-group" id="<portlet:namespace />idOptions">
	<aui:input id="openSiteGraphEnabled" label="enable-open-graph" name="openSiteGraphEnabled" type="checkbox" value="<%= siteSEOEntry.isOpenGraphSiteEnabled() %>" />
</div>

<p class="text-muted">
	<liferay-ui:message key="enable-open-graph-description" />
</p>

<h4 class="sheet-tertiary-title">
	<liferay-ui:message key="open-graph-image" />
</h4>

<p class="text-muted">
	<liferay-ui:message key="open-graph-image-description" />
</p>

<div>
	<label class="control-label"><liferay-ui:message key="image" /></label>

	<div class="input-group">
		<div class="input-group-item">
			<aui:input disabled="<%= true %>" label="<%= StringPool.BLANK %>" name="openGraphImageURL" placeholder="image" type="text" value="" wrapperCssClass="w-100" />
		</div>

		<div class="input-group-item input-group-item-shrink">
			<aui:button name="openGraphImageButton" value="select" />
		</div>
	</div>
</div>

<div id="<portlet:namespace />openGraphSettings">
	<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" />
</div>

<div class="form-group">
	<label><liferay-ui:message key="preview" /></label>
</div>

<aui:script>
	var openGraphImageButton = document.getElementById(
		'<portlet:namespace />openGraphImageButton'
	);

	var openSiteGraphEnabledCheck = document.getElementById(
		'<portlet:namespace />openSiteGraphEnabled'
	);

	if (openSiteGraphEnabledCheck && openGraphImageButton) {
		openSiteGraphEnabledCheck.addEventListener('click', function(event) {
			Liferay.Util.toggleDisabled(
				openGraphImageButton,
				!event.target.checked
			);
		});
	}
</aui:script>