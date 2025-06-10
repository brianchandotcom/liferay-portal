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
 * This class is a wrapper for {@link ImportReportEntry}.
 * </p>
 *
 * @author Carlos Correa
 * @see ImportReportEntry
 * @generated
 */
public class ImportReportEntryWrapper
	extends BaseModelWrapper<ImportReportEntry>
	implements ImportReportEntry, ModelWrapper<ImportReportEntry> {

	public ImportReportEntryWrapper(ImportReportEntry importReportEntry) {
		super(importReportEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("importReportEntryId", getImportReportEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("entityClassNameId", getEntityClassNameId());
		attributes.put(
			"entityExternalReferenceCode", getEntityExternalReferenceCode());
		attributes.put("error", getError());
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

		Long importReportEntryId = (Long)attributes.get("importReportEntryId");

		if (importReportEntryId != null) {
			setImportReportEntryId(importReportEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
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
	public ImportReportEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this import report entry.
	 *
	 * @return the fully qualified class name of this import report entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this import report entry.
	 *
	 * @return the class name ID of this import report entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this import report entry.
	 *
	 * @return the class pk of this import report entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this import report entry.
	 *
	 * @return the company ID of this import report entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this import report entry.
	 *
	 * @return the create date of this import report entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the entity class name ID of this import report entry.
	 *
	 * @return the entity class name ID of this import report entry
	 */
	@Override
	public long getEntityClassNameId() {
		return model.getEntityClassNameId();
	}

	/**
	 * Returns the entity external reference code of this import report entry.
	 *
	 * @return the entity external reference code of this import report entry
	 */
	@Override
	public String getEntityExternalReferenceCode() {
		return model.getEntityExternalReferenceCode();
	}

	/**
	 * Returns the error of this import report entry.
	 *
	 * @return the error of this import report entry
	 */
	@Override
	public String getError() {
		return model.getError();
	}

	/**
	 * Returns the import report entry ID of this import report entry.
	 *
	 * @return the import report entry ID of this import report entry
	 */
	@Override
	public long getImportReportEntryId() {
		return model.getImportReportEntryId();
	}

	/**
	 * Returns the modified date of this import report entry.
	 *
	 * @return the modified date of this import report entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this import report entry.
	 *
	 * @return the mvcc version of this import report entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this import report entry.
	 *
	 * @return the primary key of this import report entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the resolved of this import report entry.
	 *
	 * @return the resolved of this import report entry
	 */
	@Override
	public boolean getResolved() {
		return model.getResolved();
	}

	/**
	 * Returns the type of this import report entry.
	 *
	 * @return the type of this import report entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns <code>true</code> if this import report entry is resolved.
	 *
	 * @return <code>true</code> if this import report entry is resolved; <code>false</code> otherwise
	 */
	@Override
	public boolean isResolved() {
		return model.isResolved();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this import report entry.
	 *
	 * @param classNameId the class name ID of this import report entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this import report entry.
	 *
	 * @param classPK the class pk of this import report entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this import report entry.
	 *
	 * @param companyId the company ID of this import report entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this import report entry.
	 *
	 * @param createDate the create date of this import report entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entity class name ID of this import report entry.
	 *
	 * @param entityClassNameId the entity class name ID of this import report entry
	 */
	@Override
	public void setEntityClassNameId(long entityClassNameId) {
		model.setEntityClassNameId(entityClassNameId);
	}

	/**
	 * Sets the entity external reference code of this import report entry.
	 *
	 * @param entityExternalReferenceCode the entity external reference code of this import report entry
	 */
	@Override
	public void setEntityExternalReferenceCode(
		String entityExternalReferenceCode) {

		model.setEntityExternalReferenceCode(entityExternalReferenceCode);
	}

	/**
	 * Sets the error of this import report entry.
	 *
	 * @param error the error of this import report entry
	 */
	@Override
	public void setError(String error) {
		model.setError(error);
	}

	/**
	 * Sets the import report entry ID of this import report entry.
	 *
	 * @param importReportEntryId the import report entry ID of this import report entry
	 */
	@Override
	public void setImportReportEntryId(long importReportEntryId) {
		model.setImportReportEntryId(importReportEntryId);
	}

	/**
	 * Sets the modified date of this import report entry.
	 *
	 * @param modifiedDate the modified date of this import report entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this import report entry.
	 *
	 * @param mvccVersion the mvcc version of this import report entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this import report entry.
	 *
	 * @param primaryKey the primary key of this import report entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this import report entry is resolved.
	 *
	 * @param resolved the resolved of this import report entry
	 */
	@Override
	public void setResolved(boolean resolved) {
		model.setResolved(resolved);
	}

	/**
	 * Sets the type of this import report entry.
	 *
	 * @param type the type of this import report entry
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
	protected ImportReportEntryWrapper wrap(
		ImportReportEntry importReportEntry) {

		return new ImportReportEntryWrapper(importReportEntry);
	}

}