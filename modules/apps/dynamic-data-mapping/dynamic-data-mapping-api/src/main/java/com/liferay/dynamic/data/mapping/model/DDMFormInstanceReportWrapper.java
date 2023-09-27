/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDMFormInstanceReport}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceReport
 * @generated
 */
public class DDMFormInstanceReportWrapper
	extends BaseModelWrapper<DDMFormInstanceReport>
	implements DDMFormInstanceReport, ModelWrapper<DDMFormInstanceReport> {

	public DDMFormInstanceReportWrapper(
		DDMFormInstanceReport ddmFormInstanceReport) {

		super(ddmFormInstanceReport);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("formInstanceReportId", getFormInstanceReportId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("formInstanceId", getFormInstanceId());
		attributes.put("data", getData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long formInstanceReportId = (Long)attributes.get(
			"formInstanceReportId");

		if (formInstanceReportId != null) {
			setFormInstanceReportId(formInstanceReportId);
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

		Long formInstanceId = (Long)attributes.get("formInstanceId");

		if (formInstanceId != null) {
			setFormInstanceId(formInstanceId);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	@Override
	public DDMFormInstanceReport cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ddm form instance report.
	 *
	 * @return the company ID of this ddm form instance report
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddm form instance report.
	 *
	 * @return the create date of this ddm form instance report
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ddm form instance report.
	 *
	 * @return the ct collection ID of this ddm form instance report
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the data of this ddm form instance report.
	 *
	 * @return the data of this ddm form instance report
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	@Override
	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFormInstance();
	}

	/**
	 * Returns the form instance ID of this ddm form instance report.
	 *
	 * @return the form instance ID of this ddm form instance report
	 */
	@Override
	public long getFormInstanceId() {
		return model.getFormInstanceId();
	}

	/**
	 * Returns the form instance report ID of this ddm form instance report.
	 *
	 * @return the form instance report ID of this ddm form instance report
	 */
	@Override
	public long getFormInstanceReportId() {
		return model.getFormInstanceReportId();
	}

	/**
	 * Returns the group ID of this ddm form instance report.
	 *
	 * @return the group ID of this ddm form instance report
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this ddm form instance report.
	 *
	 * @return the modified date of this ddm form instance report
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ddm form instance report.
	 *
	 * @return the mvcc version of this ddm form instance report
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddm form instance report.
	 *
	 * @return the primary key of this ddm form instance report
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ddm form instance report.
	 *
	 * @param companyId the company ID of this ddm form instance report
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddm form instance report.
	 *
	 * @param createDate the create date of this ddm form instance report
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ddm form instance report.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm form instance report
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the data of this ddm form instance report.
	 *
	 * @param data the data of this ddm form instance report
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	 * Sets the form instance ID of this ddm form instance report.
	 *
	 * @param formInstanceId the form instance ID of this ddm form instance report
	 */
	@Override
	public void setFormInstanceId(long formInstanceId) {
		model.setFormInstanceId(formInstanceId);
	}

	/**
	 * Sets the form instance report ID of this ddm form instance report.
	 *
	 * @param formInstanceReportId the form instance report ID of this ddm form instance report
	 */
	@Override
	public void setFormInstanceReportId(long formInstanceReportId) {
		model.setFormInstanceReportId(formInstanceReportId);
	}

	/**
	 * Sets the group ID of this ddm form instance report.
	 *
	 * @param groupId the group ID of this ddm form instance report
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this ddm form instance report.
	 *
	 * @param modifiedDate the modified date of this ddm form instance report
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ddm form instance report.
	 *
	 * @param mvccVersion the mvcc version of this ddm form instance report
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddm form instance report.
	 *
	 * @param primaryKey the primary key of this ddm form instance report
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DDMFormInstanceReport, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMFormInstanceReport, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDMFormInstanceReportWrapper wrap(
		DDMFormInstanceReport ddmFormInstanceReport) {

		return new DDMFormInstanceReportWrapper(ddmFormInstanceReport);
	}

}