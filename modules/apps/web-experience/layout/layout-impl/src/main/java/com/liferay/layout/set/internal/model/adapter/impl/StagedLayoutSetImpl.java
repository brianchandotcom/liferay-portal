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

package com.liferay.layout.set.internal.model.adapter.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.time.Instant;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mate Thurzo
 */
public class StagedLayoutSetImpl implements StagedLayoutSet {

	public StagedLayoutSetImpl() {
	}

	public StagedLayoutSetImpl(LayoutSet layoutSet) {
		Objects.requireNonNull(
			layoutSet,
			"Unable to create a new staged layout set for a null layout set");

		_layoutSet = layoutSet;

		// Last Publish Date

		UnicodeProperties settingsProperties =
			_layoutSet.getSettingsProperties();

		String lastPublishDateString = settingsProperties.getProperty(
			"last-publish-date");

		Instant instant = Instant.ofEpochMilli(
			GetterUtil.getLong(lastPublishDateString));

		_lastPublishDate = Date.from(instant);

		// LayoutSet Prototype

		if (Validator.isNotNull(_layoutSet.getLayoutSetPrototypeUuid())) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						_layoutSet.getLayoutSetPrototypeUuid(),
						_layoutSet.getCompanyId());

			if (layoutSetPrototype != null) {
				_layoutSetPrototypeName = layoutSetPrototype.getName(
					LocaleUtil.getDefault());
			}
		}

		try {
			_userId = _layoutSet.getGroup().getCreatorUserId();

			User user = UserLocalServiceUtil.getUser(_userId);

			_userName = user.getFullName();
			_userUuid = user.getUuid();
		}
		catch (PortalException pe) {
		}
	}

	@Override
	public Object clone() {
		return new StagedLayoutSetImpl((LayoutSet)_layoutSet.clone());
	}

	@Override
	public int compareTo(LayoutSet layoutSet) {
		return _layoutSet.compareTo(layoutSet);
	}

	@Override
	public ColorScheme getColorScheme() {
		return _layoutSet.getColorScheme();
	}

	@Override
	public String getColorSchemeId() {
		return _layoutSet.getColorSchemeId();
	}

	@Override
	public String getCompanyFallbackVirtualHostname() {
		return _layoutSet.getCompanyFallbackVirtualHostname();
	}

	@Override
	public long getCompanyId() {
		return _layoutSet.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _layoutSet.getCreateDate();
	}

	@Override
	public String getCss() {
		return _layoutSet.getCss();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutSet.getExpandoBridge();
	}

	@Override
	public Group getGroup() throws PortalException {
		return _layoutSet.getGroup();
	}

	@Override
	public long getGroupId() {
		return _layoutSet.getGroupId();
	}

	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public long getLayoutSetId() {
		return _layoutSet.getLayoutSetId();
	}

	@Override
	public long getLayoutSetPrototypeId() throws PortalException {
		return _layoutSet.getLayoutSetPrototypeId();
	}

	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSet.getLayoutSetPrototypeLinkEnabled();
	}

	@Override
	public Optional<String> getLayoutSetPrototypeName() {
		return Optional.ofNullable(_layoutSetPrototypeName);
	}

	@Override
	public String getLayoutSetPrototypeUuid() {
		return _layoutSet.getLayoutSetPrototypeUuid();
	}

	@Override
	public long getLiveLogoId() {
		return _layoutSet.getLiveLogoId();
	}

	@Override
	public boolean getLogo() {
		return _layoutSet.getLogo();
	}

	@Override
	public long getLogoId() {
		return _layoutSet.getLogoId();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _layoutSet.getModelAttributes();
	}

	@Override
	public Class<?> getModelClass() {
		return _layoutSet.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _layoutSet.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _layoutSet.getModifiedDate();
	}

	@Override
	public long getMvccVersion() {
		return _layoutSet.getMvccVersion();
	}

	@Override
	public int getPageCount() {
		return _layoutSet.getPageCount();
	}

	@Override
	public long getPrimaryKey() {
		return _layoutSet.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutSet.getPrimaryKeyObj();
	}

	@Override
	public boolean getPrivateLayout() {
		return _layoutSet.getPrivateLayout();
	}

	@Override
	public String getSettings() {
		return _layoutSet.getSettings();
	}

	@Override
	public UnicodeProperties getSettingsProperties() {
		return _layoutSet.getSettingsProperties();
	}

	@Override
	public String getSettingsProperty(String key) {
		return _layoutSet.getSettingsProperty(key);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedLayoutSet.class);
	}

	@Override
	public Theme getTheme() {
		return _layoutSet.getTheme();
	}

	@Override
	public String getThemeId() {
		return _layoutSet.getThemeId();
	}

	@Override
	public String getThemeSetting(String key, String device) {
		return _layoutSet.getThemeSetting(key, device);
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public String getUserUuid() {
		return _userUuid;
	}

	@Override
	public String getUuid() {
		return String.valueOf(_layoutSet.isPrivateLayout());
	}

	@Override
	public String getVirtualHostname() {
		return _layoutSet.getVirtualHostname();
	}

	@Override
	public boolean hasSetModifiedDate() {
		return _layoutSet.hasSetModifiedDate();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutSet.isCachedModel();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutSet.isEntityCacheEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutSet.isEscapedModel();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutSet.isFinderCacheEnabled();
	}

	@Override
	public boolean isLayoutSetPrototypeLinkActive() {
		return _layoutSet.isLayoutSetPrototypeLinkActive();
	}

	@Override
	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSet.isLayoutSetPrototypeLinkEnabled();
	}

	@Override
	public boolean isLogo() {
		return _layoutSet.isLogo();
	}

	@Override
	public boolean isNew() {
		return _layoutSet.isNew();
	}

	@Override
	public boolean isPrivateLayout() {
		return _layoutSet.isPrivateLayout();
	}

	@Override
	public void persist() {
		_layoutSet.persist();
	}

	@Override
	public void resetOriginalValues() {
		_layoutSet.resetOriginalValues();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutSet.setCachedModel(cachedModel);
	}

	@Override
	public void setColorSchemeId(String colorSchemeId) {
		_layoutSet.setColorSchemeId(colorSchemeId);
	}

	@Override
	public void setCompanyFallbackVirtualHostname(
		String companyFallbackVirtualHostname) {

		_layoutSet.setCompanyFallbackVirtualHostname(
			companyFallbackVirtualHostname);
	}

	@Override
	public void setCompanyId(long companyId) {
		_layoutSet.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_layoutSet.setCreateDate(createDate);
	}

	@Override
	public void setCss(String css) {
		_layoutSet.setCss(css);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_layoutSet.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutSet.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutSet.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setGroupId(long groupId) {
		_layoutSet.setGroupId(groupId);
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;

		UnicodeProperties settingsProperties = getSettingsProperties();

		settingsProperties.setProperty(
			"last-publish-date", String.valueOf(_lastPublishDate.getTime()));
	}

	public void setLayoutSet(LayoutSet layoutSet) {
		_layoutSet = layoutSet;
	}

	@Override
	public void setLayoutSetId(long layoutSetId) {
		_layoutSet.setLayoutSetId(layoutSetId);
	}

	@Override
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {

		_layoutSet.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);
	}

	public void setLayoutSetPrototypeName(String layoutSetPrototypeName) {
		_layoutSetPrototypeName = layoutSetPrototypeName;
	}

	@Override
	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	@Override
	public void setLogoId(long logoId) {
		_layoutSet.setLogoId(logoId);
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		_layoutSet.setModelAttributes(attributes);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutSet.setModifiedDate(modifiedDate);
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_layoutSet.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_layoutSet.setNew(n);
	}

	@Override
	public void setPageCount(int pageCount) {
		_layoutSet.setPageCount(pageCount);
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutSet.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutSet.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_layoutSet.setPrivateLayout(privateLayout);
	}

	@Override
	public void setSettings(String settings) {
		_layoutSet.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		_layoutSet.setSettingsProperties(settingsProperties);
	}

	@Override
	public void setThemeId(String themeId) {
		_layoutSet.setThemeId(themeId);
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVirtualHostname(String virtualHostname) {
		_layoutSet.setVirtualHostname(virtualHostname);
	}

	@Override
	public CacheModel<LayoutSet> toCacheModel() {
		return _layoutSet.toCacheModel();
	}

	@Override
	public LayoutSet toEscapedModel() {
		return _layoutSet.toEscapedModel();
	}

	@Override
	public LayoutSet toUnescapedModel() {
		return _layoutSet.toUnescapedModel();
	}

	@Override
	public String toXmlString() {
		return _layoutSet.toXmlString();
	}

	private Date _lastPublishDate;
	private LayoutSet _layoutSet;
	private String _layoutSetPrototypeName;
	private long _userId;
	private String _userName;
	private String _userUuid;

}