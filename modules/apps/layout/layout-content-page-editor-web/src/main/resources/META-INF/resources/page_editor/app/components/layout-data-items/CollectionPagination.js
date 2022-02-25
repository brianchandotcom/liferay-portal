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

import ClayButton from '@clayui/button';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../config/index';
import {useIsActive} from '../../contexts/ControlsContext';

export default function CollectionPagination({
	activePage,
	collectionConfig,
	collectionId,
	onPageChange,
	totalNumberOfItems,
	totalPages,
}) {
	const isActive = useIsActive();
	const {
		displayAllPages,
		numberOfItems,
		numberOfItemsPerPage,
		numberOfPages,
		paginationType,
		showAllItems,
	} = collectionConfig;

	const numericPaginationLabel = [
		numberOfItemsPerPage && numberOfItems && totalNumberOfItems
			? (activePage - 1) * numberOfItemsPerPage + 1
			: 0,
		paginationType && showAllItems
			? Math.min(activePage * numberOfItemsPerPage, totalNumberOfItems)
			: Math.min(
					Math.min(activePage * numberOfItemsPerPage, numberOfItems),
					totalNumberOfItems
			  ),
		paginationType && showAllItems
			? totalNumberOfItems
			: Math.min(numberOfItems, totalNumberOfItems),
	];

	const itemsPerPage = Math.min(
		numberOfItemsPerPage,
		config.searchContainerPageMaxDelta
	);

	const numericPaginationLabelValues = [
		totalNumberOfItems ? (activePage - 1) * itemsPerPage + 1 : 0,
		Math.min(activePage * itemsPerPage, totalNumberOfItems),
		displayAllPages
			? totalNumberOfItems
			: Math.min(numberOfPages * itemsPerPage, totalNumberOfItems),
	];

	return (
		<div
			className={classNames(
				'page-editor__collection__pagination d-flex',
				{
					'page-editor__collection__pagination__overlay':
						totalNumberOfItems < 1 || !isActive(collectionId),
					'pt-3 pb-2': paginationType === 'numeric',
					'py-3': paginationType === 'simple',
				}
			)}
		>
			{paginationType === 'numeric' ? (
				<ClayPaginationBar className="flex-grow-1">
					<ClayPaginationBar.Results>
						{Liferay.Util.sub(
							Liferay.Language.get('showing-x-to-x-of-x-entries'),
							config.paginationImprovementsEnabled
								? numericPaginationLabelValues
								: numericPaginationLabel
						)}
					</ClayPaginationBar.Results>

					<ClayPaginationWithBasicItems
						activePage={activePage}
						disableEllipsis={config.paginationImprovementsEnabled}
						onPageChange={onPageChange}
						totalPages={
							(Number.isFinite(totalPages) && totalPages) || 1
						}
					/>
				</ClayPaginationBar>
			) : (
				<div className="d-flex flex-grow-1 h-100 justify-content-center">
					<ClayButton
						className="font-weight-semi-bold mr-3 text-secondary"
						disabled={activePage === 1}
						displayType="unstyled"
					>
						<span
							className="c-inner"
							onClick={() => onPageChange(activePage - 1)}
							tabIndex="-1"
						>
							{Liferay.Language.get('previous')}
						</span>
					</ClayButton>

					<ClayButton
						className="font-weight-semi-bold ml-3 text-secondary"
						disabled={activePage === totalPages}
						displayType="unstyled"
					>
						<span
							className="c-inner"
							onClick={() => onPageChange(activePage + 1)}
							tabIndex="-1"
						>
							{Liferay.Language.get('next')}
						</span>
					</ClayButton>
				</div>
			)}
		</div>
	);
}

CollectionPagination.propTypes = {
	activePage: PropTypes.number,
	collectionConfig: PropTypes.object,
	collectionId: PropTypes.string,
	onPageChange: PropTypes.func,
	totalNumberOfItems: PropTypes.number,
};
