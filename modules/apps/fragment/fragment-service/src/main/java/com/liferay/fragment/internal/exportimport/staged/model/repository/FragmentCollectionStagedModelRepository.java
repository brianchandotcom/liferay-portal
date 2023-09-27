/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentCollection",
	service = StagedModelRepository.class
)
public class FragmentCollectionStagedModelRepository
	implements StagedModelRepository<FragmentCollection> {

	@Override
	public FragmentCollection addStagedModel(
			PortletDataContext portletDataContext,
			FragmentCollection fragmentCollection)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			fragmentCollection.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fragmentCollection);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(fragmentCollection.getUuid());
		}

		return _fragmentCollectionLocalService.addFragmentCollection(
			userId, fragmentCollection.getGroupId(),
			fragmentCollection.getName(), fragmentCollection.getDescription(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(FragmentCollection fragmentCollection)
		throws PortalException {

		_fragmentCollectionLocalService.deleteFragmentCollection(
			fragmentCollection);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (fragmentCollection != null) {
			deleteStagedModel(fragmentCollection);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public FragmentCollection fetchMissingReference(String uuid, long groupId) {
		return (FragmentCollection)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public FragmentCollection fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _fragmentCollectionLocalService.
			fetchFragmentCollectionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<FragmentCollection> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _fragmentCollectionLocalService.
			getFragmentCollectionsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<FragmentCollection>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _fragmentCollectionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public FragmentCollection getStagedModel(long id) throws PortalException {
		return _fragmentCollectionLocalService.getFragmentCollection(id);
	}

	@Override
	public FragmentCollection saveStagedModel(
			FragmentCollection fragmentCollection)
		throws PortalException {

		return _fragmentCollectionLocalService.updateFragmentCollection(
			fragmentCollection);
	}

	@Override
	public FragmentCollection updateStagedModel(
			PortletDataContext portletDataContext,
			FragmentCollection fragmentCollection)
		throws PortalException {

		return _fragmentCollectionLocalService.updateFragmentCollection(
			fragmentCollection.getFragmentCollectionId(),
			fragmentCollection.getName(), fragmentCollection.getDescription());
	}

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}