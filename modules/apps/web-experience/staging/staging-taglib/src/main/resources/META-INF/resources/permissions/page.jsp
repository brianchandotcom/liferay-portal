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

<%@ include file="/permissions/init.jsp" %>

<aui:fieldset cssClass="options-group" markupView="lexicon">
	<div class="sheet-section">
		<h3 class="sheet-subtitle"><liferay-ui:message key="permissions" /></h3>

		<liferay-staging:checkbox
			checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PERMISSIONS, false) %>"
			description="<%= inputDescription %>"
			disabled="<%= disableInputs %>"
			label="<%= inputTitle %>"
			name="<%= PortletDataHandlerKeys.PERMISSIONS %>"
		/>
	</div>
</aui:fieldset>