/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Region}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Region
 * @generated
 */
public class RegionWrapper
	extends BaseModelWrapper<Region> implements ModelWrapper<Region>, Region {

	public RegionWrapper(Region region) {
		super(region);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("regionId", getRegionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("countryId", getCountryId());
		attributes.put("active", isActive());
		attributes.put("name", getName());
		attributes.put("position", getPosition());
		attributes.put("regionCode", getRegionCode());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}

		Long regionId = (Long)attributes.get("regionId");

		if (regionId != null) {
			setRegionId(regionId);
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

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Double position = (Double)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}

		String regionCode = (String)attributes.get("regionCode");

		if (regionCode != null) {
			setRegionCode(regionCode);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Region cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this region.
	 *
	 * @return the active of this region
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this region.
	 *
	 * @return the company ID of this region
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the country ID of this region.
	 *
	 * @return the country ID of this region
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the create date of this region.
	 *
	 * @return the create date of this region
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default language ID of this region.
	 *
	 * @return the default language ID of this region
	 */
	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	@Override
	public Map<String, String> getLanguageIdToTitleMap() {
		return model.getLanguageIdToTitleMap();
	}

	/**
	 * Returns the last publish date of this region.
	 *
	 * @return the last publish date of this region
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this region.
	 *
	 * @return the modified date of this region
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this region.
	 *
	 * @return the mvcc version of this region
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this region.
	 *
	 * @return the name of this region
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the position of this region.
	 *
	 * @return the position of this region
	 */
	@Override
	public double getPosition() {
		return model.getPosition();
	}

	/**
	 * Returns the primary key of this region.
	 *
	 * @return the primary key of this region
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the region code of this region.
	 *
	 * @return the region code of this region
	 */
	@Override
	public String getRegionCode() {
		return model.getRegionCode();
	}

	/**
	 * Returns the region ID of this region.
	 *
	 * @return the region ID of this region
	 */
	@Override
	public long getRegionId() {
		return model.getRegionId();
	}

	@Override
	public String getTitle() {
		return model.getTitle();
	}

	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return model.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleMapAsXML() {
		return model.getTitleMapAsXML();
	}

	/**
	 * Returns the user ID of this region.
	 *
	 * @return the user ID of this region
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this region.
	 *
	 * @return the user name of this region
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this region.
	 *
	 * @return the user uuid of this region
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this region.
	 *
	 * @return the uuid of this region
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this region is active.
	 *
	 * @return <code>true</code> if this region is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this region is active.
	 *
	 * @param active the active of this region
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this region.
	 *
	 * @param companyId the company ID of this region
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the country ID of this region.
	 *
	 * @param countryId the country ID of this region
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the create date of this region.
	 *
	 * @param createDate the create date of this region
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the default language ID of this region.
	 *
	 * @param defaultLanguageId the default language ID of this region
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		model.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	 * Sets the last publish date of this region.
	 *
	 * @param lastPublishDate the last publish date of this region
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this region.
	 *
	 * @param modifiedDate the modified date of this region
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this region.
	 *
	 * @param mvccVersion the mvcc version of this region
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this region.
	 *
	 * @param name the name of this region
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the position of this region.
	 *
	 * @param position the position of this region
	 */
	@Override
	public void setPosition(double position) {
		model.setPosition(position);
	}

	/**
	 * Sets the primary key of this region.
	 *
	 * @param primaryKey the primary key of this region
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the region code of this region.
	 *
	 * @param regionCode the region code of this region
	 */
	@Override
	public void setRegionCode(String regionCode) {
		model.setRegionCode(regionCode);
	}

	/**
	 * Sets the region ID of this region.
	 *
	 * @param regionId the region ID of this region
	 */
	@Override
	public void setRegionId(long regionId) {
		model.setRegionId(regionId);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		model.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the user ID of this region.
	 *
	 * @param userId the user ID of this region
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this region.
	 *
	 * @param userName the user name of this region
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this region.
	 *
	 * @param userUuid the user uuid of this region
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this region.
	 *
	 * @param uuid the uuid of this region
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
	protected RegionWrapper wrap(Region region) {
		return new RegionWrapper(region);
	}

}