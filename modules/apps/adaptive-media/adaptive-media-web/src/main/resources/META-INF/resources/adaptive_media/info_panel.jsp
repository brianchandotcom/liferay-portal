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

<%@ include file="/adaptive_media/init.jsp" %>

<%
List<AdaptiveMediaImageConfigurationEntry> selectedConfigurationEntries = (List)request.getAttribute(AdaptiveMediaWebKeys.SELECTED_CONFIGURATION_ENTRIES);

AdaptiveMediaImageConfigurationEntry configurationEntry = null;

int selectedConfigurationEntriesSize = 0;

if (ListUtil.isNotEmpty(selectedConfigurationEntries)) {
	configurationEntry = selectedConfigurationEntries.get(0);

	selectedConfigurationEntriesSize = selectedConfigurationEntries.size();
}
%>

<div class="sidebar-header">
	<c:choose>
		<c:when test="<%= (selectedConfigurationEntriesSize == 1) %>">
			<ul class="sidebar-actions">

				<%
				request.setAttribute("info_panel.jsp-configurationEntry", configurationEntry);
				%>

				<li>
					<liferay-util:include page="/adaptive_media/image_configuration_entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title">
				<%= HtmlUtil.escape(configurationEntry.getName()) %>
			</h4>

			<h5>
				<liferay-ui:message key="image-resolution" />
			</h5>
		</c:when>
		<c:when test="<%= (selectedConfigurationEntriesSize > 1) %>">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= selectedConfigurationEntriesSize %>" key="x-items-are-selected" /></h4>
		</c:when>
		<c:otherwise>
			<h4 class="sidebar-title"><liferay-ui:message key="adaptive-media" /></h4>
		</c:otherwise>
	</c:choose>
</div>

<liferay-ui:tabs cssClass="navbar-no-collapse" names="details" refresh="<%= false %>" type="dropdown">
	<liferay-ui:section>
		<div class="sidebar-body">
			<dl class="sidebar-block">
				<c:choose>
					<c:when test="<%= (selectedConfigurationEntriesSize == 1) %>">
						<dt class="h5">
							<liferay-ui:message key="name" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(configurationEntry.getName()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="state" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= configurationEntry.isEnabled() ? LanguageUtil.get(request, "enabled") : LanguageUtil.get(request, "disabled") %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="adapted-images" />
						</dt>
						<dd class="h6 sidebar-caption">

							<%
							int adaptedImages = AdaptiveMediaImageEntryLocalServiceUtil.getAdaptiveMediaImageEntriesCount(themeDisplay.getCompanyId(), configurationEntry.getUUID());

							int totalImages = AdaptiveMediaImageEntryLocalServiceUtil.getExpectedAdaptiveMediaImageEntriesCount(themeDisplay.getCompanyId());
							%>

							<%= Math.min(adaptedImages, totalImages) + "/" + totalImages %>
						</dd>

						<%
						Map<String, String> properties = configurationEntry.getProperties();
						%>

						<dt class="h5">
							<liferay-ui:message key="max-width" />
						</dt>
						<dd class="h6 sidebar-caption">

							<%
							String maxWidth = properties.get("max-width");
							%>

							<%= maxWidth.equals("0") ? LanguageUtil.get(request, "auto") : HtmlUtil.escape(maxWidth + "px") %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="max-height" />
						</dt>
						<dd class="h6 sidebar-caption">

							<%
							String maxHeight = properties.get("max-height");
							%>

							<%= maxHeight.equals("0") ? LanguageUtil.get(request, "auto") : HtmlUtil.escape(maxHeight + "px") %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="id" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(configurationEntry.getUUID()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="description" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(configurationEntry.getDescription()) %>
						</dd>
					</c:when>
					<c:when test="<%= (selectedConfigurationEntriesSize > 1) %>">
						<dt class="h5">
							<liferay-ui:message arguments="<%= selectedConfigurationEntriesSize %>" key="x-items-are-selected" />
						</dt>
					</c:when>
					<c:otherwise>
						<dt class="h5">
							<liferay-ui:message key="num-of-items" />
						</dt>
						<dd class="h6 sidebar-caption">

							<%
							List<AdaptiveMediaImageConfigurationEntry> configurationEntries = (List)request.getAttribute(AdaptiveMediaWebKeys.CONFIGURATION_ENTRIES_LIST);
							%>

							<%= configurationEntries.size() %>
						</dd>
					</c:otherwise>
				</c:choose>
			</dl>
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>