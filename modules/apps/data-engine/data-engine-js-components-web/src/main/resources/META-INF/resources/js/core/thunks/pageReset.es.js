/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {findRuleByFieldName} from '../../utils/rulesSupport';
import {PagesVisitor} from '../../utils/visitors.es';

export default function pageReset({
	action,
	modalDispatch,
	onClose,
	pages,
	rules,
}) {
	const currentPage = pages[action.payload.pageIndex];
	const visitor = new PagesVisitor([currentPage]);

	return (dispatch) => {
		let ruleFound = false;
		visitor.mapFields((field) => {
			if (findRuleByFieldName(field.fieldName, null, rules)) {
				ruleFound = true;

				return;
			}
		});

		if (ruleFound) {
			modalDispatch({
				payload: {
					body: Liferay.Language.get(
						'a-rule-is-applied-to-fields-of-this-page'
					),
					footer: [
						null,
						null,
						<ClayButton.Group key={3} spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
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
					header: Liferay.Language.get(
						'reset-page-with-rule-applied-to-field'
					),
					size: 'md',
				},
				type: 1,
			});
		}
		else {
			dispatch(action);
		}
	};
}
