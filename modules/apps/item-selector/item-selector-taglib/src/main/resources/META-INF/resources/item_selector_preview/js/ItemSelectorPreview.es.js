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

import PropTypes from 'prop-types';
import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import Carousel from './Carousel.es';
import Footer from './Footer.es';
import Header from './Header.es';

const KEY_CODE = {
	ESC: 27,
	LEFT: 37,
	RIGTH: 39
};

const TPL_EDIT_DIALOG_TITLE = '{edit} {title} ({copy})';

class ItemSelectorPreview extends Component {
	static propTypes = {
		container: PropTypes.instanceOf(Element).isRequired,
		currentIndex: PropTypes.number.isRequired,
		editItemURL: PropTypes.string,
		handleSelectedItem: PropTypes.func.isRequired,
		headerTitle: PropTypes.string.isRequired,
		items: PropTypes.array.isRequired,
		uploadItemReturnType: PropTypes.string,
		uploadItemURL: PropTypes.string
	};

	constructor(props) {
		super(props);

		const {currentIndex, items} = props;
		const currentItem = items[currentIndex];

		this.state = {
			currentItem,
			currentItemIndex: currentIndex,
			items
		};

		this.infoButtonRef = React.createRef();
	}

	componentDidMount() {
		document.documentElement.addEventListener(
			'keydown',
			this.handleOnKeyDown.bind(this)
		);

		const sidenavToggle = this.infoButtonRef.current;

		if (sidenavToggle) {
			Liferay.SideNavigation.initialize(sidenavToggle, {
				container: '.sidenav-container',
				position: 'right',
				typeMobile: 'fixed',
				width: '320px'
			});
		}

		this._updateCurrentItemHandler = Liferay.on('updateCurrentItem', this.updateCurrentItem);
	}

	componentWillUnmount() {
		document.documentElement.removeEventListener(
			'keydown',
			this.handleOnKeyDown.bind(this)
		);

		Liferay.detach('updateCurrentItem', this._updateCurrentItemHandler);
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
		const {currentItem} = this.state;

		const itemTitle = currentItem.title;

		const editDialogTitle = Liferay.Util.sub(TPL_EDIT_DIALOG_TITLE, {
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
	};

	handleClickNext = () => {
		const {currentItemIndex, items} = this.state;

		const lastIndex = items.length - 1;
		const shouldResetIndex = currentItemIndex === lastIndex;
		const index = shouldResetIndex ? 0 : currentItemIndex + 1;

		const currentItem = items[index];

		this.setState({
			currentItem,
			currentItemIndex: index
		});
	};

	handleClickPrevious = () => {
		const {currentItemIndex, items} = this.state;

		const lastIndex = items.length - 1;
		const shouldResetIndex = currentItemIndex === 0;
		const index = shouldResetIndex ? lastIndex : currentItemIndex - 1;

		const currentItem = items[index];

		this.setState({
			currentItem,
			currentItemIndex: index
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
			default:
				break;
		}
	};

	handleSaveEdit = e => {
		const {items} = this.state;
		const itemData = e.data.file;

		const editedItemMetadata = {
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

		const editedItem = {
			metadata: editedItemMetadata,
			returnType: this.props.uploadItemReturnType,
			url: itemData.url,
			value: itemData.resolvedValue
		};

		items.push(editedItem);

		this.setState({
			currentItem: editedItem,
			currentItemIndex: items.length - 1,
			items
		});
	};

	updateCurrentItem = ({url, value}) => {
		this.setState({currentItem: {...this.state.currentItem, url, value}});
	}

	render() {
		const {currentItem, currentItemIndex, items} = this.state;

		return (
			<div className="fullscreen item-selector-preview">
				<Header
					handleClickClose={this.handleClickClose}
					handleClickDone={this.handleClickDone}
					handleClickEdit={this.handleClickEdit}
					headerTitle={this.props.headerTitle}
					infoButtonRef={this.infoButtonRef}
					showEditIcon={this.props.editItemURL}
					showInfoIcon={currentItem.metadata}
				/>

				<Carousel
					currentItem={currentItem}
					handleClickNext={this.handleClickNext}
					handleClickPrevious={this.handleClickPrevious}
					showArrows={items.length > 1}
				/>

				<Footer
					currentIndex={currentItemIndex}
					title={currentItem.title}
					totalItems={items.length}
				/>
			</div>
		);
	}
}

export default ItemSelectorPreview;
