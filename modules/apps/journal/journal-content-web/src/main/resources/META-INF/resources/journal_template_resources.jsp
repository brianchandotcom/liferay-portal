<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:vertical-card
			verticalCard="<%= new JournalDDMTemplateVerticalCard(journalContentDisplayContext.getDDMTemplate(), request) %>"
		/>
	</clay:col>
</clay:row>