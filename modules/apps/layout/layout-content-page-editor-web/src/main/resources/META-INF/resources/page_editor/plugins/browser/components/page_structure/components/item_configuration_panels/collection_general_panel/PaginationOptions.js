/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {PAGINATION_ERROR_MESSAGES} from '../../../../../../../app/config/constants/paginationErrorMessages';
import {config} from '../../../../../../../app/config/index';
import {WarningMessage} from '../../../../../../../common/components/WarningMessage';

export function PaginationOptions({
	displayAllPages,
	handleConfigurationChanged,
	initialNumberOfItemsPerPage,
	initialNumberOfPages,
	warningMessage = '',
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
			errorMessage = sub(
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
					'has-warning': numberOfItemsPerPageError || warningMessage,
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

				{numberOfItemsPerPageError && (
					<WarningMessage message={numberOfItemsPerPageError} />
				)}

				{warningMessage && <WarningMessage message={warningMessage} />}
			</ClayForm.Group>
		</>
	);
}

PaginationOptions.propTypes = {
	displayAllPages: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	initialNumberOfItemsPerPage: PropTypes.number.isRequired,
	initialNumberOfPages: PropTypes.number.isRequired,
	warningMessage: PropTypes.string,
};
