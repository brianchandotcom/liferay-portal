/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutSetPrototype service. Represents a row in the &quot;LayoutSetPrototype&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.LayoutSetPrototypeImpl")
@ProviderType
public interface LayoutSetPrototype
	extends LayoutSetPrototypeModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.LayoutSetPrototypeImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutSetPrototype, Long>
		LAYOUT_SET_PROTOTYPE_ID_ACCESSOR =
			new Accessor<LayoutSetPrototype, Long>() {

				@Override
				public Long get(LayoutSetPrototype layoutSetPrototype) {
					return layoutSetPrototype.getLayoutSetPrototypeId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<LayoutSetPrototype> getTypeClass() {
					return LayoutSetPrototype.class;
				}

			};

	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	 * Returns the number of failed merge attempts for the layout set prototype
	 * since its last reset or update.
	 *
	 * @return the number of failed merge attempts for the layout set prototype
	 */
	public int getMergeFailCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.util.UnicodeProperties
		getSettingsProperties();

	public String getSettingsProperty(String key);

	public boolean hasSetModifiedDate();

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			settingsUnicodeProperties);

}