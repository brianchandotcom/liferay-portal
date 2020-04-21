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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

export default function Undo() {
	return (
		<ClayButton.Group className="d-block d-none mr-3">
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('undo')}
				className="btn-monospaced"
				displayType="secondary"
				small
				symbol="undo"
				title={Liferay.Language.get('undo')}
			/>
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('redo')}
				className="btn-monospaced"
				displayType="secondary"
				small
				symbol="redo"
				title={Liferay.Language.get('redo')}
			/>
		</ClayButton.Group>
	);
}
