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

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<%
StringBuilder friendlyURLBase = new StringBuilder();
%>

<c:if test="<%= !group.isLayoutPrototype() && selLayoutType.isURLFriendliable() && !layoutsAdminDisplayContext.isDraft() %>">

	<%
	friendlyURLBase.append(themeDisplay.getPortalURL());

	LayoutSet layoutSet = selLayout.getLayoutSet();

	String virtualHostname = layoutSet.getVirtualHostname();

	if (Validator.isNull(virtualHostname) || (friendlyURLBase.indexOf(virtualHostname) == -1)) {
		friendlyURLBase.append(group.getPathFriendlyURL(layoutsAdminDisplayContext.isPrivateLayout(), themeDisplay));
		friendlyURLBase.append(HttpUtil.decodeURL(group.getFriendlyURL()));
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
		<c:if test="<%= !layoutsAdminDisplayContext.isDraft() %>">
			<aui:input name="name" />

			<div class="form-group">
				<aui:input helpMessage="hidden-from-navigation-menu-widget-help-message" label="hidden-from-navigation-menu-widget" name="hidden" type="toggle-switch" value="<%= selLayout.isHidden() %>" />
			</div>
		</c:if>

		<c:choose>
			<c:when test="<%= selLayoutType.isURLFriendliable() && !layoutsAdminDisplayContext.isDraft() %>">
				<div class="form-group friendly-url">
					<label for="<portlet:namespace />friendlyURL"><liferay-ui:message key="friendly-url" /> <liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>/news</em>", false) %>' /></label>

					<liferay-ui:input-localized
						defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
						inputAddon="<%= friendlyURLBase.toString() %>"
						name="friendlyURL"
						xml="<%= HttpUtil.decodeURL(selLayout.getFriendlyURLsXML()) %>"
					/>
				</div>
			</c:when>
			<c:otherwise>
				<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? HttpUtil.decodeURL(selLayout.getFriendlyURL()) : StringPool.BLANK %>" />
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
		<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? HttpUtil.decodeURL(selLayout.getFriendlyURL()) : StringPool.BLANK %>" />
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(selLayout.getLayoutPrototypeUuid()) %>">

	<%
	LayoutPrototype layoutPrototype = LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuidAndCompanyId(selLayout.getLayoutPrototypeUuid(), company.getCompanyId());
	%>

	<aui:input name="applyLayoutPrototype" type="hidden" value="<%= false %>" />
	<aui:input name="layoutPrototypeUuid" type="hidden" value="<%= selLayout.getLayoutPrototypeUuid() %>" />

	<aui:input helpMessage='<%= LanguageUtil.format(request, "if-enabled-this-page-will-inherit-changes-made-to-the-x-page-template", HtmlUtil.escape(layoutPrototype.getName(user.getLocale())), false) %>' label="inherit-changes" name="layoutPrototypeLinkEnabled" type="toggle-switch" value="<%= selLayout.isLayoutPrototypeLinkEnabled() %>" />

	<div class="alert alert-warning layout-prototype-info-message <%= selLayout.isLayoutPrototypeLinkActive() ? StringPool.BLANK : "hide" %>">
		<liferay-ui:message arguments='<%= new String[] {"inherit-changes", "general"} %>' key="some-page-settings-are-unavailable-because-x-is-enabled" translateArguments="<%= true %>" />
	</div>

	<div class="<%= selLayout.isLayoutPrototypeLinkEnabled() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />layoutPrototypeMergeAlert">

		<%
		request.setAttribute("edit_layout_prototype.jsp-layoutPrototype", layoutPrototype);
		request.setAttribute("edit_layout_prototype.jsp-redirect", currentURL);
		request.setAttribute("edit_layout_prototype.jsp-selPlid", String.valueOf(selLayout.getPlid()));
		%>

		<liferay-util:include page="/layout_merge_alert.jsp" servletContext="<%= application %>" />
	</div>
</c:if>

<div class="<%= selLayout.isLayoutPrototypeLinkActive() ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />typeOptions">
	<liferay-util:include page="/layout_type_resources.jsp" servletContext="<%= application %>">
		<liferay-util:param name="id" value="<%= selLayout.getType() %>" />
		<liferay-util:param name="type" value="<%= selLayout.getType() %>" />
	</liferay-util:include>
</div>

<aui:script require="metal-dom/src/dom as dom">
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />layoutPrototypeLinkEnabled',
		'<portlet:namespace />layoutPrototypeMergeAlert'
	);
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />layoutPrototypeLinkEnabled',
		'<portlet:namespace />typeOptions',
		true
	);

	var layoutPrototypeLinkEnabled = document.getElementById(
		'<portlet:namespace />layoutPrototypeLinkEnabled'
	);

	if (layoutPrototypeLinkEnabled) {
		layoutPrototypeLinkEnabled.addEventListener('change', function(event) {
			var layoutPrototypeLinkChecked = event.currentTarget.checked;

			var layoutPrototypeInfoMessage = document.querySelector(
				'.layout-prototype-info-message'
			);

			var applyLayoutPrototype = document.getElementById(
				'<portlet:namespace />applyLayoutPrototype'
			);

			if (layoutPrototypeInfoMessage) {
				if (layoutPrototypeLinkChecked) {
					layoutPrototypeInfoMessage.classList.remove('hide');

					applyLayoutPrototype.value = '<%= true %>';
				} else {
					layoutPrototypeInfoMessage.classList.add('hide');

					applyLayoutPrototype.value = '<%= false %>';
				}
			}

			var propagatableFields = document.querySelectorAll(
				'#<portlet:namespace />editLayoutFm .propagatable-field'
			);

			Array.prototype.forEach.call(propagatableFields, function(
				field,
				index
			) {
				Liferay.Util.toggleDisabled(field, layoutPrototypeLinkChecked);
			});
		});
	}
</aui:script>