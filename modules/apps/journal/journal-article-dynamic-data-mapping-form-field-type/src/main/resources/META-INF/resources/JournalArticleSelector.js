/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {
	FieldBaseProxy,
	connectStore,
	getConnectedReactComponentAdapter,
} from 'dynamic-data-mapping-form-field-type';
import {ItemSelectorDialog} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const JournalArticleSelector = ({
	disabled,
	inputValue,
	itemSelectorURL,
	name,
	onChange,
	portletNamespace,
}) => {
	const [article, setArticle] = useState({});

	useEffect(() => {
		setArticle({
			...JSON.parse(inputValue || '{}'),
		});
	}, [inputValue]);

	const handleClearClick = () => {
		setArticle({});
		onChange('');
	};

	const handleFieldChanged = event => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.value) {
			setArticle(selectedItem.value);
			onChange(selectedItem.value);
		}
	};

	const handleItemSelectorTriggerClick = event => {
		event.preventDefault();

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectJournalArticle`,
			singleSelect: true,
			title: Liferay.Language.get('journal-article'),
			url: itemSelectorURL,
		});

		itemSelectorDialog.on('selectedItemChange', handleFieldChanged);

		itemSelectorDialog.open();
	};

	return (
		<ClayForm.Group style={{marginBottom: '0.5rem'}}>
			<ClayInput.Group>
				<ClayInput.GroupItem className="d-none d-sm-block" prepend>
					<input name={name} type="hidden" value={inputValue} />

					<ClayInput
						className="bg-light"
						disabled={disabled}
						onClick={handleItemSelectorTriggerClick}
						readOnly
						type="text"
						value={article.title || ''}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						disabled={disabled}
						displayType="secondary"
						onClick={handleItemSelectorTriggerClick}
						type="button"
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</ClayInput.GroupItem>

				{article.classPK && (
					<ClayInput.GroupItem shrink>
						<ClayButton
							disabled={disabled}
							displayType="secondary"
							onClick={handleClearClick}
							type="button"
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

const JournalArticleSelectorProxy = connectStore(
	({
		emit,
		itemSelectorURL,
		name,
		portletNamespace,
		predefinedValue,
		readOnly,
		value,
		...otherProps
	}) => (
		<FieldBaseProxy {...otherProps} name={name} readOnly={readOnly}>
			<JournalArticleSelector
				disabled={readOnly}
				inputValue={value && value !== '' ? value : predefinedValue}
				itemSelectorURL={itemSelectorURL}
				name={name}
				onChange={value => emit('fieldEdited', {}, value)}
				portletNamespace={portletNamespace}
			/>
		</FieldBaseProxy>
	)
);

const ReactJournalArticleSelectorAdapter = getConnectedReactComponentAdapter(
	JournalArticleSelectorProxy,
	'journal_article'
);

export {ReactJournalArticleSelectorAdapter};
export default ReactJournalArticleSelectorAdapter;
