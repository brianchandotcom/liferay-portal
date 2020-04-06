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

import ClayForm from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../AppContext.es';
import {getAllTags} from '../utils/client.es';

export default ({tagsChange, tagsLoaded, tags = [], ...props}) => {
	const context = useContext(AppContext);

	const [error, setError] = useState(false);
	const [inputValue, setInputValue] = useState('');
	const [items, setItems] = useState([]);
	const [sourceItems, setSourceItems] = useState([]);

	useEffect(() => {
		getAllTags(context.siteKey).then(data => {
			const vocabulariesOfQuestions = data.items.filter(
				vocabulary =>
					vocabulary.assetTypes.filter(
						assetType =>
							assetType.type === context.defaultVocabulary
					).length > 0
			);

			setSourceItems(
				vocabulariesOfQuestions
					.flatMap(vocabulary => vocabulary.taxonomyCategories.items)
					.map(({id, name}) => ({
						label: name,
						value: +id,
					}))
			);
		});
	}, [context.defaultVocabulary, context.siteKey]);

	useEffect(() => {
		if (tags.length) {
			setItems(tags);
		}
	}, [tags]);

	useEffect(() => {
		if (inputValue) {
			tagsLoaded(false);
		}
		else {
			tagsLoaded(true);
		}
	}, [inputValue, tagsLoaded]);

	const maxTags = tags => tags.length > 5;
	const duplicatedTags = tags =>
		new Set(tags.map(tag => tag.value)).size !== tags.length;
	const tagsAlreadyCreated = tags =>
		tags
			.map(tags => tags.value)
			.every(tag => sourceItems.map(tag => tag.value).includes(tag));

	const filterItems = tags => {
		if (
			tagsAlreadyCreated(tags) &&
			!maxTags(tags) &&
			!duplicatedTags(tags)
		) {
			setError(false);
			setItems(tags);
			tagsChange(tags);
		}
		else {
			setError(true);
		}
	};

	return (
		<>
			<ClayForm.Group className="c-mt-4">
				<label htmlFor="basicInput">
					{Liferay.Language.get('tags')}
				</label>
				<ClayMultiSelect
					{...props}
					inputValue={inputValue}
					items={items}
					onChange={setInputValue}
					onItemsChange={filterItems}
					sourceItems={sourceItems}
				/>
				<ClayForm.FeedbackGroup className={error && 'has-error'}>
					<ClayForm.FeedbackItem>
						<span className="small text-secondary">
							{Liferay.Language.get(
								'add-up-to-5-tags-to-describe-what-your-question-is-about'
							)}
						</span>
					</ClayForm.FeedbackItem>
					{error && (
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />
							{Liferay.Language.get('this-is-an-invalid-tag')}
						</ClayForm.FeedbackItem>
					)}
				</ClayForm.FeedbackGroup>
			</ClayForm.Group>
		</>
	);
};
