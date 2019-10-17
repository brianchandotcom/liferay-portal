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
boolean trashEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TRASH_ENABLED);
%>

<h3><liferay-ui:message key="recycle-bin" /></h3>

<aui:fieldset>
	<aui:input helpMessage="enable-recycle-bin-default" id="trashEnabled" label="enable-recycle-bin" name='<%= "settings--" + PropsKeys.TRASH_ENABLED + "--" %>' type="checkbox" value="<%= trashEnabled %>" />
</aui:fieldset>

<script>
	(function() {
		var trashEnabledCheckbox = document.getElementById(
			'<portlet:namespace />trashEnabled'
		);

		if (trashEnabledCheckbox) {
			var trashEnabledDefault = trashEnabledCheckbox.checked;

			trashEnabledCheckbox.addEventListener('change', function(event) {
				if (!trashEnabledCheckbox.checked && trashEnabledDefault) {
					if (
						!confirm(
							'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "disabling-the-recycle-bin-prevents-the-restoring-of-content-that-has-been-moved-to-the-recycle-bin")) %>'
						)
					) {
						trashEnabledCheckbox.checked = true;
					}
				}
			});
		}
	})();
</script>