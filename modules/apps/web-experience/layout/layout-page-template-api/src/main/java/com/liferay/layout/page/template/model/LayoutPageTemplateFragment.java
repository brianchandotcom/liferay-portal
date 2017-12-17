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

package com.liferay.layout.page.template.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the LayoutPageTemplateFragment service. Represents a row in the &quot;LayoutPageTemplateFragment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragmentModel
 * @see com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentImpl
 * @see com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentImpl")
@ProviderType
public interface LayoutPageTemplateFragment
	extends LayoutPageTemplateFragmentModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutPageTemplateFragment, Long> LAYOUT_PAGE_TEMPLATE_FRAGMENT_ID_ACCESSOR =
		new Accessor<LayoutPageTemplateFragment, Long>() {
			@Override
			public Long get(
				LayoutPageTemplateFragment layoutPageTemplateFragment) {
				return layoutPageTemplateFragment.getLayoutPageTemplateFragmentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutPageTemplateFragment> getTypeClass() {
				return LayoutPageTemplateFragment.class;
			}
		};

	public java.lang.String getCss()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getHtml()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getJs()
		throws com.liferay.portal.kernel.exception.PortalException;
}