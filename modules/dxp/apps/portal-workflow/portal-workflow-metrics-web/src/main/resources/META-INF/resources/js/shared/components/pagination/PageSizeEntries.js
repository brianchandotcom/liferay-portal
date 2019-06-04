import {AppContext} from '../../../components/AppContext';
import Icon from '../Icon';
import PageSizeItem from './PageSizeItem';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageSizeEntries extends React.Component {
	render() {
		const {deltas} = this.context;
		const {pageSizeEntries = deltas, selectedPageSize} = this.props;

		return (
			<div className='dropdown pagination-items-per-page'>
				<a
					aria-expanded='false'
					aria-haspopup='true'
					className='dropdown-toggle'
					data-toggle='dropdown'
					href='#1'
					role='button'
				>
					{`${selectedPageSize} ${'Entries'}`}
					<Icon iconName='caret-double-l' />
				</a>
				<div className='dropdown-menu dropdown-menu-top'>
					{pageSizeEntries.map((pageSizeKey, index) => (
						<PageSizeItem
							key={`${index}_${pageSizeKey}`}
							pageSize={pageSizeKey}
						/>
					))}
				</div>
			</div>
		);
	}
}

PageSizeEntries.contextType = AppContext;
export default PageSizeEntries;