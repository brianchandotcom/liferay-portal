/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

export default function CommerceReturnItemStatusDataRenderer(props) {
	const getLabelType = (label) => {
		if (
			label === 'authorized' ||
			label === 'completed' ||
			label === 'defined' ||
			label === 'partiallyAuthorized' ||
			label === 'partiallyReceived' ||
			label === 'processed' ||
			label === 'received'
		) {
			return 'label-success';
		}
		if (label === 'notAuthorized' || label === 'receiptRejected') {
			return 'label-danger';
		}

		return 'label-secondary';
	};

	return props.value ? (
		<span className="taglib-workflow-status">
			<span className="workflow-status">
				<strong className={`label ${getLabelType(props.value.key)}`}>
					{props.value.name}
				</strong>
			</span>
		</span>
	) : null;
}

CommerceReturnItemStatusDataRenderer.propTypes = {
	value: PropTypes.shape({
		key: PropTypes.string,
		name: PropTypes.string,
	}),
};
