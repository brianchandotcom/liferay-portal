/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.translator;

import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public interface InfoItemIdentifierTranslator<T> {

	public <S extends InfoItemIdentifier> S translateInfoItemIdentifier(
			InfoItemIdentifier infoItemIdentifier,
			Class<S> targetInfoItemIdentifierClass)
		throws PortalException;

}