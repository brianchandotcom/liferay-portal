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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the WebDAVProps service. Represents a row in the &quot;WebDAVProps&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.WebDAVPropsImpl")
@ProviderType
public interface WebDAVProps extends PersistedModel, WebDAVPropsModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.WebDAVPropsImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WebDAVProps, Long> WEB_DAV_PROPS_ID_ACCESSOR =
		new Accessor<WebDAVProps, Long>() {

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

	public void addProp(String name, String prefix, String uri)
		throws Exception;

	public void addProp(String name, String prefix, String uri, String text)
		throws Exception;

	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPropsSet()
		throws Exception;

	public String getText(String name, String prefix, String uri)
		throws Exception;

	public void removeProp(String name, String prefix, String uri)
		throws Exception;

	public void store() throws Exception;

}