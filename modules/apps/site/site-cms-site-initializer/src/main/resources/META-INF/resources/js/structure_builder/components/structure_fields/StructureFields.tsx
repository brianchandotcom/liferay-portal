/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {getImage} from '../../../structure_builder/utils/getImage';
import AddFieldDropdown from './AddFieldDropdown';

export default function StructureFields() {
	return (
		<div className="border p-4 structure-builder__structure-fields">
			<h3 className="font-weight-semi-bold text-4">
				{Liferay.Language.get('structure-fields')}
			</h3>

			<EmptyState />
		</div>
	);
}

function EmptyState() {
	return (
		<ClayEmptyState
			className="structure-builder__structure-fields-empty-state"
			description={Liferay.Language.get(
				'add-new-fields-to-start-building-your-structure'
			)}
			imgSrc={getImage('structure_fields_empty_state.svg')}
			small
			title={Liferay.Language.get('no-fields-yet')}
		>
			<AddFieldDropdown />
		</ClayEmptyState>
	);
}
