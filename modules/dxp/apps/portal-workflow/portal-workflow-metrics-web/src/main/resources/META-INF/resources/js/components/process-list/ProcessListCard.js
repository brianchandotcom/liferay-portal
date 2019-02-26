import {
	REQUEST_ORIGIN_TYPE_FETCH,
	REQUEST_ORIGIN_TYPE_SEARCH
} from './Constants';
import autobind from 'autobind-decorator';
import DisplayResult from '../../shared/components/pagination/DisplayResult';
import EmptyContent from '../../shared/components/EmptyContent';
import Entries from '../../shared/components/pagination/Entries';
import Pagination from '../../shared/components/pagination/Pagination';
import ProcessListTable from './ProcessListTable';
import React from 'react';
import Search from '../../shared/components/pagination/Search';

/**
 * @class
 * @memberof processes-list
 * */
export default class ProcessListCard extends React.Component {
	constructor() {
		super();

		this.state = {
			processes: [],
			requestOriginType: null,
			selectedEntry: 20,
			start: 1,
			total: 0
		};
	}

	componentWillMount() {
		const {selectedEntry, start} = this.state;

		this.requestData({size: selectedEntry, start}).then(
			({processes, total}) =>
				this.setState({
					processes,
					total
				})
		);
	}

	/**
	 * @param {Object} configuration
	 * @param {string} configuration.keyword
	 * @param {number} configuration.size
	 * @param {number} configuration.start
	 */
	requestData({keyword, size, start}) {
		const {client} = this.props;

		this.state.requestOriginType =
			typeof keyword === 'string'
				? REQUEST_ORIGIN_TYPE_SEARCH
				: REQUEST_ORIGIN_TYPE_FETCH;

		return client(`/processes?page=${start}&pageSize=${size}`).then(
			({items: processes, totalCount: total}) => ({
				processes,
				total
			})
		);
	}

	@autobind
	onSearch(keyword) {
		const {selectedEntry} = this.state;
		const start = 1;

		this.requestData({keyword, size: selectedEntry, start}).then(
			({processes, total}) => this.setState({processes, start, total})
		);
	}

	@autobind
	setPage({size, start}) {
		return this.requestData({size, start}).then(({processes, total}) =>
			this.setState({processes, start, total})
		);
	}

	@autobind
	setEntry(entry) {
		const start = 1;

		this.requestData({size: entry, start}).then(({processes, total}) =>
			this.setState({processes, selectedEntry: entry, start, total})
		);
	}

	render() {
		const {
			processes,
			requestOriginType,
			selectedEntry,
			start,
			total
		} = this.state;
		const entries = [5, 10, 20, 30, 50, 75];

		const emptySearchRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_SEARCH && total === 0 ? (
				<EmptyContent
					message={Liferay.Language.get('no-results-were-found')}
					type="not-found"
				/>
			) : (
				secondaryRender
			);

		const emptyContentRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && total === 0 ? (
				<EmptyContent
					message={Liferay.Language.get(
						'once-there-are-active-processes-metrics-will-appear-here'
					)}
					title={Liferay.Language.get('no-current-metrics')}
				/>
			) : (
				secondaryRender
			);

		const listRender = secondaryRender =>
			total > 0 ? (
				<div>
					<ProcessListTable processes={processes} />
					{total > entries[0] && (
						<div className="pagination-bar">
							<Entries
								entries={entries}
								onSelectEntry={this.setEntry}
								selectedEntry={selectedEntry}
							/>

							<DisplayResult
								count={processes.length}
								start={start}
								total={total}
							/>

							<Pagination
								entry={selectedEntry}
								pageClickHandler={this.setPage}
								start={start}
								totalCount={total}
							/>
						</div>
					)}
				</div>
			) : (
				secondaryRender
			);

		const loadingRender = () => (
			<span aria-hidden="true" className="loading-animation" />
		);

		return (
			<div>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<div className="navbar-form navbar-form-autofit">
							<Search
								disabled={
									requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && total === 0
								}
								onSearch={this.onSearch}
							/>
						</div>
					</div>
				</nav>
				<div className="container-fluid-1280">
					{emptySearchRender(emptyContentRender(listRender(loadingRender())))}
				</div>
			</div>
		);
	}
}