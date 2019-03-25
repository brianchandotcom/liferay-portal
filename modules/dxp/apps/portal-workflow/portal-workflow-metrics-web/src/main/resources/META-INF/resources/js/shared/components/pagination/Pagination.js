import autobind from 'autobind-decorator';
import PageItem from './PageItem';
import PageLink from './PageLink';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
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
		const { onSelectPage } = this.props;

		onSelectPage(activePage);
		this.setState({ activePage });
	}

	buildDropMenu(initCount, condition, functionIterator) {
		const data = [];

		for (let i = initCount; i < condition; i++) {
			if (functionIterator) {
				functionIterator(data, i);
			}
			else {
				data.push(i);
			}
		}

		return (
			<li className="dropdown page-item" key={`process_list_pag_${initCount}`}>
				<a
					className="dropdown-toggle page-link"
					data-toggle="dropdown"
					href="javascript:;"
				>
					<span aria-hidden="true">{'...'}</span>

					<span className="sr-only" />
				</a>
				<div className="dropdown-menu dropdown-menu-top-center">
					<ul
						className="inline-scroller link-list"
						data-current-index={`${initCount}`}
						key={`process_list_pag_sublist_${initCount}`}
					>
						{data.map(page => (
							<PageLink
								key={`process_list_pag_sublist_item_${page}`}
								onChangePage={this.goToPage}
								page={page}
							/>
						))}
					</ul>
				</div>
			</li>
		);
	}

	render() {
		const { page, pageSize, totalCount } = this.props;

		const activePage = page === 1 ? page : this.state.activePage;
		const lastPage = Math.ceil(totalCount / pageSize);

		const nextPage = activePage + 1;
		const prevPage = activePage - 1;

		const hasNextPage = nextPage <= lastPage;
		const hasPreviousPage = prevPage > 0;

		const maxDelta = 25;

		const pages = lastPage;

		const renderPageItems = () => {
			const rows = [];

			if (pages <= 5) {
				for (let i = 1; i <= pages; i++) {
					rows.push(
						<PageItem
							highlighted={i === activePage ? true : false}
							key={`process_list_pag_${i}`}
							onChangePage={this.goToPage}
							page={i}
						/>
					);
				}
			}
			else if (activePage === 1) {
				for (let i = 1; i <= 3; i++) {
					rows.push(
						<PageItem
							highlighted={i === activePage ? true : false}
							key={`process_list_pag_sublist_${i}`}
							onChangePage={this.goToPage}
							page={i}
						/>
					);
				}

				rows.push(
					this.buildDropMenu(4, maxDelta, (data, i) => {
						if (i < pages) {
							data.push(i);
						}
					})
				);

				rows.push(
					<PageItem
						highlighted={lastPage === activePage ? true : false}
						key={`process_list_pag_${lastPage}`}
						onChangePage={this.goToPage}
						page={lastPage}
					/>
				);
			}
			else if (activePage === pages) {
				rows.push(
					<PageItem
						highlighted={1 === activePage ? true : false}
						key={'process_list_pag_1'}
						onChangePage={this.goToPage}
						page={1}
					/>
				);

				rows.push(
					this.buildDropMenu(
						2,
						maxDelta > activePage - 2 ? activePage - 2 : maxDelta
					)
				);

				for (let i = pages - 2; i <= pages; i++) {
					rows.push(
						<PageItem
							highlighted={i === activePage ? true : false}
							key={`process_list_pag_sublist_${i}`}
							onChangePage={this.goToPage}
							page={i}
						/>
					);
				}
			}
			else {
				rows.push(
					<PageItem
						highlighted={activePage === 1 ? true : false}
						key={'process_list_pag_1'}
						onChangePage={this.goToPage}
						page={1}
					/>
				);
				if (activePage - 3 > 1) {
					rows.push(
						this.buildDropMenu(
							2,
							maxDelta > activePage - 1 ? activePage - 1 : maxDelta
						)
					);
				}
				else {
					for (
						let i = 2,
							x = maxDelta > activePage - 1 ? activePage - 1 : maxDelta;
						i < x;
						i++
					) {
						rows.push(
							<PageItem
								highlighted={i === activePage ? true : false}
								key={`process_list_pag_sublist_${i}`}
								onChangePage={this.goToPage}
								page={i}
							/>
						);
					}
				}

				if (activePage - 1 > 1) {
					rows.push(
						<PageItem
							key={`process_list_pag_sublist_${activePage - 1}`}
							onChangePage={this.goToPage}
							page={activePage - 1}
						/>
					);
				}

				rows.push(
					<PageItem
						highlighted={activePage !== 1 ? true : false}
						key={`process_list_pag_active_${activePage}`}
						onChangePage={this.goToPage}
						page={activePage}
					/>
				);

				if (activePage + 1 < pages) {
					rows.push(
						<PageItem
							key={`process_list_pag_plus_${activePage + 1}`}
							onChangePage={this.goToPage}
							page={activePage + 1}
						/>
					);
				}
				const remainingPages =
					pages - activePage + 2 < maxDelta ? pages : activePage + 2 + maxDelta;

				if (activePage + 3 < pages) {
					rows.push(this.buildDropMenu(activePage + 2, remainingPages));
				}
				else {
					for (let i = activePage + 2; i < remainingPages; i++) {
						rows.push(
							<PageItem
								highlighted={i === activePage ? true : false}
								key={`process_list_pag_sublist_${i}`}
								onChangePage={this.goToPage}
								page={i}
							/>
						);
					}
				}

				rows.push(
					<PageItem
						highlighted={lastPage === activePage ? true : false}
						key={`process_list_pag_last_${lastPage}`}
						onChangePage={this.goToPage}
						page={lastPage}
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