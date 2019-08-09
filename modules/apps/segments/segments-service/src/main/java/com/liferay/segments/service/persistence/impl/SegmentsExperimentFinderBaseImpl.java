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

package com.liferay.segments.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.persistence.SegmentsExperimentPersistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperimentFinderBaseImpl
	extends BasePersistenceImpl<SegmentsExperiment> {

	public SegmentsExperimentFinderBaseImpl() {
		setModelClass(SegmentsExperiment.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getSegmentsExperimentPersistence().getBadColumnNames();
	}

	/**
	 * Returns the segments experiment persistence.
	 *
	 * @return the segments experiment persistence
	 */
	public SegmentsExperimentPersistence getSegmentsExperimentPersistence() {
		return segmentsExperimentPersistence;
	}

	/**
	 * Sets the segments experiment persistence.
	 *
	 * @param segmentsExperimentPersistence the segments experiment persistence
	 */
	public void setSegmentsExperimentPersistence(
		SegmentsExperimentPersistence segmentsExperimentPersistence) {

		this.segmentsExperimentPersistence = segmentsExperimentPersistence;
	}

	@BeanReference(type = SegmentsExperimentPersistence.class)
	protected SegmentsExperimentPersistence segmentsExperimentPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentFinderBaseImpl.class);

}