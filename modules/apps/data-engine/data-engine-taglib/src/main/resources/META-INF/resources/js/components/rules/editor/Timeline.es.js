/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Timeline.scss';

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
		className={classnames('form-rule-builder-timeline timeline', className)}
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
					className="timeline-dropdown"
					items={items}
					trigger={
						<ClayButton
							className="mr-3"
							disabled={disabled}
							displayType="secondary"
							small
						>
							<span className="text-uppercase">{operator}</span>

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
		className={classnames('form-builder-rule-builder', className)}
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
