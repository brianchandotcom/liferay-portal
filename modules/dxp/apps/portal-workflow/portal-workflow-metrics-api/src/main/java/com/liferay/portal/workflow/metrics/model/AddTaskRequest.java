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

package com.liferay.portal.workflow.metrics.model;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class AddTaskRequest {

	public Map<Locale, String> getAssetTitleMap() {
		return _assetTitleMap;
	}

	public Map<Locale, String> getAssetTypeMap() {
		return _assetTypeMap;
	}

	public List<Assignment> getAssignments() {
		return _assignments;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCompletionDate() {
		return _completionDate;
	}

	public Long getCompletionUserId() {
		return _completionUserId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Date getInstanceCompletionDate() {
		return _instanceCompletionDate;
	}

	public long getInstanceId() {
		return _instanceId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public long getProcessId() {
		return _processId;
	}

	public String getProcessVersion() {
		return _processVersion;
	}

	public long getTaskId() {
		return _taskId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isCompleted() {
		return _completed;
	}

	public boolean isInstanceCompleted() {
		return _instanceCompleted;
	}

	public static class Builder {

		public AddTaskRequest build() {
			return _addTaskRequest;
		}

		public Builder setAssetTitleMap(Map<Locale, String> assetTitleMap) {
			_addTaskRequest._assetTitleMap = assetTitleMap;

			return this;
		}

		public Builder setAssetTypeMap(Map<Locale, String> assetTitleMap) {
			_addTaskRequest._assetTypeMap = assetTitleMap;

			return this;
		}

		public Builder setAssignments(List<Assignment> assignments) {
			_addTaskRequest._assignments = assignments;

			return this;
		}

		public Builder setAssignments(
			UnsafeSupplier<List<Assignment>, Exception>
				assignmentsUnsafeSupplier) {

			try {
				_addTaskRequest._assignments = assignmentsUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			return this;
		}

		public Builder setClassName(String className) {
			_addTaskRequest._className = className;

			return this;
		}

		public Builder setClassPK(long classPK) {
			_addTaskRequest._classPK = classPK;

			return this;
		}

		public Builder setCompanyId(long companyId) {
			_addTaskRequest._companyId = companyId;

			return this;
		}

		public Builder setCompleted(boolean completed) {
			_addTaskRequest._completed = completed;

			return this;
		}

		public Builder setCompletionDate(Date completionDate) {
			_addTaskRequest._completionDate = completionDate;

			return this;
		}

		public Builder setCompletionUserId(Long completionUserId) {
			_addTaskRequest._completionUserId = completionUserId;

			return this;
		}

		public Builder setCompletionUserId(
			UnsafeSupplier<Long, Exception> completionUserIdUnsafeSupplier) {

			try {
				_addTaskRequest._completionUserId =
					completionUserIdUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			return this;
		}

		public Builder setCreateDate(Date createDate) {
			_addTaskRequest._createDate = createDate;

			return this;
		}

		public Builder setInstanceCompleted(boolean instanceCompleted) {
			_addTaskRequest._instanceCompleted = instanceCompleted;

			return this;
		}

		public Builder setInstanceCompletionDate(Date instanceCompletionDate) {
			_addTaskRequest._instanceCompletionDate = instanceCompletionDate;

			return this;
		}

		public Builder setInstanceId(long instanceId) {
			_addTaskRequest._instanceId = instanceId;

			return this;
		}

		public Builder setModifiedDate(Date modifiedDate) {
			_addTaskRequest._modifiedDate = modifiedDate;

			return this;
		}

		public Builder setName(String name) {
			_addTaskRequest._name = name;

			return this;
		}

		public Builder setNodeId(long nodeId) {
			_addTaskRequest._nodeId = nodeId;

			return this;
		}

		public Builder setProcessId(long processId) {
			_addTaskRequest._processId = processId;

			return this;
		}

		public Builder setProcessVersion(String processVersion) {
			_addTaskRequest._processVersion = processVersion;

			return this;
		}

		public Builder setTaskId(long taskId) {
			_addTaskRequest._taskId = taskId;

			return this;
		}

		public Builder setUserId(long userId) {
			_addTaskRequest._userId = userId;

			return this;
		}

		private final AddTaskRequest _addTaskRequest = new AddTaskRequest();

	}

	private static final Log _log = LogFactoryUtil.getLog(AddTaskRequest.class);

	private Map<Locale, String> _assetTitleMap;
	private Map<Locale, String> _assetTypeMap;
	private List<Assignment> _assignments;
	private String _className;
	private long _classPK;
	private long _companyId;
	private boolean _completed;
	private Date _completionDate;
	private Long _completionUserId;
	private Date _createDate;
	private boolean _instanceCompleted;
	private Date _instanceCompletionDate;
	private long _instanceId;
	private Date _modifiedDate;
	private String _name;
	private long _nodeId;
	private long _processId;
	private String _processVersion;
	private long _taskId;
	private long _userId;

}