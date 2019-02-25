import React from 'react';
import {sub} from '../../util/lang';

/**
 * @class
 * @memberof shared/components
 * */
export default class DisplayResult extends React.Component {
	render() {
		const {count, start, total} = this.props;

		return (
			<div className="pagination-results">
				{`${sub(Liferay.Language.get('showing-x-to-x-of-x-entries'), [
					start + 1,
					start + count,
					total
				])}`}
			</div>
		);
	}
}