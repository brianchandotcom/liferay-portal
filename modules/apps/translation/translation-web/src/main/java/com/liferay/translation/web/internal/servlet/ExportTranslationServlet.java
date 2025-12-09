/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.web.internal.servlet;

import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.translation.manager.TranslationManager;
import com.liferay.translation.web.internal.helper.TranslationRequestHelper;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.translation.web.internal.servlet.ExportTranslationServlet",
		"osgi.http.whiteboard.servlet.pattern=/translation/export_translation",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class ExportTranslationServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			User user = _portal.getUser(httpServletRequest);

			if ((user == null) || user.isGuestUser()) {
				throw new PrincipalException.MustBeAuthenticated(
					StringPool.BLANK);
			}

			long[] segmentsExperienceIds = ParamUtil.getLongValues(
				httpServletRequest, "segmentsExperienceIds");

			TranslationRequestHelper translationRequestHelper =
				new TranslationRequestHelper(
					httpServletRequest, _infoItemServiceRegistry,
					_segmentsExperienceLocalService);

			String className = translationRequestHelper.getClassName(
				segmentsExperienceIds);

			Set<Long> classPKs = SetUtil.fromArray(
				_getClassPKs(
					className, segmentsExperienceIds,
					translationRequestHelper));

			InfoItemPermissionProvider infoItemPermissionProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemPermissionProvider.class, className);

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			if (infoItemPermissionProvider != null) {
				for (long classPK : classPKs) {
					if (!infoItemPermissionProvider.hasPermission(
							permissionChecker,
							new InfoItemReference(className, classPK),
							ActionKeys.VIEW)) {

						throw new PrincipalException.MustHavePermission(
							permissionChecker, className, classPK,
							ActionKeys.VIEW);
					}
				}
			}

			String sourceLanguageId = ParamUtil.getString(
				httpServletRequest, "sourceLanguageId");
			String[] targetLanguageIds = ParamUtil.getStringValues(
				httpServletRequest, "targetLanguageIds");
			String exportMimeType = ParamUtil.getString(
				httpServletRequest, "exportMimeType");

			File file = _translationManager.getXLIFFZipFile(
				className, SetUtil.toLongArray(classPKs, classPK -> classPK),
				exportMimeType, _portal.getLocale(httpServletRequest),
				sourceLanguageId, targetLanguageIds);

			try (InputStream inputStream = new FileInputStream(file)) {
				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse, file.getName(),
					inputStream, ContentTypes.APPLICATION_ZIP);
			}
		}
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}
	}

	private long[] _getClassPKs(
			String className, long[] segmentsExperienceIds,
			TranslationRequestHelper translationRequestHelper)
		throws PortalException {

		if (!className.equals(Layout.class.getName())) {
			return translationRequestHelper.getClassPKs(segmentsExperienceIds);
		}

		long[] classPKs = translationRequestHelper.getClassPKs(
			segmentsExperienceIds);

		long[] replacementClassPKs = new long[classPKs.length];

		for (int i = 0; i < classPKs.length; i++) {
			replacementClassPKs[i] = _getDraftLayoutPlid(classPKs[i]);
		}

		return replacementClassPKs;
	}

	private long _getDraftLayoutPlid(long plid) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if ((layout == null) || layout.isDraftLayout()) {
			return plid;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return plid;
		}

		return draftLayout.getPlid();
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private TranslationManager _translationManager;

}