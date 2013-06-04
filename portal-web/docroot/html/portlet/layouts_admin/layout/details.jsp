<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

LayoutTypePortletImpl selLayoutTypePortlet = new LayoutTypePortletImpl(selLayout);

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
%>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="details" /></h3>

<%
StringBuilder friendlyURLBase = new StringBuilder();
%>

<c:if test="<%= !group.isLayoutPrototype() && PortalUtil.isLayoutFriendliable(selLayout) %>">

	<%
	friendlyURLBase.append(themeDisplay.getPortalURL());

	LayoutSet layoutSet = selLayout.getLayoutSet();

	String virtualHostname = layoutSet.getVirtualHostname();

	if (Validator.isNull(virtualHostname) || (friendlyURLBase.indexOf(virtualHostname) == -1)) {
		friendlyURLBase.append(group.getPathFriendlyURL(privateLayout, themeDisplay));
		friendlyURLBase.append(group.getFriendlyURL());
	}
	%>

	<liferay-ui:error exception="<%= LayoutFriendlyURLException.class %>">

		<%
		LayoutFriendlyURLException lfurle = (LayoutFriendlyURLException)errorException;
		%>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ADJACENT_SLASHES %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-have-adjacent-slashes" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-begins-with-a-slash" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DUPLICATE %>">
			<liferay-ui:message key="please-enter-a-unique-friendly-url" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ENDS_WITH_SLASH %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-end-with-a-slash" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.INVALID_CHARACTERS %>">
			<liferay-ui:message key="please-enter-a-friendly-url-with-valid-characters" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.KEYWORD_CONFLICT %>">
			<%= LanguageUtil.format(pageContext, "please-enter-a-friendly-url-that-does-not-conflict-with-the-keyword-x", lfurle.getKeywordConflict()) %>
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.POSSIBLE_DUPLICATE %>">
			<liferay-ui:message key="the-friendly-url-may-conflict-with-another-page" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.TOO_DEEP %>">
			<liferay-ui:message key="the-friendly-url-has-too-many-slashes" />
		</c:if>

		<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.TOO_SHORT %>">
			<liferay-ui:message key="please-enter-a-friendly-url-that-is-at-least-two-characters-long" />
		</c:if>
	</liferay-ui:error>
</c:if>

<liferay-ui:error key="resetMergeFailCountAndMerge" message="unable-to-reset-the-failure-counter-and-propagate-the-changes" />

