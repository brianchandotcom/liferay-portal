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
declare type Props = {
	fallback: React.ReactElement;
	children: React.ReactElement;
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
