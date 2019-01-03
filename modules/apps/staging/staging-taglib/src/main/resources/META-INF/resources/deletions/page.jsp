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

<%@ include file="/deletions/init.jsp" %>

<c:if test="<%= cmd.equals(Constants.EXPORT) || cmd.equals(Constants.IMPORT) || cmd.equals(Constants.PUBLISH) %>">
	<aui:fieldset cssClass="options-group" markupView="lexicon">
		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="deletions" /></h3>

			<c:if test="<%= !cmd.equals(Constants.EXPORT) %>">
				<liferay-staging:checkbox
					checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA, false) %>"
					disabled="<%= disableInputs %>"
					label="delete-application-data-before-importing"
					name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>"
					suggestion="delete-content-before-importing-suggestion"
					warning="delete-content-before-importing-warning"
				/>
			</c:if>

			<liferay-staging:checkbox
				checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, false) %>"
				description="<%= individualDeletionsDescription %>"
				disabled="<%= disableInputs %>"
				label="<%= individualDeletionsTitle %>"
				name="<%= PortletDataHandlerKeys.DELETIONS %>"
			/>
		</div>
	</aui:fieldset>
</c:if>