/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@ProviderType
public interface InfoItemServiceRegistry {

	public <P> List<P> getAllInfoItemServices(Class<P> serviceClass);

	public default <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName) {

		return getAllInfoItemServices(serviceClass, itemClassName, null);
	}

	public <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName,
		InfoItemServiceFilter infoItemServiceFilter);

	public default <P> P getFirstInfoItemService(
		Class<P> serviceClass, String itemClassName) {

		return getFirstInfoItemService(serviceClass, itemClassName, null);
	}

	public <P> P getFirstInfoItemService(
		Class<P> serviceClass, String itemClassName,
		InfoItemServiceFilter infoItemServiceFilter);

	public List<InfoItemCapability> getInfoItemCapabilities(
		String itemClassName);

	public InfoItemCapability getInfoItemCapability(
		String infoItemCapabilityKey);

	public <P> List<InfoItemClassDetails> getInfoItemClassDetails(
		Class<P> serviceClass);

	public List<InfoItemClassDetails> getInfoItemClassDetails(
			InfoItemCapability itemCapability)
		throws CapabilityVerificationException;

	public List<InfoItemClassDetails> getInfoItemClassDetails(
			long groupId, String itemCapabilityKey,
			PermissionChecker permissionChecker)
		throws CapabilityVerificationException;

	public List<InfoItemClassDetails> getInfoItemClassDetails(
			String itemCapabilityKey)
		throws CapabilityVerificationException;

	public <P> List<String> getInfoItemClassNames(Class<P> serviceClass);

	public <P> P getInfoItemService(Class<P> serviceClass, String serviceKey);

}