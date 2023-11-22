/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {openSelectionModal, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {StructureList} from './StructureList';

export interface DDMStructure {
	ddmStructureId: string;
	name: string;
	scope: string;
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
			<p className="text-secondary">
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

			<div className="align-items-end d-flex justify-content-between sheet-subtitle text-secondary">
				{Liferay.Language.get('highlighted-structures')}

				<ClayButton
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('highlighted-structures')
					)}
					displayType="secondary"
					onClick={onSelectButtonClick}
					size="sm"
					type="button"
				>
					{Liferay.Language.get('select')}
				</ClayButton>
			</div>

			<StructureList
				onRemoveStructure={(nextStructures) =>
					setDDMStructures(nextStructures)
				}
				structures={ddmStructures}
			/>
		</div>
	);
}

export function itemSelectorValueToDDMStructure(item: {
	value: string;
}): DDMStructure {
	const parsedValue = JSON.parse(item.value) as {
		ddmstructureid: string;
		name: string;
		scope: string;
	};

	return {
		ddmStructureId: parsedValue.ddmstructureid,
		name: parsedValue.name,
		scope: parsedValue.scope,
	};
}

export function removeDuplicates<T>(
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
