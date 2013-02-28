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
boolean trashEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TRASH_ENABLED);
%>

<h3><liferay-ui:message key="recycle-bin" /></h3>

<aui:fieldset>
	<aui:input class="aui-field-label" helpMessage="enable-it-by-default-while-allowing-site-administrators-to-disable-it-per-site" id="trashEnabled" label="enable-recycle-bin" name='<%= "settings--" + PropsKeys.TRASH_ENABLED + "--" %>' type="checkbox" value="<%= trashEnabled %>" />
</aui:fieldset>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />trashEnabledCheckbox').on(
		'change',
		function(event) {
			var target = event.currentTarget;

			var trashEnabled = target.attr('checked');

			if (!trashEnabled) {
				if (!confirm('<%= HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "disabling-the-recycle-bin-will-affect-any-existing-sites-that-have-it-enabled-and-will-prevent-the-restoring-of-content-that-has-been-moved-to-the-recycle-bin")) %>')) {
					target.attr('checked', true);

					trashEnabled = true;
				}
			}
		}
	);
</aui:script>