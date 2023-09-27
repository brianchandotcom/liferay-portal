/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.exportimport.staged.model.repository;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut",
	service = StagedModelRepository.class
)
public class FileShortcutStagedModelRepository
	implements StagedModelRepository<FileShortcut> {

	@Override
	public FileShortcut addStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(FileShortcut fileShortcut)
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
	public FileShortcut fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileShortcut fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileShortcut> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_dlFileShortcutLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property property = PropertyFactoryUtil.forName("active");

				dynamicQuery.add(property.eq(Boolean.TRUE));
			});

		exportActionableDynamicQuery.setPerformActionMethod(
			(DLFileShortcut dlFileShortcut) ->
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext,
					_dlAppLocalService.getFileShortcut(
						dlFileShortcut.getFileShortcutId())));
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFileShortcutConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	@Override
	public FileShortcut getStagedModel(long fileShortcutId)
		throws PortalException {

		return _dlAppLocalService.getFileShortcut(fileShortcutId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileShortcut saveStagedModel(FileShortcut fileShortcut)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileShortcut updateStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

}