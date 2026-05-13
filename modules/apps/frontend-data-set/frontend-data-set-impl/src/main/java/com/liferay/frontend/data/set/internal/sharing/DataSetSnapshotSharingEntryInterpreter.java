/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.sharing;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juanjo Fernandez
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectDefinition#Z7H2",
	service = SharingEntryInterpreter.class
)
public class DataSetSnapshotSharingEntryInterpreter
	implements SharingEntryInterpreter {

	@Override
	public String getAssetTypeTitle(SharingEntry sharingEntry, Locale locale) {
		return _language.get(locale, "data-set-user-view");
	}

	@Override
	public SharingEntryEditRenderer getSharingEntryEditRenderer() {
		return _EDIT_RENDERER;
	}

	@Override
	public SharingEntryViewRenderer getSharingEntryViewRenderer() {
		return _VIEW_RENDERER;
	}

	@Override
	public String getTitle(SharingEntry sharingEntry, Locale locale) {
		return _getLabel(sharingEntry);
	}

	@Override
	public Map<Locale, String> getTitleMap(SharingEntry sharingEntry) {
		String label = _getLabel(sharingEntry);

		if (label.isEmpty()) {
			return new HashMap<>();
		}

		return Collections.singletonMap(LocaleUtil.getDefault(), label);
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

	private static final SharingEntryEditRenderer _EDIT_RENDERER =
		(sharingEntry, liferayPortletRequest, liferayPortletResponse) -> null;

	private static final SharingEntryViewRenderer _VIEW_RENDERER =
		(sharingEntry, httpServletRequest, httpServletResponse) -> {
		};

	@Reference
	private Language _language;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}