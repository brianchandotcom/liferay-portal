<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ReschedulePublicationDisplayContext reschedulePublicationDisplayContext = (ReschedulePublicationDisplayContext)request.getAttribute(CTWebKeys.RESCHEDULE_PUBLICATION_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(reschedulePublicationDisplayContext.getRedirect());

renderResponse.setTitle(reschedulePublicationDisplayContext.getTitle());
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<react:component
		module="publications/js/views/ChangeTrackingRescheduleView"
		props="<%= reschedulePublicationDisplayContext.getReactData() %>"
	/>
</clay:container-fluid>