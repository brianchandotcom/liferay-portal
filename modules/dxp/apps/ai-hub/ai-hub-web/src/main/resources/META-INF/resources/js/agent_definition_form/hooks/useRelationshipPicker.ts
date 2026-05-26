/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useRef, useState} from 'react';

interface RelationshipItem {
	externalReferenceCode: string;
}

export interface RelationshipPicker<T extends RelationshipItem> {
	diff: () => {toAdd: T[]; toRemove: T[]};
	inputValue: string;
	reset: (initial: T[]) => void;
	selected: T[];
	setInputValue: (value: string) => void;
	setSelected: (items: T[]) => void;
	setSourceList: (items: T[]) => void;
	sourceList: T[];
	syncToInitial: () => void;
}

export function useRelationshipPicker<
	T extends RelationshipItem,
>(): RelationshipPicker<T> {
	const [selected, setSelected] = useState<T[]>([]);
	const [sourceList, setSourceList] = useState<T[]>([]);
	const [inputValue, setInputValue] = useState('');
	const initialRef = useRef<T[]>([]);

	const diff = useCallback(() => {
		const initialERCs = new Set(
			initialRef.current.map((item) => item.externalReferenceCode)
		);
		const currentERCs = new Set(
			selected.map((item) => item.externalReferenceCode)
		);

		return {
			toAdd: selected.filter(
				(item) => !initialERCs.has(item.externalReferenceCode)
			),
			toRemove: initialRef.current.filter(
				(item) => !currentERCs.has(item.externalReferenceCode)
			),
		};
	}, [selected]);

	const reset = useCallback((initial: T[]) => {
		initialRef.current = initial;
		setSelected(initial);
	}, []);

	const syncToInitial = useCallback(() => {
		initialRef.current = [...selected];
	}, [selected]);

	return {
		diff,
		inputValue,
		reset,
		selected,
		setInputValue,
		setSelected,
		setSourceList,
		sourceList,
		syncToInitial,
	};
}
