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

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';
import ReactDOM from 'react-dom';

import Carousel from './Carousel.es';
import Footer from './Footer.es';
import Header from './Header.es';

const KEY_CODE = {
	ESC: 27,
	LEFT: 37,
	RIGTH: 39,
};

const ItemSelectorPreview = ({
	container,
	currentIndex = 0,
	handleSelectedItem,
	headerTitle,
	items,
}) => {
	const [currentItemIndex, setCurrentItemIndex] = useState(currentIndex);
	const [itemList, setItemList] = useState(items);
	const [reloadOnHide, setReloadOnHide] = useState(false);

	const infoButtonRef = React.createRef();

	const isMounted = useIsMounted();

	const close = useCallback(() => {
		ReactDOM.unmountComponentAtNode(container);
	}, [container]);

	const handleClickNext = useCallback(() => {
		if (itemList.length > 1) {
			setCurrentItemIndex((index) => {
				const lastIndex = itemList.length - 1;
				const shouldResetIndex = index === lastIndex;

				return shouldResetIndex ? 0 : index + 1;
			});
		}
	}, [itemList.length]);

	const handleClickPrevious = useCallback(() => {
		if (itemList.length > 1) {
			setCurrentItemIndex((index) => {
				const lastIndex = itemList.length - 1;
				const shouldResetIndex = index === 0;

				return shouldResetIndex ? lastIndex : index - 1;
			});
		}
	}, [itemList.length]);

	const handleOnKeyDown = useCallback(
		(e) => {
			if (!isMounted()) {
				return;
			}

			switch (e.which || e.keyCode) {
				case KEY_CODE.LEFT:
					handleClickPrevious();
					break;
				case KEY_CODE.RIGTH:
					handleClickNext();
					break;
				case KEY_CODE.ESC:
					e.preventDefault();
					e.stopPropagation();
					close();
					break;
				default:
					break;
			}
		},
		[close, handleClickNext, handleClickPrevious, isMounted]
	);

	const currentItem = itemList[currentItemIndex];

	const updateItemList = (newItemList) => {
		setItemList(newItemList);
		setReloadOnHide(true);
	};

	const updateCurrentItem = useCallback(
		({url, value}) => {
			if (isMounted()) {
				const newItemList = [...itemList];

				newItemList[currentItemIndex] = {...currentItem, url, value};

				updateItemList(newItemList);
			}
		},
		[currentItem, currentItemIndex, isMounted, itemList]
	);

	useEffect(() => {
		document.documentElement.addEventListener('keydown', handleOnKeyDown);

		const updateCurrentItemHandler = Liferay.on(
			'updateCurrentItem',
			updateCurrentItem
		);

		Liferay.component('ItemSelectorPreview', ItemSelectorPreview);

		return () => {
			document.documentElement.removeEventListener(
				'keydown',
				handleOnKeyDown
			);

			Liferay.detach(updateCurrentItemHandler);
			Liferay.component('ItemSelectorPreview', null);
		};
	}, [handleOnKeyDown, updateCurrentItem]);

	useEffect(() => {
		const sidenavToggle = infoButtonRef.current;

		if (sidenavToggle) {
			Liferay.SideNavigation.initialize(sidenavToggle, {
				container: '.sidenav-container',
				position: 'right',
				typeMobile: 'fixed',
				width: '320px',
			});
		}
	}, [infoButtonRef]);

	const handleClickBack = () => {
		close();

		if (reloadOnHide) {
			const frame = window.frameElement;

			if (frame) {
				frame.contentWindow.location.reload();
			}
		}
	};

	const handleClickDone = () => {

		// LPS-120692

		close();

		handleSelectedItem(currentItem);
	};

	return (
		<div className="fullscreen item-selector-preview">
			<Header
				disabledAddButton={!currentItem.url}
				handleClickAdd={handleClickDone}
				handleClickBack={handleClickBack}
				headerTitle={headerTitle}
				infoButtonRef={infoButtonRef}
				showInfoIcon={!!currentItem.metadata}
			/>

			<Carousel
				currentItem={currentItem}
				handleClickNext={handleClickNext}
				handleClickPrevious={handleClickPrevious}
				showArrows={itemList.length > 1}
			/>

			<Footer
				currentIndex={currentItemIndex}
				title={currentItem.title}
				totalItems={itemList.length}
			/>
		</div>
	);
};

ItemSelectorPreview.propTypes = {
	container: PropTypes.instanceOf(Element).isRequired,
	currentIndex: PropTypes.number,
	handleSelectedItem: PropTypes.func.isRequired,
	headerTitle: PropTypes.string.isRequired,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			base64: PropTypes.string,
			metadata: PropTypes.string,
			returntype: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
			url: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
		})
	).isRequired,
};

export default ItemSelectorPreview;
