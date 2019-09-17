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

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
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
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.TypedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
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
 * @author Máté Thurzó
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
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		Object[] stagedModelTypesStrings = parameterMap.get("stagedModelTypes");

		if (ArrayUtil.isEmpty(stagedModelTypesStrings)) {
			return super.exportData(
				portletDataContext, portletId, portletPreferences);
		}

		StagedModelType[] stagedModelTypes =
			new StagedModelType[stagedModelTypesStrings.length];

		for (int i = 0; i < stagedModelTypesStrings.length; i++) {
			stagedModelTypes[i] = StagedModelType.parse(
				String.valueOf(stagedModelTypesStrings[i]));
		}

		setDeletionSystemEventStagedModelTypes(stagedModelTypes);

		return super.exportData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
	}

	@Override
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
		else {
			Optional<Changeset> changesetOptional =
				_changesetManager.popChangeset(changesetUuid);

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
		}

		_exportAssetLinks(portletDataContext);

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

	private void _exportAssetLinks(PortletDataContext portletDataContext)
		throws PortletDataException {

		for (Long linkId : portletDataContext.getAssetLinkIds()) {
			AssetLink assetLink = _assetLinkLocalService.fetchAssetLink(linkId);

			if (assetLink == null) {
				continue;
			}

			StagedAssetLink stagedAssetLink = ModelAdapterUtil.adapt(
				assetLink, AssetLink.class, StagedAssetLink.class);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedAssetLink);
		}
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

		StagedModel stagedModel = stagedModelRepository.getStagedModel(
			changesetEntry.getClassPK());

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean exportModel = _isExportModel(
			portletDataContext, className.getValue());

		if (!exportModel) {
			if (!(stagedModel instanceof TypedModel)) {
				return false;
			}

			TypedModel typedModel = (TypedModel)stagedModel;

			String referrerClassName = typedModel.getClassName();

			boolean exportTypedModel = MapUtil.getBoolean(
				parameterMap,
				className.getValue() + StringPool.POUND + referrerClassName);

			if (!exportTypedModel) {
				return false;
			}
		}

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		return true;
	}

	private boolean _isExportModel(
		PortletDataContext portletDataContext, String className) {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean exportModel = MapUtil.getBoolean(parameterMap, className);

		if (exportModel) {
			return true;
		}

		return MapUtil.getBoolean(
			parameterMap,
			className + StringPool.POUND +
				StagedModelType.REFERRER_CLASS_NAME_ALL);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetPortletDataHandler.class);

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ChangesetManager _changesetManager;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}