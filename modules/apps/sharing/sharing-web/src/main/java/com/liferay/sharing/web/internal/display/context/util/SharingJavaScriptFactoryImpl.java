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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;
import com.liferay.sharing.model.SharingEntry;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
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
	public String createSharingJavaScript(HttpServletRequest request)
		throws PortalException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		String javaScript =
			"/com/liferay/sharing/web/internal/display/context/util" +
				"/dependencies/sharing_js.ftl";
		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse(request);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("namespace", liferayPortletResponse.getNamespace());
		template.put(
			"sharingDialogId",
			liferayPortletResponse.getNamespace() + "sharingDialogId");

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	@Override
	public String createSharingOnClickMethod(
		String className, long classPK, HttpServletRequest request) {

		String sharingPortletId = PortletProviderUtil.getPortletId(
			SharingEntry.class.getName(), PortletProvider.Action.EDIT);

		PortletURL sharingURL = PortletURLFactoryUtil.create(
			request, sharingPortletId, PortletRequest.RENDER_PHASE);

		sharingURL.setParameter("mvcRenderCommandName", "/sharing/share");

		long classNameId = _classNameLocalService.getClassNameId(className);

		sharingURL.setParameter("classNameId", String.valueOf(classNameId));

		sharingURL.setParameter("classPK", String.valueOf(classPK));

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse(request);

		sharingURL.setParameter(
			"refererPortletNamespace", liferayPortletResponse.getNamespace());
		sharingURL.setParameter(
			"sharingDialogId",
			liferayPortletResponse.getNamespace() + "sharingDialogId");

		try {
			sharingURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			throw new SystemException("Unable to set window state", wse);
		}

		StringBundler sb = new StringBundler(6);

		sb.append(liferayPortletResponse.getNamespace());
		sb.append("sharing('");
		sb.append(sharingURL.toString());
		sb.append("', '");

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			request.getLocale(), SharingJavaScriptFactoryImpl.class);

		String title = _getTitle(className, classPK, request.getLocale());

		if (Validator.isNotNull(title)) {
			sb.append(LanguageUtil.format(resourceBundle, "share-x", title));
		}
		else {
			sb.append(LanguageUtil.get(resourceBundle, "share"));
		}

		sb.append("');");

		return sb.toString();
	}

	private LiferayPortletResponse _getLiferayPortletResponse(
		HttpServletRequest request) {

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		return _portal.getLiferayPortletResponse(portletResponse);
	}

	private String _getTitle(String className, long classPK, Locale locale) {
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
					"Cannot find asset renderer for classPK: " + classPK, pe);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingJavaScriptFactoryImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Portal _portal;

}