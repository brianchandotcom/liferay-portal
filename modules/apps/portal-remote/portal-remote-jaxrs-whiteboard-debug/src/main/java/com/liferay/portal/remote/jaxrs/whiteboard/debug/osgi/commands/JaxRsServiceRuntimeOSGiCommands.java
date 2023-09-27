/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.jaxrs.whiteboard.debug.osgi.commands;

import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.BaseDTO;
import org.osgi.service.jaxrs.runtime.dto.DTOConstants;
import org.osgi.service.jaxrs.runtime.dto.ExtensionDTO;
import org.osgi.service.jaxrs.runtime.dto.FailedApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.FailedExtensionDTO;
import org.osgi.service.jaxrs.runtime.dto.FailedResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {"osgi.command.function=check", "osgi.command.scope=jaxrs"},
	service = JaxRsServiceRuntimeOSGiCommands.class
)
public class JaxRsServiceRuntimeOSGiCommands {

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

		if (ArrayUtil.isNotEmpty(runtimeDTO.failedApplicationDTOs)) {
			System.out.println();

			System.out.println("Failed application report:");
		}

		for (FailedApplicationDTO failedApplicationDTO :
				runtimeDTO.failedApplicationDTOs) {

			printFailedApplicationDTO(
				failedApplicationDTO, runtimeDTO.applicationDTOs);
		}

		if (ArrayUtil.isNotEmpty(runtimeDTO.failedExtensionDTOs)) {
			System.out.println();

			System.out.println("Extensions report:");
		}

