/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.discount.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceDiscountUsageEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntry
 * @generated
 */
@ProviderType
public class CommerceDiscountUsageEntryWrapper
	implements CommerceDiscountUsageEntry,
		ModelWrapper<CommerceDiscountUsageEntry> {
	public CommerceDiscountUsageEntryWrapper(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		_commerceDiscountUsageEntry = commerceDiscountUsageEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountUsageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountUsageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceDiscountUsageEntryId",
			getCommerceDiscountUsageEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("discountUserId", getDiscountUserId());
		attributes.put("discountOrganizationId", getDiscountOrganizationId());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("commerceDiscountId", getCommerceDiscountId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountUsageEntryId = (Long)attributes.get(
				"commerceDiscountUsageEntryId");

		if (commerceDiscountUsageEntryId != null) {
			setCommerceDiscountUsageEntryId(commerceDiscountUsageEntryId);
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

		Long discountUserId = (Long)attributes.get("discountUserId");

		if (discountUserId != null) {
			setDiscountUserId(discountUserId);
		}

		Long discountOrganizationId = (Long)attributes.get(
				"discountOrganizationId");

		if (discountOrganizationId != null) {
			setDiscountOrganizationId(discountOrganizationId);
		}

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceDiscountUsageEntryWrapper((CommerceDiscountUsageEntry)_commerceDiscountUsageEntry.clone());
	}

	@Override
	public int compareTo(CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		return _commerceDiscountUsageEntry.compareTo(commerceDiscountUsageEntry);
	}

	/**
	* Returns the commerce discount ID of this commerce discount usage entry.
	*
	* @return the commerce discount ID of this commerce discount usage entry
	*/
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountUsageEntry.getCommerceDiscountId();
	}

	/**
	* Returns the commerce discount usage entry ID of this commerce discount usage entry.
	*
	* @return the commerce discount usage entry ID of this commerce discount usage entry
	*/
	@Override
	public long getCommerceDiscountUsageEntryId() {
		return _commerceDiscountUsageEntry.getCommerceDiscountUsageEntryId();
	}

	/**
	* Returns the commerce order ID of this commerce discount usage entry.
	*
	* @return the commerce order ID of this commerce discount usage entry
	*/
	@Override
	public long getCommerceOrderId() {
		return _commerceDiscountUsageEntry.getCommerceOrderId();
	}

	/**
	* Returns the company ID of this commerce discount usage entry.
	*
	* @return the company ID of this commerce discount usage entry
	*/
	@Override
	public long getCompanyId() {
		return _commerceDiscountUsageEntry.getCompanyId();
	}

	/**
	* Returns the create date of this commerce discount usage entry.
	*
	* @return the create date of this commerce discount usage entry
	*/
	@Override
	public Date getCreateDate() {
		return _commerceDiscountUsageEntry.getCreateDate();
	}

	/**
	* Returns the discount organization ID of this commerce discount usage entry.
	*
	* @return the discount organization ID of this commerce discount usage entry
	*/
	@Override
	public long getDiscountOrganizationId() {
		return _commerceDiscountUsageEntry.getDiscountOrganizationId();
	}

	/**
	* Returns the discount user ID of this commerce discount usage entry.
	*
	* @return the discount user ID of this commerce discount usage entry
	*/
	@Override
	public long getDiscountUserId() {
		return _commerceDiscountUsageEntry.getDiscountUserId();
	}

	/**
	* Returns the discount user uuid of this commerce discount usage entry.
	*
	* @return the discount user uuid of this commerce discount usage entry
	*/
	@Override
	public String getDiscountUserUuid() {
		return _commerceDiscountUsageEntry.getDiscountUserUuid();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountUsageEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce discount usage entry.
	*
	* @return the group ID of this commerce discount usage entry
	*/
	@Override
	public long getGroupId() {
		return _commerceDiscountUsageEntry.getGroupId();
	}

	/**
	* Returns the modified date of this commerce discount usage entry.
	*
	* @return the modified date of this commerce discount usage entry
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountUsageEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce discount usage entry.
	*
	* @return the primary key of this commerce discount usage entry
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountUsageEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountUsageEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce discount usage entry.
	*
	* @return the user ID of this commerce discount usage entry
	*/
	@Override
	public long getUserId() {
		return _commerceDiscountUsageEntry.getUserId();
	}

	/**
	* Returns the user name of this commerce discount usage entry.
	*
	* @return the user name of this commerce discount usage entry
	*/
	@Override
	public String getUserName() {
		return _commerceDiscountUsageEntry.getUserName();
	}

	/**
	* Returns the user uuid of this commerce discount usage entry.
	*
	* @return the user uuid of this commerce discount usage entry
	*/
	@Override
	public String getUserUuid() {
		return _commerceDiscountUsageEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountUsageEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountUsageEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountUsageEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountUsageEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountUsageEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountUsageEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce discount ID of this commerce discount usage entry.
	*
	* @param commerceDiscountId the commerce discount ID of this commerce discount usage entry
	*/
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountUsageEntry.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	* Sets the commerce discount usage entry ID of this commerce discount usage entry.
	*
	* @param commerceDiscountUsageEntryId the commerce discount usage entry ID of this commerce discount usage entry
	*/
	@Override
	public void setCommerceDiscountUsageEntryId(
		long commerceDiscountUsageEntryId) {
		_commerceDiscountUsageEntry.setCommerceDiscountUsageEntryId(commerceDiscountUsageEntryId);
	}

	/**
	* Sets the commerce order ID of this commerce discount usage entry.
	*
	* @param commerceOrderId the commerce order ID of this commerce discount usage entry
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceDiscountUsageEntry.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the company ID of this commerce discount usage entry.
	*
	* @param companyId the company ID of this commerce discount usage entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountUsageEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce discount usage entry.
	*
	* @param createDate the create date of this commerce discount usage entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountUsageEntry.setCreateDate(createDate);
	}

	/**
	* Sets the discount organization ID of this commerce discount usage entry.
	*
	* @param discountOrganizationId the discount organization ID of this commerce discount usage entry
	*/
	@Override
	public void setDiscountOrganizationId(long discountOrganizationId) {
		_commerceDiscountUsageEntry.setDiscountOrganizationId(discountOrganizationId);
	}

	/**
	* Sets the discount user ID of this commerce discount usage entry.
	*
	* @param discountUserId the discount user ID of this commerce discount usage entry
	*/
	@Override
	public void setDiscountUserId(long discountUserId) {
		_commerceDiscountUsageEntry.setDiscountUserId(discountUserId);
	}

	/**
	* Sets the discount user uuid of this commerce discount usage entry.
	*
	* @param discountUserUuid the discount user uuid of this commerce discount usage entry
	*/
	@Override
	public void setDiscountUserUuid(String discountUserUuid) {
		_commerceDiscountUsageEntry.setDiscountUserUuid(discountUserUuid);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceDiscountUsageEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountUsageEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountUsageEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce discount usage entry.
	*
	* @param groupId the group ID of this commerce discount usage entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceDiscountUsageEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce discount usage entry.
	*
	* @param modifiedDate the modified date of this commerce discount usage entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountUsageEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountUsageEntry.setNew(n);
	}

	/**
	* Sets the primary key of this commerce discount usage entry.
	*
	* @param primaryKey the primary key of this commerce discount usage entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountUsageEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountUsageEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce discount usage entry.
	*
	* @param userId the user ID of this commerce discount usage entry
	*/
	@Override
	public void setUserId(long userId) {
		_commerceDiscountUsageEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce discount usage entry.
	*
	* @param userName the user name of this commerce discount usage entry
	*/
	@Override
	public void setUserName(String userName) {
		_commerceDiscountUsageEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce discount usage entry.
	*
	* @param userUuid the user uuid of this commerce discount usage entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDiscountUsageEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceDiscountUsageEntry> toCacheModel() {
		return _commerceDiscountUsageEntry.toCacheModel();
	}

	@Override
	public CommerceDiscountUsageEntry toEscapedModel() {
		return new CommerceDiscountUsageEntryWrapper(_commerceDiscountUsageEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDiscountUsageEntry.toString();
	}

	@Override
	public CommerceDiscountUsageEntry toUnescapedModel() {
		return new CommerceDiscountUsageEntryWrapper(_commerceDiscountUsageEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDiscountUsageEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountUsageEntryWrapper)) {
			return false;
		}

		CommerceDiscountUsageEntryWrapper commerceDiscountUsageEntryWrapper = (CommerceDiscountUsageEntryWrapper)obj;

		if (Objects.equals(_commerceDiscountUsageEntry,
					commerceDiscountUsageEntryWrapper._commerceDiscountUsageEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceDiscountUsageEntry getWrappedModel() {
		return _commerceDiscountUsageEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountUsageEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountUsageEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountUsageEntry.resetOriginalValues();
	}

	private final CommerceDiscountUsageEntry _commerceDiscountUsageEntry;
}