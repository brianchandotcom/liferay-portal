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

package com.liferay.portal.remote.jaxrs.whiteboard.debug.osgi.commands;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.DTOConstants;
import org.osgi.service.jaxrs.runtime.dto.ExtensionDTO;
import org.osgi.service.jaxrs.runtime.dto.FailedApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.FailedExtensionDTO;
import org.osgi.service.jaxrs.runtime.dto.FailedResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = {"osgi.command.function=check", "osgi.command.scope=jaxrs"},
	service = JaxrsServiceRuntimeOSGiCommands.class
)
public class JaxrsServiceRuntimeOSGiCommands {

	public void check() {
		RuntimeDTO runtimeDTO = _jaxrsServiceRuntime.getRuntimeDTO();

		if (runtimeDTO.defaultApplication != null) {
			System.out.println();

			printApplicationDTO(runtimeDTO.defaultApplication);
		}

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
			System.out.println();

			printApplicationDTO(applicationDTO);
		}

		for (FailedApplicationDTO failedApplicationDTO :
				runtimeDTO.failedApplicationDTOs) {

			printFailedApplicationDTO(failedApplicationDTO);
		}

		if (ArrayUtil.isNotEmpty(runtimeDTO.failedExtensionDTOs)) {
			System.out.println();

			System.out.println("Extensions report:");
		}

		for (FailedExtensionDTO failedExtensionDTO :
				runtimeDTO.failedExtensionDTOs) {

			printFailedExtensionDTO(failedExtensionDTO);
		}

		if (ArrayUtil.isNotEmpty(runtimeDTO.failedResourceDTOs)) {
			System.out.println();

			System.out.println("Resources report:");
		}

		for (FailedResourceDTO failedResourceDTO :
				runtimeDTO.failedResourceDTOs) {

			printFailedResourceDTO(failedResourceDTO);
		}
	}

	protected void printApplicationDTO(ApplicationDTO applicationDTO) {
		System.out.println(
			StringBundler.concat(
				"Application ", applicationDTO.name, " (",
				String.valueOf(applicationDTO.serviceId), ") ",
				applicationDTO.base));

		if (ArrayUtil.isNotEmpty(applicationDTO.resourceMethods)) {
			System.out.println();
		}

		for (ResourceMethodInfoDTO resourceMethodInfoDTO :
				applicationDTO.resourceMethods) {

			printResourceMethodInfoDTO("    ", resourceMethodInfoDTO);
		}

		if (ArrayUtil.isNotEmpty(applicationDTO.extensionDTOs)) {
			System.out.println();
			System.out.println("    Attached extensions:");
		}

		for (ExtensionDTO extensionDTO : applicationDTO.extensionDTOs) {
			System.out.println(
				StringBundler.concat(
					"        ", extensionDTO.name, " (",
					String.valueOf(extensionDTO.serviceId), ")"));
		}

		if (ArrayUtil.isNotEmpty(applicationDTO.resourceDTOs)) {
			System.out.println();
			System.out.println("    Attached resources:");
		}

		for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
			System.out.println(
				StringBundler.concat(
					"        ", resourceDTO.name, " (",
					String.valueOf(resourceDTO.serviceId), ")"));

			for (ResourceMethodInfoDTO resourceMethodInfoDTO :
					resourceDTO.resourceMethods) {

				printResourceMethodInfoDTO(
					"            ", resourceMethodInfoDTO);
			}
		}
	}

	protected void printFailedApplicationDTO(
		FailedApplicationDTO failedApplicationDTO) {

		StringBundler sb = new StringBundler(5);

		sb.append("    Application with service id ");
		sb.append(failedApplicationDTO.serviceId);

		if (failedApplicationDTO.failureReason ==
				DTOConstants.FAILURE_REASON_DUPLICATE_NAME) {

			sb.append(" is clashing with another service (");
			sb.append(failedApplicationDTO.name);
			sb.append(")");
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_EXTENSIONS_UNAVAILABLE) {

			sb.append(" has unresolved dependencies on extensions");
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SHADOWED_BY_OTHER_SERVICE) {

			sb.append(" is shadowed by another application (");
			sb.append(failedApplicationDTO.base);
			sb.append(")");
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SERVICE_NOT_GETTABLE) {

			sb.append(" can't be retrieved by the whiteboard");
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.FAILURE_REASON_UNKNOWN) {

			sb.append(" has failed for an unknown reason");
		}

		System.out.println(sb.toString());
	}

	protected void printFailedExtensionDTO(
		FailedExtensionDTO failedExtensionDTO) {

		StringBundler sb = new StringBundler(8);

		sb.append("    Extension ");
		sb.append(failedExtensionDTO.name);
		sb.append(" (");
		sb.append(failedExtensionDTO.serviceId);
		sb.append(")");

		if (failedExtensionDTO.failureReason ==
				DTOConstants.FAILURE_REASON_DUPLICATE_NAME) {

			sb.append(" is clashing with another service (");
			sb.append(failedExtensionDTO.name);
			sb.append(")");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.FAILURE_REASON_NOT_AN_EXTENSION_TYPE) {

			sb.append(" is not a valid extension type");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_APPLICATION_UNAVAILABLE) {

			sb.append(" is waiting for an application");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_EXTENSIONS_UNAVAILABLE) {

			sb.append(" has unresolved dependencies on extensions");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SERVICE_NOT_GETTABLE) {

			sb.append(" can't be retrieved by the whiteboard");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.FAILURE_REASON_UNKNOWN) {

			sb.append(" has failed for an unknown reason");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED) {

			sb.append(" has failed property validation");
		}

		System.out.println(sb.toString());
	}

	protected void printFailedResourceDTO(FailedResourceDTO failedResourceDTO) {
		StringBundler sb = new StringBundler(8);

		sb.append("    Resource ");
		sb.append(failedResourceDTO.name);
		sb.append(" (");
		sb.append(failedResourceDTO.serviceId);
		sb.append(")");

		if (failedResourceDTO.failureReason ==
				DTOConstants.FAILURE_REASON_DUPLICATE_NAME) {

			sb.append(" is clashing with another service (");
			sb.append(failedResourceDTO.name);
			sb.append(")");
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_APPLICATION_UNAVAILABLE) {

			sb.append(" is waiting for an application");
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_EXTENSIONS_UNAVAILABLE) {

			sb.append(" has unresolved dependencies on extensions");
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SERVICE_NOT_GETTABLE) {

			sb.append(" can't be retrieved by the whiteboard");
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.FAILURE_REASON_UNKNOWN) {

			sb.append(" has failed for an unknown reason");
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED) {

			sb.append(" has failed property validation");
		}

		System.out.println(sb.toString());
	}

	protected void printResourceMethodInfoDTO(
		String prefix, ResourceMethodInfoDTO resourceMethodInfoDTO) {

		System.out.println(
			StringBundler.concat(
				prefix, resourceMethodInfoDTO.method, " ",
				resourceMethodInfoDTO.path, " Consumes: ",
				Arrays.toString(resourceMethodInfoDTO.consumingMimeType),
				" Produces: ",
				Arrays.toString(resourceMethodInfoDTO.producingMimeType)));
	}

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

}
