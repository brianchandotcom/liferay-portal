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

package com.liferay.segments.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SegmentsExperimentRel service. Represents a row in the &quot;SegmentsExperimentRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.segments.model.impl.SegmentsExperimentRelImpl"
)
@ProviderType
public interface SegmentsExperimentRel
	extends PersistedModel, SegmentsExperimentRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.segments.model.impl.SegmentsExperimentRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsExperimentRel, Long>
		SEGMENTS_EXPERIMENT_REL_ID_ACCESSOR =
			new Accessor<SegmentsExperimentRel, Long>() {

				@Override
				public Long get(SegmentsExperimentRel segmentsExperimentRel) {
					return segmentsExperimentRel.getSegmentsExperimentRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SegmentsExperimentRel> getTypeClass() {
					return SegmentsExperimentRel.class;
				}

			};

	public String getName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getSegmentsExperienceKey();

	public String getSegmentsExperimentKey()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isActive()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isControl()
		throws com.liferay.portal.kernel.exception.PortalException;

}