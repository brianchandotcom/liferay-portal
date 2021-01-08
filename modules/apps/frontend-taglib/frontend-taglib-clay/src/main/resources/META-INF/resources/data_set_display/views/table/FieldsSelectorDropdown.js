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
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext';
import DataSetDisplayContext from '../../DataSetDisplayContext';
import persistVisibleFieldNames from '../../thunks/persistVisibleFieldNames';
import ViewsContext from '../ViewsContext';

const FieldsSelectorDropdown = ({fields}) => {
	const {id} = useContext(DataSetDisplayContext);
	const {appURL, portletId} = useContext(AppContext);
	const [{visibleFieldNames}, dispatch] = useContext(ViewsContext);

	const [active, setActive] = useState(false);
	const [filteredFields, setFilteredFields] = useState(fields);
	const [query, setQuery] = useState('');

	const selectedFieldNames = Object.keys(visibleFieldNames).length
		? visibleFieldNames
		: fields.reduce(
				(selectedFieldNames, field) => ({
					...selectedFieldNames,
					[field.fieldName]: true,
				}),
				{}
		  );

	useEffect(() => {
		setFilteredFields(
			fields.filter((field) =>
				field.label.toLowerCase().includes(query.toLowerCase())
			)
		);
	}, [fields, query]);

	return (
		<ClayDropDown
			active={active}
			className="data-set-fields-selector-dropdown"
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					borderless
					className="p-0"
					displayType="secondary"
					monospaced={false}
					symbol={active ? 'caret-top' : 'caret-bottom'}
				/>
			}
		>
			<ClayDropDown.Search
				onChange={(event) => setQuery(event.target.value)}
				value={query}
			/>
			{filteredFields.length ? (
				<ClayDropDown.ItemList>
					{filteredFields.map(({fieldName, label}) => (
						<ClayDropDown.Item
							key={fieldName}
							onClick={() => {
								dispatch(
									persistVisibleFieldNames({
										appURL,
										id,
										portletId,
										visibleFieldNames: {
											...selectedFieldNames,
											[fieldName]: !selectedFieldNames[
												fieldName
											],
										},
									})
								);
							}}
						>
							{selectedFieldNames[fieldName] && (
								<ClayIcon className="mr-2" symbol="check" />
							)}
							{label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			) : (
				<div className="dropdown-section text-muted">
					{Liferay.Language.get('no-fields-were-found')}
				</div>
			)}
		</ClayDropDown>
	);
};

FieldsSelectorDropdown.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			fieldName: PropTypes.oneOfType([
				PropTypes.string,
				PropTypes.arrayOf(PropTypes.string),
			]),
			label: PropTypes.string,
		})
	),
};

export default FieldsSelectorDropdown;
