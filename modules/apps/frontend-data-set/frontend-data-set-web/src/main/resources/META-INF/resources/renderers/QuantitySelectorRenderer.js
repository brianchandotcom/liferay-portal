/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropType from 'prop-types';
import React from 'react';

import DefaultSelector from '../quantity_selector/QuantitySelector';

function QuantitySelectorRenderer({options, value}) {
	return (
		<div className="row">
			<div className="col-auto">
				<DefaultSelector
					size="small"
					style="simple"
					{...options}
					{...value}
				/>
			</div>
		</div>
	);
}

QuantitySelectorRenderer.propTypes = {
	value: PropType.object,
};

export default QuantitySelectorRenderer;
