import { AppContext } from '../../AppContext';
import autobind from 'autobind-decorator';
import InstanceListTable from './InstanceListTable';
import ListView from '../../../shared/components/list/ListView';
import PaginationBar from '../../../shared/components/pagination/PaginationBar';
import React from 'react';
import ReloadButton from '../../../shared/components/list/ReloadButton';

class InstanceListCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			error: null,
			items: [],
			loading: false,
			totalCount: 0
		};
	}

	componentWillMount() {
		this.loadProcess();
	}

	componentWillReceiveProps(nextProps) {
		this.loadInstances(nextProps);
	}

	@autobind
	handleRequestError() {
		this.setState({
			error: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
			loading: false
		});
	}

	loadInstances({ page, pageSize, processId } = this.props) {
		const { loading } = this.state;

		if (loading) {
			return;
		}

		return this.requestData(
			`/processes/${processId}/instances?page=${page}&pageSize=${pageSize}`
		)
			.then(({ items, totalCount }) => {
				this.setState({
					items,
					totalCount
				});
			})
			.catch(this.handleRequestError);
	}

	loadProcess({ processId } = this.props) {
		const { loading } = this.state;

		if (loading) {
			return;
		}

		return this.requestData(`/processes/${processId}/title`)
			.then(data => {
				this.context.setTitle(
					`${data}: ${Liferay.Language.get('all-items')}`
				);
			})
			.catch(this.handleRequestError);
	}

	requestData(endpoint) {
		const { client } = this.context;

		this.setState({
			loading: true
		});

		return client.get(endpoint).then(({ data }) => {
			this.setState({
				error: null,
				loading: false
			});

			return data;
		});
	}

	render() {
		const { error, items = [], loading, totalCount } = this.state;
		const { page, pageSize } = this.props;

		const fetching = !loading && !totalCount;

		return (
			<div className="container-fluid-1280 mt-4">
				<ListView
					emptyActionButton={<ReloadButton />}
					emptyMessageText={Liferay.Language.get(
						'there-are-no-process-items-at-the-moment'
					)}
					errorMessageText={error}
					fetching={fetching}
					loading={loading}
				>
					<InstanceListTable items={items} />

					<PaginationBar
						page={page}
						pageCount={items.length}
						pageSize={pageSize}
						totalCount={totalCount}
					/>
				</ListView>
			</div>
		);
	}
}

InstanceListCard.contextType = AppContext;
export default InstanceListCard;