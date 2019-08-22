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

import React from "react";
import PropTypes from "prop-types";

import ImageSlide from "./ImageSlide.es";
import Arrow from "./Arrow.es";

class Carousel extends React.Component {
	static propTypes = {
		views: PropTypes.array.isRequired,
		circular: PropTypes.bool
  	};

	constructor(props) {
		super(props);

		this.state = {
			currentItemIndex: 0
		};
  	}

	previousSlide = () => {
		const lastIndex = this.props.views.length - 1;
		const { currentItemIndex } = this.state;
		const shouldResetIndex = currentItemIndex === 0;
		const index = shouldResetIndex ? lastIndex : currentItemIndex - 1;

		this.setState({
			currentItemIndex: index
		});
	};

	nextSlide = () => {
		const lastIndex = this.props.views.length - 1;
		const { currentItemIndex } = this.state;
		const shouldResetIndex = currentItemIndex === lastIndex;
		const index = shouldResetIndex ? 0 : currentItemIndex + 1;

		this.setState({
			currentItemIndex: index
		});
	};

	render() {
		const views = this.props.views;

		const currentItem = views[this.state.currentItemIndex];

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
