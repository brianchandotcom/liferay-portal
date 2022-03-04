/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {ClayResultsBar} from '@clayui/management-toolbar';

import i18n from '../../i18n';

type ManagementToolbarResultsBarProps = {
	keywords: string;
	onClear: (event: any) => void;
	totalItems: number;
};

const ManagementToolbarResultsBar: React.FC<ManagementToolbarResultsBarProps> = ({
	keywords,
	onClear,
	totalItems,
}) => {
	return (
		<ClayResultsBar>
			<ClayResultsBar.Item expand>
				<span className="component-text text-truncate-inline">
					<span className="text-truncate">
						{i18n.sub('x-results-for-x', [
							totalItems.toString(),
							keywords,
						])}
					</span>
				</span>
			</ClayResultsBar.Item>

			<ClayResultsBar.Item>
				<ClayButton
					className="component-link tbar-link"
					displayType="unstyled"
					onClick={onClear}
				>
					Clear
				</ClayButton>
			</ClayResultsBar.Item>
		</ClayResultsBar>
	);
};

export default ManagementToolbarResultsBar;
