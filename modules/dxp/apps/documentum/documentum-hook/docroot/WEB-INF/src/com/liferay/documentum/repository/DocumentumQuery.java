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

package com.liferay.documentum.repository;

import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;

import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.documentum.repository.model.Constants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class DocumentumQuery {

	public DocumentumQuery(IDfClientX idfClientX, IDfSession idfSession) {
		_idfClientX = idfClientX;
		_idfSession = idfSession;
	}

	public int getCount(
			String extRepositoryFolderKey,
			ExtRepositoryObjectType<?> extRepositoryObjectType)
		throws DfException {

		IDfCollection idfCollection = null;

		try {
			IDfQuery idfQuery = _idfClientX.getQuery();

			String queryString = buildSysObjectQuery(
				"COUNT(r_object_id) AS num_hits", extRepositoryFolderKey,
				extRepositoryObjectType);

			idfQuery.setDQL(queryString);

			if (_log.isDebugEnabled()) {
				_log.debug("Executing query: " + queryString);
			}

			idfCollection = idfQuery.execute(
				_idfSession, IDfQuery.DF_READ_QUERY);

			int total = 0;

			if (idfCollection.next()) {
				total = idfCollection.getInt("num_hits");
			}

			return total;
		}
		finally {
			close(idfCollection);
		}
	}

	public List<IDfSysObject> getIDfSysObjects(
			String extRepositoryFolderKey,
			ExtRepositoryObjectType<?> extRepositoryObjectType)
		throws DfException {

		IDfCollection idfCollection = null;

		try {
			List<IDfSysObject> idfSysObjects = new ArrayList<>();

			IDfQuery idfQuery = _idfClientX.getQuery();

			String queryString = buildSysObjectQuery(
				Constants.R_OBJECT_ID, extRepositoryFolderKey,
				extRepositoryObjectType);

			idfQuery.setDQL(queryString);

			if (_log.isDebugEnabled()) {
				_log.debug("Executing query: " + queryString);
			}

			idfCollection = idfQuery.execute(
				_idfSession, IDfQuery.DF_READ_QUERY);

			while (idfCollection.next()) {
				IDfId idfId = idfCollection.getId(Constants.R_OBJECT_ID);

				IDfSysObject idfSysObject = (IDfSysObject)_idfSession.getObject(
					idfId);

				if (_log.isTraceEnabled()) {
					_log.trace(idfSysObject.dump());
				}

				try {
					idfSysObjects.add(idfSysObject);
				}
				catch (RepositoryException re) {
					if (_log.isWarnEnabled()) {
						_log.warn(re, re);
					}
				}
			}

			return idfSysObjects;
		}
		finally {
			close(idfCollection);
		}
	}

	protected String buildSysObjectQuery(
		String selector, String extRepositoryFolderKey,
		ExtRepositoryObjectType<?> extRepositoryObjectType) {

		StringBuilder sb = new StringBuilder(16);

		sb.append("SELECT ");
		sb.append(selector);
		sb.append(" FROM ");
		sb.append(Constants.DM_SYSOBJECT);
		sb.append(" WHERE FOLDER(ID('");
		sb.append(extRepositoryFolderKey);
		sb.append("')) AND ");

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			sb.append(Constants.R_OBJECT_TYPE);
			sb.append("='");
			sb.append(Constants.DM_DOCUMENT);
			sb.append("'");
		}
		else if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
			sb.append(Constants.R_OBJECT_TYPE);
			sb.append("='");
			sb.append(Constants.DM_FOLDER);
			sb.append("'");
		}
		else {
			sb.append("((");
			sb.append(Constants.R_OBJECT_TYPE);
			sb.append("='");
			sb.append(Constants.DM_DOCUMENT);
			sb.append("') OR (");
			sb.append(Constants.R_OBJECT_TYPE);
			sb.append("='");
			sb.append(Constants.DM_FOLDER);
			sb.append("'))");
		}

		return sb.toString();
	}

	protected void close(IDfCollection idfCollection) {
		if (idfCollection != null) {
			try {
				idfCollection.close();
			}
			catch (DfException de) {
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentumQuery.class);

	private final IDfClientX _idfClientX;
	private final IDfSession _idfSession;

}