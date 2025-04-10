/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import React, {useEffect, useState} from 'react';

import SpaceSticker from '../../components/SpaceSticker';
import SpaceService from '../services/SpaceService';

type Space = {
	label: string;
	value: any;
};

export default function CategorizationSpaces({
	checkboxText,
}: {
	checkboxText: string;
}) {
	const [allSpaces, setAllSpaces] = useState<Space[]>([]);
	const [availableSpaces, setAvailableSpaces] = useState<Space[]>([]);
	const [checkbox, setCheckbox] = useState(true);
	const [selectedSpaces, setSelectedSpaces] = useState<string[]>([]);

	useEffect(() => {
		SpaceService.getSpaces().then((response) => {
			const spaces = response.map((space) => ({
				label: space.name,
				value: space.id,
			}));

			setAvailableSpaces(spaces);

			setAllSpaces([
				{
					label: 'All Spaces',
					value: response.map(({id}) => id),
				},
			]);
		});
	}, []);

	const isChecked = (itemValue: string) => {
		return selectedSpaces.includes(itemValue);
	};

	const handleCheckboxChange = (itemValue: any) => {
		setSelectedSpaces((prevSelectedSpaces) => {
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
			setSelectedSpaces(allSpaces.flatMap((item) => item.value));
		}
		else {
			setSelectedSpaces([]);
		}
	}, [checkbox, allSpaces]);

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
					items={allSpaces}
				/>
			)}

			{!checkbox && (
				<ClayMultiSelect
					aria-label={Liferay.Language.get('space-selector')}
					disabled={checkbox}
					id="multiSelect"
					loadingState={3}
					onItemsChange={(items: Space[]) => {
						setSelectedSpaces(items.map((item) => item.value));
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
											handleCheckboxChange(item.value);
										}}
									/>
								</div>

								<span className="align-items-center d-flex space-renderer-sticker">
									<SpaceSticker name={item.label} size="sm" />
								</span>
							</div>
						</ClayMultiSelect.Item>
					)}
				</ClayMultiSelect>
			)}

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
					onChange={() => setCheckbox(!checkbox)}
				/>
			</div>
		</div>
	);
}
