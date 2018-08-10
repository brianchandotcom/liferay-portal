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

package com.liferay.document.library.internal.exportimport.staged.model.repository;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.repository.liferayrepository.LiferayRepositoryDefiner;
import com.liferay.portal.repository.temporaryrepository.TemporaryFileEntryRepositoryDefiner;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.Repository",
	service = StagedModelRepository.class
)
public class RepositoryStagedModelRepository
	implements StagedModelRepository<Repository> {

	@Override
	public Repository addStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(Repository repository)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Repository fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Repository fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<Repository> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			portletDataContext);

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_repositoryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long liferayRepositoryClassNameId = _portal.getClassNameId(
					LiferayRepositoryDefiner.CLASS_NAME);

				conjunction.add(
					classNameIdProperty.ne(liferayRepositoryClassNameId));

				long tempFileRepositoryClassNameId = _portal.getClassNameId(
					TemporaryFileEntryRepositoryDefiner.CLASS_NAME);

				conjunction.add(
					classNameIdProperty.ne(tempFileRepositoryClassNameId));

				dynamicQuery.add(conjunction);

				Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

				Property portletIdProperty = PropertyFactoryUtil.forName(
					"portletId");

				disjunction.add(portletIdProperty.isNull());
				disjunction.add(portletIdProperty.eq(StringPool.BLANK));
				disjunction.add(
					portletIdProperty.eq(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN));
				disjunction.add(
					portletIdProperty.like(
						DLPortletKeys.DOCUMENT_LIBRARY + "%"));

				Property repositoryIdProperty = PropertyFactoryUtil.forName(
					"repositoryId");

				disjunction.add(
					repositoryIdProperty.in(exportableRepositoryIds));

				dynamicQuery.add(disjunction);
			});

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				_portal.getClassNameId(Repository.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	@Override
	public Repository getStagedModel(long repositoryId) throws PortalException {
		return _repositoryLocalService.getRepository(repositoryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Repository saveStagedModel(Repository repository)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Repository updateStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlExportableRepositoryPublishers = ServiceTrackerListFactory.open(
			bundleContext, DLExportableRepositoryPublisher.class);
	}

	@Deactivate
	protected void deactivate() {
		if (_dlExportableRepositoryPublishers != null) {
			_dlExportableRepositoryPublishers.close();
		}
	}

	private Collection<Long> _getExportableRepositoryIds(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = new HashSet<>();

		exportableRepositoryIds.add(portletDataContext.getScopeGroupId());

		for (DLExportableRepositoryPublisher dlExportableRepositoryPublisher :
				_dlExportableRepositoryPublishers) {

			dlExportableRepositoryPublisher.publish(
				portletDataContext.getScopeGroupId(),
				exportableRepositoryIds::add);
		}

		return exportableRepositoryIds;
	}

	private ServiceTrackerList
		<DLExportableRepositoryPublisher, DLExportableRepositoryPublisher>
			_dlExportableRepositoryPublishers;

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}