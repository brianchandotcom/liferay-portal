/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {getImage} from '../utils/getImage';

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
		<div className="c-empty-state c-empty-state-animation w-75">
			<img
				className="w-75"
				src={getImage('structure_fields_empty_state.svg')}
			/>

			<div className="c-empty-state-title mt-3">
				<span>{Liferay.Language.get('no-fields-yet')}</span>
			</div>

			<div className="c-empty-state-text">
				<span>
					{Liferay.Language.get(
						'add-new-fields-to-start-building-your-structure'
					)}
				</span>
			</div>

			<ClayButton className="mt-3" displayType="secondary" size="sm">
				{Liferay.Language.get('add-field')}
			</ClayButton>
		</div>
	);
}
