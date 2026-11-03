/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.upgrade.v3_0_6;

import com.liferay.layout.importer.LayoutsImportStrategy;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jan Brychta
 */
public class CMSMissingLayoutPageTemplatesUpgradeProcess
	extends UpgradeProcess {

	public CMSMissingLayoutPageTemplatesUpgradeProcess(
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutsImporter layoutsImporter, RoleLocalService roleLocalService,
		UserLocalService userLocalService, ZipWriterFactory zipWriterFactory) {

		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutsImporter = layoutsImporter;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
		_zipWriterFactory = zipWriterFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		File file = _getLayoutPageTemplatesFile();

		if (file == null) {
			return;
		}

		try {
			_companyLocalService.forEachCompanyId(
				companyId -> _upgradeCompany(companyId, file));
		}
		finally {
			file.delete();
		}
	}

	private void _backfillContentEditorMasterExternalReferenceCode(long groupId)
		throws Exception {

		LayoutPageTemplateEntry contentEditorMasterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				groupId, "content-editor-master");

		if (contentEditorMasterLayoutPageTemplateEntry == null) {
			return;
		}

		String externalReferenceCode =
			contentEditorMasterLayoutPageTemplateEntry.
				getExternalReferenceCode();

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				_layoutPageTemplateEntryLocalService.
					getLayoutPageTemplateEntries(groupId)) {

			if ((layoutPageTemplateEntry.getType() !=
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE) ||
				!_contentEditorMasterDisplayPageTemplateNames.contains(
					layoutPageTemplateEntry.getName())) {

				continue;
			}

			Layout layout = _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());

			if (layout == null) {
				continue;
			}

			_updateMasterLayoutPageTemplateEntryERC(
				layout, externalReferenceCode);
			_updateMasterLayoutPageTemplateEntryERC(
				layout.fetchDraftLayout(), externalReferenceCode);
		}
	}

	private File _getLayoutPageTemplatesFile() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		if (bundle == null) {
			return null;
		}

		BundleContext bundleContext = bundle.getBundleContext();

		Bundle siteInitializerCMSBundle = null;

		for (Bundle candidateBundle : bundleContext.getBundles()) {
			if (Objects.equals(
					candidateBundle.getSymbolicName(),
					"com.liferay.site.initializer.cms")) {

				siteInitializerCMSBundle = candidateBundle;

				break;
			}
		}

		if (siteInitializerCMSBundle == null) {
			return null;
		}

		Enumeration<URL> enumeration = siteInitializerCMSBundle.findEntries(
			"/site-initializer/layout-page-templates", "*", true);

		if (enumeration == null) {
			return null;
		}

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String urlPath = url.getPath();

			if (urlPath.endsWith("/")) {
				continue;
			}

			try (InputStream inputStream = url.openStream()) {
				zipWriter.addEntry(
					_getRelativePath(
						urlPath, "/site-initializer/layout-page-templates"),
					inputStream);
			}
		}

		return zipWriter.getFile();
	}

	private String _getRelativePath(String path, String basePath) {
		int index = path.indexOf(basePath);

		return path.substring(index + basePath.length());
	}

	private User _getUser(long companyId) throws Exception {
		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.ADMINISTRATOR);

		if (role == null) {
			return _userLocalService.fetchGuestUser(companyId);
		}

		List<User> adminUsers = _userLocalService.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (adminUsers.isEmpty()) {
			return _userLocalService.fetchGuestUser(companyId);
		}

		return adminUsers.get(0);
	}

	private void _logInvalidLayoutsImporterResultEntries(
			long companyId,
			List<LayoutsImporterResultEntry> layoutsImporterResultEntries)
		throws Exception {

		if (!_log.isWarnEnabled()) {
			return;
		}

		Locale locale = LocaleUtil.fromLanguageId(
			UpgradeProcessUtil.getDefaultLanguageId(companyId));

		for (LayoutsImporterResultEntry layoutsImporterResultEntry :
				layoutsImporterResultEntries) {

			if (layoutsImporterResultEntry.getStatus() !=
					LayoutsImporterResultEntry.Status.INVALID) {

				continue;
			}

			_log.warn(
				StringBundler.concat(
					"Unable to import ", layoutsImporterResultEntry.getName(),
					": ", layoutsImporterResultEntry.getErrorMessage(locale)));
		}
	}

	private void _updateMasterLayoutPageTemplateEntryERC(
			Layout layout, String externalReferenceCode)
		throws Exception {

		if ((layout == null) ||
			Validator.isNotNull(layout.getMasterLayoutPageTemplateEntryERC())) {

			return;
		}

		layout.setMasterLayoutPageTemplateEntryERC(externalReferenceCode);

		_layoutLocalService.updateLayout(layout);
	}

	private void _upgradeCompany(long companyId, File file) throws Exception {
		Group group = _groupLocalService.fetchGroup(
			companyId, GroupConstants.CMS);

		if (group == null) {
			return;
		}

		User user = _getUser(companyId);

		if (user == null) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		String principalName = PrincipalThreadLocal.getName();
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries;

		try {
			PrincipalThreadLocal.setName(user.getUserId());
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			layoutsImporterResultEntries = _layoutsImporter.importFile(
				user.getUserId(), group.getGroupId(), 0, file,
				LayoutsImportStrategy.DO_NOT_OVERWRITE, true);
		}
		finally {
			PrincipalThreadLocal.setName(principalName);
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			ServiceContextThreadLocal.popServiceContext();
		}

		_logInvalidLayoutsImporterResultEntries(
			companyId, layoutsImporterResultEntries);

		_backfillContentEditorMasterExternalReferenceCode(group.getGroupId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMSMissingLayoutPageTemplatesUpgradeProcess.class);

	private static final Set<String>
		_contentEditorMasterDisplayPageTemplateNames = SetUtil.fromArray(
			"Basic Document", "Basic Web Content", "Blog", "External Video");

	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutsImporter _layoutsImporter;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;
	private final ZipWriterFactory _zipWriterFactory;

}