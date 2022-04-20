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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

const FileSizeField = ({mimeType, size}) => {
	return (
		<ClayLayout.Row>
			<ClayLayout.Col md="6">
				<label htmlFor="mimeType">
					{Liferay.Language.get('mime-type')}

					<span
						className="inline-item-after lfr-portal-tooltip tooltip-icon"
						title={Liferay.Language.get('mime-type-help-message')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					id="mimeType"
					type="text"
					value={mimeType}
				/>
			</ClayLayout.Col>

			<ClayLayout.Col md="6">
				<label htmlFor="size">
					{Liferay.Language.get('maximum-file-size')}

					<span
						className="inline-item-after lfr-portal-tooltip tooltip-icon"
						title={Liferay.Language.get('maximum-file-size-help-message')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					id="size"
					type="number"
					value={size}
				/>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
}

const FileSizePerMimeType = ({description = 'file-size-mimetype-description'}) => {
	return (
		<>
			<p className="text-muted">
				{Liferay.Language.get(description)}
			</p>

			<FileSizeField />
		</>
	);
};

export default FileSizePerMimeType;
