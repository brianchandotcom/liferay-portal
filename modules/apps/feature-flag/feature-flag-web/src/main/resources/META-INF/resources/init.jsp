<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.feature.flag.web.internal.display.FeatureFlagsDisplayContext" %><%@
page import="com.liferay.feature.flag.web.internal.model.FeatureFlagDisplay" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<liferay-theme:defineObjects />