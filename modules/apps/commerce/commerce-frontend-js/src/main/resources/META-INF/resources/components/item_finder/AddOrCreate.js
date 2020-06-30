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
import ClayList from '@clayui/list';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {getSchemaString} from '../../utilities/index.es';
import Expose from './Expose';

function Item(props) {
	return (
		<ClayList.Item className={classNames('py-3', props.className)} flex>
			<ClayList.ItemField expand>
				<ClayList.ItemTitle>{props.title}</ClayList.ItemTitle>
			</ClayList.ItemField>
			<ClayList.ItemField>
				<ClayButton
					disabled={props.selected}
					displayType="secondary"
					onClick={props.onSelect}
					small
				>
					{Liferay.Language.get('select')}
				</ClayButton>
			</ClayList.ItemField>
		</ClayList.Item>
	);
}

class AddOrCreateBase extends Component {
	constructor(props) {
		super(props);
		this.state = {
			focus: false,
			selected: 0
		};
	}

	focus() {
		this.setState({
			focus: true
		});
		if (this.props.onFocusIn) {
			this.props.onFocusIn();
		}
	}

	blur() {
		this.setState({focus: false});
	}

	select(selected) {
		this.setState({selected});
	}

	handleFocusOut(_e) {
		this._timeoutID = setTimeout(() => {
			if (this.props.onFocusOut) {
				this.props.onFocusOut();
			}
		}, 0);
	}

	handleFocusIn(_e) {
		clearTimeout(this._timeoutID);
	}

	render() {
		return (
			<div
				className={`card mb-0 add-or-create ${
					this.state.focus ? 'has-focus' : ''
				}`}
				onFocus={e => this.handleFocusIn(e)}
			>
				<h4 className="card-header align-items-center py-3">
					{this.props.panelHeaderLabel}
				</h4>
				<div className="card-body">
					<div className="input-group">
						<div className="input-group-item">
							<input
								className="form-control input-group-inset input-group-inset-after"
								onChange={e =>
									this.props.onInputSearchChange(
										e.target.value
									)
								}
								onFocus={e => this.focus(e)}
								placeholder={this.props.inputPlaceholder}
								ref={this.input}
								type="text"
								value={this.props.inputSearchValue}
							/>
							<span className="input-group-inset-item input-group-inset-item-after">
								{this.props.inputSearchValue && (
									<button
										className="btn btn-unstyled"
										onClick={_e =>
											this.props.onInputSearchChange('')
										}
										type="button"
									>
										<ClayIcon symbol="times" />
									</button>
								)}
							</span>
						</div>
					</div>
				</div>
				{this.props.active &&
					(this.props.inputSearchValue ||
						(this.props.items && this.props.items.length)) && (
						<div className="card-body">
							<ClayList>
								{this.props.inputSearchValue && (
									<>
										<ClayList.Header>
											{this.props.createNewItemLabel}
										</ClayList.Header>
										<ClayList.Item
											className={classNames(
												'py-3',
												this.props.items &&
													this.props.items.length &&
													'border-bottom mb-3'
											)}
											flex
										>
											<ClayList.ItemField expand>
												<ClayList.ItemTitle>
													&quot;
													{
														this.props
															.inputSearchValue
													}
													&quot;
												</ClayList.ItemTitle>
											</ClayList.ItemField>

											<ClayList.ItemField>
												<ClayButton
													onClick={
														this.props.onItemCreated
													}
													small
												>
													{Liferay.Language.get(
														'create-new'
													)}
												</ClayButton>
											</ClayList.ItemField>
										</ClayList.Item>
									</>
								)}

								{this.props.items && this.props.items.length ? (
									<>
										<ClayList.Header>
											{this.props.titleLabel}
										</ClayList.Header>
										{this.props.items.map((item, i) => (
											<Item
												className={classNames(
													i !==
														this.props.items
															.length -
															1 && 'border-bottom'
												)}
												key={
													item[this.props.itemsKey] ||
													i
												}
												onSelect={() =>
													this.props.onItemSelected(
														item[
															this.props.itemsKey
														]
													)
												}
												selected={this.props.selectedItems.includes(
													item[this.props.itemsKey]
												)}
												title={getSchemaString(
													item,
													this.props.schema.itemTitle
												)}
											/>
										))}
										<ClayPaginationBarWithBasicItems
											activeDelta={this.props.pageSize}
											activePage={this.props.currentPage}
											className="mt-3"
											deltas={this.props.deltas}
											ellipsisBuffer={3}
											onDeltaChange={deltaVal => {
												this.props.updateCurrentPage(1);
												this.props.updatePageSize(
													deltaVal
												);
											}}
											onPageChange={
												this.props.updateCurrentPage
											}
											spritemap={this.props.spritemap}
											totalItems={this.props.itemsCount}
										/>
									</>
								) : null}
							</ClayList>
						</div>
					)}
			</div>
		);
	}
}

AddOrCreateBase.propTypes = {
	createNewItemLabel: PropTypes.string,
	currentPage: PropTypes.number,
	deltas: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.number
		})
	),
	inputPlaceholder: PropTypes.string,
	inputSearchValue: PropTypes.string,
	itemsCount: PropTypes.number,
	itemsKey: PropTypes.string,
	onAddItem: PropTypes.func,
	onCreateItem: PropTypes.func,
	onFocusIn: PropTypes.func,
	onFocusOut: PropTypes.func,
	onInputSearchChange: PropTypes.func,
	onItemSelected: PropTypes.func,
	onSubmit: PropTypes.func,
	pageSize: PropTypes.number,
	panelHeaderLabel: PropTypes.string,
	schema: PropTypes.shape({
		itemTitle: PropTypes.oneOfType([
			PropTypes.arrayOf(PropTypes.string),
			PropTypes.string
		])
	}),
	selectedItems: PropTypes.arrayOf(
		PropTypes.oneOfType([PropTypes.number, PropTypes.string])
	),
	titleLabel: PropTypes.string,
	updateCurrentPage: PropTypes.func,
	updatePageSize: PropTypes.func
};

AddOrCreateBase.defaultProps = {
	createNewItemLabel: Liferay.Language.get('create-new-item'),
	deltas: [
		{
			label: 5
		},
		{
			label: 10
		},
		{
			label: 20
		},
		{
			label: 30
		},
		{
			label: 50
		},
		{
			label: 75
		}
	],
	inputPlaceholder: Liferay.Language.get('find-an-option-or-create-one'),
	inputSearchValue: '',
	panelHeaderLabel: Liferay.Language.get('add-new'),
	titleLabel: Liferay.Language.get('select-an-existing-one')
};

export default React.forwardRef((props, ref) => {
	const [active, setActive] = React.useState(false);

	function closeAndSubmit(e) {
		props.onSubmit(e);
		setActive(false);
	}

	return (
		<Expose active={active} onClose={() => setActive(false)}>
			<AddOrCreateBase
				{...props}
				active={active}
				innerRef={ref}
				onFocusIn={() => setActive(true)}
				onFocusOut={() => setActive(false)}
				onSubmit={closeAndSubmit}
			/>
		</Expose>
	);
});
