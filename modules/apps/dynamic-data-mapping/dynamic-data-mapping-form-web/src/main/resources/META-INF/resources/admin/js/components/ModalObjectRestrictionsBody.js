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

import ClayPanel from '@clayui/panel';
import React from 'react';

const ModalObjectRestrictionsBody = ({fieldsGroupedByType}) => (
	<>
		<p>
			{Liferay.Language.get(
				'all-fields-in-this-form-must-be-mapped-to-a-field-in-the-object'
			)}
		</p>

		<ClayPanel
			displayTitle={Liferay.Language.get('form-fields-not-mapped')}
			displayType="secondary"
		>
			<ClayPanel.Body>
				{fieldsGroupedByType.map(({fields, type}) => (
					<div key={type}>
						<strong className="text-capitalize">{type}</strong>

						<ol>
							{fields.map(({fieldName, label}) => (
								<li key={fieldName}>{label}</li>
							))}
						</ol>
					</div>
				))}
			</ClayPanel.Body>
		</ClayPanel>
	</>
);

export default ModalObjectRestrictionsBody;
