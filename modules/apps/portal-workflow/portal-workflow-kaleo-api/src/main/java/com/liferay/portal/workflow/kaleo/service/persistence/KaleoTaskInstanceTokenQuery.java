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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTaskInstanceTokenQuery implements Serializable {

	public KaleoTaskInstanceTokenQuery(ServiceContext serviceContext) {
		_serviceContext = serviceContext;

		_companyId = serviceContext.getCompanyId();
		_userId = serviceContext.getUserId();
	}

	public Long[] getAssetPrimaryKeys() {
		return _assetPrimaryKeys;
	}

	public String getAssetTitle() {
		return _assetTitle;
	}

	public String[] getAssetTypes() {
		return _assetTypes;
	}

	public String getAssigneeClassName() {
		return _assigneeClassName;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getAssigneeClassPKs()}
	 */
	@Deprecated
	public Long getAssigneeClassPK() {
		Long[] assigneeClassPKs = getAssigneeClassPKs();

		if (ArrayUtil.isEmpty(assigneeClassPKs)) {
			return null;
		}

		return assigneeClassPKs[0];
	}

	public Long[] getAssigneeClassPKs() {
		return _assigneeClassPKs;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getDueDateGT() {
		return _dueDateGT;
	}

	public Date getDueDateLT() {
		return _dueDateLT;
	}

	public int getEnd() {
		return _end;
	}

	public Long getKaleoDefinitionId() {
		return _kaleoDefinitionId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getKaleoInstanceIds()}
	 */
	@Deprecated
	public Long getKaleoInstanceId() {
		Long[] kaleoInstanceIds = getKaleoInstanceIds();

		if (ArrayUtil.isEmpty(kaleoInstanceIds)) {
			return null;
		}

		return kaleoInstanceIds[0];
	}

	public Long[] getKaleoInstanceIds() {
		return _kaleoInstanceIds;
	}

	public OrderByComparator<KaleoTaskInstanceToken> getOrderByComparator() {
		return _orderByComparator;
	}

	public List<Long> getRoleIds() {
		return _roleIds;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public int getStart() {
		return _start;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getTaskNames()}
	 */
	@Deprecated
	public String getTaskName() {
		String[] taskNames = getTaskNames();

		if (ArrayUtil.isEmpty(taskNames)) {
			return null;
		}

		return taskNames[0];
	}

	public String[] getTaskNames() {
		return _taskNames;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isAndOperator() {
		return _andOperator;
	}

	public Boolean isCompleted() {
		return _completed;
	}

	public Boolean isSearchByUserRoles() {
		return _searchByUserRoles;
	}

	public void setAndOperator(boolean andOperator) {
		_andOperator = andOperator;
	}

	public void setAssetPrimaryKeys(Long[] assetPrimaryKeys) {
		_assetPrimaryKeys = assetPrimaryKeys;
	}

	public void setAssetTitle(String assetTitle) {
		_assetTitle = assetTitle;
	}

	public void setAssetTypes(String[] assetTypes) {
		_assetTypes = assetTypes;
	}

	public void setAssigneeClassName(String assigneeClassName) {
		_assigneeClassName = assigneeClassName;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setAssigneeClassPKs(Long[])}
	 */
	@Deprecated
	public void setAssigneeClassPK(Long assigneeClassPK) {
		setAssigneeClassPKs(new Long[] {assigneeClassPK});
	}

	public void setAssigneeClassPKs(Long[] assigneeClassPKs) {
		_assigneeClassPKs = assigneeClassPKs;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCompleted(Boolean completed) {
		_completed = completed;
	}

	public void setDueDateGT(Date dueDateGT) {
		_dueDateGT = dueDateGT;
	}

	public void setDueDateLT(Date dueDateLT) {
		_dueDateLT = dueDateLT;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setKaleoDefinitionId(Long kaleoDefinitionId) {
		_kaleoDefinitionId = kaleoDefinitionId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setKaleoInstanceIds(Long[])}
	 */
	@Deprecated
	public void setKaleoInstanceId(Long kaleoInstanceId) {
		setKaleoInstanceIds(new Long[] {kaleoInstanceId});
	}

	public void setKaleoInstanceIds(Long[] kaleoInstanceIds) {
		_kaleoInstanceIds = kaleoInstanceIds;
	}

	public void setOrderByComparator(
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		_orderByComparator = orderByComparator;
	}

	public void setRoleIds(List<Long> roleIds) {
		_roleIds = roleIds;
	}

	public void setSearchByUserRoles(Boolean searchByUserRoles) {
		_searchByUserRoles = searchByUserRoles;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	public void setStart(int start) {
		_start = start;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setTaskNames(String[])}
	 */
	@Deprecated
	public void setTaskName(String taskName) {
		setTaskNames(new String[] {taskName});
	}

	public void setTaskNames(String[] taskNames) {
		_taskNames = taskNames;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private boolean _andOperator = true;
	private Long[] _assetPrimaryKeys;
	private String _assetTitle;
	private String[] _assetTypes;
	private String _assigneeClassName;
	private Long[] _assigneeClassPKs;
	private long _companyId;
	private Boolean _completed;
	private Date _dueDateGT;
	private Date _dueDateLT;
	private int _end = QueryUtil.ALL_POS;
	private Long _kaleoDefinitionId;
	private Long[] _kaleoInstanceIds;
	private OrderByComparator<KaleoTaskInstanceToken> _orderByComparator;
	private List<Long> _roleIds;
	private Boolean _searchByUserRoles;
	private ServiceContext _serviceContext;
	private int _start = QueryUtil.ALL_POS;
	private String[] _taskNames;
	private long _userId;

}