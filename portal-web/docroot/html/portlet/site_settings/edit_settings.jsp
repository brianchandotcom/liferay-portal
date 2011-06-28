<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/site_settings/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletDisplay.setURLBack(backURL);
}

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

Group group = null;

if (stagingGroup != null) {
	group = stagingGroup;
}
else {
	group = liveGroup;
}

long groupId = liveGroup.getGroupId();

if (group != null) {
	groupId = group.getGroupId();
}

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

UnicodeProperties groupTypeSettings = null;

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/site_settings/edit_settings");
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("groupId", String.valueOf(liveGroupId));

List<String> tabs2NamesList = new ArrayList<String>();

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.UPDATE)) {
	tabs2NamesList.add("virtual-host");
	tabs2NamesList.add("sitemap");
	tabs2NamesList.add("robots");
	tabs2NamesList.add("monitoring");
}

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {
	tabs2NamesList.add("staging");
}

String tabs2Names = StringUtil.merge(tabs2NamesList);

if (!StringUtil.contains(tabs2Names, tabs2)) {
	int pos = tabs2Names.indexOf(StringPool.COMMA);

	if (pos != -1) {
		tabs2 = tabs2Names.substring(0, pos);
	}
}

PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "settings"), HttpUtil.removeParameter(currentURL, "tabs2"));
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs2, TextFormatter.O)), currentURL);

request.setAttribute("edit_settings.jsp-liveGroup", liveGroup);
request.setAttribute("edit_settings.jsp-liveGroupId", new Long(liveGroupId));
request.setAttribute("edit_settings.jsp-liveGroupTypeSettings", liveGroupTypeSettings);
%>

<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN) %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		title="<%= liveGroup.getDescriptiveName() %>"
	/>
</c:if>

<portlet:actionURL var="editSettingsURL">
	<portlet:param name="struts_action" value="/site_settings/edit_settings" />
</portlet:actionURL>

