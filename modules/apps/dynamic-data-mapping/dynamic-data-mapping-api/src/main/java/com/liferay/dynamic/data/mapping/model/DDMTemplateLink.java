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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the DDMTemplateLink service. Represents a row in the &quot;DDMTemplateLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkImpl")
@ProviderType
public interface DDMTemplateLink extends DDMTemplateLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMTemplateLink, Long> TEMPLATE_LINK_ID_ACCESSOR =
		new Accessor<DDMTemplateLink, Long>() {
			@Override
			public Long get(DDMTemplateLink ddmTemplateLink) {
				return ddmTemplateLink.getTemplateLinkId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMTemplateLink> getTypeClass() {
				return DDMTemplateLink.class;
			}
		};

	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate()
		throws com.liferay.portal.kernel.exception.PortalException;
}