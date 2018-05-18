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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.exception.CommerceAddressCityException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressCountryException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressStreetException" %><%@
page import="com.liferay.commerce.exception.NoSuchAddressException" %><%@
page import="com.liferay.commerce.model.CommerceAddress" %><%@
page import="com.liferay.commerce.model.CommerceCountry" %><%@
page import="com.liferay.commerce.model.CommerceRegion" %><%@
page import="com.liferay.commerce.organization.constants.CommerceOrganizationConstants" %><%@
page import="com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationAddressesDisplayContext" %><%@
page import="com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationBranchesDisplayContext" %><%@
page import="com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationDetailDisplayContext" %><%@
page import="com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationMembersDisplayContext" %><%@
page import="com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationSearchDisplayContext" %><%@
page import="com.liferay.commerce.organization.web.internal.servlet.taglib.ui.CommerceOrganizationScreenNavigationConstants" %><%@
page import="com.liferay.frontend.taglib.servlet.taglib.util.AddMenuKeys" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.exception.AddressCityException" %><%@
page import="com.liferay.portal.kernel.exception.AddressStreetException" %><%@
page import="com.liferay.portal.kernel.exception.AddressZipException" %><%@
page import="com.liferay.portal.kernel.exception.DuplicateOrganizationException" %><%@
page import="com.liferay.portal.kernel.exception.EmailAddressException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchCountryException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchListTypeException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchRegionException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchUserException" %><%@
page import="com.liferay.portal.kernel.exception.OrganizationNameException" %><%@
page import="com.liferay.portal.kernel.exception.UserEmailAddressException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Address" %><%@
page import="com.liferay.portal.kernel.model.EmailAddress" %><%@
page import="com.liferay.portal.kernel.model.ListTypeConstants" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.OrganizationConstants" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.users.admin.configuration.UserFileUploadsConfiguration" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />