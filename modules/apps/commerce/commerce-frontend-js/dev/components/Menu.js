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
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import React from 'react';

import launcher from '../../../src/main/resources/META-INF/resources/utilities/launcher';
import components from './index';

function getCurrentPage() {
	return (
		window.location.hash &&
		components.find((c) => c.entry === window.location.hash.slice(1)).page
	);
}

function Menu({spritemap}) {
	const [current, setComponent] = React.useState(getCurrentPage());

	return (
		<div className="container-fluid">
			<div className="row">
				<div className="col-3">
					<ClayList>
						<ClayList.Header>Components</ClayList.Header>

						{components
							.filter((c) => c.page)
							.map((component) => (
								<ClayList.Item flex key={component.entry}>
									<ClayList.ItemField>
										<ClaySticker
											displayType="light"
											size="sm"
										>
											<ClayIcon
												spritemap={spritemap}
												symbol="caret-right"
											/>
										</ClaySticker>
									</ClayList.ItemField>
									<ClayList.ItemField>
										<ClayLink
											href={`#${component.entry}`}
											onClick={() =>
												setComponent(component.page)
											}
										>
											{component.name}
										</ClayLink>
									</ClayList.ItemField>
								</ClayList.Item>
							))}
					</ClayList>
				</div>
				<div className="col-9">
					{current && <iframe frameBorder="0" src={current}></iframe>}
				</div>
			</div>
		</div>
	);
}

launcher(Menu, 'menu', 'menu-root', {
	spritemap: './assets/clay/icons.svg',
});
