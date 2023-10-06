/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.change.tracking.model.CTEntry",
	service = ModelDocumentContributor.class
)
public class CTEntryModelDocumentContributor
	implements ModelDocumentContributor<CTEntry> {

	@Override
	public void contribute(Document document, CTEntry ctEntry) {
		document.addKeyword(Field.COMPANY_ID, ctEntry.getCompanyId());
		document.addDate(Field.CREATE_DATE, ctEntry.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, ctEntry.getModifiedDate());

		User user = _userLocalService.fetchUser(ctEntry.getUserId());

		if (user != null) {
			document.addKeyword(Field.USER_ID, user.getUserId());
			document.addText(Field.USER_NAME, user.getFullName());
		}

		document.addKeyword("ctCollectionId", ctEntry.getCtCollectionId());
		document.addKeyword("modelClassNameId", ctEntry.getModelClassNameId());
		document.addKeyword("modelClassPK", ctEntry.getModelClassPK());

		_indexModelAttributes(document, ctEntry);
	}

	private <T extends BaseModel<T>> Locale[] _getAvailableLocales(
		T model, CTDisplayRenderer ctDisplayRenderer) {

		String[] languageIds = new String[0];

		if (model != null) {
			languageIds = ctDisplayRenderer.getAvailableLanguageIds(model);
		}

		if (ArrayUtil.isNotEmpty(languageIds)) {
			return LocaleUtil.fromLanguageIds(languageIds);
		}

		Set<Locale> locales = _language.getAvailableLocales();

		return locales.toArray(new Locale[0]);
	}

	private Map<Locale, String> _getChangeTypeLabelMap(
		Locale[] locales, int changeType) {

		Map<Locale, String> map = new HashMap<>();

		String changeTypeLabel = CTConstants.getCTChangeTypeLabel(changeType);

		for (Locale locale : locales) {
			map.put(locale, _language.get(locale, changeTypeLabel));
		}

		return map;
	}

	private <T extends BaseModel<T>> Map<Locale, String> _getTitleMap(
		CTEntry ctEntry, Locale[] locales) {

		Map<Locale, String> map = new HashMap<>();

		for (Locale locale : locales) {
			map.put(
				locale,
				_ctDisplayRendererRegistry.getTitle(
					ctEntry.getCtCollectionId(), ctEntry, locale));
		}

		return map;
	}

	private Map<Locale, String> _getTypeNameMap(
		Locale[] locales, long modelClassNameId) {

		Map<Locale, String> map = new HashMap<>();

		for (Locale locale : locales) {
			map.put(
				locale,
				_ctDisplayRendererRegistry.getTypeName(
					locale, modelClassNameId));
		}

		return map;
	}

	private <T extends BaseModel<T>> void _indexModelAttributes(
		Document document, CTEntry ctEntry) {

		long ctCollectionId = ctEntry.getCtCollectionId();

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_DELETION) {
			ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		T model = _ctDisplayRendererRegistry.fetchCTModel(
			ctCollectionId, CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
			ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

		int changeType = _ctDisplayRendererRegistry.getChangeType(
			ctEntry, model);

		document.addKeyword("changeType", changeType);

		Locale[] locales = _getAvailableLocales(
			model,
			_ctDisplayRendererRegistry.getCTDisplayRenderer(
				ctEntry.getModelClassNameId()));

		document.addLocalizedText(
			"changeTypeLabel", _getChangeTypeLabelMap(locales, changeType),
			true);

		document.addLocalizedText(
			"typeName", _getTypeNameMap(locales, ctEntry.getModelClassNameId()),
			true);

		if (model == null) {
			return;
		}

		if (model instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)model;

			Group group = _groupLocalService.fetchGroup(
				groupedModel.getGroupId());

			if (group != null) {
				document.addKeyword(Field.GROUP_ID, group.getGroupId());
				document.addLocalizedKeyword(
					Field.getSortableFieldName("groupName"), group.getNameMap(),
					true, true);
			}
		}

		document.addLocalizedText(
			Field.TITLE, _getTitleMap(ctEntry, locales), true);

		document.addKeyword(
			"hideable",
			_ctDisplayRendererRegistry.isHideable(
				model, ctEntry.getModelClassNameId()));

		Map<String, Object> modelAttributes = model.getModelAttributes();

		if (modelAttributes.containsKey("status")) {
			document.addKeyword(
				"workflowStatus", (Integer)modelAttributes.get("status"));
		}
	}

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}