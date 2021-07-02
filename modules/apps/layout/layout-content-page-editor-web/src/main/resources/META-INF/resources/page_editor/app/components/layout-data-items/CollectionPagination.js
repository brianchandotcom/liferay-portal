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

import {useIsActive} from '../../contexts/ControlsContext';

export default function CollectionPagination({
	activePage,
	collectionConfig,
	collectionId,
	onPageChange,
	totalNumberOfItems,
}) {
	const isActive = useIsActive();
	const {
		numberOfItems,
		numberOfItemsPerPage,
		paginationType,
	} = collectionConfig;

	const totalPages = Math.ceil(totalNumberOfItems / numberOfItemsPerPage);

	const simplePaginationButtons = [
		{
			disabled: activePage === 1,
			label: Liferay.Language.get('previous'),
			onClick: () => onPageChange(activePage - 1),
		},
		{
			disabled: activePage === totalPages,
			label: Liferay.Language.get('next'),
			onClick: () => onPageChange(activePage + 1),
		},
	];

	const regularPaginationLabel = [
		numberOfItemsPerPage && numberOfItems && totalNumberOfItems
			? (activePage - 1) * numberOfItemsPerPage + 1
			: 0,
		Math.min(
			Math.min(activePage * numberOfItemsPerPage, numberOfItems),
			totalNumberOfItems
		),
		Math.min(numberOfItems, totalNumberOfItems),
	];

	return (
		<div
			className={classNames('page-editor__collection__pagination', {
				'page-editor__collection__pagination__overlay': !isActive(
					collectionId
				),
			})}
		>
			{paginationType === 'regular' ? (
				<ClayPaginationBar>
					<ClayPaginationBar.Results>
						{Liferay.Util.sub(
							Liferay.Language.get('showing-x-to-x-of-x-entries'),
							regularPaginationLabel
						)}
					</ClayPaginationBar.Results>

					<ClayPaginationWithBasicItems
						activePage={activePage}
						onPageChange={onPageChange}
						totalPages={
							Number.isFinite(totalPages) ? totalPages : 0
						}
					/>
				</ClayPaginationBar>
			) : (
				<div className="page-editor__collection__pagination--simple">
					{simplePaginationButtons.map(
						({disabled, label, onClick}) => (
							<ClayButton
								disabled={disabled}
								displayType="unstyled"
								key={label}
							>
								<span
									className="c-inner"
									onClick={onClick}
									tabIndex="-1"
								>
									{label}
								</span>
							</ClayButton>
						)
					)}
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
