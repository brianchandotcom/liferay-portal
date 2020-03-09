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

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Inácio Nery
 */
public class KaleoInstanceQuery implements Serializable {

	public KaleoInstanceQuery(ServiceContext serviceContext) {
		_companyId = serviceContext.getCompanyId();
	}

	public String[] getClassNames() {
		return _classNames;
	}

	public Long getClassPK() {
		return _classPK;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCompletionDateGT() {
		return _completionDateGT;
	}

	public Date getCompletionDateLT() {
		return _completionDateLT;
	}

	public String getCurrentKaleoNodeName() {
		return _currentKaleoNodeName;
	}

	public String getKaleoDefinitionName() {
		return _kaleoDefinitionName;
	}

	public Integer getKaleoDefinitionVersion() {
		return _kaleoDefinitionVersion;
	}

	public Long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	public Long getKaleoInstanceId() {
		return _kaleoInstanceId;
	}

	public Long getRootKaleoInstanceTokenId() {
		return _rootKaleoInstanceTokenId;
	}

	public Long getUserId() {
		return _userId;
	}

	public Boolean isCompleted() {
		return _completed;
	}

	public void setClassNames(String[] classNames) {
		_classNames = classNames;
	}

	public void setClassPK(Long classPK) {
		_classPK = classPK;
	}

	public void setCompleted(Boolean completed) {
		_completed = completed;
	}

	public void setCompletionDateGT(Date completionDateGT) {
		_completionDateGT = completionDateGT;
	}

	public void setCompletionDateLT(Date completionDateLT) {
		_completionDateLT = completionDateLT;
	}

	public void setCurrentKaleoNodeName(String currentKaleoNodeName) {
		_currentKaleoNodeName = currentKaleoNodeName;
	}

	public void setKaleoDefinitionName(String kaleoDefinitionName) {
		_kaleoDefinitionName = kaleoDefinitionName;
	}

	public void setKaleoDefinitionVersion(Integer kaleoDefinitionVersion) {
		_kaleoDefinitionVersion = kaleoDefinitionVersion;
	}

	public void setKaleoDefinitionVersionId(Long kaleoDefinitionVersionId) {
		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
	}

	public void setKaleoInstanceId(Long kaleoInstanceId) {
		_kaleoInstanceId = kaleoInstanceId;
	}

	public void setRootKaleoInstanceTokenId(Long rootKaleoInstanceTokenId) {
		_rootKaleoInstanceTokenId = rootKaleoInstanceTokenId;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	private String[] _classNames;
	private Long _classPK;
	private final long _companyId;
	private Boolean _completed;
	private Date _completionDateGT;
	private Date _completionDateLT;
	private String _currentKaleoNodeName;
	private String _kaleoDefinitionName;
	private Integer _kaleoDefinitionVersion;
	private Long _kaleoDefinitionVersionId;
	private Long _kaleoInstanceId;
	private Long _rootKaleoInstanceTokenId;
	private Long _userId;

}