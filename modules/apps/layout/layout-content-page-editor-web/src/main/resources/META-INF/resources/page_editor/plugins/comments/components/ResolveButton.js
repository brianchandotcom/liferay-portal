/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import Loader from '../../../common/components/Loader';

export default function ResolveButton({
	disabled = false,
	loading,
	onClick,
	resolved,
}) {
	let icon = (
		<span className="text-lowercase">
			<ClayIcon symbol="check-circle" />
		</span>
	);
	let title = Liferay.Language.get('resolve');

	if (loading) {
		title = undefined;
		icon = <Loader />;
	}
	else if (resolved) {
		icon = (
			<span className="text-lowercase text-success">
				<ClayIcon symbol="check-circle-full" />
			</span>
		);
		title = Liferay.Language.get('reopen');
	}

	return (
		<ClayButton
			borderless
			className={classNames('flex-shrink-0', {
				'lfr-portal-tooltip': !!title,
			})}
			data-title={title}
			disabled={disabled || loading}
			displayType="secondary"
			monospaced
			onClick={onClick}
			outline
			size="sm"
		>
			{icon}
		</ClayButton>
	);
}

ResolveButton.propTypes = {
	disabled: PropTypes.bool,
	loading: PropTypes.bool.isRequired,
	onClick: PropTypes.func.isRequired,
	resolved: PropTypes.bool.isRequired,
};
