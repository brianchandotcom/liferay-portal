<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/cp_definition_specification_option_value/init.jsp" %>

<%
CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
%>

<dt class="specification-term">
	<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
</dt>
<dd class="specification-desc">
	<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValue.getValue(languageId)) %>
</dd>