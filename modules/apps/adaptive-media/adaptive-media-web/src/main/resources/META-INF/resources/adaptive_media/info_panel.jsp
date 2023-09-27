<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/adaptive_media/init.jsp" %>

<%
List<AMImageConfigurationEntry> selectedAMImageConfigurationEntries = (List)request.getAttribute(AMWebKeys.SELECTED_CONFIGURATION_ENTRIES);

AMImageConfigurationEntry amImageConfigurationEntry = null;

int selectedConfigurationEntriesSize = 0;

if (ListUtil.isNotEmpty(selectedAMImageConfigurationEntries)) {
	amImageConfigurationEntry = selectedAMImageConfigurationEntries.get(0);

	selectedConfigurationEntriesSize = selectedAMImageConfigurationEntries.size();
}
%>

<div class="sidebar-header">
	<c:choose>
		<c:when test="<%= selectedConfigurationEntriesSize == 1 %>">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title">
						<%= HtmlUtil.escape(amImageConfigurationEntry.getName()) %>
					</h4>

					<h5 class="component-subtitle">
						<liferay-ui:message key="image-resolution" />
					</h5>
				</div>

				<div class="autofit-col">

					<%
					request.setAttribute("info_panel.jsp-amImageConfigurationEntry", amImageConfigurationEntry);
					%>

					<liferay-util:include page="/adaptive_media/image_configuration_entry_action.jsp" servletContext="<%= application %>" />
				</div>
			</div>
		</c:when>
		<c:when test="<%= selectedConfigurationEntriesSize > 1 %>">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><liferay-ui:message arguments="<%= selectedConfigurationEntriesSize %>" key="x-items-are-selected" /></h4>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><liferay-ui:message key="adaptive-media" /></h4>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<liferay-ui:tabs
	cssClass="navbar-no-collapse"
	names="details"
	refresh="<%= false %>"
	type="dropdown"
>
	<liferay-ui:section>
		<div class="sidebar-body">
			<dl class="sidebar-dl sidebar-section">
				<c:choose>
					<c:when test="<%= selectedConfigurationEntriesSize == 1 %>">
						<dt class="sidebar-dt">
							<liferay-ui:message key="name" />
						</dt>
						<dd class="sidebar-dd">
							<%= HtmlUtil.escape(amImageConfigurationEntry.getName()) %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="state" />
						</dt>
						<dd class="sidebar-dd">
							<%= amImageConfigurationEntry.isEnabled() ? LanguageUtil.get(request, "enabled") : LanguageUtil.get(request, "disabled") %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="adapted-images" />
						</dt>
						<dd class="sidebar-dd">

							<%
							int adaptedImages = AMImageEntryLocalServiceUtil.getAMImageEntriesCount(themeDisplay.getCompanyId(), amImageConfigurationEntry.getUUID());

							int totalImages = AMImageEntryLocalServiceUtil.getExpectedAMImageEntriesCount(themeDisplay.getCompanyId());
							%>

							<%= Math.min(adaptedImages, totalImages) %>/<%= totalImages %>
						</dd>

						<%
						Map<String, String> properties = amImageConfigurationEntry.getProperties();
						%>

						<dt class="sidebar-dt">
							<liferay-ui:message key="max-width" />
						</dt>
						<dd class="sidebar-dd">

							<%
							String maxWidth = properties.get("max-width");
							%>

							<%= (Validator.isNull(maxWidth) || maxWidth.equals("0")) ? LanguageUtil.get(request, "auto") : HtmlUtil.escape(maxWidth + "px") %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="max-height" />
						</dt>
						<dd class="sidebar-dd">

							<%
							String maxHeight = properties.get("max-height");
							%>

							<%= (Validator.isNull(maxHeight) || maxHeight.equals("0")) ? LanguageUtil.get(request, "auto") : HtmlUtil.escape(maxHeight + "px") %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="id" />
						</dt>
						<dd class="sidebar-dd">
							<%= HtmlUtil.escape(amImageConfigurationEntry.getUUID()) %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="description" />
						</dt>
						<dd class="sidebar-dd">
							<%= HtmlUtil.escape(amImageConfigurationEntry.getDescription()) %>
						</dd>
					</c:when>
					<c:when test="<%= selectedConfigurationEntriesSize > 1 %>">
						<dt class="sidebar-dt">
							<liferay-ui:message arguments="<%= selectedConfigurationEntriesSize %>" key="x-items-are-selected" />
						</dt>
					</c:when>
					<c:otherwise>
						<dt class="sidebar-dt">
							<liferay-ui:message key="num-of-items" />
						</dt>
						<dd class="sidebar-dd">

							<%
							List<AMImageConfigurationEntry> configurationEntries = (List)request.getAttribute(AMWebKeys.CONFIGURATION_ENTRIES_LIST);
							%>

							<%= configurationEntries.size() %>
						</dd>
					</c:otherwise>
				</c:choose>
			</dl>
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>