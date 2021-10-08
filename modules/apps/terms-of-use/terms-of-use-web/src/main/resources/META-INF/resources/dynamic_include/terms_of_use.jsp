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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.terms.of.use.web.internal.constants.TermsOfUseWebKeys" %>

<aui:script position="inline">
	Liferay.Util.openModal({
		bodyHTML:
			'<%= HtmlUtil.escapeJS((String)request.getAttribute(TermsOfUseWebKeys.MODAL_BODY_HTML)) %>',
		buttons: [
			{
				displayType: 'primary',
				label: '<liferay-ui:message key="done" />',
				onClick: function ({processClose}) {
					Liferay.Util.fetch(
						'<%= PortalUtil.getPortalURL(request) + "/o/terms-of-use/confirm/" %>',
						{method: 'post'}
					);

					processClose();
				},
			},
		],
		size: 'lg',
		title: '<liferay-ui:message key="terms-of-use" />',
	});
</aui:script>