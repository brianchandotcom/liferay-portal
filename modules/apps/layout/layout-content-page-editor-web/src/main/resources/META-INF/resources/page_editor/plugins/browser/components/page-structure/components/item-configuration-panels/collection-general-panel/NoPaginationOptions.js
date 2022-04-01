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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {PAGINATION_ERROR_MESSAGES} from '../../../../../../../app/config/constants/paginationErrorMessages';
import {config} from '../../../../../../../app/config/index';
import CollectionService from '../../../../../../../app/services/CollectionService';
import {useId} from '../../../../../../../app/utils/useId';
import {WarningMessage} from '../../../../../../../common/components/WarningMessage';
import useControlledState from '../../../../../../../core/hooks/useControlledState';

export function NoPaginationOptions({
	collection,
	displayAllItems,
	handleConfigurationChanged,
	initialNumberOfItems,
}) {
	const collectionNumberOfItemsId = useId();
	const isMounted = useIsMounted();

	const [numberOfItems, setNumberOfItems] = useControlledState(
		initialNumberOfItems
	);
	const [numberOfItemsError, setNumberOfItemsError] = useState(null);
	const [totalNumberOfItems, setTotalNumberOfItems] = useState(0);

	useEffect(() => {
		if (collection) {
			CollectionService.getCollectionItemCount({
				collection,
				onNetworkStatus: () => {},
			}).then(({totalNumberOfItems}) => {
				if (isMounted()) {
					setTotalNumberOfItems(totalNumberOfItems);
				}
			});
		}
	}, [collection, isMounted]);

	useEffect(() => {
		let errorMessage = null;

		if (totalNumberOfItems) {
			if (initialNumberOfItems > totalNumberOfItems) {
				errorMessage = Liferay.Util.sub(
					PAGINATION_ERROR_MESSAGES.maximumItems,
					totalNumberOfItems
				);
			}
		}
		else {
			errorMessage = PAGINATION_ERROR_MESSAGES.noItems;
		}

		setNumberOfItemsError(errorMessage);
	}, [totalNumberOfItems, initialNumberOfItems]);

	const handleDisplayAllItemsChanged = (event) =>
		handleConfigurationChanged({
			displayAllItems: event.target.checked,
		});

	const handleCollectionNumberOfItemsBlurred = (event) => {
		const nextValue = Math.abs(Number(event.target.value)) || 1;

		if (!numberOfItems || numberOfItems < 0) {
			setNumberOfItems(nextValue);
		}

		if (nextValue !== initialNumberOfItems) {
			handleConfigurationChanged({
				numberOfItems: nextValue,
			});
		}
	};

	return (
		<>
			<div className="mb-2 pt-1">
				<ClayCheckbox
					checked={displayAllItems}
					label={Liferay.Language.get('display-all-collection-items')}
					onChange={handleDisplayAllItemsChanged}
				/>
			</div>

			{displayAllItems && (
				<p className="mt-1 small text-secondary">
					{Liferay.Util.sub(
						Liferay.Language.get(
							'this-setting-can-affect-page-performance-severely-if-the-number-of-collection-items-is-above-x.-we-strongly-recommend-using-pagination-instead'
						),
						config.searchContainerPageMaxDelta
					)}
				</p>
			)}

			{!displayAllItems && (
				<ClayForm.Group
					className={classNames({
						'has-warning': numberOfItemsError,
					})}
					small
				>
					<label htmlFor={collectionNumberOfItemsId}>
						{Liferay.Language.get(
							'maximum-number-of-items-to-display'
						)}
					</label>

					<ClayInput
						id={collectionNumberOfItemsId}
						min="1"
						onBlur={handleCollectionNumberOfItemsBlurred}
						onChange={(event) =>
							setNumberOfItems(Number(event.target.value))
						}
						type="number"
						value={numberOfItems || ''}
					/>

					<p className="mt-1 small text-secondary">
						{Liferay.Util.sub(
							Liferay.Language.get(
								'setting-a-value-above-x-can-affect-page-performance-severely'
							),
							config.searchContainerPageMaxDelta
						)}
					</p>

					{numberOfItemsError && (
						<WarningMessage message={numberOfItemsError} />
					)}
				</ClayForm.Group>
			)}
		</>
	);
}

NoPaginationOptions.propTypes = {
	collection: PropTypes.object.isRequired,
	displayAllItems: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	initialNumberOfItems: PropTypes.number.isRequired,
};
