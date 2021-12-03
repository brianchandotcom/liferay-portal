import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

const TablePagination = ({
	activePage,
	itemsPerPage,
	setActivePage,
	totalItems,
}) => {
	const handlePageChange = (page) => {
		setActivePage(page);
	};

	return (
		<>
			{totalItems > itemsPerPage ? (
				<div className="mb-3 mx-3">
					<ClayPaginationBarWithBasicItems
						activeDelta={5}
						activePage={activePage}
						ellipsisBuffer={3}
						onPageChange={handlePageChange}
						showDeltasDropDown={false}
						spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`}
						totalItems={totalItems}
					/>
				</div>
			) : (
				<p className="mb-4 mx-4 text-paragraph">{`Showing 1 to ${totalItems} of ${totalItems} entries.`}</p>
			)}
		</>
	);
};

export default TablePagination;
