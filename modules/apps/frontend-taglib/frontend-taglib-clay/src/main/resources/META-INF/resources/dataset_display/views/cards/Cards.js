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

function Cards({datasetDisplayContext, items, schema}) {
	const {
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		style,
	} = useContext(datasetDisplayContext);

	return (
		<div
			className={classNames(
				'cards-container mb-n4',
				style === 'default' && 'px-3 pt-4'
			)}
		>
			<div className="row">
				{items.map((item) => {
					return (
						<div className="col-md-3" key={item[selectedItemsKey]}>
							<ClayCardWithInfo
								actions={item.actionItems}
								description={
									schema.description &&
									item[schema.description]
								}
								href={schema.href && item[schema.href]}
								imgProps={
									schema.imgProps && item[schema.imgProps]
								}
								onSelectChange={
									selectable &&
									(() => selectItems(item[selectedItemsKey]))
								}
								selected={
									selectable &&
									!!selectedItemsValue.find(
										(element) =>
											element === item[selectedItemsKey]
									)
								}
								stickerProps={
									schema.stickerProps &&
									item[schema.stickerProps]
								}
								symbol={schema.symbol && item[schema.symbol]}
								title={schema.title && item[schema.title]}
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
