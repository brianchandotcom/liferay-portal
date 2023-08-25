/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useControlledState} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {PAGINATION_ERROR_MESSAGES} from '../../../../../../../app/config/constants/paginationErrorMessages';
import {config} from '../../../../../../../app/config/index';
import CollectionService from '../../../../../../../app/services/CollectionService';
import {WarningMessage} from '../../../../../../../common/components/WarningMessage';

export function NoPaginationOptions({
	collection,
	displayAllItems,
	handleConfigurationChanged,
	initialNumberOfItems,
	warningMessage = '',
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
				errorMessage = sub(
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
		<ClayForm.Group small>
			<div className="mb-2 pt-1">
				<ClayCheckbox
					checked={displayAllItems}
					label={Liferay.Language.get('display-all-collection-items')}
					onChange={handleDisplayAllItemsChanged}
				/>
			</div>

			{!displayAllItems && (
				<ClayForm.Group
					className={classNames({
						'has-warning': numberOfItemsError,
					})}
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

					{warningMessage ? null : (
						<p className="mt-1 small text-secondary">
							{sub(
								Liferay.Language.get(
									'setting-a-value-above-x-can-affect-page-performance-severely'
								),
								config.searchContainerPageMaxDelta
							)}
						</p>
					)}

					{numberOfItemsError && (
						<WarningMessage message={numberOfItemsError} />
					)}
				</ClayForm.Group>
			)}

			{warningMessage && (
				<WarningMessage fontWeight="normal" message={warningMessage} />
			)}
		</ClayForm.Group>
	);
}

NoPaginationOptions.propTypes = {
	collection: PropTypes.object.isRequired,
	displayAllItems: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	initialNumberOfItems: PropTypes.number.isRequired,
	warningMessage: PropTypes.string,
};
