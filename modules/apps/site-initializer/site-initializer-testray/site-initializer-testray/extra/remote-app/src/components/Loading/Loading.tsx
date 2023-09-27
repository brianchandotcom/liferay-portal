/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {ReactNode} from 'react';

type LoadingProps = {
	className?: string;
};

type LoadingWrapperProps = {
	children: ReactNode;
	isLoading: boolean;
} & LoadingProps;

const Loading: React.FC<LoadingProps> = ({className}) => (
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

const LoadingWrapper: React.FC<LoadingWrapperProps> = ({
	children,
	isLoading,
}) => {
	if (isLoading) {
		return <Loading />;
	}

	return <>{children}</>;
};

export {LoadingWrapper};

export default Loading;
