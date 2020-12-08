/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useEffect, useRef, useState} from 'react';

import {StoreContext} from '../StoreContext.es';

export function Resume(props) {
	const [orientationModifier, setOrientationModifier] = useState(null);

	useEffect(() => {
		if (!orientationModifier) {
			const verticalOrientation =
				props.position.y > 50 ? 'bottom' : 'top';
			const horizontalOrientation =
				props.position.x > 50 ? 'right' : 'left';

			setOrientationModifier(
				` part-detail__resume--${verticalOrientation}-${horizontalOrientation}`
			);
		}
	}, [orientationModifier, props.position.y, props.position.x]);

	return (
		<div
			className={`part-detail__resume detail-resume detail-resume--product${
				orientationModifier || ''
			}`}
		>
			{props.thumbnailUrl && (
				<div
					className="detail-resume__thumbnail"
					style={{backgroundImage: `url(${props.thumbnailUrl})`}}
				/>
			)}
			<div className="detail-resume__info">
				<div
					className={`detail-resume__state detail-resume__state--${props.state}`}
				/>
				<p className="detail-resume__sku">{props.sku}</p>
				<p className="detail-resume__price">{props.price}</p>
				<p className="detail-resume__name">{props.name}</p>
			</div>
		</div>
	);
}

export function PartDetail(props) {
	const numberRef = useRef(null);

	const {actions, state} = React.useContext(StoreContext);

	const containerClasses =
		'part-detail' +
		(props.highlightedNumber ? ' part-detail--highlighted' : '') +
		(props.resumeVisible ? ' part-detail--resume-visible' : '');

	const product = state.area.products.reduce(
		(acc, product) => acc || (product.id === props.productId && product),
		null
	);

	return (
		<div
			className={containerClasses}
			style={{
				bottom: props.position.y + '%',
				left: props.position.x + '%',
			}}
		>
			<a
				className="part-detail__number"
				data-senna-off="true"
				href={product.url && state.app.basePathUrl + product.url}
				onMouseOut={() => actions.highlightDetail(null)}
				onMouseOver={() => actions.highlightDetail(props.number)}
				ref={numberRef}
			>
				{props.number}
			</a>
			<Resume
				containerRef={props.containerRef}
				name={product.name}
				numberRef={numberRef}
				position={props.position}
				price={product.price}
				sku={product.sku}
				state={product.state}
				thumbnailUrl={product.thumbnailUrl}
			/>
		</div>
	);
}

export function SpotsList(props) {
	const {state} = React.useContext(StoreContext);

	let resumeShown = false;

	return state.area.spots.map((detail, i) => {
		const highlightedNumber =
			(state.area.highlightedDetail &&
				state.area.highlightedDetail.number) === detail.number;

		let resumeVisible = false;
		if (
			!resumeShown &&
			highlightedNumber &&
			state.area.highlightedDetail &&
			state.area.highlightedDetail.showFirstResume
		) {
			resumeVisible = true;
			resumeShown = true;
		}

		return (
			<PartDetail
				containerRef={props.containerRef}
				highlightedNumber={highlightedNumber}
				key={i}
				resumeVisible={resumeVisible}
				{...detail}
			/>
		);
	});
}

export function EmptyBoxMessage() {
	return (
		<div className="empty-box-research">
			<h3>{Liferay.Language.get('select-car-and-parts')}</h3>
			<h5>
				{Liferay.Language.get(
					'please-select-the-carmaker-the-model-the-type-and-the-car-parts'
				)}
			</h5>
		</div>
	);
}

function PictureBox() {
	const containerRef = useRef(null);
	const {state} = React.useContext(StoreContext);

	const highlightedModifierClass =
		state.area.highlightedDetail && state.area.highlightedDetail.number
			? ' picture-box--hovered-detail'
			: '';

	return (
		<>
			{state.area.name ? (
				<div className="picture-box-wrapper">
					<div
						className={`picture-box${highlightedModifierClass}`}
						ref={containerRef}
					>
						<SpotsList containerRef={containerRef} />
						<img
							alt={state.area.name}
							className="picture-box__image"
							src={state.area.imageUrl}
						/>
					</div>
				</div>
			) : (
				<EmptyBoxMessage />
			)}
		</>
	);
}

export default PictureBox;
