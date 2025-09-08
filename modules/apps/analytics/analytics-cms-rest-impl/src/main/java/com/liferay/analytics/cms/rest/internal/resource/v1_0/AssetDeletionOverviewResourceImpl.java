/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.AssetDeletionOverview;
import com.liferay.analytics.cms.rest.resource.v1_0.AssetDeletionOverviewResource;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryVersion;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryVersionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Thiago Buarque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asset-deletion-overview.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AssetDeletionOverviewResource.class
)
public class AssetDeletionOverviewResourceImpl
	extends BaseAssetDeletionOverviewResourceImpl {

	@Override
	public Page<AssetDeletionOverview> postAssetDeletionOverviewsPage(
			String languageId, Pagination pagination, Long[] assetIds)
		throws Exception {

		List<AssetDeletionOverview> assetDeletionOverviews = new ArrayList<>();

		for (Long assetId :
				ListUtil.subList(
					ListUtil.fromArray(assetIds), pagination.getStartPosition(),
					pagination.getEndPosition())) {

			AssetDeletionOverview assetDeletionOverview =
				new AssetDeletionOverview();

			assetDeletionOverview.setDeletionType(
				() -> AssetDeletionOverview.DeletionType.PERMANENT_DELETION);

			assetDeletionOverview.setId(() -> assetId);

			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				assetId);

			if (objectEntry == null) {
				throw new NotFoundException("Unable to find asset " + assetId);
			}

			try {
				_depotEntryService.getGroupDepotEntry(objectEntry.getGroupId());
			}
			catch (PrincipalException.MustHavePermission principalException) {
				throw new NotAuthorizedException(
					StringBundler.concat(
						"User ", contextUser.getUserId(),
						" does not have permission to view asset ", assetId),
					Response.status(
						Response.Status.UNAUTHORIZED
					).build(),
					principalException);
			}

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());

			assetDeletionOverview.setMimeType(
				() -> _getMimeType(objectDefinition, objectEntry));

			assetDeletionOverview.setTitle(
				() -> {
					if (languageId == null) {
						return objectEntry.getTitleValue(
							contextUser.getLanguageId(), true);
					}

					return objectEntry.getTitleValue(languageId, true);
				});

			assetDeletionOverview.setUsages(
				() -> _getUsages(
					_portal.getClassNameId(objectDefinition.getClassName()),
					objectDefinition.getClassName(),
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getObjectEntryId()));

			assetDeletionOverviews.add(assetDeletionOverview);
		}

		return Page.of(assetDeletionOverviews, pagination, assetIds.length);
	}

	private String _getMimeType(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws PortalException {

		if (Objects.equals(
				objectDefinition.getExternalReferenceCode(),
				"L_BASIC_WEB_CONTENT")) {

			return "basic-web-content";
		}
		else if (Objects.equals(
					objectDefinition.getExternalReferenceCode(), "L_BLOG")) {

			return "blog";
		}
		else if (Objects.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_KNOWLEDGE_BASE")) {

			return "knowledge-base";
		}

		ObjectEntryVersion objectEntryVersion =
			_objectEntryVersionLocalService.getObjectEntryVersion(
				objectEntry.getObjectEntryId(), objectEntry.getVersion());

		JSONObject contentJSONObject = _jsonFactory.createJSONObject(
			objectEntryVersion.getContent());

		JSONObject propertiesJSONObject = contentJSONObject.getJSONObject(
			"properties");

		JSONObject fileJSONObject = propertiesJSONObject.getJSONObject("file");

		if (fileJSONObject != null) {
			return _dlMimeTypeDisplayContext.getIconFileMimeType(
				fileJSONObject.getString("mimeType"));
		}

		return "custom-structure";
	}

	private int _getUsages(
			long classNameId, String entryClassName, long objectDefinitionId,
			long objectEntryId)
		throws PortalException {

		int usages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesCount(classNameId, objectEntryId);

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.
				getObjectRelationshipsByObjectDefinitionId2(objectDefinitionId);

		boolean skipObjectEntryResourcePermission =
			ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission();

		try {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(true);

			for (ObjectRelationship objectRelationship : objectRelationships) {
				ObjectRelatedModelsProvider objectRelatedModelsProvider =
					_objectRelatedModelsProviderRegistry.
						getObjectRelatedModelsProvider(
							entryClassName, contextCompany.getCompanyId(),
							objectRelationship.getType());

				usages += objectRelatedModelsProvider.getRelatedModelsCount(
					0, objectRelationship.getObjectRelationshipId(), null,
					objectEntryId, null);
			}
		}
		finally {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(
				skipObjectEntryResourcePermission);
		}

		return usages;
	}

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryVersionLocalService _objectEntryVersionLocalService;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private Portal _portal;

}