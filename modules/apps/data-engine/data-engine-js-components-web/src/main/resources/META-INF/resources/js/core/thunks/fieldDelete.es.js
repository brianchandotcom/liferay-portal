/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {findRuleByFieldName} from '../../utils/rulesSupport';

export default function fieldDelete({action, modalDispatch, onClose, rules}) {
	return (dispatch) => {
		if (!findRuleByFieldName(action.payload.fieldName, null, rules)) {
			dispatch(action);

			return;
		}

		modalDispatch({
			payload: {
				body: Liferay.Language.get('a-rule-is-applied-to-this-field'),
				footer: [
					null,
					null,
					<ClayButton.Group key={3} spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="danger"
							onClick={() => {
								onClose();
								dispatch(action);
							}}
						>
							{Liferay.Language.get('confirm')}
						</ClayButton>
					</ClayButton.Group>,
				],
				header: Liferay.Language.get('delete-field-with-rule-applied'),
				size: 'md',
			},
			type: 1,
		});
	};
}
