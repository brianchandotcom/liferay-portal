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

class ItemSelectorPreview extends Component {
	static propTypes = {
		container: PropTypes.node,
		currentIndex: PropTypes.number.isRequired,
		handleSelectedItem: PropTypes.func.isRequired,
		headerTitle: PropTypes.string.isRequired,
		items: PropTypes.array.isRequired,
	};

	constructor(props) {
		super(props);

		const {currentIndex, items} = props;
		const currentItem = items[currentIndex];

		this.state = {
			currentItem: currentItem,
			currentItemIndex: currentIndex
		}
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

	handleClickNext = () => {
		const items = this.props.items;

		const lastIndex = items.length - 1;
		const { currentItemIndex } = this.state;
		const shouldResetIndex = currentItemIndex === lastIndex;
		const index = shouldResetIndex ? 0 : currentItemIndex + 1;

		const currentItem = items[index];

		this.setState({
			currentItemIndex: index,
			currentItem: currentItem
		});
	};

	handleClickPrevious = () => {
		const items = this.props.items;

		const lastIndex = items.length - 1;
		const { currentItemIndex } = this.state;
		const shouldResetIndex = currentItemIndex === 0;
		const index = shouldResetIndex ? lastIndex : currentItemIndex - 1;

		const currentItem = items[index];

		this.setState({
			currentItemIndex: index,
			currentItem: currentItem
		});
	};

	render() {
		const {currentItemIndex, currentItem} = this.state;
		const {items} = this.props;

		const spritemap =
			Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

		return (
			<div className="fullscreen item-selector-preview">
				<ClayIconSpriteContext.Provider value={spritemap}>
					<Header
						handleClickClose={this.handleClickClose}
						handleClickDone={this.handleClickDone}
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
				</ClayIconSpriteContext.Provider>
			</div>
		);
	}
}

export default ItemSelectorPreview;
