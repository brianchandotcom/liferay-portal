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

import {ClayIconSpriteContext} from '@clayui/icon';
import dom from 'metal-dom';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import Header from './Header.es';
import Carousel from './Carousel.es';
import Footer from './Footer.es';

const KEY_CODE = {
	ESC: 27,
	LEFT: 37,
	RIGTH: 39
};

const TPL_EDIT_DIALOG_TITLE = '{edit} {title} ({copy})';

class ItemSelectorPreview extends Component {
	static propTypes = {
		container: PropTypes.node,
		currentIndex: PropTypes.number.isRequired,
		editItemURL: PropTypes.string.isRequired,
		handleSelectedItem: PropTypes.func.isRequired,
		headerTitle: PropTypes.string.isRequired,
		items: PropTypes.array.isRequired,
		uploadItemURL: PropTypes.string.isRequired,
		uploadItemReturnType: PropTypes.string.isRequired
	};

	constructor(props) {
		super(props);

		const {currentIndex, items} = props;
		const currentItem = items[currentIndex];

		this.state = {
			currentItem: currentItem,
			currentItemIndex: currentIndex,
			items: items
		}
	}

	componentDidMount() {
		document.documentElement.addEventListener("keydown", this.handleOnKeyDown.bind(this));
	}

	componentWillUnmount() {
		document.documentElement.removeEventListener("keydown", this.handleOnKeyDown.bind(this));
	}

	componentDidUpdate() {
		console.log('componentDidUpdate');
		console.log(this.refs);

		const sidenavToggle = this.refs.test;

		if (sidenavToggle) {
			Liferay.SideNavigation.initialize(sidenavToggle, {
				container: '.sidenav-container',
				position: 'right',
				type: 'relative',
				typeMobile: 'fixed',
				width: '320px'
			});
		}
	}

	close = () => {
		ReactDOM.unmountComponentAtNode(this.props.container);
	};

	handleClickClose = () => {
		this.close();
	};

	handleClickDone = () => {
		const selectedItem = this.state.currentItem;

		this.props.handleSelectedItem(selectedItem);

		this.close();
	};

	handleClickEdit = () => {
		const { currentItem } = this.state;

		const itemTitle = currentItem.title;

		let editDialogTitle = Liferay.Util.sub(TPL_EDIT_DIALOG_TITLE, {
			copy: Liferay.Language.get('copy'),
			edit: Liferay.Language.get('edit'),
			title: itemTitle
		});

		let editEntityBaseZIndex = Liferay.zIndex.WINDOW;

		const iframeModalEl = window.parent.document.getElementsByClassName(
			'dialog-iframe-modal'
		);

		if (iframeModalEl) {
			editEntityBaseZIndex = window
				.getComputedStyle(iframeModalEl[0])
				.getPropertyValue('z-index');
		}

		Liferay.Util.editEntity(
			{
				dialog: {
					destroyOnHide: true,
					zIndex: editEntityBaseZIndex + 100
				},
				id: 'Edit_' + itemTitle,
				stack: false,
				title: editDialogTitle,
				uri: this.props.editItemURL,
				urlParams: {
					entityURL: currentItem.url,
					saveFileName: itemTitle,
					saveParamName: 'imageSelectorFileName',
					saveURL: this.props.uploadItemURL
				}
			},
			this.handleSaveEdit.bind(this)
		);
	}

	handleClickNext = () => {
		const {currentItemIndex, items } = this.state;

		const lastIndex = items.length - 1;
		const shouldResetIndex = currentItemIndex === lastIndex;
		const index = shouldResetIndex ? 0 : currentItemIndex + 1;

		const currentItem = items[index];

		this.setState({
			currentItemIndex: index,
			currentItem: currentItem
		});
	};

	handleClickPrevious = () => {
		const {currentItemIndex, items } = this.state;

		const lastIndex = items.length - 1;
		const shouldResetIndex = currentItemIndex === 0;
		const index = shouldResetIndex ? lastIndex : currentItemIndex - 1;

		const currentItem = items[index];

		this.setState({
			currentItemIndex: index,
			currentItem: currentItem
		});
	};

	handleOnKeyDown = e => {
		switch (e.which || e.keyCode) {
			case KEY_CODE.LEFT:
				this.handleClickPrevious();
				break;
			case KEY_CODE.RIGTH:
				this.handleClickNext();
				break;
			case KEY_CODE.ESC:
				e.preventDefault();
				e.stopPropagation();
				this.close();
				break;
		}
	};

	handleSaveEdit = e => {
		let { items }  = this.state;
		let itemData = e.data.file;

		let editedItemMetadata = {
			groups: [
				{
					data: [
						{
							key: Liferay.Language.get('format'),
							value: itemData.type
						},
						{
							key: Liferay.Language.get('name'),
							value: itemData.title
						}
					],
					title: Liferay.Language.get('file-info')
				}
			]
		};

		let editedItem = {
			metadata: editedItemMetadata,
			returnType: this.props.uploadItemReturnType,
			value: itemData.resolvedValue,
			url: itemData.url
		}

		items.push(editedItem);

		this.setState({
			currentItem: editedItem,
			currentItemIndex: items.length - 1,
			items: items
		});
	}

	render() {
		const {currentItemIndex, currentItem, items} = this.state;

		const spritemap =
			Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

		return (
			<div className="fullscreen item-selector-preview">
				<Header
					handleClickClose={this.handleClickClose}
					handleClickDone={this.handleClickDone}
					handleClickEdit={this.handleClickEdit}
					headerTitle={this.props.headerTitle}
				/>

				<Carousel
					currentItem = {currentItem}
					handleClickNext = {this.handleClickNext}
					handleClickPrevious = {this.handleClickPrevious}
					showArrows = {items.length > 1}
				/>

				<Footer
					title={currentItem.title}
					currentIndex={currentItemIndex}
					totalItems={items.length}
				/>
			</div>
		);
	}
}

export default ItemSelectorPreview;
