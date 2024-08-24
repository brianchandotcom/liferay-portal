/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../helpers/ApiHelpers';

export default async function getBasicWebContentStructureId(
	apiHelpers: ApiHelpers
): Promise<number> {
	const company =
		await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
			'liferay.com'
		);

	const globalGroup = await apiHelpers.jsonWebServicesGroup.getCompanyGroup(
		company.companyId
	);

	return getWebContentStructureId(
		apiHelpers,
		globalGroup.groupId,
		'BASIC-WEB-CONTENT'
	);
}

export async function getWebContentStructureId(
	apiHelpers: ApiHelpers,
	groupId: string,
	structureKey: string
): Promise<number> {
	const className = await apiHelpers.jsonWebServicesClassName.fetchClassName(
		'com.liferay.journal.model.JournalArticle'
	);

	const ddmStructure = await apiHelpers.jsonWebServicesDDM.fetchStructure(
		groupId,
		className.classNameId,
		structureKey
	);

	return Number(ddmStructure.structureId);
}
