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

package com.liferay.headless.commerce.admin.site.setting.internal.util.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0.DTOMapper;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
@Component(immediate = true, service = MeasurementUnitHelper.class)
public class MeasurementUnitHelper {

	public void deleteMeasurementUnit(Long id) throws PortalException {
		_cpMeasurementUnitService.deleteCPMeasurementUnit(id);
	}

	public MeasurementUnit getMeasurementUnit(Long id) throws PortalException {
		return _dtoMapper.modelToDTO(
			_cpMeasurementUnitService.getCPMeasurementUnit(id));
	}

	public Page<MeasurementUnit> getMeasurementUnits(
			Long companyId, Integer type, Pagination pagination)
		throws PortalException {

		List<CPMeasurementUnit> cpMeasurementUnits;
		int count;

		if (type == null) {
			cpMeasurementUnits =
				_cpMeasurementUnitService.getCPMeasurementUnits(
					companyId, pagination.getStartPosition(),
					pagination.getEndPosition(), null);

			count = _cpMeasurementUnitService.getCPMeasurementUnitsCount(
				companyId);
		}
		else {
			cpMeasurementUnits =
				_cpMeasurementUnitService.getCPMeasurementUnits(
					companyId, type, pagination.getStartPosition(),
					pagination.getEndPosition(), null);

			count = _cpMeasurementUnitService.getCPMeasurementUnitsCount(
				companyId, type);
		}

		List<MeasurementUnit> measurementUnits = new ArrayList<>();

		for (CPMeasurementUnit cpMeasurementUnit : cpMeasurementUnits) {
			measurementUnits.add(_dtoMapper.modelToDTO(cpMeasurementUnit));
		}

		return Page.of(measurementUnits, pagination, count);
	}

	public CPMeasurementUnit updateMeasurementUnit(
			Long id, MeasurementUnit measurementUnit, User user)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitService.getCPMeasurementUnit(id);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			cpMeasurementUnit.getGroupId(), new long[0], user, true);

		return _cpMeasurementUnitService.updateCPMeasurementUnit(
			cpMeasurementUnit.getCPMeasurementUnitId(),
			LanguageUtils.getLocalizedMap(measurementUnit.getName()),
			measurementUnit.getKey(),
			GetterUtil.get(
				measurementUnit.getRate(), cpMeasurementUnit.getRate()),
			GetterUtil.get(
				measurementUnit.getPrimary(), cpMeasurementUnit.isPrimary()),
			GetterUtil.get(
				measurementUnit.getPriority(), cpMeasurementUnit.getPriority()),
			GetterUtil.get(
				measurementUnit.getType(), cpMeasurementUnit.getType()),
			serviceContext);
	}

	public MeasurementUnit upsertMeasurementUnit(
			Long groupId, MeasurementUnit measurementUnit, User user)
		throws PortalException {

		try {
			CPMeasurementUnit cpMeasurementUnit = updateMeasurementUnit(
				measurementUnit.getId(), measurementUnit, user);

			return _dtoMapper.modelToDTO(cpMeasurementUnit);
		}
		catch (NoSuchCPMeasurementUnitException nscpmue) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find measurementUnit with ID: " +
						measurementUnit.getId());
			}
		}

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			groupId, new long[0], user, true);

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitService.addCPMeasurementUnit(
				LanguageUtils.getLocalizedMap(measurementUnit.getName()),
				measurementUnit.getKey(),
				GetterUtil.get(measurementUnit.getRate(), 0D),
				GetterUtil.get(measurementUnit.getPrimary(), false),
				GetterUtil.get(measurementUnit.getPriority(), 0D),
				GetterUtil.get(measurementUnit.getType(), 0), serviceContext);

		return _dtoMapper.modelToDTO(cpMeasurementUnit);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MeasurementUnitHelper.class);

	@Reference
	private CPMeasurementUnitService _cpMeasurementUnitService;

	@Reference
	private DTOMapper _dtoMapper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}