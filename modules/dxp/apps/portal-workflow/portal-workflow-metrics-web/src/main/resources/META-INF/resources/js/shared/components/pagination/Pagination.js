import autobind from 'autobind-decorator';
import PageItem from './PageItem';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 * */
export default class Pagination extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			activePage: 0,
			pages: 1
		};
	}

	@autobind
	goToPage(activePage) {
		const {onSelectPage} = this.props;

		onSelectPage(activePage);
		this.setState({activePage});
	}

	render() {
		const {page, pageSize, totalCount} = this.props;

		const activePage = page === 1 ? page : this.state.activePage;
		const lastPage = Math.ceil(totalCount / pageSize);

		const nextPage = activePage + 1;
		const prevPage = activePage - 1;

		const hasNextPage = nextPage <= lastPage;
		const hasPreviousPage = prevPage > 0;

		const renderPageItems = () => {
			const rows = [];

			for (let i = 1; i <= lastPage; i++) {
				rows.push(
					<PageItem
						highlighted={i === activePage ? true : false}
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
				<PageItem
					disabled={!hasPreviousPage}
					key={`process_list_pag_prev_${prevPage}`}
					onChangePage={this.goToPage}
					page={prevPage}
					type="prev"
				/>

				{renderPageItems()}

				<PageItem
					disabled={!hasNextPage}
					key={`process_list_pag_next_${nextPage}`}
					onChangePage={this.goToPage}
					page={nextPage}
					type="next"
				/>
			</ul>
		);
	}
}