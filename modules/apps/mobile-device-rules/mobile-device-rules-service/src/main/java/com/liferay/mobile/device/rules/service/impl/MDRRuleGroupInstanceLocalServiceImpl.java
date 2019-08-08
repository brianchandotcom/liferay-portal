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

import com.liferay.mobile.device.rules.exception.DuplicateRuleGroupInstanceException;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.mobile.device.rules.service.base.MDRRuleGroupInstanceLocalServiceBaseImpl;
import com.liferay.mobile.device.rules.service.persistence.MDRRuleGroupPersistence;
import com.liferay.mobile.device.rules.util.comparator.RuleGroupInstancePriorityComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward C. Han
 */
@Component(
	property = "model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroupInstance",
	service = AopService.class
)
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
			RuleGroupInstancePriorityComparator.INSTANCE_ASCENDING);

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

		_mdrActionLocalService.deleteActions(
			ruleGroupInstance.getRuleGroupInstanceId());

		// Rule group instance priorities

		List<MDRRuleGroupInstance> mdrRuleGroupInstances =
			getRuleGroupInstances(
				ruleGroupInstance.getClassName(),
				ruleGroupInstance.getClassPK(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				RuleGroupInstancePriorityComparator.INSTANCE_ASCENDING);

		for (int i = 0; i < mdrRuleGroupInstances.size(); i++) {
			MDRRuleGroupInstance mdrRuleGroupInstance =
				mdrRuleGroupInstances.get(i);

			if (mdrRuleGroupInstance.getPriority() != i) {
				mdrRuleGroupInstance.setPriority(i);

				mdrRuleGroupInstancePersistence.update(mdrRuleGroupInstance);
			}
		}
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

		return mdrRuleGroupInstancePersistence.fetchByC_C_R(
			classNameLocalService.getClassNameId(className), classPK,
			ruleGroupId);
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

		return mdrRuleGroupInstancePersistence.findByC_C_R(
			classNameLocalService.getClassNameId(className), classPK,
			ruleGroupId);
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

		return mdrRuleGroupInstancePersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
		String className, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {

		return mdrRuleGroupInstancePersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK, start,
			end, orderByComparator);
	}

	@Override
	public int getRuleGroupInstancesCount(long ruleGroupId) {
		return mdrRuleGroupInstancePersistence.countByRuleGroupId(ruleGroupId);
	}

	@Override
	public int getRuleGroupInstancesCount(String className, long classPK) {
		return mdrRuleGroupInstancePersistence.countByC_C(
			classNameLocalService.getClassNameId(className), classPK);
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

		_mdrRuleGroupPersistence.findByPrimaryKey(ruleGroupId);
	}

	@Reference
	private MDRActionLocalService _mdrActionLocalService;

	@Reference
	private MDRRuleGroupPersistence _mdrRuleGroupPersistence;

}