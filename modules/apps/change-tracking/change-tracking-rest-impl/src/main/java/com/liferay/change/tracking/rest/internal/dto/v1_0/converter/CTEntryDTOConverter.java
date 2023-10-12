/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.dto.v1_0.converter;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.rest.dto.v1_0.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.Status;
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "dto.class.name=com.liferay.change.tracking.model.CTEntry",
	service = DTOConverter.class
)
public class CTEntryDTOConverter
	implements DTOConverter
		<com.liferay.change.tracking.model.CTEntry, CTEntry> {

	@Override
	public String getContentType() {
		return CTEntry.class.getSimpleName();
	}

	@Override
	public CTEntry toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.change.tracking.model.CTEntry ctEntry)
		throws Exception {

		if (ctEntry == null) {
			return null;
		}

		return _toCTEntry(dtoConverterContext, ctEntry);
	}

	private Group _getGroup(long ctCollectionId, long groupId) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			return _groupLocalService.fetchGroup(groupId);
		}
	}

	private Long _getSiteId(long ctCollectionId, BaseModel<?> model) {
		if (model instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)model;

			Group group = _getGroup(ctCollectionId, groupedModel.getGroupId());

			if (group != null) {
				return group.getGroupId();
			}
		}

		return null;
	}

	private String _getSiteName(
		Locale locale, long ctCollectionId, BaseModel<?> model) {

		if (model instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)model;

			Group group = _getGroup(ctCollectionId, groupedModel.getGroupId());

			if (group != null) {
				return group.getName(locale);
			}
		}

		return null;
	}

	private <T extends BaseModel<T>> CTEntry _toCTEntry(
			DTOConverterContext dtoConverterContext,
			com.liferay.change.tracking.model.CTEntry ctEntry)
		throws Exception {

		long ctCollectionId = ctEntry.getCtCollectionId();

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_DELETION) {
			ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		T model = _ctDisplayRendererRegistry.fetchCTModel(
			ctCollectionId, CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
			ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

		return new CTEntry() {
			{
				actions = dtoConverterContext.getActions();
				changeType = _language.get(
					dtoConverterContext.getLocale(),
					CTConstants.getCTChangeTypeLabel(
						_ctDisplayRendererRegistry.getChangeType(
							ctEntry, model)));
				ctCollectionId = ctEntry.getCtCollectionId();
				dateCreated = ctEntry.getCreateDate();
				dateModified = ctEntry.getModifiedDate();
				hideable = _ctDisplayRendererRegistry.isHideable(
					model, ctEntry.getModelClassNameId());
				id = ctEntry.getCtEntryId();
				modelClassNameId = ctEntry.getModelClassNameId();
				modelClassPK = ctEntry.getModelClassPK();
				ownerId = ctEntry.getUserId();
				ownerName = ctEntry.getUserName();
				siteId = _getSiteId(ctEntry.getCtCollectionId(), model);
				siteName = _getSiteName(
					dtoConverterContext.getLocale(),
					ctEntry.getCtCollectionId(), model);
				status = _toStatus(dtoConverterContext.getLocale(), model);
				title = _ctDisplayRendererRegistry.getTitle(
					ctEntry.getCtCollectionId(), ctEntry,
					dtoConverterContext.getLocale());
				typeName = _ctDisplayRendererRegistry.getTypeName(
					dtoConverterContext.getLocale(),
					ctEntry.getModelClassNameId());
			}
		};
	}

	private Status _toStatus(Locale locale, BaseModel<?> model)
		throws Exception {

		if (model == null) {
			return null;
		}

		Map<String, Object> modelAttributes = model.getModelAttributes();

		if (!modelAttributes.containsKey("status")) {
			return null;
		}

		int status = (int)modelAttributes.get("status");

		String statusLabel = WorkflowConstants.getStatusLabel(status);

		return new Status() {
			{
				code = status;
				label = statusLabel;
				label_i18n = _language.get(locale, statusLabel);
			}
		};
	}

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}