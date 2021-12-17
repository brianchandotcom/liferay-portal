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

import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React, {useState} from 'react';

import './ShowPartialResultsAlert.scss';

const ShowPartialResultsAlert: React.FC<IProps> = ({
	dismissible,
	showPartialResultsToRespondents,
}) => {
	const [isDismissed, setDismissed] = useState(
		!showPartialResultsToRespondents
	);

	return (
		<div
			className={classNames('lfr-ddm__show-partial-results-alert', {
				'lfr-ddm__show-partial-results-alert--hidden': isDismissed,
			})}
		>
			<ClayAlert
				displayType={'info' as DisplayType}
				onClose={dismissible ? () => setDismissed(true) : undefined}
				title="Info"
			>
				{Liferay.Language.get(
					'your-responses-will-be-visible-to-all-form-respondents'
				)}

				{dismissible && (
					<ClayAlert.Footer>
						<ClayButton.Group>
							<ClayButton
								alert
								onClick={() => setDismissed(true)}
							>
								{Liferay.Language.get('understood')}
							</ClayButton>
						</ClayButton.Group>
					</ClayAlert.Footer>
				)}
			</ClayAlert>
		</div>
	);
};

export default ShowPartialResultsAlert;

interface IProps {
	dismissible?: boolean;
	showPartialResultsToRespondents: boolean;
}
