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

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {PAGINATION_ERROR_MESSAGES} from '../../../../../../../app/config/constants/paginationErrorMessages';
import {config} from '../../../../../../../app/config/index';
import {useId} from '../../../../../../../app/utils/useId';
import {WarningMessage} from '../../../../../../../common/components/WarningMessage';
import useControlledState from '../../../../../../../core/hooks/useControlledState';

export function PaginationOptions({
	displayAllPages,
	handleConfigurationChanged,
	initialNumberOfItemsPerPage,
	initialNumberOfPages,
}) {
	const collectionNumberOfItemsPerPageId = useId();
	const collectionNumberOfPagesId = useId();

	const [numberOfItemsPerPage, setNumberOfItemsPerPage] = useControlledState(
		initialNumberOfItemsPerPage
	);
	const [numberOfItemsPerPageError, setNumberOfItemsPerPageError] = useState(
		null
	);
	const [numberOfPages, setNumberOfPages] = useState(initialNumberOfPages);

	const isMaximumValuePerPageError =
		initialNumberOfItemsPerPage > config.searchContainerPageMaxDelta;

	useEffect(() => {
		let errorMessage = null;

		if (isMaximumValuePerPageError) {
			errorMessage = Liferay.Util.sub(
				PAGINATION_ERROR_MESSAGES.maximumItemsPerPage,
				config.searchContainerPageMaxDelta
			);
		}
		else if (initialNumberOfItemsPerPage < 1) {
			errorMessage = PAGINATION_ERROR_MESSAGES.neededItem;
		}

		setNumberOfItemsPerPageError(errorMessage);
	}, [isMaximumValuePerPageError, initialNumberOfItemsPerPage]);

	const handleCollectionNumberOfItemsPerPageBlurred = (event) => {
		const nextValue = Math.abs(Number(event.target.value)) || 1;

		if (!numberOfItemsPerPage || numberOfItemsPerPage < 0) {
			setNumberOfItemsPerPage(nextValue);
		}

		if (nextValue !== initialNumberOfItemsPerPage) {
			handleConfigurationChanged({
				numberOfItemsPerPage: nextValue,
			});
		}
	};

	const handleCollectionNumberOfPagesBlurred = (event) => {
		const nextValue = Math.abs(Number(event.target.value)) || 1;

		if (!numberOfPages || numberOfPages < 0) {
			setNumberOfPages(nextValue);
		}

		if (nextValue !== initialNumberOfPages) {
			handleConfigurationChanged({
				numberOfPages: nextValue,
			});
		}
	};

	const handleDisplayAllPagesChanged = (event) =>
		handleConfigurationChanged({
			displayAllPages: event.target.checked,
		});

	return (
		<>
			<div className="mb-2 pt-1">
				<ClayCheckbox
					checked={displayAllPages}
					label={Liferay.Language.get('display-all-pages')}
					onChange={handleDisplayAllPagesChanged}
				/>
			</div>

			{!displayAllPages && (
				<ClayForm.Group small>
					<label htmlFor={collectionNumberOfPagesId}>
						{Liferay.Language.get(
							'maximum-number-of-pages-to-display'
						)}
					</label>

					<ClayInput
						id={collectionNumberOfPagesId}
						min="1"
						onBlur={handleCollectionNumberOfPagesBlurred}
						onChange={(event) =>
							setNumberOfPages(Number(event.target.value))
						}
						type="number"
						value={numberOfPages || ''}
					/>
				</ClayForm.Group>
			)}

			<ClayForm.Group
				className={classNames({
					'has-warning': numberOfItemsPerPageError,
				})}
				small
			>
				<label htmlFor={collectionNumberOfItemsPerPageId}>
					{Liferay.Language.get('maximum-number-of-items-per-page')}
				</label>

				<ClayInput
					id={collectionNumberOfItemsPerPageId}
					min="1"
					onBlur={handleCollectionNumberOfItemsPerPageBlurred}
					onChange={(event) =>
						setNumberOfItemsPerPage(Number(event.target.value))
					}
					type="number"
					value={numberOfItemsPerPage || ''}
				/>

				<div className="mb-2 mt-1">
					<span
						className={classNames(
							'mr-1 small',
							isMaximumValuePerPageError &&
								numberOfItemsPerPageError
								? 'text-warning'
								: 'text-secondary',
							{
								'font-weight-bold':
									isMaximumValuePerPageError &&
									numberOfItemsPerPageError,
							}
						)}
					>
						{Liferay.Util.sub(
							Liferay.Language.get('x-items-maximum'),
							config.searchContainerPageMaxDelta
						)}
					</span>

					{numberOfItemsPerPageError && (
						<WarningMessage message={numberOfItemsPerPageError} />
					)}
				</div>
			</ClayForm.Group>
		</>
	);
}

PaginationOptions.propTypes = {
	displayAllPages: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	initialNumberOfItemsPerPage: PropTypes.number.isRequired,
	initialNumberOfPages: PropTypes.number.isRequired,
};
