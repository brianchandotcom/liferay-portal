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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import {LIST_ITEM_TYPES} from '../../../app/config/constants/listItemTypes';
import {config} from '../../../app/config/index';
import {useSessionState} from '../../../common/hooks/useSessionState';
import useKeyboardNavigation from '../hooks/useKeyboardNavigation';
import TabItem from './TabItem';

const HIGHLIGHTED_COLLECTION_ID = 'highlighted';

export default function TabCollection({
	collection,
	displayStyle,
	initialOpen,
	isSearchResult,
}) {
	const [open, setOpen] = useSessionState(
		`${config.portletNamespace}_fragment-collection_${collection.collectionId}_open`,
		initialOpen
	);

	const onRemoveHighlighted = (itemIndex) => {
		if (collection.collectionId !== HIGHLIGHTED_COLLECTION_ID) {
			return;
		}

		const items = collapseRef.current?.querySelectorAll('li');

		if (items.length === 1) {
			const nextCollapse = collapseRef.current.nextSibling;
			const nextHeader = nextCollapse.querySelector('button');

			nextHeader.focus();
		}

		const nextIndex = itemIndex === 0 ? 1 : itemIndex - 1;

		items[nextIndex]?.focus();
	};

	const collapseRef = useRef();

	return (
		<TabCollectionCollapse
			collapseRef={collapseRef}
			isSearchResult={isSearchResult}
			open={open}
			setOpen={setOpen}
			title={collection.label}
		>
			{collection.collections &&
				collection.collections.map((collection, index) => (
					<TabCollection
						collection={collection}
						displayStyle={displayStyle}
						initialOpen={false}
						isSearchResult={isSearchResult}
						key={index}
					/>
				))}

			<ul
				aria-orientation="vertical"
				className={`list-unstyled page-editor__fragments-widgets__tab-collection-${displayStyle} pb-2 w-100`}
				role="menu"
			>
				{collection.children.map((item, index) => (
					<React.Fragment key={item.itemId}>
						<TabItem
							displayStyle={displayStyle}
							item={item}
							key={item.itemId}
							onRemoveHighlighted={() =>
								onRemoveHighlighted(index)
							}
						/>

						{item.portletItems?.length && (
							<TabPortletItems item={item} />
						)}
					</React.Fragment>
				))}
			</ul>
		</TabCollectionCollapse>
	);
}

TabCollection.proptypes = {
	collection: PropTypes.shape({
		children: PropTypes.array,
		collectionId: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
	displayStyle: PropTypes.oneOf(Object.values(FRAGMENTS_DISPLAY_STYLES)),
	isSearchResult: PropTypes.bool,
	open: PropTypes.bool,
};

const TabPortletItems = ({item}) => (
	<ul className="d-flex flex-wrap list-unstyled w-100">
		{item.portletItems.map((portlet, index) => (
			<TabItem item={portlet} key={index} />
		))}
	</ul>
);

TabPortletItems.proptypes = {
	data: PropTypes.shape({
		instanceable: PropTypes.bool,
		portletId: PropTypes.string,
		portletItemId: PropTypes.string,
		used: PropTypes.bool,
	}).isRequired,
	disabled: PropTypes.bool,
	icon: PropTypes.string,
	itemId: PropTypes.string,
	label: PropTypes.string,
	portletItems: PropTypes.array,
	preview: PropTypes.string,
	type: PropTypes.string,
};

function TabCollectionCollapse({
	children,
	collapseRef,
	isSearchResult,
	open,
	setOpen,
	title,
}) {
	const [searchOpen, setSearchOpen] = useState(true);

	const isOpen = isSearchResult ? searchOpen : open;
	const setIsOpen = isSearchResult ? setSearchOpen : setOpen;

	const {isTarget, setElement} = useKeyboardNavigation({
		handleOpen: setIsOpen,
		type: LIST_ITEM_TYPES.header,
	});

	return (
		<li
			className="page-editor__collapse panel-group panel-group-flush"
			ref={collapseRef}
			role="none"
		>
			<button
				aria-expanded={isOpen ? 'true' : 'false'}
				aria-haspopup="menu"
				className={classNames(
					'btn',
					'btn-unstyled',
					'collapse-icon',
					'sheet-subtitle',
					{
						collapsed: !isOpen,
					}
				)}
				onClick={() => setIsOpen(!isOpen)}
				ref={setElement}
				role="menuitem"
				tabIndex={isTarget ? 0 : -1}
				type="button"
			>
				<span className="c-inner text-truncate" tabIndex={-1}>
					{title}

					<span
						className={`text-secondary collapse-icon-${
							isOpen ? 'open' : 'closed'
						}`}
					>
						<ClayIcon
							symbol={isOpen ? 'angle-down' : 'angle-right'}
						/>
					</span>
				</span>
			</button>

			{isOpen && children}
		</li>
	);
}

TabCollectionCollapse.proptypes = {
	open: PropTypes.bool.isRequired,
	setOpen: PropTypes.func.isRequired,
	title: PropTypes.string.isRequired,
};
