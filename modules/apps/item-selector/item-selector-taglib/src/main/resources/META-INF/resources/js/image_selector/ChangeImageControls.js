/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

const ChangeImageControls = ({handleClickDelete, handleClickPicture}) => (
	<div className="change-image-controls">
		<ClayButtonWithIcon
			className="browse-image"
			displayType="secondary"
			monospaced
			onClick={handleClickPicture}
			symbol="picture"
			title={Liferay.Language.get('change-image')}
		/>

		<ClayButtonWithIcon
			className="ml-1"
			displayType="secondary"
			monospaced
			onClick={handleClickDelete}
			symbol="trash"
			title={Liferay.Language.get('remove-image')}
		/>
	</div>
);

ChangeImageControls.propTypes = {
	handleClickDelete: PropTypes.func.isRequired,
	handleClickPicture: PropTypes.func.isRequired,
};

export default ChangeImageControls;
