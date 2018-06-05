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

package com.liferay.exportimport.changeset.web.internal.portlet.data.handler;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.kernel.exception.ExportImportRuntimeException;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ChangesetPortletKeys.CHANGESET,
	service = PortletDataHandler.class
)
public class ChangesetPortletDataHandler extends BasePortletDataHandler {

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String changesetUuid = MapUtil.getString(parameterMap, "changesetUuid");

		if (Validator.isBlank(changesetUuid)) {
			long changesetCollectionId = MapUtil.getLong(
				parameterMap, "changesetCollectionId");

			ChangesetCollection changesetCollection = null;

			if (changesetCollectionId > 0) {
				changesetCollection =
					_changesetCollectionLocalService.getChangesetCollection(
						changesetCollectionId);
			}
			else if (ExportImportDateUtil.isRangeFromLastPublishDate(
						portletDataContext)) {

				changesetCollection =
					_changesetCollectionLocalService.fetchChangesetCollection(
						portletDataContext.getScopeGroupId(),
						StagingConstants.
							RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);
			}

			if (changesetCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"ChangesetCollection is empty, there is nothing to " +
							"export");
				}

				return getExportDataRootElementString(rootElement);
			}

			_exportChangesetCollection(portletDataContext, changesetCollection);
		}

		Optional<Changeset> changesetOptional = _changesetManager.popChangeset(
			changesetUuid);

		if (!changesetOptional.isPresent()) {
			return getExportDataRootElementString(rootElement);
		}

		Changeset changeset = changesetOptional.get();

		Stream<StagedModel> stream = changeset.stream();

		stream.filter(
			Objects::nonNull
		).forEach(
			stagedModel -> {
				try {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, stagedModel);
				}
				catch (PortletDataException pde) {
					throw new ExportImportRuntimeException(pde);
				}
			}
		);

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		List<Element> entityTypeElements = importDataRootElement.elements();

		for (Element entityTypeElement : entityTypeElements) {
			List<Element> entityElements = entityTypeElement.elements();

			for (Element entityElement : entityElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, entityElement);
			}
		}

		return portletPreferences;
	}

	private void _exportChangesetCollection(
			PortletDataContext portletDataContext,
			ChangesetCollection changesetCollection)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_changesetEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"changesetCollectionId",
					changesetCollection.getChangesetCollectionId())));

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ChangesetEntry>)
				changesetEntry -> _exportStagedModel(
					portletDataContext, changesetEntry));

		actionableDynamicQuery.performActions();
	}

	private boolean _exportStagedModel(
			PortletDataContext portletDataContext,
			ChangesetEntry changesetEntry)
		throws PortalException {

		ClassName className = _classNameLocalService.getClassName(
			changesetEntry.getClassNameId());

		StagedModelRepository<?> stagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				className.getValue());

		if (stagedModelRepository == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Staged model repository not found for " +
						className.getValue());
			}

			return false;
		}

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean exportModel = MapUtil.getBoolean(
			parameterMap, className.getValue());

		if (!exportModel) {
			return false;
		}

		StagedModel stagedModel = stagedModelRepository.getStagedModel(
			changesetEntry.getClassPK());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetPortletDataHandler.class);

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ChangesetManager _changesetManager;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}