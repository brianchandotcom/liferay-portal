/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.dto.v1_0.converter;

import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.rest.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.Status;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Date;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "dto.class.name=com.liferay.change.tracking.model.CTCollection",
	service = DTOConverter.class
)
public class CTCollectionDTOConverter
	implements DTOConverter
		<com.liferay.change.tracking.model.CTCollection, CTCollection> {

	@Override
	public String getContentType() {
		return CTCollection.class.getSimpleName();
	}

	@Override
	public CTCollection toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.change.tracking.model.CTCollection ctCollection)
		throws Exception {

		if (ctCollection == null) {
			return null;
		}

		return new CTCollection() {
			{
				actions = dtoConverterContext.getActions();
				dateCreated = ctCollection.getCreateDate();
				dateModified = ctCollection.getModifiedDate();
				dateScheduled = _getDateScheduled(ctCollection);
				description = ctCollection.getDescription();
				externalReferenceCode = ctCollection.getExternalReferenceCode();
				id = ctCollection.getCtCollectionId();
				name = ctCollection.getName();
				ownerName = ctCollection.getUserName();
				status = _toStatus(
					dtoConverterContext.getLocale(), ctCollection.getStatus());
			}
		};
	}

	private Date _getDateScheduled(
			com.liferay.change.tracking.model.CTCollection ctCollection)
		throws Exception {

		if (ctCollection.getStatus() != WorkflowConstants.STATUS_SCHEDULED) {
			return null;
		}

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				String.valueOf(ctCollection.getCtCollectionId()),
				CTDestinationNames.CT_COLLECTION_SCHEDULED_PUBLISH,
				StorageType.PERSISTED);

		if (schedulerResponse == null) {
			return null;
		}

		return _schedulerEngineHelper.getStartTime(schedulerResponse);
	}

	private Status _toStatus(Locale locale, int status) throws Exception {
		String statusLabel;

		if (status == WorkflowConstants.STATUS_APPROVED) {
			statusLabel = "published";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			statusLabel = "out-of-date";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			statusLabel = "in-progress";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			statusLabel = "failed";
		}
		else if (status == WorkflowConstants.STATUS_SCHEDULED) {
			statusLabel = "scheduled";
		}
		else {
			statusLabel = StringPool.BLANK;
		}

		return new Status() {
			{
				code = status;
				label = statusLabel;
				label_i18n = _language.get(locale, statusLabel);
			}
		};
	}

	@Reference
	private Language _language;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}