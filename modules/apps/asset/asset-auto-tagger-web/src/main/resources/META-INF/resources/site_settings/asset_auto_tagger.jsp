<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetAutoTaggerConfiguration assetAutoTaggerConfiguration = (AssetAutoTaggerConfiguration)request.getAttribute(AssetAutoTaggerConfiguration.class.getName());
%>

<aui:input helpMessage="site-asset-auto-tagging-help" inlineLabel="right" label="enable-auto-tagging-of-assets-on-this-site" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--assetAutoTaggingEnabled--" type="toggle-switch" value="<%= assetAutoTaggerConfiguration.isEnabled() %>" />