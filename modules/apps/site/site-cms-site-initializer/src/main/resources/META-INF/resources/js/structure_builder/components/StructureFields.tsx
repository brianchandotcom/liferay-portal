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

export default function () {
	return (
		<div className="border structure-builder__structure-fields">
			<h3 className="font-weight-semi-bold pt-4 px-4 text-4">
				{Liferay.Language.get('structure-fields')}
			</h3>

			<StructureFields />
		</div>
	);
}

function StructureFields() {
	const fields = useSelector(selectStructureFields);

	const [search, setSearch] = useState('');

	const filteredFields = useMemo(() => {
		return fields.filter((field) =>
			field.label[
				Liferay.ThemeDisplay.getDefaultLanguageId()
			]!.toLowerCase().includes(search.toLowerCase())
		);
	}, [fields, search]);

	if (!fields.length) {
		return <EmptyState />;
	}

	return (
		<>
			<Toolbar setSearch={setSearch} />

			<FieldsTree fields={filteredFields} />
		</>
	);
}

function EmptyState() {
	return (
		<ClayEmptyState
			className="px-4 structure-builder__structure-fields-empty-state"
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

function Toolbar({
	setSearch,
}: {
	setSearch: React.Dispatch<React.SetStateAction<string>>;
}) {
	return (
		<div className="align-items-center c-gap-2 d-flex px-4">
			<SearchForm
				className="flex-grow-1 my-3"
				label={Liferay.Language.get('search-fields')}
				onChange={setSearch}
				variant="white"
			/>

			<AddFieldDropdown triggerType="icon" />
		</div>
	);
}
