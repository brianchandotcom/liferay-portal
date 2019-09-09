/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	REQUEST_ORIGIN_TYPE_FETCH,
	REQUEST_ORIGIN_TYPE_SEARCH
} from './Constants.es';
import {AppContext} from '../AppContext.es';
import ListView from '../../shared/components/list/ListView.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import ProcessListTable from './ProcessListTable.es';
import React from 'react';
import ResultsBar from './ResultsBar.es';
import Search from '../../shared/components/pagination/Search.es';

/**
 * @class
 * @memberof process-list
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
		this.requestData(nextProps).then(({items, totalCount}) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	/**
	 * @desc request data
	 */
	requestData({page, pageSize, search, sort}) {
		const {client} = this.context;

		const searching = typeof search === 'string' && search ? true : false;

		const params = {
			page,
			pageSize,
			sort: decodeURIComponent(sort)
		};

		if (searching) {
			params.title = decodeURIComponent(search);
		}

		return client.get('/processes', {params}).then(({data}) => {
			if (data && data.totalCount === 0) {
				this.requestOriginType = searching
					? REQUEST_ORIGIN_TYPE_SEARCH
					: REQUEST_ORIGIN_TYPE_FETCH;
			}

			return data;
		});
	}

	render() {
		const {requestOriginType} = this;
		const {items = [], totalCount} = this.state;
		const {page, pageSize} = this.props;

		const emptyTitleText = Liferay.Language.get('no-current-metrics');
		const fetching =
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0;
		const loading = !requestOriginType && totalCount === 0;
		const searching =
			requestOriginType === REQUEST_ORIGIN_TYPE_SEARCH &&
			totalCount === 0;

		const emptyMessageText = searching
			? Liferay.Language.get('no-results-were-found')
			: Liferay.Language.get(
					'once-there-are-active-processes-metrics-will-appear-here'
			  );

		return (
			<div>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<div className="navbar-form navbar-form-autofit">
							<Search disabled={fetching} />
						</div>
					</div>
				</nav>

				{this.props.search && <ResultsBar totalCount={totalCount} />}

				<div className="container-fluid-1280">
					<ListView
						emptyMessageText={emptyMessageText}
						emptyTitleText={emptyTitleText}
						fetching={fetching}
						loading={loading}
						searching={searching}
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
