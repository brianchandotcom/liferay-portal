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

import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ImageRenderer from '../../../data_renderers/ImageRenderer';

function List(props) {
	const {
		selectItems,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
	} = useContext(props.datasetDisplayContext);

	return (
		<ClayList>
			{props.items.map((item, i) => (
				<ClayList.Item
					className={classNames(
						i
							? 'border-left-0 border-bottom-0 border-right-0'
							: 'border-0'
					)}
					flex
					key={item.id}
				>
					<ClayList.ItemField className="justify-content-center">
						{selectionType === 'single' ? (
							<ClayRadio
								checked={selectedItemsValue
									.map((el) => String(el))
									.includes(String(item[selectedItemsKey]))}
								onChange={() =>
									selectItems(item[selectedItemsKey])
								}
							/>
						) : (
							<ClayCheckbox
								checked={selectedItemsValue
									.map((el) => String(el))
									.includes(String(item[selectedItemsKey]))}
								onChange={() =>
									selectItems(item[selectedItemsKey])
								}
							/>
						)}
					</ClayList.ItemField>
					{props.schema.thumbnail && item[props.schema.thumbnail] && (
						<ClayList.ItemField>
							<ImageRenderer
								value={item[props.schema.thumbnail]}
							/>
						</ClayList.ItemField>
					)}
					<ClayList.ItemField
						className="justify-content-center"
						expand
					>
						{props.schema.title && (
							<ClayList.ItemTitle>
								{item[props.schema.title]}
							</ClayList.ItemTitle>
						)}
						{props.schema.description && (
							<ClayList.ItemText>
								{item[props.schema.description]}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>
				</ClayList.Item>
			))}
		</ClayList>
	);
}

List.propTypes = {
	context: PropTypes.any,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
				.isRequired,
		})
	),
	schema: PropTypes.shape({
		description: PropTypes.string,
		selectedItemValue: PropTypes.string,
		thumbnail: PropTypes.string,
		title: PropTypes.string,
	}),
};

List.defaultTypes = {
	activeItemValue: '',
};

export default List;
