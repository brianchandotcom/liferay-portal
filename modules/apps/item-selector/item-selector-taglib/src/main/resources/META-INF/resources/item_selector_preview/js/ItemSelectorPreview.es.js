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

import Header from './Header.es';
import Carousel from './Carousel.es';

class ItemSelectorPreview extends Component {
	static propTypes = {
		headerTitle: PropTypes.string.isRequired,
		links: PropTypes.string.isRequired,
		selector: PropTypes.string
	};

	constructor(props) {
		super(props);

		let items = Array.from(document.querySelectorAll(this.props.links));

		this.state = {
			openViewer: false,
			items: items,
			selectedItemIndex: 0
		};

		items.forEach((item, index) => {
			//TODO tener en cuenta el "selector"
			item.addEventListener('click', e => {
				this.setState({
					openViewer: true,
					selectedItemIndex: index
				});
			});
		});
	}

	handleAdd = () => {
		//TODO
		this.setState({openViewer: false});
	};

	handleClose = () => {
		this.setState({openViewer: false});
	};

	render() {
		const {openViewer, selectedItemIndex, items} = this.state;

		const spritemap =
			Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

		return (
			<div className="item-selector-preview">
				{openViewer && (
					<>
						<ClayIconSpriteContext.Provider value={spritemap}>
							<Header
								handleAdd={this.handleAdd}
								handleClose={this.handleClose}
								headerTitle={this.props.headerTitle}
							/>
							<Carousel
								items={items}
								startIndex={selectedItemIndex}
								circular={true}
							/>
						</ClayIconSpriteContext.Provider>
					</>
				)}
			</div>
		);
	}
}

export default ItemSelectorPreview;
