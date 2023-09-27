/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getAssetDescription() {
		return _assetDescription;
	}

	public String getAssetTitle() {
		return _assetTitle;
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

	public Boolean isActive() {
		return _active;
	}

	public Boolean isCompleted() {
		return _completed;
	}

	public boolean isSearchByActiveWorkflowHandlers() {
		return _searchByActiveWorkflowHandlers;
	}

	public void setActive(Boolean active) {
		_active = active;
	}

	public void setAssetDescription(String assetDescription) {
		_assetDescription = assetDescription;
	}

	public void setAssetTitle(String assetTitle) {
		_assetTitle = assetTitle;
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

	public void setSearchByActiveWorkflowHandlers(
		boolean searchByActiveWorkflowHandlers) {

		_searchByActiveWorkflowHandlers = searchByActiveWorkflowHandlers;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	private Boolean _active;
	private String _assetDescription;
	private String _assetTitle;
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
	private boolean _searchByActiveWorkflowHandlers;
	private Long _userId;

}