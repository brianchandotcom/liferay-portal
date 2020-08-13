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

import ClayList from '@clayui/list';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

function TimelineEntry({date, description, title}) {
	return (
		<li className="timeline-item">
			<div className="panel panel-secondary">
				<div className="timeline-increment">
					<span className="timeline-icon"></span>
				</div>
				<div className="panel-body">
					<div className="mb-2 row">
						<div className="col">
							<h4 className="mb-0">{title}</h4>
						</div>
						<div className="col-auto">{description}</div>
					</div>
					<small>{date}</small>
				</div>
			</div>
		</li>
	);
}

TimelineEntry.propTypes = {
	date: PropTypes.string.isRequired,
	description: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};

TimelineEntry.defaultProps = {};

function Timeline({dataSetDisplayContext, items}) {
	return (
		<ClayList className={classNames('mb-0', 'timeline')}>
			{items.map((item, i) => (
				<TimelineEntry
					key={i}
					{...item}
					borderBottom={i !== items.length - 1}
					dataSetDisplayContext={dataSetDisplayContext}
				/>
			))}
		</ClayList>
	);
}

Timeline.propTypes = {
	dataRenderers: PropTypes.object,
	dataSetDisplayContext: PropTypes.any,
	items: PropTypes.array,
};

Timeline.defaultProps = {
	items: [],
};

export default Timeline;
