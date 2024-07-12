/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.model.listener;

import com.liferay.frontend.js.web.internal.LanguageState;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.language.override.model.PLOEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ModelListener.class)
public class PLOEntryModelListener extends BaseModelListener<PLOEntry> {

	@Override
	public void onAfterCreate(PLOEntry ploEntry) throws ModelListenerException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Language override entry '", ploEntry.getPloEntryId(),
					"' for language '", ploEntry.getLanguageId(),
					"' was created"));
		}

		LanguageState.reload();
	}

	@Override
	public void onAfterRemove(PLOEntry ploEntry) throws ModelListenerException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Language override entry '", ploEntry.getPloEntryId(),
					"' for language '", ploEntry.getLanguageId(),
					"' was removed"));
		}

		LanguageState.reload();
	}

	@Override
	public void onAfterUpdate(PLOEntry originalPLOEntry, PLOEntry ploEntry)
		throws ModelListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Language override entry '", ploEntry.getPloEntryId(),
					"' for language '", ploEntry.getLanguageId(),
					"' was modified"));
		}

		LanguageState.reload();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PLOEntryModelListener.class);

}