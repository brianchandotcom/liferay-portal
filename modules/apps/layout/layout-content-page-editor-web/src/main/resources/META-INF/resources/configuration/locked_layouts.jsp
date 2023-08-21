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
	cssClass="c-mt-2"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<p class="text-secondary"><liferay-ui:message key="pages-that-are-already-being-edited-get-locked-for-other-users-so-just-one-user-can-edit-at-a-time" /></p>
	</clay:content-col>
</clay:content-row>

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="automatic-unlocking" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row>
		<clay:content-col
			expand="<%= true %>"
		>
			<p class="text-secondary"><liferay-ui:message key="configure-how-often-to-review-which-pages-are-locked-and-how-long-a-page-can-be-locked-without-any-autosave" /></p>
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
			<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "lockReviewFrequencyHiddenDescription" %>' label="lock-review-frequency" name="lockReviewFrequency" required="<%= true %>" type="number" value="<%= lockedLayoutsConfigurationDisplayContext.getLockReviewFrequency() %>">
				<aui:validator name="number" />
				<aui:validator name="min">1</aui:validator>
				<aui:validator name="max">99999</aui:validator>
			</aui:input>

			<p class="text-3 text-secondary" id="<portlet:namespace />lockReviewFrequencyHiddenDescription"><liferay-ui:message key="set-in-minutes.-valid-values-between-1-and-99.999" /></p>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "timeWithoutAutosaveHiddenDescription" %>' label="time-without-autosave" name="timeWithoutAutosave" required="<%= true %>" type="number" value="<%= lockedLayoutsConfigurationDisplayContext.getTimeWithoutAutosave() %>">
				<aui:validator name="number" />
				<aui:validator name="min">5</aui:validator>
				<aui:validator name="max">99999</aui:validator>
			</aui:input>

			<p class="text-3 text-secondary" id="<portlet:namespace />timeWithoutAutosaveHiddenDescription"><liferay-ui:message key="set-in-minutes.-valid-values-between-1-and-99.999" /></p>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>