/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

/**
 * The extended model interface for the LayoutSet service. Represents a row in the &quot;LayoutSet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetModel
 * @see com.liferay.portal.model.impl.LayoutSetImpl
 * @see com.liferay.portal.model.impl.LayoutSetModelImpl
 * @generated
 */
public interface LayoutSet extends LayoutSetModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.LayoutSetImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public long getLayoutSetPrototypeId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public long getLiveLogoId();

	public java.lang.String getSettings();

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties();

	public java.lang.String getSettingsProperty(java.lang.String key);

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getVirtualHostname();

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isLayoutSetPrototypeLinkActive();

	public void setSettings(java.lang.String settings);

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties);

	public void setVirtualHostname(java.lang.String virtualHostname);
}