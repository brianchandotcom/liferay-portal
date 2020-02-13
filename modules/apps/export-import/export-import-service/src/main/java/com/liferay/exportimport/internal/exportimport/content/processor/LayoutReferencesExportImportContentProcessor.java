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

package com.liferay.exportimport.internal.exportimport.content.processor;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.StagingGroupHelper;

import java.net.InetAddress;
import java.net.URL;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	configurationPid = "com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	immediate = true, property = "content.processor.type=LayoutReferences",
	service = ExportImportContentProcessor.class
)
public class LayoutReferencesExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return replaceExportLayoutReferences(
			portletDataContext, stagedModel, content);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return replaceImportLayoutReferences(portletDataContext, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		validateLayoutReferences(groupId, content);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_exportImportServiceConfiguration = ConfigurableUtil.createConfigurable(
			ExportImportServiceConfiguration.class, properties);
	}

	protected String replaceExportHostname(
			Group group, String url, StringBundler urlSB)
		throws PortalException {

		if (!_http.hasProtocol(url)) {
			return url;
		}

		boolean secure = _http.isSecure(url);

		int serverPort = _portal.getPortalServerPort(secure);

		if (serverPort == -1) {
			return url;
		}

		LayoutSet publicLayoutSet = group.getPublicLayoutSet();

		TreeMap<String, String> publicLayoutSetVirtualHostnames =
			publicLayoutSet.getVirtualHostnames();

		String portalURL = StringPool.BLANK;

		if (!publicLayoutSetVirtualHostnames.isEmpty()) {
			portalURL = _getPortalURL(
				url,
				_portal.getPortalURL(
					publicLayoutSetVirtualHostnames.firstKey(), serverPort,
					secure));

			if (url.startsWith(portalURL)) {
				if (secure) {
					urlSB.append(_DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL);
				}
				else {
					urlSB.append(_DATA_HANDLER_PUBLIC_LAYOUT_SET_URL);
				}

				return url.substring(portalURL.length());
			}
		}

		LayoutSet privateLayoutSet = group.getPrivateLayoutSet();

		TreeMap<String, String> privateLayoutSetVirtualHostnames =
			privateLayoutSet.getVirtualHostnames();

		if (!privateLayoutSetVirtualHostnames.isEmpty()) {
			portalURL = _getPortalURL(
				url,
				_portal.getPortalURL(
					privateLayoutSetVirtualHostnames.firstKey(), serverPort,
					secure));

			if (url.startsWith(portalURL)) {
				if (secure) {
					urlSB.append(_DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL);
				}
				else {
					urlSB.append(_DATA_HANDLER_PRIVATE_LAYOUT_SET_URL);
				}

				return url.substring(portalURL.length());
			}
		}

		Company company = _companyLocalService.getCompany(group.getCompanyId());

		String companyVirtualHostname = company.getVirtualHostname();

		if (Validator.isNotNull(companyVirtualHostname)) {
			portalURL = _getPortalURL(
				url,
				_portal.getPortalURL(
					companyVirtualHostname, serverPort, secure));

			if (url.startsWith(portalURL)) {
				if (secure) {
					urlSB.append(_DATA_HANDLER_COMPANY_SECURE_URL);
				}
				else {
					urlSB.append(_DATA_HANDLER_COMPANY_URL);
				}

				return url.substring(portalURL.length());
			}
		}

		portalURL = _portal.getPortalURL("localhost", serverPort, secure);

		if (url.startsWith(portalURL)) {
			return url.substring(portalURL.length());
		}

		return url;
	}

	protected String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		StringBundler hostnameSB = new StringBundler(2);

		content = replaceExportHostname(group, content, hostnameSB);

		if (hostnameSB.index() > 0) {
			hostnameSB.append(content);

			content = hostnameSB.toString();
		}

		StringBuilder sb = new StringBuilder(content);

		String[] patterns = {"href=", "[[", "{{"};

		int beginPos = -1;
		int endPos = content.length();
		int offset = 0;

		while (true) {
			if (beginPos > -1) {
				endPos = beginPos - 1;
			}

			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			if (content.startsWith("href=", beginPos)) {
				offset = 5;

				char c = content.charAt(beginPos + offset);

				if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
					offset++;
				}
			}
			else if ((content.charAt(beginPos) == CharPool.OPEN_BRACKET) ||
					 (content.charAt(beginPos) == CharPool.OPEN_CURLY_BRACE)) {

				offset = 2;
			}

			endPos = StringUtil.indexOfAny(
				content, _LAYOUT_REFERENCE_STOP_CHARS, beginPos + offset,
				endPos);

			if (endPos == -1) {
				continue;
			}

			String url = content.substring(beginPos + offset, endPos);

			int pos = url.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

			if (pos != -1) {
				url = url.substring(0, pos);

				endPos = beginPos + offset + pos;
			}

			if (url.endsWith(StringPool.SLASH)) {
				url = url.substring(0, url.length() - 1);

				endPos--;
			}

			StringBundler urlSB = new StringBundler(6);

			try {
				url = replaceExportHostname(group, url, urlSB);

				if (!url.startsWith(StringPool.SLASH)) {
					continue;
				}

				String pathContext = _portal.getPathContext();

				if (pathContext.length() > 1) {
					if (!url.startsWith(pathContext)) {
						continue;
					}

					urlSB.append(_DATA_HANDLER_PATH_CONTEXT);

					url = url.substring(pathContext.length());
				}

				if (!url.startsWith(StringPool.SLASH)) {
					continue;
				}

				pos = url.indexOf(StringPool.SLASH, 1);

				String localePath = StringPool.BLANK;

				Locale locale = null;

				if (pos != -1) {
					localePath = url.substring(0, pos);

					locale = LocaleUtil.fromLanguageId(
						localePath.substring(1), true, false);
				}

				if (locale != null) {
					String urlWithoutLocale = url.substring(
						localePath.length());

					if (urlWithoutLocale.startsWith(
							_PRIVATE_GROUP_SERVLET_MAPPING) ||
						urlWithoutLocale.startsWith(
							_PRIVATE_USER_SERVLET_MAPPING) ||
						urlWithoutLocale.startsWith(
							_PUBLIC_GROUP_SERVLET_MAPPING)) {

						urlSB.append(localePath);

						url = urlWithoutLocale;
					}
				}

				boolean privateLayout = false;

				if (url.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING)) {
					urlSB.append(_DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING);

					url = url.substring(
						_PRIVATE_GROUP_SERVLET_MAPPING.length() - 1);

					privateLayout = true;
				}
				else if (url.startsWith(_PRIVATE_USER_SERVLET_MAPPING)) {
					urlSB.append(_DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING);

					url = url.substring(
						_PRIVATE_USER_SERVLET_MAPPING.length() - 1);

					privateLayout = true;
				}
				else if (url.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING)) {
					urlSB.append(_DATA_HANDLER_PUBLIC_SERVLET_MAPPING);

					url = url.substring(
						_PUBLIC_GROUP_SERVLET_MAPPING.length() - 1);
				}
				else {
					String urlSBString = urlSB.toString();

					LayoutSet layoutSet = null;

					if (urlSBString.contains(
							_DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL) ||
						urlSBString.contains(
							_DATA_HANDLER_PUBLIC_LAYOUT_SET_URL)) {

						layoutSet = group.getPublicLayoutSet();
					}
					else if (urlSBString.contains(
								_DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL) ||
							 urlSBString.contains(
								 _DATA_HANDLER_PRIVATE_LAYOUT_SET_URL)) {

						layoutSet = group.getPrivateLayoutSet();
					}

					if (layoutSet == null) {
						continue;
					}

					privateLayout = layoutSet.isPrivateLayout();

					LayoutFriendlyURL layoutFriendlyURL =
						_layoutFriendlyURLLocalService.
							fetchFirstLayoutFriendlyURL(
								group.getGroupId(), privateLayout, url);

					if (layoutFriendlyURL == null) {
						continue;
					}

					if (privateLayout) {
						if (group.isUser()) {
							urlSB.append(
								_DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING);
						}
						else {
							urlSB.append(
								_DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING);
						}
					}
					else {
						urlSB.append(_DATA_HANDLER_PUBLIC_SERVLET_MAPPING);
					}

					urlSB.append(_DATA_HANDLER_GROUP_FRIENDLY_URL);

					continue;
				}

				Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
					group.getGroupId(), privateLayout, url);

				if (layout != null) {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					portletDataContext.addReferenceElement(
						stagedModel, entityElement, layout,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

					continue;
				}

				pos = url.indexOf(StringPool.SLASH, 1);

				String groupFriendlyURL = url;

				if (pos != -1) {
					groupFriendlyURL = url.substring(0, pos);
				}

				Group urlGroup = _groupLocalService.fetchFriendlyURLGroup(
					group.getCompanyId(), groupFriendlyURL);

				if (urlGroup == null) {
					throw new NoSuchLayoutException();
				}

				urlSB.append(_DATA_HANDLER_GROUP_FRIENDLY_URL);

				// Append the UUID. This information will be used during the
				// import process when looking up the proper group for the link.

				urlSB.append(StringPool.AT);

				if (urlGroup.isStagedRemotely()) {
					String remoteGroupUuid = urlGroup.getTypeSettingsProperty(
						"remoteGroupUUID");

					if (Validator.isNotNull(remoteGroupUuid)) {
						urlSB.append(remoteGroupUuid);
					}
				}
				else if (_stagingGroupHelper.isStagingGroup(urlGroup)) {
					Group liveGroup = urlGroup.getLiveGroup();

					urlSB.append(liveGroup.getUuid());
				}
				else if (urlGroup.isControlPanel() ||
						 (_stagingGroupHelper.isLiveGroup(urlGroup) &&
						  (group.getLiveGroupId() == urlGroup.getGroupId()))) {

					urlSB.append(urlGroup.getUuid());
				}
				else {
					urlSB.append(urlGroup.getFriendlyURL());
				}

				urlSB.append(StringPool.AT);

				String siteAdminURL =
					GroupConstants.CONTROL_PANEL_FRIENDLY_URL +
						PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL;

				if (url.endsWith(siteAdminURL)) {
					urlSB.append(_DATA_HANDLER_SITE_ADMIN_URL);

					url = StringPool.BLANK;

					continue;
				}

				if (pos == -1) {
					url = StringPool.BLANK;

					continue;
				}

				url = url.substring(pos);

				layout = _layoutLocalService.getFriendlyURLLayout(
					urlGroup.getGroupId(), privateLayout, url);

				Element entityElement = portletDataContext.getExportDataElement(
					stagedModel);

				portletDataContext.addReferenceElement(
					stagedModel, entityElement, layout,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
			catch (Exception exception) {
				if ((exception instanceof NoSuchLayoutException) &&
					!_exportImportServiceConfiguration.
						validateLayoutReferences()) {

					continue;
				}

				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process layout URL ");
				exceptionSB.append(url);
				exceptionSB.append(" for staged model ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException
					exportImportContentProcessorException =
						new ExportImportContentProcessorException(
							exceptionSB.toString(), exception);

				if (_log.isDebugEnabled()) {
					_log.debug(
						exceptionSB.toString(),
						exportImportContentProcessorException);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}
			finally {
				if (urlSB.length() > 0) {
					urlSB.append(url);

					url = urlSB.toString();
				}

				sb.replace(beginPos + offset, endPos, url);
			}
		}

		return sb.toString();
	}

	protected String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		String companyPortalURL = StringPool.BLANK;
		String privateLayoutSetPortalURL = StringPool.BLANK;
		String publicLayoutSetPortalURL = StringPool.BLANK;

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		Company company = _companyLocalService.getCompany(group.getCompanyId());

		LayoutSet privateLayoutSet = group.getPrivateLayoutSet();
		LayoutSet publicLayoutSet = group.getPublicLayoutSet();

		int serverPort = _portal.getPortalServerPort(false);

		if (serverPort != -1) {
			if (Validator.isNotNull(company.getVirtualHostname())) {
				companyPortalURL = _portal.getPortalURL(
					company.getVirtualHostname(), serverPort, false);
			}

			TreeMap<String, String> privateVirtualHostnames =
				privateLayoutSet.getVirtualHostnames();

			if (!privateVirtualHostnames.isEmpty()) {
				privateLayoutSetPortalURL = _portal.getPortalURL(
					privateVirtualHostnames.firstKey(), serverPort, false);
			}
			else {
				privateLayoutSetPortalURL = companyPortalURL;
			}

			TreeMap<String, String> publicVirtualHostnames =
				publicLayoutSet.getVirtualHostnames();

			if (!publicVirtualHostnames.isEmpty()) {
				publicLayoutSetPortalURL = _portal.getPortalURL(
					publicVirtualHostnames.firstKey(), serverPort, false);
			}
			else {
				publicLayoutSetPortalURL = companyPortalURL;
			}
		}

		int secureSecurePort = _portal.getPortalServerPort(true);

		String companySecurePortalURL = StringPool.BLANK;
		String privateLayoutSetSecurePortalURL = StringPool.BLANK;
		String publicLayoutSetSecurePortalURL = StringPool.BLANK;

		if (secureSecurePort != -1) {
			if (Validator.isNotNull(company.getVirtualHostname())) {
				companySecurePortalURL = _portal.getPortalURL(
					company.getVirtualHostname(), secureSecurePort, true);
			}

			TreeMap<String, String> privateVirtualHostnames =
				privateLayoutSet.getVirtualHostnames();

			if (!privateVirtualHostnames.isEmpty()) {
				privateLayoutSetSecurePortalURL = _portal.getPortalURL(
					privateVirtualHostnames.firstKey(), secureSecurePort, true);
			}

			TreeMap<String, String> publicVirtualHostnames =
				publicLayoutSet.getVirtualHostnames();

			if (!publicVirtualHostnames.isEmpty()) {
				publicLayoutSetSecurePortalURL = _portal.getPortalURL(
					publicVirtualHostnames.firstKey(), secureSecurePort, true);
			}
		}

		StringBundler sb = new StringBundler(3);

		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		content = StringUtil.replace(
			content, _DATA_HANDLER_COMPANY_SECURE_URL, companySecurePortalURL);
		content = StringUtil.replace(
			content, _DATA_HANDLER_COMPANY_URL, companyPortalURL);

		// Group friendly URLs

		while (true) {
			int groupFriendlyUrlPos = content.indexOf(
				_DATA_HANDLER_GROUP_FRIENDLY_URL);

			if (groupFriendlyUrlPos == -1) {
				break;
			}

			int groupUuidPos =
				groupFriendlyUrlPos + _DATA_HANDLER_GROUP_FRIENDLY_URL.length();

			int endIndex = -1;

			if (content.charAt(groupUuidPos) == CharPool.AT) {
				endIndex = content.indexOf(StringPool.AT, groupUuidPos + 1);
			}

			if (endIndex < (groupUuidPos + 1)) {
				content = StringUtil.replaceFirst(
					content, _DATA_HANDLER_GROUP_FRIENDLY_URL, StringPool.BLANK,
					groupFriendlyUrlPos);

				continue;
			}

			String groupUuid = content.substring(groupUuidPos + 1, endIndex);

			Group groupFriendlyUrlGroup =
				_groupLocalService.fetchGroupByUuidAndCompanyId(
					groupUuid, portletDataContext.getCompanyId());

			if (groupFriendlyUrlGroup == null) {
				groupFriendlyUrlGroup =
					_groupLocalService.fetchFriendlyURLGroup(
						portletDataContext.getCompanyId(), groupUuid);
			}

			if ((groupFriendlyUrlGroup == null) ||
				groupUuid.contains(_TEMPLATE_NAME_PREFIX)) {

				content = StringUtil.replaceFirst(
					content, _DATA_HANDLER_GROUP_FRIENDLY_URL,
					group.getFriendlyURL(), groupFriendlyUrlPos);
				content = StringUtil.replaceFirst(
					content, StringPool.AT + groupUuid + StringPool.AT,
					StringPool.BLANK, groupFriendlyUrlPos);

				if (groupUuid.contains(_TEMPLATE_NAME_PREFIX)) {
					content = _replaceTemplateLinkToLayout(
						content, portletDataContext.isPrivateLayout());
				}

				continue;
			}

			content = StringUtil.replaceFirst(
				content, _DATA_HANDLER_GROUP_FRIENDLY_URL, StringPool.BLANK,
				groupFriendlyUrlPos);
			content = StringUtil.replaceFirst(
				content, StringPool.AT + groupUuid + StringPool.AT,
				groupFriendlyUrlGroup.getFriendlyURL(), groupFriendlyUrlPos);
		}

		content = StringUtil.replace(
			content, _DATA_HANDLER_PATH_CONTEXT, _portal.getPathContext());
		content = StringUtil.replace(
			content, _DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING,
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);
		content = StringUtil.replace(
			content, _DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL,
			privateLayoutSetSecurePortalURL);
		content = StringUtil.replace(
			content, _DATA_HANDLER_PRIVATE_LAYOUT_SET_URL,
			privateLayoutSetPortalURL);
		content = StringUtil.replace(
			content, _DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING,
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);
		content = StringUtil.replace(
			content, _DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL,
			publicLayoutSetSecurePortalURL);
		content = StringUtil.replace(
			content, _DATA_HANDLER_PUBLIC_LAYOUT_SET_URL,
			publicLayoutSetPortalURL);
		content = StringUtil.replace(
			content, _DATA_HANDLER_PUBLIC_SERVLET_MAPPING,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
		content = StringUtil.replace(
			content, _DATA_HANDLER_SITE_ADMIN_URL, sb.toString());

		return content;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	protected void validateLayoutReferences(long groupId, String content)
		throws PortalException {

		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			_exportImportServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ExportImportServiceConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(configurationException.getMessage());
			}
		}

		if (!_exportImportServiceConfiguration.validateLayoutReferences()) {
			return;
		}

		Group group = _groupLocalService.getGroup(groupId);

		String[] patterns = {"href=", "[[", "{{"};

		int beginPos = -1;
		int endPos = content.length();
		int offset = 0;

		while (true) {
			if (beginPos > -1) {
				endPos = beginPos - 1;
			}

			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			if (content.startsWith("href=", beginPos)) {
				offset = 5;

				char c = content.charAt(beginPos + offset);

				if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
					offset++;
				}
			}
			else if ((content.charAt(beginPos) == CharPool.OPEN_BRACKET) ||
					 (content.charAt(beginPos) == CharPool.OPEN_CURLY_BRACE)) {

				offset = 2;
			}

			endPos = StringUtil.indexOfAny(
				content, _LAYOUT_REFERENCE_STOP_CHARS, beginPos + offset,
				endPos);

			if (endPos == -1) {
				continue;
			}

			String url = content.substring(beginPos + offset, endPos);

			if (url.contains("/c/document_library/get_file?") ||
				url.contains("/documents/") ||
				url.contains("/image/image_gallery?")) {

				continue;
			}

			endPos = url.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

			if (endPos != -1) {
				url = url.substring(0, endPos);
			}

			if (url.endsWith(StringPool.SLASH)) {
				url = url.substring(0, url.length() - 1);
			}

			StringBundler urlSB = new StringBundler(1);

			url = replaceExportHostname(group, url, urlSB);

			if (!url.startsWith(StringPool.SLASH)) {
				continue;
			}

			String pathContext = _portal.getPathContext();

			if (pathContext.length() > 1) {
				if (!url.startsWith(pathContext)) {
					continue;
				}

				url = url.substring(pathContext.length());
			}

			if (!url.startsWith(StringPool.SLASH)) {
				continue;
			}

			int pos = url.indexOf(StringPool.SLASH, 1);

			String localePath = StringPool.BLANK;

			Locale locale = null;

			if (pos != -1) {
				localePath = url.substring(0, pos);

				locale = LocaleUtil.fromLanguageId(
					localePath.substring(1), true, false);
			}

			if (locale != null) {
				String urlWithoutLocale = url.substring(localePath.length());

				if (urlWithoutLocale.startsWith(
						_PRIVATE_GROUP_SERVLET_MAPPING) ||
					urlWithoutLocale.startsWith(
						_PRIVATE_USER_SERVLET_MAPPING) ||
					urlWithoutLocale.startsWith(
						_PUBLIC_GROUP_SERVLET_MAPPING) ||
					_isVirtualHostDefined(urlSB)) {

					url = urlWithoutLocale;
				}
			}

			boolean privateLayout = false;

			if (url.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING)) {
				url = url.substring(
					_PRIVATE_GROUP_SERVLET_MAPPING.length() - 1);

				privateLayout = true;
			}
			else if (url.startsWith(_PRIVATE_USER_SERVLET_MAPPING)) {
				url = url.substring(_PRIVATE_USER_SERVLET_MAPPING.length() - 1);

				privateLayout = true;
			}
			else if (url.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING)) {
				url = url.substring(_PUBLIC_GROUP_SERVLET_MAPPING.length() - 1);
			}
			else {
				String urlSBString = urlSB.toString();

				LayoutSet layoutSet = null;

				if (urlSBString.contains(
						_DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL) ||
					urlSBString.contains(_DATA_HANDLER_PUBLIC_LAYOUT_SET_URL)) {

					layoutSet = group.getPublicLayoutSet();
				}
				else if (urlSBString.contains(
							_DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL) ||
						 urlSBString.contains(
							 _DATA_HANDLER_PRIVATE_LAYOUT_SET_URL)) {

					layoutSet = group.getPrivateLayoutSet();
				}

				if (layoutSet == null) {
					continue;
				}

				privateLayout = layoutSet.isPrivateLayout();
			}

			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				groupId, privateLayout, url);

			if (layout != null) {
				continue;
			}

			String siteAdminURL =
				GroupConstants.CONTROL_PANEL_FRIENDLY_URL +
					PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL;

			if (url.endsWith(
					VirtualLayoutConstants.CANONICAL_URL_SEPARATOR +
						siteAdminURL)) {

				url = url.substring(url.indexOf(siteAdminURL));
			}

			pos = url.indexOf(StringPool.SLASH, 1);

			String groupFriendlyURL = url;

			if (pos != -1) {
				groupFriendlyURL = url.substring(0, pos);
			}

			Group urlGroup = _groupLocalService.fetchFriendlyURLGroup(
				group.getCompanyId(), groupFriendlyURL);

			if (urlGroup == null) {
				ExportImportContentValidationException
					exportImportContentValidationException =
						new ExportImportContentValidationException(
							LayoutReferencesExportImportContentProcessor.class.
								getName());

				exportImportContentValidationException.setGroupFriendlyURL(
					groupFriendlyURL);
				exportImportContentValidationException.setLayoutURL(url);
				exportImportContentValidationException.setType(
					ExportImportContentValidationException.
						LAYOUT_GROUP_NOT_FOUND);

				throw exportImportContentValidationException;
			}

			if (pos == -1) {
				continue;
			}

			url = url.substring(pos);

			try {
				_layoutLocalService.getFriendlyURLLayout(
					urlGroup.getGroupId(), privateLayout, url);
			}
			catch (NoSuchLayoutException noSuchLayoutException) {
				ExportImportContentValidationException
					exportImportContentValidationException =
						new ExportImportContentValidationException(
							LayoutReferencesExportImportContentProcessor.class.
								getName(),
							noSuchLayoutException);

				exportImportContentValidationException.setLayoutURL(url);
				exportImportContentValidationException.setType(
					ExportImportContentValidationException.
						LAYOUT_WITH_URL_NOT_FOUND);

				throw exportImportContentValidationException;
			}
		}
	}

	private String _getPortalURL(String url, String portalURL)
		throws PortalException {

		try {
			URL urlObject = new URL(url);

			if (InetAddressUtil.isLocalInetAddress(
					InetAddress.getByName(urlObject.getHost()))) {

				StringBundler sb = new StringBundler(5);

				sb.append(urlObject.getProtocol());
				sb.append("://");
				sb.append(urlObject.getHost());
				sb.append(StringPool.COLON);
				sb.append(urlObject.getPort());

				return sb.toString();
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		return portalURL;
	}

	private boolean _isVirtualHostDefined(StringBundler urlSB) {
		String urlSBString = urlSB.toString();

		if (urlSBString.contains(_DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL) ||
			urlSBString.contains(_DATA_HANDLER_PUBLIC_LAYOUT_SET_URL) ||
			urlSBString.contains(_DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL) ||
			urlSBString.contains(_DATA_HANDLER_PRIVATE_LAYOUT_SET_URL)) {

			return true;
		}

		return false;
	}

	private String _replaceTemplateLinkToLayout(
		String content, boolean privateLayout) {

		if (privateLayout) {
			content = StringUtil.replace(
				content, _DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);
		}
		else {
			content = StringUtil.replace(
				content, _DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
		}

		return content;
	}

	private static final String _DATA_HANDLER_COMPANY_SECURE_URL =
		"@data_handler_company_secure_url@";

	private static final String _DATA_HANDLER_COMPANY_URL =
		"@data_handler_company_url@";

	private static final String _DATA_HANDLER_GROUP_FRIENDLY_URL =
		"@data_handler_group_friendly_url@";

	private static final String _DATA_HANDLER_PATH_CONTEXT =
		"@data_handler_path_context@";

	private static final String _DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING =
		"@data_handler_private_group_servlet_mapping@";

	private static final String _DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL =
		"@data_handler_private_layout_set_secure_url@";

	private static final String _DATA_HANDLER_PRIVATE_LAYOUT_SET_URL =
		"@data_handler_private_layout_set_url@";

	private static final String _DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING =
		"@data_handler_private_user_servlet_mapping@";

	private static final String _DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL =
		"@data_handler_public_layout_set_secure_url@";

	private static final String _DATA_HANDLER_PUBLIC_LAYOUT_SET_URL =
		"@data_handler_public_layout_set_url@";

	private static final String _DATA_HANDLER_PUBLIC_SERVLET_MAPPING =
		"@data_handler_public_servlet_mapping@";

	private static final String _DATA_HANDLER_SITE_ADMIN_URL =
		"@data_handler_site_admin_url@";

	private static final char[] _LAYOUT_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.POUND, CharPool.QUESTION, CharPool.QUOTE,
		CharPool.SPACE
	};

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING) +
				StringPool.SLASH;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING) +
				StringPool.SLASH;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsUtil.get(PropsKeys.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING) +
			StringPool.SLASH;

	private static final String _TEMPLATE_NAME_PREFIX = "template";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReferencesExportImportContentProcessor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private ConfigurationProvider _configurationProvider;
	private ExportImportServiceConfiguration _exportImportServiceConfiguration;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}