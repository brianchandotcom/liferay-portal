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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPOptionException;
import com.liferay.commerce.product.exception.NoSuchCPOptionValueException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Option;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.OptionValueDTOConverter;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionValueResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/option-value.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, OptionValueResource.class}
)
public class OptionValueResourceImpl
	extends BaseOptionValueResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteOptionValue(@NotNull Long id) throws Exception {
		_cpOptionValueService.deleteCPOptionValue(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteOptionValueByExternalReferenceCode(
			@NotNull String externalReferenceCode)
		throws Exception {

		CPOptionValue cpOptionValue =
			_cpOptionValueService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (cpOptionValue == null) {
			throw new NoSuchCPOptionValueException(
				"Unable to find Option Value with externalReferenceCode: " +
					externalReferenceCode);
		}

		_cpOptionValueService.deleteCPOptionValue(
			cpOptionValue.getCPOptionValueId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<OptionValue> getOptionByExternalReferenceCodeOptionValuesPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPOption cpOption = _cpOptionService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (cpOption == null) {
			throw new NoSuchCPOptionException(
				"Unable to find Option with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CPOptionValue> cpOptionValues =
			_cpOptionValueService.getCPOptionValues(
				cpOption.getCPOptionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems = _cpOptionValueService.getCPOptionValuesCount(
			cpOption.getCPOptionId());

		return Page.of(_toOptionValues(cpOptionValues), pagination, totalItems);
	}

	@NestedField(parentClass = Option.class, value = "values")
	@Override
	public Page<OptionValue> getOptionIdOptionValuesPage(
			Long id, Pagination pagination)
		throws Exception {

		CPOption cpOption = _cpOptionService.getCPOption(id);

		List<CPOptionValue> cpOptionValues =
			_cpOptionValueService.getCPOptionValues(
				cpOption.getCPOptionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems = _cpOptionValueService.getCPOptionValuesCount(
			cpOption.getCPOptionId());

		return Page.of(_toOptionValues(cpOptionValues), pagination, totalItems);
	}

	@Override
	public OptionValue getOptionValue(@NotNull Long id) throws Exception {
		return _toOptionValue(id);
	}

	@Override
	public OptionValue getOptionValueByExternalReferenceCode(
			@NotNull String externalReferenceCode)
		throws Exception {

		CPOptionValue cpOptionValue =
			_cpOptionValueService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (cpOptionValue == null) {
			throw new NoSuchCPOptionValueException(
				"Unable to find Option Value with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _toOptionValue(cpOptionValue.getCPOptionValueId());
	}

	@Override
	public Response patchOptionValue(@NotNull Long id, OptionValue optionValue)
		throws Exception {

		CPOptionValue cpOptionValue = _cpOptionValueService.getCPOptionValue(
			id);

		_upsertOptionValue(cpOptionValue.getCPOption(), optionValue);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchOptionValueByExternalReferenceCode(
			@NotNull String externalReferenceCode, OptionValue optionValue)
		throws Exception {

		CPOptionValue cpOptionValue =
			_cpOptionValueService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (cpOptionValue == null) {
			throw new NoSuchCPOptionValueException(
				"Unable to find Option Value with externalReferenceCode: " +
					externalReferenceCode);
		}

		_upsertOptionValue(cpOptionValue.getCPOption(), optionValue);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public OptionValue postOptionByExternalReferenceCodeOptionValue(
			String externalReferenceCode, OptionValue optionValue)
		throws Exception {

		CPOption cpOption = _cpOptionService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (cpOption == null) {
			throw new NoSuchCPOptionException(
				"Unable to find Option with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertOptionValue(cpOption, optionValue);
	}

	@Override
	public OptionValue postOptionIdOptionValue(Long id, OptionValue optionValue)
		throws Exception {

		return _upsertOptionValue(
			_cpOptionService.getCPOption(id), optionValue);
	}

	private Map<String, String> _addAction(
			String actionId, long cpOptionValueId, UriInfo uriInfo,
			String methodName, Class<?> clazz)
		throws Exception {

		CPOptionValue cpOptionValue = _cpOptionValueService.getCPOptionValue(
			cpOptionValueId);

		if (!_cpOptionValueModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				cpOptionValue.getCPOptionId(), actionId)) {

			return null;
		}

		return HashMapBuilder.put(
			"href",
			() -> {
				UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

				return uriBuilder.path(
					_getVersion(uriInfo)
				).path(
					clazz.getSuperclass(), methodName
				).toTemplate();
			}
		).put(
			"method", _getHttpMethodName(clazz, _getMethod(clazz, methodName))
		).build();
	}

	private Map<String, Map<String, String>> _getActions(long cpOptionValueId)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			_addAction(
				ActionKeys.DELETE, cpOptionValueId, contextUriInfo,
				"deleteOptionValue", getClass())
		).put(
			"get",
			_addAction(
				ActionKeys.VIEW, cpOptionValueId, contextUriInfo,
				"getOptionValue", getClass())
		).put(
			"update",
			_addAction(
				ActionKeys.UPDATE, cpOptionValueId, contextUriInfo,
				"patchOptionValue", getClass())
		).build();
	}

	private String _getHttpMethodName(Class<?> clazz, Method method)
		throws Exception {

		Class<?> superClass = clazz.getSuperclass();

		Method superMethod = superClass.getMethod(
			method.getName(), method.getParameterTypes());

		for (Annotation annotation : superMethod.getAnnotations()) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			Annotation[] annotations = annotationType.getAnnotationsByType(
				HttpMethod.class);

			if (annotations.length > 0) {
				HttpMethod httpMethod = (HttpMethod)annotations[0];

				return httpMethod.value();
			}
		}

		return null;
	}

	private Method _getMethod(Class<?> clazz, String methodName) {
		for (Method method : clazz.getMethods()) {
			if (!methodName.equals(method.getName())) {
				continue;
			}

			return method;
		}

		return null;
	}

	private String _getVersion(UriInfo uriInfo) {
		String version = "";

		List<String> matchedURIs = uriInfo.getMatchedURIs();

		if (!matchedURIs.isEmpty()) {
			version = matchedURIs.get(matchedURIs.size() - 1);
		}

		return version;
	}

	private OptionValue _toOptionValue(Long cpOptionValueId) throws Exception {
		return _optionValueDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpOptionValueId), _dtoConverterRegistry,
				cpOptionValueId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	private List<OptionValue> _toOptionValues(
			List<CPOptionValue> cpOptionValues)
		throws Exception {

		List<OptionValue> productOptionValues = new ArrayList<>();

		for (CPOptionValue cpOptionValue : cpOptionValues) {
			productOptionValues.add(
				_toOptionValue(cpOptionValue.getCPOptionValueId()));
		}

		return productOptionValues;
	}

	private OptionValue _upsertOptionValue(
			CPOption cpOption, OptionValue optionValue)
		throws Exception {

		CPOptionValue cpOptionValue = _cpOptionValueService.upsertCPOptionValue(
			cpOption.getCPOptionId(),
			LanguageUtils.getLocalizedMap(optionValue.getName()),
			GetterUtil.get(optionValue.getPriority(), 0D), optionValue.getKey(),
			optionValue.getExternalReferenceCode(),
			_serviceContextHelper.getServiceContext());

		return _toOptionValue(cpOptionValue.getCPOptionValueId());
	}

	@Reference
	private CPOptionService _cpOptionService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOption)"
	)
	private ModelResourcePermission<CPOption>
		_cpOptionValueModelResourcePermission;

	@Reference
	private CPOptionValueService _cpOptionValueService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OptionValueDTOConverter _optionValueDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}