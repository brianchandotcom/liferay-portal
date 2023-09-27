<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="layout"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<c:if test="<%= Validator.isNotNull(selLayout.getLayoutPrototypeUuid()) %>">

	<%
	LayoutPrototype layoutPrototype = LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuidAndCompanyId(selLayout.getLayoutPrototypeUuid(), company.getCompanyId());
	%>

	<aui:input name="applyLayoutPrototype" type="hidden" value="<%= false %>" />
	<aui:input name="layoutPrototypeUuid" type="hidden" value="<%= selLayout.getLayoutPrototypeUuid() %>" />

	<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "inheritChangesDescription" %>' label="inherit-changes" labelCssClass="font-weight-normal" name="layoutPrototypeLinkEnabled" type="checkbox" value="<%= selLayout.isLayoutPrototypeLinkEnabled() %>" wrapperCssClass="c-mb-2" />

	<p class="text-3 text-secondary" id="<portlet:namespace />inheritChangesDescription">
		<liferay-ui:message arguments="<%= HtmlUtil.escape(layoutPrototype.getName(user.getLocale())) %>" key="if-enabled-this-page-will-inherit-changes-made-to-the-x-page-template" />
	</p>

	<clay:alert
		cssClass='<%= selLayout.isLayoutPrototypeLinkActive() ? "layout-prototype-info-message" : "layout-prototype-info-message hide" %>'
		displayType="warning"
	>
		<liferay-ui:message arguments='<%= new String[] {"inherit-changes", "general"} %>' key="some-page-settings-are-unavailable-because-x-is-enabled" translateArguments="<%= true %>" />
	</clay:alert>

	<div class="<%= selLayout.isLayoutPrototypeLinkEnabled() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />layoutPrototypeMergeAlert">

		<%
		request.setAttribute("edit_layout_prototype.jsp-layoutPrototype", layoutPrototype);
		request.setAttribute("edit_layout_prototype.jsp-redirect", currentURL);
		request.setAttribute("edit_layout_prototype.jsp-selPlid", String.valueOf(selLayout.getPlid()));
		%>

		<liferay-util:include page="/layout_merge_alert.jsp" servletContext="<%= application %>" />
	</div>
</c:if>

<c:if test="<%= !selLayout.isTypeAssetDisplay() && !selLayout.isTypeContent() %>">
	<div class="<%= selLayout.isLayoutPrototypeLinkActive() ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />typeOptions">
		<liferay-util:include page="/layout_type_resources.jsp" servletContext="<%= application %>">
			<liferay-util:param name="id" value="<%= selLayout.getType() %>" />
			<liferay-util:param name="type" value="<%= selLayout.getType() %>" />
		</liferay-util:include>
	</div>
</c:if>

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