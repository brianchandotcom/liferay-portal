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

String friendlyURLBase = StringPool.BLANK;

if (!group.isLayoutPrototype() && selLayoutType.isURLFriendliable() && !layoutsAdminDisplayContext.isDraft() && !selLayout.isSystem()) {
	friendlyURLBase = layoutsAdminDisplayContext.getFriendlyURLBase();
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<c:choose>
	<c:when test="<%= !group.isLayoutPrototype() %>">
		<c:if test="<%= !layoutsAdminDisplayContext.isDraft() && !selLayout.isSystem() %>">
			<aui:input ignoreRequestValue="<%= SessionErrors.isEmpty(liferayPortletRequest) %>" label="name" localized="<%= true %>" name="nameMapAsXML" required="<%= true %>" type="text" value="<%= layoutsAdminDisplayContext.getNameMapAsXML() %>" />

			<div class="form-group">
				<aui:input helpMessage="hidden-from-navigation-menu-widget-help-message" inlineLabel="right" label="hidden-from-navigation-menu-widget" labelCssClass="simple-toggle-switch" name="hidden" type="toggle-switch" value="<%= selLayout.isHidden() %>" />
			</div>
		</c:if>

		<c:choose>
			<c:when test="<%= selLayoutType.isURLFriendliable() && !layoutsAdminDisplayContext.isDraft() && !selLayout.isSystem() %>">
				<liferay-friendly-url:input
					className="<%= Layout.class.getName() %>"
					classPK="<%= selLayout.getPlid() %>"
					inputAddon="<%= friendlyURLBase %>"
					name="friendlyURL"
				/>
			</c:when>
			<c:otherwise>
				<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? HttpComponentsUtil.decodeURL(selLayout.getFriendlyURL()) : StringPool.BLANK %>" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= group.isLayoutSetPrototype() %>">

			<%
			LayoutSetPrototype layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(group.getClassPK());
			%>

			<c:if test='<%= (layoutSetPrototype != null) && GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true) %>'>
				<aui:input helpMessage="allow-site-administrators-to-modify-this-page-for-their-site-help" label="allow-site-administrators-to-modify-this-page-for-their-site" name="TypeSettingsProperties--layoutUpdateable--" type="checkbox" value='<%= GetterUtil.getBoolean(selLayoutType.getTypeSettingsProperty("layoutUpdateable"), true) %>' />
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>
		<aui:input name='<%= "nameMapAsXML_" + defaultLanguageId %>' type="hidden" value="<%= selLayout.getName(defaultLocale) %>" />
		<aui:input name="friendlyURL" type="hidden" value="<%= (selLayout != null) ? HttpComponentsUtil.decodeURL(selLayout.getFriendlyURL()) : StringPool.BLANK %>" />
	</c:otherwise>
</c:choose>

<clay:sheet-section
	cssClass="mb-5"
>
	<h3 class="mb-4 text-uppercase"><liferay-ui:message key="layout" /></h3>

	<c:if test="<%= Validator.isNotNull(selLayout.getLayoutPrototypeUuid()) %>">

		<%
		LayoutPrototype layoutPrototype = LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuidAndCompanyId(selLayout.getLayoutPrototypeUuid(), company.getCompanyId());
		%>

		<aui:input name="applyLayoutPrototype" type="hidden" value="<%= false %>" />
		<aui:input name="layoutPrototypeUuid" type="hidden" value="<%= selLayout.getLayoutPrototypeUuid() %>" />

		<aui:input helpMessage='<%= LanguageUtil.format(request, "if-enabled-this-page-will-inherit-changes-made-to-the-x-page-template", HtmlUtil.escape(layoutPrototype.getName(user.getLocale())), false) %>' inlineLabel="right" label="inherit-changes" labelCssClass="simple-toggle-switch" name="layoutPrototypeLinkEnabled" type="toggle-switch" value="<%= selLayout.isLayoutPrototypeLinkEnabled() %>" />

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
</clay:sheet-section>

<aui:script sandbox="<%= true %>">
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
		layoutPrototypeLinkEnabled.addEventListener('change', (event) => {
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
				}
				else {
					layoutPrototypeInfoMessage.classList.add('hide');

					applyLayoutPrototype.value = '<%= false %>';
				}
			}

			var propagatableFields = document.querySelectorAll(
				'#<portlet:namespace />editLayoutFm .propagatable-field'
			);

			Array.prototype.forEach.call(propagatableFields, (field, index) => {
				Liferay.Util.toggleDisabled(field, layoutPrototypeLinkChecked);
			});
		});
	}
</aui:script>