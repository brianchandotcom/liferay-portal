/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.exportimport.data.handler;

import java.util.function.Consumer;

/**
 * @author Adolfo Pérez
 */
public interface DLExportableRepositoryPublisher {

	public void publish(long groupId, Consumer<Long> repositoryIdConsumer);

}