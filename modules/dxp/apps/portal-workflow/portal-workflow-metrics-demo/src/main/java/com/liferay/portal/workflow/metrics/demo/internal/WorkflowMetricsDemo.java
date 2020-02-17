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

package com.liferay.portal.workflow.metrics.demo.internal;

import com.liferay.dynamic.data.mapping.demo.data.creator.DDMFormInstanceDemoDataCreator;
import com.liferay.dynamic.data.mapping.demo.data.creator.DDMFormInstanceRecordDemoDataCreator;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowDefinitionDemoDataCreator;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowDefinitionLinkDemoDataCreator;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowInstanceDemoDataCreator;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowTaskDemoDataCreator;
import com.liferay.portal.workflow.metrics.demo.data.creator.WorkflowMetricsSLADefinitionDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteMemberUserDemoDataCreator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class WorkflowMetricsDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User omniAdminUser = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId());

		LocalDateTime nowLocalDateTime = LocalDateTime.now();

		LocalDateTime startLocalDateTime = nowLocalDateTime.minusDays(45);

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionDemoDataCreator.create(
				company.getCompanyId(), omniAdminUser.getUserId(),
				_toDate(startLocalDateTime));

		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		List<Long> userIds = _createUsers(group.getGroupId());

		List<Long> insuranceAgentUserIds = _createUsers(
			group.getGroupId(),
			_roleLocalService.getRole(
				company.getCompanyId(), "Insurance agent"));

		List<Long> underwriterUserIds = _createUsers(
			group.getGroupId(),
			_roleLocalService.getRole(company.getCompanyId(), "Underwriter"));

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceDemoDataCreator.create(
				omniAdminUser.getUserId(), group.getGroupId(),
				_toDate(startLocalDateTime));

		_workflowDefinitionLinkDemoDataCreator.create(
			omniAdminUser.getUserId(), company.getCompanyId(),
			group.getGroupId(), DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId(), 0);

		_workflowMetricsSLADefinitionDemoDataCreator.create(
			company.getCompanyId(), omniAdminUser.getUserId(),
			_toDate(startLocalDateTime),
			workflowDefinition.getWorkflowDefinitionId());

		for (int i = 1; i <= 200; i++) {
			LocalDateTime createLocalDateTime = startLocalDateTime.plusDays(
				RandomUtil.nextInt(40));

			long creatorUserId = _getRandomElement(userIds);

			WorkflowInstance workflowInstance = _addWorkflowInstance(
				company.getCompanyId(), _toDate(createLocalDateTime),
				ddmFormInstance.getFormInstanceId(), group.getGroupId(),
				creatorUserId);

			_updateCreateDateWorkflowTask(
				company.getCompanyId(), _toDate(createLocalDateTime),
				workflowInstance.getWorkflowInstanceId());

			if (RandomUtil.nextInt(10) > 8) {
				continue;
			}

			LocalDateTime completionLocalDateTime =
				createLocalDateTime.plusHours(RandomUtil.nextInt(i));

			if (completionLocalDateTime.isAfter(nowLocalDateTime)) {
				completionLocalDateTime = nowLocalDateTime;
			}

			String transitionName = _completeWorkflowTask(
				company.getCompanyId(), _toDate(completionLocalDateTime),
				_getRandomElement(insuranceAgentUserIds),
				workflowInstance.getWorkflowInstanceId());

			if (Objects.equals(transitionName, "Payment")) {
				createLocalDateTime = completionLocalDateTime;

				_updateCreateDateWorkflowTask(
					company.getCompanyId(), _toDate(createLocalDateTime),
					workflowInstance.getWorkflowInstanceId());

				if (RandomUtil.nextInt(10) > 8) {
					continue;
				}

				completionLocalDateTime = createLocalDateTime.plusHours(
					RandomUtil.nextInt(i));

				if (completionLocalDateTime.isAfter(nowLocalDateTime)) {
					completionLocalDateTime = nowLocalDateTime;
				}

				transitionName = _completeWorkflowTask(
					company.getCompanyId(), _toDate(completionLocalDateTime),
					creatorUserId, workflowInstance.getWorkflowInstanceId());

				createLocalDateTime = completionLocalDateTime;

				_updateCreateDateWorkflowTask(
					company.getCompanyId(), _toDate(createLocalDateTime),
					workflowInstance.getWorkflowInstanceId());

				if (RandomUtil.nextInt(10) > 8) {
					continue;
				}

				completionLocalDateTime = createLocalDateTime.plusHours(
					RandomUtil.nextInt(i));

				if (completionLocalDateTime.isAfter(nowLocalDateTime)) {
					completionLocalDateTime = nowLocalDateTime;
				}

				_completeWorkflowTask(
					company.getCompanyId(), _toDate(completionLocalDateTime),
					_getRandomElement(underwriterUserIds),
					workflowInstance.getWorkflowInstanceId());
			}
			else {
				createLocalDateTime = completionLocalDateTime;

				_updateCreateDateWorkflowTask(
					company.getCompanyId(), _toDate(createLocalDateTime),
					workflowInstance.getWorkflowInstanceId());

				if (RandomUtil.nextInt(10) > 8) {
					continue;
				}

				completionLocalDateTime = createLocalDateTime.plusHours(
					RandomUtil.nextInt(i));

				if (completionLocalDateTime.isAfter(nowLocalDateTime)) {
					completionLocalDateTime = nowLocalDateTime;
				}

				transitionName = _completeWorkflowTask(
					company.getCompanyId(), _toDate(completionLocalDateTime),
					_getRandomElement(underwriterUserIds),
					workflowInstance.getWorkflowInstanceId());

				if (Objects.equals(transitionName, "Approve")) {
					createLocalDateTime = completionLocalDateTime;

					_updateCreateDateWorkflowTask(
						company.getCompanyId(), _toDate(createLocalDateTime),
						workflowInstance.getWorkflowInstanceId());

					if (RandomUtil.nextInt(10) > 8) {
						continue;
					}

					completionLocalDateTime = createLocalDateTime.plusHours(
						RandomUtil.nextInt(i));

					if (completionLocalDateTime.isAfter(nowLocalDateTime)) {
						completionLocalDateTime = nowLocalDateTime;
					}

					_completeWorkflowTask(
						company.getCompanyId(),
						_toDate(completionLocalDateTime), creatorUserId,
						workflowInstance.getWorkflowInstanceId());
				}
			}

			_workflowInstanceDemoDataCreator.updateCompletionDate(
				workflowInstance.getWorkflowInstanceId(),
				_toDate(completionLocalDateTime));
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_ddmFormInstanceRecordDemoDataCreator.delete();

		_ddmFormInstanceDemoDataCreator.delete();
		_workflowMetricsSLADefinitionDemoDataCreator.delete();

		_workflowDefinitionLinkDemoDataCreator.delete();

		_workflowDefinitionDemoDataCreator.delete();

		_omniAdminUserDemoDataCreator.delete();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private WorkflowInstance _addWorkflowInstance(
			long companyId, Date createDate, long ddmFormInstanceId,
			long groupId, long userId)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordDemoDataCreator.create(
				userId, groupId, createDate, ddmFormInstanceId);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getFormInstanceRecordVersion();

		WorkflowInstance workflowInstance =
			_workflowInstanceDemoDataCreator.getWorkflowInstance(
				companyId, DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId());

		_workflowTaskDemoDataCreator.getWorkflowTask(
			companyId, workflowInstance.getWorkflowInstanceId());

		_workflowInstanceDemoDataCreator.updateCreateDate(
			workflowInstance.getWorkflowInstanceId(), createDate);

		return workflowInstance;
	}

	private String _completeWorkflowTask(
			long companyId, Date completionDate, long userId,
			long workflowInstanceId)
		throws Exception {

		WorkflowTask workflowTask =
			_workflowTaskDemoDataCreator.getWorkflowTask(
				companyId, workflowInstanceId);

		String transitionName = _getRandomElement(
			_workflowTaskDemoDataCreator.getNextTransitionNames(
				companyId, workflowTask.getWorkflowTaskId()));

		_workflowTaskDemoDataCreator.completeWorkflowTask(
			companyId, userId, workflowTask.getWorkflowTaskId(),
			transitionName);

		_workflowTaskDemoDataCreator.updateCompletionDate(
			workflowTask.getWorkflowTaskId(), completionDate);

		return transitionName;
	}

	private List<Long> _createUsers(long groupId) throws PortalException {
		return _createUsers(groupId, null);
	}

	private List<Long> _createUsers(long groupId, Role role)
		throws PortalException {

		List<Long> userIds = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			if (role == null) {
				User user = _siteMemberUserDemoDataCreator.create(groupId);

				userIds.add(user.getUserId());
			}
			else {
				User user = _siteMemberUserDemoDataCreator.create(
					groupId, null, new long[] {role.getRoleId()});

				userIds.add(user.getUserId());
			}
		}

		return userIds;
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(
			ZoneId.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	private void _updateCreateDateWorkflowTask(
			long companyId, Date createDate, long workflowInstanceId)
		throws Exception {

		WorkflowTask workflowTask =
			_workflowTaskDemoDataCreator.getWorkflowTask(
				companyId, workflowInstanceId);

		_workflowTaskDemoDataCreator.updateCreateDate(
			workflowTask.getWorkflowTaskId(), createDate);
	}

	@Reference
	private DDMFormInstanceDemoDataCreator _ddmFormInstanceDemoDataCreator;

	@Reference
	private DDMFormInstanceRecordDemoDataCreator
		_ddmFormInstanceRecordDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SiteMemberUserDemoDataCreator _siteMemberUserDemoDataCreator;

	@Reference
	private WorkflowDefinitionDemoDataCreator
		_workflowDefinitionDemoDataCreator;

	@Reference
	private WorkflowDefinitionLinkDemoDataCreator
		_workflowDefinitionLinkDemoDataCreator;

	@Reference
	private WorkflowInstanceDemoDataCreator _workflowInstanceDemoDataCreator;

	@Reference
	private WorkflowMetricsSLADefinitionDemoDataCreator
		_workflowMetricsSLADefinitionDemoDataCreator;

	@Reference
	private WorkflowTaskDemoDataCreator _workflowTaskDemoDataCreator;

}