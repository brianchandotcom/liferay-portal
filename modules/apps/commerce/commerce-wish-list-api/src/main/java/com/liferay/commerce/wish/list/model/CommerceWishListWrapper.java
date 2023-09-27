/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceWishList}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishList
 * @generated
 */
public class CommerceWishListWrapper
	extends BaseModelWrapper<CommerceWishList>
	implements CommerceWishList, ModelWrapper<CommerceWishList> {

	public CommerceWishListWrapper(CommerceWishList commerceWishList) {
		super(commerceWishList);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("commerceWishListId", getCommerceWishListId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("defaultWishList", isDefaultWishList());

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

		Long commerceWishListId = (Long)attributes.get("commerceWishListId");

		if (commerceWishListId != null) {
			setCommerceWishListId(commerceWishListId);
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

		Boolean defaultWishList = (Boolean)attributes.get("defaultWishList");

		if (defaultWishList != null) {
			setDefaultWishList(defaultWishList);
		}
	}

	@Override
	public CommerceWishList cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce wish list ID of this commerce wish list.
	 *
	 * @return the commerce wish list ID of this commerce wish list
	 */
	@Override
	public long getCommerceWishListId() {
		return model.getCommerceWishListId();
	}

	/**
	 * Returns the company ID of this commerce wish list.
	 *
	 * @return the company ID of this commerce wish list
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce wish list.
	 *
	 * @return the create date of this commerce wish list
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default wish list of this commerce wish list.
	 *
	 * @return the default wish list of this commerce wish list
	 */
	@Override
	public boolean getDefaultWishList() {
		return model.getDefaultWishList();
	}

	/**
	 * Returns the group ID of this commerce wish list.
	 *
	 * @return the group ID of this commerce wish list
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce wish list.
	 *
	 * @return the modified date of this commerce wish list
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce wish list.
	 *
	 * @return the mvcc version of this commerce wish list
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this commerce wish list.
	 *
	 * @return the name of this commerce wish list
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce wish list.
	 *
	 * @return the primary key of this commerce wish list
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce wish list.
	 *
	 * @return the user ID of this commerce wish list
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce wish list.
	 *
	 * @return the user name of this commerce wish list
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce wish list.
	 *
	 * @return the user uuid of this commerce wish list
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce wish list.
	 *
	 * @return the uuid of this commerce wish list
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce wish list is default wish list.
	 *
	 * @return <code>true</code> if this commerce wish list is default wish list; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultWishList() {
		return model.isDefaultWishList();
	}

	@Override
	public boolean isGuestWishList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isGuestWishList();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce wish list ID of this commerce wish list.
	 *
	 * @param commerceWishListId the commerce wish list ID of this commerce wish list
	 */
	@Override
	public void setCommerceWishListId(long commerceWishListId) {
		model.setCommerceWishListId(commerceWishListId);
	}

	/**
	 * Sets the company ID of this commerce wish list.
	 *
	 * @param companyId the company ID of this commerce wish list
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce wish list.
	 *
	 * @param createDate the create date of this commerce wish list
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this commerce wish list is default wish list.
	 *
	 * @param defaultWishList the default wish list of this commerce wish list
	 */
	@Override
	public void setDefaultWishList(boolean defaultWishList) {
		model.setDefaultWishList(defaultWishList);
	}

	/**
	 * Sets the group ID of this commerce wish list.
	 *
	 * @param groupId the group ID of this commerce wish list
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce wish list.
	 *
	 * @param modifiedDate the modified date of this commerce wish list
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce wish list.
	 *
	 * @param mvccVersion the mvcc version of this commerce wish list
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this commerce wish list.
	 *
	 * @param name the name of this commerce wish list
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce wish list.
	 *
	 * @param primaryKey the primary key of this commerce wish list
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce wish list.
	 *
	 * @param userId the user ID of this commerce wish list
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce wish list.
	 *
	 * @param userName the user name of this commerce wish list
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce wish list.
	 *
	 * @param userUuid the user uuid of this commerce wish list
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce wish list.
	 *
	 * @param uuid the uuid of this commerce wish list
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
	protected CommerceWishListWrapper wrap(CommerceWishList commerceWishList) {
		return new CommerceWishListWrapper(commerceWishList);
	}

}