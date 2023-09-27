/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

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
 * This class is a wrapper for {@link LayoutPageTemplateCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollection
 * @generated
 */
public class LayoutPageTemplateCollectionWrapper
	extends BaseModelWrapper<LayoutPageTemplateCollection>
	implements LayoutPageTemplateCollection,
			   ModelWrapper<LayoutPageTemplateCollection> {

	public LayoutPageTemplateCollectionWrapper(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		super(layoutPageTemplateCollection);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"layoutPageTemplateCollectionId",
			getLayoutPageTemplateCollectionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"parentLayoutPageTemplateCollectionId",
			getParentLayoutPageTemplateCollectionId());
		attributes.put(
			"layoutPageTemplateCollectionKey",
			getLayoutPageTemplateCollectionKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
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

		Long layoutPageTemplateCollectionId = (Long)attributes.get(
			"layoutPageTemplateCollectionId");

		if (layoutPageTemplateCollectionId != null) {
			setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
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

		Long parentLayoutPageTemplateCollectionId = (Long)attributes.get(
			"parentLayoutPageTemplateCollectionId");

		if (parentLayoutPageTemplateCollectionId != null) {
			setParentLayoutPageTemplateCollectionId(
				parentLayoutPageTemplateCollectionId);
		}

		String layoutPageTemplateCollectionKey = (String)attributes.get(
			"layoutPageTemplateCollectionKey");

		if (layoutPageTemplateCollectionKey != null) {
			setLayoutPageTemplateCollectionKey(layoutPageTemplateCollectionKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public LayoutPageTemplateCollection cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public LayoutPageTemplateCollection getAncestor() {
		return model.getAncestor();
	}

	@Override
	public java.util.List<LayoutPageTemplateCollection> getAncestors() {
		return model.getAncestors();
	}

	/**
	 * Returns the company ID of this layout page template collection.
	 *
	 * @return the company ID of this layout page template collection
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout page template collection.
	 *
	 * @return the create date of this layout page template collection
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this layout page template collection.
	 *
	 * @return the ct collection ID of this layout page template collection
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this layout page template collection.
	 *
	 * @return the description of this layout page template collection
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this layout page template collection.
	 *
	 * @return the group ID of this layout page template collection
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this layout page template collection.
	 *
	 * @return the last publish date of this layout page template collection
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the layout page template collection ID of this layout page template collection.
	 *
	 * @return the layout page template collection ID of this layout page template collection
	 */
	@Override
	public long getLayoutPageTemplateCollectionId() {
		return model.getLayoutPageTemplateCollectionId();
	}

	/**
	 * Returns the layout page template collection key of this layout page template collection.
	 *
	 * @return the layout page template collection key of this layout page template collection
	 */
	@Override
	public String getLayoutPageTemplateCollectionKey() {
		return model.getLayoutPageTemplateCollectionKey();
	}

	/**
	 * Returns the modified date of this layout page template collection.
	 *
	 * @return the modified date of this layout page template collection
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout page template collection.
	 *
	 * @return the mvcc version of this layout page template collection
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this layout page template collection.
	 *
	 * @return the name of this layout page template collection
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent layout page template collection ID of this layout page template collection.
	 *
	 * @return the parent layout page template collection ID of this layout page template collection
	 */
	@Override
	public long getParentLayoutPageTemplateCollectionId() {
		return model.getParentLayoutPageTemplateCollectionId();
	}

	/**
	 * Returns the primary key of this layout page template collection.
	 *
	 * @return the primary key of this layout page template collection
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this layout page template collection.
	 *
	 * @return the type of this layout page template collection
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this layout page template collection.
	 *
	 * @return the user ID of this layout page template collection
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout page template collection.
	 *
	 * @return the user name of this layout page template collection
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout page template collection.
	 *
	 * @return the user uuid of this layout page template collection
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout page template collection.
	 *
	 * @return the uuid of this layout page template collection
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this layout page template collection.
	 *
	 * @param companyId the company ID of this layout page template collection
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout page template collection.
	 *
	 * @param createDate the create date of this layout page template collection
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this layout page template collection.
	 *
	 * @param ctCollectionId the ct collection ID of this layout page template collection
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this layout page template collection.
	 *
	 * @param description the description of this layout page template collection
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this layout page template collection.
	 *
	 * @param groupId the group ID of this layout page template collection
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this layout page template collection.
	 *
	 * @param lastPublishDate the last publish date of this layout page template collection
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the layout page template collection ID of this layout page template collection.
	 *
	 * @param layoutPageTemplateCollectionId the layout page template collection ID of this layout page template collection
	 */
	@Override
	public void setLayoutPageTemplateCollectionId(
		long layoutPageTemplateCollectionId) {

		model.setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
	}

	/**
	 * Sets the layout page template collection key of this layout page template collection.
	 *
	 * @param layoutPageTemplateCollectionKey the layout page template collection key of this layout page template collection
	 */
	@Override
	public void setLayoutPageTemplateCollectionKey(
		String layoutPageTemplateCollectionKey) {

		model.setLayoutPageTemplateCollectionKey(
			layoutPageTemplateCollectionKey);
	}

	/**
	 * Sets the modified date of this layout page template collection.
	 *
	 * @param modifiedDate the modified date of this layout page template collection
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout page template collection.
	 *
	 * @param mvccVersion the mvcc version of this layout page template collection
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this layout page template collection.
	 *
	 * @param name the name of this layout page template collection
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent layout page template collection ID of this layout page template collection.
	 *
	 * @param parentLayoutPageTemplateCollectionId the parent layout page template collection ID of this layout page template collection
	 */
	@Override
	public void setParentLayoutPageTemplateCollectionId(
		long parentLayoutPageTemplateCollectionId) {

		model.setParentLayoutPageTemplateCollectionId(
			parentLayoutPageTemplateCollectionId);
	}

	/**
	 * Sets the primary key of this layout page template collection.
	 *
	 * @param primaryKey the primary key of this layout page template collection
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this layout page template collection.
	 *
	 * @param type the type of this layout page template collection
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this layout page template collection.
	 *
	 * @param userId the user ID of this layout page template collection
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout page template collection.
	 *
	 * @param userName the user name of this layout page template collection
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout page template collection.
	 *
	 * @param userUuid the user uuid of this layout page template collection
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout page template collection.
	 *
	 * @param uuid the uuid of this layout page template collection
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
	public Map<String, Function<LayoutPageTemplateCollection, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<LayoutPageTemplateCollection, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected LayoutPageTemplateCollectionWrapper wrap(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return new LayoutPageTemplateCollectionWrapper(
			layoutPageTemplateCollection);
	}

}