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

<%@ include file="/flags/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-flags:flags:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-flags:flags:classPK"));
String contentTitle = GetterUtil.getString((String)request.getAttribute("liferay-flags:flags:contentTitle"));
boolean enabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-flags:flags:enabled"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-flags:flags:label"), true);
String message = GetterUtil.getString((String)request.getAttribute("liferay-flags:flags:message"), "flag[action]");
long reportedUserId = GetterUtil.getLong((String)request.getAttribute("liferay-flags:flags:reportedUserId"));

String cssClass = randomNamespace;

if (enabled) {
	cssClass = randomNamespace + " flag-enable";
}
%>

<div class="taglib-flags" title="<liferay-ui:message key="<%= message %>" />">
	<liferay-ui:icon
		cssClass="<%= cssClass %>"
		iconCssClass="icon-flag"
		label="<%= label %>"
		message="<%= message %>"
		url='<%= enabled ? "javascript:;" : null %>'
	/>
</div>

<c:if test="<%= enabled %>">
	<c:choose>
		<c:when test="<%= flagsGroupServiceConfiguration.guestUsersEnabled() || themeDisplay.isSignedIn() %>">
			<aui:script use="aui-io-plugin-deprecated,aui-modal">
				var icon = A.one('.<%= randomNamespace %>');

				if (icon) {
					icon.on(
						'click',
						function() {
							var popup = Liferay.Util.Window.getWindow(
								{
									dialog: {
										destroyOnHide: true,
										height: 400,
										width: 400
									},
									title: '<%= UnicodeLanguageUtil.get(request, "report-inappropriate-content") %>'
								}
							);

							var data = Liferay.Util.ns(
								'<%= PortalUtil.getPortletNamespace(PortletKeys.FLAGS) %>',
								{
									className: '<%= className %>',
									classPK: '<%= classPK %>',
									contentTitle: '<%= HtmlUtil.escapeJS(contentTitle) %>',
									contentURL: '<%= HtmlUtil.escapeJS(PortalUtil.getPortalURL(request) + currentURL) %>',
									reportedUserId: '<%= reportedUserId %>'
								}
							);

							popup.plug(
								A.Plugin.IO, {
									data: data,
									uri: '<liferay-portlet:renderURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/flags/edit_entry" /></liferay-portlet:renderURL>'
								}
							);
						}
					);
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<div id="<%= randomNamespace %>signIn" style="display:none">
				<liferay-ui:message key="please-sign-in-to-flag-this-as-inappropriate" />
			</div>

			<aui:script use="aui-modal">
				var icon = A.one('.<%= randomNamespace %>');

				if (icon) {
					icon.on(
						'click',
						function(event) {
							var popup = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: A.one('#<%= randomNamespace %>signIn').html(),
										destroyOnHide: true,
										height: 400,
										width: 400
									},
									title: '<%= UnicodeLanguageUtil.get(request, "report-inappropriate-content") %>'
								}
							);

							event.preventDefault();
						}
					);
				}
			</aui:script>
		</c:otherwise>
	</c:choose>
</c:if>

<div class="taglib-flags" id="<portlet:namespace />taglibFlag" title="<liferay-ui:message key='<%= !TrashUtil.isInTrash(className, classPK) ? message : "flags-are-disabled-because-this-entry-is-in-the-recycle-bin" %>' />">
</div>

<aui:script require="flags-taglib/flags/js/Flags.es">
	new flagsTaglibFlagsJsFlagsEs.default(
		{
			cssClass: '<%= cssClass %>',
			data: Liferay.Util.ns(
				'<%= PortalUtil.getPortletNamespace(PortletKeys.FLAGS) %>',
				{
					className: '<%= className %>',
					classPK: '<%= classPK %>',
					contentTitle: '<%= HtmlUtil.escapeJS(contentTitle) %>',
					contentURL: '<%= HtmlUtil.escapeJS(PortalUtil.getPortalURL(request) + currentURL) %>',
					reportedUserId: '<%= reportedUserId %>'
				}
			),
			inTrash: <%= TrashUtil.isInTrash(className, classPK) %>,
			label: <%= label %>,
			message: '<%= message %>',
			pathThemeImages: '<%= themeDisplay.getPathThemeImages() %>',
			signedUser: <%= flagsGroupServiceConfiguration.guestUsersEnabled() || themeDisplay.isSignedIn() %>,
			uri: '<liferay-portlet:renderURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/flags/edit_entry" /></liferay-portlet:renderURL>'
		},
		'#<%= namespace %>taglibFlag'
	);
</aui:script>