/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portlet.softwarecatalog.DuplicateProductVersionDirectDownloadURLException;
import com.liferay.portlet.softwarecatalog.ProductVersionChangeLogException;
import com.liferay.portlet.softwarecatalog.ProductVersionDownloadURLException;
import com.liferay.portlet.softwarecatalog.ProductVersionFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.ProductVersionNameException;
import com.liferay.portlet.softwarecatalog.UnavailableProductVersionDirectDownloadURLException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCProductVersionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCProductVersionLocalServiceImpl
	extends SCProductVersionLocalServiceBaseImpl {

	@Override
	public SCProductVersion addProductVersion(
			long userId, long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean testDirectDownloadURL, boolean repoStoreArtifact,
			long[] frameworkVersionIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Product version

		User user = userPersistence.findByPrimaryKey(userId);
		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);
		directDownloadURL = directDownloadURL.trim().toLowerCase();
		Date now = new Date();

		validate(
			0, version, changeLog, downloadPageURL, directDownloadURL,
			testDirectDownloadURL, frameworkVersionIds);

		long productVersionId = counterLocalService.increment();

		SCProductVersion productVersion = scProductVersionPersistence.create(
			productVersionId);

		productVersion.setCompanyId(user.getCompanyId());
		productVersion.setUserId(user.getUserId());
		productVersion.setUserName(user.getFullName());
		productVersion.setCreateDate(now);
		productVersion.setModifiedDate(now);
		productVersion.setProductEntryId(productEntryId);
		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		scProductVersionPersistence.update(productVersion);

		// Framework versions

		scProductVersionPersistence.setSCFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		productEntry.setModifiedDate(now);

		scProductEntryPersistence.update(productEntry);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			SCProductEntry.class);

		indexer.reindex(productEntry);

		return productVersion;
	}

	@Override
	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SCProductVersion productVersion =
			scProductVersionPersistence.findByPrimaryKey(productVersionId);

		deleteProductVersion(productVersion);
	}

	@Override
	public void deleteProductVersion(SCProductVersion productVersion)
		throws SystemException {

		scProductVersionPersistence.remove(productVersion);
	}

	@Override
	public void deleteProductVersions(long productEntryId)
		throws SystemException {

		List<SCProductVersion> productVersions =
			scProductVersionPersistence.findByProductEntryId(productEntryId);

		for (SCProductVersion productVersion : productVersions) {
			deleteProductVersion(productVersion);
		}
	}

	@Override
	public SCProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		return scProductVersionPersistence.findByPrimaryKey(productVersionId);
	}

	@Override
	public SCProductVersion getProductVersionByDirectDownloadURL(
			String directDownloadURL)
		throws PortalException, SystemException {

		return scProductVersionPersistence.findByDirectDownloadURL(
			directDownloadURL);
	}

	@Override
	public List<SCProductVersion> getProductVersions(
			long productEntryId, int start, int end)
		throws SystemException {

		return scProductVersionPersistence.findByProductEntryId(
			productEntryId, start, end);
	}

	@Override
	public int getProductVersionsCount(long productEntryId)
		throws SystemException {

		return scProductVersionPersistence.countByProductEntryId(
			productEntryId);
	}

	@Override
	public SCProductVersion updateProductVersion(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean testDirectDownloadURL, boolean repoStoreArtifact,
			long[] frameworkVersionIds)
		throws PortalException, SystemException {

		// Product version

		directDownloadURL = directDownloadURL.trim().toLowerCase();
		Date now = new Date();

		validate(
			productVersionId, version, changeLog, downloadPageURL,
			directDownloadURL, testDirectDownloadURL, frameworkVersionIds);

		SCProductVersion productVersion =
			scProductVersionPersistence.findByPrimaryKey(productVersionId);

		productVersion.setModifiedDate(now);
		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		scProductVersionPersistence.update(productVersion);

		// Framework versions

		scProductVersionPersistence.setSCFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(
				productVersion.getProductEntryId());

		productEntry.setModifiedDate(now);

		scProductEntryPersistence.update(productEntry);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			SCProductEntry.class);

		indexer.reindex(productEntry);

		return productVersion;
	}

	protected void testDirectDownloadURL(String directDownloadURL)
		throws PortalException {

		try {
			HttpImpl httpImpl = null;

			Object httpObject = HttpUtil.getHttp();

			if (httpObject instanceof DoPrivilegedBean) {
				DoPrivilegedBean doPrivilegedBean =
					(DoPrivilegedBean)httpObject;

				httpImpl = (HttpImpl)doPrivilegedBean.getActualBean();
			}
			else {
				httpImpl = (HttpImpl)httpObject;
			}

			HostConfiguration hostConfiguration = httpImpl.getHostConfiguration(
				directDownloadURL);

			HttpClient httpClient = httpImpl.getClient(hostConfiguration);

			GetMethod getFileMethod = new GetMethod(directDownloadURL);

			int responseCode = httpClient.executeMethod(
				hostConfiguration, getFileMethod);

			if (responseCode != HttpServletResponse.SC_OK) {
				throw new UnavailableProductVersionDirectDownloadURLException();
			}
		}
		catch (Exception e) {
			throw new UnavailableProductVersionDirectDownloadURLException();
		}
	}

	protected void validate(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean testDirectDownloadURL, long[] frameworkVersionIds)
		throws PortalException, SystemException {

		if (Validator.isNull(version)) {
			throw new ProductVersionNameException();
		}
		else if (Validator.isNull(changeLog)) {
			throw new ProductVersionChangeLogException();
		}
		else if (Validator.isNull(downloadPageURL) &&
				 Validator.isNull(directDownloadURL)) {

			throw new ProductVersionDownloadURLException();
		}
		else if (Validator.isNotNull(directDownloadURL)) {
			SCProductVersion productVersion =
				scProductVersionPersistence.fetchByDirectDownloadURL(
					directDownloadURL);

			if ((productVersion != null) &&
				(productVersion.getProductVersionId() != productVersionId)) {

				throw new DuplicateProductVersionDirectDownloadURLException();
			}

			if (testDirectDownloadURL) {
				testDirectDownloadURL(directDownloadURL);
			}
		}
		else if (frameworkVersionIds.length == 0) {
			throw new ProductVersionFrameworkVersionException();
		}
	}

}