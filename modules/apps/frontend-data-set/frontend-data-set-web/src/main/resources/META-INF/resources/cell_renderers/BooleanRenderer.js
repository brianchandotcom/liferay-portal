/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

function BooleanRenderer(props) {
	if (typeof props.value !== 'boolean') {
		return null;
	}

	return props.value
		? props.options?.trueLabel || Liferay.Language.get('yes')
		: props.options?.falseLabel || Liferay.Language.get('no');
}

BooleanRenderer.propTypes = {
	options: PropTypes.shape({
		falseLabel: PropTypes.string,
		trueLabel: PropTypes.string,
	}),
	value: PropTypes.bool,
};

export default BooleanRenderer;
