/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LaunchEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntry
 * @generated
 */
public class LaunchEntryWrapper
	extends BaseModelWrapper<LaunchEntry>
	implements LaunchEntry, ModelWrapper<LaunchEntry> {

	public LaunchEntryWrapper(LaunchEntry launchEntry) {
		super(launchEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("launchEntryId", getLaunchEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("launchSetId", getLaunchSetId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("classVersion", getClassVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long launchEntryId = (Long)attributes.get("launchEntryId");

		if (launchEntryId != null) {
			setLaunchEntryId(launchEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long launchSetId = (Long)attributes.get("launchSetId");

		if (launchSetId != null) {
			setLaunchSetId(launchSetId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String classVersion = (String)attributes.get("classVersion");

		if (classVersion != null) {
			setClassVersion(classVersion);
		}
	}

	@Override
	public LaunchEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this launch entry.
	 *
	 * @return the fully qualified class name of this launch entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this launch entry.
	 *
	 * @return the class name ID of this launch entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this launch entry.
	 *
	 * @return the class pk of this launch entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the class version of this launch entry.
	 *
	 * @return the class version of this launch entry
	 */
	@Override
	public String getClassVersion() {
		return model.getClassVersion();
	}

	/**
	 * Returns the company ID of this launch entry.
	 *
	 * @return the company ID of this launch entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this launch entry.
	 *
	 * @return the create date of this launch entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this launch entry.
	 *
	 * @return the external reference code of this launch entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the launch entry ID of this launch entry.
	 *
	 * @return the launch entry ID of this launch entry
	 */
	@Override
	public long getLaunchEntryId() {
		return model.getLaunchEntryId();
	}

	/**
	 * Returns the launch set ID of this launch entry.
	 *
	 * @return the launch set ID of this launch entry
	 */
	@Override
	public long getLaunchSetId() {
		return model.getLaunchSetId();
	}

	/**
	 * Returns the modified date of this launch entry.
	 *
	 * @return the modified date of this launch entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this launch entry.
	 *
	 * @return the mvcc version of this launch entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this launch entry.
	 *
	 * @return the primary key of this launch entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this launch entry.
	 *
	 * @return the user ID of this launch entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this launch entry.
	 *
	 * @return the user uuid of this launch entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this launch entry.
	 *
	 * @return the uuid of this launch entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
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
	 * Sets the class name ID of this launch entry.
	 *
	 * @param classNameId the class name ID of this launch entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this launch entry.
	 *
	 * @param classPK the class pk of this launch entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the class version of this launch entry.
	 *
	 * @param classVersion the class version of this launch entry
	 */
	@Override
	public void setClassVersion(String classVersion) {
		model.setClassVersion(classVersion);
	}

	/**
	 * Sets the company ID of this launch entry.
	 *
	 * @param companyId the company ID of this launch entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this launch entry.
	 *
	 * @param createDate the create date of this launch entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this launch entry.
	 *
	 * @param externalReferenceCode the external reference code of this launch entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the launch entry ID of this launch entry.
	 *
	 * @param launchEntryId the launch entry ID of this launch entry
	 */
	@Override
	public void setLaunchEntryId(long launchEntryId) {
		model.setLaunchEntryId(launchEntryId);
	}

	/**
	 * Sets the launch set ID of this launch entry.
	 *
	 * @param launchSetId the launch set ID of this launch entry
	 */
	@Override
	public void setLaunchSetId(long launchSetId) {
		model.setLaunchSetId(launchSetId);
	}

	/**
	 * Sets the modified date of this launch entry.
	 *
	 * @param modifiedDate the modified date of this launch entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this launch entry.
	 *
	 * @param mvccVersion the mvcc version of this launch entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this launch entry.
	 *
	 * @param primaryKey the primary key of this launch entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this launch entry.
	 *
	 * @param userId the user ID of this launch entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this launch entry.
	 *
	 * @param userUuid the user uuid of this launch entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this launch entry.
	 *
	 * @param uuid the uuid of this launch entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected LaunchEntryWrapper wrap(LaunchEntry launchEntry) {
		return new LaunchEntryWrapper(launchEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1909748486