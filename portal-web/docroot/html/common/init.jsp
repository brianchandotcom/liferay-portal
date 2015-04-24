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

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.counter.service.CounterLocalServiceUtil" %><%@
page import="com.liferay.portal.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.LocaleException" %><%@
page import="com.liferay.portal.NoSuchLayoutException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.NoSuchUserException" %><%@
page import="com.liferay.portal.NoSuchWorkflowDefinitionLinkException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.cal.Recurrence" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaTextException" %><%@
page import="com.liferay.portal.kernel.configuration.Filter" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.DisplayTerms" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainerResults" %><%@
page import="com.liferay.portal.kernel.exception.LocalizedException" %><%@
page import="com.liferay.portal.kernel.exception.PortalException" %><%@
page import="com.liferay.portal.kernel.exception.SystemException" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageWrapper" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.lar.ManifestSummary" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandler" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.log.LogUtil" %><%@
page import="com.liferay.portal.kernel.messaging.DestinationNames" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletMode" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletURL" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletRequestModel" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.repository.model.FileVersion" %><%@
page import="com.liferay.portal.kernel.repository.model.Folder" %><%@
page import="com.liferay.portal.kernel.search.BaseModelSearchResult" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.QueryConfig" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.SearchResultUtil" %><%@
page import="com.liferay.portal.kernel.search.Sort" %><%@
page import="com.liferay.portal.kernel.search.SortFactoryUtil" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portal.kernel.servlet.BrowserSnifferUtil" %><%@
page import="com.liferay.portal.kernel.servlet.BufferCacheServletResponse" %><%@
page import="com.liferay.portal.kernel.servlet.MultiSessionMessages" %><%@
page import="com.liferay.portal.kernel.servlet.PortalMessages" %><%@
page import="com.liferay.portal.kernel.servlet.PortalWebResourcesUtil" %><%@
page import="com.liferay.portal.kernel.servlet.ServletContextPool" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.MenuItem" %><%@
page import="com.liferay.portal.kernel.settings.SettingsFactoryUtil" %><%@
page import="com.liferay.portal.kernel.staging.LayoutStagingUtil" %><%@
page import="com.liferay.portal.kernel.staging.StagingUtil" %><%@
page import="com.liferay.portal.kernel.template.StringTemplateResource" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.upload.LiferayFileItemException" %><%@
page import="com.liferay.portal.kernel.upload.UploadException" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarUtil" %><%@
page import="com.liferay.portal.kernel.util.CharPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ContentTypes" %><%@
page import="com.liferay.portal.kernel.util.CookieKeys" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Http" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.IntegerWrapper" %><%@
page import="com.liferay.portal.kernel.util.JavaConstants" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.LocalizationUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.MathUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.OrderedProperties" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.RSSUtil" %><%@
page import="com.liferay.portal.kernel.util.ReleaseInfo" %><%@
page import="com.liferay.portal.kernel.util.ResourceBundleUtil" %><%@
page import="com.liferay.portal.kernel.util.ServerDetector" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.portal.kernel.util.TimeZoneUtil" %><%@
page import="com.liferay.portal.kernel.util.Tuple" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portal.model.*" %><%@
page import="com.liferay.portal.model.impl.*" %><%@
page import="com.liferay.portal.plugin.PluginUtil" %><%@
page import="com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.security.auth.AuthException" %><%@
page import="com.liferay.portal.security.auth.AuthTokenUtil" %><%@
page import="com.liferay.portal.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.security.sso.SSOUtil" %><%@
page import="com.liferay.portal.service.*" %><%@
page import="com.liferay.portal.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.LayoutPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.PortletPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.RolePermissionUtil" %><%@
page import="com.liferay.portal.struts.StrutsUtil" %><%@
page import="com.liferay.portal.struts.TilesAttributeUtil" %><%@
page import="com.liferay.portal.theme.ThemeDisplay" %><%@
page import="com.liferay.portal.upload.LiferayFileItem" %><%@
page import="com.liferay.portal.util.JavaScriptBundleUtil" %><%@
page import="com.liferay.portal.util.LayoutDescription" %><%@
page import="com.liferay.portal.util.Portal" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletCategoryKeys" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portal.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.util.PropsUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.util.SessionClicks" %><%@
page import="com.liferay.portal.util.ShutdownUtil" %><%@
page import="com.liferay.portal.util.WebAppPool" %><%@
page import="com.liferay.portal.util.WebKeys" %><%@
page import="com.liferay.portal.util.comparator.PortletCategoryComparator" %><%@
page import="com.liferay.portal.util.comparator.PortletTitleComparator" %><%@
page import="com.liferay.portal.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.portlet.InvokerPortlet" %><%@
page import="com.liferay.portlet.PortalPreferences" %><%@
page import="com.liferay.portlet.PortletConfigFactoryUtil" %><%@
page import="com.liferay.portlet.PortletInstanceFactoryUtil" %><%@
page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portlet.PortletResponseImpl" %><%@
page import="com.liferay.portlet.PortletSetupUtil" %><%@
page import="com.liferay.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLImpl" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.RenderParametersPool" %><%@
page import="com.liferay.portlet.RenderRequestFactory" %><%@
page import="com.liferay.portlet.RenderRequestImpl" %><%@
page import="com.liferay.portlet.RenderResponseFactory" %><%@
page import="com.liferay.portlet.RenderResponseImpl" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetCategory" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.model.AssetVocabulary" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.blogs.model.BlogsEntry" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileException" %><%@
page import="com.liferay.portlet.documentlibrary.FileExtensionException" %><%@
page import="com.liferay.portlet.documentlibrary.FileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.FileSizeException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileException" %><%@
page import="com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolder" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DocumentConversionUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.Fields" %><%@
page import="com.liferay.portlet.expando.model.ExpandoBridge" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.util.JournalContentUtil" %><%@
page import="com.liferay.portlet.messageboards.model.MBMessage" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.util.MBUtil" %><%@
page import="com.liferay.portlet.portletconfiguration.util.PortletConfigurationUtil" %><%@
page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %><%@
page import="com.liferay.portlet.ratings.RatingsType" %><%@
page import="com.liferay.portlet.ratings.transformer.RatingsDataTransformerUtil" %><%@
page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %><%@
page import="com.liferay.portlet.sites.util.Sites" %><%@
page import="com.liferay.portlet.sites.util.SitesUtil" %><%@
page import="com.liferay.portlet.trash.model.TrashEntry" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupDisplayTerms" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdmin" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.taglib.search.SearchEntry" %><%@
page import="com.liferay.taglib.search.TextSearchEntry" %><%@
page import="com.liferay.taglib.ui.util.SessionTreeJSClicks" %><%@
page import="com.liferay.taglib.util.OutputTag" %><%@
page import="com.liferay.util.ContentUtil" %><%@
page import="com.liferay.util.CreditCard" %><%@
page import="com.liferay.util.Encryptor" %><%@
page import="com.liferay.util.PKParser" %><%@
page import="com.liferay.util.State" %><%@
page import="com.liferay.util.StateUtil" %><%@
page import="com.liferay.util.log4j.Levels" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.text.DateFormat" %><%@
page import="java.text.DecimalFormat" %><%@
page import="java.text.DecimalFormatSymbols" %><%@
page import="java.text.Format" %><%@
page import="java.text.NumberFormat" %><%@
page import="java.text.SimpleDateFormat" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Collection" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Currency" %><%@
page import="java.util.Date" %><%@
page import="java.util.Enumeration" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.Iterator" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.LinkedHashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Properties" %><%@
page import="java.util.ResourceBundle" %><%@
page import="java.util.Set" %><%@
page import="java.util.TimeZone" %><%@
page import="java.util.TreeMap" %><%@
page import="java.util.TreeSet" %>

<%@ page import="javax.portlet.MimeResponse" %><%@
page import="javax.portlet.PortletConfig" %><%@
page import="javax.portlet.PortletContext" %><%@
page import="javax.portlet.PortletException" %><%@
page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletPreferences" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletResponse" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.ResourceURL" %><%@
page import="javax.portlet.UnavailableException" %><%@
page import="javax.portlet.WindowState" %>

<liferay-theme:defineObjects />

<%@ include file="/html/common/init-ext.jsp" %>