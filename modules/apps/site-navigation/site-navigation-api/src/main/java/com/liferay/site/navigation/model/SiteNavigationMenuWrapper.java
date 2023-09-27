/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link SiteNavigationMenu}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenu
 * @generated
 */
public class SiteNavigationMenuWrapper
	extends BaseModelWrapper<SiteNavigationMenu>
	implements ModelWrapper<SiteNavigationMenu>, SiteNavigationMenu {

	public SiteNavigationMenuWrapper(SiteNavigationMenu siteNavigationMenu) {
		super(siteNavigationMenu);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("siteNavigationMenuId", getSiteNavigationMenuId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("auto", isAuto());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long siteNavigationMenuId = (Long)attributes.get(
			"siteNavigationMenuId");

		if (siteNavigationMenuId != null) {
			setSiteNavigationMenuId(siteNavigationMenuId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean auto = (Boolean)attributes.get("auto");

		if (auto != null) {
			setAuto(auto);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public SiteNavigationMenu cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the auto of this site navigation menu.
	 *
	 * @return the auto of this site navigation menu
	 */
	@Override
	public boolean getAuto() {
		return model.getAuto();
	}

	/**
	 * Returns the company ID of this site navigation menu.
	 *
	 * @return the company ID of this site navigation menu
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this site navigation menu.
	 *
	 * @return the create date of this site navigation menu
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this site navigation menu.
	 *
	 * @return the ct collection ID of this site navigation menu
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this site navigation menu.
	 *
	 * @return the group ID of this site navigation menu
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this site navigation menu.
	 *
	 * @return the last publish date of this site navigation menu
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this site navigation menu.
	 *
	 * @return the modified date of this site navigation menu
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this site navigation menu.
	 *
	 * @return the mvcc version of this site navigation menu
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this site navigation menu.
	 *
	 * @return the name of this site navigation menu
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this site navigation menu.
	 *
	 * @return the primary key of this site navigation menu
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the site navigation menu ID of this site navigation menu.
	 *
	 * @return the site navigation menu ID of this site navigation menu
	 */
	@Override
	public long getSiteNavigationMenuId() {
		return model.getSiteNavigationMenuId();
	}

	/**
	 * Returns the type of this site navigation menu.
	 *
	 * @return the type of this site navigation menu
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	@Override
	public String getTypeKey() {
		return model.getTypeKey();
	}

	/**
	 * Returns the user ID of this site navigation menu.
	 *
	 * @return the user ID of this site navigation menu
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this site navigation menu.
	 *
	 * @return the user name of this site navigation menu
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this site navigation menu.
	 *
	 * @return the user uuid of this site navigation menu
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this site navigation menu.
	 *
	 * @return the uuid of this site navigation menu
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this site navigation menu is auto.
	 *
	 * @return <code>true</code> if this site navigation menu is auto; <code>false</code> otherwise
	 */
	@Override
	public boolean isAuto() {
		return model.isAuto();
	}

	@Override
	public boolean isPrimary() {
		return model.isPrimary();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this site navigation menu is auto.
	 *
	 * @param auto the auto of this site navigation menu
	 */
	@Override
	public void setAuto(boolean auto) {
		model.setAuto(auto);
	}

	/**
	 * Sets the company ID of this site navigation menu.
	 *
	 * @param companyId the company ID of this site navigation menu
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this site navigation menu.
	 *
	 * @param createDate the create date of this site navigation menu
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this site navigation menu.
	 *
	 * @param ctCollectionId the ct collection ID of this site navigation menu
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this site navigation menu.
	 *
	 * @param groupId the group ID of this site navigation menu
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this site navigation menu.
	 *
	 * @param lastPublishDate the last publish date of this site navigation menu
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this site navigation menu.
	 *
	 * @param modifiedDate the modified date of this site navigation menu
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this site navigation menu.
	 *
	 * @param mvccVersion the mvcc version of this site navigation menu
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this site navigation menu.
	 *
	 * @param name the name of this site navigation menu
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this site navigation menu.
	 *
	 * @param primaryKey the primary key of this site navigation menu
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the site navigation menu ID of this site navigation menu.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID of this site navigation menu
	 */
	@Override
	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		model.setSiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	 * Sets the type of this site navigation menu.
	 *
	 * @param type the type of this site navigation menu
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this site navigation menu.
	 *
	 * @param userId the user ID of this site navigation menu
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this site navigation menu.
	 *
	 * @param userName the user name of this site navigation menu
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this site navigation menu.
	 *
	 * @param userUuid the user uuid of this site navigation menu
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this site navigation menu.
	 *
	 * @param uuid the uuid of this site navigation menu
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
	public Map<String, Function<SiteNavigationMenu, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<SiteNavigationMenu, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SiteNavigationMenuWrapper wrap(
		SiteNavigationMenu siteNavigationMenu) {

		return new SiteNavigationMenuWrapper(siteNavigationMenu);
	}

}