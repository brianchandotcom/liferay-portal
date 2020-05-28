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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

export default function () {
	return (
		<>
			<ClayForm.Group>
				<label className="dpt-mapping-label" htmlFor="titleSelector">
					<div className="control-label">
						{Liferay.Language.get('html-title')}
					</div>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								className="dpt-mapping-input"
								id="title"
								readOnly
								type="text"
							/>
						</ClayInput.GroupItem>
						<ClayInput.GroupItem shrink>
							<ClayButton
								className="dpt-mapping-btn"
								displayType="secondary"
								id="titleSelector"
								monospaced
							>
								<ClayIcon symbol="bolt" />
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</label>
			</ClayForm.Group>
		</>
	);
}
