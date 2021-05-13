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
Group siteGoup = (Group)request.getAttribute("site.group");

Group liveGroup = (Group)request.getAttribute("site.liveGroup");

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSetPrototype privateLayoutSetPrototype = null;
boolean privateLayoutSetPrototypeLinkEnabled = true;

LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(siteGoup.getGroupId(), true);

if (privateLayoutSet != null) {
	privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

	String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

	if (Validator.isNotNull(layoutSetPrototypeUuid)) {
		privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
	}
}

LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeLinkEnabled = true;

LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(siteGoup.getGroupId(), false);

if (publicLayoutSet != null) {
	publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

	String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

	if (Validator.isNotNull(layoutSetPrototypeUuid)) {
		publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
	}
}
%>

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<%
boolean disableLayoutSetPrototypeInput = false;

SiteAdminConfiguration siteAdminConfiguration = ConfigurationProviderUtil.getSystemConfiguration(SiteAdminConfiguration.class);

if (!LanguageUtil.isInheritLocales(siteGoup.getGroupId()) && !siteAdminConfiguration.enableCustomLanguagesWithTemplatePropagation()) {
	disableLayoutSetPrototypeInput = true;
}

boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
%>

<h4 class="sheet-subtitle"><liferay-ui:message key="public-pages" /></h4>

<aui:field-wrapper cssClass="form-group">
	<c:choose>
		<c:when test="<%= (publicLayoutSetPrototype == null) && (siteGoup.getPublicLayoutsPageCount() == 0) && !layoutSetPrototypes.isEmpty() %>">
			<c:if test="<%= disableLayoutSetPrototypeInput %>">
				<div class="alert alert-info">
					<liferay-ui:message key="you-cannot-apply-a-site-template-because-you-modified-the-display-settings-of-this-site" />
				</div>
			</c:if>

			<aui:select disabled="<%= disableLayoutSetPrototypeInput %>" helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="site-template" name="publicLayoutSetPrototypeId">
				<aui:option label="none" selected="<%= true %>" value="" />

				<%
				for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
					UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();
				%>

					<aui:option data-servletContextName='<%= settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK) %>' value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(locale)) %></aui:option>

				<%
				}
				%>

			</aui:select>

			<c:if test="<%= !siteGoup.isStaged() %>">
				<c:choose>
					<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
						<div class="hide" id="<portlet:namespace />publicLayoutSetPrototypeIdOptions">
							<c:if test="<%= disableLayoutSetPrototypeInput %>">
								<div class="alert alert-info">
									<liferay-ui:message key="you-cannot-enable-the-propagation-of-changes-because-you-modified-the-display-settings-of-this-site" />
								</div>
							</c:if>

							<aui:input disabled="<%= disableLayoutSetPrototypeInput %>" helpMessage="enable-propagation-of-changes-from-the-site-template-help" inlineLabel="right" label="enable-propagation-of-changes-from-the-site-template" labelCssClass="simple-toggle-switch" name="publicLayoutSetPrototypeLinkEnabled" type="toggle-switch" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
						</div>
					</c:when>
					<c:otherwise>
						<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= siteGoup.getPublicLayoutsPageCount() > 0 %>">
					<aui:a href="<%= siteGoup.getDisplayURL(themeDisplay, false) %>" label="open-public-pages" target="_blank" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="this-site-does-not-have-any-public-pages" />
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= (publicLayoutSetPrototype != null) && !siteGoup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
					<c:if test="<%= disableLayoutSetPrototypeInput %>">
						<div class="alert alert-info">
							<liferay-ui:message key="you-cannot-enable-the-propagation-of-changes-because-you-modified-the-display-settings-of-this-site" />
						</div>
					</c:if>

					<aui:input disabled="<%= disableLayoutSetPrototypeInput %>" inlineLabel="right" label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)), false) %>' labelCssClass="simple-toggle-switch" name="publicLayoutSetPrototypeLinkEnabled" type="toggle-switch" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />

					<div class="<%= publicLayoutSetPrototypeLinkEnabled ? "" : "hide" %>" id="<portlet:namespace />publicLayoutSetPrototypeMergeAlert">

						<%
						request.setAttribute("edit_layout_set_prototype.jsp-groupId", String.valueOf(siteGoup.getGroupId()));
						request.setAttribute("edit_layout_set_prototype.jsp-layoutSet", publicLayoutSet);
						request.setAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype", publicLayoutSetPrototype);
						request.setAttribute("edit_layout_set_prototype.jsp-redirect", currentURL);
						%>

						<liferay-util:include page="/layout_set_merge_alert.jsp" servletContext="<%= application %>" />
					</div>
				</c:when>
				<c:when test="<%= publicLayoutSetPrototype != null %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)) %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

					<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
				</c:when>
			</c:choose>
		</c:otherwise>
	</c:choose>
