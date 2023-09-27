/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React from 'react';

export function LoadingComponent({className}) {
	return (
		<div
			className={classNames(
				'align-items-center',
				'd-flex',
				'w-100',
				className
			)}
		>
			<ClayLoadingIndicator />
		</div>
	);
}

export function withLoading(Component) {
	const Wrapper = (props) => {
		const {className, isLoading, ...restProps} = props;

		if (isLoading) {
			return <LoadingComponent className={className} />;
		}

		return <Component {...restProps} />;
	};

	return Wrapper;
}

export default withLoading(({children}) => <>{children}</>);
