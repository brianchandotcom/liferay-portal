/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class IGImageFinderImpl
	extends BasePersistenceImpl<IGImage> implements IGImageFinder {

	public static String FIND_BY_ANY_IMAGE_ID =
		IGImageFinder.class.getName() + ".findByAnyImageId";

	public static String FIND_BY_NO_ASSETS =
		IGImageFinder.class.getName() + ".findByNoAssets";

	public IGImage fetchByAnyImageId(long imageId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ANY_IMAGE_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("IGImage", IGImageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(imageId);
			qPos.add(imageId);
			qPos.add(imageId);
			qPos.add(imageId);

			List<IGImage> list = q.list();

			if (list.isEmpty()) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage findByAnyImageId(long imageId)
		throws NoSuchImageException, SystemException {

		IGImage image = fetchByAnyImageId(imageId);

		if (image == null) {
			throw new NoSuchImageException(
				"No IGImage exists with the imageId " + imageId);
		}
		else {
			return image;
		}
	}

	public List<IGImage> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("IGImage", IGImageImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}