/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutPrototype service. Represents a row in the &quot;LayoutPrototype&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypeModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.LayoutPrototypeImpl")
@ProviderType
public interface LayoutPrototype extends LayoutPrototypeModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.LayoutPrototypeImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutPrototype, Long>
		LAYOUT_PROTOTYPE_ID_ACCESSOR = new Accessor<LayoutPrototype, Long>() {

			@Override
			public Long get(LayoutPrototype layoutPrototype) {
				return layoutPrototype.getLayoutPrototypeId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutPrototype> getTypeClass() {
				return LayoutPrototype.class;
			}

		};

	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Layout getLayout()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	 * Returns the number of failed merge attempts for the layout prototype
	 * since its last reset or update.
	 *
	 * @return the number of failed merge attempts for the layout prototype
	 */
	public int getMergeFailCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasSetModifiedDate();

}