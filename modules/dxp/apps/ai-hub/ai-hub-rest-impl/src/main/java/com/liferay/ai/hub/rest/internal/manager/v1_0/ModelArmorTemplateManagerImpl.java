/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.ai.hub.rest.dto.v1_0.ModelArmorTemplate;
import com.liferay.ai.hub.rest.manager.v1_0.ModelArmorTemplateManager;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = ModelArmorTemplateManager.class)
public class ModelArmorTemplateManagerImpl
	implements ModelArmorTemplateManager {

	@Override
	public void deleteModelArmorTemplate(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);

		_modelArmorHandler.deleteModelArmorTemplate(
			companyId, externalReferenceCode,
			GetterUtil.getString(objectEntry.getPropertyValue("location")));

		_objectEntryManager.deleteObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);
	}

	@Override
	public ModelArmorTemplate postModelArmorTemplate(
			long companyId, DTOConverterContext dtoConverterContext,
			ModelArmorTemplate modelArmorTemplate)
		throws Exception {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			dtoConverterContext.getUserId());

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _getObjectDefinition(companyId),
			_toObjectEntry(
				accountEntry.getAccountEntryId(), modelArmorTemplate),
			null);

		_modelArmorHandler.createModelArmorTemplate(
			companyId, objectEntry.getExternalReferenceCode(),
			objectEntry.getProperties());

		return _toModelArmorTemplate(objectEntry);
	}

	@Override
	public ModelArmorTemplate putModelArmorTemplate(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ModelArmorTemplate modelArmorTemplate)
		throws Exception {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			dtoConverterContext.getUserId());
		ObjectDefinition objectDefinition = _getObjectDefinition(companyId);

		DefaultObjectEntryManager defaultObjectEntryManager =
			(DefaultObjectEntryManager)_objectEntryManager;

		ObjectEntry existingObjectEntry =
			defaultObjectEntryManager.fetchObjectEntry(
				dtoConverterContext, externalReferenceCode, objectDefinition,
				null);

		ObjectEntry objectEntry = _objectEntryManager.updateObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition,
			_toObjectEntry(
				accountEntry.getAccountEntryId(), modelArmorTemplate),
			null);

		if (existingObjectEntry == null) {
			_modelArmorHandler.createModelArmorTemplate(
				companyId, externalReferenceCode, objectEntry.getProperties());
		}
		else {
			_modelArmorHandler.updateModelArmorTemplate(
				companyId, externalReferenceCode, objectEntry.getProperties());
		}

		return _toModelArmorTemplate(objectEntry);
	}

	private ObjectDefinition _getObjectDefinition(long companyId)
		throws Exception {

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_AI_HUB_MODEL_ARMOR_TEMPLATE", companyId);
	}

	private ModelArmorTemplate _toModelArmorTemplate(ObjectEntry objectEntry) {
		return new ModelArmorTemplate() {
			{
				setActive(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue("active")));
				setDescription(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("description")));
				setExternalReferenceCode(objectEntry::getExternalReferenceCode);
				setGuardrailType(
					() -> ModelArmorTemplate.GuardrailType.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue("guardrailType"))));
				setLocation(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("location")));
				setMaliciousUriFilterEnabled(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue(
							"maliciousUriFilterEnabled")));
				setMultilanguageDetectionEnabled(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue(
							"multilanguageDetectionEnabled")));
				setPiAndJailbreakConfidenceLevel(
					() ->
						ModelArmorTemplate.PiAndJailbreakConfidenceLevel.create(
							GetterUtil.getString(
								objectEntry.getPropertyValue(
									"piAndJailbreakConfidenceLevel"))));
				setPiAndJailbreakFilterEnabled(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue(
							"piAndJailbreakFilterEnabled")));
				setRaiDangerousLevel(
					() -> ModelArmorTemplate.RaiDangerousLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiDangerousLevel"))));
				setRaiHarassmentLevel(
					() -> ModelArmorTemplate.RaiHarassmentLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiHarassmentLevel"))));
				setRaiHateSpeechLevel(
					() -> ModelArmorTemplate.RaiHateSpeechLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiHateSpeechLevel"))));
				setRaiSexuallyExplicitLevel(
					() -> ModelArmorTemplate.RaiSexuallyExplicitLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiSexuallyExplicitLevel"))));
				setSdpFilterEnabled(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue("sdpFilterEnabled")));
				setTitle(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("title")));
				setTitle_i18n(
					() -> (Map<String, String>)objectEntry.getPropertyValue(
						"title_i18n"));
			}
		};
	}

	private ObjectEntry _toObjectEntry(
		long accountEntryId, ModelArmorTemplate modelArmorTemplate) {

		return new ObjectEntry() {
			{
				setExternalReferenceCode(
					modelArmorTemplate::getExternalReferenceCode);
				setProperties(
					() -> HashMapBuilder.<String, Object>put(
						"active",
						GetterUtil.getBoolean(
							modelArmorTemplate.getActive(), true)
					).put(
						"description",
						GetterUtil.getString(
							modelArmorTemplate.getDescription())
					).put(
						"guardrailType",
						String.valueOf(modelArmorTemplate.getGuardrailType())
					).put(
						"location", modelArmorTemplate.getLocation()
					).put(
						"maliciousUriFilterEnabled",
						GetterUtil.getBoolean(
							modelArmorTemplate.getMaliciousUriFilterEnabled())
					).put(
						"multilanguageDetectionEnabled",
						GetterUtil.getBoolean(
							modelArmorTemplate.
								getMultilanguageDetectionEnabled())
					).put(
						"piAndJailbreakConfidenceLevel",
						Objects.toString(
							modelArmorTemplate.
								getPiAndJailbreakConfidenceLevel(),
							null)
					).put(
						"piAndJailbreakFilterEnabled",
						GetterUtil.getBoolean(
							modelArmorTemplate.getPiAndJailbreakFilterEnabled())
					).put(
						"r_accountToAIHubModelArmorTemplates_accountEntryId",
						String.valueOf(accountEntryId)
					).put(
						"raiDangerousLevel",
						Objects.toString(
							modelArmorTemplate.getRaiDangerousLevel(), null)
					).put(
						"raiHarassmentLevel",
						Objects.toString(
							modelArmorTemplate.getRaiHarassmentLevel(), null)
					).put(
						"raiHateSpeechLevel",
						Objects.toString(
							modelArmorTemplate.getRaiHateSpeechLevel(), null)
					).put(
						"raiSexuallyExplicitLevel",
						Objects.toString(
							modelArmorTemplate.getRaiSexuallyExplicitLevel(),
							null)
					).put(
						"sdpFilterEnabled",
						GetterUtil.getBoolean(
							modelArmorTemplate.getSdpFilterEnabled())
					).put(
						"title_i18n", modelArmorTemplate.getTitle_i18n()
					).build());
			}
		};
	}

	@Reference
	private ModelArmorHandler _modelArmorHandler;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

}