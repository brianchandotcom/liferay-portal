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
import classNames from 'classnames';
import i18n from '../../../../common/I18n';
import getIconSpriteMap from '../../../utils/getIconSpriteMap';

const TablePagination = ({
	activePage,
	ellipsisBuffer = 3,
	itemsPerPage = 5,
	setActivePage,
	labels,
	setItemsPerPage,
	showDeltasDropDown = false,
	listItemsPerPage = [],
	totalItems,
}) => {
	if (showDeltasDropDown || totalItems > itemsPerPage) {
		const defaultLabels = {
			paginationResults: i18n.translate('showing-x-to-x-of-x'),
			perPageItems: i18n.translate('show-x-items'),
			selectPerPageItems: i18n.translate('x-items'),
		};

		return (
			<div className="mb-3 mx-2">
				<ClayPaginationBarWithBasicItems
					activeDelta={itemsPerPage}
					activePage={activePage}
					className={classNames({
						'cp-hide-pagination-activation-keys':
							itemsPerPage >= totalItems,
					})}
					deltas={listItemsPerPage}
					ellipsisBuffer={ellipsisBuffer}
					labels={labels || defaultLabels}
					onDeltaChange={setItemsPerPage}
					onPageChange={(page) => setActivePage(page)}
					showDeltasDropDown={showDeltasDropDown}
					spritemap={getIconSpriteMap()}
					totalItems={totalItems}
				/>
			</div>
		);
	}

	return (
		<>
			<p className="mb-4 mx-4 pagination-results">
				{i18n.sub('showing-x-to-x-of-x-entries', [
					`${itemsPerPage * activePage + 1 - itemsPerPage}`,
					totalItems,
					totalItems,
				])}
			</p>
		</>
	);
};

export default TablePagination;
