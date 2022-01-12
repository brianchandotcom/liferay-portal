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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const CheckButton = ({checked, expanded, hasError = false}) => {
	const isChecked = checked && !hasError && !expanded;

	return (
		<div className="panel-right">
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
