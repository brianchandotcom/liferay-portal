<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/themes/init.jsp" %>

<%-- Raw Text --%>

<%
com.liferay.petra.string.StringBundler bodyTopSB = OutputTag.getDataSB(request, WebKeys.PAGE_BODY_TOP);
%>

<c:if test="<%= bodyTopSB != null %>">

	<%
	bodyTopSB.writeTo(out);
	%>

</c:if>

<%@ include file="/html/common/themes/top_messages.jsp" %>

<liferay-util:include page="/html/common/themes/body_top-ext.jsp" />

<liferay-util:dynamic-include key="/html/common/themes/body_top.jsp#post" />