<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean groupMentionsEnabled = GetterUtil.getBoolean(request.getAttribute(MentionsWebKeys.GROUP_MENTIONS_ENABLED));
%>

<aui:input checked="<%= groupMentionsEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "allow-users-to-mention-other-users") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--mentionsEnabled--" type="toggle-switch" value="<%= groupMentionsEnabled %>" />