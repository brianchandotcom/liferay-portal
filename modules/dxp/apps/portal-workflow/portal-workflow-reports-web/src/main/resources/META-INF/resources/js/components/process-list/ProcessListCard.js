import autobind from 'autobind-decorator';
import EmptyContent from '../../libs/EmptyContent';
import gql from 'graphql-tag';
import graphqlClient from '../../libs/graphql-client';
import ProcessListEntries from './ProcessListEntries';
import ProcessListPagination from './ProcessListPagination';
import ProcessListPaginationResults from './ProcessListPaginationResults';
import ProcessListTable from './ProcessListTable';
import React from 'react';

export default class ProcessListCard extends React.Component {
	constructor() {
		super();

		this.state = {
			processes: [],
			selectedEntry: 20,
			start: 0,
			total: 0
		};
	}

	componentWillMount() {
		const {selectedEntry, start} = this.state;

		const unsubscribe = graphqlClient.onResetStore(() => {
			this.requestData({size: selectedEntry, start}).then(
				({processes, total}) => this.setState({processes, total})
			);

			unsubscribe();
		});

		graphqlClient.resetStore();
	}

	requestData({start, size}) {
		const {companyId} = this.props;

		return graphqlClient
			.query({
				query: gql`
				query workflowProcessesQuery {
					processes(companyId:${companyId}, start:${start}, size:${size}) {
						total
						workflowProcesses {
	  						title
	  						instancesCount
						}
					}
				}
			`
			})
			.then(({data: {processes}}) => ({
				processes: processes.workflowProcesses,
				total: processes.total
			}))
			.catch(error => console.error(error));
	}

	@autobind
	setPage({size, start}) {
		this.requestData({size, start}).then(({processes, total}) =>
			this.setState({processes, start, total})
		);
	}

	@autobind
	setEntry(entry) {
		const start = 0;

		this.requestData({size: entry, start}).then(({processes, total}) =>
			this.setState({processes, selectedEntry: entry, start, total})
		);
	}

	render() {
		const {selectedEntry, processes, start, total} = this.state;
		const entries = [5, 10, 20, 30, 50, 75];

		const paginationBar = () => {
			if (total > entries[0]) {
				return (
					<div className="pagination-bar">
						<ProcessListEntries
							entries={entries}
							onSelectEntry={this.setEntry}
							selectedEntry={selectedEntry}
						/>

						<ProcessListPaginationResults
							count={processes.length}
							start={start}
							total={total}
						/>

						<ProcessListPagination
							entry={selectedEntry}
							pageClickHandler={this.setPage}
							totalCount={total}
						/>
					</div>
				);
			}
			return <div />;
		};

		return (
			<div>
				{total > 0 ? (
					<ProcessListTable processes={processes} />
				) : (
					<EmptyContent message="There are no process." />
				)}

				{paginationBar()}
			</div>
		);
	}
}