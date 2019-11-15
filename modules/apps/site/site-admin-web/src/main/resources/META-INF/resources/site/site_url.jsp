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
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
Long liveGroupId = (Long)request.getAttribute("site.liveGroupId");
Group stagingGroup = (Group)request.getAttribute("site.stagingGroup");
Long stagingGroupId = (Long)request.getAttribute("site.stagingGroupId");

LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, false);
LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, true);

Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(liveGroupId);

TreeMap<String, String> publicVirtualHostnames = publicLayoutSet.getVirtualHostnames();

if (publicVirtualHostnames.isEmpty()) {
	publicVirtualHostnames.put(StringPool.BLANK, StringPool.BLANK);
}

TreeMap<String, String> privateVirtualHostnames = privateLayoutSet.getVirtualHostnames();

if (privateVirtualHostnames.isEmpty()) {
	privateVirtualHostnames.put(StringPool.BLANK, StringPool.BLANK);
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="site-url"
/>

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

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.ENDS_WITH_DASH %>">
		<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-end-with-a-dash" />
	</c:if>

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.ENDS_WITH_SLASH %>">
		<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-end-with-a-slash" />
	</c:if>

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.INVALID_CHARACTERS %>">
		<liferay-ui:message key="please-enter-a-friendly-url-with-valid-characters" />
	</c:if>

	<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.KEYWORD_CONFLICT %>">
		<liferay-ui:message arguments="<%= gfurle.getKeywordConflict() %>" key="please-enter-a-friendly-url-that-does-not-conflict-with-the-keyword-x" translateArguments="<%= false %>" />
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

<aui:fieldset>
	<p class="text-muted">
		<liferay-ui:message key="enter-the-friendly-url-that-is-used-by-both-public-and-private-pages" />

		<liferay-ui:message arguments="<%= new Object[] {themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic(), themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPrivateGroup()} %>" key="the-friendly-url-is-appended-to-x-for-public-pages-and-x-for-private-pages" translateArguments="<%= false %>" />
	</p>

	<aui:input label="friendly-url" name="groupFriendlyURL" type="text" value="<%= HttpUtil.decodeURL(liveGroup.getFriendlyURL()) %>" />

	<c:if test="<%= liveGroup.hasStagingGroup() %>">
		<aui:input label="staging-friendly-url" name="stagingFriendlyURL" type="text" value="<%= HttpUtil.decodeURL(stagingGroup.getFriendlyURL()) %>" />
	</c:if>

	<p class="text-muted">
		<liferay-ui:message key="enter-the-public-and-private-virtual-host-that-map-to-the-public-and-private-friendly-url" />

		<liferay-ui:message arguments="<%= new Object[] {HttpUtil.getProtocol(request), themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()} %>" key="for-example,-if-the-public-virtual-host-is-www.helloworld.com-and-the-friendly-url-is-/helloworld" translateArguments="<%= false %>" />
	</p>

	<div id="<portlet:namespace />publicVirtualHostFields">

		<%
		for (Map.Entry<String, String> entry : publicVirtualHostnames.entrySet()) {
			String virtualHostname = entry.getKey();

			String virtualHostLanguageId = Validator.isNotNull(entry.getValue()) ? entry.getValue() : StringPool.BLANK;
		%>

			<div class="container-fluid lfr-form-row">
				<div class="row">
					<aui:input inlineField="<%= true %>" label="public-pages" maxlength="200" name="publicVirtualHostname[]" placeholder="virtual-host" type="text" value="<%= virtualHostname %>" wrapperCssClass="col-sm-6" />

					<aui:select inlineField="<%= true %>" label="language" name="publicVirtualHostLanguageId[]" wrapperCssClass="col-sm-6">
						<aui:option label="default-language" value="" />

						<%
						for (Locale localeEntry : availableLocales) {
							String languageId = LocaleUtil.toLanguageId(localeEntry);
						%>

							<aui:option label="<%= localeEntry.getDisplayName(themeDisplay.getLocale()) %>" selected="<%= languageId.equals(virtualHostLanguageId) %>" value="<%= languageId %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</div>

		<%
		}
		%>

	</div>

	<div id="<portlet:namespace />privateVirtualHostFields">

		<%
		for (Map.Entry<String, String> entry : privateVirtualHostnames.entrySet()) {
			String virtualHostname = entry.getKey();

			String virtualHostLanguageId = Validator.isNotNull(entry.getValue()) ? entry.getValue() : StringPool.BLANK;
		%>

			<div class="container-fluid lfr-form-row">
				<div class="row">
					<aui:input inlineField="<%= true %>" label="private-pages" maxlength="200" name="privateVirtualHostname[]" placeholder="virtual-host" type="text" value="<%= virtualHostname %>" wrapperCssClass="col-sm-6" />

					<aui:select inlineField="<%= true %>" label="language" name="privateVirtualHostLanguageId[]" wrapperCssClass="col-sm-6">
						<aui:option label="default-language" value="" />

						<%
						for (Locale localeEntry : availableLocales) {
							String languageId = LocaleUtil.toLanguageId(localeEntry);
						%>

							<aui:option label="<%= localeEntry.getDisplayName(themeDisplay.getLocale()) %>" selected="<%= languageId.equals(virtualHostLanguageId) %>" value="<%= languageId %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</div>

		<%
		}
		%>

	</div>

	<c:if test="<%= liveGroup.hasStagingGroup() %>">

		<%
		LayoutSet stagingPublicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(stagingGroupId, false);

		TreeMap<String, String> stagingPublicVirtualHostnames = stagingPublicLayoutSet.getVirtualHostnames();

		if (stagingPublicVirtualHostnames.isEmpty()) {
			stagingPublicVirtualHostnames.put(StringPool.BLANK, StringPool.BLANK);
		}
		%>

		<div id="<portlet:namespace />stagingPublicVirtualHostFields">

			<%
			for (Map.Entry<String, String> entry : stagingPublicVirtualHostnames.entrySet()) {
				String virtualHostname = entry.getKey();

				String virtualHostLanguageId = Validator.isNotNull(entry.getValue()) ? entry.getValue() : StringPool.BLANK;
			%>

				<div class="container-fluid lfr-form-row">
					<div class="row">
						<aui:input inlineField="<%= true %>" label="staging-public-pages" maxlength="200" name="stagingPublicVirtualHostname[]" placeholder="virtual-host" type="text" value="<%= virtualHostname %>" wrapperCssClass="col-sm-6" />

						<aui:select inlineField="<%= true %>" label="language" name="stagingPublicVirtualHostLanguageId[]" wrapperCssClass="col-sm-6">
							<aui:option label="default-language" value="" />

							<%
							for (Locale localeEntry : availableLocales) {
								String languageId = LocaleUtil.toLanguageId(localeEntry);
							%>

								<aui:option label="<%= localeEntry.getDisplayName(themeDisplay.getLocale()) %>" selected="<%= languageId.equals(virtualHostLanguageId) %>" value="<%= languageId %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</div>

			<%
			}
			%>

		</div>

		<%
		LayoutSet stagingPrivateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(stagingGroupId, true);

		TreeMap<String, String> stagingPrivateVirtualHostnames = stagingPrivateLayoutSet.getVirtualHostnames();

		if (stagingPrivateVirtualHostnames.isEmpty()) {
			stagingPrivateVirtualHostnames.put(StringPool.BLANK, StringPool.BLANK);
		}
		%>

		<div id="<portlet:namespace />stagingPrivateVirtualHostFields">

			<%
			for (Map.Entry<String, String> entry : stagingPrivateVirtualHostnames.entrySet()) {
				String virtualHostname = entry.getKey();

				String virtualHostLanguageId = Validator.isNotNull(entry.getValue()) ? entry.getValue() : StringPool.BLANK;
			%>

				<div class="container-fluid lfr-form-row">
					<div class="row">
						<aui:input inlineField="<%= true %>" label="staging-private-pages" maxlength="200" name="stagingPrivateVirtualHostname[]" placeholder="virtual-host" type="text" value="<%= virtualHostname %>" wrapperCssClass="col-sm-6" />

						<aui:select inlineField="<%= true %>" label="language" name="stagingPrivateVirtualHostLanguageId[]" wrapperCssClass="col-sm-6">
							<aui:option label="default-language" value="" />

							<%
							for (Locale localeEntry : availableLocales) {
								String languageId = LocaleUtil.toLanguageId(localeEntry);
							%>

								<aui:option label="<%= localeEntry.getDisplayName(themeDisplay.getLocale()) %>" selected="<%= languageId.equals(virtualHostLanguageId) %>" value="<%= languageId %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</div>

			<%
			}
			%>

		</div>
	</c:if>
</aui:fieldset>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />publicVirtualHostFields',
		namespace: '<portlet:namespace />'
	}).render();

	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />privateVirtualHostFields',
		namespace: '<portlet:namespace />'
	}).render();

	<c:if test="<%= liveGroup.hasStagingGroup() %>">
		new Liferay.AutoFields({
			contentBox: '#<portlet:namespace />stagingPublicVirtualHostFields',
			namespace: '<portlet:namespace />'
		}).render();

		new Liferay.AutoFields({
			contentBox: '#<portlet:namespace />stagingPrivateVirtualHostFields',
			namespace: '<portlet:namespace />'
		}).render();
	</c:if>
</aui:script>

<script>
	var friendlyURL = document.getElementById(
		'<portlet:namespace />groupFriendlyURL'
	);

	if (friendlyURL) {
		friendlyURL.addEventListener('change', function(event) {
			var value = friendlyURL.value.trim();

			if (value == '/') {
				value = '';
			} else {
				value = value.replace(/^[^\/]|\/$/g, function(match, index) {
					var str = '';

					if (index == 0) {
						str = '/' + match;
					}

					return str;
				});
			}

			friendlyURL.value = value;
		});
	}
</script>