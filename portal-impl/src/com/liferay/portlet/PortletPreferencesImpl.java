/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashCode;
import com.liferay.portal.kernel.util.HashCodeFactoryUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PortletPreferencesImpl
	extends BasePreferencesImpl
	implements Cloneable, PortletPreferences, Serializable {

	public PortletPreferencesImpl() {
		this(
			0, 0, 0, 0, null, null, Collections.<String, Preference>emptyMap());
	}

	public PortletPreferencesImpl(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml, Map<String, Preference> preferences) {

		super(companyId, ownerId, ownerType, xml, preferences);

		_plid = plid;
		_portletId = portletId;
	}

	public PortletPreferencesImpl(
		String xml, Map<String, Preference> preferences) {

		this(0, 0, 0, 0, null, xml, preferences);
	}

	@Override
	public Object clone() {
		return new PortletPreferencesImpl(
			getCompanyId(), getOwnerId(), getOwnerType(), _plid, _portletId,
			getOriginalXML(), getOriginalPreferences());
	}

	@Override
	public boolean equals(Object obj) {
		PortletPreferencesImpl portletPreferences = (PortletPreferencesImpl)obj;

		if (this == portletPreferences) {
			return true;
		}

		if ((getCompanyId() == portletPreferences.getCompanyId()) &&
			(getOwnerId() == portletPreferences.getOwnerId()) &&
			(getOwnerType() == portletPreferences.getOwnerType()) &&
			(getPlid() == portletPreferences.getPlid()) &&
			getPortletId().equals(portletPreferences.getPortletId()) &&
			getPreferences().equals(portletPreferences.getPreferences())) {

			return true;
		}
		else {
			return false;
		}
	}

	public long getPlid() {
		return _plid;
	}

	@Override
	public int hashCode() {
		HashCode hashCode = HashCodeFactoryUtil.getHashCode();

		hashCode.append(getCompanyId());
		hashCode.append(getOwnerId());
		hashCode.append(getOwnerType());
		hashCode.append(_plid);
		hashCode.append(_portletId);
		hashCode.append(getPreferences());

		return hashCode.toHashCode();
	}

	@Override
	public void reset(String key) throws ReadOnlyException {
		if (isReadOnly(key)) {
			throw new ReadOnlyException(key);
		}

		if ((_defaultPreferences == null) && (_portletId != null)) {
			try {
				_defaultPreferences =
					PortletPreferencesLocalServiceUtil.getDefaultPreferences(
						getCompanyId(), _portletId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		String[] defaultValues = null;

		if (_defaultPreferences != null) {
			defaultValues = _defaultPreferences.getValues(key, defaultValues);
		}

		if (defaultValues != null) {
			setValues(key, defaultValues);
		}
		else {
			Map<String, Preference> modifiedPreferences =
				getModifiedPreferences();

			modifiedPreferences.remove(key);
		}
	}

	@Override
	public void store() throws IOException, ValidatorException {
		if (_portletId == null) {
			throw new UnsupportedOperationException();
		}

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), _portletId);

			PreferencesValidator preferencesValidator =
				PortalUtil.getPreferencesValidator(portlet);

			if (preferencesValidator != null) {
				preferencesValidator.validate(this);
			}

			PortletPreferencesLocalServiceUtil.updatePreferences(
				getOwnerId(), getOwnerType(), _plid, _portletId, this);
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	protected String getPortletId() {
		return _portletId;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletPreferencesImpl.class);

	private PortletPreferences _defaultPreferences;
	private long _plid;
	private String _portletId;

}