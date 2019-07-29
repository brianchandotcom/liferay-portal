import {AppContext} from '../../AppContext';
import Filter from '../../../shared/components/filter/Filter';
import Icon from '../../../shared/components/Icon';
import LoadingState from '../../../shared/components/loading/LoadingState';
import Panel from '../../../shared/components/Panel';
import PANELS from './Panels';
import React from 'react';
import ReloadButton from '../../../shared/components/list/ReloadButton';
import SummaryCard from './SummaryCard';
import Tooltip from '../../../shared/components/Tooltip';

export default class ProcessItemsCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			error: null,
			loading: false,
			process: {}
		};
	}

	componentWillMount() {
		return this.loadData();
	}

	componentWillReceiveProps(nextProps) {
		if (nextProps.timeRange !== this.props.timeRange) {
			return this.loadData(nextProps);
		}
	}

	loadData(props = this.props) {
		return this.requestData(props)
			.then(data => {
				this.context.setTitle(data.title);

				this.setState({
					error: null,
					loading: false,
					process: data
				});
			})
			.catch(() => {
				this.setState({
					error: Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					),
					loading: false
				});
			});
	}

	requestData(props = this.props) {
		const {client} = this.context;
		const {completed = false, processId, timeRange} = props;

		let urlRequest = `/processes/${processId}?completed=${completed}`;

		if (timeRange && typeof timeRange.id === 'number') {
			urlRequest += `&timeRange=${timeRange.id}`;
		}

		this.setState({
			loading: true
		});

		return client.get(urlRequest).then(({data}) => data);
	}

	render() {
		const {error, loading, process} = this.state;
		const {
			completed,
			description,
			filter,
			processId,
			timeRange,
			title
		} = this.props;

		const errorRender = Component =>
			(error && (
				<div className="pb-6 pt-5 text-center">
					<p className="small">{error}</p>
					<ReloadButton />
				</div>
			)) ||
			Component;

		const loadingRender = Component =>
			(loading && (
				<div className="pb-6 pt-5">
					<LoadingState />
				</div>
			)) ||
			Component;

		return (
			<Panel>
				<Panel.Header
					elementClasses={[
						'dashboard-panel-header',
						filter && 'pb-0'
					]}
				>
					<div className="autofit-row">
						<div className="autofit-col autofit-col-expand flex-row">
							<span className="mr-3">{title}</span>

							<Tooltip
								message={description}
								position="right"
								width="288"
							>
								<Icon iconName={'question-circle-full'} />
							</Tooltip>
						</div>

						{filter && (
							<div className="autofit-col m-0 management-bar management-bar-light navbar">
								<ul className="navbar-nav">
									<Filter
										{...filter}
										filterKey={filter.key}
										position="right"
									/>
								</ul>
							</div>
						)}
					</div>
				</Panel.Header>

				<Panel.Body>
					{errorRender(
						loadingRender(
							<div className={'pt-1 pb-4 d-flex'}>
								{PANELS.map((panel, index) => (
									<SummaryCard
										{...panel}
										completed={completed}
										key={index}
										processId={processId}
										timeRange={timeRange}
										total={
											panel.addressedToField ===
											panel.totalField
										}
										totalValue={process[panel.totalField]}
										value={process[panel.addressedToField]}
									/>
								))}
							</div>
						)
					)}
				</Panel.Body>
			</Panel>
		);
	}
}

ProcessItemsCard.contextType = AppContext;
