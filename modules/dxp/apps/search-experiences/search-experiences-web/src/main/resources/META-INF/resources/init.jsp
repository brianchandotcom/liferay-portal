<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/frontend-data-set" prefix="frontend-data-set" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %><%@
page import="com.liferay.learn.LearnMessageUtil" %><%@
page import="com.liferay.petra.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.settings.ParameterMapSettings" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.search.experiences.exception.SXPElementReadOnlyException" %><%@
page import="com.liferay.search.experiences.model.SXPBlueprint" %><%@
page import="com.liferay.search.experiences.service.SXPBlueprintLocalServiceUtil" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.admin.constants.SXPBlueprintAdminFDSNames" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.admin.display.context.ViewSXPBlueprintsDisplayContext" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.admin.display.context.ViewSXPElementsDisplayContext" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.options.portlet.preferences.SXPBlueprintOptionsPortletPreferencesUtil" %><%@
page import="com.liferay.search.experiences.web.internal.constants.SXPWebKeys" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />