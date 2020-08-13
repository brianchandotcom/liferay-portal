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

import {
	handleAction,
	isLink,
} from '../../data_renderers/ActionsDropdownRenderer';

function Cards({dataSetDisplayContext, items, schema}) {
	const {
		executeAsyncItemAction,
		highlightItems,
		openModal,
		openSidePanel,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		style,
	} = useContext(dataSetDisplayContext);

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
								actions={item.actionDropdownItems.map(
									({href, ...action}) => ({
										...action,
										href: isLink(
											action.target,
											action.onClick
										)
											? href
											: null,
										onClick: (event) => {
											handleAction(
												{
													event,
													itemId:
														item[selectedItemsKey],
													method: action.data?.method,
													url: href,
													...action,
												},
												{
													executeAsyncItemAction,
													highlightItems,
													openModal,
													openSidePanel,
												}
											);
										},
									})
								)}
								description={
									schema.description &&
									item[schema.description]
								}
								href={
									(schema.href && item[schema.href]) || null
								}
								imgProps={schema.image && item[schema.image]}
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
									schema.sticker && item[schema.sticker]
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
