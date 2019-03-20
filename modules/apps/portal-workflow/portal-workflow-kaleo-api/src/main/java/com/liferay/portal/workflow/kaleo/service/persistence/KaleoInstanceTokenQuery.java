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
 * @author István András Dézsi
 */
public class KaleoInstanceTokenQuery implements Serializable {

	public KaleoInstanceTokenQuery(ServiceContext serviceContext) {
		_companyId = serviceContext.getCompanyId();
	}

	public String getAssetDescription() {
		return _assetDescription;
	}

	public String getAssetTitle() {
		return _assetTitle;
	}

	public String getClassName() {
		return _className;
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

	public Long getKaleoInstanceId() {
		return _kaleoInstanceId;
	}

	public Long getKaleoInstanceTokenId() {
		return _kaleoInstanceTokenId;
	}

	public Long getParentKaleoInstanceTokenId() {
		return _parentKaleoInstanceTokenId;
	}

	public Long getUserId() {
		return _userId;
	}

	public Boolean isCompleted() {
		return _completed;
	}

	public void setAssetDescription(String assetDescription) {
		_assetDescription = assetDescription;
	}

	public void setAssetTitle(String assetTitle) {
		_assetTitle = assetTitle;
	}

	public void setClassName(String className) {
		_className = className;
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

	public void setKaleoInstanceId(Long kaleoInstanceId) {
		_kaleoInstanceId = kaleoInstanceId;
	}

	public void setKaleoInstanceTokenId(Long kaleoInstanceTokenId) {
		_kaleoInstanceTokenId = kaleoInstanceTokenId;
	}

	public void setParentKaleoInstanceTokenId(Long parentKaleoInstanceTokenId) {
		_parentKaleoInstanceTokenId = parentKaleoInstanceTokenId;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	private String _assetDescription;
	private String _assetTitle;
	private String _className;
	private final long _companyId;
	private Boolean _completed;
	private Date _completionDateGT;
	private Date _completionDateLT;
	private String _currentKaleoNodeName;
	private String _kaleoDefinitionName;
	private Long _kaleoInstanceId;
	private Long _kaleoInstanceTokenId;
	private Long _parentKaleoInstanceTokenId;
	private Long _userId;

}