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

package com.liferay.portal.language.override.web.internal.portlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.provider.PLOOriginalTranslationProvider;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.language.override.service.PLOEntryService;
import com.liferay.portal.language.override.web.internal.constants.PLOPortletKeys;
import com.liferay.portal.language.override.web.internal.display.context.EditDisplayContextFactory;
import com.liferay.portal.language.override.web.internal.display.context.ViewDisplayContextFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Portal Language Override",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PLOPortletKeys.PORTAL_LANGUAGE_OVERRIDE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class PLOPortlet extends MVCPortlet {

	public void deletePLOEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		for (String key : ParamUtil.getStringValues(actionRequest, "key")) {
			_ploEntryService.deletePLOEntries(key);
		}
	}

	public void deletePLOEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		String key = ParamUtil.getString(actionRequest, "key");
		String selectedLanguageId = ParamUtil.getString(
			actionRequest, "selectedLanguageId");

		_ploEntryService.deletePLOEntry(key, selectedLanguageId);
	}

	public void editPLOEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		_ploEntryService.setPLOEntries(
			ParamUtil.getString(actionRequest, "key"),
			LocalizationUtil.getLocalizationMap(actionRequest, "value"));
	}

	public void importTranslations(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		File file = uploadPortletRequest.getFile("file");

		if ((file == null) ||
			!Objects.equals(
				FileUtil.getExtension(file.getName()), "properties")) {

			throw new PortalException();
		}

		String languageId = ParamUtil.getString(actionRequest, "languageId");

		Properties languageProperties = new Properties();

		languageProperties.load(new FileInputStream(file));

		Enumeration<String> languageKeyEnumeration =
			(Enumeration<String>)languageProperties.propertyNames();

		while (languageKeyEnumeration.hasMoreElements()) {
			String languageKey = languageKeyEnumeration.nextElement();

			_ploEntryService.addOrUpdatePLOEntry(
				languageKey, languageId,
				languageProperties.getProperty(languageKey));
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		String resourceID = resourceRequest.getResourceID();

		if (resourceID.equals("exportPLOEntries")) {
			_exportPLOEntries(resourceRequest, resourceResponse);
		}
	}

	@Activate
	protected void activate() {
		_editDisplayContextFactory = new EditDisplayContextFactory(
			_ploEntryLocalService, _ploOriginalTranslationProvider, _portal);
		_viewDisplayContextFactory = new ViewDisplayContextFactory(
			_permissionCheckerFactory, _ploEntryLocalService, _portal);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_setAttributes(renderRequest, renderResponse);

		super.doDispatch(renderRequest, renderResponse);
	}

	private void _exportPLOEntries(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			List<PLOEntry> ploEntries = _ploEntryService.getPLOEntries(
				_portal.getCompanyId(resourceRequest));

			Stream<PLOEntry> stream = ploEntries.stream();

			Map<String, List<PLOEntry>> map = stream.collect(
				Collectors.groupingBy(PLOEntry::getLanguageId));

			for (Map.Entry<String, List<PLOEntry>> entry : map.entrySet()) {
				StringBundler sb = new StringBundler();

				for (PLOEntry ploEntry : entry.getValue()) {
					sb.append(ploEntry.getKey());
					sb.append(StringPool.EQUAL);
					sb.append(ploEntry.getValue());
					sb.append(StringPool.NEW_LINE);
				}

				zipWriter.addEntry(
					"Language_" + entry.getKey() + ".properties",
					sb.toString());
			}

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				StringUtil.randomString() + ".zip",
				new FileInputStream(zipWriter.getFile()),
				ContentTypes.APPLICATION_ZIP);
		}
		catch (IOException | PortalException exception) {
			throw new PortletException(exception);
		}
	}

	private Object _getPortletDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		String path = getPath(renderRequest, renderResponse);

		if ((path == null) || path.equals("/view.jsp")) {
			return _viewDisplayContextFactory.create(
				renderRequest, renderResponse);
		}
		else if (path.equals("/edit_plo_entry.jsp")) {
			return _editDisplayContextFactory.create(renderRequest);
		}

		return null;
	}

	private void _setAttributes(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Object portletDisplayContext = _getPortletDisplayContext(
			renderRequest, renderResponse);

		if (portletDisplayContext != null) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, portletDisplayContext);
		}
	}

	private EditDisplayContextFactory _editDisplayContextFactory;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	@Reference
	private PLOEntryService _ploEntryService;

	@Reference
	private PLOOriginalTranslationProvider _ploOriginalTranslationProvider;

	@Reference
	private Portal _portal;

	private ViewDisplayContextFactory _viewDisplayContextFactory;

}