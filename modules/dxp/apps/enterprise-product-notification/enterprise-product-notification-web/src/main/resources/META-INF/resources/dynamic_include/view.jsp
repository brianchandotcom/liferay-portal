<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
						'<%= PortalUtil.getPortalURL(request) + PortalUtil.getPathModule() + "/enterprise-product-notification/confirm/" %>',
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