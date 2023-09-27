/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLiferaySiteName} from './liferay';

const createUrlByERC = (externalReferenceCode: string, entity: string) => {
	const currentSiteName = getLiferaySiteName();
	const link = `${currentSiteName}/${entity}?externalReferenceCode=${externalReferenceCode}`;

	return link;
};

export default createUrlByERC;
