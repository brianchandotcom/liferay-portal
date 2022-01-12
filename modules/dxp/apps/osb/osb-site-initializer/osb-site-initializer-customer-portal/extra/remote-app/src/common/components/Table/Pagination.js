/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {getIconSpriteMap} from '../../providers/ClayProvider';

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
					spritemap={getIconSpriteMap()}
					totalItems={totalItems}
				/>
			</div>
		);
	}

	return (
		<p className="mb-4 mx-4 pagination-results">{`Showing ${
			itemsPerPage * activePage + 1 - itemsPerPage
		} to ${totalItems} of ${totalItems} entries.`}</p>
	);
};

export default TablePagination;
