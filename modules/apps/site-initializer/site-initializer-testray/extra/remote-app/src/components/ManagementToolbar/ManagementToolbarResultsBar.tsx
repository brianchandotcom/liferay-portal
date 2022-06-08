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
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ClayResultsBar} from '@clayui/management-toolbar';
import {useContext} from 'react';

import {ListViewContext, ListViewTypes} from '../../context/ListViewContext';
import i18n from '../../i18n';

type ManagementToolbarResultsBarProps = {
	totalItems: number;
};

const ManagementToolbarResultsBar: React.FC<ManagementToolbarResultsBarProps> = ({
	totalItems,
}) => {
	const [
		{
			filters: {entries},
		},
		dispatch,
	] = useContext(ListViewContext);

	const onClear = () => {
		dispatch({payload: null, type: ListViewTypes.SET_CLEAR});
	};

	const onRemoveFilter = (filterName: string) => {
		dispatch({payload: filterName, type: ListViewTypes.SET_REMOVE_FILTER});
	};

	return (
		<ClayResultsBar>
			<ClayResultsBar.Item>
				<span className="component-text text-truncate-inline">
					<span className="text-truncate">
						{i18n.sub('x-results-for-x', [
							totalItems.toString(),
							'',
						])}
					</span>
				</span>
			</ClayResultsBar.Item>

			{entries.map((entry, index) => {
				return (
					<ClayResultsBar.Item
						expand={index === entries.length - 1}
						key={index}
					>
						<ClayLabel
							className="component-label result-filter tbar-label"
							displayType="unstyled"
						>
							<span className="d-flex flex-row">
								<b>{entry.label}</b>

								{`: ${entry.value}`}

								<ClayIcon
									className="cursor-pointer ml-2"
									onClick={() => onRemoveFilter(entry.name)}
									symbol="times"
								/>
							</span>
						</ClayLabel>
					</ClayResultsBar.Item>
				);
			})}

			<ClayResultsBar.Item>
				<ClayButton
					className="component-link tbar-link"
					displayType="unstyled"
					onClick={onClear}
				>
					{i18n.translate('clear')}
				</ClayButton>
			</ClayResultsBar.Item>
		</ClayResultsBar>
	);
};

export default ManagementToolbarResultsBar;
