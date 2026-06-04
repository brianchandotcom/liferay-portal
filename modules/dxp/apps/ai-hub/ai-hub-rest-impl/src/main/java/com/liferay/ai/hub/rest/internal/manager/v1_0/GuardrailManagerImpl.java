/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.ai.hub.rest.dto.v1_0.Guardrail;
import com.liferay.ai.hub.rest.manager.v1_0.GuardrailManager;
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
@Component(service = GuardrailManager.class)
public class GuardrailManagerImpl implements GuardrailManager {

	@Override
	public void deleteGuardrail(
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
	public Guardrail postGuardrail(
			long companyId, DTOConverterContext dtoConverterContext,
			Guardrail guardrail)
		throws Exception {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			dtoConverterContext.getUserId());

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _getObjectDefinition(companyId),
			_toObjectEntry(accountEntry.getAccountEntryId(), guardrail), null);

		_modelArmorHandler.createModelArmorTemplate(
			companyId, objectEntry.getExternalReferenceCode(),
			objectEntry.getProperties());

		return _toGuardrail(objectEntry);
	}

	@Override
	public Guardrail putGuardrail(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, Guardrail guardrail)
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
			_toObjectEntry(accountEntry.getAccountEntryId(), guardrail), null);

		if (existingObjectEntry == null) {
			_modelArmorHandler.createModelArmorTemplate(
				companyId, externalReferenceCode, objectEntry.getProperties());
		}
		else {
			_modelArmorHandler.updateModelArmorTemplate(
				companyId, externalReferenceCode, objectEntry.getProperties());
		}

		return _toGuardrail(objectEntry);
	}

	private ObjectDefinition _getObjectDefinition(long companyId)
		throws Exception {

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_AI_HUB_GUARDRAIL", companyId);
	}

	private Guardrail _toGuardrail(ObjectEntry objectEntry) {
		return new Guardrail() {
			{
				setActive(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue("active")));
				setDescription(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("description")));
				setExternalReferenceCode(objectEntry::getExternalReferenceCode);
				setGuardrailType(
					() -> Guardrail.GuardrailType.create(
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
					() -> Guardrail.PiAndJailbreakConfidenceLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"piAndJailbreakConfidenceLevel"))));
				setPiAndJailbreakFilterEnabled(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue(
							"piAndJailbreakFilterEnabled")));
				setRaiDangerousLevel(
					() -> Guardrail.RaiDangerousLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiDangerousLevel"))));
				setRaiHarassmentLevel(
					() -> Guardrail.RaiHarassmentLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiHarassmentLevel"))));
				setRaiHateSpeechLevel(
					() -> Guardrail.RaiHateSpeechLevel.create(
						GetterUtil.getString(
							objectEntry.getPropertyValue(
								"raiHateSpeechLevel"))));
				setRaiSexuallyExplicitLevel(
					() -> Guardrail.RaiSexuallyExplicitLevel.create(
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
		long accountEntryId, Guardrail guardrail) {

		return new ObjectEntry() {
			{
				setExternalReferenceCode(guardrail::getExternalReferenceCode);
				setProperties(
					() -> HashMapBuilder.<String, Object>put(
						"active",
						GetterUtil.getBoolean(guardrail.getActive(), true)
					).put(
						"description",
						GetterUtil.getString(guardrail.getDescription())
					).put(
						"guardrailType",
						String.valueOf(guardrail.getGuardrailType())
					).put(
						"location", guardrail.getLocation()
					).put(
						"maliciousUriFilterEnabled",
						GetterUtil.getBoolean(
							guardrail.getMaliciousUriFilterEnabled())
					).put(
						"multilanguageDetectionEnabled",
						GetterUtil.getBoolean(
							guardrail.getMultilanguageDetectionEnabled())
					).put(
						"piAndJailbreakConfidenceLevel",
						Objects.toString(
							guardrail.getPiAndJailbreakConfidenceLevel(), null)
					).put(
						"piAndJailbreakFilterEnabled",
						GetterUtil.getBoolean(
							guardrail.getPiAndJailbreakFilterEnabled())
					).put(
						"r_accountToAIHubGuardrails_accountEntryId",
						String.valueOf(accountEntryId)
					).put(
						"raiDangerousLevel",
						Objects.toString(guardrail.getRaiDangerousLevel(), null)
					).put(
						"raiHarassmentLevel",
						Objects.toString(
							guardrail.getRaiHarassmentLevel(), null)
					).put(
						"raiHateSpeechLevel",
						Objects.toString(
							guardrail.getRaiHateSpeechLevel(), null)
					).put(
						"raiSexuallyExplicitLevel",
						Objects.toString(
							guardrail.getRaiSexuallyExplicitLevel(), null)
					).put(
						"sdpFilterEnabled",
						GetterUtil.getBoolean(guardrail.getSdpFilterEnabled())
					).put(
						"title_i18n", guardrail.getTitle_i18n()
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