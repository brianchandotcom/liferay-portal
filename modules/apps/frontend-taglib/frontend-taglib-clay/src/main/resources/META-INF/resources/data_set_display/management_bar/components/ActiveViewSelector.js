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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import persistActiveView from '../../thunks/persistActiveView';
import ViewsContext from '../../views/ViewsContext';

function ActiveViewSelector({views}) {
	const [active, setActive] = useState(false);
	const [{activeView}, dispatch] = useContext(ViewsContext);
	const {id} = useContext(DataSetDisplayContext);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					displayType="secondary"
					symbol={activeView.thumbnail}
				/>
			}
		>
			<ClayDropDown.ItemList>
				{views.map(({label, name, thumbnail}) => (
					<ClayDropDown.Item
						key={name}
						onClick={(event) => {
							event.preventDefault();
							setActive(false);
							dispatch(
								persistActiveView({
									activeViewName: name,
									id,
								})
							);
						}}
					>
						<ClayIcon className="mr-3" symbol={thumbnail} />
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

ActiveViewSelector.propTypes = {
	views: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			thumbnail: PropTypes.string.isRequired,
		})
	),
};

export default ActiveViewSelector;
