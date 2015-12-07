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
Group group = layoutsAdminDisplayContext.getGroup();
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

LayoutType selLayoutType = selLayout.getLayoutType();

String[] types = LayoutTypeControllerTracker.getTypes();

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
%>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<%
StringBuilder friendlyURLBase = new StringBuilder();
%>

<c:if test="<%= !group.isLayoutPrototype() && selLayoutType.isURLFriendliable() %>">

	<%
	friendlyURLBase.append(themeDisplay.getPortalURL());

	LayoutSet layoutSet = selLayout.getLayoutSet();

	String virtualHostname = layoutSet.getVirtualHostname();

	if (Validator.isNull(virtualHostname) || (friendlyURLBase.indexOf(virtualHostname) == -1)) {
		friendlyURLBase.append(group.getPathFriendlyURL(layoutsAdminDisplayContext.isPrivateLayout(), themeDisplay));
		friendlyURLBase.append(group.getFriendlyURL());
	}
	%>

	<liferay-ui:error exception="<%= LayoutFriendlyURLException.class %>" focusField="friendlyURL">

		<%
		Locale exceptionLocale = null;
		LayoutFriendlyURLException lfurle = (LayoutFriendlyURLException)errorException;
		%>

		<%@ include file="/error_friendly_url_exception.jspf" %>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= LayoutFriendlyURLsException.class %>" focusField="friendlyURL">

		<%
		LayoutFriendlyURLsException lfurlse = (LayoutFriendlyURLsException)errorException;

		Map<Locale, Exception> localizedExceptionsMap = lfurlse.getLocalizedExceptionsMap();

		for (Map.Entry<Locale, Exception> entry : localizedExceptionsMap.entrySet()) {
			Locale exceptionLocale = entry.getKey();
			LayoutFriendlyURLException lfurle = (LayoutFriendlyURLException)entry.getValue();
		%>

			<%@ include file="/error_friendly_url_exception.jspf" %>

		<%
		}
		%>

	</liferay-ui:error>
</c:if>

<liferay-ui:error key="resetMergeFailCountAndMerge" message="unable-to-reset-the-failure-counter-and-propagate-the-changes" />

<c:choose>
	<c:when test="<%= !group.isLayoutPrototype() %>">
		<aui:input name="name" />

		<div class="form-group">
			<aui:input helpMessage="if-this-is-checked-this-page-does-not-show-up-in-the-navigation-menu" label="hide-from-navigation-menu" name="hidden" />
		</div>

		<c:choose>
			<c:when test="<%= selLayoutType.isURLFriendliable() %>">
				<aui:field-wrapper cssClass="input-flex-add-on" helpMessage='<%= LanguageUtil.format(request, "for-example-x", "<em>/news</em>", false) %>' label="friendly-url" name="friendlyURL">
					<span class="input-group-addon" id="<portlet:namespace />urlBase"><liferay-ui:message key="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>" /></span>

					<liferay-ui:input-localized cssClass="form-control" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" name="friendlyURL" xml="<%= selLayout.getFriendlyURLsXML() %>" />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? selLayout.getFriendlyURL() : StringPool.BLANK %>" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= group.isLayoutSetPrototype() %>">

			<%
			LayoutSetPrototype layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(group.getClassPK());

			boolean layoutSetPrototypeUpdateable = GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);
			boolean layoutUpdateable = GetterUtil.getBoolean(selLayoutType.getTypeSettingsProperty("layoutUpdateable"), true);
			%>

			<aui:input disabled="<%= !layoutSetPrototypeUpdateable %>" helpMessage="allow-site-administrators-to-modify-this-page-for-their-site-help" label="allow-site-administrators-to-modify-this-page-for-their-site" name="TypeSettingsProperties--layoutUpdateable--" type="checkbox" value="<%= layoutUpdateable %>" />
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

	<aui:input label='<%= LanguageUtil.format(request, "automatically-apply-changes-done-to-the-page-template-x", HtmlUtil.escape(layoutPrototype.getName(user.getLocale())), false) %>' name="layoutPrototypeLinkEnabled" type="checkbox" value="<%= selLayout.isLayoutPrototypeLinkEnabled() %>" />

	<div class='<%= selLayout.isLayoutPrototypeLinkEnabled() ? "" : "hide" %>' id="<portlet:namespace/>layoutPrototypeMergeAlert">

		<%
		request.setAttribute("edit_layout_prototype.jsp-layoutPrototype", layoutPrototype);
		request.setAttribute("edit_layout_prototype.jsp-redirect", currentURL);
		request.setAttribute("edit_layout_prototype.jsp-selPlid", String.valueOf(selLayout.getPlid()));
		%>

		<liferay-util:include page="/layout_merge_alert.jsp" servletContext="<%= application %>" />
	</div>
