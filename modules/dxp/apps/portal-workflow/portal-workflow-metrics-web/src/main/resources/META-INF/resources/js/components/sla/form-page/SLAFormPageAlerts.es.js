/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React, {useState} from 'react';

function AlertChange() {
	const [visible, setVisible] = useState(true);

	return (
		visible && (
			<ClayAlert
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
}

function AlertMessage({message}) {
	return (
		<ClayAlert displayType="danger" title={Liferay.Language.get('error')}>
			{message}
		</ClayAlert>
	);
}

export {AlertChange, AlertMessage};
