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
import InfoPanel from "./InfoPanel.es";
import Arrow from "./Arrow.es";

class Carousel extends Component {
	static propTypes = {
		currentItemIndex: PropTypes.number,
		items: PropTypes.array.isRequired,
		onItemChange: PropTypes.func,
  	};

  	static defaultProps = {
  		onItemChange: () => {}
  	};

	constructor(props) {
		super(props);

		const currentIndex = this.props.currentItemIndex;

		this.state = {
			currentIndex: currentIndex,
			currentItem: this.props.items[currentIndex]
		};
  	};

	previousSlide = () => {
		const items = this.props.items;

		const lastIndex = items.length - 1;
		const { currentIndex } = this.state;
		const shouldResetIndex = currentIndex === 0;
		const index = shouldResetIndex ? lastIndex : currentIndex - 1;

		const currentItem = items[index];

		this.setState({
			currentIndex: index,
			currentItem: currentItem
		});

		this.props.onItemChange(currentItem, index);
	};

	nextSlide = () => {
		const items = this.props.items;

		const lastIndex = items.length - 1;
		const { currentIndex } = this.state;
		const shouldResetIndex = currentIndex === lastIndex;
		const index = shouldResetIndex ? 0 : currentIndex + 1;

		const currentItem = items[index];

		this.setState({
			currentIndex: index,
			currentItem: currentItem
		});

		this.props.onItemChange(currentItem, index);
	};

	render() {
		const {currentItem} = this.state;
		const showArrows = this.props.items.length > 1;
		console.log(currentItem.dataset.metadata);

		return (
			<div className="carousel">

				{showArrows && (
					<Arrow
						direction="left"
						clickFunction={this.previousSlide}
					/>
				)}

				<ImageSlide
					url={currentItem.dataset.url}
				/>

				{showArrows && (
					<Arrow
						direction="right"
						clickFunction={this.nextSlide}
					/>
				)}

				<InfoPanel imageData={currentItem.dataset.metadata} />
			</div>
		);
	}
}
export default Carousel;