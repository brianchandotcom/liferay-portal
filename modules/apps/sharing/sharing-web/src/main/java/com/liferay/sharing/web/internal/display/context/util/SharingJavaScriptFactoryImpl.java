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

package com.liferay.sharing.web.internal.display.context.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.web.internal.constants.SharingWebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = SharingJavaScriptFactory.class)
public class SharingJavaScriptFactoryImpl implements SharingJavaScriptFactory {

	@Override
	public String createManageCollaboratorsJavaScript(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getOpenDialogJavaScript(
			httpServletRequest, SharingWebKeys.MANAGE_COLLABORATORS_DIALOG_ID);
	}

	@Override
	public String createManageCollaboratorsOnClickMethod(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			httpServletRequest.getLocale(), SharingJavaScriptFactoryImpl.class);

		return _getOpenDialogOnClickMethod(
			_getManageCollaboratorsPortletURL(
				className, classPK, httpServletRequest),
			LanguageUtil.get(resourceBundle, "collaborators"), true,
			SharingWebKeys.MANAGE_COLLABORATORS_DIALOG_ID,
			_getNamespace(httpServletRequest));
	}

	@Override
	public String createSharingJavaScript(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getOpenDialogJavaScript(
			httpServletRequest, SharingWebKeys.SHARING_DIALOG_ID);
	}

	@Override
	public String createSharingOnClickMethod(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getOpenDialogOnClickMethod(
			_getSharingPortletURL(className, classPK, httpServletRequest),
			_getSharingDialogTitle(
				className, classPK, httpServletRequest.getLocale()),
			false, SharingWebKeys.SHARING_DIALOG_ID,
			_getNamespace(httpServletRequest));
	}

	private String _getAssetTitle(
		String className, long classPK, Locale locale) {

		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			if (assetRendererFactory == null) {
				return null;
			}

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

			if (assetRenderer == null) {
				return null;
			}

			return assetRenderer.getTitle(locale);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get asset renderer with class primary key " +
						classPK,
					pe);
			}

			return null;
		}
	}

	private PortletURL _getManageCollaboratorsPortletURL(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL manageCollaboratorsURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, SharingEntry.class.getName(),
			PortletProvider.Action.MANAGE);

		manageCollaboratorsURL.setParameter(
			"classNameId",
			String.valueOf(_classNameLocalService.getClassNameId(className)));
		manageCollaboratorsURL.setParameter("classPK", String.valueOf(classPK));

		return manageCollaboratorsURL;
	}

	private String _getNamespace(HttpServletRequest httpServletRequest) {
		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return portletResponse.getNamespace();
	}

	private String _getOpenDialogJavaScript(
			HttpServletRequest httpServletRequest, String dialogId)
		throws TemplateException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		String javaScript =
			"/com/liferay/sharing/web/internal/display/context/util" +
				"/dependencies/open_dialog.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("dialogId", dialogId);
		template.put("namespace", _getNamespace(httpServletRequest));

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	private String _getOpenDialogOnClickMethod(
		PortletURL portletURL, String title, boolean refreshOnClose,
		String dialogId, String namespace) {

		portletURL.setParameter("dialogId", namespace + dialogId);
		portletURL.setParameter("refererPortletNamespace", namespace);

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			throw new SystemException("Unable to set window state", wse);
		}

		StringBundler sb = new StringBundler(10);

		sb.append(namespace);
		sb.append("open_");
		sb.append(dialogId);
		sb.append("('");
		sb.append(portletURL.toString());
		sb.append("', '");
		sb.append(title);
		sb.append("', ");
		sb.append(refreshOnClose);
		sb.append(");");

		return sb.toString();
	}

	private String _getSharingDialogTitle(
		String className, long classPK, Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, SharingJavaScriptFactoryImpl.class);

		String title = _getAssetTitle(className, classPK, locale);

		if (Validator.isNotNull(title)) {
			return LanguageUtil.format(
				resourceBundle, "share-x", HtmlUtil.escapeJS(title));
		}

		return LanguageUtil.get(resourceBundle, "share");
	}

	private PortletURL _getSharingPortletURL(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL sharingURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, SharingEntry.class.getName(),
			PortletProvider.Action.EDIT);

		sharingURL.setParameter(
			"classNameId",
			String.valueOf(_classNameLocalService.getClassNameId(className)));
		sharingURL.setParameter("classPK", String.valueOf(classPK));

		return sharingURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingJavaScriptFactoryImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

}