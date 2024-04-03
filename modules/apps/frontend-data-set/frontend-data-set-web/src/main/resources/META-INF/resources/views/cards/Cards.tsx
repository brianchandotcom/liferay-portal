/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithInfo} from '@clayui/card';
import ClayEmptyState from '@clayui/empty-state';
import classNames from 'classnames';
import React, {useContext, useRef} from 'react';

import FrontendDataSetContext, {
	IFrontendDataSetContext,
} from '../../FrontendDataSetContext';
import {IItemsActions} from '../../index';
import {getLocalizedValue} from '../../utils/getLocalizedValue';
import getRandomId from '../../utils/getRandomId';
import isLink from '../../utils/isLink';
import imagePropsTransformer from '../utils/imagePropsTransformer';

interface ICardSchema {
	description: string;
	image: string;
	link: string;
	sticker: string;
	symbol: string;
	title: string;
}

const Card = ({item, schema}: {item: any; schema: ICardSchema}) => {
	const {
		itemsActions,
		loadData,
		onActionDropdownItemClick,
		openSidePanel,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
	}: IFrontendDataSetContext = useContext(FrontendDataSetContext);

	const actionsRef = useRef(
		(itemsActions?.length && itemsActions) || item.actionDropdownItems
	);

	const cardSelected =
		selectable &&
		!!selectedItemsValue?.find(
			(element) => selectedItemsKey && element === item[selectedItemsKey]
		);
	const imageProps =
		schema.image && imagePropsTransformer(item[schema.image]);
	const localizedDescription = getLocalizedValue(item, schema.description)
		?.value;
	const localizedTitle = getLocalizedValue(item, schema.title)?.value || '';
	const selectedItemKey = selectedItemsKey && item[selectedItemsKey];

	return (
		<ClayCardWithInfo
			actions={actionsRef.current?.map((action: IItemsActions) => ({
				...action,
				href: isLink(action.target, null) ? action.href : null,
				onClick: (event: Event) => {
					if (onActionDropdownItemClick) {
						onActionDropdownItemClick({
							action,
							event,
							itemData: item,
							loadData,
							openSidePanel,
						});
					}
				},
			}))}
			description={localizedDescription}
			href={(schema.link && item[schema.link]) || null}
			imgProps={imageProps}
			onSelectChange={
				selectable
					? () => {
							selectItems(selectedItemKey);
					  }
					: undefined
			}
			selected={cardSelected}
			stickerProps={schema.sticker && item[schema.sticker] || null}
			symbol={schema.symbol && item[schema.symbol]}
			title={localizedTitle}
		/>
	);
};

const Cards = ({items, schema}: {items: Array<any>; schema: ICardSchema}) => {
	const {selectedItemsKey, style}: IFrontendDataSetContext = useContext(
		FrontendDataSetContext
	);

	return items?.length ? (
		<div
			className={classNames(
				'cards-container mb-n4',
				style === 'default' && 'px-3 pt-4'
			)}
		>
			<div className="row">
				{items.map((item) => {
					return (
						<div
							className="col-md-3"
							key={
								selectedItemsKey
									? item[selectedItemsKey]
									: getRandomId()
							}
						>
							<Card item={item} schema={schema} />
						</div>
					);
				})}
			</div>
		</div>
	) : (
		<ClayEmptyState
			description={Liferay.Language.get('sorry,-no-results-were-found')}
			imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
			title={Liferay.Language.get('no-results-found')}
		/>
	);
};

export default Cards;
