<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LockedLayoutsConfigurationDisplayContext lockedLayoutsConfigurationDisplayContext = (LockedLayoutsConfigurationDisplayContext)request.getAttribute(LockedLayoutsConfigurationDisplayContext.class.getName());
%>

<clay:content-row
	cssClass="c-mt-3"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<c:if test="<%= !lockedLayoutsConfigurationDisplayContext.hasConfiguration() %>">
			<clay:alert
				message="this-configuration-is-not-saved-yet.-the-values-shown-are-the-default"
			/>
		</c:if>

		<p class="text-secondary"><liferay-ui:message key="pages-that-are-already-being-edited-get-locked-for-other-users-to-ensure-that-a-page-can-only-be-edited-by-one-user-at-a-time" /></p>
	</clay:content-col>
</clay:content-row>

<clay:sheet-section
	aria-labelledby='<%= liferayPortletResponse.getNamespace() + "automaticUnlockingTitle" %>'
	role="group"
>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />automaticUnlockingTitle"><liferay-ui:message key="automatic-unlocking" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row>
		<clay:content-col
			expand="<%= true %>"
		>
			<p class="text-secondary"><liferay-ui:message key="set-how-long-a-page-can-remain-locked-without-being-autosaved" /></p>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<aui:input inlineLabel="right" label='<%= LanguageUtil.get(request, "allow-automatic-unlocking-process") %>' labelCssClass="simple-toggle-switch" name="allowAutomaticUnlockingProcess" type="toggle-switch" value="<%= lockedLayoutsConfigurationDisplayContext.isAllowAutomaticUnlockingProcess() %>" />
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "autosaveMinutesHiddenDescription" %>' label="autosave-minutes" name="autosaveMinutes" required="<%= true %>" type="number" value="<%= lockedLayoutsConfigurationDisplayContext.getAutosaveMinutes() %>" wrapperCssClass="c-mb-1">
				<aui:validator name="number" />
				<aui:validator name="min">1</aui:validator>
				<aui:validator name="max">99999</aui:validator>
			</aui:input>

			<p class="text-3 text-secondary" id="<portlet:namespace />autosaveMinutesHiddenDescription"><liferay-ui:message arguments='<%= new String[] {"1", "99999"} %>' key="set-a-value-in-minutes-between-x-and-x" translateArguments="<%= false %>" /></p>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<aui:script>
	const numericInput = document.getElementById(
		'<portlet:namespace />autosaveMinutes'
	);

	if (numericInput) {
		const keysNotAllowed = new Set(['e', '-']);

		numericInput.addEventListener('keydown', (event) => {
			if (keysNotAllowed.has(event.key)) {
				event.preventDefault();
			}
		});
	}
</aui:script>