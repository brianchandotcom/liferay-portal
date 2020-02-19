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
import {getConnectedReactComponentAdapter} from 'dynamic-data-mapping-form-field-type';
import {ItemSelectorDialog} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import templates from './JournalArticleSelectorAdapter.soy';

const JournalArticleSelectorWithState = ({
	dispatch,
	inputValue,
	itemSelectorURL,
	name,
	portletNamespace,
	readOnly
}) => {
	const [article, setArticle] = useState({});

	useEffect(() => {
		setArticle({
			...JSON.parse(inputValue || '{}')
		});
	}, [inputValue]);

	const _dispatchValue = (value, clear) => {
		setArticle(() => {
			dispatch({
				payload: clear ? '' : JSON.stringify(value),
				type: 'value'
			});

			return value;
		});
	};

	const _handleClearClick = () => {
		_dispatchValue({}, true);
	};

	const _handleFieldChanged = event => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.value) {
			setArticle(() => {
				dispatch({
					payload: selectedItem.value,
					type: 'value'
				});

				return selectedItem.value;
			});
		}
	};

	const _handleItemSelectorTriggerClick = event => {
		event.preventDefault();

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectJournalArticle`,
			singleSelect: true,
			title: Liferay.Language.get('journal-article'),
			url: itemSelectorURL
		});

		itemSelectorDialog.on('selectedItemChange', _handleFieldChanged);

		itemSelectorDialog.open();
	};

	return (
		<>
			<ClayForm.Group style={{marginBottom: '0.5rem'}}>
				<ClayInput.Group>
					<ClayInput.GroupItem className="d-none d-sm-block" prepend>
						<input name={name} type="hidden" value={inputValue} />

						<ClayInput
							className="bg-light"
							disabled={readOnly}
							onClick={_handleItemSelectorTriggerClick}
							readOnly
							type="text"
							value={article.title || ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							disabled={readOnly}
							displayType="secondary"
							onClick={_handleItemSelectorTriggerClick}
							type="button"
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>

					{article.classPK && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								disabled={readOnly}
								displayType="secondary"
								onClick={_handleClearClick}
								type="button"
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>
		</>
	);
};

const ReactJournalArticleSelectorAdapter = getConnectedReactComponentAdapter(
	JournalArticleSelectorWithState,
	templates
);

export {ReactJournalArticleSelectorAdapter};
export default ReactJournalArticleSelectorAdapter;
