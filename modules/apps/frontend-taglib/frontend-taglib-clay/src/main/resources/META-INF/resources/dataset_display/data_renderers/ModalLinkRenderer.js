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

import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import DatasetDisplayContext from '../DatasetDisplayContext';
import DefaultContent from './DefaultRenderer';

function ModalLinkRenderer({value}) {
	const {openModal} = useContext(DatasetDisplayContext);

	return (
		<div className="table-list-title">
			<ClayLink
				data-senna-off
				href="#"
				onClick={(event) => {
					event.preventDefault();
					openModal({
						size: value.size,
						title: value.title,
						url: value.href,
					});
				}}
			>
				<DefaultContent value={value} />
			</ClayLink>
		</div>
	);
}

ModalLinkRenderer.propTypes = {
	value: PropTypes.shape({
		href: PropTypes.string.isRequired,
		icon: PropTypes.string,
		label: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	}).isRequired,
};

export default ModalLinkRenderer;
