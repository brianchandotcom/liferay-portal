/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.util;

import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryWrapperAware {

	public LocalRepository wrapLocalRepository(LocalRepository localRepository);

	public Repository wrapRepository(Repository repository);

}