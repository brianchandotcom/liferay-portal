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

package com.liferay.portal.repository.cmis;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Repository;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.persistence.RepositoryUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.exceptions.CmisPermissionDeniedException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

/**
 * @author Alexander Chow
 */
public class CMISRepositoryUtil {

	public static void checkRepository(
			long repositoryId, Map<String, String> parameters,
			UnicodeProperties typeSettingsProperties, String typeSettingsKey)
		throws PortalException, RepositoryException {

		if (!typeSettingsProperties.containsKey(typeSettingsKey)) {
			org.apache.chemistry.opencmis.client.api.Repository cmisRepository =
				_sessionFactory.getRepositories(parameters).get(0);

			typeSettingsProperties.setProperty(
				typeSettingsKey, cmisRepository.getId());

			try {
				Repository repository = RepositoryUtil.findByPrimaryKey(
					repositoryId);

				repository.setTypeSettingsProperties(typeSettingsProperties);

				RepositoryUtil.update(repository);
			}
			catch (Exception e) {
				throw new RepositoryException(e);
			}
		}

		parameters.put(
			SessionParameter.REPOSITORY_ID,
			getTypeSettingsValue(typeSettingsProperties, typeSettingsKey));
	}

	public static com.liferay.portal.kernel.repository.cmis.Session
			createSession(Map<String, String> parameters)
		throws PrincipalException, RepositoryException {

		try {
			Session session = _sessionFactory.createSession(parameters);

			session.setDefaultContext(_operationContext);

			return new SessionImpl(session);
		}
		catch (CmisPermissionDeniedException cpde) {
			throw new PrincipalException(cpde);
		}
		catch (CmisUnauthorizedException cue) {
			throw new PrincipalException();
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public static OperationContext getOperationContext() {
		return _operationContext;
	}

	public static SessionFactory getSessionFactory() {
		return _sessionFactory;
	}

	public static String getTypeSettingsValue(
			UnicodeProperties typeSettingsProperties, String typeSettingsKey)
		throws InvalidRepositoryException {

		String value = typeSettingsProperties.getProperty(typeSettingsKey);

		if (Validator.isNull(value)) {
			throw new InvalidRepositoryException(
				"Properties value cannot be null for key " + typeSettingsKey);
		}

		return value;
	}

	private static final OperationContext _operationContext;
	private static final SessionFactory _sessionFactory =
		SessionFactoryImpl.newInstance();

	static {
		Set<String> defaultFilterSet = new HashSet<String>();

		// Base

		defaultFilterSet.add(PropertyIds.BASE_TYPE_ID);
		defaultFilterSet.add(PropertyIds.CREATED_BY);
		defaultFilterSet.add(PropertyIds.CREATION_DATE);
		defaultFilterSet.add(PropertyIds.LAST_MODIFIED_BY);
		defaultFilterSet.add(PropertyIds.LAST_MODIFICATION_DATE);
		defaultFilterSet.add(PropertyIds.NAME);
		defaultFilterSet.add(PropertyIds.OBJECT_ID);
		defaultFilterSet.add(PropertyIds.OBJECT_TYPE_ID);

		// Document

		defaultFilterSet.add(PropertyIds.CONTENT_STREAM_LENGTH);
		defaultFilterSet.add(PropertyIds.CONTENT_STREAM_MIME_TYPE);
		defaultFilterSet.add(PropertyIds.IS_VERSION_SERIES_CHECKED_OUT);
		defaultFilterSet.add(PropertyIds.VERSION_LABEL);
		defaultFilterSet.add(PropertyIds.VERSION_SERIES_CHECKED_OUT_BY);
		defaultFilterSet.add(PropertyIds.VERSION_SERIES_CHECKED_OUT_ID);
		defaultFilterSet.add(PropertyIds.VERSION_SERIES_ID);

		// Folder

		defaultFilterSet.add(PropertyIds.PARENT_ID);
		defaultFilterSet.add(PropertyIds.PATH);

		// Operation context

		_operationContext = new OperationContextImpl(
			defaultFilterSet, false, true, false, IncludeRelationships.NONE,
			null, false, "cmis:name ASC", true, 1000);
	}

}