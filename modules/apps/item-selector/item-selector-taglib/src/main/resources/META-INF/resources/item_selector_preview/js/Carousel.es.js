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

import React, {Component} from 'react';
import PropTypes from "prop-types";

import ImageSlide from "./ImageSlide.es";
import Arrow from "./Arrow.es";

class Carousel extends Component {
	static propTypes = {
		items: PropTypes.array.isRequired,
		startIndex: PropTypes.number,
		circular: PropTypes.bool
  	};

	constructor(props) {
		super(props);

		this.state = {
			currentIndex: this.props.startIndex
		};
  	}

	previousSlide = () => {
		const lastIndex = this.props.items.length - 1;
		const { currentIndex } = this.state;
		const shouldResetIndex = currentIndex === 0;
		const index = shouldResetIndex ? lastIndex : currentIndex - 1;

		this.setState({
			currentIndex: index
		});
	};

	nextSlide = () => {
		const lastIndex = this.props.items.length - 1;
		const { currentIndex } = this.state;
		const shouldResetIndex = currentIndex === lastIndex;
		const index = shouldResetIndex ? 0 : currentIndex + 1;

		this.setState({
			currentIndex: index
		});
	};

	render() {
		const items = this.props.items;

		const currentItem = items[this.state.currentIndex];

		console.log(currentItem.dataset.metadata)

		return (
			<div className="carousel">
				<Arrow
					direction="left"
					clickFunction={this.previousSlide}
					glyph="&#9664;"
				/>

				<ImageSlide
					url={currentItem.dataset.url}
				/>

				<Arrow
					direction="right"
					clickFunction={this.nextSlide}
					glyph="&#9654;"
				/>
			</div>
		);
	}
}
export default Carousel;