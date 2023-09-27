/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RedirectEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntry
 * @generated
 */
public class RedirectEntryWrapper
	extends BaseModelWrapper<RedirectEntry>
	implements ModelWrapper<RedirectEntry>, RedirectEntry {

	public RedirectEntryWrapper(RedirectEntry redirectEntry) {
		super(redirectEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("redirectEntryId", getRedirectEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("destinationURL", getDestinationURL());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastOccurrenceDate", getLastOccurrenceDate());
		attributes.put("permanent", isPermanent());
		attributes.put("sourceURL", getSourceURL());

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

		Long redirectEntryId = (Long)attributes.get("redirectEntryId");

		if (redirectEntryId != null) {
			setRedirectEntryId(redirectEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String destinationURL = (String)attributes.get("destinationURL");

		if (destinationURL != null) {
			setDestinationURL(destinationURL);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastOccurrenceDate = (Date)attributes.get("lastOccurrenceDate");

		if (lastOccurrenceDate != null) {
			setLastOccurrenceDate(lastOccurrenceDate);
		}

		Boolean permanent = (Boolean)attributes.get("permanent");

		if (permanent != null) {
			setPermanent(permanent);
		}

		String sourceURL = (String)attributes.get("sourceURL");

		if (sourceURL != null) {
			setSourceURL(sourceURL);
		}
	}

	@Override
	public RedirectEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this redirect entry.
	 *
	 * @return the company ID of this redirect entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this redirect entry.
	 *
	 * @return the create date of this redirect entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the destination url of this redirect entry.
	 *
	 * @return the destination url of this redirect entry
	 */
	@Override
	public String getDestinationURL() {
		return model.getDestinationURL();
	}

	/**
	 * Returns the expiration date of this redirect entry.
	 *
	 * @return the expiration date of this redirect entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the group ID of this redirect entry.
	 *
	 * @return the group ID of this redirect entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last occurrence date of this redirect entry.
	 *
	 * @return the last occurrence date of this redirect entry
	 */
	@Override
	public Date getLastOccurrenceDate() {
		return model.getLastOccurrenceDate();
	}

	/**
	 * Returns the modified date of this redirect entry.
	 *
	 * @return the modified date of this redirect entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this redirect entry.
	 *
	 * @return the mvcc version of this redirect entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the permanent of this redirect entry.
	 *
	 * @return the permanent of this redirect entry
	 */
	@Override
	public boolean getPermanent() {
		return model.getPermanent();
	}

	/**
	 * Returns the primary key of this redirect entry.
	 *
	 * @return the primary key of this redirect entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the redirect entry ID of this redirect entry.
	 *
	 * @return the redirect entry ID of this redirect entry
	 */
	@Override
	public long getRedirectEntryId() {
		return model.getRedirectEntryId();
	}

	/**
	 * Returns the source url of this redirect entry.
	 *
	 * @return the source url of this redirect entry
	 */
	@Override
	public String getSourceURL() {
		return model.getSourceURL();
	}

	/**
	 * Returns the user ID of this redirect entry.
	 *
	 * @return the user ID of this redirect entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this redirect entry.
	 *
	 * @return the user name of this redirect entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this redirect entry.
	 *
	 * @return the user uuid of this redirect entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this redirect entry.
	 *
	 * @return the uuid of this redirect entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this redirect entry is permanent.
	 *
	 * @return <code>true</code> if this redirect entry is permanent; <code>false</code> otherwise
	 */
	@Override
	public boolean isPermanent() {
		return model.isPermanent();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this redirect entry.
	 *
	 * @param companyId the company ID of this redirect entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this redirect entry.
	 *
	 * @param createDate the create date of this redirect entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the destination url of this redirect entry.
	 *
	 * @param destinationURL the destination url of this redirect entry
	 */
	@Override
	public void setDestinationURL(String destinationURL) {
		model.setDestinationURL(destinationURL);
	}

	/**
	 * Sets the expiration date of this redirect entry.
	 *
	 * @param expirationDate the expiration date of this redirect entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the group ID of this redirect entry.
	 *
	 * @param groupId the group ID of this redirect entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last occurrence date of this redirect entry.
	 *
	 * @param lastOccurrenceDate the last occurrence date of this redirect entry
	 */
	@Override
	public void setLastOccurrenceDate(Date lastOccurrenceDate) {
		model.setLastOccurrenceDate(lastOccurrenceDate);
	}

	/**
	 * Sets the modified date of this redirect entry.
	 *
	 * @param modifiedDate the modified date of this redirect entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this redirect entry.
	 *
	 * @param mvccVersion the mvcc version of this redirect entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this redirect entry is permanent.
	 *
	 * @param permanent the permanent of this redirect entry
	 */
	@Override
	public void setPermanent(boolean permanent) {
		model.setPermanent(permanent);
	}

	/**
	 * Sets the primary key of this redirect entry.
	 *
	 * @param primaryKey the primary key of this redirect entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the redirect entry ID of this redirect entry.
	 *
	 * @param redirectEntryId the redirect entry ID of this redirect entry
	 */
	@Override
	public void setRedirectEntryId(long redirectEntryId) {
		model.setRedirectEntryId(redirectEntryId);
	}

	/**
	 * Sets the source url of this redirect entry.
	 *
	 * @param sourceURL the source url of this redirect entry
	 */
	@Override
	public void setSourceURL(String sourceURL) {
		model.setSourceURL(sourceURL);
	}

	/**
	 * Sets the user ID of this redirect entry.
	 *
	 * @param userId the user ID of this redirect entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this redirect entry.
	 *
	 * @param userName the user name of this redirect entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this redirect entry.
	 *
	 * @param userUuid the user uuid of this redirect entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this redirect entry.
	 *
	 * @param uuid the uuid of this redirect entry
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
	protected RedirectEntryWrapper wrap(RedirectEntry redirectEntry) {
		return new RedirectEntryWrapper(redirectEntry);
	}

}