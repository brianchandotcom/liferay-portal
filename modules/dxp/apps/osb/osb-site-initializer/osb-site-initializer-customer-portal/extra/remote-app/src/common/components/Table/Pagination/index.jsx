/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
