/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.LARFileException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImport;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.apache.xerces.parsers.SAXParser;

import org.xml.sax.InputSource;

/**
 * @author Zsolt Berentey
 * @author Levente Hudák
 */
public class ExportImportImpl implements ExportImport {

	@Override
	public Calendar getDate(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int dateMonth = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Month");
		int dateDay = ParamUtil.getInteger(portletRequest, paramPrefix + "Day");
		int dateYear = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Year");
		int dateHour = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Hour");
		int dateMinute = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Minute");
		int dateAmPm = ParamUtil.getInteger(
			portletRequest, paramPrefix + "AmPm");

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = themeDisplay.getLocale();
			timeZone = themeDisplay.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

		calendar.set(Calendar.MONTH, dateMonth);
		calendar.set(Calendar.DATE, dateDay);
		calendar.set(Calendar.YEAR, dateYear);
		calendar.set(Calendar.HOUR_OF_DAY, dateHour);
		calendar.set(Calendar.MINUTE, dateMinute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	@Override
	public DateRange getDateRange(
			PortletRequest portletRequest, long groupId, boolean privateLayout,
			long plid, String portletId)
		throws Exception {

		Date startDate = null;
		Date endDate = null;

		String range = ParamUtil.getString(portletRequest, "range");

		if (range.equals("dateRange")) {
			startDate = getDate(portletRequest, "startDate", true).getTime();

			endDate = getDate(portletRequest, "endDate", true).getTime();
		}
		else if (range.equals("fromLastPublishDate")) {
			if (Validator.isNotNull(portletId) && (plid > 0)) {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				PortletPreferences preferences =
					PortletPreferencesFactoryUtil.getPortletSetup(
						layout, portletId, StringPool.BLANK);

				long lastPublishDate = GetterUtil.getLong(
					preferences.getValue(
						"last-publish-date", StringPool.BLANK));

				if (lastPublishDate > 0) {
					endDate = new Date();

					startDate = new Date(lastPublishDate);
				}
			}
			else {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				long lastPublishDate = GetterUtil.getLong(
					layoutSet.getSettingsProperty("last-publish-date"));

				if (lastPublishDate > 0) {
					endDate = new Date();

					startDate = new Date(lastPublishDate);
				}
			}
		}
		else if (range.equals("last")) {
			int rangeLast = ParamUtil.getInteger(portletRequest, "last");

			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		return new DateRange(startDate, endDate);
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);
		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				group.getCompanyId(), groupId, parameterMap,
				getUserIdStrategy(userId, userIdStrategy), zipReader);

		final ManifestSummary manifestSummary = new ManifestSummary();

		SAXParser saxParser = new SAXParser();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					String elementName = element.getName();

					if (elementName.equals("header")) {
						String exportDateString = element.attributeValue(
							"export-date");

						Date exportDate = GetterUtil.getDate(
							exportDateString,
							DateFormatFactoryUtil.getSimpleDateFormat(
								Time.RFC822_FORMAT));

						manifestSummary.setExportDate(exportDate);
					}
					else if (elementName.equals("portlet")) {
						String portletId = element.attributeValue("portlet-id");

						try {
							Portlet portlet =
								PortletLocalServiceUtil.getPortletById(
									group.getCompanyId(), portletId);

							if (GetterUtil.getBoolean(
									element.attributeValue("portlet-setup"))) {

								manifestSummary.addSetupPortlet(portlet);
							}

							if ((portlet.
									getPortletDataHandlerInstance() != null) &&
								GetterUtil.getBoolean(
									element.attributeValue("portlet-data"))) {

								manifestSummary.addDataPortlet(portlet);
							}
						}
						catch (SystemException se) {
						}
					}
					else if (elementName.equals("staged-model")) {
						String className = element.attributeValue("class-name");
						long count = GetterUtil.getLong(element.getText());

						manifestSummary.addModelCount(className, count);
					}
				}

			},
			new String[] {"header", "portlet", "staged-model"});

		saxParser.setContentHandler(elementHandler);

		InputStream is = portletDataContext.getZipEntryAsInputStream(
			"/manifest.xml");

		if (is == null) {
			throw new LARFileException("manifest.xml is not in the LAR");
		}

		String manifestXMLContent = StringUtil.read(is);

		saxParser.parse(new InputSource(new StringReader(manifestXMLContent)));

		return manifestSummary;
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			FileEntry fileEntry)
		throws Exception {

		File file = DLFileEntryLocalServiceUtil.getFile(
			userId, fileEntry.getFileEntryId(), fileEntry.getVersion(), false);
		File newFile = null;
		boolean rename = false;

		ManifestSummary manifestSummary = null;

		try {
			String newFileName = StringUtil.replace(
				file.getPath(), file.getName(), fileEntry.getTitle());

			newFile = new File(newFileName);

			rename = file.renameTo(newFile);

			if (!rename) {
				newFile = FileUtil.createTempFile(fileEntry.getExtension());

				FileUtil.copyFile(file, newFile);
			}

			manifestSummary = getManifestSummary(
				userId, groupId, parameterMap, newFile);

		}
		finally {
			if (rename) {
				newFile.renameTo(file);
			}
			else {
				FileUtil.delete(newFile);
			}
		}

		return manifestSummary;
	}

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		content = ExportImportUtil.replaceExportLayoutReferences(
			portletDataContext, content, exportReferencedContent);
		content = ExportImportUtil.replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);

		content = ExportImportUtil.replaceExportDLReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);

		Element groupElement = entityElement.getParent();

		String groupElementName = groupElement.getName();

		if (!groupElementName.equals(JournalArticle.class.getSimpleName())) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String contextPath = PortalUtil.getPathContext();

		String[] patterns = {
			contextPath.concat("/c/document_library/get_file?"),
			contextPath.concat("/documents/"),
			contextPath.concat("/image/image_gallery?")
		};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String[]> dlReferenceParameters =
				getDLReferenceParameters(
					portletDataContext, content,
					beginPos + contextPath.length(), endPos);

			FileEntry fileEntry = getFileEntry(
				portletDataContext, dlReferenceParameters);

			if (fileEntry == null) {
				endPos = beginPos - 1;

				continue;
			}

			endPos = MapUtil.getInteger(dlReferenceParameters, "endPos");

			try {
				if (exportReferencedContent) {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, fileEntry);
				}

				portletDataContext.addReferenceElement(
					entityStagedModel, entityElement, fileEntry,
					FileEntry.class,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
					!exportReferencedContent);

				String path = ExportImportPathUtil.getModelPath(
					fileEntry.getGroupId(), FileEntry.class.getName(),
					fileEntry.getFileEntryId());

				sb.replace(beginPos, endPos, "[$dl-reference=" + path + "$]");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean exportReferencedContent)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		StringBuilder sb = new StringBuilder(content);

		String[] patterns = {"href=", "[["};

		int beginPos = -1;
		int endPos = content.length();
		int offset = 0;

		while (true) {
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
			else if (content.charAt(beginPos) == CharPool.OPEN_BRACKET) {
				offset = 2;
			}

			endPos = StringUtil.indexOfAny(
				content, _LAYOUT_REFERENCE_STOP_CHARS, beginPos + offset,
				endPos);

			if (endPos == -1) {
				endPos = beginPos - 1;

				continue;
			}

			String url = content.substring(beginPos + offset, endPos);

			String servletMapping = null;
			String servletMappingParam = null;

			if (url.startsWith(PortalUtil.getPathFriendlyURLPrivateGroup())) {
				servletMapping = PortalUtil.getPathFriendlyURLPrivateGroup();
				servletMappingParam =
					"@data_handler_private_group_servlet_mapping@";
			}
			else if (url.startsWith(
						PortalUtil.getPathFriendlyURLPrivateUser())) {

				servletMapping = PortalUtil.getPathFriendlyURLPrivateUser();
				servletMappingParam =
					"@data_handler_private_user_servlet_mapping@";
			}
			else if (url.startsWith(PortalUtil.getPathFriendlyURLPublic())) {
				servletMapping = PortalUtil.getPathFriendlyURLPublic();
				servletMappingParam = "@data_handler_public_servlet_mapping@";
			}
			else {
				endPos = beginPos - 1;

				continue;
			}

			int beginGroupPos = beginPos + offset + servletMapping.length();

			if (content.charAt(beginGroupPos) == CharPool.SLASH) {
				int endGroupPos = url.indexOf(
					CharPool.SLASH, servletMapping.length() + 1);

				if (endGroupPos == -1) {
					endGroupPos = endPos;
				}
				else {
					endGroupPos = endGroupPos + beginPos + offset;
				}

				String groupFriendlyURL = content.substring(
					beginGroupPos, endGroupPos);

				if (groupFriendlyURL.equals(group.getFriendlyURL())) {
					sb.replace(
						beginGroupPos, endGroupPos,
						"@data_handler_group_friendly_url@");
				}
			}

			sb.replace(beginPos + offset, beginGroupPos, servletMappingParam);

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _exportLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long layoutId = GetterUtil.getLong(matcher.group(1));

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					portletDataContext.getScopeGroupId(), privateLayout,
					layoutId);

				String oldLinkToLayout = matcher.group(0);

				StringBundler sb = new StringBundler(5);

				sb.append(type);
				sb.append(StringPool.AT);
				sb.append(layout.getUuid());
				sb.append(StringPool.AT);
				sb.append(layout.getFriendlyURL());

				String newLinkToLayout = StringUtil.replace(
					oldLinkToLayout, type, sb.toString());

				oldLinksToLayout.add(oldLinkToLayout);
				newLinksToLayout.add(newLinkToLayout);

				if (exportReferencedContent) {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, layout);
				}

				portletDataContext.addReferenceElement(
					entityStagedModel, entityElement, layout, Layout.class,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
					!exportReferencedContent);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled() || _log.isWarnEnabled()) {
					String message =
						"Unable to get layout with ID " + layoutId +
							" in group " + portletDataContext.getScopeGroupId();

					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
					else {
						_log.debug(message, e);
					}
				}
			}
		}

		content = StringUtil.replace(
				content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
				ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		content = ExportImportUtil.replaceImportLayoutReferences(
			portletDataContext, content, importReferencedContent);
		content = ExportImportUtil.replaceImportLinksToLayouts(
			portletDataContext, content, importReferencedContent);

		content = ExportImportUtil.replaceImportDLReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);

		return content;
	}

	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				entityElement, FileEntry.class);

		for (Element referenceDataElement : referenceDataElements) {
			String fileEntryUUID = referenceDataElement.attributeValue("uuid");

			if (fileEntryUUID == null) {
				continue;
			}

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceDataElement);

			String path = referenceDataElement.attributeValue("path");

			FileEntry fileEntry = null;

			try {
				long groupId = portletDataContext.getScopeGroupId();

				long fileEntryGroupId = GetterUtil.getLong(
					referenceDataElement.attributeValue("group-id"));

				if (fileEntryGroupId ==
						portletDataContext.getSourceCompanyGroupId()) {

					groupId = portletDataContext.getSourceCompanyGroupId();
				}

				fileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					fileEntryUUID, groupId);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to reference " + path);
				}

				continue;
			}

			String url = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			content = StringUtil.replace(
				content, "[$dl-reference=" + path + "$]", url);
		}

		return content;
	}

	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		content = StringUtil.replace(
			content, "@data_handler_private_group_servlet_mapping@",
			PortalUtil.getPathFriendlyURLPrivateGroup());
		content = StringUtil.replace(
			content, "@data_handler_private_user_servlet_mapping@",
			PortalUtil.getPathFriendlyURLPrivateUser());
		content = StringUtil.replace(
			content, "@data_handler_public_servlet_mapping@",
			PortalUtil.getPathFriendlyURLPublic());

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		return content;
	}

	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long oldLayoutId = GetterUtil.getLong(matcher.group(1));

			long newLayoutId = oldLayoutId;

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			String layoutUuid = matcher.group(3);
			String friendlyURL = matcher.group(4);

			try {
				Layout layout =
					LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
						layoutUuid, portletDataContext.getScopeGroupId(),
						privateLayout);

				if (layout == null) {
					layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
						portletDataContext.getScopeGroupId(), privateLayout,
						friendlyURL);
				}

				if (layout == null) {
					layout = LayoutLocalServiceUtil.fetchLayout(
						portletDataContext.getScopeGroupId(), privateLayout,
						oldLayoutId);
				}

				if (layout == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(9);

						sb.append("Unable to get layout with UUID ");
						sb.append(layoutUuid);
						sb.append(", friendly URL ");
						sb.append(friendlyURL);
						sb.append(", or ");
						sb.append("layoutId ");
						sb.append(oldLayoutId);
						sb.append(" in group ");
						sb.append(portletDataContext.getScopeGroupId());

						_log.warn(sb.toString());
					}
				}
				else {
					newLayoutId = layout.getLayoutId();
				}
			}
			catch (SystemException se) {
				if (_log.isDebugEnabled() || _log.isWarnEnabled()) {
					String message =
						"Unable to get layout in group " +
							portletDataContext.getScopeGroupId();

					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
					else {
						_log.debug(message, se);
					}
				}
			}

			String oldLinkToLayout = matcher.group(0);

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.AT);
			sb.append(layoutUuid);
			sb.append(StringPool.AT);
			sb.append(friendlyURL);

			String newLinkToLayout = StringUtil.replace(
				oldLinkToLayout,
				new String[] {sb.toString(), String.valueOf(oldLayoutId)},
				new String[] {StringPool.BLANK, String.valueOf(newLayoutId)});

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	@Override
	public Map<String, MissingReference> validateMissingReferences(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		final Map<String, MissingReference> missingReferences =
			new HashMap<String, MissingReference>();

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		String userIdStrategy = MapUtil.getString(
				parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);
		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				group.getCompanyId(), groupId, new HashMap<String, String[]>(),
				getUserIdStrategy(userId, userIdStrategy), zipReader);

		SAXParser saxParser = new SAXParser();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					MissingReference missingReference =
						validateMissingReference(element);

					if (missingReference != null) {
						MissingReference existingMissingReference =
							missingReferences.get(
								missingReference.getDisplayName());

						if (existingMissingReference != null) {
							existingMissingReference.addReferrers(
								missingReference.getReferrers());
						}
						else {
							missingReferences.put(
								missingReference.getDisplayName(),
								missingReference);
						}
					}
				}

			},
			new String[] {"missing-reference"});

		saxParser.setContentHandler(elementHandler);

		saxParser.parse(
			new InputSource(
				portletDataContext.getZipEntryAsInputStream("/manifest.xml")));

		return missingReferences;
	}

	@Override
	public void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		Element rootElement = document.getRootElement();

		Element summaryElement = rootElement.addElement("summary");

		Map<String, Long> modelCounters = manifestSummary.getModelCounters();

		for (String modelClassName : modelCounters.keySet()) {
			Element element = summaryElement.addElement("staged-model");

			element.addAttribute("class-name", modelClassName);

			String count = String.valueOf(modelCounters.get(modelClassName));

			element.addText(count);
		}
	}

	protected Map<String, String[]> getDLReferenceParameters(
		PortletDataContext portletDataContext, String content, int beginPos,
		int endPos) {

		boolean legacyURL = true;
		char[] stopChars = _DL_REFERENCE_LEGACY_STOP_CHARS;

		if (content.startsWith("/documents/", beginPos)) {
			legacyURL = false;
			stopChars = _DL_REFERENCE_STOP_CHARS;
		}

		endPos = StringUtil.indexOfAny(content, stopChars, beginPos, endPos);

		if (endPos == -1) {
			return null;
		}

		Map<String, String[]> map = new HashMap<String, String[]>();

		String dlReference = content.substring(beginPos, endPos);

		while (dlReference.contains(StringPool.AMPERSAND_ENCODED)) {
			dlReference = dlReference.replace(
				StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		if (!legacyURL) {
			String[] pathArray = dlReference.split(StringPool.SLASH);

			map.put("groupId", new String[] {pathArray[2]});

			if (pathArray.length == 4) {
				map.put("uuid", new String[] {pathArray[3]});
			}
			else if (pathArray.length == 5) {
				map.put("folderId", new String[] {pathArray[3]});
				map.put(
					"title", new String[] {HttpUtil.decodeURL(pathArray[4])});
			}
			else if (pathArray.length > 5) {
				map.put("uuid", new String[] {pathArray[5]});
			}
		}
		else {
			dlReference = dlReference.substring(
				dlReference.indexOf(CharPool.QUESTION) + 1);

			map = HttpUtil.parameterMapFromString(dlReference);

			if (map.containsKey("img_id")) {
				map.put("image_id", map.get("img_id"));
			}
			else if (map.containsKey("i_id")) {
				map.put("image_id", map.get("i_id"));
			}
		}

		map.put("endPos", new String[] {String.valueOf(endPos)});

		String groupIdString = MapUtil.getString(map, "groupId");

		if (groupIdString.equals("@group_id@")) {
			groupIdString = String.valueOf(
				portletDataContext.getScopeGroupId());

			map.put("groupId", new String[] {groupIdString});
		}

		return map;
	}

	protected FileEntry getFileEntry(
		PortletDataContext portletDataContext, Map<String, String[]> map) {

		if (map == null) {
			return null;
		}

		FileEntry fileEntry = null;

		try {
			String uuid = MapUtil.getString(map, "uuid");
			long groupId = MapUtil.getLong(map, "groupId");

			if (Validator.isNotNull(uuid)) {
				fileEntry =
					DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
						uuid, groupId);
			}
			else {
				if (map.containsKey("folderId")) {
					long folderId = MapUtil.getLong(map, "folderId");
					String name = MapUtil.getString(map, "name");
					String title = MapUtil.getString(map, "title");

					if (Validator.isNotNull(title)) {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							groupId, folderId, title);
					}
					else {
						DLFileEntry dlFileEntry =
							DLFileEntryLocalServiceUtil.fetchFileEntryByName(
								groupId, folderId, name);

						if (dlFileEntry != null) {
							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								dlFileEntry.getFileEntryId());
						}
					}
				}
				else if (map.containsKey("image_id")) {
					DLFileEntry dlFileEntry =
						DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(
							MapUtil.getLong(map, "image_id"));

					if (dlFileEntry != null) {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							dlFileEntry.getFileEntryId());
					}
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return fileEntry;
	}

	protected UserIdStrategy getUserIdStrategy(
			long userId, String userIdStrategy)
		throws Exception {

		User user = UserLocalServiceUtil.getUserById(userId);

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	protected MissingReference validateMissingReference(Element element) {
		String className = element.attributeValue("class-name");

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				className);

		if (!stagedModelDataHandler.validateReference(
				element.getParent(), element)) {

			return new MissingReference(element);
		}

		return null;
	}

	private static final char[] _DL_REFERENCE_LEGACY_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUOTE, CharPool.SPACE
	};

	private static final char[] _DL_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	private static final char[] _LAYOUT_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	private static Log _log = LogFactoryUtil.getLog(ExportImportImpl.class);

	private Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");
	private Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@(\\p{XDigit}{8}\\-" +
		"(?:\\p{XDigit}{4}\\-){3}\\p{XDigit}{12})@([^\\]]*)\\]");

}