</c:if>

<div class="<%= selLayout.isLayoutPrototypeLinkEnabled() ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />typeOptions">
	<aui:select name="type">

		<%
		for (String type : types) {
			if (type.equals("article") && (group.isLayoutPrototype() || group.isLayoutSetPrototype())) {
				continue;
			}

			LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

			if (!layoutTypeController.isInstanceable()) {
				continue;
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
		%>

			<aui:option disabled="<%= selLayout.isFirstParent() && !layoutTypeController.isFirstPageable() %>" label='<%= LanguageUtil.get(request, resourceBundle, "layout.types." + type) %>' selected="<%= selLayout.getType().equals(type) %>" value="<%= type %>" />

		<%
			}
		%>

	</aui:select>

	<div id="<portlet:namespace />layoutTypeForm">

		<%
		for (String type : types) {
			if (type.equals("article") && (group.isLayoutPrototype() || group.isLayoutSetPrototype())) {
				continue;
			}

			LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

			if (!layoutTypeController.isInstanceable()) {
				continue;
			}
		%>

			<div class="layout-type-form layout-type-form-<%= type %> <%= selLayout.getType().equals(type) ? "" : "hide" %>">

				<%
				request.setAttribute(WebKeys.SEL_LAYOUT, selLayout);

				DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(request, Collections.singletonMap("idPrefix", new String[] {"details"}));
				%>

				<%= layoutTypeController.includeEditContent(dynamicServletRequest, response, selLayout) %>

			</div>

		<%
		}
		%>

	</div>
</div>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />layoutPrototypeLinkEnabled', '<portlet:namespace />layoutPrototypeMergeAlert');
	Liferay.Util.toggleBoxes('<portlet:namespace />layoutPrototypeLinkEnabled', '<portlet:namespace />typeOptions', true);
</aui:script>

<aui:script sandbox="<%= true %>">
	function toggleLayoutTypeFields(type) {
		var currentType = 'layout-type-form-' + type;

		$('#<portlet:namespace />layoutTypeForm').find('.layout-type-form').each(
			function(index, item) {
				item = $(item);

				var visible = item.hasClass(currentType);

				item.toggleClass('hide', !visible);

				item.find('input, select, textarea').prop('disabled', !visible);
			}
		);
	}

	toggleLayoutTypeFields('<%= HtmlUtil.escapeJS(selLayout.getType()) %>');

	$('#<portlet:namespace />type').on(
		'change',
		function(event) {
			var type = $(event.currentTarget).val();

			toggleLayoutTypeFields(type);

			Liferay.fire(
				'<portlet:namespace />toggleLayoutTypeFields',
				{
					type: type
				}
			);
		}
	);

	var friendlyURLBase = '<%= friendlyURLBase.toString() %>';

	if (friendlyURLBase.length > 40) {
		$('#<portlet:namespace />urlBase').on(
			'mouseenter',
			function(event) {
				Liferay.Portal.ToolTip.show(event.currentTarget, friendlyURLBase);
			}
		);
	}
</aui:script>