<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBTemplate kbTemplate = (KBTemplate)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);
%>

<div class="kb-entity-header">
	<div class="kb-title">
		<%= HtmlUtil.escape(kbTemplate.getTitle()) %>
	</div>

	<div class="kb-tools">
		<liferay-ui:icon
			icon="print"
			label="<%= true %>"
			markupView="lexicon"
			message="print"
			url="javascript:print();"
		/>
	</div>
</div>

<div class="kb-entity-body">
	<%= kbTemplate.getContent() %>
</div>