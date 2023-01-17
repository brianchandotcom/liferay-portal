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

import ClayEmptyState from '@clayui/empty-state';
import PropTypes from 'prop-types';
import React from 'react';

import {CONTENT_TYPE_LABELS} from '../../../app/config/constants/contentTypeLabels';
import Collapse from '../../../common/components/Collapse';
import PageContent from './PageContent';

export default function ContentList({contents, selectedType}) {
	const filteredContentTypes = Object.keys(contents);
	const hasSelectedType =
		selectedType && selectedType !== CONTENT_TYPE_LABELS.allContent;

	return (
		<div className="overflow-auto page-editor__page-contents__content-list pt-4 px-3">
			{(filteredContentTypes.includes(selectedType) ||
				!hasSelectedType) &&
			!!filteredContentTypes.length ? (
				hasSelectedType ? (
					<PageContentList
						pageContents={contents}
						type={selectedType}
					/>
				) : (
					Object.keys(contents).map((type) => (
						<Collapse key={type} label={type} open>
							<PageContentList
								pageContents={contents}
								type={type}
							/>
						</Collapse>
					))
				)
			) : (
				<ClayEmptyState
					description={Liferay.Language.get(
						'try-again-with-a-different-search'
					)}
					imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
					small
					title={Liferay.Language.get('no-results-found')}
				/>
			)}
		</div>
	);
}

ContentList.propTypes = {
	contents: PropTypes.object,
	selectedType: PropTypes.string,
};

const PageContentList = ({pageContents, type}) => (
	<ul className="list-unstyled mb-1">
		{pageContents[type || CONTENT_TYPE_LABELS.allContent]?.map(
			(pageContent, index) => (
				<PageContent
					key={`${pageContent.classPK}${index}`}
					{...pageContent}
				/>
			)
		)}
	</ul>
);

PageContentList.proptypes = {
	pageContents: PropTypes.array,
	type: PropTypes.string,
};
