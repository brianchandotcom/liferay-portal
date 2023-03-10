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
