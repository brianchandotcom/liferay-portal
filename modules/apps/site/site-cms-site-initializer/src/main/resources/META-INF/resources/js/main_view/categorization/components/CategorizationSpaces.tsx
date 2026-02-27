/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {NetworkStatus} from '@clayui/data-provider';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SpaceSticker from '../../../common/components/SpaceSticker';
import SpaceService from '../../../common/services/SpaceService';
import {LogoColor} from '../../../common/types/Space';

type Space = {
	displayType?: LogoColor;
	label: string;
	scopeKey: string;
	value: any;
};

export default function CategorizationSpaces({
	assetLibraries,
	checkboxText,
	setSelectedSpaces,
	setSpaceChange,
	setSpaceInputError,
	spaceInputError,
}: {
	assetLibraries?: any;
	checkboxText: string;
	setSelectedSpaces: (value: any) => void;
	setSpaceChange?: (value: boolean) => void;
	setSpaceInputError: (value: string) => void;
	spaceInputError: string;
}) {
	const [availableSpaces, setAvailableSpaces] = useState<Space[]>([]);
	const [availableSpacesKey, setAvailableSpacesKey] = useState(0);
	const [checkbox, setCheckbox] = useState(true);
	const isVocabulary = checkboxText === 'vocabulary';
	const [displaySpaceError, setDisplaySpaceError] = useState(!isVocabulary);
	const [query, setQuery] = useState('');
	const [selectedItems, setSelectedItems] = useState<Space[]>([]);
	const [initialSelectedSpaces, setInitialSelectedSpaces] = useState<
		number[]
	>([]);

	const loadingState = !availableSpaces.length
		? NetworkStatus.Polling
		: undefined;

	useEffect(() => {
		SpaceService.getSpaces().then((response) => {
			const spaces = response.map((item) => ({
				displayType: item.settings?.logoColor,
				label: item.name,
				scopeKey: item.assetLibraryKey,
				value: item.id,
			}));

			setAvailableSpaces(spaces);
			setAvailableSpacesKey((key) => key + 1);

			const initialSpaces = assetLibraries?.map(
				(item: {name: string}) =>
					spaces.find((space) => space.label === item.name)?.value
			);

			setInitialSelectedSpaces(initialSpaces);

			if (
				!assetLibraries ||
				!assetLibraries.length ||
				assetLibraries?.some((item: {id: number}) => item.id === -1)
			) {
				setCheckbox(true);

				setSelectedItems([]);
			}
			else if (initialSpaces) {
				setCheckbox(false);

				setSelectedItems(
					spaces.filter((item) => initialSpaces.includes(item.value))
				);
			}
		});
	}, [assetLibraries]);

	useEffect(() => {
		if (setSpaceChange) {
			if (checkbox) {
				setSpaceChange(false);
			}
			else if (
				initialSelectedSpaces?.some(
					(item: number) =>
						!selectedItems.find((space) => space.value === item)
				)
			) {
				setSpaceChange(true);
			}
			else {
				setSpaceChange(false);
			}
		}

		if (checkbox || selectedItems.length) {
			setSpaceInputError('');
		}
		else {
			setSpaceInputError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('space')
				)
			);
		}
	}, [
		checkbox,
		initialSelectedSpaces,
		selectedItems,
		setSpaceChange,
		setSpaceInputError,
	]);

	const _getAvailableSpaces = (items: Space[]) => {
		return availableSpaces.filter((availableItem) =>
			items.some((item) => availableItem.value === item.value)
		);
	};

	const _handleChangeAllSpaces = () => {
		if (isVocabulary && checkbox) {
			setDisplaySpaceError(false);
		}

		setSelectedItems([]);
		setSelectedSpaces([]);
		setQuery('');
		setCheckbox((checkbox) => !checkbox);
	};

	const _handleChangeSpaces = (items: Space[]) => {
		setDisplaySpaceError(true);
		setSelectedItems(_getAvailableSpaces(items));

		setSelectedSpaces(items.map((item) => item.scopeKey));
	};

	return (
		<div className="categorization-spaces">
			<label htmlFor="multiSelect">
				{Liferay.Language.get('space')}

				<span className="ml-1 reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			<div
				className={
					displaySpaceError && spaceInputError ? 'has-error' : ''
				}
			>
				<ClayMultiSelect
					aria-label={Liferay.Language.get('space-selector')}
					disabled={checkbox}
					id="multiSelect"
					items={selectedItems}
					key={availableSpacesKey}
					loadingState={loadingState}
					onChange={setQuery}
					onItemsChange={_handleChangeSpaces}
					sourceItems={availableSpaces}
					value={
						checkbox ? Liferay.Language.get('all-spaces') : query
					}
				>
					{(item) => (
						<ClayMultiSelect.Item
							key={item.value}
							textValue={item.label}
						>
							<SpaceSticker
								displayType={item.displayType}
								name={item.label}
								size="sm"
							/>
						</ClayMultiSelect.Item>
					)}
				</ClayMultiSelect>

				{displaySpaceError && spaceInputError && (
					<ClayAlert displayType="danger" variant="feedback">
						<strong>{Liferay.Language.get('error')}: </strong>

						{spaceInputError}
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
					onChange={_handleChangeAllSpaces}
				/>
			</div>
		</div>
	);
}