</aui:field-wrapper>

<h4 class="sheet-subtitle"><liferay-ui:message key="private-pages" /></h4>

<aui:field-wrapper cssClass="form-group">
	<c:choose>
		<c:when test="<%= (privateLayoutSetPrototype == null) && (siteGoup.getPrivateLayoutsPageCount() == 0) && !layoutSetPrototypes.isEmpty() %>">
			<c:if test="<%= disableLayoutSetPrototypeInput %>">
				<div class="alert alert-info">
					<liferay-ui:message key="you-cannot-apply-a-site-template-because-you-modified-the-display-settings-of-this-site" />
				</div>
			</c:if>

			<aui:select disabled="<%= disableLayoutSetPrototypeInput %>" helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="site-template" name="privateLayoutSetPrototypeId">
				<aui:option label="none" selected="<%= true %>" value="" />

				<%
				for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
					UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();
				%>

					<aui:option data-servletContextName='<%= settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK) %>' value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(locale)) %></aui:option>

				<%
				}
				%>

			</aui:select>

			<c:if test="<%= !siteGoup.isStaged() %>">
				<c:choose>
					<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
						<div class="hide" id="<portlet:namespace />privateLayoutSetPrototypeIdOptions">
							<c:if test="<%= disableLayoutSetPrototypeInput %>">
								<div class="alert alert-info">
									<liferay-ui:message key="you-cannot-enable-the-propagation-of-changes-because-you-modified-the-display-settings-of-this-site" />
								</div>
							</c:if>

							<aui:input disabled="<%= disableLayoutSetPrototypeInput %>" helpMessage="enable-propagation-of-changes-from-the-site-template-help" inlineLabel="right" label="enable-propagation-of-changes-from-the-site-template" labelCssClass="simple-toggle-switch" name="privateLayoutSetPrototypeLinkEnabled" type="toggle-switch" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
						</div>
					</c:when>
					<c:otherwise>
						<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= siteGoup.getPrivateLayoutsPageCount() > 0 %>">
					<aui:a href="<%= siteGoup.getDisplayURL(themeDisplay, true) %>" label="open-private-pages" target="_blank" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="this-site-does-not-have-any-private-pages" />
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= (privateLayoutSetPrototype != null) && !siteGoup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
					<c:if test="<%= disableLayoutSetPrototypeInput %>">
						<div class="alert alert-info">
							<liferay-ui:message key="you-cannot-enable-the-propagation-of-changes-because-you-modified-the-display-settings-of-this-site" />
						</div>
					</c:if>

					<aui:input disabled="<%= disableLayoutSetPrototypeInput %>" inlineLabel="right" label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)), false) %>' labelCssClass="simple-toggle-switch" name="privateLayoutSetPrototypeLinkEnabled" type="toggle-switch" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />

					<div class="<%= privateLayoutSetPrototypeLinkEnabled ? "" : "hide" %>" id="<portlet:namespace />privateLayoutSetPrototypeMergeAlert">

						<%
						request.setAttribute("edit_layout_set_prototype.jsp-groupId", String.valueOf(siteGoup.getGroupId()));
						request.setAttribute("edit_layout_set_prototype.jsp-layoutSet", privateLayoutSet);
						request.setAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype", privateLayoutSetPrototype);
						request.setAttribute("edit_layout_set_prototype.jsp-redirect", currentURL);
						%>

						<liferay-util:include page="/layout_set_merge_alert.jsp" servletContext="<%= application %>" />
					</div>
				</c:when>
				<c:when test="<%= privateLayoutSetPrototype != null %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)) %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

					<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
				</c:when>
			</c:choose>
		</c:otherwise>
	</c:choose>
</aui:field-wrapper>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />publicLayoutSetPrototypeId',
		<portlet:namespace />isVisible,
		'<portlet:namespace />publicLayoutSetPrototypeIdOptions'
	);
	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />privateLayoutSetPrototypeId',
		<portlet:namespace />isVisible,
		'<portlet:namespace />privateLayoutSetPrototypeIdOptions'
	);

	Liferay.Util.toggleBoxes(
		'<portlet:namespace />publicLayoutSetPrototypeLinkEnabled',
		'<portlet:namespace />publicLayoutSetPrototypeMergeAlert'
	);
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />privateLayoutSetPrototypeLinkEnabled',
		'<portlet:namespace />privateLayoutSetPrototypeMergeAlert'
	);
</aui:script>