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

package com.liferay.site.welcome.site.initializer.internal;

import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.InputStream;

import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + WelcomeSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class WelcomeSiteInitializer implements SiteInitializer {

	public static final String KEY = "site-welcome-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return null;
	}

	@Override
	public String getThumbnailSrc() {
		return null;
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			_addDefaultGuestPublicLayout(groupId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	private void _addDefaultGuestPublicLayout(long groupId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = _getUser(group.getCompanyId());

		String friendlyURL = FriendlyURLNormalizerUtil.normalizeWithEncoding(
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = _layoutLocalService.addLayout(
			user.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false, friendlyURL,
			serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		String currentName = PrincipalThreadLocal.getName();
		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.popServiceContext();

		PrincipalThreadLocal.setName(String.valueOf(layout.getUserId()));
		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			_importPageElement(draftLayout, serviceContext);

			layout = _layoutCopyHelper.copyLayout(draftLayout, layout);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				0, PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_TEMPLATE_ID, false);

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());

			_layoutLocalService.updatePriority(
				layout.getPlid(), LayoutConstants.FIRST_PRIORITY);

			_layoutLocalService.updateStatus(
				layout.getUserId(), layout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

			_layoutLocalService.updateStatus(
				layout.getUserId(), draftLayout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

			boolean updateLayoutSet = false;

			LayoutSet layoutSet = layout.getLayoutSet();

			if (Validator.isNotNull(
					PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID)) {

				layoutSet.setThemeId(
					PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID);

				updateLayoutSet = true;
			}

			if (Validator.isNotNull(
					PropsValues.
						DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID)) {

				layoutSet.setColorSchemeId(
					PropsValues.
						DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID);

				updateLayoutSet = true;
			}

			if (updateLayoutSet) {
				_layoutSetLocalService.updateLayoutSet(layoutSet);
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
		finally {
			PrincipalThreadLocal.setName(currentName);
			ServiceContextThreadLocal.pushServiceContext(currentServiceContext);
		}
	}

	private long _getTreeImageId(
			long groupId, long userId, long plid, ServiceContext serviceContext)
		throws Exception {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, Layout.class.getName());

		if (repository == null) {
			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = _portletFileRepository.addPortletRepository(
				groupId, Layout.class.getName(), serviceContext);
		}

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			groupId, repository.getDlFolderId(), _FILE_NAME_TREE_IMAGE);

		if (fileEntry != null) {
			_portletFileRepository.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		URL url = _bundle.getEntry(_PATH + _FILE_NAME_TREE_IMAGE);

		byte[] bytes = null;

		try (InputStream inputStream = url.openStream()) {
			bytes = FileUtil.getBytes(inputStream);
		}

		fileEntry = _portletFileRepository.addPortletFileEntry(
			groupId, userId, Layout.class.getName(), plid,
			Layout.class.getName(), repository.getDlFolderId(), bytes,
			_FILE_NAME_TREE_IMAGE,
			MimeTypesUtil.getContentType(_FILE_NAME_TREE_IMAGE), false);

		return fileEntry.getFileEntryId();
	}

	private User _getUser(long companyId) throws PortalException {
		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.ADMINISTRATOR);

		if (role == null) {
			return _userLocalService.getDefaultUser(companyId);
		}

		List<User> adminUsers = _userLocalService.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (adminUsers.isEmpty()) {
			return _userLocalService.getDefaultUser(companyId);
		}

		return adminUsers.get(0);
	}

	private void _importPageElement(
			Layout layout, ServiceContext serviceContext)
		throws PortalException {

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(), layout.getPlid(), true);

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(
					SegmentsExperienceConstants.ID_DEFAULT));

			Class<?> clazz = getClass();

			String pageElementJSON = StringUtil.replace(
				StringUtil.read(
					clazz.getClassLoader(), _PATH + "page-element.json"),
				"\"[£", "£]\"",
				HashMapBuilder.put(
					"WELCOME_TO_LIFERAY_I18N_JSON_VALUE",
					() -> {
						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject();

						Set<Locale> locales = new HashSet<>(
							LanguageUtil.getAvailableLocales());

						for (Locale locale : locales) {
							jsonObject.put(
								LocaleUtil.toLanguageId(locale),
								LanguageUtil.get(locale, "welcome-to-liferay"));
						}

						return jsonObject.toJSONString();
					}
				).build());

			String releaseInfo = StringPool.BLANK;

			if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
				releaseInfo = ReleaseInfo.getName();
			}
			else if (!_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
				releaseInfo = ReleaseInfo.getReleaseInfo();
			}

			releaseInfo = StringUtil.replace(
				releaseInfo, CharPool.OPEN_PARENTHESIS, "<br>(");

			pageElementJSON = StringUtil.replace(
				pageElementJSON, "[$", "$]",
				HashMapBuilder.put(
					"RELEASE_INFO", releaseInfo + "."
				).put(
					"TREE_IMAGE_ID",
					String.valueOf(
						_getTreeImageId(
							layout.getGroupId(), layout.getUserId(),
							layout.getPlid(), serviceContext))
				).build());

			_layoutPageTemplatesImporter.importPageElement(
				layout, layoutStructure, layoutStructure.getMainItemId(),
				pageElementJSON, 0);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private static final String _FILE_NAME_TREE_IMAGE = "tree.png";

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "off");

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "partial");

	private static final String _PATH =
		"com/liferay/site/welcome/site/initializer/internal/dependencies/";

	private static final Log _log = LogFactoryUtil.getLog(
		WelcomeSiteInitializer.class);

	private Bundle _bundle;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.welcome.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}