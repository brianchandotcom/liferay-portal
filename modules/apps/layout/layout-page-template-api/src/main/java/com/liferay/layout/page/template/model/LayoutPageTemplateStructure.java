/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutPageTemplateStructure service. Represents a row in the &quot;LayoutPageTemplateStructure&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureImpl"
)
@ProviderType
public interface LayoutPageTemplateStructure
	extends LayoutPageTemplateStructureModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutPageTemplateStructure, Long>
		LAYOUT_PAGE_TEMPLATE_STRUCTURE_ID_ACCESSOR =
			new Accessor<LayoutPageTemplateStructure, Long>() {

				@Override
				public Long get(
					LayoutPageTemplateStructure layoutPageTemplateStructure) {

					return layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<LayoutPageTemplateStructure> getTypeClass() {
					return LayoutPageTemplateStructure.class;
				}

			};

	public String getData(long segmentsExperienceId);

	public String getData(String segmentsExperienceKey);

	public String getDefaultSegmentsExperienceData();

}