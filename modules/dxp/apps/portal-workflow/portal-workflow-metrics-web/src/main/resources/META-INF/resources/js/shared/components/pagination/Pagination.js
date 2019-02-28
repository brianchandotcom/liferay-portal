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
		const renderPages = () => {
			const rows = [];

			for (let i = 1; i <= lastPage; i++) {
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
				<PageItem onChangePage={this.goToPage} page={1} type="prev" />

				{renderPages()}

				<PageItem onChangePage={this.goToPage} page={lastPage} type="next" />
			</ul>
		);
	}
}