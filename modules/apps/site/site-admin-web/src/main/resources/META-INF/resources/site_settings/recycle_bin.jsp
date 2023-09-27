<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
UnicodeProperties groupTypeSettingsUnicodeProperties = (UnicodeProperties)request.getAttribute("site.groupTypeSettings");

boolean groupTrashEnabled = PropertiesParamUtil.getBoolean(groupTypeSettingsUnicodeProperties, request, "trashEnabled", true);
int trashEntriesMaxAge = PropertiesParamUtil.getInteger(groupTypeSettingsUnicodeProperties, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<aui:input id="trashEnabled" inlineLabel="right" label="enable-recycle-bin" labelCssClass="simple-toggle-switch" name="trashEnabled" type="toggle-switch" value="<%= groupTrashEnabled %>" />

<aui:field-wrapper cssClass="form-group">
	<aui:input disabled="<%= !groupTrashEnabled %>" label="trash-entries-max-age" name="trashEntriesMaxAge" type="text" value="<%= ((trashEntriesMaxAge % 1) == 0) ? GetterUtil.getInteger(trashEntriesMaxAge) : String.valueOf(trashEntriesMaxAge) %>">
		<aui:validator name="min"><%= PropsValues.TRASH_ENTRY_CHECK_INTERVAL %></aui:validator>
	</aui:input>

	<span class="small text-secondary"><liferay-ui:message key="trash-entries-max-age-help" /></span>
</aui:field-wrapper>

<script>
	var trashEnabledCheckbox = document.getElementById(
		'<portlet:namespace />trashEnabled'
	);

	if (trashEnabledCheckbox) {
		var trashEnabledDefault = trashEnabledCheckbox.checked;

		trashEnabledCheckbox.addEventListener('change', (event) => {
			var trashEnabled = trashEnabledCheckbox.checked;

			var trashEntriesMaxAge = document.getElementById(
				'<portlet:namespace />trashEntriesMaxAge'
			);

			if (!trashEnabled && trashEnabledDefault) {
				Liferay.Util.openConfirmModal({
					message:
						'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "disabling-the-recycle-bin-prevents-the-restoring-of-content-that-has-been-moved-to-the-recycle-bin")) %>',
					onConfirm: (isConfirmed) => {
						if (!isConfirmed) {
							trashEnabledCheckbox.checked = true;

							trashEnabled = true;

							if (trashEntriesMaxAge) {
								Liferay.Util.toggleDisabled(
									trashEntriesMaxAge,
									!trashEnabled
								);
							}
						}
					},
				});
			}
			else {
				if (trashEntriesMaxAge) {
					Liferay.Util.toggleDisabled(trashEntriesMaxAge, !trashEnabled);
				}
			}
		});
	}
</script>