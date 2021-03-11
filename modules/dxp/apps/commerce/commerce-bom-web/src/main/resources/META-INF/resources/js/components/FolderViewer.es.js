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

import React, {useContext, useState} from 'react';

import {StoreContext} from './StoreContext.es';
import Collapse from './collapse/Collapse.es';
import Icon from './utilities/Icon.es';

function ModelsList(props) {
	return (
		<ul className="list-group">
			{props.models.map(({name, power, productionYears}, i) => (
				<li className="list-group-item" key={i}>
					<div className="row">
						<div className="col">{name}</div>
						<div className="col-auto text-right">{power}</div>
						<div className="col-4 text-right">
							{productionYears}
						</div>
					</div>
				</li>
			))}
		</ul>
	);
}

function Brands(props) {
	const [selectedBrand, setSelectedBrand] = useState(0);

	const {state} = useContext(StoreContext);

	return (
		<div className="panel panel-secondary sticky-panel suitable-veichles">
			<div className="panel-heading">
				<h2 className="panel-title">
					{Liferay.Language.get('suitable-veichles')}
				</h2>
			</div>
			<div className="panel-body">
				{props.data.map((el, i) => (
					<Collapse
						additionalWrapperClasses={i !== 0 && 'mt-3'}
						closedIcon={
							<Icon
								spritemap={state.app.spritemap}
								symbol="plus"
							/>
						}
						content={<ModelsList models={el.models} />}
						handleClose={() => setSelectedBrand(null)}
						handleOpen={() => setSelectedBrand(i)}
						key={i}
						open={i === selectedBrand}
						openIcon={
							<Icon
								spritemap={state.app.spritemap}
								symbol="minus"
							/>
						}
						title={el.name}
					/>
				))}
			</div>
		</div>
	);
}

function Card(props) {
	const {state} = useContext(StoreContext);

	const itemsCard = (
		<div className="area-card card image-card">
			<div className="aspect-ratio aspect-ratio-4-to-3 bg-checkered card-item-first">
				<img
					alt={props.title}
					className="aspect-ratio-item-center-middle"
					src={props.thumbnail}
				/>
			</div>
			<div className="card-body">
				<div className="card-row">
					<div className="autofit-col autofit-col-expand">
						<div
							className="card-title text-truncate"
							data-title={props.title}
							title={props.title}
						>
							{props.title}
						</div>
					</div>
				</div>
			</div>
		</div>
	);

	function handleCardLink(e) {
		e.preventDefault();
		const url = `?${props.type === 'folder' ? 'folderId' : 'areaId'}=${
			props.id
		}`;
		state.app.history.push(url);
	}

	return (
		<a
			className="card-link"
			data-senna-off
			href="#"
			onClick={handleCardLink}
		>
			{itemsCard}
		</a>
	);
}

function CardContainer(props) {
	return (
		<div className="area-list row">
			{props.items.map((el, i) => {
				return (
					<div
						className={props.cardsWrapperAdditionalClasses}
						key={i}
					>
						<Card
							id={el.id}
							thumbnail={el.thumbnail}
							title={el.name}
							type={el.type}
						/>
					</div>
				);
			})}
		</div>
	);
}

export default function FolderViewer() {
	const {state} = useContext(StoreContext);

	return state.folder.items ? (
		<div className="row">
			<div className="col">
				<CardContainer
					cardsWrapperAdditionalClasses={
						state.folder.brands ? 'col-3' : 'col-2'
					}
					items={state.folder.items}
				/>
			</div>
			{state.folder.brands && (
				<div className="col-sm-4 position-relative">
					<Brands data={state.folder.brands} />
				</div>
			)}
		</div>
	) : null;
}
