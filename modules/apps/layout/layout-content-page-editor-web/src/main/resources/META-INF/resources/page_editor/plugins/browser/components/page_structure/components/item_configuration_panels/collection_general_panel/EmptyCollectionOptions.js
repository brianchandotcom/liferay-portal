/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

import {useSelector} from '../../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../../app/selectors/selectLanguageId';
import {getEditableLocalizedValue} from '../../../../../../../app/utils/getEditableLocalizedValue';
import CurrentLanguageFlag from '../../../../../../../common/components/CurrentLanguageFlag';

export function EmptyCollectionOptions({
	collectionEmptyCollectionMessageId,
	emptyCollectionOptions,
	handleConfigurationChanged,
}) {
	const {displayMessage = true} = emptyCollectionOptions || {};
	const helpTextId = useId();

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
			<div className="mb-2 pt-1">
				<ClayCheckbox
					aria-describedby={helpTextId}
					checked={displayMessage}
					containerProps={{className: 'mb-0'}}
					label={Liferay.Language.get('show-empty-collection-alert')}
					onChange={handleDisplayMessageChanged}
				/>

				<div className="mb-3 mt-1 small text-secondary" id={helpTextId}>
					{Liferay.Language.get(
						'checking-this-option-will-display-an-informative-message-in-view-mode-when-no-results-match-the-applied-filters-or-the-collection-is-empty'
					)}
				</div>
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
