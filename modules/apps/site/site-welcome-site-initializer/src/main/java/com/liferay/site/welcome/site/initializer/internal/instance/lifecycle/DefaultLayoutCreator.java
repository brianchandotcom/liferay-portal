/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.welcome.site.initializer.internal.instance.lifecycle;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.InitialRequestSyncUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PropsValues;
import com.liferay.site.initializer.SiteInitializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = DefaultLayoutCreator.class)
public class DefaultLayoutCreator {

	public void createDefaultLayout(long companyId) {
		_companyIds.add(companyId);

		_saveCompanyIds(_companyIds);

		InitialRequestSyncUtil.registerSyncCallable(
			() -> {
				try {
					_createDefaultLayout(companyId);
				}
				finally {
					_companyIds.remove(companyId);

					_saveCompanyIds(_companyIds);
				}

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		for (long companyId : _loadCompanyIds()) {
			createDefaultLayout(companyId);
		}
	}

	private void _createDefaultLayout(long companyId) throws Exception {
		Group group = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		String friendlyURL = _friendlyURLNormalizer.normalizeWithEncoding(
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

		Layout defaultLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			group.getGroupId(), false, friendlyURL);

		if (defaultLayout == null) {
			defaultLayout = _layoutLocalService.fetchFirstLayout(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false);

			if (defaultLayout == null) {
				String name = PrincipalThreadLocal.getName();

				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				try {
					User user = _getUser(companyId);

					PrincipalThreadLocal.setName(user.getUserId());

					PermissionThreadLocal.setPermissionChecker(
						_defaultPermissionCheckerFactory.create(user));

					ServiceContextThreadLocal.pushServiceContext(
						new ServiceContext());

					_siteInitializer.initialize(group.getGroupId());
				}
				finally {
					PrincipalThreadLocal.setName(name);

					PermissionThreadLocal.setPermissionChecker(
						permissionChecker);

					ServiceContextThreadLocal.popServiceContext();
				}
			}
		}
	}

	private User _getUser(long companyId) throws PortalException {
		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.ADMINISTRATOR);

		if (role == null) {
			return _userLocalService.getGuestUser(companyId);
		}

		List<User> adminUsers = _userLocalService.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (adminUsers.isEmpty()) {
			return _userLocalService.getGuestUser(companyId);
		}

		return adminUsers.get(0);
	}

	private List<Long> _loadCompanyIds() {
		List<Long> companyIds = new ArrayList<>();

		File dataFile = _bundleContext.getDataFile("companyIds.data");

		if (dataFile.exists() && !StartupHelperUtil.isDBNew()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(dataFile)));

				Bundle bundle = _bundleContext.getBundle();

				if (deserializer.readLong() == bundle.getLastModified()) {
					int size = deserializer.readInt();

					for (int i = 0; i < size; i++) {
						companyIds.add(deserializer.readLong());
					}
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to load company ids", exception);
				}
			}
		}

		return companyIds;
	}

	private void _saveCompanyIds(Set<Long> companyIds) {
		File dataFile = _bundleContext.getDataFile("companyIds.data");

		if (companyIds.isEmpty()) {
			dataFile.delete();

			return;
		}

		Bundle bundle = _bundleContext.getBundle();

		Serializer serializer = new Serializer();

		serializer.writeLong(bundle.getLastModified());

		serializer.writeInt(companyIds.size());

		for (long companyId : companyIds) {
			serializer.writeLong(companyId);
		}

		try (OutputStream outputStream = new FileOutputStream(dataFile)) {
			serializer.writeTo(outputStream);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to write company ids", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLayoutCreator.class);

	private BundleContext _bundleContext;
	private final Set<Long> _companyIds = new HashSet<>();

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(site.initializer.key=com.liferay.site.initializer.welcome)"
	)
	private SiteInitializer _siteInitializer;

	@Reference
	private UserLocalService _userLocalService;

}