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
import PropTypes from 'prop-types';
import React from 'react';

import Loader from '../../common/Loader.es';

const ResolveButton = props => {
	let icon = (
		<span
			className="lfr-portal-tooltip text-lowercase"
			data-title={Liferay.Language.get('resolve')}
		>
			<ClayIcon symbol="check-circle" />
		</span>
	);

	if (props.loading) {
		icon = <Loader />;
	} else if (props.resolved) {
		icon = (
			<span
				className="lfr-portal-tooltip text-lowercase text-success"
				data-title={Liferay.Language.get('reopen')}
			>
				<ClayIcon symbol="check-circle-full" />
			</span>
		);
	}

	return (
		<ClayButton
			borderless
			className="flex-shrink-0"
			disabled={props.disabled || props.loading}
			displayType="secondary"
			monospaced
			onClick={props.onClick}
			outline
			small
		>
			{icon}
		</ClayButton>
	);
};

ResolveButton.propTypes = {
	disabled: PropTypes.bool,
	loading: PropTypes.bool.isRequired,
	onClick: PropTypes.func.isRequired,
	resolved: PropTypes.bool.isRequired
};

export {ResolveButton};
export default ResolveButton;
