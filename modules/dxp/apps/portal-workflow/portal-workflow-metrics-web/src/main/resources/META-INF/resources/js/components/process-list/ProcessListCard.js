import {
	REQUEST_ORIGIN_TYPE_FETCH,
	REQUEST_ORIGIN_TYPE_SEARCH
} from './Constants';
import { AppContext } from '../AppContext';
import ListView from '../../shared/components/list/ListView';
import PaginationBar from '../../shared/components/pagination/PaginationBar';
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

		this.requestOriginType = null;
		this.state = {
			items: [],
			totalCount: 0
		};
	}

	componentWillMount() {
		this.context.setTitle(Liferay.Language.get('metrics'));
	}

	componentWillReceiveProps(nextProps) {
		this.requestData(nextProps).then(({ items, totalCount }) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	/**
	 * @desc request data
	 */
	requestData({ page, pageSize, search, sort }) {
		const { client } = this.context;

		const isSearching = typeof search === 'string' && search ? true : false;

		const params = {
			page,
			pageSize,
			sort: decodeURIComponent(sort)
		};

		if (isSearching) {
			params.title = decodeURIComponent(search);
		}

		return client.get('/processes', { params }).then(({ data }) => {
			if (data && data.totalCount === 0) {
				this.requestOriginType = isSearching
					? REQUEST_ORIGIN_TYPE_SEARCH
					: REQUEST_ORIGIN_TYPE_FETCH;
			}

			return data;
		});
	}

	render() {
		const { requestOriginType } = this;
		const { items = [], totalCount } = this.state;
		const { page, pageSize } = this.props;

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

		return (
			<div>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<div className="navbar-form navbar-form-autofit">
							<Search disabled={isFetching} />
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

						<PaginationBar
							page={page}
							pageCount={items.length}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</ListView>
				</div>
			</div>
		);
	}
}

ProcessListCard.contextType = AppContext;
export default ProcessListCard;