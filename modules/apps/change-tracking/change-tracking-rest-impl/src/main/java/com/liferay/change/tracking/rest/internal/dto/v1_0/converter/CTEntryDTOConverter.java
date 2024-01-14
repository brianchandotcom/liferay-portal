/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.dto.v1_0.converter;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.rest.dto.v1_0.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.Status;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;

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

	private String _getLocalizedValue(Field field, Locale locale) {
		if (field == null) {
			return StringPool.BLANK;
		}

		return MapUtil.getWithFallbackKey(
			field.getLocalizedValues(), locale, LocaleUtil.getDefault());
	}

	private <T extends BaseModel<T>> CTEntry _toCTEntry(
			DTOConverterContext dtoConverterContext,
			com.liferay.change.tracking.model.CTEntry ctEntry)
		throws Exception {

		Indexer<com.liferay.change.tracking.model.CTEntry> indexer =
			_indexerRegistry.getIndexer(
				com.liferay.change.tracking.model.CTEntry.class);

		Document document = indexer.getDocument(ctEntry);

		return new CTEntry() {
			{
				setActions(dtoConverterContext::getActions);
				setChangeType(
					() -> _language.get(
						dtoConverterContext.getLocale(),
						CTConstants.getCTChangeTypeLabel(
							GetterUtil.getInteger(
								document.get("changeType")))));
				setCtCollectionId(ctEntry::getCtCollectionId);
				setDateCreated(ctEntry::getCreateDate);
				setDateModified(ctEntry::getModifiedDate);
				setHideable(
					() -> GetterUtil.getBoolean(document.get("hideable")));
				setId(ctEntry::getCtEntryId);
				setModelClassNameId(ctEntry::getModelClassNameId);
				setModelClassPK(ctEntry::getModelClassPK);
				setOwnerId(ctEntry::getUserId);
				setOwnerName(ctEntry::getUserName);
				setSiteId(
					() -> {
						if (document.hasField(Field.GROUP_ID)) {
							return GetterUtil.getLong(
								document.get(Field.GROUP_ID));
						}

						return null;
					});
				setSiteName(
					() -> {
						if (document.hasField("groupName")) {
							return _getLocalizedValue(
								document.getField("groupName"),
								dtoConverterContext.getLocale());
						}

						return null;
					});
				setStatus(
					() -> _toStatus(dtoConverterContext.getLocale(), document));
				setTitle(
					() -> _getLocalizedValue(
						document.getField("title"),
						dtoConverterContext.getLocale()));
				setTypeName(
					() -> _getLocalizedValue(
						document.getField("typeName"),
						dtoConverterContext.getLocale()));
			}
		};
	}

	private Status _toStatus(Locale locale, Document document)
		throws Exception {

		if (!document.hasField(Field.STATUS)) {
			return null;
		}

		int status = Integer.valueOf(document.get(Field.STATUS));

		String statusLabel = WorkflowConstants.getStatusLabel(status);

		return new Status() {
			{
				setCode(() -> status);
				setLabel(() -> statusLabel);
				setLabel_i18n(() -> _language.get(locale, statusLabel));
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Language _language;

}