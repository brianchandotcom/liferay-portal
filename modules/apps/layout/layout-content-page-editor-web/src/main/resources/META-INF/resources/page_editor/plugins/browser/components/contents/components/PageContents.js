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

import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import Collapse from '../../../../../common/components/Collapse';
import PageContent from './PageContent';
import SearchContents from './SearchContents';

export const CONTENT_TYPE_LABELS = {
	allContent: Liferay.Language.get('all-content'),
	collection: Liferay.Language.get('collection'),
	inlineText: Liferay.Language.get('inline-text'),
};

export default function PageContents({pageContents}) {
	const [searchValue, setSearchValue] = useState('');
	const [selectedType, setSelectedType] = useState(
		CONTENT_TYPE_LABELS.allContent
	);

	const contentTypes = Object.keys(pageContents);

	const sortedTypes = contentTypes.includes(CONTENT_TYPE_LABELS.collection)
		? [
				...[
					CONTENT_TYPE_LABELS.allContent,
					CONTENT_TYPE_LABELS.collection,
				],
				...contentTypes.filter(
					(type) => type !== CONTENT_TYPE_LABELS.collection
				),
		  ]
		: [CONTENT_TYPE_LABELS.allContent, ...contentTypes];

	const filteredContents = useMemo(
		() =>
			searchValue
				? Object.entries(pageContents).reduce(
						(acc, [key, pageContent]) => {
							const filteredContent = pageContent.filter(
								(content) =>
									content.title
										.toLowerCase()
										.indexOf(searchValue.toLowerCase()) !==
									-1
							);

							return filteredContent.length
								? {...acc, ...{[key]: filteredContent}}
								: acc;
						},
						{}
				  )
				: pageContents,
		[pageContents, searchValue]
	);

	return (
		<>
			<SearchContents
				contentTypes={sortedTypes}
				onChangeInput={setSearchValue}
				onChangeSelect={setSelectedType}
				selectedType={selectedType}
			/>

			{Object.keys(filteredContents).map((type) => (
				<Collapse key={type} label={type} open>
					<ul className="list-unstyled mb-1">
						{pageContents[type].map((pageContent, index) => (
							<PageContent
								key={`${pageContent.classPK}${index}`}
								{...pageContent}
							/>
						))}
					</ul>
				</Collapse>
			))}
		</>
	);
}

PageContents.propTypes = {
	pageContents: PropTypes.object,
};
