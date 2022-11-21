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

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import React, {createContext, useContext} from 'react';

import {ERROR_MESSAGE} from '../utils/constants';
import Loading from './Loading';

const Context = createContext<IStateRendererProps>({
	empty: false,
	error: false,
	loading: false,
});

interface IStateRendererProps extends React.HTMLAttributes<HTMLElement> {
	empty: boolean;
	error: boolean;
	loading: boolean;
	loadingProps?: any;
}

const StateRenderer: React.FC<IStateRendererProps> & {
	Empty: typeof EmptyState;
	Error: typeof ErrorState;
	Success: typeof SuccessState;
} = ({children, empty, error, loading, loadingProps}) => {
	if (loading) {
		return <Loading {...loadingProps} />;
	}

	return (
		<Context.Provider value={{empty, error, loading}}>
			{children}
		</Context.Provider>
	);
};

const EmptyState: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
}) => {
	const {empty, error, loading} = useContext(Context);

	return !error && !loading && empty ? <>{children}</> : null;
};

const ErrorState: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
}) => {
	const {error, loading} = useContext(Context);

	return !loading && error ? <>{children}</> : null;
};

const SuccessState: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
}) => {
	const {empty, error, loading} = useContext(Context);

	return !error && !loading && !empty ? <>{children}</> : null;
};

interface IErrorStateComponentProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	onClickRefetch?: () => void;
}

export function ErrorStateComponent({
	className,
	disabled,
	onClickRefetch,
}: IErrorStateComponentProps) {
	return (
		<div className={className}>
			<ClayEmptyState
				className="text-center"
				description=""
				title={ERROR_MESSAGE}
			>
				{onClickRefetch && (
					<ClayButton
						disabled={disabled}
						displayType="secondary"
						onClick={onClickRefetch}
					>
						{Liferay.Language.get('try-again')}
					</ClayButton>
				)}
			</ClayEmptyState>
		</div>
	);
}

interface IEmptyStateComponentProps extends React.HTMLAttributes<HTMLElement> {
	description?: string;
	imgSrc: string;
	title?: string;
}

export function EmptyStateComponent({
	children,
	className,
	description,
	imgSrc,
	title,
}: IEmptyStateComponentProps) {
	return (
		<div className={className}>
			<ClayEmptyState
				description={description}
				imgProps={{
					alt: title,
					title,
				}}
				imgSrc={imgSrc}
				title={title}
			>
				{children}
			</ClayEmptyState>
		</div>
	);
}

StateRenderer.Empty = EmptyState;
StateRenderer.Error = ErrorState;
StateRenderer.Success = SuccessState;

export default StateRenderer;
