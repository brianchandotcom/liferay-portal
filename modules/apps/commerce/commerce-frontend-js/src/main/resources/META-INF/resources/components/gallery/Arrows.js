/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

export default function Arrows({onNext, onPrev}) {
	return (
		<>
			{onPrev && <div className="arrow prev" onClick={onPrev} />}
			{onNext && <div className="arrow next" onClick={onNext} />}
		</>
	);
}

Arrows.propTypes = {
	onNext: PropTypes.func,
	onPrev: PropTypes.func,
};
