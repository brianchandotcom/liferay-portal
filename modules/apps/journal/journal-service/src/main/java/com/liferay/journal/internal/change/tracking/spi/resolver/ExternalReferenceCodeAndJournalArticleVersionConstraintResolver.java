/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class ExternalReferenceCodeAndJournalArticleVersionConstraintResolver
	extends BaseJournalArticleVersionConstraintResolver {

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "externalReferenceCode", "version"};
	}

}