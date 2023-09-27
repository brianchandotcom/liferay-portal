/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		public Builder assetTitleMap(Map<Locale, String> assetTitleMap) {
			_addTaskRequest._assetTitleMap = assetTitleMap;

			return this;
		}

		public Builder assetTypeMap(Map<Locale, String> assetTitleMap) {
			_addTaskRequest._assetTypeMap = assetTitleMap;

			return this;
		}

		public Builder assignments(List<Assignment> assignments) {
			_addTaskRequest._assignments = assignments;

			return this;
		}

		public Builder assignments(
			UnsafeSupplier<List<Assignment>, Exception>
				assignmentsUnsafeSupplier) {

			try {
				_addTaskRequest._assignments = assignmentsUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}

			return this;
		}

		public AddTaskRequest build() {
			return _addTaskRequest;
		}

		public Builder className(String className) {
			_addTaskRequest._className = className;

			return this;
		}

		public Builder classPK(long classPK) {
			_addTaskRequest._classPK = classPK;

			return this;
		}

		public Builder companyId(long companyId) {
			_addTaskRequest._companyId = companyId;

			return this;
		}

		public Builder completed(boolean completed) {
			_addTaskRequest._completed = completed;

			return this;
		}

		public Builder completionDate(Date completionDate) {
			_addTaskRequest._completionDate = completionDate;

			return this;
		}

		public Builder completionUserId(Long completionUserId) {
			_addTaskRequest._completionUserId = completionUserId;

			return this;
		}

		public Builder completionUserId(
			UnsafeSupplier<Long, Exception> completionUserIdUnsafeSupplier) {

			try {
				_addTaskRequest._completionUserId =
					completionUserIdUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}

			return this;
		}

		public Builder createDate(Date createDate) {
			_addTaskRequest._createDate = createDate;

			return this;
		}

		public Builder instanceCompleted(boolean instanceCompleted) {
			_addTaskRequest._instanceCompleted = instanceCompleted;

			return this;
		}

		public Builder instanceCompletionDate(Date instanceCompletionDate) {
			_addTaskRequest._instanceCompletionDate = instanceCompletionDate;

			return this;
		}

		public Builder instanceId(long instanceId) {
			_addTaskRequest._instanceId = instanceId;

			return this;
		}

		public Builder modifiedDate(Date modifiedDate) {
			_addTaskRequest._modifiedDate = modifiedDate;

			return this;
		}

		public Builder name(String name) {
			_addTaskRequest._name = name;

			return this;
		}

		public Builder nodeId(long nodeId) {
			_addTaskRequest._nodeId = nodeId;

			return this;
		}

		public Builder processId(long processId) {
			_addTaskRequest._processId = processId;

			return this;
		}

		public Builder processVersion(String processVersion) {
			_addTaskRequest._processVersion = processVersion;

			return this;
		}

		public Builder taskId(long taskId) {
			_addTaskRequest._taskId = taskId;

			return this;
		}

		public Builder userId(long userId) {
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