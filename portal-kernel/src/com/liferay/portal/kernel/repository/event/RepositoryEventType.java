/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.event;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryEventType {

	public interface Add extends RepositoryEventType {
	}

	public interface Delete extends RepositoryEventType {
	}

	public interface Move extends RepositoryEventType {
	}

	public interface Update extends RepositoryEventType {
	}

}