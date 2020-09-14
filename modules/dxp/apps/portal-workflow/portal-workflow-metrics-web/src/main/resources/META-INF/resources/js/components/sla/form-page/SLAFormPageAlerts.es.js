/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import React, {useState} from 'react';

const AlertChange = () => {
	const [visible, setVisible] = useState(true);

	return (
		visible && (
			<ClayAlert
				data-testid="alertChange"
				displayType="danger"
				onClose={() => setVisible(false)}
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get(
					'the-time-frame-options-changed-in-the-workflow-definition'
				)}
			</ClayAlert>
		)
	);
};

const AlertMessage = ({message}) => (
	<ClayAlert displayType="danger" title={Liferay.Language.get('error')}>
		{message}
	</ClayAlert>
);

export {AlertChange, AlertMessage};
