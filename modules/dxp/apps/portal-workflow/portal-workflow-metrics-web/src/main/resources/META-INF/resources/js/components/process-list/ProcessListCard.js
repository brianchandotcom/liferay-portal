import {
	REQUEST_ORIGIN_TYPE_FETCH,
	REQUEST_ORIGIN_TYPE_SEARCH
} from './Constants';
import autobind from 'autobind-decorator';
import DisplayResult from '../../shared/components/pagination/DisplayResult';
import EmptyContent from '../../shared/components/EmptyContent';
import PageSizeEntries from '../../shared/components/pagination/PageSizeEntries';
import Pagination from '../../shared/components/pagination/Pagination';
import ProcessListTable from './ProcessListTable';
import React from 'react';
import Search from '../../shared/components/pagination/Search';

/**
 * @class
 * @memberof processes-list
 * */
export default class ProcessListCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			items: [],
			page: 1,
			pageSize: 20,
			requestOriginType: null,
			totalCount: 0
		};
	}

	componentWillMount() {
		const {page, pageSize} = this.state;

		this.requestData({page, pageSize}).then(({items, totalCount}) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	/**
	 * @param {Object} configuration
	 * @param {number} configuration.page
	 * @param {number} configuration.pageSize
	 * @param {string} configuration.title
	 */
	requestData({page, pageSize, title}) {
		const {client} = this.props;
		const isSearch = typeof title === 'string';
		let urlRequest = `/processes?page=${page}&pageSize=${pageSize}`;

		if (isSearch) {
			urlRequest += `&title=${encodeURIComponent(title)}`;
		}

		this.state.requestOriginType = isSearch
			? REQUEST_ORIGIN_TYPE_SEARCH
			: REQUEST_ORIGIN_TYPE_FETCH;

		return client(urlRequest).then(({items, totalCount}) => ({
			items,
			totalCount
		}));
	}

	@autobind
	onSearch(title) {
		const {pageSize} = this.state;
		const page = 1;

		this.requestData({page, pageSize, title}).then(({items, totalCount}) =>
			this.setState({items, page, totalCount})
		);
	}

	@autobind
	setPage(page) {
		const {pageSize} = this.state;

		return this.requestData({page, pageSize}).then(({items, totalCount}) =>
			this.setState({items, page, totalCount})
		);
	}

	@autobind
	setPageSize(pageSize) {
		const page = 1;

		return this.requestData({page, pageSize}).then(({items, totalCount}) =>
			this.setState({items, page, pageSize, totalCount})
		);
	}

	render() {
		const {items, page, pageSize, requestOriginType, totalCount} = this.state;
		const pageSizes = [5, 10, 20, 30, 50, 75];

		const emptySearchRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_SEARCH && totalCount === 0 ? (
				<EmptyContent
					message={Liferay.Language.get('no-results-were-found')}
					type="not-found"
				/>
			) : (
				secondaryRender
			);

		const emptyContentRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0 ? (
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
			totalCount > 0 ? (
				<div>
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
									requestOriginType === REQUEST_ORIGIN_TYPE_FETCH &&
									totalCount === 0
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