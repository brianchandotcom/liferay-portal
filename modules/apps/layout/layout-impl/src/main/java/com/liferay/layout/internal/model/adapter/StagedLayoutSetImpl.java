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

package com.liferay.layout.internal.model.adapter;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutSetWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.time.Instant;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author Máté Thurzó
 */
public class StagedLayoutSetImpl
	extends LayoutSetWrapper implements StagedLayoutSet {

	public StagedLayoutSetImpl(LayoutSet layoutSet) {
		super(layoutSet);

		Objects.requireNonNull(
			layoutSet,
			"Unable to create a new staged layout set for a null layout set");

		_layoutSet = layoutSet;

		// Last publish date

		UnicodeProperties settingsProperties =
			_layoutSet.getSettingsProperties();

		String lastPublishDateString = settingsProperties.getProperty(
			"last-publish-date");

		Instant instant = Instant.ofEpochMilli(
			GetterUtil.getLong(lastPublishDateString));

		_lastPublishDate = Date.from(instant);

		// Layout set prototype

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
			Group layoutSetGroup = _layoutSet.getGroup();

			_userId = layoutSetGroup.getCreatorUserId();

			User user = UserLocalServiceUtil.getUser(_userId);

			_userName = user.getFullName();
			_userUuid = user.getUuid();
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}
	}

	@Override
	public Object clone() {
		return new StagedLayoutSetImpl((LayoutSet)_layoutSet.clone());
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
	public LayoutSet getLayoutSet() {
		return _layoutSet;
	}

	@Override
	public Optional<String> getLayoutSetPrototypeName() {
		return Optional.ofNullable(_layoutSetPrototypeName);
	}

	@Override
	public Class<?> getModelClass() {
		return StagedLayoutSet.class;
	}

	@Override
	public String getModelClassName() {
		return StagedLayoutSet.class.getName();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutSet.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedLayoutSet.class);
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

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getVirtualHostnames()}
	 */
	@Deprecated
	@Override
	public String getVirtualHostname() {
		return _layoutSet.getVirtualHostname();
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

	public void setLayoutSetPrototypeName(String layoutSetPrototypeName) {
		_layoutSetPrototypeName = layoutSetPrototypeName;
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

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #setVirtualHostnames(TreeMap)}
	 */
	@Deprecated
	@Override
	public void setVirtualHostname(String virtualHostname) {
		_layoutSet.setVirtualHostname(virtualHostname);
	}

	@Override
	public void setVirtualHostnames(TreeMap virtualHostnames) {
		_layoutSet.setVirtualHostnames(virtualHostnames);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedLayoutSetImpl.class);

	private Date _lastPublishDate;
	private LayoutSet _layoutSet;
	private String _layoutSetPrototypeName;
	private long _userId;
	private String _userName;
	private String _userUuid;

}