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

import ClayLabel from '@clayui/label';
import PropTypes from 'prop-types';
import React, {Fragment} from 'react';

import {groupVocabulariesBy, sortByStrings} from './utils/taxonomiesUtils';

const ItemVocabularies = ({cssClassNames = '', title, vocabularies}) => {
	const [global, nonGlobal] = groupVocabulariesBy({
		array: vocabularies,
		key: 'groupName',
		value: 'global',
	});

	const globalSorted = sortByStrings({array: global, key: 'vocabularyName'});
	const nonGlobalSorted = sortByStrings({
		array: nonGlobal,
		key: 'vocabularyName',
	});

	const groupedAndSortedVocabularies = nonGlobalSorted.concat(globalSorted);

	return (
		<div
			className={`c-mb-4 item-vocabularies sidebar-section ${cssClassNames}`}
		>
			<h6 className="font-weight-semi-bold sidebar-section-subtitle-sm text-2 text-secondary text-uppercase">
				{title}
			</h6>

			<div>
				{groupedAndSortedVocabularies.map(
					({categories, groupName, vocabularyName}) => (
						<Fragment key={vocabularyName}>
							<h5 className="c-mb-2 font-weight-semi-bold">
								{vocabularyName}

								{groupName ? ` (${groupName})` : ''}
							</h5>

							<p>
								{sortByStrings({array: categories}).map(
									(category) => (
										<ClayLabel
											className="c-mb-2 c-mr-2"
											displayType="secondary"
											key={category}
											large
										>
											{category}
										</ClayLabel>
									)
								)}
							</p>
						</Fragment>
					)
				)}
			</div>
		</div>
	);
};
ItemVocabularies.defaultProps = {
	cssClassNames: '',
};

ItemVocabularies.propTypes = {
	cssClassNames: PropTypes.string,
	title: PropTypes.string.isRequired,
	vocabularies: PropTypes.array.isRequired,
};

export default ItemVocabularies;
