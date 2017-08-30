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

package com.liferay.portal.osgi.debug.declarative.service.internal;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Collection;

import org.osgi.framework.Bundle;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentConfigurationDTO;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.service.component.runtime.dto.UnsatisfiedReferenceDTO;

/**
 * @author Tina Tian
 */
public class DeclarativeServiceUtil {

	public static String listUnsatisfiedDeclarativeServices(
		ServiceComponentRuntime serviceComponentRuntime, Bundle... bundles) {

		StringBundler sb = new StringBundler();

		for (Bundle bundle : bundles) {
			String unsatisfiedDeclarativeServices =
				_listUnsatisfiedDeclarativeServices(
					serviceComponentRuntime, bundle);

			if (!unsatisfiedDeclarativeServices.isEmpty()) {
				sb.append(unsatisfiedDeclarativeServices);
			}
		}

		return sb.toString();
	}

	private static String _collectUnsatisfiedInformation(
		ServiceComponentRuntime serviceComponentRuntime, Bundle bundle) {

		StringBundler sb = new StringBundler();

		Collection<ComponentDescriptionDTO> componentDescriptionDTOs =
			serviceComponentRuntime.getComponentDescriptionDTOs(bundle);

		for (ComponentDescriptionDTO componentDescriptionDTO :
				componentDescriptionDTOs) {

			Collection<ComponentConfigurationDTO> componentConfigurationDTOs =
				serviceComponentRuntime.getComponentConfigurationDTOs(
					componentDescriptionDTO);

			for (ComponentConfigurationDTO componentConfigurationDTO :
					componentConfigurationDTOs) {

				if (componentConfigurationDTO.state ==
						ComponentConfigurationDTO.UNSATISFIED_REFERENCE) {

					_collectUnsatisfiedReferences(
						componentDescriptionDTO, componentConfigurationDTO, sb);
				}
			}
		}

		return sb.toString();
	}

	private static void _collectUnsatisfiedReferences(
		ComponentDescriptionDTO componentDescriptionDTO,
		ComponentConfigurationDTO componentConfigurationDTO, StringBundler sb) {

		sb.append("\n    Declarative Service: id=");
		sb.append(componentConfigurationDTO.id);
		sb.append(",name=");
		sb.append(componentDescriptionDTO.name);
		sb.append(",unsatisfied references=");

		for (UnsatisfiedReferenceDTO unsatisfiedReferenceDTO :
				componentConfigurationDTO.unsatisfiedReferences) {

			sb.append("{name: ");
			sb.append(unsatisfiedReferenceDTO.name);
			sb.append(", target: ");
			sb.append(unsatisfiedReferenceDTO.target);
			sb.append("}");
		}
	}

	private static String _listUnsatisfiedDeclarativeServices(
		ServiceComponentRuntime serviceComponentRuntime, Bundle bundle) {

		if (bundle.getState() != Bundle.ACTIVE) {
			return StringPool.BLANK;
		}

		String unsatisfiedInformation = _collectUnsatisfiedInformation(
			serviceComponentRuntime, bundle);

		if (unsatisfiedInformation.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("\nBundle: id=");
		sb.append(bundle.getBundleId());
		sb.append(",name=");
		sb.append(bundle.getSymbolicName());
		sb.append(",version=");
		sb.append(bundle.getVersion());
		sb.append(unsatisfiedInformation);

		return sb.toString();
	}

}