/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AnalyticsAssociation}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsAssociation
 * @generated
 */
public class AnalyticsAssociationWrapper
	extends BaseModelWrapper<AnalyticsAssociation>
	implements AnalyticsAssociation, ModelWrapper<AnalyticsAssociation> {

	public AnalyticsAssociationWrapper(
		AnalyticsAssociation analyticsAssociation) {

		super(analyticsAssociation);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("analyticsAssociationId", getAnalyticsAssociationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("userId", getUserId());
		attributes.put("associationClassName", getAssociationClassName());
		attributes.put("associationClassPK", getAssociationClassPK());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());

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

		Long analyticsAssociationId = (Long)attributes.get(
			"analyticsAssociationId");

		if (analyticsAssociationId != null) {
			setAnalyticsAssociationId(analyticsAssociationId);
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

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String associationClassName = (String)attributes.get(
			"associationClassName");

		if (associationClassName != null) {
			setAssociationClassName(associationClassName);
		}

		Long associationClassPK = (Long)attributes.get("associationClassPK");

		if (associationClassPK != null) {
			setAssociationClassPK(associationClassPK);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public AnalyticsAssociation cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the analytics association ID of this analytics association.
	 *
	 * @return the analytics association ID of this analytics association
	 */
	@Override
	public long getAnalyticsAssociationId() {
		return model.getAnalyticsAssociationId();
	}

	/**
	 * Returns the association class name of this analytics association.
	 *
	 * @return the association class name of this analytics association
	 */
	@Override
	public String getAssociationClassName() {
		return model.getAssociationClassName();
	}

	/**
	 * Returns the association class pk of this analytics association.
	 *
	 * @return the association class pk of this analytics association
	 */
	@Override
	public long getAssociationClassPK() {
		return model.getAssociationClassPK();
	}

	/**
	 * Returns the class name of this analytics association.
	 *
	 * @return the class name of this analytics association
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class pk of this analytics association.
	 *
	 * @return the class pk of this analytics association
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this analytics association.
	 *
	 * @return the company ID of this analytics association
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this analytics association.
	 *
	 * @return the create date of this analytics association
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this analytics association.
	 *
	 * @return the ct collection ID of this analytics association
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this analytics association.
	 *
	 * @return the modified date of this analytics association
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this analytics association.
	 *
	 * @return the mvcc version of this analytics association
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this analytics association.
	 *
	 * @return the primary key of this analytics association
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this analytics association.
	 *
	 * @return the user ID of this analytics association
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this analytics association.
	 *
	 * @return the user uuid of this analytics association
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the analytics association ID of this analytics association.
	 *
	 * @param analyticsAssociationId the analytics association ID of this analytics association
	 */
	@Override
	public void setAnalyticsAssociationId(long analyticsAssociationId) {
		model.setAnalyticsAssociationId(analyticsAssociationId);
	}

	/**
	 * Sets the association class name of this analytics association.
	 *
	 * @param associationClassName the association class name of this analytics association
	 */
	@Override
	public void setAssociationClassName(String associationClassName) {
		model.setAssociationClassName(associationClassName);
	}

	/**
	 * Sets the association class pk of this analytics association.
	 *
	 * @param associationClassPK the association class pk of this analytics association
	 */
	@Override
	public void setAssociationClassPK(long associationClassPK) {
		model.setAssociationClassPK(associationClassPK);
	}

	/**
	 * Sets the class name of this analytics association.
	 *
	 * @param className the class name of this analytics association
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class pk of this analytics association.
	 *
	 * @param classPK the class pk of this analytics association
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this analytics association.
	 *
	 * @param companyId the company ID of this analytics association
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this analytics association.
	 *
	 * @param createDate the create date of this analytics association
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this analytics association.
	 *
	 * @param ctCollectionId the ct collection ID of this analytics association
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this analytics association.
	 *
	 * @param modifiedDate the modified date of this analytics association
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this analytics association.
	 *
	 * @param mvccVersion the mvcc version of this analytics association
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this analytics association.
	 *
	 * @param primaryKey the primary key of this analytics association
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this analytics association.
	 *
	 * @param userId the user ID of this analytics association
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this analytics association.
	 *
	 * @param userUuid the user uuid of this analytics association
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<AnalyticsAssociation, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AnalyticsAssociation, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AnalyticsAssociationWrapper wrap(
		AnalyticsAssociation analyticsAssociation) {

		return new AnalyticsAssociationWrapper(analyticsAssociation);
	}

}