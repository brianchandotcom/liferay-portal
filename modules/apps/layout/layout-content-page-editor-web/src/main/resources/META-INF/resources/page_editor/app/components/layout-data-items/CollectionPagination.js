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

import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useIsActive} from '../../contexts/ControlsContext';

const TOTAL_ENTRIES = 20;

export default function CollectionPagination({collectionConfig, collectionId}) {
	const [activePage, setActivePage] = useState(1);
	const isActive = useIsActive();

	const totalPages = Math.ceil(
		TOTAL_ENTRIES / collectionConfig.numberOfItems
	);

	return (
		<div
			className={classNames('page-editor__collection__pagination', {
				'page-editor__collection__pagination__overlay': !isActive(
					collectionId
				),
			})}
		>
			{collectionConfig.paginationType === 'regular' && (
				<ClayPaginationBar>
					<ClayPaginationBar.Results>
						{Liferay.Util.sub(
							Liferay.Language.get('showing-x-to-x-of-x-entries'),
							[
								collectionConfig.numberOfItems *
									(activePage - 1) || 1,
								collectionConfig.numberOfItems * activePage,
								TOTAL_ENTRIES,
							]
						)}
					</ClayPaginationBar.Results>

					<ClayPaginationWithBasicItems
						activePage={activePage}
						onPageChange={setActivePage}
						totalPages={totalPages}
					/>
				</ClayPaginationBar>
			)}
		</div>
	);
}

CollectionPagination.propTypes = {
	collectionConfig: PropTypes.object,
	collectionId: PropTypes.string,
};
