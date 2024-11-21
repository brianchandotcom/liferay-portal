<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<clay:navigation-bar
	navigationItems="<%= publicationsDisplayContext.getViewNavigationItems() %>"
/>

<div>
	<react:component
		module="{PublicationsExpiredInfoPanel} from change-tracking-web"
	/>
</div>

<clay:container-fluid>
	<frontend-data-set:headless-display
		apiURL="<%= publicationsDisplayContext.getAPIURL() %>"
		creationMenu="<%= publicationsDisplayContext.getCreationMenu() %>"
		fdsActionDropdownItems="<%= publicationsDisplayContext.getFDSActionDropdownItems() %>"
		id="<%= PublicationsFDSNames.PUBLICATIONS_ONGOING %>"
		propsTransformer="{DeletePublicationsFDSPropsTransformer} from change-tracking-web"
	/>
</clay:container-fluid>

<aui:script>
	(function () {
		const sessionKey = 'com.liferay.change.tracking.web.successMessage';

		const successMessage = Liferay.Util.SessionStorage.getItem(
			sessionKey,
			Liferay.Util.SessionStorage.TYPES.NECESSARY
		);

		if (successMessage) {
			Liferay.Util.openToast({
				message: successMessage,
				type: 'success',
			});

			Liferay.Util.SessionStorage.removeItem(sessionKey);
		}
	})();
</aui:script>

<%
CTLocalizedException ctLocalizedException = null;

if (MultiSessionErrors.contains(liferayPortletRequest, CTLocalizedException.class.getName())) {
	ctLocalizedException = (CTLocalizedException)MultiSessionErrors.get(liferayPortletRequest, CTLocalizedException.class.getName());
}
%>

<c:if test="<%= ctLocalizedException != null %>">
	<aui:script>
		Liferay.Util.openToast({
			autoClose: 10000,
			message:
				'<%= HtmlUtil.escapeJS(ctLocalizedException.formatMessage(resourceBundle)) %>',
			title: '<liferay-ui:message key="error" />:',
			type: 'danger',
		});
	</aui:script>
</c:if>