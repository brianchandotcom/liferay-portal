/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';

export async function deleteObjectField(
	defaultLanguageId: Liferay.Language.Locale,
	id: number,
	objectField: ObjectField
) {
	try {
		await API.deleteObjectField(id);

		Liferay.Util.openToast({
			message: sub(
				Liferay.Language.get('x-was-deleted-successfully'),
				`<strong>${objectField?.label[defaultLanguageId]}</strong>`
			),
		});
	}
	catch (error) {
		Liferay.Util.openToast({
			message: (error as Error).message,
			type: 'danger',
		});
	}
}
