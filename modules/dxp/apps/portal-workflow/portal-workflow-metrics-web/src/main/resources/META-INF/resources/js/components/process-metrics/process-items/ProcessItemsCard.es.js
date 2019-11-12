/* eslint-disable react-hooks/exhaustive-deps */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useEffect, useState} from 'react';

import Icon from '../../../shared/components/Icon.es';
import Panel from '../../../shared/components/Panel.es';
import {ErrorContext} from '../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../shared/components/request/Loading.es';
import Request from '../../../shared/components/request/Request.es';
import {AppContext} from '../../AppContext.es';
import PANELS from './Panels.es';
import SummaryCard from './SummaryCard.es';

function ProcessItemsCard({
	children,
	completed,
	description,
	processId,
	timeRange,
	title
}) {
	return (
		<Request>
			<Panel>
				<ProcessItemsCard.Header
					description={description}
					title={title}
				>
					{children}
				</ProcessItemsCard.Header>

				<ProcessItemsCard.Body
					completed={completed}
					processId={processId}
					timeRange={timeRange}
				/>
			</Panel>
		</Request>
	);
}

const Body = ({completed = false, processId, timeRange}) => {
	const {client, setTitle} = useContext(AppContext);
	const {dateEnd, dateStart} = timeRange || {};
	const [process, setProcess] = useState(null);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		const isValidDate = date => date && !isNaN(date);

		let urlRequest = `/processes/${processId}?completed=${completed}`;

		if (isValidDate(dateEnd) && isValidDate(dateStart)) {
			urlRequest += `&dateEnd=${dateEnd.toISOString()}&dateStart=${dateStart.toISOString()}`;
		}

		return client
			.get(urlRequest)
			.then(({data}) => {
				setTitle(data.title);
				setProcess(data);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	useEffect(() => {
		fetchData();
	}, [dateStart, dateEnd]);

	return (
		<Panel.Body>
			<Request.Error />

			<Request.Loading />

			<Request.Success>
				{process && (
					<div className={'d-flex pb-4 pt-1'}>
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
								totalValue={process[panel.totalField]}
								value={process[panel.addressedToField]}
							/>
						))}
					</div>
				)}
			</Request.Success>
		</Panel.Body>
	);
};

const Header = ({children, description, title}) => (
	<Panel.Header
		elementClasses={['dashboard-panel-header', children && 'pb-0']}
	>
		<div className="autofit-row">
			<div className="autofit-col autofit-col-expand flex-row">
				<span className="mr-2">{title}</span>

				<ClayTooltipProvider>
					<span>
						<span
							className="workflow-tooltip"
							data-tooltip-align={'right'}
							title={description}
						>
							<Icon iconName={'question-circle-full'} />
						</span>
					</span>
				</ClayTooltipProvider>
			</div>

			{children && (
				<Request.Success>
					<div className="autofit-col m-0 management-bar management-bar-light navbar">
						<ul className="navbar-nav">{children}</ul>
					</div>
				</Request.Success>
			)}
		</div>
	</Panel.Header>
);

ProcessItemsCard.Body = Body;
ProcessItemsCard.Header = Header;

export default ProcessItemsCard;
