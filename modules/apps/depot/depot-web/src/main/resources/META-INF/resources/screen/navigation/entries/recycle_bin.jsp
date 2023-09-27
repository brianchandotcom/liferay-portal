<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = depotEntry.getGroup();

UnicodeProperties typeSettingsUnicodeProperties = group.getTypeSettingsProperties();

boolean groupTrashEnabled = PropertiesParamUtil.getBoolean(typeSettingsUnicodeProperties, request, "trashEnabled", true);
int trashEntriesMaxAge = PropertiesParamUtil.getInteger(typeSettingsUnicodeProperties, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(depotEntry.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<liferay-frontend:fieldset
	collapsible="<%= true %>"
	cssClass="panel-group-flush"
	label='<%= LanguageUtil.get(request, "recycle-bin") %>'
>
	<aui:input id="trashEnabled" inlineLabel="right" label="enable-recycle-bin" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--trashEnabled--" type="toggle-switch" value="<%= groupTrashEnabled %>" />

	<div class="trash-entries-max-age">
		<aui:input disabled="<%= !groupTrashEnabled %>" helpMessage="trash-entries-max-age-help" label="trash-entries-max-age" name="TypeSettingsProperties--trashEntriesMaxAge--" type="text" value="<%= ((trashEntriesMaxAge % 1) == 0) ? GetterUtil.getInteger(trashEntriesMaxAge) : String.valueOf(trashEntriesMaxAge) %>">
			<aui:validator name="min"><%= PropsValues.TRASH_ENTRY_CHECK_INTERVAL %></aui:validator>
		</aui:input>
	</div>

	<script>
		var trashEnabledCheckbox = document.getElementById(
			'<portlet:namespace />trashEnabled'
		);

		if (trashEnabledCheckbox) {
			var trashEnabledDefault = trashEnabledCheckbox.checked;

			trashEnabledCheckbox.addEventListener('change', (event) => {
				var trashEnabled = trashEnabledCheckbox.checked;

				if (!trashEnabled && trashEnabledDefault) {
					var trashEntriesMaxAge = document.getElementById(
						'<portlet:namespace />trashEntriesMaxAge'
					);

					Liferay.Util.openConfirmModal({
						message:
							'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "disabling-the-recycle-bin-prevents-the-restoring-of-content-that-has-been-moved-to-the-recycle-bin")) %>',
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								if (trashEntriesMaxAge) {
									Liferay.Util.toggleDisabled(
										trashEntriesMaxAge,
										!trashEnabled
									);
								}
							}
							else {
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
				}
			});
		}
	</script>
</liferay-frontend:fieldset>