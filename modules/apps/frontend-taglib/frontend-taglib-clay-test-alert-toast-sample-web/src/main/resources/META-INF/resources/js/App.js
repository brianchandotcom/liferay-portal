/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {openToast} from 'frontend-js-web';
import React from 'react';

import '../css/main.scss';

export default function App() {
	const onClickSuccess = () => {
		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			title: Liferay.Language.get('success'),
			type: 'success',
		});
	};

	const onClickFail = () => {
		openToast({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	};

	return (
		<div>
			<ClayAlert title="Info">
				This widget is used to test out Clay Toast Alert.
			</ClayAlert>

			<div className="sheet-footer">
				<ClayButton.Group spaced>
					<ClayButton onClick={onClickSuccess} type="submit">
						{Liferay.Language.get('success-submit')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={onClickFail}
						type="submit"
					>
						{Liferay.Language.get('fail-submit')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
}
