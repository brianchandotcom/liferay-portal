/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ExportImportReportEntry}.
 * </p>
 *
 * @author Carlos Correa
 * @see ExportImportReportEntry
 * @generated
 */
public class ExportImportReportEntryWrapper
	extends BaseModelWrapper<ExportImportReportEntry>
	implements ExportImportReportEntry, ModelWrapper<ExportImportReportEntry> {

	public ExportImportReportEntryWrapper(
		ExportImportReportEntry exportImportReportEntry) {

		super(exportImportReportEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"exportImportReportEntryId", getExportImportReportEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("entityClassNameId", getEntityClassNameId());
		attributes.put(
			"entityExternalReferenceCode", getEntityExternalReferenceCode());
		attributes.put("error", getError());
		attributes.put("errorStacktrace", getErrorStacktrace());
		attributes.put(
			"exportImportConfigurationId", getExportImportConfigurationId());
		attributes.put("resolved", isResolved());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long exportImportReportEntryId = (Long)attributes.get(
			"exportImportReportEntryId");

		if (exportImportReportEntryId != null) {
			setExportImportReportEntryId(exportImportReportEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long entityClassNameId = (Long)attributes.get("entityClassNameId");

		if (entityClassNameId != null) {
			setEntityClassNameId(entityClassNameId);
		}

		String entityExternalReferenceCode = (String)attributes.get(
			"entityExternalReferenceCode");

		if (entityExternalReferenceCode != null) {
			setEntityExternalReferenceCode(entityExternalReferenceCode);
		}

		String error = (String)attributes.get("error");

		if (error != null) {
			setError(error);
		}

		String errorStacktrace = (String)attributes.get("errorStacktrace");

		if (errorStacktrace != null) {
			setErrorStacktrace(errorStacktrace);
		}

		Long exportImportConfigurationId = (Long)attributes.get(
			"exportImportConfigurationId");

		if (exportImportConfigurationId != null) {
			setExportImportConfigurationId(exportImportConfigurationId);
		}

		Boolean resolved = (Boolean)attributes.get("resolved");

		if (resolved != null) {
			setResolved(resolved);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public ExportImportReportEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this export import report entry.
	 *
	 * @return the company ID of this export import report entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this export import report entry.
	 *
	 * @return the create date of this export import report entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the entity class name ID of this export import report entry.
	 *
	 * @return the entity class name ID of this export import report entry
	 */
	@Override
	public long getEntityClassNameId() {
		return model.getEntityClassNameId();
	}

	/**
	 * Returns the entity external reference code of this export import report entry.
	 *
	 * @return the entity external reference code of this export import report entry
	 */
	@Override
	public String getEntityExternalReferenceCode() {
		return model.getEntityExternalReferenceCode();
	}

	/**
	 * Returns the error of this export import report entry.
	 *
	 * @return the error of this export import report entry
	 */
	@Override
	public String getError() {
		return model.getError();
	}

	/**
	 * Returns the error stacktrace of this export import report entry.
	 *
	 * @return the error stacktrace of this export import report entry
	 */
	@Override
	public String getErrorStacktrace() {
		return model.getErrorStacktrace();
	}

	/**
	 * Returns the export import configuration ID of this export import report entry.
	 *
	 * @return the export import configuration ID of this export import report entry
	 */
	@Override
	public long getExportImportConfigurationId() {
		return model.getExportImportConfigurationId();
	}

	/**
	 * Returns the export import report entry ID of this export import report entry.
	 *
	 * @return the export import report entry ID of this export import report entry
	 */
	@Override
	public long getExportImportReportEntryId() {
		return model.getExportImportReportEntryId();
	}

	/**
	 * Returns the group ID of this export import report entry.
	 *
	 * @return the group ID of this export import report entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this export import report entry.
	 *
	 * @return the modified date of this export import report entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this export import report entry.
	 *
	 * @return the mvcc version of this export import report entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this export import report entry.
	 *
	 * @return the primary key of this export import report entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the resolved of this export import report entry.
	 *
	 * @return the resolved of this export import report entry
	 */
	@Override
	public boolean getResolved() {
		return model.getResolved();
	}

	/**
	 * Returns the type of this export import report entry.
	 *
	 * @return the type of this export import report entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns <code>true</code> if this export import report entry is resolved.
	 *
	 * @return <code>true</code> if this export import report entry is resolved; <code>false</code> otherwise
	 */
	@Override
	public boolean isResolved() {
		return model.isResolved();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this export import report entry.
	 *
	 * @param companyId the company ID of this export import report entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this export import report entry.
	 *
	 * @param createDate the create date of this export import report entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entity class name ID of this export import report entry.
	 *
	 * @param entityClassNameId the entity class name ID of this export import report entry
	 */
	@Override
	public void setEntityClassNameId(long entityClassNameId) {
		model.setEntityClassNameId(entityClassNameId);
	}

	/**
	 * Sets the entity external reference code of this export import report entry.
	 *
	 * @param entityExternalReferenceCode the entity external reference code of this export import report entry
	 */
	@Override
	public void setEntityExternalReferenceCode(
		String entityExternalReferenceCode) {

		model.setEntityExternalReferenceCode(entityExternalReferenceCode);
	}

	/**
	 * Sets the error of this export import report entry.
	 *
	 * @param error the error of this export import report entry
	 */
	@Override
	public void setError(String error) {
		model.setError(error);
	}

	/**
	 * Sets the error stacktrace of this export import report entry.
	 *
	 * @param errorStacktrace the error stacktrace of this export import report entry
	 */
	@Override
	public void setErrorStacktrace(String errorStacktrace) {
		model.setErrorStacktrace(errorStacktrace);
	}

	/**
	 * Sets the export import configuration ID of this export import report entry.
	 *
	 * @param exportImportConfigurationId the export import configuration ID of this export import report entry
	 */
	@Override
	public void setExportImportConfigurationId(
		long exportImportConfigurationId) {

		model.setExportImportConfigurationId(exportImportConfigurationId);
	}

	/**
	 * Sets the export import report entry ID of this export import report entry.
	 *
	 * @param exportImportReportEntryId the export import report entry ID of this export import report entry
	 */
	@Override
	public void setExportImportReportEntryId(long exportImportReportEntryId) {
		model.setExportImportReportEntryId(exportImportReportEntryId);
	}

	/**
	 * Sets the group ID of this export import report entry.
	 *
	 * @param groupId the group ID of this export import report entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this export import report entry.
	 *
	 * @param modifiedDate the modified date of this export import report entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this export import report entry.
	 *
	 * @param mvccVersion the mvcc version of this export import report entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this export import report entry.
	 *
	 * @param primaryKey the primary key of this export import report entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this export import report entry is resolved.
	 *
	 * @param resolved the resolved of this export import report entry
	 */
	@Override
	public void setResolved(boolean resolved) {
		model.setResolved(resolved);
	}

	/**
	 * Sets the type of this export import report entry.
	 *
	 * @param type the type of this export import report entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected ExportImportReportEntryWrapper wrap(
		ExportImportReportEntry exportImportReportEntry) {

		return new ExportImportReportEntryWrapper(exportImportReportEntry);
	}

}