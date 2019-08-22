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

import Header from "./Header.es";
import Carousel from "./Carousel.es";

class ItemSelectorPreview extends Component {
	static propTypes = {
		links: PropTypes.string,
		selector: PropTypes.string
	}

	constructor(props) {
		super(props);

		this.state = {
			openViewer: false,
			views: Array.from(document.querySelectorAll(this.props.links))
		};

		console.log(this.state.views);

		setTimeout(() => {
			this.setState({ openViewer: true });
		}, 3000);
	}

	render() {
		const openViewer = this.state.openViewer;
		const views = this.state.views;

		return (
			<div className="item-selector-preview">
				{openViewer && (
					<>
						<Header headerTitle="Images" />
						<Carousel views={views} circular={true} />
					</>
				)}
			</div>
		);
	}
}

export default ItemSelectorPreview;
