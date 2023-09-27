/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {FieldBase} from 'dynamic-data-mapping-form-field-type/FieldBase/ReactFieldBase.es';
import {openSelectionModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

function parseInputValue(inputValue) {
	if (!inputValue) {
		return {};
	}

	if (typeof inputValue === 'object') {
		return inputValue;
	}

	return JSON.parse(inputValue);
}

const JournalArticleSelector = ({
	disabled,
	editingLanguageId,
	inputValue,
	itemSelectorURL,
	message,
	name,
	onChange,
	portletNamespace,
}) => {
	const [article, setArticle] = useState(() => parseInputValue(inputValue));

	useEffect(() => {
		setArticle(parseInputValue(inputValue));
	}, [inputValue]);

	const handleClearClick = () => {
		setArticle({});
		onChange('');
	};

	const handleFieldChanged = (selectedItem) => {
		if (selectedItem && selectedItem.value) {
			setArticle(JSON.parse(selectedItem.value));
			onChange(selectedItem.value);
		}
	};

	const handleItemSelectorTriggerClick = (event) => {
		event.preventDefault();

		openSelectionModal({
			onSelect: handleFieldChanged,
			selectEventName: `${portletNamespace}selectJournalArticle`,
			title: Liferay.Language.get('journal-article'),
			url: itemSelectorURL,
		});
	};

	return (
		<ClayForm.Group style={{marginBottom: '0.5rem'}}>
			<ClayInput.Group>
				<ClayInput.GroupItem className="d-none d-sm-block" prepend>
					<input
						name={name}
						type="hidden"
						value={JSON.stringify(article)}
					/>

					<ClayInput
						className="bg-light"
						dir={Liferay.Language.direction[editingLanguageId]}
						disabled={disabled}
						lang={editingLanguageId}
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

			{message && <div className="form-feedback-item">{message}</div>}
		</ClayForm.Group>
	);
};

const Main = ({
	editingLanguageId,
	itemSelectorURL,
	message,
	name,
	onChange,
	portletNamespace,
	predefinedValue,
	readOnly,
	value,
	...otherProps
}) => (
	<FieldBase {...otherProps} name={name} readOnly={readOnly}>
		<JournalArticleSelector
			disabled={readOnly}
			editingLanguageId={editingLanguageId}
			inputValue={value && value !== '' ? value : predefinedValue}
			itemSelectorURL={itemSelectorURL}
			message={message}
			name={name}
			onChange={(value) => onChange({}, value)}
			portletNamespace={portletNamespace}
		/>
	</FieldBase>
);

Main.displayName = 'JournalArticleSelector';

export default Main;
