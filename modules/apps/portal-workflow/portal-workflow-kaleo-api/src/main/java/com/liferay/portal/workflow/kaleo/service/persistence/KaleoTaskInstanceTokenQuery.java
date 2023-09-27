/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
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

	public boolean isSearchByActiveWorkflowHandlers() {
		return _searchByActiveWorkflowHandlers;
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

	public void setSearchByActiveWorkflowHandlers(
		boolean searchByActiveWorkflowHandlers) {

		_searchByActiveWorkflowHandlers = searchByActiveWorkflowHandlers;
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
	private boolean _searchByActiveWorkflowHandlers;
	private Boolean _searchByUserRoles;
	private ServiceContext _serviceContext;
	private int _start = QueryUtil.ALL_POS;
	private String[] _taskNames;
	private long _userId;

}