		Set<ExtensionDTO> extensionDTOsSet = new HashSet<>();

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
			for (ExtensionDTO extensionDTO : applicationDTO.extensionDTOs) {
				extensionDTOsSet.add(extensionDTO);
			}
		}

		ExtensionDTO[] extensionDTOS = extensionDTOsSet.toArray(
			new ExtensionDTO[0]);

		for (FailedExtensionDTO failedExtensionDTO :
				runtimeDTO.failedExtensionDTOs) {

			printFailedExtensionDTO(failedExtensionDTO, extensionDTOS);
		}

		if (ArrayUtil.isNotEmpty(runtimeDTO.failedResourceDTOs)) {
			System.out.println();

			System.out.println("Resources report:");

			Set<ResourceDTO> resourceDTOsSet = new HashSet<>();

			for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
				for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
					resourceDTOsSet.add(resourceDTO);
				}
			}

			ResourceDTO[] resourcesDTOs = resourceDTOsSet.toArray(
				new ResourceDTO[0]);

			for (FailedResourceDTO failedResourceDTO :
					runtimeDTO.failedResourceDTOs) {

				printFailedResourceDTO(failedResourceDTO, resourcesDTOs);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected BaseDTO getDTOByName(
		BaseDTO[] applicationDTOS, String jaxRsName) {

		for (BaseDTO applicationDTO : applicationDTOS) {
			if (jaxRsName.equals(applicationDTO.name)) {
				return applicationDTO;
			}
		}

		return null;
	}

	protected ServiceReference<?> getServiceReference(long serviceId) {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getServiceReferences(
					(String)null, "(service.id=" + serviceId + ")");

			if ((serviceReferences == null) ||
				(serviceReferences.length == 0)) {

				return null;
			}

			return serviceReferences[0];
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new IllegalArgumentException(invalidSyntaxException);
		}
	}

	protected void printApplicationDTO(ApplicationDTO applicationDTO) {
		System.out.println(
			StringBundler.concat(
				"Application ", applicationDTO.name, " (",
				applicationDTO.serviceId, ") ", applicationDTO.base));

		if (ArrayUtil.isNotEmpty(applicationDTO.resourceMethods)) {
			System.out.println();
		}

		for (ResourceMethodInfoDTO resourceMethodInfoDTO :
				applicationDTO.resourceMethods) {

			_printResourceMethodInfoDTO("    ", resourceMethodInfoDTO);
		}

		if (ArrayUtil.isNotEmpty(applicationDTO.extensionDTOs)) {
			System.out.println();
			System.out.println("    Attached extensions:");
		}

		for (ExtensionDTO extensionDTO : applicationDTO.extensionDTOs) {
			System.out.println(
				StringBundler.concat(
					"        ", extensionDTO.name, " (", extensionDTO.serviceId,
					")"));
		}

		if (ArrayUtil.isNotEmpty(applicationDTO.resourceDTOs)) {
			System.out.println();
			System.out.println("    Attached resources:");
		}

		for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
			System.out.println(
				StringBundler.concat(
					"        ", resourceDTO.name, " (", resourceDTO.serviceId,
					")"));

			for (ResourceMethodInfoDTO resourceMethodInfoDTO :
					resourceDTO.resourceMethods) {

				_printResourceMethodInfoDTO(
					"            ", resourceMethodInfoDTO);
			}
		}
	}

	protected void printFailedApplicationDTO(
		FailedApplicationDTO failedApplicationDTO,
		ApplicationDTO[] applicationDTOS) {

		StringBundler sb = new StringBundler(8);

		sb.append("    Application with service ID ");
		sb.append(failedApplicationDTO.serviceId);

		ServiceReference<?> serviceReference = getServiceReference(
			failedApplicationDTO.serviceId);

		String jaxRsName = GetterUtil.getString(
			serviceReference.getProperty(JaxrsWhiteboardConstants.JAX_RS_NAME));

		if (Validator.isNotNull(jaxRsName)) {
			sb.append(" and name ");
			sb.append(jaxRsName);
		}

		if (failedApplicationDTO.failureReason ==
				DTOConstants.FAILURE_REASON_DUPLICATE_NAME) {

			sb.append(" is clashing with another service");

			BaseDTO applicationDTOByName = getDTOByName(
				applicationDTOS, jaxRsName);

			if (applicationDTOByName != null) {
				sb.append(" (");
				sb.append(applicationDTOByName.serviceId);
				sb.append(")");
			}
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_EXTENSIONS_UNAVAILABLE) {

			sb.append(" has unresolved dependencies on extensions: ");
			sb.append(
				StringPlus.asList(
					serviceReference.getProperty(
						JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT)));
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SHADOWED_BY_OTHER_SERVICE) {

			sb.append(" is shadowed by another application (");
			sb.append(failedApplicationDTO.base);
			sb.append(")");
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SERVICE_NOT_GETTABLE) {

			sb.append(" cannot be retrieved by the whiteboard");
		}
		else if (failedApplicationDTO.failureReason ==
					DTOConstants.FAILURE_REASON_UNKNOWN) {

			sb.append(" has failed for an unknown reason");
		}

		System.out.println(sb.toString());
	}

	protected void printFailedExtensionDTO(
		FailedExtensionDTO failedExtensionDTO, ExtensionDTO[] extensionDTOS) {

		StringBundler sb = new StringBundler(9);

		sb.append("    Extension ");
		sb.append(failedExtensionDTO.name);
		sb.append(" (");
		sb.append(failedExtensionDTO.serviceId);
		sb.append(")");

		if (failedExtensionDTO.failureReason ==
				DTOConstants.FAILURE_REASON_DUPLICATE_NAME) {

			sb.append(" is clashing with another service");

			BaseDTO baseDTO = getDTOByName(
				extensionDTOS, failedExtensionDTO.name);

			if (baseDTO != null) {
				sb.append(" (");
				sb.append(failedExtensionDTO.serviceId);
				sb.append(")");
			}
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.FAILURE_REASON_NOT_AN_EXTENSION_TYPE) {

			sb.append(" is not a valid extension type");
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_APPLICATION_UNAVAILABLE) {

			sb.append(" is waiting for an application: ");

			ServiceReference<?> serviceReference = getServiceReference(
				failedExtensionDTO.serviceId);

			if (serviceReference != null) {
				sb.append(
					serviceReference.getProperty(
						JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT));
			}
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_EXTENSIONS_UNAVAILABLE) {

			sb.append(" has unresolved dependencies on extensions: ");

			ServiceReference<?> serviceReference = getServiceReference(
				failedExtensionDTO.serviceId);

			if (serviceReference != null) {
				sb.append(
					StringPlus.asList(
						serviceReference.getProperty(
							JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT)));
			}
		}
		else if (failedExtensionDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SERVICE_NOT_GETTABLE) {

			sb.append(" cannot be retrieved by the whiteboard");
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

	protected void printFailedResourceDTO(
		FailedResourceDTO failedResourceDTO, ResourceDTO[] resourceDTOS) {

		StringBundler sb = new StringBundler(9);

		sb.append("    Resource ");
		sb.append(failedResourceDTO.name);
		sb.append(" (");
		sb.append(failedResourceDTO.serviceId);
		sb.append(")");

		if (failedResourceDTO.failureReason ==
				DTOConstants.FAILURE_REASON_DUPLICATE_NAME) {

			sb.append(" is clashing with another service");

			BaseDTO baseDTO = getDTOByName(
				resourceDTOS, failedResourceDTO.name);

			if (baseDTO != null) {
				sb.append(" (");
				sb.append(failedResourceDTO.serviceId);
				sb.append(")");
			}
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_APPLICATION_UNAVAILABLE) {

			sb.append(" is waiting for an application: ");

			ServiceReference<?> serviceReference = getServiceReference(
				failedResourceDTO.serviceId);

			if (serviceReference != null) {
				sb.append(
					serviceReference.getProperty(
						JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT));
			}
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.
						FAILURE_REASON_REQUIRED_EXTENSIONS_UNAVAILABLE) {

			sb.append(" has unresolved dependencies on extensions: ");

			ServiceReference<?> serviceReference = getServiceReference(
				failedResourceDTO.serviceId);

			if (serviceReference != null) {
				sb.append(
					StringPlus.asList(
						serviceReference.getProperty(
							JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT)));
			}
		}
		else if (failedResourceDTO.failureReason ==
					DTOConstants.FAILURE_REASON_SERVICE_NOT_GETTABLE) {

			sb.append(" cannot be retrieved by the whiteboard");
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

	private void _printResourceMethodInfoDTO(
		String prefix, ResourceMethodInfoDTO resourceMethodInfoDTO) {

		System.out.println(
			StringBundler.concat(
				prefix, resourceMethodInfoDTO.method, " ",
				resourceMethodInfoDTO.path, " Consumes: ",
				Arrays.toString(resourceMethodInfoDTO.consumingMimeType),
				" Produces: ",
				Arrays.toString(resourceMethodInfoDTO.producingMimeType)));
	}

	private BundleContext _bundleContext;

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

}