/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPMeasurementUnitKeyException;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPMeasurementUnitConstants;
import com.liferay.commerce.product.service.base.CPMeasurementUnitLocalServiceBaseImpl;
import com.liferay.commerce.product.util.comparator.CPMeasurementUnitPriorityComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPMeasurementUnitLocalServiceImpl
	extends CPMeasurementUnitLocalServiceBaseImpl {

	@Override
	public CPMeasurementUnit addCPMeasurementUnit(
			Map<Locale, String> nameMap, String key, double rate,
			boolean primary, double priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (primary) {
			rate = 1;
		}

		validate(0, groupId, key, primary, type);

		long cpMeasurementUnitId = counterLocalService.increment();

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitPersistence.create(cpMeasurementUnitId);

		cpMeasurementUnit.setUuid(serviceContext.getUuid());
		cpMeasurementUnit.setGroupId(groupId);
		cpMeasurementUnit.setCompanyId(user.getCompanyId());
		cpMeasurementUnit.setUserId(user.getUserId());
		cpMeasurementUnit.setUserName(user.getFullName());
		cpMeasurementUnit.setNameMap(nameMap);
		cpMeasurementUnit.setKey(key);
		cpMeasurementUnit.setRate(rate);
		cpMeasurementUnit.setPrimary(primary);
		cpMeasurementUnit.setPriority(priority);
		cpMeasurementUnit.setType(type);

		cpMeasurementUnitPersistence.update(cpMeasurementUnit);

		return cpMeasurementUnit;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPMeasurementUnit deleteCPMeasurementUnit(
		CPMeasurementUnit cpMeasurementUnit) {

		return cpMeasurementUnitPersistence.remove(cpMeasurementUnit);
	}

	@Override
	public CPMeasurementUnit deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitPersistence.findByPrimaryKey(cpMeasurementUnitId);

		return cpMeasurementUnitLocalService.deleteCPMeasurementUnit(
			cpMeasurementUnit);
	}

	@Override
	public void deleteCPMeasurementUnits(long groupId) {
		cpMeasurementUnitPersistence.removeByGroupId(groupId);
	}

	@Override
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
		long groupId, int type) {

		return cpMeasurementUnitPersistence.fetchByG_P_T_First(
			groupId, true, type, new CPMeasurementUnitPriorityComparator());
	}

	@Override
	public List<CPMeasurementUnit> getCPMeasurementUnits(
		long groupId, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return cpMeasurementUnitPersistence.findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public List<CPMeasurementUnit> getCPMeasurementUnits(
		long groupId, String[] keys, int type) {

		List<CPMeasurementUnit> cpMeasurementUnits = new ArrayList<>(
			keys.length);

		for (String key : keys) {
			CPMeasurementUnit cpMeasurementUnit =
				cpMeasurementUnitPersistence.fetchByG_K_T(groupId, key, type);

			if (cpMeasurementUnit != null) {
				cpMeasurementUnits.add(cpMeasurementUnit);
			}
		}

		return cpMeasurementUnits;
	}

	@Override
	public int getCPMeasurementUnitsCount(long groupId) {
		return cpMeasurementUnitPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCPMeasurementUnitsCount(long groupId, int type) {
		return cpMeasurementUnitPersistence.countByG_T(groupId, type);
	}

	@Override
	public void importDefaultValues(ServiceContext serviceContext)
		throws PortalException {

		_addCPMeasurementUnit(
			"inches", "in", 1, true, 1,
			CPMeasurementUnitConstants.TYPE_DIMENSION, serviceContext);
		_addCPMeasurementUnit(
			"feet", "ft", 0.08333333, false, 2,
			CPMeasurementUnitConstants.TYPE_DIMENSION, serviceContext);
		_addCPMeasurementUnit(
			"meters", "m", 0.0254, false, 3,
			CPMeasurementUnitConstants.TYPE_DIMENSION, serviceContext);
		_addCPMeasurementUnit(
			"millimeters", "mm", 25.4, false, 1,
			CPMeasurementUnitConstants.TYPE_DIMENSION, serviceContext);

		_addCPMeasurementUnit(
			"ounces", "oz", 16, false, 1,
			CPMeasurementUnitConstants.TYPE_WEIGHT, serviceContext);
		_addCPMeasurementUnit(
			"pounds", "lb", 1, true, 2, CPMeasurementUnitConstants.TYPE_WEIGHT,
			serviceContext);
		_addCPMeasurementUnit(
			"kilograms", "kg", 0.45359237, false, 3,
			CPMeasurementUnitConstants.TYPE_WEIGHT, serviceContext);
		_addCPMeasurementUnit(
			"grams", "g", 453.59237, false, 4,
			CPMeasurementUnitConstants.TYPE_WEIGHT, serviceContext);
	}

	@Override
	public CPMeasurementUnit updateCPMeasurementUnit(
			long cpMeasurementUnitId, Map<Locale, String> nameMap, String key,
			double rate, boolean primary, double priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitPersistence.findByPrimaryKey(cpMeasurementUnitId);

		if (primary) {
			rate = 1;
		}

		validate(
			cpMeasurementUnit.getCPMeasurementUnitId(),
			cpMeasurementUnit.getGroupId(), key, primary, type);

		cpMeasurementUnit.setNameMap(nameMap);
		cpMeasurementUnit.setKey(key);
		cpMeasurementUnit.setRate(rate);
		cpMeasurementUnit.setPrimary(primary);
		cpMeasurementUnit.setPriority(priority);
		cpMeasurementUnit.setType(type);

		cpMeasurementUnitPersistence.update(cpMeasurementUnit);

		return cpMeasurementUnit;
	}

	protected void validate(
			long cpMeasurementUnitId, long groupId, String key, boolean primary,
			int type)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitPersistence.fetchByG_K_T(groupId, key, type);

		if ((cpMeasurementUnit != null) &&
			(cpMeasurementUnit.getCPMeasurementUnitId() !=
				cpMeasurementUnitId)) {

			throw new CPMeasurementUnitKeyException();
		}

		if (primary) {
			List<CPMeasurementUnit> cpMeasurementUnits =
				cpMeasurementUnitPersistence.findByG_P_T(
					groupId, primary, type);

			for (CPMeasurementUnit curCPMeasurementUnit : cpMeasurementUnits) {
				if (curCPMeasurementUnit.getCPMeasurementUnitId() !=
						cpMeasurementUnitId) {

					curCPMeasurementUnit.setPrimary(false);

					cpMeasurementUnitPersistence.update(curCPMeasurementUnit);
				}
			}
		}
	}

	private void _addCPMeasurementUnit(
			String name, String key, double rate, boolean primary,
			double priority, int type, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(serviceContext.getLocale(), name);

		addCPMeasurementUnit(
			nameMap, key, rate, primary, priority, type, serviceContext);
	}

}