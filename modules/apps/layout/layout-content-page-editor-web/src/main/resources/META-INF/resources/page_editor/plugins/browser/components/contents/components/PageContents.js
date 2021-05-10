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

import {CONTENT_TYPE_LABELS} from '../../../../../app/config/constants/contentTypeLabels';
import ContentFilter from './ContentFilter';
import ContentList from './ContentList';

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
			<ContentFilter
				contentTypes={sortedTypes}
				onChangeInput={setSearchValue}
				onChangeSelect={setSelectedType}
				selectedType={selectedType}
			/>
			<ContentList
				contents={filteredContents}
				selectedType={selectedType}
			/>
		</>
	);
}

PageContents.propTypes = {
	pageContents: PropTypes.object,
};
