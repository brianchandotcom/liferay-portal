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

package com.liferay.fragment.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FragmentEntryLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLink
 * @generated
 */
public class FragmentEntryLinkWrapper
	extends BaseModelWrapper<FragmentEntryLink>
	implements FragmentEntryLink, ModelWrapper<FragmentEntryLink> {

	public FragmentEntryLinkWrapper(FragmentEntryLink fragmentEntryLink) {
		super(fragmentEntryLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("fragmentEntryLinkId", getFragmentEntryLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"originalFragmentEntryLinkId", getOriginalFragmentEntryLinkId());
		attributes.put("fragmentEntryId", getFragmentEntryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("css", getCss());
		attributes.put("html", getHtml());
		attributes.put("js", getJs());
		attributes.put("configuration", getConfiguration());
		attributes.put("editableValues", getEditableValues());
		attributes.put("namespace", getNamespace());
		attributes.put("position", getPosition());
		attributes.put("rendererKey", getRendererKey());
		attributes.put("lastPropagationDate", getLastPropagationDate());
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

		Long fragmentEntryLinkId = (Long)attributes.get("fragmentEntryLinkId");

		if (fragmentEntryLinkId != null) {
			setFragmentEntryLinkId(fragmentEntryLinkId);
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

		Long originalFragmentEntryLinkId = (Long)attributes.get(
			"originalFragmentEntryLinkId");

		if (originalFragmentEntryLinkId != null) {
			setOriginalFragmentEntryLinkId(originalFragmentEntryLinkId);
		}

		Long fragmentEntryId = (Long)attributes.get("fragmentEntryId");

		if (fragmentEntryId != null) {
			setFragmentEntryId(fragmentEntryId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		String html = (String)attributes.get("html");

		if (html != null) {
			setHtml(html);
		}

		String js = (String)attributes.get("js");

		if (js != null) {
			setJs(js);
		}

		String configuration = (String)attributes.get("configuration");

		if (configuration != null) {
			setConfiguration(configuration);
		}

		String editableValues = (String)attributes.get("editableValues");

		if (editableValues != null) {
			setEditableValues(editableValues);
		}

		String namespace = (String)attributes.get("namespace");

		if (namespace != null) {
			setNamespace(namespace);
		}

		Integer position = (Integer)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}

		String rendererKey = (String)attributes.get("rendererKey");

		if (rendererKey != null) {
			setRendererKey(rendererKey);
		}

		Date lastPropagationDate = (Date)attributes.get("lastPropagationDate");

		if (lastPropagationDate != null) {
			setLastPropagationDate(lastPropagationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the fully qualified class name of this fragment entry link.
	 *
	 * @return the fully qualified class name of this fragment entry link
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this fragment entry link.
	 *
	 * @return the class name ID of this fragment entry link
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this fragment entry link.
	 *
	 * @return the class pk of this fragment entry link
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this fragment entry link.
	 *
	 * @return the company ID of this fragment entry link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the configuration of this fragment entry link.
	 *
	 * @return the configuration of this fragment entry link
	 */
	@Override
	public String getConfiguration() {
		return model.getConfiguration();
	}

	/**
	 * Returns the create date of this fragment entry link.
	 *
	 * @return the create date of this fragment entry link
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the css of this fragment entry link.
	 *
	 * @return the css of this fragment entry link
	 */
	@Override
	public String getCss() {
		return model.getCss();
	}

	/**
	 * Returns the editable values of this fragment entry link.
	 *
	 * @return the editable values of this fragment entry link
	 */
	@Override
	public String getEditableValues() {
		return model.getEditableValues();
	}

	/**
	 * Returns the fragment entry ID of this fragment entry link.
	 *
	 * @return the fragment entry ID of this fragment entry link
	 */
	@Override
	public long getFragmentEntryId() {
		return model.getFragmentEntryId();
	}

	/**
	 * Returns the fragment entry link ID of this fragment entry link.
	 *
	 * @return the fragment entry link ID of this fragment entry link
	 */
	@Override
	public long getFragmentEntryLinkId() {
		return model.getFragmentEntryLinkId();
	}

	/**
	 * Returns the group ID of this fragment entry link.
	 *
	 * @return the group ID of this fragment entry link
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the html of this fragment entry link.
	 *
	 * @return the html of this fragment entry link
	 */
	@Override
	public String getHtml() {
		return model.getHtml();
	}

	/**
	 * Returns the js of this fragment entry link.
	 *
	 * @return the js of this fragment entry link
	 */
	@Override
	public String getJs() {
		return model.getJs();
	}

	/**
	 * Returns the last propagation date of this fragment entry link.
	 *
	 * @return the last propagation date of this fragment entry link
	 */
	@Override
	public Date getLastPropagationDate() {
		return model.getLastPropagationDate();
	}

	/**
	 * Returns the last publish date of this fragment entry link.
	 *
	 * @return the last publish date of this fragment entry link
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this fragment entry link.
	 *
	 * @return the modified date of this fragment entry link
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fragment entry link.
	 *
	 * @return the mvcc version of this fragment entry link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the namespace of this fragment entry link.
	 *
	 * @return the namespace of this fragment entry link
	 */
	@Override
	public String getNamespace() {
		return model.getNamespace();
	}

	/**
	 * Returns the original fragment entry link ID of this fragment entry link.
	 *
	 * @return the original fragment entry link ID of this fragment entry link
	 */
	@Override
	public long getOriginalFragmentEntryLinkId() {
		return model.getOriginalFragmentEntryLinkId();
	}

	/**
	 * Returns the position of this fragment entry link.
	 *
	 * @return the position of this fragment entry link
	 */
	@Override
	public int getPosition() {
		return model.getPosition();
	}

	/**
	 * Returns the primary key of this fragment entry link.
	 *
	 * @return the primary key of this fragment entry link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the renderer key of this fragment entry link.
	 *
	 * @return the renderer key of this fragment entry link
	 */
	@Override
	public String getRendererKey() {
		return model.getRendererKey();
	}

	/**
	 * Returns the user ID of this fragment entry link.
	 *
	 * @return the user ID of this fragment entry link
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fragment entry link.
	 *
	 * @return the user name of this fragment entry link
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fragment entry link.
	 *
	 * @return the user uuid of this fragment entry link
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fragment entry link.
	 *
	 * @return the uuid of this fragment entry link
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean isLatestVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isLatestVersion();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a fragment entry link model instance should use the <code>FragmentEntryLink</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this fragment entry link.
	 *
	 * @param classNameId the class name ID of this fragment entry link
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this fragment entry link.
	 *
	 * @param classPK the class pk of this fragment entry link
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this fragment entry link.
	 *
	 * @param companyId the company ID of this fragment entry link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the configuration of this fragment entry link.
	 *
	 * @param configuration the configuration of this fragment entry link
	 */
	@Override
	public void setConfiguration(String configuration) {
		model.setConfiguration(configuration);
	}

	/**
	 * Sets the create date of this fragment entry link.
	 *
	 * @param createDate the create date of this fragment entry link
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the css of this fragment entry link.
	 *
	 * @param css the css of this fragment entry link
	 */
	@Override
	public void setCss(String css) {
		model.setCss(css);
	}

	/**
	 * Sets the editable values of this fragment entry link.
	 *
	 * @param editableValues the editable values of this fragment entry link
	 */
	@Override
	public void setEditableValues(String editableValues) {
		model.setEditableValues(editableValues);
	}

	/**
	 * Sets the fragment entry ID of this fragment entry link.
	 *
	 * @param fragmentEntryId the fragment entry ID of this fragment entry link
	 */
	@Override
	public void setFragmentEntryId(long fragmentEntryId) {
		model.setFragmentEntryId(fragmentEntryId);
	}

	/**
	 * Sets the fragment entry link ID of this fragment entry link.
	 *
	 * @param fragmentEntryLinkId the fragment entry link ID of this fragment entry link
	 */
	@Override
	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		model.setFragmentEntryLinkId(fragmentEntryLinkId);
	}

	/**
	 * Sets the group ID of this fragment entry link.
	 *
	 * @param groupId the group ID of this fragment entry link
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the html of this fragment entry link.
	 *
	 * @param html the html of this fragment entry link
	 */
	@Override
	public void setHtml(String html) {
		model.setHtml(html);
	}

	/**
	 * Sets the js of this fragment entry link.
	 *
	 * @param js the js of this fragment entry link
	 */
	@Override
	public void setJs(String js) {
		model.setJs(js);
	}

	/**
	 * Sets the last propagation date of this fragment entry link.
	 *
	 * @param lastPropagationDate the last propagation date of this fragment entry link
	 */
	@Override
	public void setLastPropagationDate(Date lastPropagationDate) {
		model.setLastPropagationDate(lastPropagationDate);
	}

	/**
	 * Sets the last publish date of this fragment entry link.
	 *
	 * @param lastPublishDate the last publish date of this fragment entry link
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this fragment entry link.
	 *
	 * @param modifiedDate the modified date of this fragment entry link
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fragment entry link.
	 *
	 * @param mvccVersion the mvcc version of this fragment entry link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the namespace of this fragment entry link.
	 *
	 * @param namespace the namespace of this fragment entry link
	 */
	@Override
	public void setNamespace(String namespace) {
		model.setNamespace(namespace);
	}

	/**
	 * Sets the original fragment entry link ID of this fragment entry link.
	 *
	 * @param originalFragmentEntryLinkId the original fragment entry link ID of this fragment entry link
	 */
	@Override
	public void setOriginalFragmentEntryLinkId(
		long originalFragmentEntryLinkId) {

		model.setOriginalFragmentEntryLinkId(originalFragmentEntryLinkId);
	}

	/**
	 * Sets the position of this fragment entry link.
	 *
	 * @param position the position of this fragment entry link
	 */
	@Override
	public void setPosition(int position) {
		model.setPosition(position);
	}

	/**
	 * Sets the primary key of this fragment entry link.
	 *
	 * @param primaryKey the primary key of this fragment entry link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the renderer key of this fragment entry link.
	 *
	 * @param rendererKey the renderer key of this fragment entry link
	 */
	@Override
	public void setRendererKey(String rendererKey) {
		model.setRendererKey(rendererKey);
	}

	/**
	 * Sets the user ID of this fragment entry link.
	 *
	 * @param userId the user ID of this fragment entry link
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fragment entry link.
	 *
	 * @param userName the user name of this fragment entry link
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fragment entry link.
	 *
	 * @param userUuid the user uuid of this fragment entry link
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fragment entry link.
	 *
	 * @param uuid the uuid of this fragment entry link
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
	protected FragmentEntryLinkWrapper wrap(
		FragmentEntryLink fragmentEntryLink) {

		return new FragmentEntryLinkWrapper(fragmentEntryLink);
	}

}