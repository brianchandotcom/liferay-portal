<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/checkbox/init.jsp" %>

<div class="custom-checkbox custom-control">
	<label>
		<input class="custom-control-input" data-qa-id="<%= name %>" id="<%= HtmlUtil.escape(domId) %>" <%= disabledString %> <%= checkedString %> type="checkbox" name="<%= HtmlUtil.escape(domName) %>" />

		<span class="custom-control-label">
			<%@ include file="/checkbox/extended_label.jspf" %>
		</span>
	</label>
</div>