<aui:form action="<%= editSettingsURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveSettings();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		url='<%= portletURL.toString() %>'
	/>

	<liferay-ui:error exception="<%= GroupFriendlyURLException.class %>">

		<%
		GroupFriendlyURLException gfurle = (GroupFriendlyURLException)errorException;
		%>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.ADJACENT_SLASHES %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-have-adjacent-slashes" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.DOES_NOT_START_WITH_SLASH %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-begins-with-a-slash" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.DUPLICATE %>">
			<liferay-ui:message key="please-enter-a-unique-friendly-url" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.ENDS_WITH_SLASH %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-end-with-a-slash" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.INVALID_CHARACTERS %>">
			<liferay-ui:message key="please-enter-a-friendly-url-with-valid-characters" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.KEYWORD_CONFLICT %>">
			<%= LanguageUtil.format(pageContext, "please-enter-a-friendly-url-that-does-not-conflict-with-the-keyword-x", gfurle.getKeywordConflict()) %>
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.POSSIBLE_DUPLICATE %>">
			<liferay-ui:message key="the-friendly-url-may-conflict-with-another-page" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.TOO_DEEP %>">
			<liferay-ui:message key="the-friendly-url-has-too-many-slashes" />
		</c:if>

		<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.TOO_SHORT %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-is-at-least-two-characters-long" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

	<liferay-ui:error exception="<%= LayoutSetVirtualHostException.class %>">
		<liferay-ui:message key="please-enter-a-unique-virtual-host" />

		<liferay-ui:message key="virtual-hosts-must-be-valid-domain-names" />
	</liferay-ui:error>

	<c:choose>
		<c:when test='<%= tabs2.equals("staging") %>'>
			<liferay-util:include page="/html/portlet/site_settings/staging.jsp" />
		</c:when>
		<c:when test='<%= tabs2.equals("virtual-host") %>'>
			<liferay-ui:message key="enter-the-public-and-private-virtual-host-that-will-map-to-the-public-and-private-friendly-url" />

			<liferay-ui:message arguments="<%= new Object[] {HttpUtil.getProtocol(request), themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()} %>" key="for-example,-if-the-public-virtual-host-is-www.helloworld.com-and-the-friendly-url-is-/helloworld" />

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="public-virtual-host" />
				</td>
				<td>

					<%
					LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, false);

					String publicVirtualHost = ParamUtil.getString(request, "publicVirtualHost", BeanParamUtil.getString(publicLayoutSet, request, "virtualHostname"));
					%>

					<input name="<portlet:namespace />publicVirtualHost" size="50" type="text" value="<%= HtmlUtil.escape(publicVirtualHost) %>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="private-virtual-host" />
				</td>
				<td>

					<%
					LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, true);

					String privateVirtualHost = ParamUtil.getString(request, "privateVirtualHost", BeanParamUtil.getString(privateLayoutSet, request, "virtualHostname"));
					%>

					<input name="<portlet:namespace />privateVirtualHost" size="50" type="text" value="<%= HtmlUtil.escape(privateVirtualHost) %>" />
				</td>
			</tr>
			</table>

			<c:if test="<%= liveGroup.isOrganization() || liveGroup.isRegularSite() %>">
				<br />

				<liferay-ui:message key="enter-the-friendly-url-that-will-be-used-by-both-public-and-private-pages" />

				<liferay-ui:message arguments="<%= new Object[] {publicVirtualHost + themeDisplay.getPathFriendlyURLPublic(), themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPrivateGroup()} %>" key="the-friendly-url-is-appended-to-x-for-public-pages-and-x-for-private-pages" />

				<br /><br />

				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="friendly-url" />
					</td>
					<td>

						<%
						String friendlyURL = BeanParamUtil.getString(liveGroup, request, "friendlyURL");
						%>

						<input name="<portlet:namespace />friendlyURL" size="30" type="text" value="<%= HtmlUtil.escape(friendlyURL) %>" />
					</td>
				</tr>
				</table>
			</c:if>

			<c:if test="<%= liveGroup.hasStagingGroup() %>">
				<br />

				<strong><liferay-ui:message key="staging" /></strong>

				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="public-virtual-host" />
					</td>
					<td>

						<%
						LayoutSet stagingPublicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(stagingGroupId, false);

						String stagingPublicVirtualHost = ParamUtil.getString(request, "stagingPublicVirtualHost", stagingPublicLayoutSet.getVirtualHostname());
						%>

						<input name="<portlet:namespace />stagingPublicVirtualHost" size="50" type="text" value="<%= HtmlUtil.escape(stagingPublicVirtualHost) %>" />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="private-virtual-host" />
					</td>
					<td>

						<%
						LayoutSet stagingPrivateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(stagingGroupId, true);

						String stagingPrivateVirtualHost = ParamUtil.getString(request, "stagingPrivateVirtualHost", stagingPrivateLayoutSet.getVirtualHostname());
						%>

						<input name="<portlet:namespace />stagingPrivateVirtualHost" size="50" type="text" value="<%= HtmlUtil.escape(stagingPrivateVirtualHost) %>" />
					</td>
				</tr>
				</table>

				<c:if test="<%= liveGroup.isOrganization() || liveGroup.isRegularSite() %>">
					<br />

					<table class="lfr-table">
					<tr>
						<td>
							<liferay-ui:message key="friendly-url" />
						</td>
						<td>

							<%
							String friendlyURL = ParamUtil.getString(request, "stagingFriendlyURL", stagingGroup.getFriendlyURL());
							%>

							<input name="<portlet:namespace />stagingFriendlyURL" size="30" type="text" value="<%= HtmlUtil.escape(friendlyURL) %>" />
						</td>
					</tr>
					</table>
				</c:if>
			</c:if>

			<br />

			<input type="submit" value="<liferay-ui:message key="save" />" />
		</c:when>
		<c:when test='<%= tabs2.equals("sitemap") %>'>

			<%
			String host = PortalUtil.getHost(request);

			String sitemapUrl = PortalUtil.getPortalURL(host, request.getServerPort(), request.isSecure()) + themeDisplay.getPathContext() + "/sitemap.xml";

			String publicSitemapUrl = sitemapUrl;

			LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, false);

			if (!host.equals(publicLayoutSet.getVirtualHostname())) {
				publicSitemapUrl += "?groupId=" + groupId + "&privateLayout=" + false;
			}

			String privateSitemapUrl = sitemapUrl;

			LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, true);

			if (!host.equals(privateLayoutSet.getVirtualHostname())) {
				privateSitemapUrl += "?groupId=" + groupId + "&privateLayout=" + true;
			}
			%>

			<liferay-util:buffer var="linkContent">
				<aui:a href="http://www.sitemaps.org" target="_blank">http://www.sitemaps.org</aui:a>
			</liferay-util:buffer>

			<liferay-ui:message key="the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website" /> <liferay-ui:message arguments="<%= linkContent %>" key="see-x-for-more-information" />

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="public-pages" />
				</td>
			</tr>
			<tr>
				<td>
					<%= LanguageUtil.format(pageContext, "send-sitemap-information-to-preview", new Object[] {"<a target=\"_blank\" href=\"" + publicSitemapUrl + "\">", "</a>"}) %>

					<ul>
						<li>
							<aui:a href='<%= "http://www.google.com/webmasters/sitemaps/ping?sitemap=" + HtmlUtil.escapeURL(publicSitemapUrl) %>' target="_blank">Google</aui:a>
						</li>
						<li>
							<aui:a href='<%= "https://siteexplorer.search.yahoo.com/submit/ping?sitemap=" + HtmlUtil.escapeURL(publicSitemapUrl) %>' target="_blank">Yahoo!</aui:a> (<liferay-ui:message key="requires-login" />)
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="private-pages" />
				</td>
			</tr>
			<tr>
				<td>
					<%= LanguageUtil.format(pageContext, "send-sitemap-information-to-preview", new Object[] {"<a target=\"_blank\" href=\"" + privateSitemapUrl + "\">", "</a>"}) %>

					<ul>
						<li>
							<aui:a href='<%= "http://www.google.com/webmasters/sitemaps/ping?sitemap=" + HtmlUtil.escapeURL(privateSitemapUrl) %>' target="_blank">Google</aui:a>
						</li>
						<li>
							<aui:a href='<%= "https://siteexplorer.search.yahoo.com/submit/ping?sitemap=" + HtmlUtil.escapeURL(privateSitemapUrl) %>' target="_blank">Yahoo!</aui:a> (<liferay-ui:message key="requires-login" />)
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>
					<br />
				</td>
			</tr>
			</table>
		</c:when>
		<c:when test='<%= tabs2.equals("robots") %>'>

			<%
			LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, false);

			String defaultPublicRobots = RobotsUtil.getRobots(publicLayoutSet);

			String publicRobots = ParamUtil.getString(request, "robots", defaultPublicRobots);

			LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, true);

			String defaultPrivateRobots = RobotsUtil.getRobots(privateLayoutSet);

			String privateRobots = ParamUtil.getString(request, "robots", defaultPrivateRobots);
			%>

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="set-the-robots-txt-for-public-pages" />
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="<%= Validator.isNotNull(publicLayoutSet.getVirtualHostname()) %>">
							<textarea cols="60" name="<portlet:namespace />publicRobots" rows="15"><%= HtmlUtil.escape(publicRobots) %></textarea>
						</c:when>
						<c:otherwise>
							<div class="portlet-msg-info">
								<liferay-ui:message key="please-set-the-virtual-host-before-you-set-the-robots-txt" />
							</div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="set-the-robots-txt-for-private-pages" />
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="<%= Validator.isNotNull(privateLayoutSet.getVirtualHostname()) %>">
							<textarea cols="60" name="<portlet:namespace />privateRobots" rows="15"><%= HtmlUtil.escape(privateRobots) %></textarea>
						</c:when>
						<c:otherwise>
							<div class="portlet-msg-info">
								<liferay-ui:message key="please-set-the-virtual-host-before-you-set-the-robots-txt" />
							</div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<br />
				</td>
			</tr>
			</table>

			<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />updateRobots();" />
		</c:when>
		<c:when test='<%= tabs2.equals("monitoring") %>'>
			<liferay-ui:message key="set-the-google-analytics-id-that-will-be-used-for-this-set-of-pages" />

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="google-analytics-id" />
				</td>
				<td>

					<%
					String googleAnalyticsId = PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsId");
					%>

					<input name="<portlet:namespace />googleAnalyticsId" size="30" type="text" value="<%= HtmlUtil.escape(googleAnalyticsId) %>" />
				</td>
			</tr>
			</table>

			<br />

			<input type="submit" value="<liferay-ui:message key="save" />" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />saveSettings() {
		<c:choose>
			<c:when test='<%= tabs2.equals("monitoring") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "monitoring";
			</c:when>
			<c:when test='<%= tabs2.equals("virtual-host") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "virtual_host";
			</c:when>
		</c:choose>

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLogo() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "logo";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateRobots() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "robots";
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />updateStaging',
		function() {
			var A = AUI();

			var selectEl = A.one('#<portlet:namespace />stagingType');

			var currentValue = null;

			if (selectEl) {
				currentValue = selectEl.val();
			}

			var ok = false;

			if (0 == currentValue) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-deactivate-staging-for-x", liveGroup.getDescriptiveName()) %>');
			}
			else if (1 == currentValue) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-local-staging-for-x", liveGroup.getDescriptiveName()) %>');
			}
			else if (2 == currentValue) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-remote-staging-for-x", liveGroup.getDescriptiveName()) %>');
			}

			if (ok) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "staging";
				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-base']
	);
</aui:script>