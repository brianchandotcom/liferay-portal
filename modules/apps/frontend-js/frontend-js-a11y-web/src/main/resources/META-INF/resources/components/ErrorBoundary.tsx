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

import React from 'react';

type Props = {
	fallback: React.ReactElement;
	children: React.ReactElement;
};

type State = {
	hasError: boolean;
};

export class ErrorBoundary extends React.Component<Props, State> {
	public state: State = {
		hasError: false,
	};

	public static getDerivedStateFromError(_: Error): State {
		return {hasError: true};
	}

	public componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
		console.error('Uncaught error:', error, errorInfo);
	}

	public render() {
		const {children, fallback, ...otherProps} = this.props;

		if (this.state.hasError) {
			return fallback;
		}
		else {
			return React.cloneElement(children, otherProps);
		}
	}
}
