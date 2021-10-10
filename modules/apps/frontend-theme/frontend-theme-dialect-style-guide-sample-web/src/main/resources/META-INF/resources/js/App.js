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

import ClayLayout from '@clayui/layout';
import React from 'react';

import '../css/main.scss';
import ButtonGuide from './guides/ButtonGuide';
import ColorGuide from './guides/ColorGuide';
import GeneralGuide from './guides/GeneralGuide';
import TypographyGuide from './guides/TypographyGuide';

export default function App() {
	return (
		<div className="dialect-style-guide">
			<ClayLayout.ContainerFluid>
				<ClayLayout.Row>
					<ClayLayout.Col>
						<h1>
							{Liferay.Language.get('dialect-style-guide-sample')}
						</h1>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<ColorGuide />

				<TypographyGuide />

				<GeneralGuide />

				<ButtonGuide />
			</ClayLayout.ContainerFluid>
		</div>
	);
}
