/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {Item, LabelList} from './LabelList';

interface DDMStructure {
	ddmStructureId: string;
	name: string;
}

interface Props {
	ddmStructures?: DDMStructure[];
	portletNamespace: string;
	selectDDMStructureURL: string;
}

export default function HighlightedDDMStructuresConfiguration({
	ddmStructures: initialDDMStructures,
	portletNamespace,
	selectDDMStructureURL,
}: Props) {
	const [ddmStructures, setDDMStructures] = useState<DDMStructure[]>(
		initialDDMStructures || []
	);

	const onSelectButtonClick = () =>
		openSelectionModal({
			multiple: true,
			onSelect: (selectedItems: Array<{value: string}>) =>
				setDDMStructures((previousDDMStructures) =>
					removeDuplicates<DDMStructure>(
						[
							...previousDDMStructures,
							...selectedItems.map(
								itemSelectorValueToDDMStructure
							),
						],
						(ddmStructure) => ddmStructure.ddmStructureId
					)
				),
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('structures')
			),
			url: selectDDMStructureURL,
		});

	return (
		<div className="c-px-4">
			<p className="c-pb-4">
				{Liferay.Language.get(
					'select-the-structures-you-want-to-highlight-in-web-content-administration-to-quickly-access-and-manage-all-its-contents'
				)}
			</p>

			<input
				name={`${portletNamespace}preferences--highlightedDDMStructures--`}
				type="hidden"
				value={ddmStructures
					.map((ddmStructure) => ddmStructure.ddmStructureId)
					.join(',')}
			/>

			<ClayForm.Group>
				<h4 className="h5 text-weight-semi-bold">
					{Liferay.Language.get('highlighted-structures')}
				</h4>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<LabelList
							items={ddmStructures.map(ddmStructureToItem)}
							onItemsChange={(nextItems) =>
								setDDMStructures(
									nextItems.map(itemToDDMStructure)
								)
							}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							aria-label={Liferay.Language.get(
								'add-highlighted-structures'
							)}
							displayType="secondary"
							onClick={onSelectButtonClick}
							type="button"
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}

function ddmStructureToItem(ddmStructure: DDMStructure): Item {
	return {
		label: ddmStructure.name,
		value: ddmStructure.ddmStructureId,
	};
}

function itemSelectorValueToDDMStructure(item: {value: string}): DDMStructure {
	const parsedValue = JSON.parse(item.value) as {
		ddmstructureid: string;
		name: string;
	};

	return {
		ddmStructureId: parsedValue.ddmstructureid,
		name: parsedValue.name,
	};
}

function itemToDDMStructure(item: Item): DDMStructure {
	return {
		ddmStructureId: item.value,
		name: item.label,
	};
}

function removeDuplicates<T>(
	list: T[],
	getElementId: (element: T) => string
): T[] {
	return list.filter((element, index, array) => {
		const elementId = getElementId(element);

		return (
			index ===
			array.findIndex(
				(otherElement) => elementId === getElementId(otherElement)
			)
		);
	});
}
