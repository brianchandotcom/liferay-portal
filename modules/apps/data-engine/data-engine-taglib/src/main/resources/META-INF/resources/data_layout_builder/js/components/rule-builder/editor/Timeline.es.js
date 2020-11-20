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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import classnames from 'classnames';
import React from 'react';

const TimelineIncrement = ({children, className, ...otherProps}) => (
	<div
		{...otherProps}
		className={classnames(className, 'timeline-increment')}
	>
		{children}
	</div>
);

const TimelineIncrementIcon = (props) => (
	<TimelineIncrement {...props}>
		<span className="timeline-icon"></span>
	</TimelineIncrement>
);

const TimelineIncrementButton = (props) => (
	<TimelineIncrement>
		<ClayButtonWithIcon
			className="rounded-circle"
			small
			symbol="plus"
			{...props}
		/>
	</TimelineIncrement>
);

const FormGroupItem = ({children, className, ...otherProps}) => (
	<div {...otherProps} className={classnames('form-group-item', className)}>
		{children}
	</div>
);

const Panel = ({bottomContent, children, expression}) => (
	<ClayPanel displayTitle={<></>} displayType="secondary">
		<ClayPanel.Body>
			<div className="form-group-autofit">
				<FormGroupItem className="form-group-item-label form-group-item-shrink">
					<h4>
						<span className="text-truncate-inline">
							<span className="text-truncate">{expression}</span>
						</span>
					</h4>
				</FormGroupItem>
				{children}
			</div>
			{bottomContent}
			<TimelineIncrementIcon />
		</ClayPanel.Body>
	</ClayPanel>
);

const ActionTrash = (props) => (
	<div className="container-trash">
		<ClayButtonWithIcon
			displayType="secondary"
			small
			symbol="trash"
			{...props}
		/>
	</div>
);

const Operator = ({operator}) => (
	<ClayPanel className="inline-item operator" displayType="secondary">
		<ClayPanel.Body>
			<span className="text-uppercase">{operator}</span>
		</ClayPanel.Body>
	</ClayPanel>
);

const List = ({children, className, ...otherProps}) => (
	<ul
		{...otherProps}
		className={classnames(
			'liferay-ddm-form-rule-builder-timeline timeline can-remove-item',
			className
		)}
	>
		{children}
	</ul>
);

const ListItem = ({children, className, ...otherProps}) => (
	<li {...otherProps} className={classnames(className, 'timeline-item')}>
		{children}
	</li>
);

const ListItemAction = ({children, className, ...otherProps}) => (
	<li
		{...otherProps}
		className={classnames(
			className,
			'timeline-item timeline-increment-action-button'
		)}
	>
		{children}
	</li>
);

const ListHeader = ({disabled, items, operator, title, ...otherProps}) => (
	<ListItem {...otherProps}>
		<ClayPanel displayTitle={title} displayType="secondary">
			{operator && (
				<ClayDropDownWithItems
					items={items}
					trigger={
						<ClayButton
							className="dropdown-toggle-operator mr-3"
							disabled={disabled}
							displayType="secondary"
						>
							<span className="dropdown-toggle-selected-value text-uppercase">
								{operator}
							</span>
							<span className="inline-item inline-item-after">
								<ClayIcon symbol="caret-bottom" />
							</span>
						</ClayButton>
					}
				/>
			)}
			<TimelineIncrementIcon />
		</ClayPanel>
	</ListItem>
);

const Timeline = ({children, className, ...otherProps}) => (
	<div
		{...otherProps}
		className={classnames(
			'form-builder-rule-builder liferay-ddm-form-builder-rule-builder-content',
			className
		)}
	>
		{children}
	</div>
);

Timeline.ActionTrash = ActionTrash;
Timeline.FormGroupItem = FormGroupItem;
Timeline.Header = ListHeader;
Timeline.IncrementButton = TimelineIncrementButton;
Timeline.IncrementIcon = TimelineIncrementIcon;
Timeline.Item = ListItem;
Timeline.ItemAction = ListItemAction;
Timeline.List = List;
Timeline.Operator = Operator;
Timeline.Panel = Panel;

export default Timeline;
