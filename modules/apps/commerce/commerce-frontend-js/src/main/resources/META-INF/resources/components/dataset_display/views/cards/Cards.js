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

import {ClayCardWithInfo} from '@clayui/card';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

function Cards(props) {
	const {
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		style,
	} = useContext(props.datasetDisplayContext);

	return (
		<div
			className={classNames(
				'cards-container mb-n4',
				style === 'default' && 'px-3 pt-4'
			)}
		>
			<div className="row">
				{props.items.map((item) => {
					return (
						<div className="col-md-3" key={item[selectedItemsKey]}>
							<ClayCardWithInfo
								actions={item.actionItems}
								description={
									props.schema.description &&
									item[props.schema.description]
								}
								href={
									props.schema.href && item[props.schema.href]
								}
								imgProps={
									props.schema.imgProps &&
									item[props.schema.imgProps]
								}
								onSelectChange={
									selectable &&
									(() => selectItems(item[selectedItemsKey]))
								}
								selected={
									selectable &&
									!!selectedItemsValue.find(
										(el) => el === item[selectedItemsKey]
									)
								}
								stickerProps={
									props.schema.stickerProps &&
									item[props.schema.stickerProps]
								}
								symbol={
									props.schema.symbol &&
									item[props.schema.symbol]
								}
								title={
									props.schema.title &&
									item[props.schema.title]
								}
							/>
						</div>
					);
				})}
			</div>
		</div>
	);
}

Cards.propTypes = {
	items: PropTypes.array,
	schema: PropTypes.shape({
		description: PropTypes.string,
		href: PropTypes.string,
		imgProps: PropTypes.imgProps,
		labels: PropTypes.arrayOf(PropTypes.string),
		stickerProps: PropTypes.string,
		title: PropTypes.string,
	}).isRequired,
};

export default Cards;
