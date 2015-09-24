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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.persistence.SocialActivitySetPersistence;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySetFinderBaseImpl extends BasePersistenceImpl<SocialActivitySet> {
	/**
	 * Returns the social activity set persistence.
	 *
	 * @return the social activity set persistence
	 */
	public SocialActivitySetPersistence getSocialActivitySetPersistence() {
		return socialActivitySetPersistence;
	}

	/**
	 * Sets the social activity set persistence.
	 *
	 * @param socialActivitySetPersistence the social activity set persistence
	 */
	public void setSocialActivitySetPersistence(
		SocialActivitySetPersistence socialActivitySetPersistence) {
		this.socialActivitySetPersistence = socialActivitySetPersistence;
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return getSocialActivitySetPersistence().getBadColumnNames();
	}

	@BeanReference(type = SocialActivitySetPersistence.class)
	protected SocialActivitySetPersistence socialActivitySetPersistence;
}