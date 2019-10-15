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

import {AppContext} from '../../../components/AppContext.es';
import Icon from '../Icon.es';
import PageSizeItem from './PageSizeItem.es';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageSizeEntries extends React.Component {
	render() {
		const {deltas} = this.context;
		const {pageSizeEntries = deltas, selectedPageSize} = this.props;

		return (
			<div className="dropdown pagination-items-per-page">
				<a
					aria-expanded="false"
					aria-haspopup="true"
					className="dropdown-toggle"
					data-toggle="dropdown"
					href="#1"
					role="button"
				>
					{`${selectedPageSize} ${'Entries'}`}
					<Icon iconName="caret-double-l" />
				</a>
				<div className="dropdown-menu dropdown-menu-top">
					{pageSizeEntries.map((pageSizeKey, index) => (
						<PageSizeItem
							key={`${index}_${pageSizeKey}`}
							pageSize={pageSizeKey}
						/>
					))}
				</div>
			</div>
		);
	}
}

PageSizeEntries.contextType = AppContext;
export default PageSizeEntries;
