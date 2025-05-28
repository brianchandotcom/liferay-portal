/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.internal.resource.v1_0;

import com.liferay.headless.object.dto.v1_0.Collaborator;
import com.liferay.headless.object.resource.v1_0.CollaboratorResource;
import com.liferay.headless.object.util.v1_0.CollaboratorUtil;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alicia García
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/collaborator.properties",
	scope = ServiceScope.PROTOTYPE, service = CollaboratorResource.class
)
public class CollaboratorResourceImpl extends BaseCollaboratorResourceImpl {

	@Override
	public void deleteObjectEntryFolderCollaboratorByTypeCollaborator(
			Long objectEntryFolderId, String type, Long collaboratorId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId);

		CollaboratorUtil.deleteCollaborator(
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaboratorId,
			_sharingEntryService, type);
	}

	@Override
	public void
			deleteScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaborator(
				String scopeKey, String externalReferenceCode, String type,
				Long collaboratorId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode,
					CollaboratorUtil.getGroupId(
						contextCompany.getCompanyId(), _groupLocalService,
						scopeKey),
					contextCompany.getCompanyId());

		CollaboratorUtil.deleteCollaborator(
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaboratorId,
			_sharingEntryService, type);
	}

	@Override
	public Collaborator getObjectEntryFolderCollaboratorByTypeCollaborator(
			Long objectEntryFolderId, String type, Long collaboratorId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId);

		return CollaboratorUtil.getCollaborator(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaboratorId,
			_collaboratorDTOConverter, _dtoConverterRegistry,
			_sharingEntryService, type, contextUriInfo, contextUser);
	}

	@Override
	public Page<Collaborator> getObjectEntryFolderCollaboratorsPage(
			Long objectEntryFolderId, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId);

		return CollaboratorUtil.getCollaborators(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(),
			_collaboratorDTOConverter, _dtoConverterRegistry,
			objectEntryFolder.getGroupId(), pagination,
			_sharingEntryLocalService, _sharingEntryService, contextUriInfo,
			contextUser);
	}

	@Override
	public Collaborator
			getScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaborator(
				String scopeKey, String externalReferenceCode, String type,
				Long collaboratorId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode,
					CollaboratorUtil.getGroupId(
						contextCompany.getCompanyId(), _groupLocalService,
						scopeKey),
					contextCompany.getCompanyId());

		return CollaboratorUtil.getCollaborator(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaboratorId,
			_collaboratorDTOConverter, _dtoConverterRegistry,
			_sharingEntryService, type, contextUriInfo, contextUser);
	}

	@Override
	public Page<Collaborator>
			getScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage(
				String scopeKey, String externalReferenceCode,
				Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode,
					CollaboratorUtil.getGroupId(
						contextCompany.getCompanyId(), _groupLocalService,
						scopeKey),
					contextCompany.getCompanyId());

		return CollaboratorUtil.getCollaborators(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(),
			_collaboratorDTOConverter, _dtoConverterRegistry,
			objectEntryFolder.getGroupId(), pagination,
			_sharingEntryLocalService, _sharingEntryService, contextUriInfo,
			contextUser);
	}

	@Override
	public Page<Collaborator> postObjectEntryFolderCollaboratorsPage(
			Long objectEntryFolderId, Collaborator[] collaborators)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId);

		return CollaboratorUtil.addOrUpdateCollaborators(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaborators,
			_collaboratorDTOConverter, _dtoConverterRegistry,
			objectEntryFolder.getGroupId(), _sharingEntryService,
			contextUriInfo, contextUser, _userGroupLocalService,
			_userLocalService);
	}

	@Override
	public Page<Collaborator>
			postScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage(
				String scopeKey, String externalReferenceCode,
				Collaborator[] collaborators)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode,
					CollaboratorUtil.getGroupId(
						contextCompany.getCompanyId(), _groupLocalService,
						scopeKey),
					contextCompany.getCompanyId());

		return CollaboratorUtil.addOrUpdateCollaborators(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaborators,
			_collaboratorDTOConverter, _dtoConverterRegistry,
			objectEntryFolder.getGroupId(), _sharingEntryService,
			contextUriInfo, contextUser, _userGroupLocalService,
			_userLocalService);
	}

	@Override
	public Collaborator putObjectEntryFolderCollaboratorByTypeCollaborator(
			Long objectEntryFolderId, String type, Long collaboratorId,
			Collaborator collaborator)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId);

		return CollaboratorUtil.addOrUpdateCollaborator(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaborator,
			collaboratorId, _collaboratorDTOConverter, _dtoConverterRegistry,
			objectEntryFolder.getGroupId(), _sharingEntryService, type,
			_userGroupLocalService, contextUriInfo, contextUser,
			_userLocalService);
	}

	@Override
	public Collaborator
			putScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaborator(
				String scopeKey, String externalReferenceCode, String type,
				Long collaboratorId, Collaborator collaborator)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode,
					CollaboratorUtil.getGroupId(
						contextCompany.getCompanyId(), _groupLocalService,
						scopeKey),
					contextCompany.getCompanyId());

		return CollaboratorUtil.addOrUpdateCollaborator(
			contextAcceptLanguage,
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaborator,
			collaboratorId, _collaboratorDTOConverter, _dtoConverterRegistry,
			objectEntryFolder.getGroupId(), _sharingEntryService, type,
			_userGroupLocalService, contextUriInfo, contextUser,
			_userLocalService);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.object.internal.dto.v1_0.converter.CollaboratorDTOConverter)"
	)
	private DTOConverter<SharingEntry, Collaborator> _collaboratorDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingEntryService _sharingEntryService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}