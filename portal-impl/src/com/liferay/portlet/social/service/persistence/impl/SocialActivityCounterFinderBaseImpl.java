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

package com.liferay.portlet.social.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.social.kernel.model.SocialActivityCounter;
import com.liferay.social.kernel.service.persistence.SocialActivityCounterPersistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityCounterFinderBaseImpl
	extends BasePersistenceImpl<SocialActivityCounter> {

	public SocialActivityCounterFinderBaseImpl() {
		setModelClass(SocialActivityCounter.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getSocialActivityCounterPersistence().getBadColumnNames();
	}

	/**
	 * Returns the social activity counter persistence.
	 *
	 * @return the social activity counter persistence
	 */
	public SocialActivityCounterPersistence
		getSocialActivityCounterPersistence() {

		return socialActivityCounterPersistence;
	}

	/**
	 * Sets the social activity counter persistence.
	 *
	 * @param socialActivityCounterPersistence the social activity counter persistence
	 */
	public void setSocialActivityCounterPersistence(
		SocialActivityCounterPersistence socialActivityCounterPersistence) {

		this.socialActivityCounterPersistence =
			socialActivityCounterPersistence;
	}

	@BeanReference(type = SocialActivityCounterPersistence.class)
	protected SocialActivityCounterPersistence socialActivityCounterPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityCounterFinderBaseImpl.class);

}