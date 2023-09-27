/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {sub} from '../../utils/utils.es';

const Footer = ({currentIndex, title, totalItems}) => (
	<div className="footer">
		<span>{title}</span>

		<div>
			{sub(Liferay.Language.get('x-of-x'), [
				currentIndex + 1,
				totalItems,
			])}
		</div>
	</div>
);

Footer.propTypes = {
	currentIndex: PropTypes.number.isRequired,
	title: PropTypes.string.isRequired,
	totalItems: PropTypes.number.isRequired,
};

export default Footer;
