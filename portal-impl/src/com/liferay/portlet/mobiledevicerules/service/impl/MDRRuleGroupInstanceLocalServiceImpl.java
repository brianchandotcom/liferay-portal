/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.mobiledevicerules.DuplicateRuleGroupInstanceException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupInstanceLocalServiceBaseImpl;
import com.liferay.portlet.mobiledevicerules.util.RuleGroupInstancePriorityComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupInstanceLocalServiceImpl
	extends MDRRuleGroupInstanceLocalServiceBaseImpl {

	@Override
	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			int priority, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		validate(classNameId, classPK, ruleGroupId);

		long ruleGroupInstanceId = counterLocalService.increment();

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstanceLocalService.createMDRRuleGroupInstance(
				ruleGroupInstanceId);

		ruleGroupInstance.setUuid(serviceContext.getUuid());
		ruleGroupInstance.setGroupId(groupId);
		ruleGroupInstance.setCompanyId(serviceContext.getCompanyId());
		ruleGroupInstance.setCreateDate(serviceContext.getCreateDate(now));
		ruleGroupInstance.setModifiedDate(serviceContext.getModifiedDate(now));
		ruleGroupInstance.setUserId(serviceContext.getUserId());
		ruleGroupInstance.setUserName(user.getFullName());
		ruleGroupInstance.setClassNameId(classNameId);
		ruleGroupInstance.setClassPK(classPK);
		ruleGroupInstance.setRuleGroupId(ruleGroupId);
		ruleGroupInstance.setPriority(priority);

		return updateMDRRuleGroupInstance(ruleGroupInstance);
	}

	@Override
	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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
	public void deleteGroupRuleGroupInstances(long groupId)
		throws PortalException, SystemException {

		List<MDRRuleGroupInstance> ruleGroupInstances =
			mdrRuleGroupInstancePersistence.findByGroupId(groupId);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			deleteRuleGroupInstance(ruleGroupInstance);
		}
	}

	@Override
	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.fetchByPrimaryKey(
				ruleGroupInstanceId);

		deleteRuleGroupInstance(ruleGroupInstance);
	}

	@Override
	public void deleteRuleGroupInstance(MDRRuleGroupInstance ruleGroupInstance)
		throws PortalException, SystemException {

		// Rule group instance

		mdrRuleGroupInstancePersistence.remove(ruleGroupInstance);

		// System event

		systemEventLocalService.addSystemEvent(
			0, ruleGroupInstance.getGroupId(),
			MDRRuleGroupInstance.class.getName(),
			ruleGroupInstance.getRuleGroupInstanceId(),
			ruleGroupInstance.getUuid(), null, SystemEventConstants.TYPE_DELETE,
			null);

		// Rule actions

		mdrActionLocalService.deleteActions(
			ruleGroupInstance.getRuleGroupInstanceId());
	}

	@Override
	public void deleteRuleGroupInstances(long ruleGroupId)
		throws PortalException, SystemException {

		List<MDRRuleGroupInstance> ruleGroupInstances =
			mdrRuleGroupInstancePersistence.findByRuleGroupId(ruleGroupId);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			deleteRuleGroupInstance(ruleGroupInstance);
		}
	}

	@Override
	public MDRRuleGroupInstance fetchRuleGroupInstance(long ruleGroupInstanceId)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.fetchByPrimaryKey(
			ruleGroupInstanceId);
	}

	@Override
	public MDRRuleGroupInstance fetchRuleGroupInstance(
			String className, long classPK, long ruleGroupId)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.fetchByC_C_R(
			classNameId, classPK, ruleGroupId);
	}

	@Override
	public MDRRuleGroupInstance getRuleGroupInstance(long ruleGroupInstanceId)
		throws PortalException, SystemException {

		return mdrRuleGroupInstancePersistence.findByPrimaryKey(
			ruleGroupInstanceId);
	}

	@Override
	public MDRRuleGroupInstance getRuleGroupInstance(
			String className, long classPK, long ruleGroupId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C_R(
			classNameId, classPK, ruleGroupId);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(long ruleGroupId)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.findByRuleGroupId(ruleGroupId);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			long ruleGroupId, int start, int end)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.findByRuleGroupId(
			ruleGroupId, start, end);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			String className, long classPK, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getRuleGroupInstancesCount(long ruleGroupId)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.countByRuleGroupId(ruleGroupId);
	}

	@Override
	public int getRuleGroupInstancesCount(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.countByC_C(classNameId, classPK);
	}

	@Override
	public MDRRuleGroupInstance updateRuleGroupInstance(
			long ruleGroupInstanceId, int priority)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				ruleGroupInstanceId);

		ruleGroupInstance.setPriority(priority);

		mdrRuleGroupInstancePersistence.update(ruleGroupInstance);

		return ruleGroupInstance;
	}

	protected void validate(long classNameId, long classPK, long ruleGroupId)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.fetchByC_C_R(
				classNameId, classPK, ruleGroupId);

		if (ruleGroupInstance != null) {
			throw new DuplicateRuleGroupInstanceException();
		}

		mdrRuleGroupLocalService.getMDRRuleGroup(ruleGroupId);
	}

}