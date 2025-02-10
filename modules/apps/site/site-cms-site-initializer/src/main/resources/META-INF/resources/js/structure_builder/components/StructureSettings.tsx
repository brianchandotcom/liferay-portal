/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import React, {useContext} from 'react';

import {StructureSettingsContext} from '../contexts/StructureSettingsContext';

export default function StructureSettings() {
	const {error, name, setName} = useContext(StructureSettingsContext);

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
					className="form-control-inline structure-builder__title-input"
					onChange={(event) => setName(event.target.value)}
					sizing="lg"
					type="text"
					value={name}
				/>
			</ClayForm.Group>
		</ClayLayout.ContainerFluid>
	);
}
