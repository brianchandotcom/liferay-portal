/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {FieldFeedback} from 'frontend-js-components-web';
import React from 'react';

import {useCache} from '../contexts/CacheContext';
import {State, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureSpaces from '../selectors/selectStructureSpaces';
import selectStructureUuid from '../selectors/selectStructureUuid';
import selectValidationErrors from '../selectors/selectValidationErrors';
import {Space} from '../types/Space';

type Item = {
	label: string;
	value: string;
};

export default function Spaces() {
	const dispatch = useStateDispatch();
	const structureSpaces = useSelector(selectStructureSpaces);
	const structureUuid = useSelector(selectStructureUuid);
	const validationErrors = useSelector(selectValidationErrors(structureUuid));

	const {data: spaces, status} = useCache('spaces');

	const hasError = validationErrors.has('no-space');

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

			<ClayForm.Group className={classNames({'has-error': hasError})}>
				<ClayMultiSelect
					aria-label={Liferay.Language.get('space-selector')}
					disabled={structureSpaces === 'all'}
					items={getSelection(structureSpaces, spaces)}
					loadingState={status === 'saving' ? 1 : 0}
					onBlur={() => {
						if (!structureSpaces.length) {
							dispatch({
								error: 'no-space',
								type: 'add-validation-error',
								uuid: structureUuid,
							});
						}
					}}
					onItemsChange={(items: Item[]) => {
						const ercs = items
							.filter((item) =>
								spaces.some(({name}) => name === item.label)
							)
							.map(({value}) => value);

						dispatch({
							spaces: ercs,
							type: 'update-structure',
						});
					}}
					sourceItems={spaces.map(toItem)}
					value={
						structureSpaces === 'all'
							? Liferay.Language.get('all-spaces')
							: ''
					}
				/>

				{hasError ? (
					<FieldFeedback
						errorMessage={Liferay.Language.get(
							'spaces-must-be-selected'
						)}
					/>
				) : null}
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

function getSelection(structureSpaces: State['spaces'], spaces: Space[]) {
	if (structureSpaces === 'all') {
		return [];
	}

	return spaces
		.filter(({externalReferenceCode}) =>
			structureSpaces.includes(externalReferenceCode)
		)
		.map(toItem);
}

function toItem(space: Space): Item {
	return {
		label: space.name,
		value: space.externalReferenceCode,
	};
}
