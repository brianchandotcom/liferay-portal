/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {setFormValues} from 'frontend-js-web';

import updatePriorities from './updatePriorities';
import updateRanks from './updateRanks';

export default function propsTransformer({
	additionalProps: {defaultLanguageId},
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				const emailMessageAdded =
					window[`${portletNamespace}emailMessageAdded`];

				if (emailMessageAdded) {
					setFormValues(form, {
						'preferences--emailMessageAddedBody--':
							emailMessageAdded.getHTML(),
					});
				}

				const emailMessageUpdated =
					window[`${portletNamespace}emailMessageUpdated`];

				if (emailMessageUpdated) {
					setFormValues(form, {
						'preferences--emailMessageUpdatedBody--':
							emailMessageUpdated.getHTML(),
					});
				}
			}

			updateRanks(defaultLanguageId, portletNamespace);
			updatePriorities(defaultLanguageId, portletNamespace);

			submitForm(document.getElementById(`${portletNamespace}fm`));
		},
	};
}
