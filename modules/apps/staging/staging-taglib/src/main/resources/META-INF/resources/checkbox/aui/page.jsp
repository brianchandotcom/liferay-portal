<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/checkbox/init.jsp" %>

<div class="form-group">
	<div class="custom-checkbox custom-control">
		<label>
			<aui:input checked="<%= checked %>" cssClass="custom-control-input" data-qa-id="<%= name %>" disabled="<%= disabled %>" id="<%= id %>" ignoreRequestValue="<%= true %>" label="" name="<%= name %>" type="checkbox" wrappedField="<%= true %>" />

			<%@ include file="/checkbox/extended_label.jspf" %>
		</label>
	</div>
</div>