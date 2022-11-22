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

import {EMPTY_STATE_GIF, NOT_FOUND_GIF} from '../../utils/constants';
import StateRenderer, {
	EmptyStateComponent,
	ErrorStateComponent,
} from '../StateRenderer';
import {useData} from './Context';

interface ITableStateRendererProps extends React.HTMLAttributes<HTMLElement> {
	empty: boolean;
	emptyStateTitle: string;
	error: boolean;
	loading: boolean;
	noResultsTitle: string;
	refetch: () => void;
}

const TableStateRenderer: React.FC<ITableStateRendererProps> = ({
	children,
	empty,
	emptyStateTitle,
	error,
	loading,
	noResultsTitle,
	refetch,
}) => {
	const {keywords} = useData();

	return (
		<StateRenderer
			empty={empty}
			error={error}
			loading={loading}
			loadingProps={{
				absolute: true,
				style: {display: 'block', minHeight: 300},
			}}
		>
			{!keywords && (
				<StateRenderer.Empty>
					<EmptyStateComponent
						className="empty-state-border"
						description=""
						imgSrc={EMPTY_STATE_GIF}
						title={emptyStateTitle}
					/>
				</StateRenderer.Empty>
			)}

			{keywords && (
				<StateRenderer.Empty>
					<EmptyStateComponent
						className="empty-state-border"
						description=""
						imgSrc={NOT_FOUND_GIF}
						title={noResultsTitle}
					/>
				</StateRenderer.Empty>
			)}

			<StateRenderer.Error>
				<ErrorStateComponent
					className="empty-state-border mb-0 pb-5"
					onClickRefetch={refetch}
				/>
			</StateRenderer.Error>

			<StateRenderer.Success>{children}</StateRenderer.Success>
		</StateRenderer>
	);
};

export default TableStateRenderer;
