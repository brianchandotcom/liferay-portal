/* eslint-disable react-hooks/exhaustive-deps */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React, {useMemo} from 'react';

import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import PANELS from './Panels.es';
import SummaryCard from './SummaryCard.es';

const ProcessItemsCard = ({
	children,
	completed,
	description,
	processId,
	timeRange = {},
	title,
}) => {
	const {data, fetchData} = useFetch({
		params: {
			completed,
			...timeRange,
		},
		url: `/processes/${processId}/metrics`,
	});

	const promises = useMemo(() => {
		if (!completed || (timeRange.dateEnd && timeRange.dateStart)) {
			return [fetchData()];
		}

		return [new Promise((_, reject) => reject())];
	}, [completed, timeRange.dateEnd, timeRange.dateStart]);

	return (
		<PromisesResolver promises={promises}>
			<ClayPanel className="mt-4">
				<ProcessItemsCard.Header
					data={data}
					description={description}
					title={title}
				>
					{children}
				</ProcessItemsCard.Header>

				<ProcessItemsCard.Body
					completed={completed}
					data={data}
					processId={processId}
					timeRange={timeRange}
				/>
			</ClayPanel>
		</PromisesResolver>
	);
};

const Body = ({completed = false, data, processId, timeRange}) => {
	const statesProps = {
		errorProps: {
			actionButton: <ReloadButton />,
			className: 'mt-2 pb-5 pt-4',
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
			messageClassName: 'small',
		},
		loadingProps: {className: 'mt-2 pb-5 pt-4'},
	};

	return (
		<ClayPanel.Body>
			<ContentView {...statesProps}>
				{data ? (
					<div className="d-flex pb-3">
						{PANELS.map((panel, index) => (
							<SummaryCard
								{...panel}
								completed={completed}
								key={index}
								processId={processId}
								timeRange={timeRange}
								total={
									panel.addressedToField === panel.totalField
								}
								totalValue={data[panel.totalField]}
								value={data[panel.addressedToField]}
							/>
						))}
					</div>
				) : (
					<></>
				)}
			</ContentView>
		</ClayPanel.Body>
	);
};

const Header = ({children, data, description, title}) => (
	<ClayPanel.Header className={['tabs-panel-header', children && 'pb-0']}>
		<ClayLayout.ContentRow>
			<ClayLayout.ContentCol className="flex-row" expand>
				<span className="mr-2">{title}</span>

				<span
					className="workflow-tooltip"
					data-tooltip-align="right"
					title={description}
				>
					<ClayIcon symbol="question-circle-full" />
				</span>
			</ClayLayout.ContentCol>

			{children && data && (
				<ClayLayout.ContentCol className="m-0 management-bar management-bar-light navbar">
					<ClayList className="navbar-nav">{children}</ClayList>
				</ClayLayout.ContentCol>
			)}
		</ClayLayout.ContentRow>
	</ClayPanel.Header>
);

ProcessItemsCard.Body = Body;
ProcessItemsCard.Header = Header;

export default ProcessItemsCard;
