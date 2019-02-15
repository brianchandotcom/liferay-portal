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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersion
 * @generated
 */
@ProviderType
public class LayoutSetVersionWrapper extends BaseModelWrapper<LayoutSetVersion>
	implements LayoutSetVersion, ModelWrapper<LayoutSetVersion> {
	public LayoutSetVersionWrapper(LayoutSetVersion layoutSetVersion) {
		super(layoutSetVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutSetVersionId", getLayoutSetVersionId());
		attributes.put("version", getVersion());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", isPrivateLayout());
		attributes.put("logoId", getLogoId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("pageCount", getPageCount());
		attributes.put("settings", getSettings());
		attributes.put("layoutSetPrototypeUuid", getLayoutSetPrototypeUuid());
		attributes.put("layoutSetPrototypeLinkEnabled",
			isLayoutSetPrototypeLinkEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutSetVersionId = (Long)attributes.get("layoutSetVersionId");

		if (layoutSetVersionId != null) {
			setLayoutSetVersionId(layoutSetVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long layoutSetId = (Long)attributes.get("layoutSetId");

		if (layoutSetId != null) {
			setLayoutSetId(layoutSetId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String themeId = (String)attributes.get("themeId");

		if (themeId != null) {
			setThemeId(themeId);
		}

		String colorSchemeId = (String)attributes.get("colorSchemeId");

		if (colorSchemeId != null) {
			setColorSchemeId(colorSchemeId);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		Integer pageCount = (Integer)attributes.get("pageCount");

		if (pageCount != null) {
			setPageCount(pageCount);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		String layoutSetPrototypeUuid = (String)attributes.get(
				"layoutSetPrototypeUuid");

		if (layoutSetPrototypeUuid != null) {
			setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		Boolean layoutSetPrototypeLinkEnabled = (Boolean)attributes.get(
				"layoutSetPrototypeLinkEnabled");

		if (layoutSetPrototypeLinkEnabled != null) {
			setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
		}
	}

	/**
	* Returns the color scheme ID of this layout set version.
	*
	* @return the color scheme ID of this layout set version
	*/
	@Override
	public String getColorSchemeId() {
		return model.getColorSchemeId();
	}

	/**
	* Returns the company ID of this layout set version.
	*
	* @return the company ID of this layout set version
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this layout set version.
	*
	* @return the create date of this layout set version
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the css of this layout set version.
	*
	* @return the css of this layout set version
	*/
	@Override
	public String getCss() {
		return model.getCss();
	}

	/**
	* Returns the group ID of this layout set version.
	*
	* @return the group ID of this layout set version
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the layout set ID of this layout set version.
	*
	* @return the layout set ID of this layout set version
	*/
	@Override
	public long getLayoutSetId() {
		return model.getLayoutSetId();
	}

	/**
	* Returns the layout set prototype link enabled of this layout set version.
	*
	* @return the layout set prototype link enabled of this layout set version
	*/
	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return model.getLayoutSetPrototypeLinkEnabled();
	}

	/**
	* Returns the layout set prototype uuid of this layout set version.
	*
	* @return the layout set prototype uuid of this layout set version
	*/
	@Override
	public String getLayoutSetPrototypeUuid() {
		return model.getLayoutSetPrototypeUuid();
	}

	/**
	* Returns the layout set version ID of this layout set version.
	*
	* @return the layout set version ID of this layout set version
	*/
	@Override
	public long getLayoutSetVersionId() {
		return model.getLayoutSetVersionId();
	}

	/**
	* Returns the logo ID of this layout set version.
	*
	* @return the logo ID of this layout set version
	*/
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	* Returns the modified date of this layout set version.
	*
	* @return the modified date of this layout set version
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the page count of this layout set version.
	*
	* @return the page count of this layout set version
	*/
	@Override
	public int getPageCount() {
		return model.getPageCount();
	}

	/**
	* Returns the primary key of this layout set version.
	*
	* @return the primary key of this layout set version
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the private layout of this layout set version.
	*
	* @return the private layout of this layout set version
	*/
	@Override
	public boolean getPrivateLayout() {
		return model.getPrivateLayout();
	}

	/**
	* Returns the settings of this layout set version.
	*
	* @return the settings of this layout set version
	*/
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	/**
	* Returns the theme ID of this layout set version.
	*
	* @return the theme ID of this layout set version
	*/
	@Override
	public String getThemeId() {
		return model.getThemeId();
	}

	/**
	* Returns the version of this layout set version.
	*
	* @return the version of this layout set version
	*/
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	* Returns <code>true</code> if this layout set version is layout set prototype link enabled.
	*
	* @return <code>true</code> if this layout set version is layout set prototype link enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isLayoutSetPrototypeLinkEnabled() {
		return model.isLayoutSetPrototypeLinkEnabled();
	}

	/**
	* Returns <code>true</code> if this layout set version is private layout.
	*
	* @return <code>true</code> if this layout set version is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return model.isPrivateLayout();
	}

	/**
	* Sets the color scheme ID of this layout set version.
	*
	* @param colorSchemeId the color scheme ID of this layout set version
	*/
	@Override
	public void setColorSchemeId(String colorSchemeId) {
		model.setColorSchemeId(colorSchemeId);
	}

	/**
	* Sets the company ID of this layout set version.
	*
	* @param companyId the company ID of this layout set version
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout set version.
	*
	* @param createDate the create date of this layout set version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the css of this layout set version.
	*
	* @param css the css of this layout set version
	*/
	@Override
	public void setCss(String css) {
		model.setCss(css);
	}

	/**
	* Sets the group ID of this layout set version.
	*
	* @param groupId the group ID of this layout set version
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the layout set ID of this layout set version.
	*
	* @param layoutSetId the layout set ID of this layout set version
	*/
	@Override
	public void setLayoutSetId(long layoutSetId) {
		model.setLayoutSetId(layoutSetId);
	}

	/**
	* Sets whether this layout set version is layout set prototype link enabled.
	*
	* @param layoutSetPrototypeLinkEnabled the layout set prototype link enabled of this layout set version
	*/
	@Override
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {
		model.setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
	}

	/**
	* Sets the layout set prototype uuid of this layout set version.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid of this layout set version
	*/
	@Override
	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		model.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Sets the layout set version ID of this layout set version.
	*
	* @param layoutSetVersionId the layout set version ID of this layout set version
	*/
	@Override
	public void setLayoutSetVersionId(long layoutSetVersionId) {
		model.setLayoutSetVersionId(layoutSetVersionId);
	}

	/**
	* Sets the logo ID of this layout set version.
	*
	* @param logoId the logo ID of this layout set version
	*/
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	* Sets the modified date of this layout set version.
	*
	* @param modifiedDate the modified date of this layout set version
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the page count of this layout set version.
	*
	* @param pageCount the page count of this layout set version
	*/
	@Override
	public void setPageCount(int pageCount) {
		model.setPageCount(pageCount);
	}

	/**
	* Sets the primary key of this layout set version.
	*
	* @param primaryKey the primary key of this layout set version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets whether this layout set version is private layout.
	*
	* @param privateLayout the private layout of this layout set version
	*/
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		model.setPrivateLayout(privateLayout);
	}

	/**
	* Sets the settings of this layout set version.
	*
	* @param settings the settings of this layout set version
	*/
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	* Sets the theme ID of this layout set version.
	*
	* @param themeId the theme ID of this layout set version
	*/
	@Override
	public void setThemeId(String themeId) {
		model.setThemeId(themeId);
	}

	/**
	* Sets the version of this layout set version.
	*
	* @param version the version of this layout set version
	*/
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public long getVersionedModelId() {
		return model.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		model.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(LayoutSet layoutSet) {
		model.populateVersionedModel(layoutSet);
	}

	@Override
	public LayoutSet toVersionedModel() {
		return model.toVersionedModel();
	}

	@Override
	protected LayoutSetVersionWrapper wrap(LayoutSetVersion layoutSetVersion) {
		return new LayoutSetVersionWrapper(layoutSetVersion);
	}
}