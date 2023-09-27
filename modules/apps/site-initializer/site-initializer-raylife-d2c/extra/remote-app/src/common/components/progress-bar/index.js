/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ProgressBar = ({progress}) => {
	return (
		<div className="progress-bar" style={{height: 4, width: 144}}>
			<div
				className="progress-bar__progress"
				style={{
					width: `${progress}%`,
				}}
			/>
		</div>
	);
};

export default ProgressBar;
