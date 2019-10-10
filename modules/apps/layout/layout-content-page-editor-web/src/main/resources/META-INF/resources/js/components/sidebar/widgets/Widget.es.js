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

import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

const Widget = props => {
	const buttonClassName = classNames(
		'btn btn-sm btn-unstyled d-block mb-1 px-2 py-1',
		props.widget.used && !props.widget.instanceable
			? 'text-muted'
			: 'fragments-editor__drag-source fragments-editor__drag-source--sidebar-widget'
	);

	return (
		<button
			className={buttonClassName}
			data-drag-source-label={props.widget.title}
			data-instanceable={props.widget.instanceable || null}
			data-portlet-id={props.widget.portletId}
			type="button"
		>
			<ClayIcon
				className="mr-2"
				symbol={props.widget.instanceable ? 'grid' : 'live'}
			/>
			<span>{props.widget.title}</span>
		</button>
	);
};

Widget.propTypes = {
	widget: PropTypes.shape({
		instanceable: PropTypes.bool.isRequired,
		title: PropTypes.string.isRequired,
		used: PropTypes.bool.isRequired
	}).isRequired
};

export default Widget;
