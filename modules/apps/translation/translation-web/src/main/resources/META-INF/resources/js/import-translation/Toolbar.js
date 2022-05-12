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
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import React from 'react';

const noop = () => {};

export default function Toolbar({
	cancelURL,
	onSave = noop,
	publishButtonDisabled,
	publishButtonLabel,
	saveButtonDisabled,
	saveButtonLabel,
	title,
}) {
	return (
		<ClayToolbar className="subnav-tbar-light">
			<ClayLayout.ContainerFluid>
				<ClayToolbar.Nav>
					<ClayToolbar.Item expand>
						<ClayToolbar.Section className="pl-2 text-left">
							<h2
								className="h4 text-truncate-inline"
								title={title}
							>
								<span className="text-truncate">{title}</span>
							</h2>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayToolbar.Section>
							<ClayButton.Group spaced>
								<ClayLink
									button
									displayType="secondary"
									href={cancelURL}
									small
								>
									{Liferay.Language.get('cancel')}
								</ClayLink>

								<ClayButton
									disabled={saveButtonDisabled}
									displayType="secondary"
									onClick={onSave}
									small
									type="submit"
								>
									{saveButtonLabel}
								</ClayButton>

								<ClayButton
									disabled={publishButtonDisabled}
									displayType="primary"
									small
									type="submit"
								>
									{publishButtonLabel}
								</ClayButton>
							</ClayButton.Group>
						</ClayToolbar.Section>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayLayout.ContainerFluid>
		</ClayToolbar>
	);
}
