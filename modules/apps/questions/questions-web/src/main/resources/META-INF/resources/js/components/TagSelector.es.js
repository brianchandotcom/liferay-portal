/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {AssetTagsSelector} from 'asset-taglib';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../AppContext.es';
import langEs from '../utils/lang.es';

const TAGS_LIMIT = 5;
const noop = () => {};

export default function TagSelector({
	tagsLimit = TAGS_LIMIT,
	tagsChange,
	tagsLoaded = noop,
	tags = [],
	showSelectButton = true,
}) {
	const context = useContext(AppContext);

	const [error, setError] = useState(false);
	const [inputValue, setInputValue] = useState('');

	useEffect(() => {
		if (inputValue) {
			tagsLoaded(false);
		}
		else {
			tagsLoaded(true);
		}
	}, [inputValue, tagsLoaded]);

	const maxTags = (tags) => tags.length > tagsLimit;

	const duplicatedTags = (tags) =>
		new Set(tags.map((tag) => tag.value)).size !== tags.length;

	const filterItems = (tags) => {
		if (!maxTags(tags) && !duplicatedTags(tags)) {
			setError(false);
			tagsChange(tags);
		}
		else {
			setError(true);
		}
	};

	return (
		<ClayForm.Group className="c-mt-4">
			<div className="questions-tag-selector">
				<AssetTagsSelector
					eventName={`${context.portletNamespace}selectTag`}
					groupIds={[context.siteKey]}
					inputValue={inputValue}
					onInputValueChange={setInputValue}
					onSelectedItemsChange={filterItems}
					portletURL={context.tagSelectorURL}
					selectedItems={tags}
					showSelectButton={showSelectButton}
				/>
			</div>

			<ClayForm.FeedbackGroup className={error && 'has-error'}>
				<ClayForm.FeedbackItem>
					<span className="small text-secondary">
						{langEs.sub(
							Liferay.Language.get(
								'add-up-to-x-tags-to-describe-what-your-question-is-about'
							),
							[tagsLimit]
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
	);
}
