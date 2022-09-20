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
RedirectPatternConfigurationDisplayContext redirectPatternConfigurationDisplayContext = (RedirectPatternConfigurationDisplayContext)request.getAttribute(RedirectPatternConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= redirectPatternConfigurationDisplayContext.getRedirectPatternConfigurationURL() %>" method="post" name="fm">
	<clay:sheet>
		<clay:sheet-header>
			<h2>
				<liferay-ui:message key="redirect-pattern-configuration-name" />
			</h2>

			<p class="text-default"><strong>
				<liferay-ui:message key="redirect-pattern-configuration-description" />
			</strong></p>
		</clay:sheet-header>

		<clay:sheet-section>
			<aui:input label="redirect-patterns" name="patterns" value="<%= StringUtil.merge(redirectPatternConfigurationDisplayContext.getPatterns(scopeGroupId)) %>" />

			<p class="text-muted">
				<liferay-ui:message key="redirect-patterns-description" />
			</p>
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button primary="<%= true %>" type="submit" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>