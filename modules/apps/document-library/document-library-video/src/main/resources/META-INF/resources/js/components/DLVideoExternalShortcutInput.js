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
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React from 'react';

const DLVideoExternalShortcutPreview = ({labelTooltip, onChange, url = ''}) => {
	const inputName = 'dlVideoExternalShortcutURLInput';

	return (
		<>
			<label htmlFor={inputName}>
				{Liferay.Language.get('video-url')}

				{labelTooltip && (
					<ClayTooltipProvider>
						<ClayIcon
							className="ml-1 text-secondary"
							symbol="question-circle-full"
							title={labelTooltip}
						/>
					</ClayTooltipProvider>
				)}
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

DLVideoExternalShortcutPreview.propTypes = {
	labelTooltip: PropTypes.string,
	onChange: PropTypes.func,
	url: PropTypes.string,
};

export default DLVideoExternalShortcutPreview;
