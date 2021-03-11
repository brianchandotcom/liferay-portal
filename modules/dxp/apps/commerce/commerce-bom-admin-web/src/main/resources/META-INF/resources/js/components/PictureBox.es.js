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

import React, {useRef, useState} from 'react';

import EditNumberForm from './EditNumberForm.es';
import {StoreContext} from './StoreContext.es';
import Icon from './utilities/Icon.es';

export function PartDetail(props) {
	const numberRef = useRef(null);

	const {actions} = React.useContext(StoreContext);

	const containerClasses =
		'spot-number' +
		(props.highlightedNumber ? ' spot-number--highlighted' : '');

	return (
		<button
			className={containerClasses}
			onClick={() => actions.selectSpot(props.id)}
			onMouseOut={() => actions.highlightDetail(null)}
			onMouseOver={() => actions.highlightDetail(props.number)}
			ref={numberRef}
			style={{
				bottom: props.position.y + '%',
				left: props.position.x + '%',
			}}
		>
			{props.number}
		</button>
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

export function CustomCursor(props) {
	const {state} = React.useContext(StoreContext);

	return (
		<div
			className={`custom-cursor${
				props.visible ? ` custom-cursor--visible` : ``
			}`}
			style={{
				left: props.x ? props.x + 'px' : 0,
				top: props.y ? props.y + 'px' : 0,
			}}
		>
			<Icon spritemap={state.app.spritemap} symbol="plus" />
		</div>
	);
}

function PictureBox() {
	const containerRef = useRef(null);
	const {actions, state} = React.useContext(StoreContext);

	const [cursor, updateCursor] = useState({
		visible: false,
		x: 0,
		y: 0,
	});

	function handleMouseMove(e) {
		switch (true) {
			case e.target.className === 'custom-cursor-wrapper': {
				const containerRect = containerRef.current.getBoundingClientRect();
				const pxLeft = e.pageX - containerRect.left;
				const pxTop = e.pageY - containerRect.top - window.scrollY;

				updateCursor({
					visible: true,
					x: pxLeft,
					y: pxTop,
				});
				break;
			}
			default: {
				updateCursor({
					...cursor,
					visible: false,
				});
				break;
			}
		}
	}

	function handleClick(e) {
		if (e.target.className === 'custom-cursor-wrapper') {
			const containerRect = containerRef.current.getBoundingClientRect();
			const pxLeft = e.pageX - containerRect.left;
			const pxTop = e.pageY - containerRect.top - window.scrollY;
			const x =
				Math.round((100 / containerRect.width) * pxLeft * 1000) / 1000;
			const y =
				100 -
				Math.round((100 / containerRect.height) * pxTop * 1000) / 1000;

			actions.createSpot({
				x,
				y,
			});
		}
	}

	const highlightedModifierClass =
		state.area &&
		state.area.highlightedDetail &&
		state.area.highlightedDetail.number
			? ' picture-box--hovered-detail'
			: '';

	return (
		<>
			{state.area.name && (
				<div className="panel panel-secondary picture-box-wrapper">
					<div className={`picture-box${highlightedModifierClass}`}>
						<div
							className="custom-cursor-wrapper"
							onClick={handleClick}
							onMouseEnter={() =>
								updateCursor({...cursor, visible: true})
							}
							onMouseLeave={() =>
								updateCursor({...cursor, visible: false})
							}
							onMouseMove={handleMouseMove}
							ref={containerRef}
						>
							<CustomCursor
								icon="plus"
								visible={cursor.visible}
								x={cursor.x}
								y={cursor.y}
							/>
							<SpotsList containerRef={containerRef} />
						</div>
						<EditNumberForm />
						<img
							alt={state.area.name}
							className="picture-box__image"
							src={state.area.imageUrl}
						/>
					</div>
				</div>
			)}
		</>
	);
}

export default PictureBox;
