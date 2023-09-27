/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare type Props = {
	children: React.ReactElement;
	fallback: React.ReactElement;
};
declare type State = {
	hasError: boolean;
};
export declare class ErrorBoundary extends React.Component<Props, State> {
	state: State;
	static getDerivedStateFromError(_: Error): State;
	componentDidCatch(error: Error, errorInfo: React.ErrorInfo): void;
	render(): React.ReactElement<
		any,
		string | React.JSXElementConstructor<any>
	>;
}
export {};
