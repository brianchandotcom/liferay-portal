import autobind from 'autobind-decorator';
import PageItem from './PageItem';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 * */
export default class Pagination extends React.Component {
	constructor() {
		super();

		this.state = {
			activePage: 0,
			pages: 1
		};
	}

	@autobind
	goToPage(page) {
		const {entry, pageClickHandler} = this.props;
		const start = page * entry;

		const size = entry;

		pageClickHandler({page, size, start});

		this.setState({activePage: page});
	}

	render() {
		const {entry, start, totalCount} = this.props;
		const activePage = start === 0 ? start : this.state.activePage;

		const pages = Math.ceil(totalCount / entry);

		const renderPages = () => {
			const rows = [];

			for (let i = 0; i < pages; i++) {
				rows.push(
					<PageItem
						active={i === activePage ? true : false}
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
				<PageItem onChangePage={this.goToPage} page={0} type="prev" />

				{renderPages()}

				<PageItem onChangePage={this.goToPage} page={pages - 1} type="next" />
			</ul>
		);
	}
}