/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SpaceSticker from '../../components/SpaceSticker';
import SpaceService from '../services/SpaceService';

type Space = {
	label: string;
	value: any;
};

const ALL_SPACES: Space[] = [
	{
		label: 'All Spaces',
		value: -1,
	},
];

export default function CategorizationSpaces({
	assetLibraries,
	checkboxText,
	setSelectedSpaces,
	setSpaceChange,
	setSpaceInputError,
}: {
	assetLibraries?: any;
	checkboxText: string;
	setSelectedSpaces: (value: any) => void;
	setSpaceChange?: (value: boolean) => void;
	setSpaceInputError: (value: string) => void;
}) {
	const [availableSpaces, setAvailableSpaces] = useState<Space[]>([]);
	const [checkbox, setCheckbox] = useState(true);
	const [inputError, setInputError] = useState('');
	const [newSelectedSpaces, setNewSelectedSpaces] = useState<number[]>([]);
	const [selectedItems, setSelectedItems] = useState<Space[]>([]);

	const initialSelectedSpaces = assetLibraries?.map(
		(item: {id: number; name: string}) => item.id
	);

	useEffect(() => {
		SpaceService.getSpaces().then((response) => {
			const spaces = response.map((item) => ({
				label: item.name,
				value: item.id,
			}));

			setAvailableSpaces(spaces);
		});
	}, []);

	const isChecked = (itemValue: number) => {
		return newSelectedSpaces.includes(itemValue);
	};

	const handleCheckboxChange = (itemValue: any) => {
		setNewSelectedSpaces((prevSelectedSpaces) => {
			if (isChecked(itemValue)) {
				return prevSelectedSpaces.filter((id) => id !== itemValue);
			}
			else {
				return [...prevSelectedSpaces, itemValue];
			}
		});
	};

	useEffect(() => {
		if (checkbox) {
			setNewSelectedSpaces([-1]);
		}
	}, [checkbox]);

	useEffect(() => {
		if (assetLibraries?.some((item: {id: number}) => item.id === -1)) {
			setCheckbox(true);

			setNewSelectedSpaces([-1]);
		}
		else if (assetLibraries) {
			setCheckbox(false);

			const initialSpaces = assetLibraries.map(
				(item: {id: number; name: string}) => ({
					label: item.name,
					value: item.id,
				})
			);

			setNewSelectedSpaces(
				initialSpaces.map((item: {value: number}) => item.value)
			);
		}
	}, [assetLibraries]);

	useEffect(() => {
		setSelectedSpaces(newSelectedSpaces);

		if (setSpaceChange) {
			if (
				initialSelectedSpaces?.some(
					(item: number) => !newSelectedSpaces.includes(item)
				)
			) {
				setSpaceChange(true);
			}
			else {
				setSpaceChange(false);
			}
		}

		if (newSelectedSpaces.length) {
			setInputError('');
		}
		else {
			setInputError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('space')
				)
			);
		}
	}, [
		initialSelectedSpaces,
		newSelectedSpaces,
		setSelectedSpaces,
		setSpaceChange,
	]);

	useEffect(() => {
		if (checkbox) {
			setSelectedItems(ALL_SPACES);
		}

		setSelectedItems(
			availableSpaces.filter((item) =>
				newSelectedSpaces.includes(item.value)
			)
		);
	}, [availableSpaces, checkbox, newSelectedSpaces]);

	useEffect(() => {
		setSpaceInputError(inputError);
	}, [inputError, setSpaceInputError]);

	return (
		<div>
			<label htmlFor="multiSelect">
				{Liferay.Language.get('space')}

				<span className="ml-1 reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			{checkbox && (
				<ClayMultiSelect
					disabled={true}
					id="multiSelect"
					items={ALL_SPACES}
				/>
			)}

			<div className={inputError ? 'has-error' : ''}>
				{!checkbox && (
					<ClayMultiSelect
						disabled={checkbox}
						id="multiSelect"
						items={selectedItems}
						loadingState={3}
						onItemsChange={(items: Space[]) => {
							setNewSelectedSpaces(
								items.map((item) => item.value)
							);
						}}
						sourceItems={availableSpaces}
					>
						{(item) => (
							<ClayMultiSelect.Item
								key={item.value}
								textValue={item.label}
							>
								<div className="autofit-row autofit-row-center">
									<div className="autofit-col">
										<ClayCheckbox
											aria-label={item.label}
											checked={isChecked(item.value)}
											onChange={() => {
												handleCheckboxChange(
													item.value
												);
											}}
										/>
									</div>

									<span className="align-items-center d-flex space-renderer-sticker">
										<SpaceSticker
											name={item.label}
											size="sm"
										/>
									</span>
								</div>
							</ClayMultiSelect.Item>
						)}
					</ClayMultiSelect>
				)}

				{inputError && (
					<ClayAlert displayType="danger" variant="feedback">
						<strong>{Liferay.Language.get('error')}: </strong>

						{inputError}
					</ClayAlert>
				)}
			</div>

			<div className="mt-2">
				<ClayCheckbox
					checked={checkbox}
					label={
						checkboxText === 'tag'
							? Liferay.Language.get(
									'make-this-tag-available-in-all-spaces'
								)
							: Liferay.Language.get(
									'make-this-vocabulary-available-in-all-spaces'
								)
					}
					onChange={() => {
						setCheckbox(!checkbox);
						setNewSelectedSpaces([]);
					}}
				/>
			</div>
		</div>
	);
}
