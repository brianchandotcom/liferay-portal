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

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the WebDAVProps service. Represents a row in the &quot;WebDAVProps&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsModel
 * @see com.liferay.portal.model.impl.WebDAVPropsImpl
 * @see com.liferay.portal.model.impl.WebDAVPropsModelImpl
 * @generated
 */
@ProviderType
public interface WebDAVProps extends WebDAVPropsModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.WebDAVPropsImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WebDAVProps, Long> WEB_DAV_PROPS_ID_ACCESSOR = new Accessor<WebDAVProps, Long>() {
			@Override
			public Long get(WebDAVProps webDAVProps) {
				return webDAVProps.getWebDavPropsId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<WebDAVProps> getTypeClass() {
				return WebDAVProps.class;
			}
		};

	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception;

	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri, java.lang.String text) throws java.lang.Exception;

	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPropsSet()
		throws java.lang.Exception;

	public java.lang.String getText(java.lang.String name,
		java.lang.String prefix, java.lang.String uri)
		throws java.lang.Exception;

	public void removeProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception;

	public void store() throws java.lang.Exception;
}