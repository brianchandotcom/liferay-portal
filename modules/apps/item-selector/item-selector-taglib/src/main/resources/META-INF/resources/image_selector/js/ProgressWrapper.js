/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayProgressBar from '@clayui/progress-bar';
import PropTypes from 'prop-types';
import React from 'react';

const ProgressWrapper = ({fileName, onCancel, progressData, progressValue}) => (
	<div className="progress-wrapper">
		<p className="file-name">{fileName}</p>

		<ClayProgressBar className="progressbar" value={progressValue} />

		<p
			className="progress-data size"
			dangerouslySetInnerHTML={{
				__html: progressData,
			}}
		/>

		<ClayButton displayType="primary" onClick={onCancel}>
			{Liferay.Language.get('cancel')}
		</ClayButton>
	</div>
);

ProgressWrapper.propTypes = {
	fileName: PropTypes.string,
	onCancel: PropTypes.func.isRequired,
};

export default ProgressWrapper;
