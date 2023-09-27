/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SearchResultsMessage} from '@liferay/layout-js-components-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {CONTENT_TYPE_LABELS} from '../../../app/config/constants/contentTypeLabels';
import ContentFilter from './ContentFilter';
import ContentList from './ContentList';

export default function PageContents({pageContents}) {
	const [searchValue, setSearchValue] = useState('');
	const [selectedType, setSelectedType] = useState(null);

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

	const numberOfResults = useMemo(
		() =>
			filteredContents !== pageContents || selectedType
				? (
						filteredContents[selectedType] ||
						Object.values(filteredContents).flatMap(
							(content) => content
						)
				  ).length
				: null,
		[filteredContents, selectedType, pageContents]
	);

	return (
		<>
			<ContentFilter
				contentTypes={sortedTypes}
				onChangeInput={setSearchValue}
				onChangeSelect={setSelectedType}
				selectedType={selectedType || CONTENT_TYPE_LABELS.allContent}
			/>
			<ContentList
				contents={filteredContents}
				selectedType={selectedType}
			/>
			<SearchResultsMessage numberOfResults={numberOfResults} />
		</>
	);
}

PageContents.propTypes = {
	pageContents: PropTypes.object,
};
