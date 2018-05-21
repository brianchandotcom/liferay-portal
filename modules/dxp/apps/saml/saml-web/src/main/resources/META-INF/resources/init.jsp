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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.ClassUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.saml.constants.SamlWebKeys" %><%@
page import="com.liferay.saml.persistence.exception.DuplicateSamlIdpSpConnectionSamlSpEntityIdException" %><%@
page import="com.liferay.saml.persistence.exception.DuplicateSamlSpIdpConnectionSamlIdpEntityIdException" %><%@
page import="com.liferay.saml.persistence.exception.SamlIdpSpConnectionMetadataUrlException" %><%@
page import="com.liferay.saml.persistence.exception.SamlIdpSpConnectionMetadataXmlException" %><%@
page import="com.liferay.saml.persistence.exception.SamlIdpSpConnectionNameException" %><%@
page import="com.liferay.saml.persistence.exception.SamlIdpSpConnectionSamlSpEntityIdException" %><%@
page import="com.liferay.saml.persistence.exception.SamlSpIdpConnectionMetadataUrlException" %><%@
page import="com.liferay.saml.persistence.exception.SamlSpIdpConnectionMetadataXmlException" %><%@
page import="com.liferay.saml.persistence.exception.SamlSpIdpConnectionSamlIdpEntityIdException" %><%@
page import="com.liferay.saml.persistence.model.SamlIdpSpConnection" %><%@
page import="com.liferay.saml.persistence.model.SamlSpIdpConnection" %><%@
page import="com.liferay.saml.runtime.certificate.CertificateTool" %><%@
page import="com.liferay.saml.runtime.configuration.SamlProviderConfiguration" %><%@
page import="com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper" %><%@
page import="com.liferay.saml.runtime.exception.CertificateKeyPasswordException" %><%@
page import="com.liferay.saml.util.NameIdTypeValues" %><%@
page import="com.liferay.saml.util.PortletPropsKeys" %><%@
page import="com.liferay.saml.web.internal.util.NameIdTypeValuesUtilHelper" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.security.InvalidParameterException" %><%@
page import="java.security.cert.CertificateException" %><%@
page import="java.security.cert.X509Certificate" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String currentURL = PortalUtil.getCurrentURL(request);

NameIdTypeValues nameIdTypeValues = NameIdTypeValuesUtilHelper.getNameIdTypeValues();

SamlProviderConfigurationHelper samlProviderConfigurationHelper = (SamlProviderConfigurationHelper)request.getAttribute(ClassUtil.getClassName(SamlProviderConfigurationHelper.class));

SamlProviderConfiguration samlProviderConfiguration = null;

if (samlProviderConfigurationHelper != null) {
	samlProviderConfiguration = samlProviderConfigurationHelper.getSamlProviderConfiguration();
}
%>