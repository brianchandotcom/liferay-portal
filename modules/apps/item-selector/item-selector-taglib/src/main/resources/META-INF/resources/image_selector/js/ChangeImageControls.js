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

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

const ChangeImageControls = ({handleClickDelete, handleClickPicture}) => (
	<div className="change-image-controls">
		<ClayButtonWithIcon
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

export default ChangeImageControls;