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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';

import i18n from '../../i18n';
import Form from '../Form';
import {RendererFields} from '../Form/Renderer';

type ManagementToolbarFilterProps = {
	filterFields?: RendererFields[];
};

const ManagementToolbarFilter: React.FC<ManagementToolbarFilterProps> = ({
	filterFields,
}) => {
	const [filter, setFilter] = useState('');
	const [active, setActive] = useState(false);

	const hasFilter = !!filterFields?.length;

	return (
		<ClayDropDown
			menuWidth="sm"
			trigger={
				<ClayButton className="nav-link" displayType="unstyled">
					<span className="navbar-breakpoint-down-d-none">
						<ClayIcon
							className="inline-item inline-item-after"
							symbol="filter"
						/>
					</span>

					<span className="navbar-breakpoint-d-none">
						<ClayIcon symbol="filter" />
					</span>
				</ClayButton>
			}
		>
			{hasFilter && (
				<>
					<div className="px-3">
						<p className="font-weight-bold my-2">Filter Results</p>

						<Form.Input
							name="search-filter"
							onChange={({target: {value}}) => setFilter(value)}
							placeholder="Search Filters"
							value={filter}
						/>
					</div>

					<Form.Divider />

					<div className="px-3">
						<Form.Renderer fields={filterFields} filter={filter} />
					</div>

					<Form.Divider />

					<div className="mb-2 px-3">
						<ClayButton onClick={() => setActive(!active)}>
							{i18n.translate('apply')}
						</ClayButton>

						<ClayButton className="ml-3" displayType="secondary">
							{i18n.translate('clear')}
						</ClayButton>
					</div>
				</>
			)}
		</ClayDropDown>
	);
};

export default ManagementToolbarFilter;
