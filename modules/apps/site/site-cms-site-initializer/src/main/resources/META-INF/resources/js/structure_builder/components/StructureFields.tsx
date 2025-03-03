/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {SearchForm} from '@liferay/layout-js-components-web';
import React, {useMemo, useState} from 'react';

import {getImage} from '../../structure_builder/utils/getImage';
import {useSelector} from '../contexts/StateContext';
import selectStructureFields from '../selectors/selectStructureFields';
import AddFieldDropdown from './AddFieldDropdown';
import FieldsTree from './FieldsTree';

export default function StructureFields() {
	const fields = useSelector(selectStructureFields);

	const [search, setSearch] = useState('');

	const filteredFields = useMemo(() => {
		return fields.filter((field) =>
			field.label[
				Liferay.ThemeDisplay.getDefaultLanguageId()
			]!.toLowerCase().includes(search.toLowerCase())
		);
	}, [fields, search]);

	return (
		<div className="border p-4 structure-builder__structure-fields">
			<h3 className="font-weight-semi-bold text-4">
				{Liferay.Language.get('structure-fields')}
			</h3>

			{fields.length ? (
				<>
					<div className="align-items-center c-gap-2 d-flex">
						<SearchForm
							className="flex-grow-1 my-3"
							label={Liferay.Language.get('search-fields')}
							onChange={setSearch}
							variant="white"
						/>

						<AddFieldDropdown triggerType="icon" />
					</div>

					<FieldsTree fields={filteredFields} />
				</>
			) : (
				<EmptyState />
			)}
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
