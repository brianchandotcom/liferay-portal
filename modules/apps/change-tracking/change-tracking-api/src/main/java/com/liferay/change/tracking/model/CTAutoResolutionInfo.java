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

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CTAutoResolutionInfo service. Represents a row in the &quot;CTAutoResolutionInfo&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CTAutoResolutionInfoModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.change.tracking.model.impl.CTAutoResolutionInfoImpl"
)
@ProviderType
public interface CTAutoResolutionInfo extends CTAutoResolutionInfoModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.change.tracking.model.impl.CTAutoResolutionInfoImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CTAutoResolutionInfo, Long>
		CT_AUTO_RESOLUTION_INFO_ID_ACCESSOR =
			new Accessor<CTAutoResolutionInfo, Long>() {

				@Override
				public Long get(CTAutoResolutionInfo ctAutoResolutionInfo) {
					return ctAutoResolutionInfo.getCtAutoResolutionInfoId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CTAutoResolutionInfo> getTypeClass() {
					return CTAutoResolutionInfo.class;
				}

			};

}