/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export function ClosableAlert({id, message, visible: initialVisible}) {
	const [visible, setVisible] = useState(!!initialVisible);

	return (
		visible && (
			<ClayAlert
				className="mb-3 mx-3"
				displayType="warning"
				id={id}
				onClose={() => setVisible(false)}
				title={Liferay.Language.get('warning')}
			>
				{message}
			</ClayAlert>
		)
	);
}

ClosableAlert.propTypes = {
	id: PropTypes.string,
	message: PropTypes.string.isRequired,
	visible: PropTypes.bool.isRequired,
};
