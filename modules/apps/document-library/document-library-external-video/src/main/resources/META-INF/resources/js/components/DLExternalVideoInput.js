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

import {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

const DLExternalVideoPreview = ({onChange, url}) => {
	const inputName = 'externalVideoURLInput';

	return (
		<>
			<label htmlFor={inputName}>
				{Liferay.Language.get('video-url')}
			</label>
			<ClayInput
				id={inputName}
				onChange={(event) => onChange(event.target.value.trim())}
				placeholder="http://"
				type="text"
				value={url}
			/>
			<p className="form-text">
				{Liferay.Language.get('video-url-help')}
			</p>
		</>
	);
};

DLExternalVideoPreview.propTypes = {
	onChange: PropTypes.func,
	url: PropTypes.string,
};

export default DLExternalVideoPreview;
