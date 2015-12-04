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
InformationMessagesControlMenuEntry informationMessagesControlMenuEntry = (InformationMessagesControlMenuEntry)request.getAttribute(ControlMenuWebKeys.CONTROL_MENU_ENTRY);
%>

<portlet:renderURL var="addURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/add_panel.jsp" />
	<portlet:param name="stateMaximized" value="<%= String.valueOf(themeDisplay.isStateMaximized()) %>" />
	<portlet:param name="viewAssetEntries" value="<%= Boolean.TRUE.toString() %>" />
</portlet:renderURL>

<%
Map<String, Object> data = new HashMap<String, Object>();

data.put("panelURL", addURL);
%>

<li>
	<aui:icon
		cssClass="control-menu-icon"
		data="<%= data %>"
		id="infoButton"
		image="information-live"
		label="additional-information"
		markupView="lexicon"
		url="javascript:;"
	/>

	<liferay-util:buffer var="infoContainer">
		<c:if test="<%= informationMessagesControlMenuEntry.isModifiedLayout(themeDisplay) %>">
			<div class="modified-layout">
				<aui:icon image="information-live" markupView="lexicon" />

				<span class="message-info">
					<liferay-ui:message key="this-page-has-been-changed-since-the-last-update-from-the-site-template-excerpt" />

					<liferay-ui:icon-help message="this-page-has-been-changed-since-the-last-update-from-the-site-template" />
				</span>

				<portlet:actionURL name="resetPrototype" var="resetPrototypeURL">
					<portlet:param name="redirect" value="<%= PortalUtil.getLayoutURL(themeDisplay) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />
				</portlet:actionURL>

				<%
				String taglibURL = "submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(resetPrototypeURL) + "');";
				%>

				<span class="button-info">
					<aui:button cssClass="btn-link" name="submit" onClick="<%= taglibURL %>" type="submit" value="reset-changes" />
				</span>
			</div>
		</c:if>

		<c:if test="<%= informationMessagesControlMenuEntry.isLinkedLayout(themeDisplay) %>">
			<div class="linked-layout">
				<aui:icon image="information-live" markupView="lexicon" />

				<span class="message-info">

					<%
					Group group = themeDisplay.getScopeGroup();
					%>

					<c:choose>
						<c:when test="<%= layout.isLayoutPrototypeLinkActive() && !group.hasStagingGroup() %>">
							<liferay-ui:message key="this-page-is-linked-to-a-page-template" />
						</c:when>
						<c:when test="<%= SitesUtil.isUserGroupLayout(layout) %>">
							<liferay-ui:message key="this-page-belongs-to-a-user-group" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="this-page-is-linked-to-a-site-template-which-does-not-allow-modifications-to-it" />
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</c:if>

		<c:if test="<%= informationMessagesControlMenuEntry.isCustomizableLayout(themeDisplay) %>">
			<div class="customizable-layout">
				<aui:icon image="information-live" markupView="lexicon" />

				<span class="message-info">
					<c:choose>
						<c:when test="<%= layoutTypePortlet.isCustomizedView() %>">
							<liferay-ui:message key="you-can-customize-this-page" />

							<liferay-ui:icon-help message="customizable-user-help" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="this-is-the-default-page-without-your-customizations" />

							<c:if test="<%= informationMessagesControlMenuEntry.hasUpdateLayoutPermission(themeDisplay) %>">
								<liferay-ui:icon-help message="customizable-admin-help" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</span>

				<span class="button-info">

					<%
					String taglibMessage = "view-default-page";

					if (!layoutTypePortlet.isCustomizedView()) {
						taglibMessage = "view-my-customized-page";
					}
					else if (layoutTypePortlet.isDefaultUpdated()) {
						taglibMessage = "the-defaults-for-the-current-page-have-been-updated-click-here-to-see-them";
					}
					%>

					<liferay-ui:icon cssClass="view-default" id="toggleCustomizedView" label="<%= true %>" message="<%= taglibMessage %>" url="javascript:;" />

					<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
						<portlet:actionURL name="resetCustomizationView" var="resetCustomizationViewURL">
							<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />
						</portlet:actionURL>

						<%
						String taglibURL = "javascript:if (confirm('" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-reset-your-customizations-to-default") + "')){submitForm(document.hrefFm, '" + HttpUtil.encodeURL(resetCustomizationViewURL) + "');}";
						%>

						<liferay-ui:icon cssClass="reset-my-customizations" label="<%= true %>" message="reset-my-customizations" url="<%= taglibURL %>" />
					</c:if>
				</span>
			</div>

			<aui:script position="inline" sandbox="<%= true %>">
				$('#<portlet:namespace />toggleCustomizedView').on(
					'click',
					function(event) {
						$.ajax(
							themeDisplay.getPathMain() + '/portal/update_layout',
							{
								data: {
									cmd: 'toggle_customized_view',
									customized_view: '<%= String.valueOf(!layoutTypePortlet.isCustomizedView()) %>',
									p_auth: '<%= AuthTokenUtil.getToken(request) %>'
								},
								success: function() {
									window.location.href = themeDisplay.getLayoutURL();
								}
							}
						);
					}
				);
			</aui:script>
		</c:if>
	</liferay-util:buffer>

	<aui:script sandbox="<%= true %>">
		$('#<portlet:namespace />infoButton').popover(
			{
				content: '<%= HtmlUtil.escapeJS(infoContainer) %>',
				html: true,
				placement: 'bottom'
			}
		);
	</aui:script>
</li>