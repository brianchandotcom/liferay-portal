/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getTop} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {addAccountEntryUserURL},
	...otherProps
}) {
	return {
		...otherProps,
		onCreateButtonClick: () => {
			const openerWindow = getTop();

			openerWindow.Liferay.Util.navigate(addAccountEntryUserURL);
		},
	};
}
