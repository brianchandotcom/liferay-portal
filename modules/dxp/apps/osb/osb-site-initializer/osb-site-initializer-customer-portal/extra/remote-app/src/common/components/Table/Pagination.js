import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

const TablePagination = ({
	activeDelta = 5,
	activePage,
	ellipsisBuffer = 3,
	itemsPerPage,
	setActivePage,
	showDeltasDropDown = false,
	totalItems,
}) => {
	if (totalItems > itemsPerPage) {
		return (
			<div className="mb-3 mx-3">
				<ClayPaginationBarWithBasicItems
					activeDelta={activeDelta}
					activePage={activePage}
					ellipsisBuffer={ellipsisBuffer}
					onPageChange={(page) => setActivePage(page)}
					showDeltasDropDown={showDeltasDropDown}
					spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`}
					totalItems={totalItems}
				/>
			</div>
		);
	}

	return (
		<p className="mb-4 mx-4 text-paragraph">{`Showing ${
			itemsPerPage * activePage + 1 - itemsPerPage
		} to ${itemsPerPage * activePage} of ${totalItems} entries.`}</p>
	);
};

export default TablePagination;
