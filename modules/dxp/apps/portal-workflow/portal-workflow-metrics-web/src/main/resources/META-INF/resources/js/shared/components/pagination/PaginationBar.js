import {AppContext} from '../../../components/AppContext';
import DisplayResult from './DisplayResult';
import PageSizeEntries from './PageSizeEntries';
import Pagination from './Pagination';
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
			return <div className='pagination-bar' />;
		}

		return (
			<div className='pagination-bar'>
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
