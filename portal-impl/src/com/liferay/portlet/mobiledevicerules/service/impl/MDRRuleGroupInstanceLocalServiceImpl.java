/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
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

	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			int priority, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

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

		return updateMDRRuleGroupInstance(ruleGroupInstance, false);
	}

	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.fetchByPrimaryKey(
				ruleGroupInstanceId);

		deleteRuleGroupInstance(ruleGroupInstance);
	}

	public void deleteRuleGroupInstance(MDRRuleGroupInstance ruleGroupInstance)
		throws SystemException {

		mdrActionLocalService.deleteActions(
			ruleGroupInstance.getRuleGroupInstanceId());

		mdrRuleGroupInstancePersistence.remove(ruleGroupInstance);
	}

	public void deleteRuleGroupInstances(long ruleGroupId)
		throws SystemException {

		List<MDRRuleGroupInstance> ruleGroupInstances =
			mdrRuleGroupInstancePersistence.findByRuleGroupId(ruleGroupId);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			deleteRuleGroupInstance(ruleGroupInstance);
		}
	}

	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C(classNameId, classPK);
	}

	public List<MDRRuleGroupInstance> getSortedRuleGroupInstances(
			String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		OrderByComparator orderByComparator =
			new RuleGroupInstancePriorityComparator();

		return mdrRuleGroupInstancePersistence.findByC_C(
			classNameId, classPK, 0, Integer.MAX_VALUE, orderByComparator);
	}

	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			String className, long classPK, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	public List<MDRRuleGroupInstance> getRuleGroupInstances(long ruleGroupId)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.findByRuleGroupId(ruleGroupId);
	}

	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			long ruleGroupId, int start, int end)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.findByRuleGroupId(
			ruleGroupId, start, end);
	}

	public int getRuleGroupInstancesCount(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.countByC_C(classNameId, classPK);
	}

	public int getRuleGroupInstancesCount(long ruleGroupId)
		throws SystemException {

		return mdrRuleGroupInstancePersistence.countByRuleGroupId(ruleGroupId);
	}

	public MDRRuleGroupInstance updateRuleGroupInstance(
			long ruleGroupInstanceId, int priority)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				ruleGroupInstanceId);

		ruleGroupInstance.setPriority(priority);

		mdrRuleGroupInstancePersistence.update(ruleGroupInstance, false);

		return ruleGroupInstance;
	}

}
