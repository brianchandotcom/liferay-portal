/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function externalReferenceCodeGenerator(entityAbreviation: string) {
	const externalReferenceCodeGeneratorMath = String(Math.random()).substring(
		2,
		11
	);

	const formattedERC = externalReferenceCodeGeneratorMath.replace(
		/(\d{2})?(\d{3})?(\d{4})/,
		'$1-$2-$3'
	);

	return `${entityAbreviation}-${formattedERC}`;
}
