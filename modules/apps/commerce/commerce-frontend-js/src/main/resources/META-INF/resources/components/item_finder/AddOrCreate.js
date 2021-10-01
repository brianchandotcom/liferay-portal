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
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {resolveRendererByType} from '../../utilities/dataRenderers';
import {getValueFromItem} from '../../utilities/index';
import Expose from './Expose';

function Item(props) {
	return (
		<ClayTable.Row>
			{props.fields.map((field, i) => {
				const value = getValueFromItem(props.itemData, field.fieldName);
				const DataRenderer = resolveRendererByType(
					field.contentRenderer
				);

				return (
					<ClayTable.Cell
						expanded
						headingTitle={i === 0}
						key={
							Array.isArray(field.fieldName)
								? field.fieldName[0]
								: field.fieldName
						}
					>
						<DataRenderer
							actions={[]}
							itemData={props.itemData}
							options={field}
							value={value}
						/>
					</ClayTable.Cell>
				);
			})}
			<ClayTable.Cell>
				<ClayButton
					disabled={props.selected}
					displayType="secondary"
					onClick={props.onSelect}
					small
				>
					{Liferay.Language.get('select')}
				</ClayButton>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

class AddOrCreateBase extends Component {
	constructor(props) {
		super(props);
		this.state = {
			focus: false,
			selected: 0,
		};
	}

	focus() {
		this.setState({
			focus: true,
		});
		if (this.props.onFocusIn) {
			this.props.onFocusIn();
		}
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
				onFocus={(event) => this.handleFocusIn(event)}
			>
				<h4 className="align-items-center card-header py-3">
					{this.props.panelHeaderLabel}
				</h4>
				<div className="card-body">
					<div className="input-group">
						<div className="input-group-item">
							<input
								className="form-control input-group-inset input-group-inset-after"
								onChange={(event) =>
									this.props.onInputSearchChange(
										event.target.value
									)
								}
								onFocus={(event) => this.focus(event)}
								placeholder={this.props.inputPlaceholder}
								ref={this.input}
								type="text"
								value={this.props.inputSearchValue}
							/>
							<span className="input-group-inset-item input-group-inset-item-after">
								{this.props.inputSearchValue && (
									<button
										className="btn btn-unstyled"
										onClick={(_e) =>
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
						(this.props.items && !!this.props.items.length)) && (
						<div className="card-body">
							<ClayList>
								{this.props.itemCreation &&
									this.props.inputSearchValue && (
										<>
											<ClayList.Header className="px-0">
												{this.props.createNewItemLabel}
											</ClayList.Header>
											<ClayList.Item
												className={classNames(
													'py-3',
													this.props.items &&
														!!this.props.items
															.length &&
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
															this.props
																.onItemCreated
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
								{this.props.items &&
									this.props.items.length === 0 &&
									!this.props.itemCreation && (
										<ClayList.Header className="d-flex px-0">
											{Liferay.Language.get(
												'no-items-were-found'
											)}
										</ClayList.Header>
									)}
							</ClayList>
							{this.props.items && !!this.props.items.length ? (
								<>
									{this.props.itemCreation && (
										<ClayList.Header className="px-0">
											{this.props.titleLabel}
										</ClayList.Header>
									)}
									<ClayTable
										borderless
										hover={false}
										responsive
									>
										<ClayTable.Body>
											{this.props.items.map((item, i) => (
												<Item
													fields={this.props.schema}
													itemData={item}
													key={
														item[
															this.props.itemsKey
														] || i
													}
													onSelect={() =>
														this.props.onItemSelected(
															item[
																this.props
																	.itemsKey
															]
														)
													}
													selected={this.props.selectedItems.includes(
														item[
															this.props.itemsKey
														]
													)}
												/>
											))}
										</ClayTable.Body>
									</ClayTable>
									<ClayPaginationBarWithBasicItems
										activeDelta={this.props.pageSize}
										activePage={this.props.currentPage}
										className="mt-3"
										deltas={this.props.deltas}
										ellipsisBuffer={3}
										onDeltaChange={(deltaVal) => {
											this.props.updateCurrentPage(1);
											this.props.updatePageSize(deltaVal);
										}}
										onPageChange={
											this.props.updateCurrentPage
										}
										spritemap={this.props.spritemap}
										totalItems={this.props.itemsCount}
									/>
								</>
							) : null}
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
			label: PropTypes.number,
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
	schema: PropTypes.arrayOf(
		PropTypes.shape({
			contentRenderer: PropTypes.string,
			fieldName: PropTypes.oneOfType([
				PropTypes.string,
				PropTypes.arrayOf(PropTypes.string),
			]).isRequired,
		})
	),
	selectedItems: PropTypes.arrayOf(
		PropTypes.oneOfType([PropTypes.number, PropTypes.string])
	),
	titleLabel: PropTypes.string,
	updateCurrentPage: PropTypes.func,
	updatePageSize: PropTypes.func,
};

AddOrCreateBase.defaultProps = {
	createNewItemLabel: Liferay.Language.get('create-new-item'),
	deltas: [
		{
			label: 5,
		},
		{
			label: 10,
		},
		{
			label: 20,
		},
		{
			label: 30,
		},
		{
			label: 50,
		},
		{
			label: 75,
		},
	],
	inputPlaceholder: Liferay.Language.get('find-an-option-or-create-one'),
	inputSearchValue: '',
	panelHeaderLabel: Liferay.Language.get('add-new'),
	titleLabel: Liferay.Language.get('select-an-existing-one'),
};

export default React.forwardRef((props, ref) => {
	const [active, setActive] = React.useState(false);

	function closeAndSubmit(event) {
		props.onSubmit(event);
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
