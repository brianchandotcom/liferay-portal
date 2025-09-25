/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {FieldFeedback, useId} from 'frontend-js-components-web';
import {ItemSelector} from 'frontend-js-item-selector-web';
import React from 'react';

import SpaceSticker from '../../common/components/SpaceSticker';
import {Space} from '../../common/types/Space';
import {useCache} from '../contexts/CacheContext';
import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectValidationErrors from '../selectors/selectValidationErrors';
import {ReferencedStructure, Structure} from '../types/Structure';

type Item = {
	label: string;
	value: string;
};

export default function Spaces({
	disabled,
	structure,
}: {
	disabled?: boolean;
	structure: Structure | ReferencedStructure;
}) {
	const [value, setValue] = React.useState('');

	const {spaces: structureSpaces, uuid: structureUuid} = structure;

	const dispatch = useStateDispatch();

	const validationErrors = useSelector(selectValidationErrors(structureUuid));

	const id = useId();

	const {data: spaces} = useCache('spaces');

	const hasError = validationErrors.has('no-space');

	const selectedSpaces = getSelection(structureSpaces, spaces);

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
				<label htmlFor={id}>
					{Liferay.Language.get('spaces')}

					<ClayIcon
						className="ml-1 reference-mark"
						focusable="false"
						role="presentation"
						symbol="asterisk"
					/>
				</label>

				<ItemSelector<Space>
					apiURL={`${location.origin}/o/headless-asset-library/v1.0/asset-libraries`}
					as={ClayInput}
					disabled={disabled || structureSpaces === 'all'}
					id={id}
					items={selectedSpaces}
					locator={{
						id: 'value',
						label: 'label',
						value: 'value',
					}}
					multiSelect
					onBlur={() => {
						if (!structureSpaces.length) {
							dispatch({
								error: 'no-space',
								type: 'add-validation-error',
								uuid: structureUuid,
							});
						}
					}}
					onChange={setValue}
					onItemsChange={(items: Array<Item | Space>) => {
						dispatch({
							spaces: items.map(
								(item) =>
									(item as Space).externalReferenceCode ||
									(item as Item).value
							),
							type: 'update-structure',
						});
					}}
					value={
						structureSpaces === 'all'
							? Liferay.Language.get('all-spaces')
							: value
					}
				>
					{(item: Space) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							<SpaceSticker name={item.name} />
						</ItemSelector.Item>
					)}
				</ItemSelector>

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
					disabled={disabled}
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

function getSelection(structureSpaces: Structure['spaces'], spaces: Space[]) {
	if (structureSpaces === 'all') {
		return [];
	}

	return spaces.filter(({externalReferenceCode}) =>
		structureSpaces.includes(externalReferenceCode)
	);
}
