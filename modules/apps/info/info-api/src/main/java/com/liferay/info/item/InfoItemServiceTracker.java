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

package com.liferay.info.item;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.capability.InfoItemCapability;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@ProviderType
public interface InfoItemServiceTracker {

	public <P> List<P> getAllInfoItemServices(Class<P> serviceClass);

	public <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName);

	public <P> P getFirstInfoItemService(
		Class<P> serviceClass, String itemClassName);

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
			String itemCapabilityKey)
		throws CapabilityVerificationException;

	public <P> List<String> getInfoItemClassNames(Class<P> serviceClass);

	public <P> P getInfoItemService(Class<P> serviceClass, String serviceKey);

}