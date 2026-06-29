/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutPageTemplateStructureRelElementVariationAudienceEntryRel service. Represents a row in the &quot;LPTSREVAudienceEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelElementVariationAudienceEntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl"
)
@ProviderType
public interface LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
	extends LayoutPageTemplateStructureRelElementVariationAudienceEntryRelModel,
			PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRel, Long>
			LAYOUT_PAGE_TEMPLATE_STRUCTURE_REL_ELEMENT_VARIATION_AUDIENCE_ENTRY_REL_ID_ACCESSOR =
				new Accessor
					<LayoutPageTemplateStructureRelElementVariationAudienceEntryRel,
					 Long>() {

					@Override
					public Long get(
						LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
							layoutPageTemplateStructureRelElementVariationAudienceEntryRel) {

						return layoutPageTemplateStructureRelElementVariationAudienceEntryRel.
							getLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId();
					}

					@Override
					public Class<Long> getAttributeClass() {
						return Long.class;
					}

					@Override
					public Class
						<LayoutPageTemplateStructureRelElementVariationAudienceEntryRel>
							getTypeClass() {

						return LayoutPageTemplateStructureRelElementVariationAudienceEntryRel.class;
					}

				};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1516868393