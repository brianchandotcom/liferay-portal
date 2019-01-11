import React from 'react';

export default class ProcessListPaginationResults extends React.Component {
	render() {
		const {start, count, total} = this.props;

		return (
			<div className="pagination-results">
				{`Showing ${start + 1} to ${start + count} of ${total} entries.`}
			</div>
		);
	}
}