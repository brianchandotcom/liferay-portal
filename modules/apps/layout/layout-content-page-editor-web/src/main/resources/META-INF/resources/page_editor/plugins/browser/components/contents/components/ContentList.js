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
import React from 'react';

import {CONTENT_TYPE_LABELS} from '../../../../../app/config/constants/contentTypeLabels';
import Collapse from '../../../../../common/components/Collapse';
import NoPageContents from './NoPageContents';
import PageContent from './PageContent';

export default function ContentList({contents, selectedType}) {
	const filteredContentTypes = Object.keys(contents);
	const hasSelectedType = selectedType !== CONTENT_TYPE_LABELS.allContent;

	return (filteredContentTypes.includes(selectedType) || !hasSelectedType) &&
		filteredContentTypes.length ? (
		hasSelectedType ? (
			<PageContentList pageContents={contents} type={selectedType} />
		) : (
			Object.keys(contents).map((type) => (
				<Collapse key={type} label={type} open>
					<PageContentList pageContents={contents} type={type} />
				</Collapse>
			))
		)
	) : (
		<NoPageContents />
	);
}

ContentList.propTypes = {
	contents: PropTypes.object,
	selectedType: PropTypes.string,
};

const PageContentList = ({pageContents, type}) => (
	<ul className="list-unstyled mb-1">
		{pageContents[type]?.map((pageContent, index) => (
			<PageContent
				key={`${pageContent.classPK}${index}`}
				{...pageContent}
			/>
		))}
	</ul>
);

PageContentList.proptypes = {
	pageContents: PropTypes.array,
	type: PropTypes.string,
};
