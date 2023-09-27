/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceWishListItem}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItem
 * @generated
 */
public class CommerceWishListItemWrapper
	extends BaseModelWrapper<CommerceWishListItem>
	implements CommerceWishListItem, ModelWrapper<CommerceWishListItem> {

	public CommerceWishListItemWrapper(
		CommerceWishListItem commerceWishListItem) {

		super(commerceWishListItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("commerceWishListItemId", getCommerceWishListItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceWishListId", getCommerceWishListId());
		attributes.put("CPInstanceUuid", getCPInstanceUuid());
		attributes.put("CProductId", getCProductId());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceWishListItemId = (Long)attributes.get(
			"commerceWishListItemId");

		if (commerceWishListItemId != null) {
			setCommerceWishListItemId(commerceWishListItemId);
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

		Long commerceWishListId = (Long)attributes.get("commerceWishListId");

		if (commerceWishListId != null) {
			setCommerceWishListId(commerceWishListId);
		}

		String CPInstanceUuid = (String)attributes.get("CPInstanceUuid");

		if (CPInstanceUuid != null) {
			setCPInstanceUuid(CPInstanceUuid);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}
	}

	@Override
	public CommerceWishListItem cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.fetchCPInstance();
	}

	@Override
	public CommerceWishList getCommerceWishList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceWishList();
	}

	/**
	 * Returns the commerce wish list ID of this commerce wish list item.
	 *
	 * @return the commerce wish list ID of this commerce wish list item
	 */
	@Override
	public long getCommerceWishListId() {
		return model.getCommerceWishListId();
	}

	/**
	 * Returns the commerce wish list item ID of this commerce wish list item.
	 *
	 * @return the commerce wish list item ID of this commerce wish list item
	 */
	@Override
	public long getCommerceWishListItemId() {
		return model.getCommerceWishListItemId();
	}

	/**
	 * Returns the company ID of this commerce wish list item.
	 *
	 * @return the company ID of this commerce wish list item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp instance uuid of this commerce wish list item.
	 *
	 * @return the cp instance uuid of this commerce wish list item
	 */
	@Override
	public String getCPInstanceUuid() {
		return model.getCPInstanceUuid();
	}

	@Override
	public com.liferay.commerce.product.model.CProduct getCProduct()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCProduct();
	}

	/**
	 * Returns the c product ID of this commerce wish list item.
	 *
	 * @return the c product ID of this commerce wish list item
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this commerce wish list item.
	 *
	 * @return the create date of this commerce wish list item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this commerce wish list item.
	 *
	 * @return the group ID of this commerce wish list item
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the json of this commerce wish list item.
	 *
	 * @return the json of this commerce wish list item
	 */
	@Override
	public String getJson() {
		return model.getJson();
	}

	/**
	 * Returns the modified date of this commerce wish list item.
	 *
	 * @return the modified date of this commerce wish list item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce wish list item.
	 *
	 * @return the mvcc version of this commerce wish list item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce wish list item.
	 *
	 * @return the primary key of this commerce wish list item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce wish list item.
	 *
	 * @return the user ID of this commerce wish list item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce wish list item.
	 *
	 * @return the user name of this commerce wish list item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce wish list item.
	 *
	 * @return the user uuid of this commerce wish list item
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isIgnoreSKUCombinations()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isIgnoreSKUCombinations();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce wish list ID of this commerce wish list item.
	 *
	 * @param commerceWishListId the commerce wish list ID of this commerce wish list item
	 */
	@Override
	public void setCommerceWishListId(long commerceWishListId) {
		model.setCommerceWishListId(commerceWishListId);
	}

	/**
	 * Sets the commerce wish list item ID of this commerce wish list item.
	 *
	 * @param commerceWishListItemId the commerce wish list item ID of this commerce wish list item
	 */
	@Override
	public void setCommerceWishListItemId(long commerceWishListItemId) {
		model.setCommerceWishListItemId(commerceWishListItemId);
	}

	/**
	 * Sets the company ID of this commerce wish list item.
	 *
	 * @param companyId the company ID of this commerce wish list item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance uuid of this commerce wish list item.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this commerce wish list item
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		model.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the c product ID of this commerce wish list item.
	 *
	 * @param CProductId the c product ID of this commerce wish list item
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce wish list item.
	 *
	 * @param createDate the create date of this commerce wish list item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this commerce wish list item.
	 *
	 * @param groupId the group ID of this commerce wish list item
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the json of this commerce wish list item.
	 *
	 * @param json the json of this commerce wish list item
	 */
	@Override
	public void setJson(String json) {
		model.setJson(json);
	}

	/**
	 * Sets the modified date of this commerce wish list item.
	 *
	 * @param modifiedDate the modified date of this commerce wish list item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce wish list item.
	 *
	 * @param mvccVersion the mvcc version of this commerce wish list item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce wish list item.
	 *
	 * @param primaryKey the primary key of this commerce wish list item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce wish list item.
	 *
	 * @param userId the user ID of this commerce wish list item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce wish list item.
	 *
	 * @param userName the user name of this commerce wish list item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce wish list item.
	 *
	 * @param userUuid the user uuid of this commerce wish list item
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
	protected CommerceWishListItemWrapper wrap(
		CommerceWishListItem commerceWishListItem) {

		return new CommerceWishListItemWrapper(commerceWishListItem);
	}

}