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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import TestrayIcons from '~/components/Icons/TestrayIcon';

type HeaderBreadcrumbTriggerProps = {
	displayCarret?: boolean;
	symbol: string;
	title: string;
};

const HeaderBreadcrumbTrigger: React.FC<HeaderBreadcrumbTriggerProps> = ({
	displayCarret,
	symbol,
	title,
}) => (
	<div className="align-items-center d-flex" title={title}>
		<TestrayIcons
			className="dropdown-poll-icon mr-2"
			fill="darkblue"
			size={35}
			symbol={symbol || 'polls'}
		/>

		{displayCarret && (
			<ClayIcon
				className={classNames('dropdown-arrow-icon')}
				color="darkblue"
				symbol="caret-bottom"
			/>
		)}
	</div>
);

export default HeaderBreadcrumbTrigger;
