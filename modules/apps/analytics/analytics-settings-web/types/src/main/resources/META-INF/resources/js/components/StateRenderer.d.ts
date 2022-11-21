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
interface IStateRendererProps extends React.HTMLAttributes<HTMLElement> {
	empty: boolean;
	error: boolean;
	loading: boolean;
	loadingProps?: any;
}
declare const StateRenderer: React.FC<IStateRendererProps> & {
	Empty: typeof EmptyState;
	Error: typeof ErrorState;
	Success: typeof SuccessState;
};
declare const EmptyState: React.FC<React.HTMLAttributes<HTMLElement>>;
declare const ErrorState: React.FC<React.HTMLAttributes<HTMLElement>>;
declare const SuccessState: React.FC<React.HTMLAttributes<HTMLElement>>;
interface IErrorStateComponentProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	onClickRefetch?: () => void;
}
export declare function ErrorStateComponent({
	className,
	disabled,
	onClickRefetch,
}: IErrorStateComponentProps): JSX.Element;
interface IEmptyStateComponentProps extends React.HTMLAttributes<HTMLElement> {
	description?: string;
	imgSrc: string;
	title?: string;
}
export declare function EmptyStateComponent({
	children,
	className,
	description,
	imgSrc,
	title,
}: IEmptyStateComponentProps): JSX.Element;
export default StateRenderer;
