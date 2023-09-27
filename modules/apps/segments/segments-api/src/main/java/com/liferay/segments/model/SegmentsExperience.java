/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SegmentsExperience service. Represents a row in the &quot;SegmentsExperience&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.segments.model.impl.SegmentsExperienceImpl"
)
@ProviderType
public interface SegmentsExperience
	extends PersistedModel, SegmentsExperienceModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.segments.model.impl.SegmentsExperienceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsExperience, Long>
		SEGMENTS_EXPERIENCE_ID_ACCESSOR =
			new Accessor<SegmentsExperience, Long>() {

				@Override
				public Long get(SegmentsExperience segmentsExperience) {
					return segmentsExperience.getSegmentsExperienceId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SegmentsExperience> getTypeClass() {
					return SegmentsExperience.class;
				}

			};

	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsUnicodeProperties();

	public boolean hasSegmentsExperiment();

	public void setTypeSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties);

}