/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

type Props = {
	children: React.ReactElement;
	fallback: React.ReactElement;
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
