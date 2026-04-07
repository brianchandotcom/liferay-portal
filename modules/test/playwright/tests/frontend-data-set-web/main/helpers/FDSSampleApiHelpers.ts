/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../../helpers/ApiHelpers';

export class FDSSampleApiHelpers extends ApiHelpers {
	async createFDSSampleItem({
		color,
		date,
		description,
		externalReferenceCode,
		imageURL,
		size,
		title,
	}) {
		const url = '/o/c/fdssamples';
		const data = {
			color,
			date,
			description,
			externalReferenceCode,
			imageURL,
			size,
			title,
		};

		return this.post(url, {data});
	}

	async deleteFDSSampleItem({erc}) {
		const url = `/o/c/fdssamples/by-external-reference-code/${erc}`;

		return this.delete(url);
	}
}
