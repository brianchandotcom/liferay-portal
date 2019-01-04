/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link JournalFeed}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeed
 * @generated
 */
@ProviderType
public class JournalFeedWrapper extends BaseModelWrapper<JournalFeed>
	implements JournalFeed, ModelWrapper<JournalFeed> {
	public JournalFeedWrapper(JournalFeed journalFeed) {
		super(journalFeed);
	}

	/**
	* Returns the company ID of this journal feed.
	*
	* @return the company ID of this journal feed
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the content field of this journal feed.
	*
	* @return the content field of this journal feed
	*/
	@Override
	public String getContentField() {
		return model.getContentField();
	}

	/**
	* Returns the create date of this journal feed.
	*
	* @return the create date of this journal feed
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the ddm renderer template key of this journal feed.
	*
	* @return the ddm renderer template key of this journal feed
	*/
	@Override
	public String getDDMRendererTemplateKey() {
		return model.getDDMRendererTemplateKey();
	}

	/**
	* Returns the ddm structure key of this journal feed.
	*
	* @return the ddm structure key of this journal feed
	*/
	@Override
	public String getDDMStructureKey() {
		return model.getDDMStructureKey();
	}

	/**
	* Returns the ddm template key of this journal feed.
	*
	* @return the ddm template key of this journal feed
	*/
	@Override
	public String getDDMTemplateKey() {
		return model.getDDMTemplateKey();
	}

	/**
	* Returns the delta of this journal feed.
	*
	* @return the delta of this journal feed
	*/
	@Override
	public int getDelta() {
		return model.getDelta();
	}

	/**
	* Returns the description of this journal feed.
	*
	* @return the description of this journal feed
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the feed format of this journal feed.
	*
	* @return the feed format of this journal feed
	*/
	@Override
	public String getFeedFormat() {
		return model.getFeedFormat();
	}

	/**
	* Returns the feed ID of this journal feed.
	*
	* @return the feed ID of this journal feed
	*/
	@Override
	public String getFeedId() {
		return model.getFeedId();
	}

	/**
	* Returns the feed version of this journal feed.
	*
	* @return the feed version of this journal feed
	*/
	@Override
	public double getFeedVersion() {
		return model.getFeedVersion();
	}

	/**
	* Returns the group ID of this journal feed.
	*
	* @return the group ID of this journal feed
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the ID of this journal feed.
	*
	* @return the ID of this journal feed
	*/
	@Override
	public long getId() {
		return model.getId();
	}

	/**
	* Returns the last publish date of this journal feed.
	*
	* @return the last publish date of this journal feed
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this journal feed.
	*
	* @return the modified date of this journal feed
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this journal feed.
	*
	* @return the name of this journal feed
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the order by col of this journal feed.
	*
	* @return the order by col of this journal feed
	*/
	@Override
	public String getOrderByCol() {
		return model.getOrderByCol();
	}

	/**
	* Returns the order by type of this journal feed.
	*
	* @return the order by type of this journal feed
	*/
	@Override
	public String getOrderByType() {
		return model.getOrderByType();
	}

	/**
	* Returns the primary key of this journal feed.
	*
	* @return the primary key of this journal feed
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#getDDMRendererTemplateKey()}
	*/
	@Deprecated
	@Override
	public String getRendererTemplateId() {
		return model.getRendererTemplateId();
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#getDDMStructureKey()}
	*/
	@Deprecated
	@Override
	public String getStructureId() {
		return model.getStructureId();
	}

	/**
	* Returns the target layout friendly url of this journal feed.
	*
	* @return the target layout friendly url of this journal feed
	*/
	@Override
	public String getTargetLayoutFriendlyUrl() {
		return model.getTargetLayoutFriendlyUrl();
	}

	/**
	* Returns the target portlet ID of this journal feed.
	*
	* @return the target portlet ID of this journal feed
	*/
	@Override
	public String getTargetPortletId() {
		return model.getTargetPortletId();
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#getDDMTemplateKey()}
	*/
	@Deprecated
	@Override
	public String getTemplateId() {
		return model.getTemplateId();
	}

	/**
	* Returns the user ID of this journal feed.
	*
	* @return the user ID of this journal feed
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this journal feed.
	*
	* @return the user name of this journal feed
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this journal feed.
	*
	* @return the user uuid of this journal feed
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this journal feed.
	*
	* @return the uuid of this journal feed
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
	* Sets the company ID of this journal feed.
	*
	* @param companyId the company ID of this journal feed
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the content field of this journal feed.
	*
	* @param contentField the content field of this journal feed
	*/
	@Override
	public void setContentField(String contentField) {
		model.setContentField(contentField);
	}

	/**
	* Sets the create date of this journal feed.
	*
	* @param createDate the create date of this journal feed
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the ddm renderer template key of this journal feed.
	*
	* @param DDMRendererTemplateKey the ddm renderer template key of this journal feed
	*/
	@Override
	public void setDDMRendererTemplateKey(String DDMRendererTemplateKey) {
		model.setDDMRendererTemplateKey(DDMRendererTemplateKey);
	}

	/**
	* Sets the ddm structure key of this journal feed.
	*
	* @param DDMStructureKey the ddm structure key of this journal feed
	*/
	@Override
	public void setDDMStructureKey(String DDMStructureKey) {
		model.setDDMStructureKey(DDMStructureKey);
	}

	/**
	* Sets the ddm template key of this journal feed.
	*
	* @param DDMTemplateKey the ddm template key of this journal feed
	*/
	@Override
	public void setDDMTemplateKey(String DDMTemplateKey) {
		model.setDDMTemplateKey(DDMTemplateKey);
	}

	/**
	* Sets the delta of this journal feed.
	*
	* @param delta the delta of this journal feed
	*/
	@Override
	public void setDelta(int delta) {
		model.setDelta(delta);
	}

	/**
	* Sets the description of this journal feed.
	*
	* @param description the description of this journal feed
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the feed format of this journal feed.
	*
	* @param feedFormat the feed format of this journal feed
	*/
	@Override
	public void setFeedFormat(String feedFormat) {
		model.setFeedFormat(feedFormat);
	}

	/**
	* Sets the feed ID of this journal feed.
	*
	* @param feedId the feed ID of this journal feed
	*/
	@Override
	public void setFeedId(String feedId) {
		model.setFeedId(feedId);
	}

	/**
	* Sets the feed version of this journal feed.
	*
	* @param feedVersion the feed version of this journal feed
	*/
	@Override
	public void setFeedVersion(double feedVersion) {
		model.setFeedVersion(feedVersion);
	}

	/**
	* Sets the group ID of this journal feed.
	*
	* @param groupId the group ID of this journal feed
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the ID of this journal feed.
	*
	* @param id the ID of this journal feed
	*/
	@Override
	public void setId(long id) {
		model.setId(id);
	}

	/**
	* Sets the last publish date of this journal feed.
	*
	* @param lastPublishDate the last publish date of this journal feed
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this journal feed.
	*
	* @param modifiedDate the modified date of this journal feed
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this journal feed.
	*
	* @param name the name of this journal feed
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the order by col of this journal feed.
	*
	* @param orderByCol the order by col of this journal feed
	*/
	@Override
	public void setOrderByCol(String orderByCol) {
		model.setOrderByCol(orderByCol);
	}

	/**
	* Sets the order by type of this journal feed.
	*
	* @param orderByType the order by type of this journal feed
	*/
	@Override
	public void setOrderByType(String orderByType) {
		model.setOrderByType(orderByType);
	}

	/**
	* Sets the primary key of this journal feed.
	*
	* @param primaryKey the primary key of this journal feed
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#setDDMRendererTemplateKey(String)}
	*/
	@Deprecated
	@Override
	public void setRendererTemplateId(String rendererTemplateKey) {
		model.setRendererTemplateId(rendererTemplateKey);
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#setDDMStructureKey(String)}
	*/
	@Deprecated
	@Override
	public void setStructureId(String structureKey) {
		model.setStructureId(structureKey);
	}

	/**
	* Sets the target layout friendly url of this journal feed.
	*
	* @param targetLayoutFriendlyUrl the target layout friendly url of this journal feed
	*/
	@Override
	public void setTargetLayoutFriendlyUrl(String targetLayoutFriendlyUrl) {
		model.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
	}

	/**
	* Sets the target portlet ID of this journal feed.
	*
	* @param targetPortletId the target portlet ID of this journal feed
	*/
	@Override
	public void setTargetPortletId(String targetPortletId) {
		model.setTargetPortletId(targetPortletId);
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#setDDMTemplateKey(String)}
	*/
	@Deprecated
	@Override
	public void setTemplateId(String templateKey) {
		model.setTemplateId(templateKey);
	}

	/**
	* Sets the user ID of this journal feed.
	*
	* @param userId the user ID of this journal feed
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this journal feed.
	*
	* @param userName the user name of this journal feed
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this journal feed.
	*
	* @param userUuid the user uuid of this journal feed
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this journal feed.
	*
	* @param uuid the uuid of this journal feed
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected JournalFeedWrapper wrap(JournalFeed journalFeed) {
		return new JournalFeedWrapper(journalFeed);
	}
}