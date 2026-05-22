/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.guardrail.ModelArmorTemplateHandler;
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

		_modelArmorTemplateHandler.deleteModelArmorTemplate(
			companyId, externalReferenceCode,
			GetterUtil.getString(objectEntry.getPropertyValue("location")));

		_objectEntryManager.deleteObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);
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

		ObjectEntry savedObjectEntry = _updateObjectEntry(
			accountEntry.getAccountEntryId(), companyId, dtoConverterContext,
			externalReferenceCode, modelArmorTemplate, objectDefinition);

		if (existingObjectEntry == null) {
			_modelArmorTemplateHandler.createModelArmorTemplate(
				companyId, externalReferenceCode,
				savedObjectEntry.getProperties());
		}
		else {
			_modelArmorTemplateHandler.updateModelArmorTemplate(
				companyId, externalReferenceCode,
				savedObjectEntry.getProperties());
		}

		return new ModelArmorTemplate() {
			{
				setActive(
					() -> GetterUtil.getBoolean(
						savedObjectEntry.getPropertyValue("active")));
				setDescription(
					() -> GetterUtil.getString(
						savedObjectEntry.getPropertyValue("description")));
				setExternalReferenceCode(
					savedObjectEntry::getExternalReferenceCode);
				setGuardrailType(
					() -> ModelArmorTemplate.GuardrailType.create(
						GetterUtil.getString(
							savedObjectEntry.getPropertyValue(
								"guardrailType"))));
				setLocation(
					() -> GetterUtil.getString(
						savedObjectEntry.getPropertyValue("location")));
				setMaliciousUriFilterEnabled(
					() -> GetterUtil.getBoolean(
						savedObjectEntry.getPropertyValue(
							"maliciousUriFilterEnabled")));
				setMultiLanguageDetectionEnabled(
					() -> GetterUtil.getBoolean(
						savedObjectEntry.getPropertyValue(
							"multiLanguageDetectionEnabled")));
				setPiAndJailbreakConfidenceLevel(
					() ->
						ModelArmorTemplate.PiAndJailbreakConfidenceLevel.create(
							GetterUtil.getString(
								savedObjectEntry.getPropertyValue(
									"piAndJailbreakConfidenceLevel"))));
				setPiAndJailbreakFilterEnabled(
					() -> GetterUtil.getBoolean(
						savedObjectEntry.getPropertyValue(
							"piAndJailbreakFilterEnabled")));
				setRaiDangerousLevel(
					() -> ModelArmorTemplate.RaiDangerousLevel.create(
						GetterUtil.getString(
							savedObjectEntry.getPropertyValue(
								"raiDangerousLevel"))));
				setRaiHarassmentLevel(
					() -> ModelArmorTemplate.RaiHarassmentLevel.create(
						GetterUtil.getString(
							savedObjectEntry.getPropertyValue(
								"raiHarassmentLevel"))));
				setRaiHateSpeechLevel(
					() -> ModelArmorTemplate.RaiHateSpeechLevel.create(
						GetterUtil.getString(
							savedObjectEntry.getPropertyValue(
								"raiHateSpeechLevel"))));
				setRaiSexuallyExplicitLevel(
					() -> ModelArmorTemplate.RaiSexuallyExplicitLevel.create(
						GetterUtil.getString(
							savedObjectEntry.getPropertyValue(
								"raiSexuallyExplicitLevel"))));
				setSdpFilterEnabled(
					() -> GetterUtil.getBoolean(
						savedObjectEntry.getPropertyValue("sdpFilterEnabled")));
				setTitle(
					() -> GetterUtil.getString(
						savedObjectEntry.getPropertyValue("title")));
				setTitle_i18n(
					() ->
						(Map<String, String>)savedObjectEntry.getPropertyValue(
							"title_i18n"));
			}
		};
	}

	private ObjectDefinition _getObjectDefinition(long companyId)
		throws Exception {

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_AI_HUB_MODEL_ARMOR_TEMPLATE", companyId);
	}

	private ObjectEntry _updateObjectEntry(
			long accountEntryId, long companyId,
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ModelArmorTemplate modelArmorTemplate,
			ObjectDefinition objectDefinition)
		throws Exception {

		return _objectEntryManager.updateObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition,
			new ObjectEntry() {
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
							String.valueOf(
								modelArmorTemplate.getGuardrailType())
						).put(
							"location",
							GetterUtil.getString(
								modelArmorTemplate.getLocation())
						).put(
							"maliciousUriFilterEnabled",
							GetterUtil.getBoolean(
								modelArmorTemplate.
									getMaliciousUriFilterEnabled())
						).put(
							"multiLanguageDetectionEnabled",
							GetterUtil.getBoolean(
								modelArmorTemplate.
									getMultiLanguageDetectionEnabled())
						).put(
							"piAndJailbreakConfidenceLevel",
							Objects.toString(
								modelArmorTemplate.
									getPiAndJailbreakConfidenceLevel(),
								null)
						).put(
							"piAndJailbreakFilterEnabled",
							GetterUtil.getBoolean(
								modelArmorTemplate.
									getPiAndJailbreakFilterEnabled())
						).put(
							"r_accountToAIHubModelArmorTemplates_" +
								"accountEntryId",
							String.valueOf(accountEntryId)
						).put(
							"raiDangerousLevel",
							Objects.toString(
								modelArmorTemplate.getRaiDangerousLevel(), null)
						).put(
							"raiHarassmentLevel",
							Objects.toString(
								modelArmorTemplate.getRaiHarassmentLevel(),
								null)
						).put(
							"raiHateSpeechLevel",
							Objects.toString(
								modelArmorTemplate.getRaiHateSpeechLevel(),
								null)
						).put(
							"raiSexuallyExplicitLevel",
							Objects.toString(
								modelArmorTemplate.
									getRaiSexuallyExplicitLevel(),
								null)
						).put(
							"sdpFilterEnabled",
							GetterUtil.getBoolean(
								modelArmorTemplate.getSdpFilterEnabled())
						).put(
							"title_i18n", modelArmorTemplate.getTitle_i18n()
						).build());
				}
			},
			null);
	}

	@Reference
	private ModelArmorTemplateHandler _modelArmorTemplateHandler;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

}