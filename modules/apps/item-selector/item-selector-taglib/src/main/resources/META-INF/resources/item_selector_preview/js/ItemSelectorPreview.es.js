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

import dom from 'metal-dom';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import Header from './Header.es';
import Carousel from './Carousel.es';

class ItemSelectorPreview extends Component {
	static propTypes = {
		links: PropTypes.string,
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

	render() {
		const {openViewer, selectedItemIndex, items} = this.state;

		return (
			<div className="item-selector-preview">
				{openViewer && (
					<>
						<Header headerTitle="Images" />
						<Carousel
							items={items}
							startIndex={selectedItemIndex}
							circular={true}
						/>
					</>
				)}
			</div>
		);
	}
}

export default ItemSelectorPreview;
