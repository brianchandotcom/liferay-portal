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
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useSelector} from '../../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../../app/selectors/selectLanguageId';
import {getEditableLocalizedValue} from '../../../../../../../app/utils/getEditableLocalizedValue';
import {useId} from '../../../../../../../app/utils/useId';
import CurrentLanguageFlag from '../../../../../../../common/components/CurrentLanguageFlag';
import useControlledState from '../../../../../../../core/hooks/useControlledState';

export function EmptyCollectionOptions({
	collectionEmptyCollectionMessageId,
	emptyCollectionOptions,
	handleConfigurationChanged,
}) {
	const {displayMessage = true} = emptyCollectionOptions || {};

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
			{
				<div className="align-items-center d-flex mb-2 pt-1">
					<div>
						<ClayCheckbox
							checked={displayMessage}
							className="mb-0"
							label={Liferay.Language.get(
								'show-empty-collection-alert'
							)}
							onChange={handleDisplayMessageChanged}
						/>
					</div>

					<EmptyCollectionHelp />
				</div>
			}

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

function EmptyCollectionHelp() {
	const id = useId();

	const [showPopover, setShowPopover] = useState(false);

	return (
		<ClayPopover
			alignPosition="top-right"
			className="position-fixed"
			disableScroll
			header={Liferay.Language.get('empty-collection-alert')}
			id={id}
			onShowChange={setShowPopover}
			role="tooltip"
			show={showPopover}
			trigger={
				<span
					aria-describedby={id}
					onBlur={() => setShowPopover(false)}
					onFocus={() => setShowPopover(true)}
					onMouseEnter={() => setShowPopover(true)}
					onMouseLeave={() => setShowPopover(false)}
					tabIndex="0"
				>
					<ClayIcon
						className="ml-2 text-secondary"
						symbol="question-circle-full"
					/>
				</span>
			}
		>
			{Liferay.Language.get(
				'information-message-displayed-in-view-mode-when-the-collection-is-empty-or-no-results-match-the-applied-filters'
			)}
		</ClayPopover>
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
