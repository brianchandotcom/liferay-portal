/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {RulesSupport} from 'dynamic-data-mapping-form-builder';
import React from 'react';

export default function fieldDelete({action, modalDispatch, onClose, rules}) {
	return (dispatch) => {
		if (
			!RulesSupport.findRuleByFieldName(
				action.payload.fieldName,
				null,
				rules
			)
		) {
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
