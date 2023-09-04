/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.osgi.commands;

import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.util.PropsValues;

import java.time.Duration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.command.function=cleanUp", "osgi.command.scope=documentLibrary"
	},
	service = StoreAreaOSGiCommands.class
)
public class StoreAreaOSGiCommands {

	public void cleanUp(long companyId) {
		if (_storeAreaProcessor == null) {
			System.out.println(
				"The selected store " + PropsValues.DL_STORE_IMPL +
					" does not support store areas. No action taken.");

			return;
		}

		_storeAreaProcessor.cleanUpDeletedStoreArea(
			companyId, Integer.MAX_VALUE,
			name -> !_isDLFileVersionReferenced(companyId, name),
			StringPool.BLANK, Duration.ofSeconds(1));
		_storeAreaProcessor.cleanUpNewStoreArea(
			companyId, Integer.MAX_VALUE,
			name -> !_isDLFileVersionReferenced(companyId, name),
			StringPool.BLANK, Duration.ofSeconds(1));
	}

	private boolean _isDLFileVersionReferenced(Long companyId, String name) {
		int pos = name.lastIndexOf(StringPool.TILDE);

		if (pos == -1) {
			return true;
		}

		int fileVersionsCount = _dlFileVersionLocalService.getFileVersionsCount(
			companyId, name.substring(pos + 1));

		if (fileVersionsCount > 0) {
			return true;
		}

		return false;
	}

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile StoreAreaProcessor _storeAreaProcessor;

}