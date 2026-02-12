/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.List;

/**
 * @author Mikel Lorza
 */
public class InfoItemServiceRegistryUtil {

	public static <P> List<P> getAllInfoItemServices(Class<P> serviceClass) {
		return getService().getAllInfoItemServices(serviceClass);
	}

	public static <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName) {

		return getService().getAllInfoItemServices(
			serviceClass, itemClassName, null);
	}

	public static <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName,
		InfoItemServiceFilter infoItemServiceFilter) {

		return getService().getAllInfoItemServices(
			serviceClass, itemClassName, infoItemServiceFilter);
	}

	public static <P> P getFirstInfoItemService(
		Class<P> serviceClass, String itemClassName) {

		return getService().getFirstInfoItemService(
			serviceClass, itemClassName, null);
	}

	public static <P> P getFirstInfoItemService(
		Class<P> serviceClass, String itemClassName,
		InfoItemServiceFilter infoItemServiceFilter) {

		return getService().getFirstInfoItemService(
			serviceClass, itemClassName, infoItemServiceFilter);
	}

	public static List<InfoItemCapability> getInfoItemCapabilities(
		String itemClassName) {

		return getService().getInfoItemCapabilities(itemClassName);
	}

	public static InfoItemCapability getInfoItemCapability(
		String infoItemCapabilityKey) {

		return getService().getInfoItemCapability(infoItemCapabilityKey);
	}

	public static <P> List<InfoItemClassDetails> getInfoItemClassDetails(
		Class<P> serviceClass) {

		return getService().getInfoItemClassDetails(serviceClass);
	}

	public static List<InfoItemClassDetails> getInfoItemClassDetails(
			InfoItemCapability itemCapability)
		throws CapabilityVerificationException {

		return getService().getInfoItemClassDetails(itemCapability);
	}

	public static List<InfoItemClassDetails> getInfoItemClassDetails(
			long groupId, String itemCapabilityKey,
			PermissionChecker permissionChecker)
		throws CapabilityVerificationException {

		return getService().getInfoItemClassDetails(
			groupId, itemCapabilityKey, permissionChecker);
	}

	public static List<InfoItemClassDetails> getInfoItemClassDetails(
			String itemCapabilityKey)
		throws CapabilityVerificationException {

		return getService().getInfoItemClassDetails(itemCapabilityKey);
	}

	public static <P> List<String> getInfoItemClassNames(
		Class<P> serviceClass) {

		return getService().getInfoItemClassNames(serviceClass);
	}

	public static <P> P getInfoItemService(
		Class<P> serviceClass, String serviceKey) {

		return getService().getInfoItemService(serviceClass, serviceKey);
	}

	public static InfoItemServiceRegistry getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<InfoItemServiceRegistry> _serviceSnapshot =
		new Snapshot<>(
			InfoItemServiceRegistryUtil.class, InfoItemServiceRegistry.class);

}