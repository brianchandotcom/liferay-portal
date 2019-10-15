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

import {AppContext} from '../../../components/AppContext.es';
import DisplayResult from './DisplayResult.es';
import PageSizeEntries from './PageSizeEntries.es';
import Pagination from './Pagination.es';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PaginationBar extends React.Component {
	render() {
		const {deltas, maxPages} = this.context;
		const {
			page,
			pageCount,
			pageSize,
			pageSizes = deltas,
			totalCount
		} = this.props;

		if (totalCount <= pageSizes[0]) {
			return <div className="pagination-bar" />;
		}

		return (
			<div className="pagination-bar">
				<PageSizeEntries
					pageSizeEntries={pageSizes}
					selectedPageSize={pageSize}
				/>

				<DisplayResult
					page={page}
					pageCount={pageCount}
					pageSize={pageSize}
					totalCount={totalCount}
				/>

				<Pagination maxPages={maxPages} totalCount={totalCount} />
			</div>
		);
	}
}

PaginationBar.contextType = AppContext;
export default PaginationBar;
