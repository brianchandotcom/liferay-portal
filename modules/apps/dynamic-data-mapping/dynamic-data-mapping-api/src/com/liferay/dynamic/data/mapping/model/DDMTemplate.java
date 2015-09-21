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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DDMTemplate service. Represents a row in the &quot;DDMTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMTemplateModelImpl
 * @generated
 */
@ProviderType
public interface DDMTemplate extends DDMTemplateModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getLatestTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getTemplateImageURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay);

	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the WebDAV URL to access the template.
	*
	* @param themeDisplay the theme display needed to build the URL. It can
	set HTTPS access, the server name, the server port, the path
	context, and the scope group.
	* @param webDAVToken the WebDAV token for the URL
	* @return the WebDAV URL
	*/
	public java.lang.String getWebDavURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String webDAVToken);

	public void setSmallImageType(java.lang.String smallImageType);
}