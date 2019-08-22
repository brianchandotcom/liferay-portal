import React, {useContext, useEffect, useState} from 'react';
import {AppContext} from '../../AppContext';
import {ErrorContext} from '../../../shared/components/request/Error';
import Icon from '../../../shared/components/Icon';
import {LoadingContext} from '../../../shared/components/request/Loading';
import Panel from '../../../shared/components/Panel';
import PANELS from './Panels';
import Request from '../../../shared/components/request/Request';
import SummaryCard from './SummaryCard';
import Tooltip from '../../../shared/components/Tooltip';

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
					children={children}
					description={description}
					title={title}
				/>

				<ProcessItemsCard.Body
					completed={completed}
					processId={processId}
					timeRange={timeRange}
				/>
			</Panel>
		</Request>
	);
}

ProcessItemsCard.Body = ({completed = false, processId, timeRange}) => {
	const {client, setTitle} = useContext(AppContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const [process, setProcess] = useState(null);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		let urlRequest = `/processes/${processId}?completed=${completed}`;

		if (timeRange && timeRange.dateEnd && timeRange.dateStart) {
			const {dateEnd, dateStart} = timeRange;

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
	}, [timeRange]);

	return (
		<Panel.Body>
			<Request.Error />

			<Request.Loading />

			<Request.Success>
				{process && (
					<div className={'pt-1 pb-4 d-flex'}>
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

ProcessItemsCard.Header = ({children, description, title}) => (
	<Panel.Header
		elementClasses={['dashboard-panel-header', children && 'pb-0']}
	>
		<div className="autofit-row">
			<div className="autofit-col autofit-col-expand flex-row">
				<span className="mr-2">{title}</span>

				<Tooltip message={description} position="right" width="288">
					<Icon iconName={'question-circle-full'} />
				</Tooltip>
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

export default ProcessItemsCard;
