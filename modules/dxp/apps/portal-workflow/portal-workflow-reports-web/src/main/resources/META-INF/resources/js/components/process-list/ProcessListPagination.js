import autobind from 'autobind-decorator';
import ProcessListPaginationItem from './ProcessListPaginationItem';
import React from 'react';

export default class ProcessListPagination extends React.Component {
	constructor() {
		super();

		this.state = {
			pages: 1
		};
	}

	@autobind
	goToPage(page) {
		const {entry, pageClickHandler} = this.props;
		const start = page * entry;

		const size = entry;

		pageClickHandler({page, size, start});
	}

	render() {
		const {entry, totalCount} = this.props;

		const pages = Math.ceil(totalCount / entry);

		const pagesRender = () => {
			const rows = [];

			for (let i = 0; i < pages; i++) {
				rows.push(
					<ProcessListPaginationItem
						key={`process_list_pag_${i}`}
						onChangePage={this.goToPage}
						page={i}
					/>
				);
			}

			return rows;
		};

		return (
			<ul className="pagination pull-right">
				<ProcessListPaginationItem
					onChangePage={this.goToPage}
					page={0}
					type="prev"
				/>

				{pagesRender()}

				<ProcessListPaginationItem
					onChangePage={this.goToPage}
					page={pages - 1}
					type="next"
				/>
			</ul>
		);
	}
}