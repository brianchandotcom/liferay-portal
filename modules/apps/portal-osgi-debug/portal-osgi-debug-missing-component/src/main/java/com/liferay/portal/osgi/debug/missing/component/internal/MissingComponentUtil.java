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

package com.liferay.portal.osgi.debug.missing.component.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.bundle.blacklist.BundleBlacklistManager;

import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentConfigurationDTO;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.service.component.runtime.dto.SatisfiedReferenceDTO;
import org.osgi.service.component.runtime.dto.UnsatisfiedReferenceDTO;

/**
 * @author Matthew Tambara
 */
public class MissingComponentUtil {

	public static String scan(
		BundleContext bundleContext,
		ServiceComponentRuntime serviceComponentRuntime) {

		Bundle blacklistBundle = null;

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.portal.bundle.blacklist.impl")) {

				blacklistBundle = bundle;

				break;
			}
		}

		ServiceReference<BundleBlacklistManager> serviceReference =
			bundleContext.getServiceReference(BundleBlacklistManager.class);

		if (serviceReference != null) {
			return null;
		}

		ComponentDescriptionDTO componentDescriptionDTO =
			serviceComponentRuntime.getComponentDescriptionDTO(
				blacklistBundle,
				"com.liferay.portal.bundle.blacklist.internal." +
					"BundleBlacklistManagerImpl");

		StringBundler sb = new StringBundler();

		sb.append("@@@@Name: ");
		sb.append(componentDescriptionDTO.name);
		sb.append("\n@@Instances:");

		for (ComponentConfigurationDTO componentConfigurationDTO :
				serviceComponentRuntime.getComponentConfigurationDTOs(
					componentDescriptionDTO)) {

			sb.append("\nid: ");
			sb.append(componentConfigurationDTO.id);
			sb.append("\nState: ");
			sb.append(componentConfigurationDTO.state);
			sb.append("\n Satisfied references: ");

			for (SatisfiedReferenceDTO satisfiedReference :
					componentConfigurationDTO.satisfiedReferences) {

				sb.append("\n\tName: ");
				sb.append(satisfiedReference.name);
			}

			sb.append("\nUnsatisfied references: ");

			for (UnsatisfiedReferenceDTO unsatisfiedReference :
					componentConfigurationDTO.unsatisfiedReferences) {

				sb.append("\n\tName: ");
				sb.append(unsatisfiedReference.name);
			}
		}

		return sb.toString();
	}

}