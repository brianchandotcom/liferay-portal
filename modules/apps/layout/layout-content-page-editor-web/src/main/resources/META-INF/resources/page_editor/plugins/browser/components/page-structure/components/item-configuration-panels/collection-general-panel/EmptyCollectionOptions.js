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

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useId} from '../../../../../../../app/utils/useId';

export function EmptyCollectionOptions({
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

	return (
		<>
			{Liferay.FeatureFlags['LPS-160243'] && (
				<div className="d-flex mb-2 pt-1">
					<ClayCheckbox
						checked={displayMessage}
						label={Liferay.Language.get(
							'show-empty-collection-alert'
						)}
						onChange={handleDisplayMessageChanged}
					/>

					<EmptyCollectionHelp />
				</div>
			)}

		</>
	);
}

function EmptyCollectionHelp() {
	const id = useId();

	const [showPopover, setShowPopover] = useState(false);

	return (
		<ClayPopover
			alignPosition="top"
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
						className="text-secondary"
						symbol="question-circle"
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
	emptyCollectionOptions: PropTypes.shape({
		displayMessage: PropTypes.bool,
	}),
	handleConfigurationChanged: PropTypes.func.isRequired,
};
