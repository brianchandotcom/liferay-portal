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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
Long liveGroupId = (Long)request.getAttribute("site.liveGroupId");
Group stagingGroup = (Group)request.getAttribute("site.stagingGroup");
Long stagingGroupId = (Long)request.getAttribute("site.stagingGroupId");

LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId);

String virtualHost = ParamUtil.getString(request, "publicVirtualHost", BeanParamUtil.getString(layoutSet, request, "virtualHostname"));
%>

<liferay-ui:error-marker key="errorSection" value="siteUrl" />

<h3><liferay-ui:message key="site-url" /></h3>

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

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

		<%
		long duplicateClassPK = gfurle.getDuplicateClassPK();
		String duplicateClassName = gfurle.getDuplicateClassName();

		String name = StringPool.BLANK;

		if (duplicateClassName.equals(Group.class.getName())) {
			Group duplicateGroup = GroupLocalServiceUtil.getGroup(duplicateClassPK);

			name = duplicateGroup.getDescriptiveName(locale);
		}
		else if (duplicateClassName.equals(Layout.class.getName())) {
			Layout duplicateLayout = LayoutLocalServiceUtil.getLayout(duplicateClassPK);

			name = duplicateLayout.getName(locale);
		}
		%>

		<liferay-ui:message arguments="<%= new Object[] {ResourceActionsUtil.getModelResource(locale, duplicateClassName), name} %>" key="please-enter-a-unique-friendly-url" translateArguments="<%= false %>" />
	</c:if>

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.ENDS_WITH_SLASH %>">
		<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-end-with-a-slash" />
	</c:if>

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.INVALID_CHARACTERS %>">
		<liferay-ui:message key="please-enter-a-friendly-url-with-valid-characters" />
	</c:if>

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.KEYWORD_CONFLICT %>">
		<%= LanguageUtil.format(request, "please-enter-a-friendly-url-that-does-not-conflict-with-the-keyword-x", gfurle.getKeywordConflict(), false) %>
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

<liferay-ui:error exception="<%= LayoutSetVirtualHostException.class %>">
	<liferay-ui:message key="please-enter-a-unique-virtual-host" />

	<liferay-ui:message key="virtual-hosts-must-be-valid-domain-names" />
</liferay-ui:error>

<aui:fieldset label="friendly-url">
	<liferay-ui:message key="enter-the-friendly-url-that-is-used-by-pages" />

	<liferay-ui:message arguments="<%= new Object[] {themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()} %>" key="the-friendly-url-is-appended-to-x-for-pages" translateArguments="<%= false %>" />

	<%
	String taglibLabel = "site-friendly-url";

	if (!liveGroup.hasStagingGroup()) {
		taglibLabel = "<span class=\"hide-accessible\">" + LanguageUtil.get(request, taglibLabel) + "</span>";
	}
	%>

	<aui:input label="<%= taglibLabel %>" name="friendlyURL" />

	<c:if test="<%= liveGroup.hasStagingGroup() %>">
		<aui:input bean="<%= stagingGroup %>" field="friendlyURL" fieldParam="stagingFriendlyURL" label="staging-friendly-url" model="<%= Group.class %>" name="stagingFriendlyURL" />
	</c:if>
</aui:fieldset>

<aui:fieldset label="virtual-hosts">
	<liferay-ui:message key="enter-the-virtual-host-that-map-to-the-friendly-url" />

	<liferay-ui:message arguments="<%= new Object[] {HttpUtil.getProtocol(request), themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()} %>" key="for-example,-if-the-virtual-host-is-www.helloworld.com-and-the-friendly-url-is-/helloworld" translateArguments="<%= false %>" />

	<aui:input label="pages" name="publicVirtualHost" type="text" value="<%= virtualHost %>" />

	<c:if test="<%= liveGroup.hasStagingGroup() %>">

		<%
		LayoutSet stagingLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(stagingGroupId);

		String stagingVirtualHost = ParamUtil.getString(request, "stagingPublicVirtualHost", stagingLayoutSet.getVirtualHostname());
		%>

		<aui:input label="staging-pages" name="stagingPublicVirtualHost" type="text" value="<%= stagingVirtualHost %>" />
	</c:if>
</aui:fieldset>

<aui:script sandbox="<%= true %>">
	var friendlyURL = $('#<portlet:namespace />friendlyURL');

	friendlyURL.on(
		'change',
		function(event) {
			var value = friendlyURL.val().trim();

			if (value == '/') {
				value = '';
			}
			else {
				value = value.replace(
					/^[^\/]|\/$/g,
					function(match, index) {
						var str = '';

						if (index == 0) {
							str = '/' + match;
						}

						return str;
					}
				);
			}

			friendlyURL.val(value);
		}
	);
</aui:script>