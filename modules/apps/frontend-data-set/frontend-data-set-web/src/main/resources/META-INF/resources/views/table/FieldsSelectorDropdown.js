/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import persistVisibleFieldNames from '../../thunks/persistVisibleFieldNames';
import ViewsContext from '../ViewsContext';

const FieldsSelectorDropdown = ({fields}) => {
	const {appURL, id, portletId} = useContext(FrontendDataSetContext);
	const [{visibleFieldNames}, viewsDispatch] = useContext(ViewsContext);

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
			className="ml-auto"
			hasLeftSymbols
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					aria-label={
						active
							? Liferay.Language.get('close-fields-menu')
							: Liferay.Language.get('open-fields-menu')
					}
					borderless
					className={classnames({
						'component-action': Liferay.FeatureFlags['LPS-193005'],
					})}
					displayType="secondary"
					size="xs"
					symbol={active ? 'caret-top' : 'caret-bottom'}
				/>
			}
		>
			<ClayDropDown.Search
				formProps={{onSubmit: (event) => event.preventDefault()}}
				onChange={setQuery}
				value={query}
			/>

			{filteredFields.length ? (
				<ClayDropDown.ItemList>
					{filteredFields.map(({fieldName, label}) => (
						<ClayDropDown.Item
							key={fieldName}
							onClick={() => {
								const newVisibleFieldNames = {
									...selectedFieldNames,
									[fieldName]: !selectedFieldNames[fieldName],
								};

								const isVisible = Object.keys(
									newVisibleFieldNames
								).some(
									(visibleFieldName) =>
										newVisibleFieldNames[visibleFieldName]
								);

								if (isVisible) {
									viewsDispatch(
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
								}
							}}
							symbolLeft={
								selectedFieldNames[fieldName] ? 'check' : null
							}
						>
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
