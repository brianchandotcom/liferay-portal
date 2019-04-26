import { AppContext } from '../../AppContext';
import Icon from '../../../shared/components/Icon';
import LoadingState from '../../../shared/components/loading/LoadingState';
import Panel from '../../../shared/components/Panel';
import PANELS from './Panels';
import React from 'react';
import ReloadButton from '../../../shared/components/list/ReloadButton';
import SummaryCard from './SummaryCard';
import Tooltip from '../../../shared/components/Tooltip';

class ProcessItemsCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			error: null,
			loading: false,
			process: {}
		};
	}

	componentWillMount() {
		return this.requestData()
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

	requestData() {
		const { client } = this.context;
		const { completed = false, processId } = this.props;

		const urlRequest = `/processes/${processId}?completed=${completed}`;

		this.setState({
			loading: true
		});

		return client.get(urlRequest).then(({ data }) => data);
	}

	render() {
		const { error, loading, process } = this.state;
		const { panelDescription, panelTitle, processId } = this.props;

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
				<Panel.Header elementClasses={'dashboard-panel-header'}>
					<div>
						<span className={'mr-3'}>{panelTitle}</span>

						<Tooltip message={panelDescription} position="right" width="288">
							<Icon iconName={'question-circle-full'} />
						</Tooltip>
					</div>
				</Panel.Header>

				<Panel.Body>
					{errorRender(
						loadingRender(
							<div className={'pt-1 pb-4 d-flex'}>
								{PANELS.map((panel, index) => (
									<SummaryCard
										{...panel}
										key={index}
										processId={processId}
										total={panel.addressedToField === panel.totalField}
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

export class CompletedItemsCard extends React.Component {
	render() {
		const { processId } = this.props;

		return (
			<ProcessItemsCard
				completed
				panelDescription={Liferay.Language.get('completed-items-description')}
				panelTitle={Liferay.Language.get('completed-items')}
				processId={processId}
			/>
		);
	}
}

export class PendingItemsCard extends React.Component {
	render() {
		const { processId } = this.props;

		return (
			<ProcessItemsCard
				panelDescription={Liferay.Language.get('pending-items-description')}
				panelTitle={Liferay.Language.get('pending-items')}
				processId={processId}
			/>
		);
	}
}