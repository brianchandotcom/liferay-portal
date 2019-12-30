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

package com.liferay.opensocial.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Gadget}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Gadget
 * @generated
 */
public class GadgetWrapper
	extends BaseModelWrapper<Gadget> implements Gadget, ModelWrapper<Gadget> {

	public GadgetWrapper(Gadget gadget) {
		super(gadget);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("gadgetId", getGadgetId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("url", getUrl());
		attributes.put("portletCategoryNames", getPortletCategoryNames());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long gadgetId = (Long)attributes.get("gadgetId");

		if (gadgetId != null) {
			setGadgetId(gadgetId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String portletCategoryNames = (String)attributes.get(
			"portletCategoryNames");

		if (portletCategoryNames != null) {
			setPortletCategoryNames(portletCategoryNames);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the company ID of this gadget.
	 *
	 * @return the company ID of this gadget
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this gadget.
	 *
	 * @return the create date of this gadget
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the gadget ID of this gadget.
	 *
	 * @return the gadget ID of this gadget
	 */
	@Override
	public long getGadgetId() {
		return model.getGadgetId();
	}

	/**
	 * Returns the last publish date of this gadget.
	 *
	 * @return the last publish date of this gadget
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this gadget.
	 *
	 * @return the modified date of this gadget
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this gadget.
	 *
	 * @return the name of this gadget
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the portlet category names of this gadget.
	 *
	 * @return the portlet category names of this gadget
	 */
	@Override
	public String getPortletCategoryNames() {
		return model.getPortletCategoryNames();
	}

	/**
	 * Returns the primary key of this gadget.
	 *
	 * @return the primary key of this gadget
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the url of this gadget.
	 *
	 * @return the url of this gadget
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the uuid of this gadget.
	 *
	 * @return the uuid of this gadget
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
	 * Sets the company ID of this gadget.
	 *
	 * @param companyId the company ID of this gadget
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this gadget.
	 *
	 * @param createDate the create date of this gadget
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the gadget ID of this gadget.
	 *
	 * @param gadgetId the gadget ID of this gadget
	 */
	@Override
	public void setGadgetId(long gadgetId) {
		model.setGadgetId(gadgetId);
	}

	/**
	 * Sets the last publish date of this gadget.
	 *
	 * @param lastPublishDate the last publish date of this gadget
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this gadget.
	 *
	 * @param modifiedDate the modified date of this gadget
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this gadget.
	 *
	 * @param name the name of this gadget
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the portlet category names of this gadget.
	 *
	 * @param portletCategoryNames the portlet category names of this gadget
	 */
	@Override
	public void setPortletCategoryNames(String portletCategoryNames) {
		model.setPortletCategoryNames(portletCategoryNames);
	}

	/**
	 * Sets the primary key of this gadget.
	 *
	 * @param primaryKey the primary key of this gadget
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the url of this gadget.
	 *
	 * @param url the url of this gadget
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the uuid of this gadget.
	 *
	 * @param uuid the uuid of this gadget
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
	protected GadgetWrapper wrap(Gadget gadget) {
		return new GadgetWrapper(gadget);
	}

}