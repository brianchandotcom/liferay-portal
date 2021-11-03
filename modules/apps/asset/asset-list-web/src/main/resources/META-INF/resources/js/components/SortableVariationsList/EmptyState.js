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
import ClayEmptyState from '@clayui/empty-state';
import PropTypes from 'prop-types';
import React from 'react';

const EmptyState = ({
	actionButtonUrl,
	availableSegments,
	disableActionButton,
	selectSegmentCallback,
	show,
}) => {
	if (!show) {
		return null;
	}

	const actionButtonText = availableSegments
		? 'New Personalized Variation'
		: 'Add your first segment';

	const handleClick = () => {
		if (!availableSegments) {
			Liferay.Util.navigate(actionButtonUrl);

			return;
		}

		const callback = window[selectSegmentCallback];
		if (typeof callback === 'function') {
			callback();
		}
	};

	return (
		<>
			{show && (
				<ClayEmptyState
					description={Liferay.Language.get(
						'no-personalized-variations-were-found'
					)}
					title="No Personalized Variations yet."
				>
					<ClayButton
						disabled={disableActionButton}
						displayType="primary"
						onClick={handleClick}
					>
						{actionButtonText}
					</ClayButton>
				</ClayEmptyState>
			)}
		</>
	);
};

EmptyState.defaultProps = {
	disableActionButton: false,
};

EmptyState.propTypes = {
	actionButtonUrl: PropTypes.string.isRequired,
	availableSegments: PropTypes.bool.isRequired,
	disableActionButton: PropTypes.bool,
	selectSegmentCallback: PropTypes.string.isRequired,
	show: PropTypes.bool.isRequired,
};

export default EmptyState;
