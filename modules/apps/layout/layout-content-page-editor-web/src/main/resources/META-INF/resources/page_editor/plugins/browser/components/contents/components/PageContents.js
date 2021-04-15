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

import Collapse from '../../../../../common/components/Collapse';
import PageContent from './PageContent';
import SearchContents from './SearchContents';

export default function PageContents({pageContents}) {
	return (
		<>
			<SearchContents />

			{Object.keys(pageContents).map((type) => (
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
