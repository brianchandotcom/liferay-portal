/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useRef, useState} from 'react';

interface RelationshipItem {
	externalReferenceCode: string;
}

interface UseRelationshipPickerOptions<T extends RelationshipItem> {
	deleteRelationship: (
		agentDefinitionERC: string,
		itemERC: string
	) => Promise<unknown>;
	fetchSourceList: () => Promise<{items?: T[]}>;
	putRelationship: (
		agentDefinitionERC: string,
		itemERC: string
	) => Promise<unknown>;
}

export interface RelationshipPicker<T extends RelationshipItem> {
	inputValue: string;
	reset: (initial: T[]) => void;
	selected: T[];
	setInputValue: (value: string) => void;
	setSelected: (items: T[]) => void;
	sourceList: T[];
	sync: (agentDefinitionERC: string) => Promise<void>;
}

export function useRelationshipPicker<T extends RelationshipItem>({
	deleteRelationship,
	fetchSourceList,
	putRelationship,
}: UseRelationshipPickerOptions<T>): RelationshipPicker<T> {
	const [selected, setSelected] = useState<T[]>([]);
	const [sourceList, setSourceList] = useState<T[]>([]);
	const [inputValue, setInputValue] = useState('');
	const initialRef = useRef<T[]>([]);

	useEffect(() => {
		async function fetchItems() {
			try {
				const response = await fetchSourceList();

				setSourceList(response.items || []);
			}
			catch (error) {
				console.error(error);
			}
		}

		fetchItems();
	}, [fetchSourceList]);

	const reset = useCallback((initial: T[]) => {
		initialRef.current = initial;

		setSelected(initial);
	}, []);

	const sync = useCallback(
		async (agentDefinitionERC: string) => {
			const initialERCs = new Set(
				initialRef.current.map((item) => item.externalReferenceCode)
			);
			const currentERCs = new Set(
				selected.map((item) => item.externalReferenceCode)
			);

			const requests = [
				...selected
					.filter(
						(item) => !initialERCs.has(item.externalReferenceCode)
					)
					.map((item) =>
						putRelationship(
							agentDefinitionERC,
							item.externalReferenceCode
						)
					),
				...initialRef.current
					.filter(
						(item) => !currentERCs.has(item.externalReferenceCode)
					)
					.map((item) =>
						deleteRelationship(
							agentDefinitionERC,
							item.externalReferenceCode
						)
					),
			];

			if (requests.length) {
				await Promise.all(requests);

				initialRef.current = [...selected];
			}
		},
		[deleteRelationship, putRelationship, selected]
	);

	return {
		inputValue,
		reset,
		selected,
		setInputValue,
		setSelected,
		sourceList,
		sync,
	};
}
