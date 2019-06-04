import React from 'react';
import {sub} from '../../util/lang';

/**
 * @class
 * @memberof shared/components
 */
export default class DisplayResult extends React.Component {
	render() {
		const {page, pageCount, pageSize, totalCount} = this.props;
		const firstItem = pageSize * (page - 1) + 1;
		const lastItem = firstItem + pageCount - 1;

		return (
			<div className='pagination-results'>
				{`${sub(Liferay.Language.get('showing-x-to-x-of-x-entries'), [
					firstItem,
					lastItem,
					totalCount
				])}`}
			</div>
		);
	}
}