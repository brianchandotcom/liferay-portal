/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {ItemSelector} from '@liferay/frontend-js-item-selector-web';
import React, {useEffect, useRef, useState} from 'react';

import {getVocabularyByExternalReferenceCode} from '../utils/api';

export interface Category {
	id: number;
	name: string;
}

interface VocabularyMultiSelectProps {
	disabled: boolean;
	label: string;
	onChange: (categories: Category[]) => void;
	placeholder: string;
	value: Category[];
	vocabularyERC: string;
}

export default function VocabularyMultiSelect({
	disabled,
	label,
	onChange,
	placeholder,
	value,
	vocabularyERC,
}: VocabularyMultiSelectProps) {
	const [apiURL, setApiURL] = useState<string | undefined>();
	const [inputValue, setInputValue] = useState('');

	const containerRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		getVocabularyByExternalReferenceCode({
			siteGroupId: Liferay.ThemeDisplay.getSiteGroupId(),
			vocabularyERC,
		})
			.then(({data}) => {

				// When the vocabulary is not available in this scope, point at
				// a nonexistent vocabulary so the field still renders enabled
				// but empty, the same as a scoped vocabulary with no categories

				const vocabularyId = data?.id ?? 0;

				setApiURL(
					`${Liferay.ThemeDisplay.getPortalURL()}/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${vocabularyId}/taxonomy-categories`
				);
			})
			.catch(() => {});
	}, [vocabularyERC]);

	const removeCategory = (id: number) => {
		onChange(
			value.filter((selectedCategory) => selectedCategory.id !== id)
		);
	};

	return (
		<div className="lfr-cmp__vocabulary-multi-select" ref={containerRef}>
			<label className="control-label">{label}</label>

			{apiURL && (
				<ItemSelector<any>
					apiURL={apiURL}
					disabled={disabled}
					displaySelectedItems={false}
					items={value}
					locator={{id: 'id', label: 'name', value: 'name'}}
					multiSelect
					onChange={setInputValue}
					onItemsChange={(items: any[]) => {
						const categories: Category[] = [];

						items.forEach((item) => {
							const id = parseInt(item.id, 10);

							if (
								!categories.some(
									(category) => category.id === id
								)
							) {
								categories.push({id, name: item.name});
							}
						});

						onChange(categories);

						const input =
							containerRef.current?.querySelector('input');

						input?.blur();
					}}
					placeholder={placeholder}
					refetchOnActive
					value={inputValue}
				>
					{(item) => (
						<ItemSelector.Item key={item.id} textValue={item.name}>
							{item.name}
						</ItemSelector.Item>
					)}
				</ItemSelector>
			)}

			<div className="lfr-cmp__vocabulary-multi-select-selected">
				{value.map((category) => (
					<Label
						closeButtonProps={{
							'aria-label': Liferay.Language.get('remove'),
							disabled,
							'onClick': (event: React.MouseEvent) => {
								event.preventDefault();

								removeCategory(category.id);
							},
						}}
						displayType="secondary"
						inverse
						key={category.id}
					>
						{category.name}
					</Label>
				))}
			</div>
		</div>
	);
}
