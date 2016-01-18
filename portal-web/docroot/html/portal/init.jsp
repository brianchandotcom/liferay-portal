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

<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.admin.kernel.util.PortalProductMenuApplicationType" %><%@
page import="com.liferay.portal.exception.LayoutPermissionException" %><%@
page import="com.liferay.portal.exception.PortletActiveException" %><%@
page import="com.liferay.portal.exception.RequiredLayoutException" %><%@
page import="com.liferay.portal.exception.RequiredRoleException" %><%@
page import="com.liferay.portal.exception.UserActiveException" %><%@
page import="com.liferay.portal.exception.UserEmailAddressException" %><%@
page import="com.liferay.portal.exception.UserLockoutException" %><%@
page import="com.liferay.portal.exception.UserPasswordException" %><%@
page import="com.liferay.portal.exception.UserReminderQueryException" %><%@
page import="com.liferay.portal.kernel.cluster.ClusterExecutorUtil" %><%@
page import="com.liferay.portal.kernel.cluster.ClusterNode" %><%@
page import="com.liferay.portal.kernel.license.util.LicenseManagerUtil" %><%@
page import="com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletConfigurationLayoutUtil" %><%@
page import="com.liferay.portal.kernel.servlet.HttpHeaders" %><%@
page import="com.liferay.portal.kernel.templateparser.TransformException" %><%@
page import="com.liferay.portal.kernel.util.ProgressTracker" %><%@
page import="com.liferay.portal.setup.SetupWizardUtil" %><%@
page import="com.liferay.portal.struts.PortletRequestProcessor" %><%@
page import="com.liferay.portal.util.LicenseUtil" %><%@
page import="com.liferay.portal.util.PortletCategoryUtil" %><%@
page import="com.liferay.portal.util.TermsOfUseContentProvider" %><%@
page import="com.liferay.portal.util.TermsOfUseContentProviderRegistryUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.DefaultConfigurationPortletDataHandler" %><%@
page import="com.liferay.portlet.portletconfiguration.util.PortletConfigurationApplicationType" %>

<%@ page import="org.apache.struts.action.ActionMapping" %><%@
page import="org.apache.struts.tiles.ComponentDefinition" %><%@
page import="org.apache.struts.tiles.TilesUtil" %><%@
page import="org.apache.struts.tiles.taglib.ComponentConstants" %>