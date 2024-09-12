/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

export default function CommercePaymentStatusDataRenderer(props) {
	const getLabelType = (label) => {
		let labelType = 'label-secondary';

		if (label === 'completed' || label === 'refunded') {
			labelType = 'label-success';
		}
		else if (label === 'failed') {
			labelType = 'label-danger';
		}
		else if (label === 'pending') {
			labelType = 'label-warning';
		}

		return labelType;
	};

	return props.value ? (
		<span className="taglib-workflow-status">
			<span className="workflow-status">
				<strong className={`label ${getLabelType(props.value.label)}`}>
					{props.value.label_i18n}
				</strong>
			</span>
		</span>
	) : null;
}

CommercePaymentStatusDataRenderer.propTypes = {
	value: PropTypes.shape({
		label: PropTypes.string,
		label_i18n: PropTypes.string,
	}),
};
