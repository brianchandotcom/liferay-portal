import { AppContext } from '../../AppContext';
import autobind from 'autobind-decorator';
import Icon from '../../../shared/components/Icon';
import ListView from '../../../shared/components/list/ListView';
import { openErrorToast } from '../../../shared/util/toast';
import PaginationBar from '../../../shared/components/pagination/PaginationBar';
import Panel from '../../../shared/components/Panel';
import React from 'react';
import Tooltip from '../../../shared/components/Tooltip';
import WorkloadByStepTable from './WorkloadByStepTable';

class WorkloadByStepCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			error: null,
			items: [],
			loading: false,
			totalCount: 0
		};
	}

	componentWillReceiveProps(nextProps) {
		this.loadData(nextProps);
	}

	loadData(props = this.props) {
		const { loading } = this.state;

		if (loading) {
			return;
		}

		return this.requestData(props)
			.then(({ items, totalCount }) =>
				this.setState({
					error: null,
					items,
					totalCount
				})
			)
			.catch(() =>
				this.setState({
					error: Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					)
				})
			);
	}

	requestData({ page, pageSize, processId, sort }) {
		const { client } = this.context;

		this.setState({
			loading: true
		});

		return client
			.get(
				`/processes/${processId}/slas?page=${page}&pageSize=${pageSize}&sort=${sort}`
			)
			.then(({ data }) => {
				this.setState({
					loading: false
				});

				return data;
			})
			.catch(openErrorToast);
	}

	@autobind
	reloadPage() {
		location.reload();
	}

	render() {
		const emptyMessageText = Liferay.Language.get(
			'there-are-no-pending-items-at-the-moment'
		);
		const { error, items = [], totalCount } = this.state;
		const { loading } = this;
		const { page, pageSize } = this.props;

		const fetching = !loading && !totalCount;

		const reloadButton = (
			<button className="btn btn-link btn-sm" onClick={this.reloadPage}>
				{Liferay.Language.get('reload-page')}
			</button>
		);

		return (
			<Panel className="container-fluid-1280 mt-4">
				<Panel.Header elementClasses={'dashboard-panel-header'}>
					<div>
						<span className={'mr-3'}>
							{Liferay.Language.get('workload-by-step')}
						</span>
						<Tooltip
							message={Liferay.Language.get('workload-by-step-description')}
							position="right"
							width="288"
						>
							<Icon iconName={'question-circle-full'} />
						</Tooltip>
					</div>
				</Panel.Header>
				<Panel.Body>
					<ListView
						emptyActionButton={reloadButton}
						emptyMessageClassName="small"
						emptyMessageText={emptyMessageText}
						errorMessageText={error}
						fetching={fetching}
						loading={loading}
					>
						<WorkloadByStepTable items={items} />

						<PaginationBar
							page={page}
							pageCount={items.length}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</ListView>
				</Panel.Body>
			</Panel>
		);
	}
}

WorkloadByStepCard.contextType = AppContext;
export default WorkloadByStepCard;