/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {CP_INSTANCE_CHANGED} from '../../utilities/eventsDefinitions';
import MainImage from './MainImage';
import Overlay from './Overlay';
import Thumbnails from './Thumbnails';
import {fetchImage, updateGallery} from './util/index';

export default class Gallery extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			fullscreen: false,
			images: this.props.images,
			loaded: new Set(),
			loading: false,
			selected: 0,
		};

		this.fullscreenClose = this.fullscreenClose.bind(this);
		this.fullscreenOpen = this.fullscreenOpen.bind(this);
		this.goTo = this.goTo.bind(this);
		this.goToNext = this.goToNext.bind(this);
		this.goToPrev = this.goToPrev.bind(this);
		this.imageLoad = this.imageLoad.bind(this);
		this.imageSelect = this.imageSelect.bind(this);
		this._handleImagesUpdate = this._handleImagesUpdate.bind(this);
	}

	componentDidMount() {
		Liferay.on(
			`${this.props.namespace}${CP_INSTANCE_CHANGED}`,
			this._handleImagesUpdate,
			this
		);
	}

	componentWillUnmount() {
		Liferay.detach(
			`${this.props.namespace}${CP_INSTANCE_CHANGED}`,
			this._handleImagesUpdate,
			this
		);
	}

	_handleImagesUpdate({cpInstance}) {
		updateGallery(
			cpInstance.skuOptions,
			this.props.viewCPAttachmentURL
		).then((selectedImage) => {
			const selectedImageIndex =
				this.state.images.length > 1
					? this.state.images.findIndex(
							({URL}) => URL === selectedImage[0].url
					  )
					: 0;

			const selected = selectedImageIndex >= 0 ? selectedImageIndex : 0;

			this.setState({selected});
		});
	}

	fullscreenOpen() {
		if (!this.state.loading) {
			this.imageLoad(this.state.images[this.state.selected].URL).then(
				() => {
					this.setState({fullscreen: true});
				}
			);
		}
	}

	fullscreenClose() {
		this.setState({fullscreen: false});
	}

	goTo(pos) {
		this.imageSelect(
			(this.state.images.length + pos) % this.state.images.length
		);
	}

	goToPrev(event) {
		event.stopPropagation();
		this.goTo(this.state.selected - 1);
	}

	goToNext(event) {
		event.stopPropagation();
		this.goTo(this.state.selected + 1);
	}

	imageLoad(imageUrl) {
		return new Promise((resolve) => {
			if (this.state.loaded.has(imageUrl)) {
				resolve(imageUrl);
			}
			else {
				this.setState({loading: true});
				fetchImage(imageUrl).then(() => {
					this.setState(
						{
							loaded: new Set(this.state.loaded).add(imageUrl),
							loading: false,
						},
						() => {
							resolve(imageUrl);
						}
					);
				});
			}
		});
	}

	imageSelect(toSelect) {
		if (toSelect !== this.state.selected && !this.state.loading) {
			this.imageLoad(this.state.images[toSelect].URL).then(() => {
				this.setState({selected: toSelect});
			});
		}
	}

	render() {
		const {background} = this.props;
		const {fullscreen, images, loading, selected} = this.state;

		return (
			<div className="product-gallery">
				{images && !!images.length && (
					<MainImage
						adaptiveMediaImageHTMLTag={
							images[selected].adaptiveMediaImageHTMLTag
						}
						background={background}
						loading={loading}
						onNext={images.length > 1 ? this.goToNext : null}
						onPrev={images.length > 1 ? this.goToPrev : null}
						onZoom={this.fullscreenOpen}
						src={images[selected].URL}
						title={images[selected].title}
					/>
				)}

				{images.length > 1 ? (
					<Thumbnails
						background={background}
						images={images}
						onChange={this.imageSelect}
						selected={selected}
					/>
				) : null}

				{fullscreen ? (
					<Overlay
						adaptiveMediaImageHTMLTag={
							images[selected].adaptiveMediaImageHTMLTag
						}
						background={background}
						onClose={this.fullscreenClose}
						onNext={images.length > 1 ? this.goToNext : null}
						onPrev={images.length > 1 ? this.goToPrev : null}
						src={images[selected].URL}
						title={images[selected].title}
					/>
				) : null}
			</div>
		);
	}
}

Gallery.propTypes = {
	background: PropTypes.string,
	images: PropTypes.arrayOf(
		PropTypes.shape({
			URL: PropTypes.string.isRequired,
			thumbnailURL: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
		})
	),
	namespace: PropTypes.string,
	viewCPAttachmentURL: PropTypes.string.isRequired,
};
