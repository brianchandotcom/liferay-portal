/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external;

import com.liferay.document.library.repository.external.ExtRepository;
import com.liferay.document.library.repository.external.ExtRepositoryAdapter;

/**
 * @author Adolfo Pérez
 */
public class SharepointExtRepositoryAdapter extends ExtRepositoryAdapter {

	protected SharepointExtRepositoryAdapter(ExtRepository extRepository) {
		super(extRepository);
	}

}