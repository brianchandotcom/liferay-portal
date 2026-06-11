/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import openDefaultFailureToast from './openDefaultFailureToast';
import {ISchemas} from './types';

export default async function getOpenApiData({
	restApplication,
	restSchema,
}: {
	restApplication: string;
	restSchema: string;
}) {
	const response = await fetch(`/o${restApplication}/openapi.json`);

	if (response.status === 404) {

		// The REST application does not expose an OpenAPI schema (for example,
		// taglib-based system data sets). This is an expected absence rather
		// than a failure, so return without showing an error.

		return null;
	}

	if (!response.ok) {
		openDefaultFailureToast();

		return null;
	}

	const responseJSON = await response.json();
	const schemas: ISchemas = responseJSON?.components?.schemas;

	if (!schemas?.[restSchema]?.properties) {
		openDefaultFailureToast();

		return null;
	}

	return {restSchema, schemas};
}
