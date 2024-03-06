/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	const showPartialResultsMessage = dismissible
		? Liferay.Language.get(
				'your-responses-will-be-visible-to-all-form-respondents'
		  )
		: Liferay.Language.get('respondents-can-see-all-submitted-form-data');

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
				{showPartialResultsMessage}

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
