import {
	REQUEST_ORIGIN_TYPE_FETCH,
	REQUEST_ORIGIN_TYPE_SEARCH
} from './Constants';
import { AppContext } from '../AppContext';
import autobind from 'autobind-decorator';
import DisplayResult from '../../shared/components/pagination/DisplayResult';
import ListView from '../../shared/components/list/ListView';
import PageSizeEntries from '../../shared/components/pagination/PageSizeEntries';
import Pagination from '../../shared/components/pagination/Pagination';
import ProcessListTable from './ProcessListTable';
import React from 'react';
import Search from '../../shared/components/pagination/Search';

/**
 * @class
 * @memberof processes-list
 */
class ProcessListCard extends React.Component {
	constructor(props) {
		super(props);

		const { page = 1, pageSize = 20 } = this.props;

		this.requestOriginType = null;

		this.state = {
			items: [],
			page,
			pageSize,
			totalCount: 0
		};
	}

	componentDidMount() {
		const { page, pageSize } = this.state;

		this.requestData({ page, pageSize }).then(({ items, totalCount }) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	componentWillMount() {
		this.context.setTitle(Liferay.Language.get('metrics'));
	}

	/**
	 * @param {Object} configuration
	 * @param {number} configuration.page
	 * @param {number} configuration.pageSize
	 * @param {string} configuration.title
	 */
	requestData({ page, pageSize, title }) {
		const { client } = this.context;
		const isSearch = typeof title === 'string';
		let urlRequest = `/processes?page=${page}&pageSize=${pageSize}`;

		if (isSearch) {
			urlRequest += `&title=${encodeURIComponent(title)}`;
		}

		this.requestOriginType = isSearch
			? REQUEST_ORIGIN_TYPE_SEARCH
			: REQUEST_ORIGIN_TYPE_FETCH;

		return client.get(urlRequest).then(({ data }) => data);
	}

	@autobind
	onSearch(title) {
		const { pageSize } = this.state;
		const page = 1;

		return this.requestData({ page, pageSize, title }).then(
			({ items, totalCount }) => this.setState({ items, page, totalCount })
		);
	}

	@autobind
	setPage(page) {
		const { pageSize } = this.state;

		return this.requestData({ page, pageSize }).then(({ items, totalCount }) =>
			this.setState({ items, page, totalCount })
		);
	}

	@autobind
	setPageSize(pageSize) {
		const page = 1;

		return this.requestData({ page, pageSize }).then(({ items, totalCount }) =>
			this.setState({ items, page, pageSize, totalCount })
		);
	}

	render() {
		const { requestOriginType } = this;
		const { items, page, pageSize, totalCount } = this.state;

		const emptyTitleText = Liferay.Language.get('no-current-metrics');
		const isFetching =
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0;
		const isLoading = !requestOriginType && totalCount === 0;
		const isSearching =
			requestOriginType === REQUEST_ORIGIN_TYPE_SEARCH && totalCount === 0;

		const emptyMessageText = isSearching
			? Liferay.Language.get('no-results-were-found')
			: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
			  );

		const pageSizes = [5, 10, 20, 30, 50, 75];

		return (
			<div>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<div className="navbar-form navbar-form-autofit">
							<Search disabled={isFetching} onSearch={this.onSearch} />
						</div>
					</div>
				</nav>

				<div className="lfr-search-container-wrapper container-fluid-1280">
					<ListView
						emptyMessageText={emptyMessageText}
						emptyTitleText={emptyTitleText}
						isFetching={isFetching}
						isLoading={isLoading}
						isSearching={isSearching}
					>
						<ProcessListTable items={items} />

						{totalCount > pageSizes[0] && (
							<div className="pagination-bar">
								<PageSizeEntries
									onSelectPageSize={this.setPageSize}
									pageSizeEntries={pageSizes}
									selectedPageSize={pageSize}
								/>

								<DisplayResult
									page={page}
									pageCount={items.length}
									pageSize={pageSize}
									totalCount={totalCount}
								/>

								<Pagination
									onSelectPage={this.setPage}
									page={page}
									pageSize={pageSize}
									totalCount={totalCount}
								/>
							</div>
						)}
					</ListView>
				</div>
			</div>
		);
	}
}

ProcessListCard.contextType = AppContext;
export default ProcessListCard;