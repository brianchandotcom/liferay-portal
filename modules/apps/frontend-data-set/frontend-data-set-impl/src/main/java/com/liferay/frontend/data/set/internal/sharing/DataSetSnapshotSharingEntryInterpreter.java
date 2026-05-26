/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.sharing;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;

import java.io.Serializable;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Juanjo Fernandez
 */
public class DataSetSnapshotSharingEntryInterpreter
	implements SharingEntryInterpreter {

	public DataSetSnapshotSharingEntryInterpreter(
		CompanyLocalService companyLocalService, Language language,
		ObjectEntryLocalService objectEntryLocalService) {

		_companyLocalService = companyLocalService;
		_language = language;
		_objectEntryLocalService = objectEntryLocalService;
	}

	@Override
	public String getAssetTypeTitle(SharingEntry sharingEntry, Locale locale) {
		return _language.get(locale, "data-set-user-view");
	}

	@Override
	public SharingEntryEditRenderer getSharingEntryEditRenderer() {
		return (sharingEntry, liferayPortletRequest, liferayPortletResponse) ->
			null;
	}

	@Override
	public SharingEntryViewRenderer getSharingEntryViewRenderer() {
		return (sharingEntry, httpServletRequest, httpServletResponse) -> {
		};
	}

	@Override
	public String getTitle(SharingEntry sharingEntry, Locale locale) {
		return _getLabel(sharingEntry);
	}

	@Override
	public Map<Locale, String> getTitleMap(SharingEntry sharingEntry) {
		String label = _getLabel(sharingEntry);

		if (label.isEmpty()) {
			return Collections.emptyMap();
		}

		try {
			Company company = _companyLocalService.getCompany(
				sharingEntry.getCompanyId());

			return Collections.singletonMap(company.getLocale(), label);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return Collections.emptyMap();
		}
	}

	@Override
	public boolean isVisible(SharingEntry sharingEntry) {
		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			sharingEntry.getClassPK());

		if (objectEntry != null) {
			return true;
		}

		return false;
	}

	private String _getLabel(SharingEntry sharingEntry) {
		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			sharingEntry.getClassPK());

		if (objectEntry == null) {
			return StringPool.BLANK;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		Serializable label = values.get("label");

		if (label == null) {
			return StringPool.BLANK;
		}

		return String.valueOf(label);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetSnapshotSharingEntryInterpreter.class);

	private final CompanyLocalService _companyLocalService;
	private final Language _language;
	private final ObjectEntryLocalService _objectEntryLocalService;

}