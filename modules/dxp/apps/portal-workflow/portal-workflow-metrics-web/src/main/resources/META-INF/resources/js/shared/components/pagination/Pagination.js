import PageItem from './PageItem';
import PageLink from './PageLink';
import React from 'react';
import {withRouter} from 'react-router-dom';

/**
 * @class
 * @memberof shared/components
 */
class Pagination extends React.Component {
	constructor(props) {
		super(props);
		this.state = {};
	}

	getProps() {
		const {
			match: {params},
			maxPages,
			totalCount
		} = this.props;
		const {page = 1, pageSize = 20} = params || {};

		return {
			maxPages,
			page: Number(page),
			pageSize: Number(pageSize),
			totalCount
		};
	}

	buildDropMenu(initCount, condition, functionIterator) {
		const data = [];

		for (let i = initCount; i < condition; i++) {
			if (functionIterator) {
				functionIterator(data, i);
			} else {
				data.push(i);
			}
		}

		return (
			<li
				className="dropdown page-item"
				key={`process_list_pag_${initCount}`}
			>
				<a
					className="dropdown-toggle page-link"
					data-senna-off
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
								page={page}
							/>
						))}
					</ul>
				</div>
			</li>
		);
	}

	render() {
		const {
			maxPages,
			page: activePage,
			pageSize,
			totalCount
		} = this.getProps();

		const lastPage = Math.ceil(totalCount / pageSize);

		const nextPage = activePage + 1;
		const prevPage = activePage - 1;

		const hasNextPage = nextPage <= lastPage;
		const hasPreviousPage = prevPage > 0;

		const pages = lastPage;

		const renderPageItems = () => {
			const rows = {};

			const addPage = (page, component) => {
				if (page > 0 && Object.keys(rows).indexOf(page) < 0) {
					rows[page] = component;
				}
			};

			if (pages <= 5) {
				for (let i = 1; i <= pages; i++) {
					addPage(
						i,
						<PageItem
							highlighted={i === activePage}
							key={`process_list_pag_${i}`}
							page={i}
						/>
					);
				}
			} else if (activePage === 1) {
				for (let i = 1; i <= 3; i++) {
					addPage(
						i,
						<PageItem
							highlighted={i === activePage ? true : false}
							key={`process_list_pag_sublist_${i}`}
							page={i}
						/>
					);
				}

				addPage(
					4,
					this.buildDropMenu(4, maxPages, (data, i) => {
						if (i < pages) {
							data.push(i);
						}
					})
				);

				addPage(
					lastPage,
					<PageItem
						highlighted={lastPage === activePage ? true : false}
						key={`process_list_pag_${lastPage}`}
						page={lastPage}
					/>
				);
			} else if (activePage === pages) {
				addPage(
					1,
					<PageItem
						highlighted={1 === activePage ? true : false}
						key={'process_list_pag_1'}
						page={1}
					/>
				);

				addPage(
					2,
					this.buildDropMenu(
						2,
						maxPages > activePage - 2 ? activePage - 2 : maxPages
					)
				);

				for (let i = pages - 2; i <= pages; i++) {
					addPage(
						i,
						<PageItem
							highlighted={i === activePage ? true : false}
							key={`process_list_pag_sublist_${i}`}
							page={i}
						/>
					);
				}
			} else {
				addPage(
					1,
					<PageItem
						highlighted={activePage === 1 ? true : false}
						key={'process_list_pag_1'}
						page={1}
					/>
				);
				if (activePage - 3 > 1) {
					addPage(
						2,
						this.buildDropMenu(
							2,
							maxPages > activePage - 1
								? activePage - 1
								: maxPages
						)
					);
				} else {
					for (
						let i = 2,
							x =
								maxPages > activePage - 1
									? activePage - 1
									: maxPages;
						i < x;
						i++
					) {
						addPage(
							i,
							<PageItem
								highlighted={i === activePage ? true : false}
								key={`process_list_pag_sublist_${i}`}
								page={i}
							/>
						);
					}
				}

				if (activePage - 1 > 1) {
					addPage(
						activePage - 1,
						<PageItem
							key={`process_list_pag_sublist_${activePage - 1}`}
							page={activePage - 1}
						/>
					);
				}

				addPage(
					activePage,
					<PageItem
						highlighted={activePage !== 1 ? true : false}
						key={`process_list_pag_active_${activePage}`}
						page={activePage}
					/>
				);

				if (activePage + 1 < pages) {
					addPage(
						activePage + 1,
						<PageItem
							key={`process_list_pag_plus_${activePage + 1}`}
							page={activePage + 1}
						/>
					);
				}
				const remainingPages =
					pages - activePage + 2 < maxPages
						? pages
						: activePage + maxPages + 2;

				if (activePage + 3 < pages) {
					addPage(
						activePage + 2,
						this.buildDropMenu(activePage + 2, remainingPages)
					);
				} else {
					for (let i = activePage + 2; i < remainingPages; i++) {
						addPage(
							i,
							<PageItem
								highlighted={i === activePage}
								key={`process_list_pag_sublist_${i}`}
								page={i}
							/>
						);
					}
				}

				addPage(
					lastPage,
					<PageItem
						highlighted={lastPage === activePage}
						key={`process_list_pag_last_${lastPage}`}
						page={lastPage}
					/>
				);
			}

			return Object.keys(rows).map(key => rows[key]);
		};

		return (
			<ul className="pagination pull-right">
				<PageItem
					disabled={!hasPreviousPage}
					key={`process_list_pag_prev_${prevPage}`}
					page={hasPreviousPage ? prevPage : 1}
					type="prev"
				/>

				{renderPageItems()}

				<PageItem
					disabled={!hasNextPage}
					key={`process_list_pag_next_${nextPage}`}
					page={nextPage}
					type="next"
				/>
			</ul>
		);
	}
}

export default withRouter(Pagination);

export {Pagination};
