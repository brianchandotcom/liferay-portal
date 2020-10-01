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

<%@ include file="/publications/init.jsp" %>

<%
ViewDiscardDisplayContext viewDiscardDisplayContext = (ViewDiscardDisplayContext)request.getAttribute(CTWebKeys.VIEW_DISCARD_DISPLAY_CONTEXT);

portletDisplay.setURLBack(viewDiscardDisplayContext.getRedirectURL());
portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(LanguageUtil.get(request, "discard-changes"));
%>

<clay:container-fluid
	cssClass="publications-discard-container"
>
	<div class="sheet">
		<clay:sheet-section>
			<h2 class="sheet-title"><liferay-ui:message key="discarded-changes" /></h2>

			<div class="sheet-text">
				<liferay-ui:message key="the-following-changes-will-be-discarded" />
			</div>

			<div>
				<react:component
					data="<%= viewDiscardDisplayContext.getReactData() %>"
					module="publications/js/ChangeTrackingDiscardView"
				/>
			</div>
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button href="<%= viewDiscardDisplayContext.getSubmitURL() %>" primary="true" value="discard" />

			<aui:button href="<%= viewDiscardDisplayContext.getRedirectURL() %>" type="cancel" />
		</clay:sheet-footer>
	</div>
</clay:container-fluid>