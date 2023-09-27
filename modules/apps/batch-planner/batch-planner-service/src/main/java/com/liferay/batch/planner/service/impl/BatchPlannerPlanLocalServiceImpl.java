/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.batch.engine.task.TaskItemUtil;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.exception.BatchPlannerPlanExternalTypeException;
import com.liferay.batch.planner.exception.BatchPlannerPlanInternalClassNameException;
import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.exception.RequiredBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerPlanLocalServiceBaseImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerPolicyPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerPlan",
	service = AopService.class
)
public class BatchPlannerPlanLocalServiceImpl
	extends BatchPlannerPlanLocalServiceBaseImpl {

	@Override
	public BatchPlannerPlan addBatchPlannerPlan(
			long userId, boolean export, String externalType,
			String externalURL, String internalClassName, String name, int size,
			String taskItemDelegateName, boolean template)
		throws PortalException {

		_validateExternalType(externalType);
		_validateInternalClassName(internalClassName);

		if (Validator.isNull(name) && !template) {
			name = _generateName(internalClassName, taskItemDelegateName);
		}

		User user = _userLocalService.getUser(userId);

		_validateName(0, user.getCompanyId(), name, template);

		BatchPlannerPlan batchPlannerPlan = batchPlannerPlanPersistence.create(
			counterLocalService.increment());

		batchPlannerPlan.setCompanyId(user.getCompanyId());
		batchPlannerPlan.setUserId(userId);
		batchPlannerPlan.setUserName(user.getFullName());
		batchPlannerPlan.setActive(true);
		batchPlannerPlan.setExport(export);
		batchPlannerPlan.setExternalType(externalType);
		batchPlannerPlan.setExternalURL(externalURL);
		batchPlannerPlan.setInternalClassName(internalClassName);
		batchPlannerPlan.setName(name);
		batchPlannerPlan.setSize(size);
		batchPlannerPlan.setTaskItemDelegateName(taskItemDelegateName);
		batchPlannerPlan.setTemplate(template);

		batchPlannerPlan = batchPlannerPlanPersistence.update(batchPlannerPlan);

		_resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), BatchPlannerPlan.class.getName(),
			batchPlannerPlan.getBatchPlannerPlanId(), false, true, false);

		return batchPlannerPlan;
	}

	@Override
	public void deactivateBatchPlannerPlan(String batchEngineTaskERC) {
		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.fetchByPrimaryKey(
				GetterUtil.getLong(batchEngineTaskERC));

		if (batchPlannerPlan == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to update batch planner plan for batch engine ",
						"task ERC ", batchEngineTaskERC));
			}

			return;
		}

		batchPlannerPlan.setActive(false);

		batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	@Override
	public BatchPlannerPlan deleteBatchPlannerPlan(
			BatchPlannerPlan batchPlannerPlan)
		throws PortalException {

		return deleteBatchPlannerPlan(batchPlannerPlan.getBatchPlannerPlanId());
	}

	@Override
	public BatchPlannerPlan deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan = batchPlannerPlanPersistence.remove(
			batchPlannerPlanId);

		_resourceLocalService.deleteResource(
			batchPlannerPlan, ResourceConstants.SCOPE_INDIVIDUAL);

		_batchPlannerMappingPersistence.removeByBatchPlannerPlanId(
			batchPlannerPlanId);

		_batchPlannerPolicyPersistence.removeByBatchPlannerPlanId(
			batchPlannerPlanId);

		return batchPlannerPlan;
	}

	@Override
	public BatchPlannerPlan updateBatchPlannerPlan(
			long batchPlannerPlanId, String externalType,
			String internalClassName, String name)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		if (!batchPlannerPlan.isTemplate()) {
			throw new RequiredBatchPlannerPlanException(
				"Batch planner plan is not a template");
		}

		_validateName(
			batchPlannerPlanId, batchPlannerPlan.getCompanyId(), name, true);

		batchPlannerPlan.setExternalType(externalType);
		batchPlannerPlan.setInternalClassName(internalClassName);
		batchPlannerPlan.setName(name);

		return batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	@Override
	public BatchPlannerPlan updateStatus(long batchPlannerPlanId, int status)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		batchPlannerPlan.setStatus(status);

		return batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	private String _generateName(
		String internalClassName, String taskItemDelegateName) {

		String simpleClassName = TaskItemUtil.getSimpleClassName(
			TaskItemUtil.getInternalClassNameKey(
				internalClassName, taskItemDelegateName));

		return simpleClassName + " Plan Execution " +
			System.currentTimeMillis();
	}

	private void _validateExternalType(String externalType)
		throws PortalException {

		if (ArrayUtil.contains(
				BatchPlannerPlanConstants.EXTERNAL_TYPES, externalType)) {

			return;
		}

		String merge = StringUtil.merge(
			BatchPlannerPlanConstants.EXTERNAL_TYPES, StringPool.COMMA);

		throw new BatchPlannerPlanExternalTypeException(
			"Batch planner plan external type must be one of following: " +
				merge);
	}

	private void _validateInternalClassName(String internalClassName)
		throws PortalException {

		if (Validator.isNull(internalClassName)) {
			throw new BatchPlannerPlanInternalClassNameException();
		}
	}

	private void _validateName(
			long batchPlannerPlanId, long companyId, String name,
			boolean template)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new BatchPlannerPlanNameException(
				"Batch planner plan name is null");
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerPlan.class.getName(), "name");

		if (name.length() > maxLength) {
			throw new BatchPlannerPlanNameException(
				"Batch planner plan name must not be longer than " + maxLength);
		}

		if (!template) {
			return;
		}

		List<BatchPlannerPlan> batchPlannerPlans =
			batchPlannerPlanPersistence.findByC_T(companyId, template);

		for (BatchPlannerPlan batchPlannerPlan : batchPlannerPlans) {
			if ((batchPlannerPlan.getBatchPlannerPlanId() !=
					batchPlannerPlanId) &&
				Objects.equals(name, batchPlannerPlan.getName())) {

				throw new DuplicateBatchPlannerPlanException(
					StringBundler.concat(
						"Batch planner plan name \"", name,
						"\" already exists for company ", companyId));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchPlannerPlanLocalServiceImpl.class);

	@Reference
	private BatchPlannerMappingPersistence _batchPlannerMappingPersistence;

	@Reference
	private BatchPlannerPolicyPersistence _batchPlannerPolicyPersistence;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}