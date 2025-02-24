/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {
	useSelectedItem,
	useStateDispatch,
	useStructureError,
	useStructureLabel,
} from '../contexts/StateContext';
import StructureFieldSettings from './StructureFieldSettings';

export function StructureSettings() {
	const dispatch = useStateDispatch();
	const error = useStructureError();
	const label = useStructureLabel();

	return (
		<ClayLayout.ContainerFluid view>
			{error ? (
				<ClayAlert
					displayType="danger"
					role={null}
					title={Liferay.Language.get('error')}
				>
					{error}
				</ClayAlert>
			) : null}

			<ClayLabel className="mb-3" displayType="info">
				{Liferay.Language.get('content')}
			</ClayLabel>

			<ClayForm.Group>
				<ClayInput
					aria-label={Liferay.Language.get('structure-label')}
					className="form-control-inline structure-builder__title-input"
					onChange={(event) =>
						dispatch({label: event.target.value, type: 'set-label'})
					}
					sizing="lg"
					type="text"
					value={label}
				/>
			</ClayForm.Group>
		</ClayLayout.ContainerFluid>
	);
}

export default function () {
	const selectedItem = useSelectedItem();

	if (selectedItem.type === 'structure') {
		return <StructureSettings />;
	}

	return <StructureFieldSettings fieldERC={selectedItem.erc} />;
}
