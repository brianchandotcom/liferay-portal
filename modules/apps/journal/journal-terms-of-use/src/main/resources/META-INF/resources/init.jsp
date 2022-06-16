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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.journal.model.JournalArticle" %><%@
page import="com.liferay.journal.terms.of.use.internal.constants.JournalArticleTermsOfUseWebConstants" %><%@
page import="com.liferay.journal.terms.of.use.internal.display.context.JournalArticleTermsOfUseDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
JournalArticleTermsOfUseDisplayContext journalArticleTermsOfUseDisplayContext = (JournalArticleTermsOfUseDisplayContext)request.getAttribute(JournalArticleTermsOfUseWebConstants.JOURNAL_ARTICLE_TERMS_OF_USE_DISPLAY_CONTEXT);
%>