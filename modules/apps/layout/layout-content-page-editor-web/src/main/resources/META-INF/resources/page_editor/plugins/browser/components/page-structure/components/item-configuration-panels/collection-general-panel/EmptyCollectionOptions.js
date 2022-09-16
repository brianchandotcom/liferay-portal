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
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {useSelector} from '../../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../../app/selectors/selectLanguageId';
import {getEditableLocalizedValue} from '../../../../../../../app/utils/getEditableLocalizedValue';
import CurrentLanguageFlag from '../../../../../../../common/components/CurrentLanguageFlag';
import {PopoverTooltip} from '../../../../../../../common/components/PopoverTooltip';
import useControlledState from '../../../../../../../core/hooks/useControlledState';
import {useId} from '../../../../../../../core/hooks/useId';

export function EmptyCollectionOptions({
	collectionEmptyCollectionMessageId,
	emptyCollectionOptions,
	handleConfigurationChanged,
}) {
	const {displayMessage = true} = emptyCollectionOptions || {};
	const tooltipId = useId();

	const handleDisplayMessageChanged = (event) =>
		handleConfigurationChanged({
			emptyCollectionOptions: {
				...emptyCollectionOptions,
				displayMessage: event.target.checked,
			},
		});

	const languageId = useSelector(selectLanguageId);

	const [
		messageForSelectedLanguage,
		setMessageForSelectedLanguage,
	] = useControlledState(
		getEditableLocalizedValue(
			emptyCollectionOptions?.message,
			languageId,
			Liferay.Language.get('no-results-found')
		)
	);

	return (
		<>
			<div className="align-items-center d-flex mb-2 pt-1">
				<ClayCheckbox
					aria-describedby={tooltipId}
					checked={displayMessage}
					containerProps={{className: 'mb-0'}}
					label={Liferay.Language.get('show-empty-collection-alert')}
					onChange={handleDisplayMessageChanged}
				/>

				<PopoverTooltip
					alignPosition="left"
					content={Liferay.Language.get(
						'information-message-displayed-in-view-mode-when-the-collection-is-empty-or-no-results-match-the-applied-filters'
					)}
					header={Liferay.Language.get('empty-collection-alert')}
					id={tooltipId}
					trigger={
						<ClayIcon
							aria-label={Liferay.Language.get('show-more')}
							className="ml-2"
							symbol="question-circle-full"
						/>
					}
				/>
			</div>

			{displayMessage && (
				<ClayForm.Group small>
					<label htmlFor={collectionEmptyCollectionMessageId}>
						{Liferay.Language.get('empty-collection-alert')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<ClayInput
								id={collectionEmptyCollectionMessageId}
								onBlur={() =>
									handleConfigurationChanged({
										emptyCollectionOptions: {
											...emptyCollectionOptions,
											message: {
												...emptyCollectionOptions?.message,
												[languageId]: messageForSelectedLanguage,
											},
										},
									})
								}
								onChange={(event) =>
									setMessageForSelectedLanguage(
										event.target.value
									)
								}
								type="text"
								value={messageForSelectedLanguage || ''}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<CurrentLanguageFlag />
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			)}
		</>
	);
}

EmptyCollectionOptions.propTypes = {
	collectionEmptyCollectionMessageId: PropTypes.string.isRequired,
	emptyCollectionOptions: PropTypes.shape({
		displayMessage: PropTypes.bool,
		message: PropTypes.object,
	}),
	handleConfigurationChanged: PropTypes.func.isRequired,
};
