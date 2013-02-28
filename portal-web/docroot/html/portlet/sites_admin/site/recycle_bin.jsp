<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

boolean groupTrashEnabled = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "trashEnabled", true);

int trashEntriesMaxAge = PropertiesParamUtil.getInteger(groupTypeSettings, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<aui:fieldset>
	<aui:input class="aui-field-label" id="trashEnabled" label="enable-recycle-bin" name="trashEnabled" type="checkbox" value="<%= groupTrashEnabled %>" />

	<div class="trash-entries-max-age">
		<aui:input disabled="<%= !groupTrashEnabled %>" label="number-of-days-that-files-will-be-kept-in-the-recycle-bin" name="trashEntriesMaxAge" type="text" value="<%= trashEntriesMaxAge %>">
			<aui:validator name="min">1</aui:validator>
		</aui:input>
	</div>

</aui:fieldset>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />trashEnabledCheckbox').on(
		'change',
		function(event) {
			var target = event.currentTarget;

			var trashEnabled = target.attr('checked');

			if (!trashEnabled) {
				if (!confirm('<%= HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "disabling-the-recycle-bin-will-prevent-the-restoring-of-content-that-has-been-moved-to-the-recycle-bin")) %>')) {
					target.attr('checked', true);

					trashEnabled = true;
				}
			}

			A.one('#<portlet:namespace />trashEntriesMaxAge').attr('disabled', !trashEnabled);
		}
	);
</aui:script>