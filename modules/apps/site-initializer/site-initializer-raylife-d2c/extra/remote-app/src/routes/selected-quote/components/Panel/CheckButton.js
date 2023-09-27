/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const CheckButton = ({checked, expanded, hasError = false}) => {
	const isChecked = checked && !hasError && !expanded;

	return (
		<div className="d-flex flex-row justify-content-end panel-right">
			<div
				className={classNames(
					'align-items-center d-flex icon justify-content-center rounded-circle',
					{
						'bg-neutral-3': !isChecked,
						'bg-success': isChecked,
					}
				)}
			>
				<ClayIcon className="text-neutral-0" symbol="check" />
			</div>
		</div>
	);
};

export default CheckButton;
