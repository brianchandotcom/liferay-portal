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

package com.liferay.frontend.theme.fjord.site.initializer.internal;

import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + FjordSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class FjordSiteInitializer implements SiteInitializer {

	public static final String KEY = "fjord-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return _THEME_NAME;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			ServiceContext serviceContext = _createServiceContext(groupId);

			_updateLogo(serviceContext);
			_updateLookAndFeel(serviceContext);

			_importFragmentEntries(serviceContext);

			_importLayoutPageTemplateEntries(serviceContext);

			_addLayout("Home", serviceContext);

			_addLayout("Features", serviceContext);

			_addLayout("Download", serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	private void _addLayout(String name, ServiceContext serviceContext)
		throws Exception {

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(name.trim());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				serviceContext.getScopeGroupId(), layoutPageTemplateEntryKey);

		if (layoutPageTemplateEntry == null) {
			throw new IllegalArgumentException(
				"Unable to get layout page template entry " + name);
		}

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), name
		).build();

		Layout layout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			_portal.getClassNameId(LayoutPageTemplateEntry.class),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), nameMap,
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_CONTENT, null, false, false, new HashMap<>(),
			0, serviceContext);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_copyLayout(layout);

				_layoutLocalService.updateStatus(
					serviceContext.getUserId(), layout.getPlid(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);

				return null;
			});
	}

	private void _copyLayout(Layout layout) throws Exception {
		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			_layoutCopyHelper.copyLayout(draftLayout, layout);
		}
	}

	private ServiceContext _createServiceContext(long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private void _importFragmentEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/fragments.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fragmentsImporter.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			file, false);
	}

	private void _importLayoutPageTemplateEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/page-templates.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_layoutPageTemplatesImporter.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), file,
			false);
	}

	private void _updateLogo(ServiceContext serviceContext) throws Exception {
		URL url = _bundle.getEntry(_PATH + "/images/logo.png");

		byte[] bytes = null;

		try (InputStream is = url.openStream()) {
			bytes = FileUtil.getBytes(is);
		}

		_layoutSetLocalService.updateLogo(
			serviceContext.getScopeGroupId(), false, true, bytes);
	}

	private void _updateLookAndFeel(ServiceContext serviceContext)
		throws PortalException {

		Theme theme = _themeLocalService.fetchTheme(
			serviceContext.getCompanyId(), _THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info("No theme found for " + _THEME_ID);
			}

			return;
		}

		_layoutSetLocalService.updateLookAndFeel(
			serviceContext.getScopeGroupId(), _THEME_ID, StringPool.BLANK,
			StringPool.BLANK);
	}

	private static final String _PATH =
		"com/liferay/frontend/theme/fjord/site/initializer/internal" +
			"/dependencies";

	private static final String _THEME_ID = "fjord_WAR_fjordtheme";

	private static final String _THEME_NAME = "Fjord";

	private static final Log _log = LogFactoryUtil.getLog(
		FjordSiteInitializer.class);

	private Bundle _bundle;

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.theme.fjord.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}