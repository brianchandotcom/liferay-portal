/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.rest.dto.v1_0.Report;
import com.liferay.ai.hub.rest.manager.v1_0.ReportManager;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fábio Alves
 */
@Component(service = ReportManager.class)
public class ReportManagerImpl implements ReportManager {

	@Override
	public Report postReport(
			Company company, DTOConverterContext dtoConverterContext,
			Report report)
		throws Exception {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			dtoConverterContext.getUserId());

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext,
			_objectDefinitionLocalService.getObjectDefinition(
				company.getCompanyId(), "AIHubReport"),
			new ObjectEntry() {
				{
					setProperties(
						() -> HashMapBuilder.<String, Object>put(
							"feedback",
							GetterUtil.getString(report.getFeedback())
						).put(
							"level",
							_levels.getOrDefault(report.getReason(), "low")
						).put(
							"r_accountToAIHubReports_accountEntryId",
							accountEntry.getAccountEntryId()
						).put(
							"reason", GetterUtil.getString(report.getReason())
						).put(
							"surface", GetterUtil.getString(report.getSurface())
						).put(
							"userMessage",
							GetterUtil.getString(report.getUserMessage())
						).build());
				}
			},
			null);

		if (ArrayUtil.isNotEmpty(
				report.getAgentDefinitionExternalReferenceCodes())) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					company.getCompanyId(), "AIHubAgentDefinition");

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectDefinition.getObjectDefinitionId(),
					"aiHubAgentDefinitionsToAIHubReports");

			for (String agentDefinitionExternalReferenceCode :
					ArrayUtil.distinct(
						report.getAgentDefinitionExternalReferenceCodes())) {

				com.liferay.object.model.ObjectEntry
					agentDefinitionObjectEntry =
						_objectEntryLocalService.fetchObjectEntry(
							agentDefinitionExternalReferenceCode, 0L,
							objectDefinition.getObjectDefinitionId());

				if (agentDefinitionObjectEntry == null) {
					throw new NoSuchObjectEntryException(
						agentDefinitionExternalReferenceCode);
				}

				_objectRelationshipLocalService.
					addObjectRelationshipMappingTableValues(
						dtoConverterContext.getUserId(),
						objectRelationship.getObjectRelationshipId(),
						agentDefinitionObjectEntry.getObjectEntryId(),
						objectEntry.getId(), new ServiceContext());
			}
		}

		return new Report() {
			{
				setAgentDefinitionExternalReferenceCodes(
					report::getAgentDefinitionExternalReferenceCodes);
				setChatbotExternalReferenceCode(
					report::getChatbotExternalReferenceCode);
				setDateCreated(objectEntry::getDateCreated);
				setExternalReferenceCode(objectEntry::getExternalReferenceCode);
				setFeedback(report::getFeedback);
				setId(objectEntry::getId);
				setLevel(() -> _levels.getOrDefault(report.getReason(), "low"));
				setReason(report::getReason);
				setSurface(report::getSurface);
				setUserMessage(report::getUserMessage);
			}
		};
	}

	private static final Map<String, String> _levels = HashMapBuilder.put(
		"agentError", "medium"
	).put(
		"harmfulContent", "critical"
	).put(
		"incorrect", "high"
	).put(
		"piiExposure", "critical"
	).build();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}