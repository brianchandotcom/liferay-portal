/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
