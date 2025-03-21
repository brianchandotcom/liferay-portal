<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewVocabulariesDisplayContext viewVocabulariesDisplayContext = (ViewVocabulariesDisplayContext)request.getAttribute(ViewVocabulariesDisplayContext.class.getName());
%>

<div class="cms-section">
	<div id="<%= CMSSiteInitializerFDSNames.CATEGORIZATION_SECTION %>">
		<react:component
			module="{ViewVocabularies} from site-cms-site-initializer"
			props="<%= viewVocabulariesDisplayContext.getReactData() %>"
		/>
	</div>
</div>