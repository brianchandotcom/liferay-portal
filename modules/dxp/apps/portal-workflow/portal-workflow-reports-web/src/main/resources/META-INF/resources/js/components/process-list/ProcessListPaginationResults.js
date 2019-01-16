import React from 'react';
import {sub} from '../../shared/util/lang';

export default class ProcessListPaginationResults extends React.Component {
	render() {
		const {count, start, total} = this.props;

		return (
			<div className="pagination-results">
				{`${sub(Liferay.Language.get('pagination-result'), [
					start + 1,
					start + count,
					total
				])}`}
			</div>
		);
	}
}