/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React from 'react';

interface ITitleProps {
	value: string;
}

const Title: React.FC<ITitleProps> = ({value}) => {
	return (
		<Text color="secondary" size={3} weight="semi-bold">
			<span className="text-uppercase">{value}</span>
		</Text>
	);
};

export default Title;
