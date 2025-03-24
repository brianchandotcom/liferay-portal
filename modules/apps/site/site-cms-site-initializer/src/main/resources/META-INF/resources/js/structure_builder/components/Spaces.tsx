/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import React, {useEffect, useState} from 'react';

import {State, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureSpaces from '../selectors/selectStructureSpaces';
import SpaceService from '../services/SpaceService';

type Space = {
	label: string;
	value: string;
};

export default function Spaces() {
	const dispatch = useStateDispatch();
	const structureSpaces = useSelector(selectStructureSpaces);

	const [loading, setLoading] = useState(0);
	const [availableSpaces, setAvailableSpaces] = useState<Space[]>([]);

	useEffect(() => {
		setLoading(1);

		SpaceService.getSpaces().then((response) => {
			const spaces = response.map((space) => ({
				label: space.name,
				value: space.externalReferenceCode,
			}));

			setAvailableSpaces(spaces);

			setLoading(0);
		});
	}, [structureSpaces]);

	return (
		<div className="mt-5">
			<span className="border-bottom d-block mb-3 panel-title text-secondary">
				{Liferay.Language.get('space-availability')}
			</span>

			<p>
				{Liferay.Language.get(
					'select-the-spaces-where-this-structure-will-be-available-for-use'
				)}
			</p>

			<ClayForm.Group>
				<ClayMultiSelect
					disabled={structureSpaces === 'all'}
					items={getSelection(structureSpaces, availableSpaces)}
					loadingState={loading}
					onItemsChange={(spaces: Space[]) => {
						const ercs = spaces
							.filter((space) =>
								availableSpaces.some(
									({label}) => label === space.label
								)
							)
							.map(({value}) => value);

						dispatch({
							spaces: ercs,
							type: 'update-structure',
						});
					}}
					sourceItems={availableSpaces}
					value={
						structureSpaces === 'all'
							? Liferay.Language.get('all-spaces')
							: ''
					}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<ClayCheckbox
					checked={structureSpaces === 'all'}
					label={Liferay.Language.get(
						'make-this-structure-available-in-all-spaces'
					)}
					onChange={(event) => {
						dispatch({
							spaces: event.target.checked ? 'all' : [],
							type: 'update-structure',
						});
					}}
				/>
			</ClayForm.Group>
		</div>
	);
}

function getSelection(
	structureSpaces: State['spaces'],
	availableSpaces: Space[]
) {
	if (structureSpaces === 'all') {
		return [];
	}

	return availableSpaces.filter(({value}) => structureSpaces.includes(value));
}
