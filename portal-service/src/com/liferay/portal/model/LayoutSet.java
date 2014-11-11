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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

/**
 * The extended model interface for the LayoutSet service. Represents a row in the &quot;LayoutSet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetModel
 * @see com.liferay.portal.model.impl.LayoutSetImpl
 * @see com.liferay.portal.model.impl.LayoutSetModelImpl
 * @generated
 */
@ProviderType
public interface LayoutSet extends LayoutSetModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.LayoutSetImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the layout set's color scheme.
	*
	* <p>
	* Just like themes, color schemes can be configured on the layout set
	* level. The layout set's color scheme can be overridden on the layout
	* level.
	* </p>
	*
	* @return the layout set's color scheme
	*/
	public com.liferay.portal.model.ColorScheme getColorScheme();

	/**
	* Returns the layout set's group.
	*
	* @return the layout set's group
	* @throws PortalException if a group with the primary key could not be
	found
	*/
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the layout set prototype's ID, or <code>0</code> if it has no
	* layout set prototype.
	*
	* <p>
	* Prototype is Liferay's technical name for a site template.
	* </p>
	*
	* @return the layout set prototype's ID, or <code>0</code> if it has no
	layout set prototype
	* @throws PortalException if a matching layout set prototype could not be
	found
	*/
	public long getLayoutSetPrototypeId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getLiveLogoId();

	public boolean getLogo();

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties();

	public java.lang.String getSettingsProperty(java.lang.String key);

	public com.liferay.portal.model.Theme getTheme();

	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device);

	/**
	* Returns the name of the layout set's virtual host.
	*
	* <p>
	* When accessing a layout set that has a the virtual host, the URL elements
	* "/web/sitename" or "/group/sitename" can be omitted.
	* </p>
	*
	* @return the layout set's virtual host name, or an empty string if the
	layout set has no virtual host configured
	*/
	public java.lang.String getVirtualHostname();

	public com.liferay.portal.model.ColorScheme getWapColorScheme();

	public com.liferay.portal.model.Theme getWapTheme();

	public boolean isLayoutSetPrototypeLinkActive();

	public boolean isLogo();

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties);

	/**
	* Sets the name of the layout set's virtual host.
	*
	* @param virtualHostname the name of the layout set's virtual host
	* @see #getVirtualHostname()
	*/
	public void setVirtualHostname(java.lang.String virtualHostname);
}