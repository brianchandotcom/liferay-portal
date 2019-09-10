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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {Component} from 'react';
import PropTypes from "prop-types";

import InfoPanel from "./InfoPanel.es";

const Arrow = ({ direction, handleClick }) => (
	<div className={`pull-${direction}`}>
		<ClayButton
			borderless
			className="icon-arrow"
			displayType="secondary"
			monospaced
			onClick={handleClick}
			size="lg"
		>
			<ClayIcon symbol={`angle-${direction}`} />
		</ClayButton>
	</div>
);

const ImageSlide = ({ url }) => {
	const styles = {
		cursor: "pointer",
		maxHeight: `100%`
	};

	return <img alt="alt" src={url} style={styles} />;
};

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

	showNextSlide = () => {
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

	showPreviousSlide = () => {
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

	render() {
		const {currentItem} = this.state;
		const showArrows = this.props.items.length > 1;

		return (
			<div className="carousel">

				{showArrows && (
					<Arrow
						direction="left"
						handleClick={this.showPreviousSlide}
					/>
				)}

				<ImageSlide
					url={currentItem.dataset.url}
				/>

				{showArrows && (
					<Arrow
						direction="right"
						handleClick={this.showNextSlide}
					/>
				)}

				<InfoPanel imageData={currentItem.dataset.metadata} />
			</div>
		);
	}
}
export default Carousel;