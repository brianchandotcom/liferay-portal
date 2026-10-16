/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.service;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.exception.ObjectDefinitionObjectFolderIdException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceWrapper;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ServiceWrapper.class)
public class CMSObjectDefinitionLocalServiceWrapper
	extends ObjectDefinitionLocalServiceWrapper {

	@Override
	public ObjectDefinition addCustomObjectDefinition(
			String externalReferenceCode, long userId, long objectFolderId,
			String className, Map<Locale, String> descriptionMap,
			boolean enableCategorization, boolean enableComments,
			boolean enableFormContainer, boolean enableFriendlyURLCustomization,
			boolean enableIndexSearch, boolean enableObjectEntryDraft,
			boolean enableObjectEntrySchedule,
			boolean enableObjectEntrySubscription,
			boolean enableObjectEntryVersioning, String friendlyURLSeparator,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			boolean portlet, String scope, String storageType,
			List<ObjectDefinitionSetting> objectDefinitionSettings,
			List<ObjectField> objectFields,
			List<WorkflowDefinitionLink> workflowDefinitionLinks,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(objectFolderId, scope);

		return super.addCustomObjectDefinition(
			externalReferenceCode, userId, objectFolderId, className,
			descriptionMap, enableCategorization, enableComments,
			enableFormContainer, enableFriendlyURLCustomization,
			enableIndexSearch, enableObjectEntryDraft,
			enableObjectEntrySchedule, enableObjectEntrySubscription,
			enableObjectEntryVersioning, friendlyURLSeparator, labelMap, name,
			panelAppOrder, panelCategoryKey, pluralLabelMap, portlet, scope,
			storageType, objectDefinitionSettings, objectFields,
			workflowDefinitionLinks, serviceContext);
	}

	@Override
	public ObjectDefinition addObjectDefinition(
			String externalReferenceCode, long userId, long objectFolderId,
			boolean modifiable, String scope, boolean system)
		throws PortalException {

		if (!system &&
			!externalReferenceCode.startsWith(
				ObjectDefinitionConstants.
					EXTERNAL_REFERENCE_CODE_PREFIX_SYSTEM_OBJECT_DEFINITION)) {

			_validate(objectFolderId, scope);
		}

		return super.addObjectDefinition(
			externalReferenceCode, userId, objectFolderId, modifiable, scope,
			system);
	}

	private void _validate(long objectFolderId, String scope)
		throws PortalException {

		if (!Objects.equals(scope, ObjectDefinitionConstants.SCOPE_DEPOT)) {
			throw new ObjectDefinitionScopeException(
				"Custom object definitions must have depot scope");
		}

		if (objectFolderId == 0) {
			throw new ObjectDefinitionObjectFolderIdException(
				"Custom object definitions must be in a CMS object folder");
		}

		ObjectFolder objectFolder = _objectFolderLocalService.getObjectFolder(
			objectFolderId);

		if (!_externalReferenceCodes.contains(
				objectFolder.getExternalReferenceCode())) {

			throw new ObjectDefinitionObjectFolderIdException(
				"Custom object definitions must be in a CMS object folder");
		}
	}

	private final Set<String> _externalReferenceCodes = SetUtil.fromArray(
		ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
		ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
		ObjectFolderConstants.
			EXTERNAL_REFERENCE_CODE_STRUCTURE_REPEATABLE_GROUPS);

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

}