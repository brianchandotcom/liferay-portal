<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.enterprise.product.notification.web.internal.constants.EPNWebKeys" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<aui:script position="inline">
	Liferay.Util.openModal({
		bodyHTML:
			'<%= HtmlUtil.escapeJS((String)request.getAttribute(EPNWebKeys.MODAL_BODY_HTML)) %>',
		buttons: [
			{
				displayType: 'primary',
				label: '<liferay-ui:message key="done" />',
				onClick: function ({processClose}) {
					Liferay.Util.fetch(
						'<%= PortalUtil.getPortalURL(request) + PortalUtil.getPathModule() + "/com-liferay-enterprise-product-notification-web/confirm/" %>',
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