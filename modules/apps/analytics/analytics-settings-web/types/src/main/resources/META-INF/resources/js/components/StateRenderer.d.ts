/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
