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

// @ts-ignore

import classNames from 'classnames';
import React from 'react';

const ErrorList: React.FC<IProps> = ({
	errorMessages = [],
	onRemove,
	sidebarOpen,
}) => {
	const handleRemove = (index: number) => () => onRemove?.(index);

	return errorMessages.length ? (
		<div className="container-fluid container-fluid-max-xl">
			<div
				className={classNames('ddm-form-web__exception-container', {
					'ddm-form-web__exception-container--sidebar-open': sidebarOpen,
				})}
			>
				{errorMessages.map((errorMsg, index) => (
					<ClayAlert
						className="alert-dismissible"
						displayType="danger"
						key={index}
						onClose={onRemove && handleRemove(index)}
						title={`${Liferay.Language.get('error')}:`}
					>
						{errorMsg}
					</ClayAlert>
				))}
			</div>
		</div>
	) : null;
};

export default ErrorList;

interface IProps {
	errorMessages?: string[];
	onRemove?: (index: number) => void;
	sidebarOpen?: boolean;
}
