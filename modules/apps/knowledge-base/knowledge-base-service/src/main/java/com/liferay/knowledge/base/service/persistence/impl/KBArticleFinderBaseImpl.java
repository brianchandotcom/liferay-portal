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

package com.liferay.knowledge.base.service.persistence.impl;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KBArticleFinderBaseImpl extends BasePersistenceImpl<KBArticle> {
	@Override
	public Set<String> getBadColumnNames() {
		return getKBArticlePersistence().getBadColumnNames();
	}

	/**
	 * Returns the kb article persistence.
	 *
	 * @return the kb article persistence
	 */
	public KBArticlePersistence getKBArticlePersistence() {
		return kbArticlePersistence;
	}

	/**
	 * Sets the kb article persistence.
	 *
	 * @param kbArticlePersistence the kb article persistence
	 */
	public void setKBArticlePersistence(
		KBArticlePersistence kbArticlePersistence) {
		this.kbArticlePersistence = kbArticlePersistence;
	}

	@BeanReference(type = KBArticlePersistence.class)
	protected KBArticlePersistence kbArticlePersistence;
}