<aui:fieldset>
	<c:choose>
		<c:when test="<%= !group.isLayoutPrototype() %>">
			<aui:input name="name" />

			<aui:input label="html-title" name="title" />

			<c:choose>
				<c:when test="<%= PortalUtil.isLayoutFriendliable(selLayout) %>">
					<aui:field-wrapper cssClass="input-prepend input-append" helpMessage='<%= LanguageUtil.format(pageContext, "for-example-x", "<em>/news</em>") %>' label="friendly-url" name="friendlyURL">
						<span class="add-on"><liferay-ui:message key="<%= friendlyURLBase.toString() %>" /></span>

						<liferay-ui:input-localized name="friendlyURL" xml="<%= selLayout.getFriendlyURLsXML() %>" />
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? selLayout.getFriendlyURL() : StringPool.BLANK %>" />
				</c:otherwise>
			</c:choose>

			<aui:input helpMessage="if-checked-this-page-wont-show-up-in-the-navigation-menu" name="hidden" />

			<c:if test="<%= group.isLayoutSetPrototype() %>">

				<%
				LayoutSetPrototype layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(group.getClassPK());

				boolean layoutSetPrototypeUpdateable = GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);
				boolean layoutUpdateable = GetterUtil.getBoolean(selLayoutTypePortlet.getTypeSettingsProperty("layoutUpdateable"), true);
				%>

				<aui:input disabled="<%= !layoutSetPrototypeUpdateable %>" helpMessage="allow-site-administrators-to-modify-this-page-for-their-site-help" label="allow-site-administrators-to-modify-this-page-for-their-site" name="layoutUpdateable" type="checkbox" value="<%= layoutUpdateable %>" />
			</c:if>
		</c:when>
		<c:otherwise>
			<aui:input name='<%= "name_" + defaultLanguageId %>' type="hidden" value="<%= selLayout.getName(defaultLocale) %>" />
			<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? selLayout.getFriendlyURL() : StringPool.BLANK %>" />
		</c:otherwise>
	</c:choose>

	<c:if test="<%= Validator.isNotNull(selLayout.getLayoutPrototypeUuid()) %>">

		<%
		LayoutPrototype layoutPrototype = LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuidAndCompanyId(selLayout.getLayoutPrototypeUuid(), company.getCompanyId());
		%>

		<aui:input name="layoutPrototypeUuid" type="hidden" value="<%= selLayout.getLayoutPrototypeUuid() %>" />

		<aui:input label='<%= LanguageUtil.format(pageContext, "automatically-apply-changes-done-to-the-page-template-x", HtmlUtil.escape(layoutPrototype.getName(user.getLocale()))) %>' name="layoutPrototypeLinkEnabled" type="checkbox" value="<%= selLayout.isLayoutPrototypeLinkEnabled() %>" />

		<div class='<%= selLayout.isLayoutPrototypeLinkEnabled() ? "" : "hide" %>' id="<portlet:namespace/>layoutPrototypeMergeAlert">

			<%
			request.setAttribute("edit_layout_prototype.jsp-layoutPrototype", layoutPrototype);
			request.setAttribute("edit_layout_prototype.jsp-redirect", currentURL);
			request.setAttribute("edit_layout_prototype.jsp-selPlid", String.valueOf(selLayout.getPlid()));
			%>

			<liferay-util:include page="/html/portlet/layout_prototypes/merge_alert.jsp" />
		</div>
	</c:if>

	<div class="<%= selLayout.isLayoutPrototypeLinkEnabled() ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />typeOptions">
		<aui:select name="type">

			<%
			for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
				if (PropsValues.LAYOUT_TYPES[i].equals("article") && (group.isLayoutPrototype() || group.isLayoutSetPrototype())) {
					continue;
				}
			%>

				<aui:option disabled="<%= selLayout.isFirstParent() && !PortalUtil.isLayoutFirstPageable(PropsValues.LAYOUT_TYPES[i]) %>" label='<%= "layout.types." + PropsValues.LAYOUT_TYPES[i] %>' selected="<%= selLayout.getType().equals(PropsValues.LAYOUT_TYPES[i]) %>" value="<%= PropsValues.LAYOUT_TYPES[i] %>" />

			<%
			}
			%>

		</aui:select>

		<div id="<portlet:namespace />layoutTypeForm">

			<%
			for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
				String curLayoutType = PropsValues.LAYOUT_TYPES[i];

				if (PropsValues.LAYOUT_TYPES[i].equals("article") && (group.isLayoutPrototype() || group.isLayoutSetPrototype())) {
					continue;
				}
			%>

				<div class="layout-type-form layout-type-form-<%= curLayoutType %><aui:spacer /><%= selLayout.getType().equals(PropsValues.LAYOUT_TYPES[i]) ? "" : "hide" %>">

					<%
					request.setAttribute(WebKeys.SEL_LAYOUT, selLayout);
					%>

					<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(curLayoutType) %>" />
				</div>

			<%
			}
			%>

		</div>
	</div>
</aui:fieldset>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />layoutPrototypeLinkEnabledCheckbox','<portlet:namespace />layoutPrototypeMergeAlert');
	Liferay.Util.toggleBoxes('<portlet:namespace />layoutPrototypeLinkEnabledCheckbox','<portlet:namespace />typeOptions', true);
</aui:script>

<aui:script use="aui-base">
	var templateLink = A.one('#templateLink');

	function toggleLayoutTypeFields(type) {
		var currentType = 'layout-type-form-' + type;

		var typeFormContainer = A.one('#<portlet:namespace />layoutTypeForm');

		typeFormContainer.all('.layout-type-form').each(
			function(item, index, collection) {
				var visible = item.hasClass(currentType);

				var disabled = !visible;

				item.toggle(visible);

				item.all('input, select, textarea').set('disabled', disabled);
			}
		);

		if (templateLink) {
			templateLink.toggle(type == 'portlet');
		}
	}

	toggleLayoutTypeFields('<%= selLayout.getType() %>');

	var typeSelector = A.one('#<portlet:namespace />type');

	if (typeSelector) {
		typeSelector.on(
			'change',
			function(event) {
				var type = event.currentTarget.val();

				toggleLayoutTypeFields(type);

				Liferay.fire(
					'<portlet:namespace />toggleLayoutTypeFields',
					{
						type: type
					}
				);
			}
		);
	}
</aui:script>