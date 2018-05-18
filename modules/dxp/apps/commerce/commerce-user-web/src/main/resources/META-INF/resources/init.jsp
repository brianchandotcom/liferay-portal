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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.exception.NoSuchAddressException" %><%@
page import="com.liferay.commerce.user.web.internal.display.context.CommerceUserDetailDisplayContext" %><%@
page import="com.liferay.commerce.user.web.internal.display.context.CommerceUserPasswordDisplayContext" %><%@
page import="com.liferay.commerce.user.web.internal.display.context.CommerceUserPermissionsDisplayContext" %><%@
page import="com.liferay.commerce.user.web.internal.servlet.taglib.ui.CommerceUserScreenNavigationConstants" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.exception.ContactBirthdayException" %><%@
page import="com.liferay.portal.kernel.exception.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchUserException" %><%@
page import="com.liferay.portal.kernel.exception.UserEmailAddressException" %><%@
page import="com.liferay.portal.kernel.exception.UserFieldException" %><%@
page import="com.liferay.portal.kernel.exception.UserIdException" %><%@
page import="com.liferay.portal.kernel.exception.UserPasswordException" %><%@
page import="com.liferay.portal.kernel.exception.UserScreenNameException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Contact" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsConstants" %><%@
page import="com.liferay.portal.kernel.model.PasswordPolicy" %><%@
page import="com.liferay.portal.kernel.model.Role" %><%@
page import="com.liferay.portal.kernel.model.RoleConstants" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.model.UserConstants" %><%@
page import="com.liferay.portal.kernel.security.auth.ScreenNameValidator" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.security.auth.ScreenNameValidatorFactory" %><%@
page import="com.liferay.portal.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.users.admin.configuration.UserFileUploadsConfiguration" %><%@
page import="com.liferay.users.admin.kernel.util.UsersAdmin" %><%@
page import="com.liferay.users.admin.kernel.util.UsersAdminUtil" %>

<%@ page import="java.util.Calendar" %><%@
page import="java.util.List" %><%@
page import="java.util.Set" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />