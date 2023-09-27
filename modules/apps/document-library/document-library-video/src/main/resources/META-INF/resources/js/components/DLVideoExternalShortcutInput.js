/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

const DLVideoExternalShortcutPreview = ({labelTooltip, onChange, url = ''}) => {
	const inputName = 'dlVideoExternalShortcutURLInput';

	return (
		<>
			<label htmlFor={inputName}>
				{Liferay.Language.get('video-url')}

				{labelTooltip && (
					<ClayIcon
						className="ml-1 text-secondary"
						symbol="question-circle-full"
						title={labelTooltip}
					/>
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
