/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export const ClosableAlert = ({id, message, visible: initialVisible}) => {
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
};

ClosableAlert.propTypes = {
	id: PropTypes.string,
	message: PropTypes.string.isRequired,
	visible: PropTypes.bool.isRequired,
};
