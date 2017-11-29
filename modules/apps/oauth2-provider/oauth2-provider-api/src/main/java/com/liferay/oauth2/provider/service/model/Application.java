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

package com.liferay.oauth2.provider.service.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Application service. Represents a row in the &quot;OAuthTwo_Application&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ApplicationModel
 * @see com.liferay.oauth2.provider.service.model.impl.ApplicationImpl
 * @see com.liferay.oauth2.provider.service.model.impl.ApplicationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.oauth2.provider.service.model.impl.ApplicationImpl")
@ProviderType
public interface Application extends ApplicationModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.oauth2.provider.service.model.impl.ApplicationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Application, Long> ID_ACCESSOR = new Accessor<Application, Long>() {
			@Override
			public Long get(Application application) {
				return application.getId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Application> getTypeClass() {
				return Application.class;
			}
		};
}