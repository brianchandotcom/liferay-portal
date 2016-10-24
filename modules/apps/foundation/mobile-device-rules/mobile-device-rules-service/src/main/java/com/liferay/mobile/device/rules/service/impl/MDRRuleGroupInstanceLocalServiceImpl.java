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

package com.liferay.mobile.device.rules.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.exception.DuplicateRuleGroupInstanceException;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.base.MDRRuleGroupInstanceLocalServiceBaseImpl;
import com.liferay.mobile.device.rules.util.comparator.RuleGroupInstancePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Edward C. Han
 */
@ProviderType
public class MDRRuleGroupInstanceLocalServiceImpl
	extends MDRRuleGroupInstanceLocalServiceBaseImpl {

	@Override
	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		// Rule group instance

		User user = userLocalService.getUser(serviceContext.getUserId());
		long classNameId = classNameLocalService.getClassNameId(className);

		validate(classNameId, classPK, ruleGroupId);

		long ruleGroupInstanceId = counterLocalService.increment();

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstanceLocalService.createMDRRuleGroupInstance(
				ruleGroupInstanceId);

		ruleGroupInstance.setUuid(serviceContext.getUuid());
		ruleGroupInstance.setGroupId(groupId);
		ruleGroupInstance.setCompanyId(serviceContext.getCompanyId());
		ruleGroupInstance.setUserId(serviceContext.getUserId());
		ruleGroupInstance.setUserName(user.getFullName());
		ruleGroupInstance.setClassNameId(classNameId);
		ruleGroupInstance.setClassPK(classPK);
		ruleGroupInstance.setRuleGroupId(ruleGroupId);
		ruleGroupInstance.setPriority(priority);

		// Resources

		resourceLocalService.addModelResources(
			ruleGroupInstance, serviceContext);

		return updateMDRRuleGroupInstance(ruleGroupInstance);
	}

	@Override
	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			ServiceContext serviceContext)
		throws PortalException {

		List<MDRRuleGroupInstance> ruleGroupInstances = getRuleGroupInstances(
			className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new RuleGroupInstancePriorityComparator());

		int priority = 0;

		if (!ruleGroupInstances.isEmpty()) {
			MDRRuleGroupInstance highestPriorityRuleGroupInstance =
				ruleGroupInstances.get(ruleGroupInstances.size() - 1);

			priority = highestPriorityRuleGroupInstance.getPriority() + 1;
		}

		return addRuleGroupInstance(
			groupId, className, classPK, ruleGroupId, priority, serviceContext);
	}

	@Override
	public void deleteGroupRuleGroupInstances(long groupId) {
		List<MDRRuleGroupInstance> ruleGroupInstances =
			mdrRuleGroupInstancePersistence.findByGroupId(groupId);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
				ruleGroupInstance);
		}
	}

	@Override
	public void deleteRuleGroupInstance(long ruleGroupInstanceId) {
		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.fetchByPrimaryKey(
				ruleGroupInstanceId);

		mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
			ruleGroupInstance);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteRuleGroupInstance(
		MDRRuleGroupInstance ruleGroupInstance) {

		// Rule group instance

		mdrRuleGroupInstancePersistence.remove(ruleGroupInstance);

		// Rule actions

		mdrActionLocalService.deleteActions(
			ruleGroupInstance.getRuleGroupInstanceId());
	}

	@Override
	public void deleteRuleGroupInstances(long ruleGroupId) {
		List<MDRRuleGroupInstance> ruleGroupInstances =
			mdrRuleGroupInstancePersistence.findByRuleGroupId(ruleGroupId);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
				ruleGroupInstance);
		}
	}

	@Override
	public MDRRuleGroupInstance fetchRuleGroupInstance(
		long ruleGroupInstanceId) {

		return mdrRuleGroupInstancePersistence.fetchByPrimaryKey(
			ruleGroupInstanceId);
	}

	@Override
	public MDRRuleGroupInstance fetchRuleGroupInstance(
		String className, long classPK, long ruleGroupId) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.fetchByC_C_R(
			classNameId, classPK, ruleGroupId);
	}

	@Override
	public MDRRuleGroupInstance getRuleGroupInstance(long ruleGroupInstanceId)
		throws PortalException {

		return mdrRuleGroupInstancePersistence.findByPrimaryKey(
			ruleGroupInstanceId);
	}

	@Override
	public MDRRuleGroupInstance getRuleGroupInstance(
			String className, long classPK, long ruleGroupId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C_R(
			classNameId, classPK, ruleGroupId);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(long ruleGroupId) {
		return mdrRuleGroupInstancePersistence.findByRuleGroupId(ruleGroupId);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
		long ruleGroupId, int start, int end) {

		return mdrRuleGroupInstancePersistence.findByRuleGroupId(
			ruleGroupId, start, end);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
		String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
		String className, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getRuleGroupInstancesCount(long ruleGroupId) {
		return mdrRuleGroupInstancePersistence.countByRuleGroupId(ruleGroupId);
	}

	@Override
	public int getRuleGroupInstancesCount(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.countByC_C(classNameId, classPK);
	}

	@Override
	public MDRRuleGroupInstance updateRuleGroupInstance(
			long ruleGroupInstanceId, int priority)
		throws PortalException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				ruleGroupInstanceId);

		ruleGroupInstance.setPriority(priority);

		mdrRuleGroupInstancePersistence.update(ruleGroupInstance);

		return ruleGroupInstance;
	}

	protected void validate(long classNameId, long classPK, long ruleGroupId)
		throws PortalException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.fetchByC_C_R(
				classNameId, classPK, ruleGroupId);

		if (ruleGroupInstance != null) {
			StringBundler sb = new StringBundler(7);

			sb.append("{classNameId=");
			sb.append(classNameId);
			sb.append(", classPK=");
			sb.append(classPK);
			sb.append(", ruleGroupId=");
			sb.append(ruleGroupId);
			sb.append("}");

			throw new DuplicateRuleGroupInstanceException(sb.toString());
		}

		mdrRuleGroupLocalService.getMDRRuleGroup(ruleGroupId);
	}

}