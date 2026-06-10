/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SegmentsExperienceAudienceEntryRel service. Represents a row in the &quot;SExperienceAudienceEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceAudienceEntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.segments.model.impl.SegmentsExperienceAudienceEntryRelImpl"
)
@ProviderType
public interface SegmentsExperienceAudienceEntryRel
	extends PersistedModel, SegmentsExperienceAudienceEntryRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.segments.model.impl.SegmentsExperienceAudienceEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsExperienceAudienceEntryRel, Long>
		SEGMENTS_EXPERIENCE_AUDIENCE_ENTRY_REL_ID_ACCESSOR =
			new Accessor<SegmentsExperienceAudienceEntryRel, Long>() {

				@Override
				public Long get(
					SegmentsExperienceAudienceEntryRel
						segmentsExperienceAudienceEntryRel) {

					return segmentsExperienceAudienceEntryRel.
						getSegmentsExperienceAudienceEntryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SegmentsExperienceAudienceEntryRel>
					getTypeClass() {

					return SegmentsExperienceAudienceEntryRel.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-154380490